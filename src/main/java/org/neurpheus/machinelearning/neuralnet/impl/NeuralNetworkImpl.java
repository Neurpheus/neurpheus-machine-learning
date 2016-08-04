/*
 * Neurpheus - Machine Learning Package
 *
 * Copyright (C) 2009, 2016 Jakub Strychowski
 *
 *  This library is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as published by the Free
 *  Software Foundation; either version 3.0 of the License, or (at your option)
 *  any later version.
 *
 *  This library is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 *  for more details.
 */

package org.neurpheus.machinelearning.neuralnet.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import org.neurpheus.machinelearning.classification.ClassificationException;
import org.neurpheus.machinelearning.classification.ClassificationResult;
import org.neurpheus.machinelearning.classification.SimpleClassificationResult;
import org.neurpheus.machinelearning.neuralnet.AbstractNeuralNetwork;
import org.neurpheus.machinelearning.neuralnet.DirectSynapse;
import org.neurpheus.machinelearning.neuralnet.FullSynapse;
import org.neurpheus.machinelearning.neuralnet.LinearLayer;
import org.neurpheus.machinelearning.neuralnet.LogLayer;
import org.neurpheus.machinelearning.neuralnet.NeuralNetwork;
import org.neurpheus.machinelearning.neuralnet.NeuralNetworkException;
import org.neurpheus.machinelearning.neuralnet.NeuralNetworkLayer;
import org.neurpheus.machinelearning.neuralnet.NeuralNetworkSynapse;
import org.neurpheus.machinelearning.neuralnet.SigmoidLayer;
import org.neurpheus.machinelearning.neuralnet.SoftmaxLayer;
import org.neurpheus.machinelearning.neuralnet.TanhLayer;
import org.neurpheus.machinelearning.neuralnet.xml.Layer;
import org.neurpheus.machinelearning.neuralnet.xml.Synapse;
import org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork;
import org.neurpheus.machinelearning.training.TrainingExample;
import org.neurpheus.machinelearning.training.TrainingException;
import org.neurpheus.machinelearning.training.TrainingSetException;

/**
 * An implementation of the neural network interface.
 *
 * @author Jakub Strychowski
 */
public class NeuralNetworkImpl extends AbstractNeuralNetwork implements NeuralNetwork, Serializable {
    
    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061227091521L;
    
    /** The logger used by this class. */
    private static Logger logger = Logger.getLogger(NeuralNetworkImpl.class.getName());
    
    public static final String PROPERTY_ETHA = "etha";
    public static final String PROPERTY_ETHA_MULTIPLER = "etha.multipler";
    public static final String PROPERTY_ALPHA = "alpha";
    public static final String PROPERTY_LEARNING_ALGORITHM = "learningAlgorythm";
    public static final String PROPERTY_MOMENTUM_RATE = "momentum.rate";
    public static final String PROPERTY_MAX_JUMP_FACTOR = "quickprop.maxJumpFactor";
    public static final String PROPERTY_MINIMUM_ERROR_VALUE = "minimumErrorValue";
    public static final String PROPERTY_INPUTS_CLASSIFICATION = "inputsClassification";
    
    public static final double DEFAULT_ETHA = 1;
    public static final double DEFAULT_ETHA_MULTIPLER = 0.3;
    public static final double DEFAULT_ALPHA = 0;

    public static final int BACKPROPAGATION_ONLINE = 0;
    public static final int BACKPROPAGATION_OFFLINE = 1;
    public static final int QUICKPROB = 2;
    
    
    private boolean classificationMode = false;
    
    /** Creates a new instance of NeuralNetworkImpl */
    public NeuralNetworkImpl() {
        setLayers(new ArrayList());
    }

    public static double evaluateExpr(String expression, Properties variables) throws NeuralNetworkException {
        int pos = expression.indexOf('*');
        if (pos >= 0) {
          String s1 = expression.substring(0, pos);
          String s2 = expression.substring(pos + 1);
          return evaluateExpr(s1, variables) * evaluateExpr(s2, variables);
        } 
        pos = expression.indexOf('/');
        if (pos >= 0) {
          String s1 = expression.substring(0, pos);
          String s2 = expression.substring(pos + 1);
          return evaluateExpr(s1, variables) / evaluateExpr(s2, variables);
        } 
        pos = expression.indexOf('+');
        if (pos >= 0) {
          String s1 = expression.substring(0, pos);
          String s2 = expression.substring(pos + 1);
          return evaluateExpr(s1, variables) + evaluateExpr(s2, variables);
        } 
        pos = expression.indexOf('-');
        if (pos >= 0) {
          String s1 = expression.substring(0, pos);
          String s2 = expression.substring(pos + 1);
          return evaluateExpr(s1, variables) - evaluateExpr(s2, variables);
        } 
        try {
            return Double.parseDouble(expression);
        } catch (NumberFormatException e) {
            // search for variables
            for (final Iterator it = variables.keySet().iterator(); it.hasNext();) {
                String var = (String) it.next();
                if (var.equals(expression)) {
                    return evaluateExpr(variables.getProperty(var), variables);
                }
            }
            throw new NeuralNetworkException("Unknown variable : "  + expression);
        }
    }
    
    public static NeuralNetworkLayer createLayer(Layer xmlLayer, Properties variables) throws NeuralNetworkException {
        String id = xmlLayer.getId();
        String type = xmlLayer.getType();
        int numberOfNeurons = (int) Math.round(evaluateExpr(xmlLayer.getNumberOfNeurons(), variables));
        NeuralNetworkLayer result;
        if ("sigmoid".equals(type)) {
            result =  new SigmoidLayer(id, numberOfNeurons);
        } else if ("linear".equals(type)) {
            result =  new LinearLayer(id, numberOfNeurons);
        } else if ("tanh".equals(type)) {
            result =  new TanhLayer(id, numberOfNeurons);
        } else if ("log".equals(type)) {
            result =  new LogLayer(id, numberOfNeurons);
        } else if ("softmax".equals(type)) {
            result =  new SoftmaxLayer(id, numberOfNeurons);
        } else {
            throw new NeuralNetworkException("Unknown layer type: " + type);
        } 
        return result;
    }
    
    /** Creates a new instance of NeuralNetworkImpl */
    public NeuralNetworkImpl(XmlNeuralNetwork architecture, Properties variables) throws NeuralNetworkException {
        setLayers(new ArrayList());
        Layer[] xmlLayers = architecture.getLayers().getLayer();
        Map mapping = new HashMap();
        for (int i = 0; i < xmlLayers.length; i++) {
            Layer xmlLayer = xmlLayers[i];
            NeuralNetworkLayer layer = createLayer(xmlLayer, variables);
            addLayer(layer);
            mapping.put(layer.getName(), layer);
        }
        Synapse[] xmlSynapses = architecture.getSynapses().getSynapse();
        for (int i = 0; i < xmlSynapses.length; i++) {
            Synapse xmlSynapse = xmlSynapses[i];
            String sourceId = xmlSynapse.getSourceLayerLayerId();
            String destinationId = xmlSynapse.getDestinationLayerLayerId();
            String type = xmlSynapse.getType();
            NeuralNetworkLayer sourceLayer = (NeuralNetworkLayer) mapping.get(sourceId);
            if (sourceLayer == null) {
                throw new NeuralNetworkException("Unknown source layer : " + sourceId);
            }
            NeuralNetworkLayer destinationLayer = (NeuralNetworkLayer) mapping.get(destinationId);
            if (destinationLayer == null) {
                throw new NeuralNetworkException("Unknown destination layer: " + destinationId);
            }
            if ("full".equals(type)) {
                sourceLayer.connectWith(destinationLayer, FullSynapse.class);
            } else if ("direct".equals(type)) {
                sourceLayer.connectWith(destinationLayer, DirectSynapse.class);
                
            }
        }
    }
    
            
    /**
     * Classifies an object into one or several categories returned 
     * as a list of {@link ClassificationResult} objects.
     * 
     * @param obj The object which should be classified.
     *
     * @return The list of result categories.
     *
     * @throws ClassificationException if any classification error occurred.
     */
    public List classify(Object obj) throws ClassificationException {
        boolean unregister = false;
        NeuralNetworkProcessingState state = 
                NeuralNetworkProcessingState.getNeuralNetworkProcessingState(this);
        if (state == null) {
            state = NeuralNetworkProcessingState.registerNeuralNetwork(this);
            unregister = true;
        }
        double[] inputs = state.getLayerInputs(getInputLayer());
        state.reset(inputs == obj);
        double[] outputs = null;
        if (obj instanceof TrainingExample) {
            TrainingExample example = (TrainingExample) obj;
            try {
                example.activate(this);
            } catch (TrainingSetException e) {
                throw new ClassificationException(
                        "Cannot activate neural network using the given training example.", e);
            }
        } else if (obj == inputs || obj instanceof double[]) {
            double[] tab = (double[]) obj;
            if (tab.length != inputs.length) {
                throw new ClassificationException(
                        "The number of inputs values should correspondence " 
                        + "to the number of inputs of neural network.");
            }
            if (tab != inputs) {
                System.arraycopy(tab, 0, inputs, 0, tab.length);
            }
        } else {
            throw new ClassificationException(
                "The type of a given object is not supported. Object class :" + obj.getClass().getName());
        }
        for (final Iterator it = getLayers().iterator(); it.hasNext(); ) {
            NeuralNetworkLayer layer = (NeuralNetworkLayer) it.next();
            layer.propagate(state.getLayerInputs(layer), state.getLayerOutputs(layer));
            for (final Iterator synIt = layer.getOutputSynapses().iterator(); synIt.hasNext();) {
                NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) synIt.next();
                synapse.propagate(state.getLayerOutputs(layer), state.getLayerInputs(synapse.getOutputLayer()));
            }
        }
        outputs = state.getLayerOutputs(getOutputLayer());
        double value = -Double.MAX_VALUE;
        int outputNumber = -1;
        for (int i = outputs.length - 1; i >= 0; i--) {
            if ((outputs[i] > value) && (!classificationMode || inputs[i] > 0)) {
                value = outputs[i];
                outputNumber = i;
            }
        }
        if (outputNumber >= 0) {
            Integer category = new Integer(outputNumber);
            ClassificationResult res = new SimpleClassificationResult(category.toString(), category, value);
            return Collections.singletonList(res);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
    
    /**
     * Initialize a learning process - the classifier should prepare for the learning allocating 
     * needed resources.
     * 
     * @param trainingProperties The set of training properties.
     *
     * @throws TrainingException if a learning process cannot be initialized.
     */
    public void initializeLearningProcess(Properties trainingProperties) throws TrainingException {
        NeuralNetworkProcessingState state = 
                NeuralNetworkProcessingState.registerNeuralNetwork(this);
        String tmp = trainingProperties.getProperty(PROPERTY_ETHA);
        state.setLearningRate(tmp == null ? DEFAULT_ETHA : Double.parseDouble(tmp));
        tmp = trainingProperties.getProperty(PROPERTY_ETHA_MULTIPLER);
        state.setLearningRateMultipler(tmp == null ? DEFAULT_ETHA_MULTIPLER : Double.parseDouble(tmp));
        tmp = trainingProperties.getProperty(PROPERTY_MOMENTUM_RATE);
        state.setMomentumRate(tmp == null ? 0 : Double.parseDouble(tmp));
        tmp = trainingProperties.getProperty(PROPERTY_MAX_JUMP_FACTOR);
        state.setMaxJumpFactor(tmp == null ? 1.75 : Double.parseDouble(tmp));
        tmp = trainingProperties.getProperty(PROPERTY_LEARNING_ALGORITHM);
        state.setAlgorithm(tmp == null ? 0 : Integer.parseInt(tmp));
        tmp = trainingProperties.getProperty(PROPERTY_MINIMUM_ERROR_VALUE);
        state.setMinimumErrorValue(tmp == null ? 0 : Double.parseDouble(tmp));
    }
    
    /**
     * A trainer calls this method when a learning process begins.
     *
     * @throws TrainingException if a learning process cannot be started.
     */
    public void onBeginLearning() throws TrainingException {
        NeuralNetworkProcessingState state = 
                NeuralNetworkProcessingState.getNeuralNetworkProcessingState(this);
        logger.info("Start neural network learn process with the following parameter:");
        logger.info("   learning rate : " + state.getLearningRate());
        logger.info("   learning rate multipler : " + state.getLearningRateMultipler());
        logger.info("   momentum rate : " + state.getMomentumRate());
        state.setLastRMSE(Double.MAX_VALUE);
    }
    
    
    /**
     * A trainer calls this method when this classifier should learn from the specified training example.
     * 
     * @param example The trainig example used by the classifier for learning.
     *
     * @throws TrainingException if an error occurred while learning using the given training example.
     */
    public void onLearn(TrainingExample example) throws TrainingException {
        NeuralNetworkProcessingState state = 
                NeuralNetworkProcessingState.getNeuralNetworkProcessingState(this);
        switch (state.getAlgorithm()) {
            case QUICKPROB :
                backpropagate(example, false);
                break;
            case BACKPROPAGATION_OFFLINE :
                backpropagate(example, false);
                break;
            case BACKPROPAGATION_ONLINE :
                backpropagate(example, true);
                break;
            default :
                throw new TrainingException("Unsupported learning algorithm : " + state.getAlgorithm());
        }
    }
    
    protected void backpropagate(TrainingExample example, boolean online) throws TrainingException {
        NeuralNetworkProcessingState state = 
                NeuralNetworkProcessingState.getNeuralNetworkProcessingState(this);
        state.nextTrainingExample();
        
        // perform classification
        try {
            Collection classificationResults = classify(example);
            if (example.isCorrect(classificationResults)) {
                setQuality(getQuality() + 1);
            }
        } catch (ClassificationException e) {
            throw new TrainingException("Cannot classify training example.", e);
        } catch (TrainingSetException te) {
            throw new TrainingException("Cannot verify training example.", te);
        }
        
        // calculate errors for output layer;
        int outNeuronIndex = ((Integer) example.getCategory()).intValue();
        NeuralNetworkLayer layer = getOutputLayer();
        double[] desiredOutput = state.getDesiredOutput();
        desiredOutput[outNeuronIndex] = layer.getMaxOutputValue();
        double rmse = layer.calculateErrorsForOutputLayer(state.getLayerOutputs(layer), desiredOutput, state.getMinimumErrorValue(), getInputs(), isClassificationMode());
        state.setRMSE(state.getRMSE() + rmse);

        // calculate errors and deltas for other layers
        for (int layerIndex = getNumberOfLayers() - 2; layerIndex >=0; layerIndex--) {
            NeuralNetworkLayer inputLayer = (NeuralNetworkLayer) getLayers().get(layerIndex);
            double[] inputErrors = inputLayer.getErrors();
            Arrays.fill(inputErrors, 0);
            for (final Iterator it = inputLayer.getOutputSynapses().iterator(); it.hasNext();) {
                NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) it.next();
                NeuralNetworkLayer outputLayer = synapse.getOutputLayer();
                double[] outputErrors = outputLayer.getErrors();
                synapse.backPropagate(inputErrors, outputErrors);
            }
            inputLayer.backpropagate(inputErrors, state.getLayerOutputs(inputLayer));
        }
        if (online) {
            // modify weights
            double learningRate = state.getLearningRate();
            double momentumRate = state.getMomentumRate();
            for (final Iterator it = getLayers().iterator(); it.hasNext();) {
                layer = (NeuralNetworkLayer) it.next();
                double[] errors = layer.getErrors();
                layer.modifyWeights(errors, learningRate, momentumRate);
                for (final Iterator sit = layer.getInputSynapses().iterator(); sit.hasNext();) {
                    NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) sit.next();
                    double[] synapseInputs = state.getLayerOutputs(synapse.getInputLayer());
                    synapse.modifyWeights(synapseInputs, errors, learningRate, momentumRate); 
                }
            }
        } else {
            for (final Iterator it = getLayers().iterator(); it.hasNext();) {
                layer = (NeuralNetworkLayer) it.next();
                layer.cumulateDeltas();
                double[] errors = layer.getErrors();
                for (final Iterator sit = layer.getInputSynapses().iterator(); sit.hasNext();) {
                    NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) sit.next();
                    double[] synapseInputs = state.getLayerOutputs(synapse.getInputLayer());
                    synapse.cumulateDeltas(synapseInputs, errors);
                }
            }
        }
    }
    
    protected void quickprop(TrainingExample example) throws TrainingException {
//        NeuralNetworkProcessingState state = 
//                NeuralNetworkProcessingState.getNeuralNetworkProcessingState(this);
//        state.nextTrainingExample();
//        
//        
//        // perform classification
//        try {
//            classify(example);
//        } catch (ClassificationException e) {
//            throw new TrainingException("Cannot classigy training example.", e);
//        }
//        
//        // calculate errors for output layer;
//        int outNeuronIndex = ((Integer) example.getCategory()).intValue();
//        NeuralNetworkLayer layer = getOutputLayer();
//        double[] desiredOutput = state.getDesiredOutput();
//        desiredOutput[outNeuronIndex] = layer.getMaxOutputValue();
//        double[] delta = state.getLayerErrors(layer);
//        state.setRMSE(state.getRMSE() + layer.calculateDeltaForOutputLayer(state.getLayerOutputs(layer), desiredOutput, delta, state.getMinimumErrorValue()));
//
//        // calculate errors and deltas for other layers
//        for (int layerIndex = getNumberOfLayers() - 2; layerIndex >=0; layerIndex--) {
//            layer = (NeuralNetworkLayer) getLayers().get(layerIndex);
//            double[] err = state.getLayerErrors(layer);
//            for (final Iterator it = layer.getOutputSynapses().iterator(); it.hasNext();) {
//                NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) it.next();
//                double[] errors2 = state.getLayerErrors(synapse.getOutputLayer());
//                synapse.backPropagate(err, errors2);
//            }
//            double[] out = state.getLayerOutputs(layer);
//            for (int i = err.length - 1; i >= 0; i--) {
//                err[i] *= layer.backPropagationFunction(out[i]);
//            }
//        }
//        
//        // modify weights
//        double maxJumpFactor = state.getMaxJumpFactor();
//        for (final Iterator it = getLayers().iterator(); it.hasNext();) {
//            layer = (NeuralNetworkLayer) it.next();
//            double[] err = state.getLayerErrors(layer);
//            layer.modifyWeightsQuickly(err, state.getLearningRate(), state.getMomentumRate(), maxJumpFactor);
//            for (final Iterator sit = layer.getInputSynapses().iterator(); sit.hasNext();) {
//                NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) sit.next();
//                double[] synapseInputs = state.getLayerOutputs(synapse.getInputLayer());
//                synapse.modifyWeightsQuickly(synapseInputs, err, state.getLearningRate(), state.getMomentumRate(), maxJumpFactor); 
//            }
//            
//        }
    }
    
    /**
     * A trainer calls this method when it starts a new iteration over the trainig set.
     * You can modify learning properties at the begining of the iteration.
     * 
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    public void onBeginIteration(int iterationNumber) throws TrainingException {
        NeuralNetworkProcessingState state = 
                NeuralNetworkProcessingState.getNeuralNetworkProcessingState(this);
        state.setEpochStartTime(System.currentTimeMillis());
        state.setRMSE(0);
        setQuality(0);
        Arrays.fill(state.getCategoryRMSE(), 0);
        state.setNumberOfTrainingExamplesInEpoch(0);
        logger.info("Start processing epoch " + iterationNumber + ". learning rate = " + state.getLearningRate());
        for (final Iterator it = getLayers().iterator(); it.hasNext();) {
            NeuralNetworkLayer layer = (NeuralNetworkLayer) it.next();
            layer.beforeEpoch();
            for (final Iterator it2 = layer.getOutputSynapses().iterator(); it2.hasNext();) {
                NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) it2.next();
                synapse.beforeEpoch();
            }
        }
    }

    /**
     * A trainer calls this method when it finishes an iteration over the trainig set.
     * You can modify learning properties at the end of the iteration.
     * 
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    public void onEndIteration(int iterationNumber) throws TrainingException {
        NeuralNetworkProcessingState state = 
                NeuralNetworkProcessingState.getNeuralNetworkProcessingState(this);
        
        double learningRate = state.getLearningRate();
        double momentumRate = state.getMomentumRate();
        int numberOfTrainingExamples = state.getNumberOfTrainingExamplesInEpoch();
        setQuality(100.0 * getQuality() / numberOfTrainingExamples);
        double maxJumpFactor = state.getMaxJumpFactor();
        switch (state.getAlgorithm()) {
            case QUICKPROB :
                for (final Iterator it = getLayers().iterator(); it.hasNext();) {
                    NeuralNetworkLayer layer = (NeuralNetworkLayer) it.next();
                    layer.modifyWeightsQuickly(learningRate, momentumRate, numberOfTrainingExamples, maxJumpFactor);
                    for (final Iterator sit = layer.getInputSynapses().iterator(); sit.hasNext();) {
                        NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) sit.next();
                        synapse.modifyWeightsQuickly(learningRate, momentumRate, numberOfTrainingExamples, maxJumpFactor); 
                    }
                }
                break;
            case BACKPROPAGATION_OFFLINE :
                for (final Iterator it = getLayers().iterator(); it.hasNext();) {
                    NeuralNetworkLayer layer = (NeuralNetworkLayer) it.next();
                    layer.modifyWightsWithCumulatedDeltas(learningRate, momentumRate, numberOfTrainingExamples);
                    for (final Iterator sit = layer.getInputSynapses().iterator(); sit.hasNext();) {
                        NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) sit.next();
                        synapse.modifyWeightsWithCumulatedDeltas(learningRate, momentumRate, numberOfTrainingExamples); 
                    }
                }
                break;
            case BACKPROPAGATION_ONLINE :
                break;
            default :
                throw new TrainingException("Unsupported learning algorithm : " + state.getAlgorithm());
        }
        
        
        double rmse = state.calculateRMSE();
        logger.info("RMSE after epoch " + iterationNumber + " : " + rmse);
        logger.info("Quality after epoch " + iterationNumber + " : " + getQuality());
        state.setLearningRate(state.getLearningRate() * state.getLearningRateMultipler());
        state.setMomentumRate(state.getMomentumRate() * state.getLearningRateMultipler());
//        if (1.01 * rmse > state.getLastRMSE()) {
//            state.setLearningRate(state.getLearningRate() * state.getLearningRateMultipler());
//        }
        state.setLastRMSE(rmse);
        setRMSE(rmse);
        for (final Iterator it = getLayers().iterator(); it.hasNext();) {
            NeuralNetworkLayer layer = (NeuralNetworkLayer) it.next();
            layer.afterEpoch();
            for (final Iterator it2 = layer.getOutputSynapses().iterator(); it2.hasNext();) {
                NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) it2.next();
                synapse.afterEpoch();
            }
        }
        
        long duration = System.currentTimeMillis() - state.getEpochStartTime();
        logger.info("Epoch time = " + duration + " ms.");
    }
    
    /**
     * A trainer calls this method when a learning process ends - the classifier should commit 
     * all changes made while the learning process.
     * 
     * @throws TrainingException if a learning process cannot be properly finished.
     */
    public void onEndLearningProcess() throws TrainingException {
        
    }
    
    /**
     * Finalizes a learning process - the classifier should dispose all resources 
     * allocated for the learning process.
     *
     * @throws TrainingException if a learning process cannot be properly finalized.
     */
    public void finalizeLearningProcess() throws TrainingException {
        NeuralNetworkProcessingState.unregisterNeuralNetwork(this);
    }
    
    /**
     * A trainer calls this method when a learning process is broken - the classifier
     * should discard all changes and dispose all resources allocated for the learning process.
     * 
     * @throws TrainingException if a learning process cannot be properly broken.
     */
    public void breakLearningProcess() throws TrainingException {
        
    }
    

    public double[] getInputs() {
        NeuralNetworkProcessingState state = 
                NeuralNetworkProcessingState.getNeuralNetworkProcessingState(this);
        return state.getLayerInputs(getInputLayer());
    }
    
    public double[] getOutputs() {
        NeuralNetworkProcessingState state = 
                NeuralNetworkProcessingState.getNeuralNetworkProcessingState(this);
        return state.getLayerOutputs(getOutputLayer());
    }

    
    /**
     * Initializes the classifier.
     *
     * @throws CLassificationException if the classifier cannot be initialized.
     */
    public void initialize() throws ClassificationException {
        NeuralNetworkProcessingState.registerNeuralNetwork(this);
    }

    public boolean isClassificationMode() {
        return classificationMode;
    }

    public void setClassificationMode(boolean inputsClassification) {
        this.classificationMode = inputsClassification;
    }
    
}

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
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.WeakHashMap;
import org.neurpheus.machinelearning.neuralnet.NeuralNetwork;
import org.neurpheus.machinelearning.neuralnet.NeuralNetworkLayer;
import org.neurpheus.machinelearning.neuralnet.NeuralNetworkSynapse;

/**
 *
 * @author Jakub Strychowski
 */
public class NeuralNetworkProcessingState implements Serializable {

    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061227092045L;
    
    private WeakHashMap inputs;
    private WeakHashMap outputs;
    private WeakHashMap errors;
    private WeakHashMap lastWeightsOfSynapses;
    private WeakHashMap lastWeightsOfNeurons;
    private WeakHashMap lastErrors;
    private double RMSE;
    private double lastRMSE;
    private double[] categoryRMSE;
    private double[] desiredOutput;
    private int numberOfOutputs;
    private int numberOfInputs;
    private WeakReference neuralNetwork;
    private double learningRate;
    private double learningRateMultipler;
    private int epochNumber;
    private int numberOfTrainingExamplesInEpoch;
    private double momentumRate;
    private double maxJumpFactor;
    private int algorithm;
    private double minimumErrorValue;
    private long epochStartTime;
    
    /** Creates a new instance of NeuralNetworkProcessingState */
    public NeuralNetworkProcessingState() {
    }
    
    /** Creates a new instance of NeuralNetworkProcessingState */
    public NeuralNetworkProcessingState(NeuralNetwork ann) {
        inputs = new WeakHashMap();
        outputs = new WeakHashMap();
        errors = new WeakHashMap();
        lastWeightsOfSynapses = new WeakHashMap();
        lastWeightsOfNeurons = new WeakHashMap();
        neuralNetwork = new WeakReference(ann);
        for (final Iterator it = ann.getLayers().iterator(); it.hasNext();) {
            NeuralNetworkLayer layer = (NeuralNetworkLayer) it.next();
            int numberOfNeurons = layer.getNumberOfNeurons();
            inputs.put(layer, new double[numberOfNeurons]);
            outputs.put(layer, new double[numberOfNeurons]);
            errors.put(layer, new double[numberOfNeurons]);
        }
        numberOfInputs = ann.getInputLayer().getNumberOfNeurons();
        numberOfOutputs = ann.getOutputLayer().getNumberOfNeurons();
        categoryRMSE = new double[numberOfOutputs];
        desiredOutput = new double[numberOfOutputs];
        RMSE = 0;
    }
    
    public void reset(boolean inputsSet) {
        double[] annInputs = getLayerInputs(((NeuralNetwork)neuralNetwork.get()).getInputLayer());
        for (Iterator it = inputs.keySet().iterator(); it.hasNext();) {
            NeuralNetworkLayer layer = (NeuralNetworkLayer) it.next();
            double[] in = (double[]) inputs.get(layer);
            if (layer.hasBias()) {
                float[] bias = layer.getBias();
                if (inputsSet && annInputs == in) {
                    for (int i = bias.length - 1; i >= 0; i--) {
                        in[i] += bias[i];
                    }
                } else { 
                    for (int i = bias.length - 1; i >= 0; i--) {
                        in[i] = bias[i];
                    }
                    //System.arraycopy(layer.getBias(), 0, in, 0, in.length);
                }
            } else if (!inputsSet || in != annInputs) {
                Arrays.fill(in, 0);
            }
        }
        for (Iterator it = outputs.values().iterator(); it.hasNext();) {
            Arrays.fill((double[]) it.next(), 0);
        }
        for (Iterator it = errors.values().iterator(); it.hasNext();) {
            Arrays.fill((double[]) it.next(), 0);
        }
        Arrays.fill(desiredOutput, ((NeuralNetwork) neuralNetwork.get()).getOutputLayer().getMinOutputValue());
    }
    
    public WeakHashMap getLayersInputs() {
        return inputs;
    }
    
    public WeakHashMap getLayersOutputs() {
        return outputs;
    }
    
    public WeakHashMap getLayersErrors() {
        return outputs;
    }
    
    public double[] getLayerInputs(final NeuralNetworkLayer layer) {
        return (double[]) inputs.get(layer);
    }
    
    public double[] getLayerOutputs(final NeuralNetworkLayer layer) {
        return (double[]) outputs.get(layer);
    }
    
    public double[] getLayerErrors(final NeuralNetworkLayer layer) {
        return (double[]) errors.get(layer);
    }
    
    public Object getLastWeightsOfSynapses(final NeuralNetworkSynapse synapse) {
        return lastWeightsOfSynapses.get(synapse);
    }
    
    public void setLastWeightsOfSynapses(final NeuralNetworkSynapse synapse, Object weights) {
        lastWeightsOfSynapses.put(synapse, weights);
    }

    public Object getLastWeightsOfNeurons(final NeuralNetworkLayer layer) {
        return lastWeightsOfNeurons.get(layer);
    }
    
    public void setLastWeightsOfNeurons(final NeuralNetworkLayer layer, Object weights) {
        lastWeightsOfNeurons.put(layer, weights);
    }
    
    private static HashMap neuralNetworks = new HashMap();
    
    private static long getANNThreadId(NeuralNetwork ann) {
        return Thread.currentThread().hashCode() * ann.hashCode();
        //long threadId = Thread.currentThread().hashCode();
        //int annId = ann.hashCode();
        //String id = Long.toString(threadId) + "-" + Integer.toString(annId);
        //return id;
    }
    
    public static void unregisterNeuralNetwork(NeuralNetwork ann) {
        //String id = getANNThreadId(ann);
        getNeuralNetworks().remove(ann);
    }
    
    public static NeuralNetworkProcessingState registerNeuralNetwork(NeuralNetwork ann) {
        long id = getANNThreadId(ann);
        NeuralNetworkProcessingState result = new NeuralNetworkProcessingState(ann);
        getNeuralNetworks().put(new Long(id), result);
        return result;
    }
    
    public static void clearCache() {
        getNeuralNetworks().clear();
    }
    
    public static NeuralNetworkProcessingState getNeuralNetworkProcessingState(NeuralNetwork ann) {
        long id = getANNThreadId(ann);
        NeuralNetworkProcessingState result =  (NeuralNetworkProcessingState) getNeuralNetworks().get(new Long(id));
        return result;
    }

    public double getRMSE() {
        return RMSE;
    }

    public void setRMSE(double RMSE) {
        this.RMSE = RMSE;
    }

    public double[] getCategoryRMSE() {
        return categoryRMSE;
    }

    public void setCategoryRMSE(double[] categoryRMSE) {
        this.categoryRMSE = categoryRMSE;
    }

    public double[] getDesiredOutput() {
        return desiredOutput;
    }

    public void setDesiredOutput(double[] desiredOutput) {
        this.desiredOutput = desiredOutput;
    }

    public double getLastRMSE() {
        return lastRMSE;
    }

    public void setLastRMSE(double lastRMSE) {
        this.lastRMSE = lastRMSE;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double getLearningRateMultipler() {
        return learningRateMultipler;
    }

    public void setLearningRateMultipler(double learningRateMultipler) {
        this.learningRateMultipler = learningRateMultipler;
    }

    public int getEpochNumber() {
        return epochNumber;
    }

    public void setEpochNumber(int epochNumber) {
        this.epochNumber = epochNumber;
    }

    public int getNumberOfTrainingExamplesInEpoch() {
        return numberOfTrainingExamplesInEpoch;
    }

    public void setNumberOfTrainingExamplesInEpoch(int numberOfTrainingExamplesInEpoch) {
        this.numberOfTrainingExamplesInEpoch = numberOfTrainingExamplesInEpoch;
    }

    public static HashMap getNeuralNetworks() {
        return neuralNetworks;
    }

    public static void setNeuralNetworks(HashMap aNeuralNetworks) {
        neuralNetworks = aNeuralNetworks;
    }
    
    public void nextTrainingExample() {
        numberOfTrainingExamplesInEpoch++;
    }
    
    public double calculateRMSE() {
        for (int i = 0; i < categoryRMSE.length; i++) {
            categoryRMSE[i] = Math.sqrt(categoryRMSE[i] / numberOfTrainingExamplesInEpoch);
        }
        RMSE = Math.sqrt(RMSE / (numberOfTrainingExamplesInEpoch * desiredOutput.length));
        return RMSE;
        
    }

    public double getMomentumRate() {
        return momentumRate;
    }

    public void setMomentumRate(double momentumRate) {
        this.momentumRate = momentumRate;
    }

    public double getMaxJumpFactor() {
        return maxJumpFactor;
    }

    public void setMaxJumpFactor(double maxJumpFactor) {
        this.maxJumpFactor = maxJumpFactor;
    }

    public int getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    public double getMinimumErrorValue() {
        return minimumErrorValue;
    }

    public void setMinimumErrorValue(double minimumErrorValue) {
        this.minimumErrorValue = minimumErrorValue;
    }

    public long getEpochStartTime() {
        return epochStartTime;
    }

    public void setEpochStartTime(long epochStartTime) {
        this.epochStartTime = epochStartTime;
    }

    
    
}

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

package org.neurpheus.machinelearning.neuralnet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import org.neurpheus.machinelearning.classification.AbstractClassifier;
import org.neurpheus.machinelearning.classification.ClassificationException;
import org.neurpheus.machinelearning.classification.ClassificationResult;
import org.neurpheus.machinelearning.classification.Classifier;
import org.neurpheus.machinelearning.classification.SimpleClassificationResult;
import org.neurpheus.machinelearning.training.TrainingExample;
import org.neurpheus.machinelearning.training.TrainingException;
import org.neurpheus.machinelearning.training.TrainingSet;
import org.neurpheus.machinelearning.training.TrainingSetException;

/**
 * An Artificial Neural Network.
 *
 * @author  Jakub Strychowski
 */
//public class NeuralNetworkImplOld extends AbstractClassifier implements Serializable, Classifier {
public class NeuralNetworkImplOld {

    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061208183720L;
    
    /** The logger used by this class. */
    private static Logger logger = Logger.getLogger(NeuralNetworkImplOld.class.getName());
    
    public static final String PROPERTY_ETHA = "etha";
    public static final String PROPERTY_ETHA_MULTIPLER = "etha.multipler";
    public static final String PROPERTY_ALPHA = "alpha";
    
    public static final double DEFAULT_ETHA = 1;
    public static final double DEFAULT_ETHA_MULTIPLER = 0.3;
    public static final double DEFAULT_ALPHA = 0;
//    
//    
//    protected ArrayList layers;
//    protected int inputsCount;
//    private int outputsCount;
//    private final static int DOUBLE_NEURONS_NET = 1;
//    private transient double[] inputs;
//    private transient double[] outputs;
//    
//    private int trainingExamplesInEpoch;
//    private double lastRMSE;
//    
//    private double etha;
//    private double ethaMultipler;
//    private double alpha;
//    
//    
//    /** Creates a new instance of NeuronsNet */
//    public NeuralNetworkImplOld(int inputsCount) {
//        layers = new ArrayList();
//        this.inputsCount = inputsCount;
//        inputs = new double[inputsCount];
//    }
//    
//
//    public void makeTransmiter(){
//        for (Iterator it = getLayers().iterator();  it.hasNext(); ){
//            NeuralNetworkLayerImplOld l = (NeuralNetworkLayerImplOld)it.next();
//            l.makeTransmiter();
//        }
//    }
//    
//    public NeuralNetworkImplOld(NeuralNetworkImplOld src, int weightsCreationType) {
//        this.inputsCount = src.getInputsCount();
//        layers = new ArrayList();
//        int icount = this.inputsCount;
//        for (Iterator it = src.getLayers().iterator();  it.hasNext(); ){
//            int ncount = ((NeuralNetworkLayerImplOld)it.next()).getNeuronsCount();
//            layers.add(new NeuralNetworkLayerImplOld(icount, ncount , true));
//            icount = ncount;
//        }
//        if (weightsCreationType==2){
//            makeTransmiter();
//        } else {
//            random();
//        }
//    }
//        
//    
//    public NeuralNetworkLayerImplOld addLayer(int nCount){
//        int icount = inputsCount;
//        if (layers.size()>0){
//            NeuralNetworkLayerImplOld lastLayer = (NeuralNetworkLayerImplOld)layers.get(layers.size()-1);
//            icount = lastLayer.getNeuronsCount();
//        }
//        NeuralNetworkLayerImplOld layer = new NeuralNetworkLayerImplOld(icount, nCount, true);
//        layers.add(layer);
//        outputsCount = nCount;
//        outputs = new double[nCount];
//        return layer;
//    }
//    
//    public void normalizeResult(double[] res) {
//        NeuralNetworkLayerImplOld layer = (NeuralNetworkLayerImplOld) layers.get(layers.size() - 1);
//        double minValue = layer.minValue;
//        double maxValue = layer.maxValue;
//        for (int i = res.length - 1; i >= 0; i--) {
//            res[i] = (res[i] - minValue) / (maxValue - minValue);
//        }
//    }
//     
//    public double[] calculate(double[] input){
//        double[] res = input;
//        for (Iterator it = layers.iterator(); it.hasNext();){
//            NeuralNetworkLayerImplOld layer = (NeuralNetworkLayerImplOld)it.next();
//            res = layer.calculate(res);
//        }
//        return res;
//    }
//    
//    public int selectMax(double[] input){
//        outputs = calculate(input);
//        double maxValue = Double.NEGATIVE_INFINITY;
//        int maxPos = -1;
//        for (int i = outputs.length-1; i >= 0; i--){
//            //if (input[i]!=0 && outputs[i] >= maxValue){
//            if (outputs[i]>=maxValue){
//                maxValue = outputs[i];
//                maxPos = i;
//            }
//        }
//        return maxPos;
//    }
//    
//    public void random(){
//        for (Iterator it = layers.iterator(); it.hasNext();){
//            NeuralNetworkLayerImplOld layer = (NeuralNetworkLayerImplOld)it.next();
//            layer.random();
//        }
//    }
//    
//    /** Getter for property inputsCount.
//     * @return Value of property inputsCount.
//     *
//     */
//    public int getInputsCount() {
//        return inputsCount;
//    }
//    
//    /** Setter for property inputsCount.
//     * @param inputsCount New value of property inputsCount.
//     *
//     */
//    public void setInputsCount(int inputsCount) {
//        this.inputsCount = inputsCount;
//    }
//    
//    /** Getter for property layers.
//     * @return Value of property layers.
//     *
//     */
//    public java.util.ArrayList getLayers() {
//        return layers;
//    }
//    
//    /** Setter for property layers.
//     * @param layers New value of property layers.
//     *
//     */
//    public void setLayers(java.util.ArrayList layers) {
//        this.layers = layers;
//    }
//
//    public NeuralNetworkImplOld recombine(NeuralNetworkImplOld net2){
//        NeuralNetworkImplOld res = new NeuralNetworkImplOld(net2,0);
//        for (int i = 0; i<layers.size(); i++){
//            NeuralNetworkLayerImplOld layerA = (NeuralNetworkLayerImplOld)layers.get(i);
//            NeuralNetworkLayerImplOld layerB = (NeuralNetworkLayerImplOld)net2.getLayers().get(i);
//            ((NeuralNetworkLayerImplOld)res.getLayers().get(i)).recombine(layerA, layerB);
//        }
//        return res;
//    }
//
//    public void mutation(){
//        int pos = (int)Math.round(Math.random()*(layers.size()-1));
//        NeuralNetworkLayerImplOld layer = (NeuralNetworkLayerImplOld)layers.get(pos);
//        layer.mutation();
//    }
//
//    /** 
//     *  Sprowadza wszystkie warto¶ci wag sieci neuronowej do zakresu od -1 do +1.
//     */
//    public void normalize(){
//        for (Iterator it = layers.iterator(); it.hasNext();){
//            NeuralNetworkLayerImplOld layer = (NeuralNetworkLayerImplOld)it.next();
//            layer.normalize();
//        }
//    }
//
//    /** 
//     *  Przkształca sieć tak, aby wagi kodowane były na 1 bajcie
//     */
//    public void makeByteWeights(){
//        for (Iterator it = layers.iterator(); it.hasNext();){
//            NeuralNetworkLayerImplOld layer = (NeuralNetworkLayerImplOld)it.next();
//            layer.makeByteWeights();
//        }
//    }
//    
//    public long getAllocationSize(){
//        long res = 24;
//        for (Iterator it = layers.iterator(); it.hasNext();){
//            NeuralNetworkLayerImplOld layer = (NeuralNetworkLayerImplOld)it.next();
//            res += layer.getAllocationSize();
//        }
//        return res;
//    }
//    
//    public void train(TrainingExample tr, double eta, double alfa) throws TrainingSetException {
//        Arrays.fill(inputs, 0);
//        Arrays.fill(outputs, NeuralNetworkLayerImplOld.minValue * 0.9);
//        tr.activate(this);
//        for (int i = layers.size() - 1; i >= 0; i--) {
//            NeuralNetworkLayerImplOld layer = (NeuralNetworkLayerImplOld) layers.get(i);
//            layer.learn(inputs, outputs, eta, alfa);
//        }
//    }
//    
//    public double train(final TrainingSet trainingSet, final double eta, final double alpha)  throws TrainingSetException {
//        for (Iterator it = trainingSet.iterator(); it.hasNext();) {
//            TrainingExample tr = (TrainingExample) it.next();
//            train(tr, eta, alpha);
//        }
//        return testNeuralNetwork(trainingSet);
//    }
//    
//    public void train(final TrainingSet trainingSet, final int iterationsCount, 
//    final double etaForFirstIteration, final double etaMultipler, final double alpha) 
//    throws TrainingSetException {
//        double eta = etaForFirstIteration;
//        logger.info("Start neural network learn process with the following parameter:");
//        logger.info("   eta for firts iteration : " + etaForFirstIteration);
//        logger.info("   eta multipler : " + etaMultipler);
//        logger.info("   alpha : " + alpha);
//        inputs = new double[inputsCount];
//        outputs = new double[outputsCount];
//        double quality = testNeuralNetwork(trainingSet);
//        logger.info("Quality before learning: " + quality);
//        for (int iterationNumber = 1; iterationNumber <= iterationsCount; iterationNumber++) {
//            logger.info("Neurons network learn process. iteraton = " + iterationNumber + "; eta = " + eta);
//            quality = train(trainingSet, eta, alpha);
//            logger.info("Neural network quality after the iteration " + iterationNumber + " : " + quality);
//            eta *= etaMultipler;
//        }
//        
//    }
//
//    public boolean isCorrect(TrainingExample tr) throws TrainingSetException {
//        Arrays.fill(inputs, 0);
//        Arrays.fill(outputs, NeuralNetworkLayerImplOld.minValue * 0.9);
//        tr.activate(this);
//        int result = selectMax(inputs);
//        return tr.isCorrect(new Integer(result));
//    }
//    
//    public double testNeuralNetwork(TrainingSet trainingSet)  throws TrainingSetException {
//        int count = 0;
//        int correctCount = 0;
//        int testExamplesCount = Integer.MAX_VALUE;
//        for (Iterator it = trainingSet.iterator(); it.hasNext() && count < testExamplesCount; count++) {
//            TrainingExample tr = (TrainingExample) it.next();
//            if (isCorrect(tr)) {
//                correctCount++;
//            }
//        }
//        return 100.0*(double)correctCount/(double)count;
//    }
//    
//    public double[] getInputs() {
//        return this.inputs;
//    }
//    
//    public double[] getOutputs() {
//        return this.outputs;
//    }
//    
//    /**
//     * Classifies an object into one or several categories returned 
//     * as a list of {@link ClassificationResult} objects.
//     * 
//     * @param obj The object which should be classified.
//     *
//     * @return The list of result categories.
//     *
//     * @throws ClassificationException if any classification error occurred.
//     */
//    public List classify(final Object obj) throws ClassificationException {
//        if (obj instanceof TrainingExample) {
//            TrainingExample example = (TrainingExample) obj;
//            int outputNumber;
//            double value = 0;
//            synchronized (outputs) {
//                Arrays.fill(inputs, 0);
//                Arrays.fill(outputs, NeuralNetworkLayerImplOld.minValue * 0.9);
//                try {
//                    example.activate(this);
//                } catch (TrainingSetException e) {
//                    throw new ClassificationException(
//                            "Cannot activate neural network using the given training example.", e);
//                }
//                outputNumber = selectMax(inputs);
//                if (outputNumber >=0) {
//                    value = outputs[outputNumber];
//                }
//            }
//            if (outputNumber >= 0) {
//                Integer category = new Integer(outputNumber);
//                ClassificationResult res = new SimpleClassificationResult(category.toString(), category, value);
//                return Collections.singletonList(res);
//            } else {
//                return Collections.EMPTY_LIST;
//            }
//        } else if (obj instanceof double[]) {
//            double[] tab = (double[]) obj;
//            if (tab.length != inputs.length) {
//                throw new ClassificationException(
//                        "The number of inputs values should correspondence " 
//                        + "to the number of inputs of neural network.");
//            }
//            int outputNumber;
//            double value;
//            synchronized (outputs) {
//                if (tab != inputs) {
//                    System.arraycopy(tab, 0, inputs, 0, tab.length);
//                }
//                Arrays.fill(outputs, NeuralNetworkLayerImplOld.minValue * 0.9);
//                outputNumber = selectMax(inputs);
//                value = outputs[outputNumber];
//            }
//            Integer category = new Integer(outputNumber);
//            ClassificationResult res = new SimpleClassificationResult(category.toString(), category, value);
//            return Collections.singletonList(res);
//        } else {
//            throw new ClassificationException(
//                "The type of a given object is not supported. Object class :" + obj.getClass().getName());
//        }
//            
//        
//    }
//    
//    /**
//     * Initialize a learning process - the classifier should prepare for the learning allocating 
//     * needed resources.
//     * 
//     * @param trainingProperties The set of training properties.
//     *
//     * @throws TrainingException if a learning process cannot be initialized.
//     */
//    public void initializeLearningProcess(final Properties trainingProperties) throws TrainingException {
//        String tmp = trainingProperties.getProperty(PROPERTY_ETHA);
//        etha = tmp == null ? DEFAULT_ETHA : Double.parseDouble(tmp);
//        tmp = trainingProperties.getProperty(PROPERTY_ETHA_MULTIPLER);
//        ethaMultipler = tmp == null ? DEFAULT_ETHA_MULTIPLER : Double.parseDouble(tmp);
//        tmp = trainingProperties.getProperty(PROPERTY_ALPHA);
//        alpha = tmp == null ? DEFAULT_ALPHA : Double.parseDouble(tmp);
//        inputs = new double[inputsCount];
//        outputs = new double[outputsCount];
//    }
//    
//    /**
//     * A trainer calls this method when a learning process begins.
//     *
//     * @throws TrainingException if a learning process cannot be started.
//     */
//    public void onBeginLearning() throws TrainingException {
//        logger.info("Start neural network learn process with the following parameter:");
//        logger.info("   etha for firts iteration : " + etha);
//        logger.info("   etha multipler : " + ethaMultipler);
//        logger.info("   alpha : " + alpha);
//        lastRMSE = Double.MAX_VALUE;
//    }
//    
//    /**
//     * A trainer calls this method when this classifier should learn from the specified training example.
//     * 
//     * @param example The trainig example used by the classifier for learning.
//     *
//     * @throws TrainingException if an error occurred while learning using the given training example.
//     */
//    public void onLearn(final TrainingExample example) throws TrainingException {
//        ++trainingExamplesInEpoch;
//        Arrays.fill(inputs, 0);
//        Arrays.fill(outputs, NeuralNetworkLayerImplOld.minValue * 0.9);
//        try {
//            example.activate(this);
//        } catch (TrainingSetException e) {
//            throw new TrainingException("Cannot use the given training example to learn neural network.");
//        }
//        for (int i = layers.size() - 1; i >= 0; i--) {
//            NeuralNetworkLayerImplOld layer = (NeuralNetworkLayerImplOld) layers.get(i);
//            layer.learn(inputs, outputs, etha, alpha);
//        }
//    }
//    
//    /**
//     * A trainer calls this method when it starts a new iteration over the trainig set.
//     * You can modify learning properties at the begining of the iteration.
//     * 
//     * @param iterationNumber   The number of an iteration (starting from 1). 
//     *
//     * @throws TrainingException if an error occurred while learning.
//     */
//    public void onBeginIteration(final int iterationNumber) throws TrainingException {
//        trainingExamplesInEpoch = 0;
//        getOutputLayer().setRMSE(0);
//        logger.info("Start processing epoch " + iterationNumber + ". learning rate = " + etha);
//    }
//
//    /**
//     * A trainer calls this method when it finishes an iteration over the trainig set.
//     * You can modify learning properties at the end of the iteration.
//     * 
//     * @param iterationNumber   The number of an iteration (starting from 1). 
//     *
//     * @throws TrainingException if an error occurred while learning.
//     */
//    public void onEndIteration(final int iterationNumber) throws TrainingException {
//        double rmse = getOutputLayer().getRMSE();
////        rmse = Math.sqrt((rmse / trainingExamplesInEpoch) / outputs.length);
//        rmse = Math.sqrt(rmse / trainingExamplesInEpoch);
//        logger.info("RMSE after epoch " + iterationNumber + " : " + rmse);
//        
//        if (rmse > lastRMSE) {
//            etha *= ethaMultipler;
//        }
//        lastRMSE = rmse;
//        
//    }
//    
//    public NeuralNetworkLayerImplOld getOutputLayer() {
//        return (NeuralNetworkLayerImplOld) layers.get(layers.size() - 1);
//    }
//    
//    /**
//     * A trainer calls this method when a learning process ends - the classifier should commit 
//     * all changes made while the learning process.
//     * 
//     * @throws TrainingException if a learning process cannot be properly finished.
//     */
//    public void onEndLearningProcess() throws TrainingException {
//        
//    }
//    
//    /**
//     * Finalizes a learning process - the classifier should dispose all resources 
//     * allocated for the learning process.
//     *
//     * @throws TrainingException if a learning process cannot be properly finalized.
//     */
//    public void finalizeLearningProcess() throws TrainingException {
//        
//    }
//    
//    /**
//     * A trainer calls this method when a learning process is broken - the classifier
//     * should discard all changes and dispose all resources allocated for the learning process.
//     * 
//     * @throws TrainingException if a learning process cannot be properly broken.
//     */
//    public void breakLearningProcess() throws TrainingException {
//        
//    }
//
//    /**
//     * Reads this object data from the given input stream.
//     *
//     * @param in   The input stream where this IPB is stored.
//     *
//     * @throws IOException if any read error occurred.
//     * @throws ClassNotFoundException if this object cannot be instantied.
//     */
//    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
//        in.defaultReadObject();
//        inputs = new double[inputsCount];
//        outputs = new double[outputsCount];
//    }
    
}

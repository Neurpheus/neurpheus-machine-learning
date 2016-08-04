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

import java.util.Collection;

/**
 * Represents <b>a neural network layer</b>. 
 *
 * @author Jakub Strychowski
 */
public interface NeuralNetworkLayer {
    
    int getNumberOfNeurons();
    
    void setNumberOfNeurons(int newNumberOfNeurons);
    
    void setName(String newName);
    
    String getName();
    
    Collection getInputSynapses();
    
    void setInputSynapses(Collection newInputSynapses);
    
    void attachInputSynapse(NeuralNetworkSynapse synapse);
    
    void detachInputSynapse(NeuralNetworkSynapse synapse);
    
    Collection getOutputSynapses();
    
    void setOutputSynapses(Collection newOutputSynapses);
    
    void attachOutputSynapse(NeuralNetworkSynapse synapse);
    
    void detachOutputSynapse(NeuralNetworkSynapse synapse);
    
    float[] getBias();
    
    void setBias(float[] newBias);
    
    float getBiasValue(int neuronIndex);
    
    void setBiasValue(int neuronIndex, float biasValue);
    
    void addNoise(double amplitude);
    
    void propagate(double[] inputs, double[] outputs);
    
    void backpropagate(double[] errors, double[] outputs);
    
    double getMinOutputValue();
    
    double getMaxOutputValue();
    
    double calculateErrorsForOutputLayer(double[] output, double[] desiredOutput, double minimumErrorValue, double[] inputs, boolean classifyInputs);
    
    double activationFunction(double value);
    
    double backPropagationFunction(double value);
    
    void modifyWightsWithCumulatedDeltas(double learningRate, double momentumRate, int numberOfTrainingExamples);
    
    void modifyWeights(double[] deltas, double learningRate, double momentumRate);

    void modifyWeightsQuickly(double learningRate, double momentumRate, int numberOfTrainingExamples, double maxJumpFactor);
    
    
    NeuralNetworkSynapse connectWith(NeuralNetworkLayer targetLayer, Class synapseType) throws NeuralNetworkException;
    
    boolean hasBias();
    
    double[] getErrors();
    
    void setErrors(double[] errors);
    
    double[] getCumulatedDeltas();
    
    void setCumulatedDeltas(double[] newCumulatedDelta);
    
    void cumulateDeltas();
    
    void beforeEpoch();
    
    void afterEpoch();
    
}

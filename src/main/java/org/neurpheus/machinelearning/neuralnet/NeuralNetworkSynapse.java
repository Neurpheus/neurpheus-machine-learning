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

/**
 * Represents <b>a synapse</b> - a connection between neural network layers.
 *
 * @author Jakub Strychowski
 */
public interface NeuralNetworkSynapse {

    String getName();
    
    void setName(String newName);
    
    NeuralNetworkLayer getInputLayer();
    
    void setInputLayer(NeuralNetworkLayer newInputLayer);
    
    NeuralNetworkLayer getOutputLayer();
    
    void setOutputLayer(NeuralNetworkLayer newOutputLayer);
    
    void setLayers(NeuralNetworkLayer newInputLayer, NeuralNetworkLayer newOutputLayer) throws NeuralNetworkException;

    void propagate(double[] inputs, double[] outputs);
    
    void backPropagate(double[] errors, double[] errors2);
    
    void modifyWeights(double[] input, double[] deltas, double learningRate, double momentumRate);

    void modifyWeightsQuickly(final double learningRate, final double momentumRate, final int numberOfTrainingExamples, final double maxJumpFactor);
    
    void addNoise(double amplitude);
    
    Object getWeights();
    
    void cumulateDeltas(final double[] input, final double[] delta);
    
    void modifyWeightsWithCumulatedDeltas(double learningRate, double momentumRate, double numberOfTrainingExamples);
    
    void beforeEpoch();
    
    void afterEpoch();
    
}

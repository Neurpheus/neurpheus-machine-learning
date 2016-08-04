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
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * Implements features common for all implementations of the neural network synapse interface.
 *
 * @author Jakub Strychowski
 */
public abstract class AbstractNeuralNetworkSynapse implements NeuralNetworkSynapse, Serializable {
    
    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061227132720L;
    
    private String name;
    private WeakReference inputLayer;
    private WeakReference outputLayer;
    
    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public NeuralNetworkLayer getInputLayer() {
        return (NeuralNetworkLayer) inputLayer.get();
    }
    
    public void setInputLayer(NeuralNetworkLayer newInputLayer) {
        inputLayer = new WeakReference(newInputLayer);
    }
    
    public NeuralNetworkLayer getOutputLayer() {
        return (NeuralNetworkLayer) outputLayer.get();
    }
    
    public void setOutputLayer(NeuralNetworkLayer newOutputLayer) {
        outputLayer = new WeakReference(newOutputLayer);
    }
    
    public void setLayers(
            final NeuralNetworkLayer newInputLayer, final NeuralNetworkLayer newOutputLayer) throws NeuralNetworkException {
        inputLayer = new WeakReference(newInputLayer);
        outputLayer = new WeakReference(newOutputLayer);
        initialize();
    }
    
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        inputLayer = new WeakReference(in.readObject());
        outputLayer = new WeakReference(in.readObject());
    }
    
    private void writeObject(final ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeUTF(name == null ? "" : name);
        out.writeObject(getInputLayer());
        out.writeObject(getOutputLayer());
    }

    public abstract void initialize() throws NeuralNetworkException;
    
    public abstract void propagate(double[] inputs, double[] outputs);
    
    public abstract void backPropagate(double[] errors, double[] errors2);

    public abstract void modifyWeightsWithCumulatedDeltas(double learningRate, double momentumRate, double numberOfTrainingExamples);
    
    public abstract void modifyWeights(double[] input, double[] deltas, double learningRate, double momentumRate);
    
    public abstract void modifyWeightsQuickly(final double learningRate, final double momentumRate, final int numberOfTrainingExamples, final double maxJumpFactor);
    
    
    public abstract void addNoise(double amplitude);
    
    public abstract Object getWeights();
    
    
}

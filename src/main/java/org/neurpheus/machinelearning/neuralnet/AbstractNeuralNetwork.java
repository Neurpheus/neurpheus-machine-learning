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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.neurpheus.machinelearning.classification.AbstractClassifier;

/**
 * Implements features common for all implementations of the neural network interface.
 *
 * @author Jakub Strychowski
 */
public abstract class AbstractNeuralNetwork extends AbstractClassifier implements NeuralNetwork, Serializable {

    
    private List layers;
    
    
    public List getLayers() {
        return layers;
    }
    
    public int getNumberOfLayers() {
        return layers.size();
    }
    
    public void setLayers(List newLayers) {
        layers = newLayers;
    }
    
    public void addLayer(NeuralNetworkLayer layer) {
        if (layers.contains(layer)) {
            throw new IllegalArgumentException("The neural network already contains the given layer.");
        }
        layers.add(layer);
    }
    
    public void insertLayer(int index, NeuralNetworkLayer layer) {
        if (layers.contains(layer)) {
            throw new IllegalArgumentException("The neural network already contains the given layer.");
        }
        layers.add(index, layer);
    }
    
    public void removeLayer(NeuralNetworkLayer layer) {
        layers.remove(layer);
    }
    
    public NeuralNetworkLayer getInputLayer() {
        return (NeuralNetworkLayer) layers.get(0);
    }
    
    public NeuralNetworkLayer getOutputLayer() {
        return (NeuralNetworkLayer) layers.get(layers.size() - 1);
    }

    public void random(double amplitude) {
        for (final Iterator it = getLayers().iterator(); it.hasNext();) {
            NeuralNetworkLayer layer = (NeuralNetworkLayer) it.next();
            layer.addNoise(amplitude);
            for (final Iterator sit = layer.getInputSynapses().iterator(); sit.hasNext();) {
                NeuralNetworkSynapse synapse = (NeuralNetworkSynapse) sit.next();
                synapse.addNoise(amplitude);
            }
            
        }
    }
    
    public void random() {
        random(0.3);
    }
    
    public abstract double[] getInputs();
   
    public abstract double[] getOutputs();

    static final byte FORMAT_VERSION = 1;

    public void write(DataOutputStream out) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void read(DataInputStream in) throws IOException {
        throw new UnsupportedOperationException();
    }

    
}

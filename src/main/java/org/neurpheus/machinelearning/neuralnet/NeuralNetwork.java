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
import java.util.List;
import org.neurpheus.machinelearning.classification.Classifier;

/**
 * Represents <b>a neural network layer</b>.
 *
 * @author Jakub Strychowski
 */
public interface NeuralNetwork extends Classifier {
    
    List<NeuralNetworkLayer> getLayers();
    
    int getNumberOfLayers();
    
    void setLayers(List newLayers);
    
    void addLayer(NeuralNetworkLayer layer);
    
    void insertLayer(int index, NeuralNetworkLayer layer);
    
    void removeLayer(NeuralNetworkLayer layer);

    NeuralNetworkLayer getInputLayer();
    
    NeuralNetworkLayer getOutputLayer();
    
    void random();
    
    void random(double amplitude);
    
    double[] getInputs();
    
    double[] getOutputs();
    
    boolean isClassificationMode();

    void setClassificationMode(boolean inputsClassification);

    void write(DataOutputStream out) throws IOException;

    void read(DataInputStream in) throws IOException;
    
}

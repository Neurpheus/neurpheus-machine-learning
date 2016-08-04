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

import java.io.Serializable;

/**
 *
 * @author Jakub
 */
public class TanhLayer extends AbstractNeuralNetworkLayer implements Serializable {

    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061227092005L;
    
    /** Creates a new instance of SigmoidLayer */
    public TanhLayer() {
        super();
    }
    
    /** Creates a new instance of SigmoidLayer */
    public TanhLayer(final String synapseName, final int neuronsCount) {
        super(synapseName, neuronsCount);
    }
    
    public double activationFunction(double value) {
        //return -1 +  2 / (1 + Math.exp(-2 * value));
        final long tmp = (long) (-2 * 1512775 * value + (1072693248 - 60801));
        return -1.0 + 2.0 / (1.0 + Double.longBitsToDouble(tmp << 32));
    }
    
    public void propagate(double[] inputs, double[] outputs) {
        long tmp;
        for (int i = outputs.length - 1; i >= 0; i--) {
            tmp = (long) (-2 * 1512775 * inputs[i] + (1072693248 - 60801));
            outputs[i] = -1.0 + 2.0 / (1.0 + Double.longBitsToDouble(tmp << 32));
        }
    }
    
    
    public double backPropagationFunction(double value) {
        return (1 + value) * (1 - value);
    }
    
    public void backpropagate(double[] errors, double[] outputs) {
        for (int i = errors.length - 1; i >= 0; i--) {
            double v= outputs[i];
            errors[i] *= (1.0 + v) * (1.0 - v);
        }
    }
    
    public double calculateErrorsForOutputLayer(double[] output, double[] desiredOutput, double minError, double[] inputs, boolean classifyInputs) {
        double rmse = 0;
        if (output.length != desiredOutput.length) {
            throw new IllegalArgumentException("The length of a outputs vector should be equal to the" 
                    + "length of a desired output vector.");
        }
        double[] err = getErrors();
        for (int i = output.length - 1; i >= 0; i--) {
            double error = desiredOutput[i] - output[i];
            if (error < minError && error > -minError) {
                error = 0;
            }
            rmse += error * error;
            double v = output[i];
            err[i] = error * (1.0 + v) * (1.0 - v);
        }
        return rmse;
    }
    
    public double getMinOutputValue() {
        return - 0.9;
    }
    
    public double getMaxOutputValue() {
        return 0.9;
    }
    
    public boolean hasBias() {
        return true;
    }
}

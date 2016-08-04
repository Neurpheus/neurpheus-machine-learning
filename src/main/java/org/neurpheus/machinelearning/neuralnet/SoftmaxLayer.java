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
public class SoftmaxLayer extends AbstractNeuralNetworkLayer implements Serializable {
    
    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061227091658L;
    
    private double alpha = 1;

    public SoftmaxLayer() {
        super();
    }
    
    /** Creates a new instance of SigmoidLayer */
    public SoftmaxLayer(final String synapseName, final int neuronsCount) {
        super(synapseName, neuronsCount);
    }
    
    
    public double activationFunction(double value) {
        return alpha * value;
    }
    
    public void propagate(double[] inputs, double[] outputs) {
        double sum = 0;
        for (int i = outputs.length - 1; i >= 0; i--) {
            final long tmp = (long) (1512775 * inputs[i] + (1072693248 - 60801));
            double v = Double.longBitsToDouble(tmp << 32);
            if (Double.isNaN(v) || Double.isInfinite(v)) {
                v = getMaxOutputValue();
            }
            sum += v;
            outputs[i] = v;
        }
        if (sum > 0) {
            for (int i = outputs.length - 1; i >= 0; i--) {
                outputs[i] = outputs[i] / sum;
            }
        }
    }
    
    public double backPropagationFunction(double value) {
        return alpha;
        
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
            err[i] = error * alpha;
        }
        return rmse;
    }
    

    public void backpropagate(double[] errors, double[] outputs) {
        for (int i = errors.length - 1; i >= 0; i--) {
            errors[i] *= alpha;
        }
    }
    
    public double getMinOutputValue() {
        return 0.0;
    }
    
    public double getMaxOutputValue() {
        return 1.0;
    }
    
    public boolean hasBias() {
        return false;
    }
    
    
}

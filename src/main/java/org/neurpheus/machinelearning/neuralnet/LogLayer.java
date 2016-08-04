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
public class LogLayer extends AbstractNeuralNetworkLayer implements Serializable {

    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061227091756L;

    public LogLayer() {
        super();
    }
    
    /** Creates a new instance of SigmoidLayer */
    public LogLayer(final String synapseName, final int neuronsCount) {
        super(synapseName, neuronsCount);
    }
    
    
    public double activationFunction(double value) {
        if (value >= 0) {
            //return Math.log(1.0 + value);
            return fastlog(1.0 + value);
        } else {
            //return -Math.log(1.0 - value);
            return -fastlog(1.0 - value);
        }
    }
    
    public void propagate(double[] inputs, double[] outputs) {
        long tmp;
        for (int i = outputs.length - 1; i >= 0; i--) {
            double value = inputs[i];
            if (value >= 0) {
                outputs[i] = 6 * (value) / (value + 2 + 4 * (Math.sqrt(1.0 + value)));
            } else {
                outputs[i] = - 6 * (- value) / (2 - value + 4 * (Math.sqrt(1.0 - value)));
            }
        }
    }
    
    public double backPropagationFunction(double value) {
        if (value >= 0) {
            return 1.0 / (1.0 + value);
        } else {
            return 1.0 / (1.0 - value);
        }
    }
    
    public void backpropagate(double[] errors, double[] outputs) {
        for (int i = errors.length - 1; i >= 0; i--) {
            double v= outputs[i];
            if (v >= 0) {
                errors[i] *= 1.0 / (1.0 + v);
            } else {
                errors[i] *= 1.0 / (1.0 - v);
            }
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
            if (v >= 0) {
                err[i] = error / (1.0 + v);
            } else {
                err[i] = error / (1.0 - v);
            }
        }
        return rmse;
    }
    
    
    public double getMinOutputValue() {
        return -0.9;
    }
    
    public double getMaxOutputValue() {
        return 0.9;
    }
    
    public boolean hasBias() {
        return true;
    }
    
    public static double fastlog(double value) {
        return 6 * (value - 1) / (value + 1 + 4 * (Math.sqrt(value)));
    }
    
    public static void main(String[] args) {
        double value = 5;
        double result = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
//            result += Math.log((double) i);
//            result = fastlog((double) i++);
//            result = fastlog((double) i++);
//            result = fastlog((double) i++);
//            result = fastlog((double) i++);
//            result = fastlog((double) i++);
//            result = fastlog((double) i++);
            result += fastlog((double) i);
        }
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Time : " + duration);
        System.out.println("Result: " + result);
    }
    
}

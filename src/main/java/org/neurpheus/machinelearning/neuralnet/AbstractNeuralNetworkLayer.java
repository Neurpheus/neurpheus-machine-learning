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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Implements features common for all implementations of the neural network layer interface.
 *
 * @author Jakub Strychowski
 */
public abstract class AbstractNeuralNetworkLayer implements NeuralNetworkLayer, Serializable {
    
    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061227132640L;
    
    private int numberOfNeurons;
    private String name;
    private Collection inputSynapses;
    private Collection outputSynapses;
    private float[] bias;
    transient private float[] previousBias;
    transient private double[] previousSlopes;
    transient private double[] errors;
    transient private double[] cumulatedDeltas;
    
    public AbstractNeuralNetworkLayer() {
        errors = null;
        cumulatedDeltas = null;
    }
    
    public AbstractNeuralNetworkLayer(final String synapseName, final int neuronsCount) {
        name = synapseName;
        inputSynapses = new HashSet();
        outputSynapses = new HashSet();
        setNumberOfNeurons(neuronsCount);
        errors = null;
        cumulatedDeltas = null;
    }
    
    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }
    
    public void setNumberOfNeurons(int newNumberOfNeurons) {
        numberOfNeurons = newNumberOfNeurons;
        float[] newBias = new float[numberOfNeurons];
        previousBias = null;
        previousSlopes = null;
        Arrays.fill(newBias, 0);
        if (bias != null) {
            System.arraycopy(bias, 0, newBias, 0, Math.min(bias.length, newBias.length));
        }
        bias = newBias;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public String getName() {
        return name;
    }
    
    public Collection getInputSynapses() {
        return inputSynapses;
    }
    
    public void setInputSynapses(Collection newInputSynapses) {
        inputSynapses = newInputSynapses;
    }
    
    public void attachInputSynapse(NeuralNetworkSynapse synapse) {
        if (!inputSynapses.contains(synapse)) {
            inputSynapses.add(synapse);
        }
    }
    
    public void detachInputSynapse(NeuralNetworkSynapse synapse) {
        inputSynapses.remove(synapse);
    }
    
    public Collection getOutputSynapses() {
        return outputSynapses;
    }
    
    public void setOutputSynapses(Collection newOutputSynapses) {
        if (outputSynapses != null) {
            outputSynapses.clear();
        }
        outputSynapses = newOutputSynapses;
    }
    
    public void attachOutputSynapse(NeuralNetworkSynapse synapse) {
        if (!outputSynapses.contains(synapse)) {
            outputSynapses.add(synapse);
        }
    }
    
    public void detachOutputSynapse(NeuralNetworkSynapse synapse) {
        outputSynapses.remove(synapse);
    }
     
    
    public float[] getBias() {
        return bias;
    }
    
    public void setBias(float[] newBias) {
        if (newBias.length != numberOfNeurons) {
            throw new IllegalArgumentException(
                "The length of a bias array should be equal to the number of neurons in the layer.");
        }
        bias = newBias;
    }
    
    
    public float getBiasValue(int neuronIndex) {
        return bias[neuronIndex];
    }
    
    public void setBiasValue(int neuronIndex, float biasValue) {
        bias[neuronIndex] = biasValue;
    }
    
    public void addNoise(double amplitude) {
        for (int i = 0; i < bias.length; i++) {
            bias[i] += Math.random() * 2 * amplitude - amplitude;
        }
    }
    
    public void propagate(double[] inputs, double[] outputs) {
        for (int i = outputs.length - 1; i >= 0; i--) {
            outputs[i] = activationFunction(inputs[i]);
        }
    }

    public double calculateErrorForOutputLayer(double[] output, double[] desiredOutput, double[] deltas, double minError, double[] inputs, boolean classifyInputs) {
        double rmse = 0;
        if (output.length != desiredOutput.length) {
            throw new IllegalArgumentException("The length of a outputs vector should be equal to the" 
                    + "length of a desired output vector.");
        }
        for (int i = output.length - 1; i >= 0; i--) {
            double error = desiredOutput[i] - output[i];
            if (error < minError && error > -minError) {
                error = 0;
            }
            rmse += error * error;
            deltas[i] = error * backPropagationFunction(output[i]);
        }
        return rmse;
    }
    
    public double calculateErrorsForOutputLayer(double[] output, double[] desiredOutput, double minError) {
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
            err[i] = error * backPropagationFunction(output[i]);
        }
        return rmse;
    }
    
    public void modifyWightsWithCumulatedDeltas(double learningRate, double momentumRate, int numberOfTrainingExamples) {
        for (int i = cumulatedDeltas.length - 1; i >= 0; i--) {
            cumulatedDeltas[i] /= numberOfTrainingExamples;
        }
        modifyWeights(cumulatedDeltas, learningRate, momentumRate);
    }
    
    public void modifyWeights(double[] deltas, double learningRate, double momentumRate) {
        if (momentumRate > 0) {
            if (previousBias == null) {
                previousBias = (float[]) bias.clone();
            }
            for (int i = deltas.length - 1; i >= 0; i--) {
                double momentum = bias[i] - previousBias[i];
                previousBias[i] = bias[i];
                bias[i] += deltas[i] * learningRate + momentumRate * momentum;
                if (bias[i] > FullSynapse.MAXIMUM_WEIGHT_VALUE) {
                    bias[i] = FullSynapse.MAXIMUM_WEIGHT_VALUE;
                } else if (bias[i] < FullSynapse.MINIMUM_WEIGHT_VALUE) {
                    bias[i] = FullSynapse.MINIMUM_WEIGHT_VALUE;
                } else if (Float.isNaN(bias[i])) {
                    bias[i] = 0.001f;
                }
            }
        } else {
            for (int i = deltas.length - 1; i >= 0; i--) {
                bias[i] += deltas[i] * learningRate;
                if (bias[i] > FullSynapse.MAXIMUM_WEIGHT_VALUE) {
                    bias[i] = FullSynapse.MAXIMUM_WEIGHT_VALUE;
                } else if (bias[i] < FullSynapse.MINIMUM_WEIGHT_VALUE) {
                    bias[i] = FullSynapse.MINIMUM_WEIGHT_VALUE;
                } else if (Float.isNaN(bias[i])) {
                    bias[i] = 0.001f;
                }
            }
        }
    }
    
    public void modifyWeightsQuickly(double learningRate, double momentumRate, int numberOfTrainingExamples, double maxJumpFactor) {
        if (previousBias == null) {
            previousBias = (float[]) bias.clone();
            previousSlopes = new double[bias.length];
        }
        double[] deltas = cumulatedDeltas;
        double shrinkFactor = maxJumpFactor / (1 + maxJumpFactor);
        for (int i = deltas.length - 1; i >= 0; i--) {
            double previousDeltaWeight = bias[i] - previousBias[i];
            double previousSlope = previousSlopes[i];
            double slope = deltas[i] / numberOfTrainingExamples;
            double deltaWeight = 0;
            if (previousDeltaWeight > 0) {
                if (slope > 0) {
                    deltaWeight = slope * learningRate;
                }
                if (slope > shrinkFactor * previousSlope) {
                    deltaWeight += maxJumpFactor * previousDeltaWeight;
                } else {
                    deltaWeight += slope * previousDeltaWeight / (previousSlope - slope);
                }
            } else if (previousDeltaWeight < 0) {
                if (slope < 0) {
                    deltaWeight = slope * learningRate;
                }
                if (slope < shrinkFactor * previousSlope) {
                    deltaWeight += maxJumpFactor * previousDeltaWeight;
                } else {
                    deltaWeight += slope * previousDeltaWeight / (previousSlope - slope);
                }
            } else {
                deltaWeight = slope * learningRate + momentumRate * previousDeltaWeight;
            }
            previousBias[i] = bias[i];
            previousSlopes[i] = slope;
            bias[i] += deltaWeight;
            if (bias[i] > FullSynapse.MAXIMUM_WEIGHT_VALUE) {
                bias[i] = FullSynapse.MAXIMUM_WEIGHT_VALUE;
            } else if (bias[i] < FullSynapse.MINIMUM_WEIGHT_VALUE) {
                bias[i] = FullSynapse.MINIMUM_WEIGHT_VALUE;
            } else if (Float.isNaN(bias[i])) {
                bias[i] = 0.001f;
            }
        }
    }
    
    
    public NeuralNetworkSynapse connectWith(
            final NeuralNetworkLayer targetLayer, final Class synapseType) throws NeuralNetworkException {
        NeuralNetworkSynapse synapse;
        try {
            synapse = (NeuralNetworkSynapse) synapseType.newInstance();
        } catch (Exception e) {
            throw new NeuralNetworkException("Cannot create an instance of a synapses class.", e);
        }
        synapse.setLayers(this, targetLayer);
        this.attachOutputSynapse(synapse);
        targetLayer.attachInputSynapse(synapse);
        return synapse;
    }
    

    public void backpropagate(double[] err, double[] outputs) {
        for (int i = err.length - 1; i >= 0; i--) {
            err[i] *= backPropagationFunction(outputs[i]);
        }
    }
    
    public double[] getErrors() {
        if (errors == null) {
            errors = new double[getNumberOfNeurons()];
        }
        return errors;
    }
    
    public void setErrors(double[] newErrors) {
        errors = newErrors;
    }

    public double[] getCumulatedDeltas() {
        return cumulatedDeltas;
    }
    
    public void setCumulatedDeltas(double[] newCumulatedDelta) {
        cumulatedDeltas = newCumulatedDelta;
    }
    
    public void cumulateDeltas() {
        for (int i = errors.length - 1; i >= 0; i--) {
            cumulatedDeltas[i] += errors[i];
        }
    }
    

    public void beforeEpoch() {
        cumulatedDeltas = new double[bias.length];
    }
    
    public void afterEpoch() {
    }
    
    
    public abstract double activationFunction(double value);
    
    public abstract double backPropagationFunction(double value);
    
    public abstract double getMinOutputValue();
    
    public abstract double getMaxOutputValue();
    
}

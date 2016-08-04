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
public class DirectSynapse extends AbstractNeuralNetworkSynapse implements Serializable {
    
    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061227091547L;
    
    private float[] weights;
    transient private float[] previousWeights;
    transient private double[] previousSlopes;
    transient private double[] cumulatedDeltas;
    
    /** Creates a new instance of DirectSynapse */
    public DirectSynapse() {
    }


    public void initialize() throws NeuralNetworkException {
        if (getInputLayer().getNumberOfNeurons() != getOutputLayer().getNumberOfNeurons()) {
            throw new NeuralNetworkException("The number neurons in the input layer"
                    + " should be equal to the number of neurons in the output layer.");
        }
        weights = new float[getInputLayer().getNumberOfNeurons()];
        previousWeights = null;
        previousSlopes = null;
        cumulatedDeltas = null;
    }
    
    
    public void propagate(double[] inputs, double[] outputs) {
        for (int i = outputs.length - 1; i >= 0; i--) {
            outputs[i] += inputs[i] * weights[i];
        }
    }
    
    public void backPropagate(final double[] errors, final double[] errors2) {
        for (int i = errors2.length - 1; i >= 0; i--) {
            errors[i] += errors2[i] * weights[i];
        }
    }
    
    public void cumulateDeltas(final double[] input, final double[] delta) {
        for (int i = cumulatedDeltas.length - 1; i >= 0; i--) {
            cumulatedDeltas[i] += input[i] * delta[i];
        }
        
    }
    
    public void modifyWeightsWithCumulatedDeltas(double learningRate, double momentumRate, double numberOfTrainingExamples) {
        for (int i = cumulatedDeltas.length - 1; i >= 0; i--) {
            cumulatedDeltas[i] /= numberOfTrainingExamples;
        }
        modifyWeights(cumulatedDeltas, learningRate, momentumRate);
    }

    private void modifyWeights(final double[] deltas, final double learningRate, final double momentumRate) {
        if (momentumRate > 0) {
            if (previousWeights == null) {
                previousWeights = (float[]) weights.clone();
            }
            for (int i = weights.length - 1; i >= 0; i--) {
                double momentum = weights[i] - previousWeights[i];
                previousWeights[i] = weights[i];
                weights[i] += deltas[i] * learningRate + momentumRate * momentum;
                if (weights[i] > FullSynapse.MAXIMUM_WEIGHT_VALUE) {
                    weights[i] = FullSynapse.MAXIMUM_WEIGHT_VALUE;
                } else if (weights[i] < FullSynapse.MINIMUM_WEIGHT_VALUE) {
                    weights[i] = FullSynapse.MINIMUM_WEIGHT_VALUE;
                }
            }
        } else {
            for (int i = weights.length - 1; i >= 0; i--) {
                weights[i] += deltas[i] * learningRate;
                if (weights[i] > FullSynapse.MAXIMUM_WEIGHT_VALUE) {
                    weights[i] = FullSynapse.MAXIMUM_WEIGHT_VALUE;
                } else if (weights[i] < FullSynapse.MINIMUM_WEIGHT_VALUE) {
                    weights[i] = FullSynapse.MINIMUM_WEIGHT_VALUE;
                }
            }
        }
    }
    
    public void modifyWeights(final double[] input, final double[] delta, final double learningRate, final double momentumRate) {
        for (int i = weights.length - 1; i >= 0; i--) {
            cumulatedDeltas[i] = input[i] * delta[i];
        }
        modifyWeights(cumulatedDeltas, learningRate, momentumRate);
    }
    
    public void modifyWeightsQuickly(final double learningRate, final double momentumRate, final int numberOfTrainingExamples, final double maxJumpFactor) {
        if (previousWeights == null) {
            previousWeights = (float[]) weights.clone();;
            previousSlopes = new double[weights.length];
        }
        double shrinkFactor = maxJumpFactor / (1 + maxJumpFactor);
        double[] delta = cumulatedDeltas;
        for (int i = weights.length - 1; i >= 0; i--) {
            double previousDeltaWeight = weights[i] - previousWeights[i];
            double previousSlope = previousSlopes[i];
            double slope = delta[i] / numberOfTrainingExamples;
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
            previousWeights[i] = weights[i];
            previousSlopes[i] = slope;
            weights[i] += deltaWeight;
            if (weights[i] > FullSynapse.MAXIMUM_WEIGHT_VALUE) {
                weights[i] = FullSynapse.MAXIMUM_WEIGHT_VALUE;
            } else if (weights[i] < FullSynapse.MINIMUM_WEIGHT_VALUE) {
                weights[i] = FullSynapse.MINIMUM_WEIGHT_VALUE;
            }
        }
    }
    

    public Object getWeights() {
        return weights;
    }

    public void setWeights(float[] weights) {
        this.weights = weights;
    }
    
    public void addNoise(double amplitude) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += Math.random() * 2 * amplitude - amplitude;
        }
    }
    
    public void beforeEpoch() {
        cumulatedDeltas = new double[weights.length];
    }
    
    public void afterEpoch() {
        
    }
}

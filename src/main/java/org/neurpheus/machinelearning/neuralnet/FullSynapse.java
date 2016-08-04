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
public class FullSynapse extends AbstractNeuralNetworkSynapse implements Serializable {

    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061227091611L;
    
    static final float MAXIMUM_WEIGHT_VALUE = 1000.0f;
    static final float MINIMUM_WEIGHT_VALUE = -1000.0f;
    
    private float[][] weights;
    transient private float[][] previousWeights;
    transient private double[][] previousSlopes;
    transient private double[][] cumulatedDeltas;
    private int numberOfInputs;
    private int numberOfOutputs;
    
    /** Creates a new instance of DirectSynapse */
    public FullSynapse() {
    }
    
    public void initialize() throws NeuralNetworkException {
        numberOfInputs = getInputLayer().getNumberOfNeurons();
        numberOfOutputs = getOutputLayer().getNumberOfNeurons();
        weights = new float[numberOfInputs][];
        previousWeights = null;
        previousSlopes = null;
        cumulatedDeltas = null;
        for (int i = 0; i < numberOfInputs; i++) {
            weights[i] = new float[numberOfOutputs];
        }
    }
    
    public void propagate(double[] inputs, double[] outputs) {
        for (int j = inputs.length - 1; j >= 0; j--) {
            double in = inputs[j];
            if (in != 0) {
                float[] w = weights[j];
                for (int i = outputs.length - 1; i >= 0; i--) {
                    outputs[i] += in * w[i];
                }
            }
        }
    }
    
    public void backPropagate(final double[] errors, final double[] errors2) {
        for (int j = errors.length - 1; j >= 0; j--) {
            float[] w = weights[j];
            for (int i = errors2.length - 1; i >= 0; i--) {
                errors[j] += errors2[i] * w[i];
            }
        }
    }
    
    public void cumulateDeltas(final double[] input, final double[] delta) {
        for (int j = input.length - 1; j >= 0; j--) {
            double[] w = cumulatedDeltas[j];
            double in = input[j];
            if (in != 0) {
                for (int i = delta.length - 1; i >= 0; i--) {
                    w[i] += in * delta[i];
                }
            }
        }
    }
    
    public void modifyWeightsWithCumulatedDeltas(double learningRate, double momentumRate, double numberOfTrainingExamples) {
        if (momentumRate > 0) {
            if (previousWeights == null) {
                previousWeights = new float[numberOfInputs][];
                for (int i = 0; i < numberOfInputs; i++) {
                    previousWeights[i] = (float[]) weights[i].clone();
                }
            }
            for (int j = cumulatedDeltas.length - 1; j >= 0; j--) {
                float[] w = weights[j];
                float[] pw = previousWeights[j];
                double[] deltas = cumulatedDeltas[j];
                for (int i = deltas.length - 1; i >= 0; i--) {
                    double momentum = w[i] - pw[i];
                    pw[i] = w[i];
                    w[i] += deltas[i] * learningRate / numberOfTrainingExamples + momentumRate * momentum;
                    if (w[i] > MAXIMUM_WEIGHT_VALUE) {
                        w[i] = MAXIMUM_WEIGHT_VALUE;
                    } else if (w[i] < MINIMUM_WEIGHT_VALUE) {
                        w[i] = MINIMUM_WEIGHT_VALUE;
                    }
                }
            }
        } else {
            for (int j = cumulatedDeltas.length - 1; j >= 0; j--) {
                float[] w = weights[j];
                double[] deltas = cumulatedDeltas[j];
                for (int i = deltas.length - 1; i >= 0; i--) {
                    w[i] += deltas[i] * learningRate / numberOfTrainingExamples ;
                    if (w[i] > MAXIMUM_WEIGHT_VALUE) {
                        w[i] = MAXIMUM_WEIGHT_VALUE;
                    } else if (w[i] < MINIMUM_WEIGHT_VALUE) {
                        w[i] = MINIMUM_WEIGHT_VALUE;
                    }
                }
            }
        }
    }
    
    public void modifyWeights(final double[] input, final double[] delta, final double learningRate, final double momentumRate) {
        if (momentumRate > 0) {
            if (previousWeights == null) {
                previousWeights = new float[numberOfInputs][];
                for (int i = 0; i < numberOfInputs; i++) {
                    previousWeights[i] = (float[]) weights[i].clone();
                }
            }
            for (int j = input.length - 1; j >= 0; j--) {
                float[] w = weights[j];
                float[] pw = previousWeights[j];
                double in = input[j];
                if (in != 0) {
                    for (int i = delta.length - 1; i >= 0; i--) {
                        double momentum = w[i] - pw[i];
                        pw[i] = w[i];
                        w[i] += in * delta[i] * learningRate + momentumRate * momentum;
                        if (w[i] > MAXIMUM_WEIGHT_VALUE) {
                            w[i] = MAXIMUM_WEIGHT_VALUE;
                        } else if (w[i] < MINIMUM_WEIGHT_VALUE) {
                            w[i] = MINIMUM_WEIGHT_VALUE;
                        } else  if (Float.isNaN(w[i])) {
                            w[i] = 0.001f;
                        }
                    }
                }
            }
        } else {
            for (int j = input.length - 1; j >= 0; j--) {
                float[] w = weights[j];
                double in = input[j];
                if (in != 0) {
                    for (int i = delta.length - 1; i >= 0; i--) {
                        w[i] += in * delta[i] * learningRate;
                        if (w[i] > MAXIMUM_WEIGHT_VALUE) {
                            w[i] = MAXIMUM_WEIGHT_VALUE;
                        } else if (w[i] < MINIMUM_WEIGHT_VALUE) {
                            w[i] = MINIMUM_WEIGHT_VALUE;
                        } else  if (Float.isNaN(w[i])) {
                            w[i] = 0.001f;
                        }
                    }
                }
            }
        }
    }
    
    public void modifyWeightsQuickly(final double learningRate, final double momentumRate, final int numberOfTrainingExamples, final double maxJumpFactor) {
        if (previousWeights == null) {
            previousWeights = new float[numberOfInputs][];
            previousSlopes = new double[numberOfInputs][];
            for (int i = 0; i < numberOfInputs; i++) {
                previousWeights[i] = (float[]) weights[i].clone();
                previousSlopes[i] = new double[weights[i].length];
            }
        }
        double shrinkFactor = maxJumpFactor / (1 + maxJumpFactor);
        for (int j = cumulatedDeltas.length - 1; j >= 0; j--) {
            float[] weightsForInputJ = weights[j];
            float[] previousWeightsForInputJ = previousWeights[j];
            double[] previousSlopeForInputJ = previousSlopes[j];
            double[] delta = cumulatedDeltas[j];
            for (int i = delta.length - 1; i >= 0; i--) {
                double previousDeltaWeight = weightsForInputJ[i] - previousWeightsForInputJ[i];
                double previousSlope = previousSlopeForInputJ[i];
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
                    deltaWeight = slope * learningRate;// + momentumRate * previousDeltaWeight;
                }
                previousWeightsForInputJ[i] = weightsForInputJ[i];
                previousSlopeForInputJ[i] = slope;
                weightsForInputJ[i] += deltaWeight;
                if (weightsForInputJ[i] > MAXIMUM_WEIGHT_VALUE) {
                    weightsForInputJ[i] = MAXIMUM_WEIGHT_VALUE;
                } else if (weightsForInputJ[i] < MINIMUM_WEIGHT_VALUE) {
                    weightsForInputJ[i] = MINIMUM_WEIGHT_VALUE;
                }
            }
        }
    }

    public Object getWeights() {
        return weights;
    }
    
    public Object getClonedWeights() {
        float[][] result = new float[weights.length][];
        for (int i = weights.length - 1; i >=0; i--) {
            result[i] = (float[]) weights[i].clone();
        }
        return result;
    }

    public void setWeights(float[][] weights) {
        this.weights = weights;
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    public int getNumberOfOutputs() {
        return numberOfOutputs;
    }

    public void setNumberOfOutputs(int numberOfOutputs) {
        this.numberOfOutputs = numberOfOutputs;
    }
    
    public void addNoise(double amplitude) {
        cumulatedDeltas = new double[weights.length][];
        for (int i = 0; i < weights.length; i++) {
            float[] w = weights[i];
            cumulatedDeltas[i] = new double[w.length];
            for (int j = 0; j < w.length; j++) {
                w[j] += Math.random() * 2 * amplitude - amplitude;
            }
        }
    }
    
    public void beforeEpoch() {
        cumulatedDeltas = new double[weights.length][];
        for (int i = 0; i < weights.length; i++) {
            float[] w = weights[i];
            cumulatedDeltas[i] = new double[w.length];
        }
    }
    
    public void afterEpoch() {
        
    }
    
    
}

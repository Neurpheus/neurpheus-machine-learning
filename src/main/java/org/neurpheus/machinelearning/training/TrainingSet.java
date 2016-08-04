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

package org.neurpheus.machinelearning.training;

import java.util.Iterator;

/**
 * Represents <b>a set of training examples</b> used by a classifier learning process.
 *
 * @author Jakub Strychowski
 */
public interface TrainingSet {
    
    /**
     * Adds a training example to this training set.
     *
     * @param example The example which should be added.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    void addTrainingExample(TrainingExample example) throws TrainingSetException;
    
    /**
     * Randomizes an order of all training examples in this set.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    void mix() throws TrainingSetException;
    
    /**
     * Ensures that all resources allocated by this training set will be realesed.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    void close() throws TrainingSetException;
    
    /**
     * Returns an iterator over training examples.
     *
     * @return An iterator over all training examples stored in this training set.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    Iterator iterator() throws TrainingSetException;
    
    /**
     * Removes all training elements from this set.
     *
     * @throws TrainingSetException if training set cannot be clear correctly.
     */
    void clear() throws TrainingSetException;
    
    
    
}

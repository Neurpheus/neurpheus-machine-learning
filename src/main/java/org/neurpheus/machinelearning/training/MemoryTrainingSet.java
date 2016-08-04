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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A set of <b>training examples persisted in a memory</b>.
 *
 * @author Jakub Strychowski
 */
public class MemoryTrainingSet implements TrainingSet, Serializable {
    
    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061101180124L;
    
    /** Holds all training examples. */
    private ArrayList examples;
    
    /** Creates a new instance of TrainingSet. */
    public MemoryTrainingSet() {
        examples = new ArrayList();
    }
    
    /**
     * Adds a training example to this training set.
     *
     * @param example The example which should be added.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    public void addTrainingExample(final TrainingExample example) throws TrainingSetException {
        examples.add(example);
    }
    
    /**
     * Randomizes an order of all training examples in this set.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    public void mix() throws TrainingSetException {
        int maxIndex = examples.size() - 1;
        for (int i = maxIndex << 2; i >= 0; i--) {
            int posA = (int) Math.round(maxIndex * Math.random());
            int posB = (int) Math.round(maxIndex * Math.random());
            Object obj = examples.get(posA);
            examples.set(posA, examples.get(posB));
            examples.set(posB, obj);
        }
    }
    
    /**
     * Ensures that all resources allocated by this training set will be realesed.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    public void close() throws TrainingSetException {
        examples.clear();
    }
    
    /**
     * Returns an iterator over training examples.
     *
     * @return An iterator over all training examples stored in this training set.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    public Iterator iterator() throws TrainingSetException {
        return examples.iterator();
    }
    
    /**
     * Removes all training elements from this set.
     *
     * @throws TrainingSetException if training set cannot be clear correctly.
     */
    public void clear() throws TrainingSetException {
        examples.clear();
    }
    
    
}

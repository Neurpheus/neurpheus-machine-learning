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

import org.neurpheus.machinelearning.classification.Classifier;


/**
 * Represents <b>a single training example</b>.
 *
 * @author Jakub Strychowski
 */
public interface TrainingExample {
    
    /**
     * Activates the given classifier with input arguments represented by this training example.
     *
     * @param classifier    The classifier which perform classification using input arguments represented
     *                      by this training example.
     *
     * @throws TrainingSetException if this training example does not support the given classifier.
     */
    void activate(Classifier classifier) throws TrainingSetException;
    
    /**
     * Checks if the given classification result is a correct result for this training example.
     *
     * @param result    The result of a classification.
     *
     * @return <code>true</code> if the given classification result is correct.
     *
     * @throws TrainingSetException if this training example does not support the type of the given result.
     */
    boolean isCorrect(Object result) throws TrainingSetException;
    
    /**
     * Returns the data stored in this training example in the form of array of bytes.
     * 
     * @return serialized training example data.
     */
    byte[] getData();
    
    /**
     * Creates an instance of training example from the given data.
     *
     * @param data A set of data of an training example.
     *
     * @return created training example
     *
     * @throws TrainingSetException if training example cannot be created.
     */
    TrainingExample createFromData(final byte[] data) throws TrainingSetException;
    
    
    /**
     * Returns a tuple describing an object represented by this training example.
     *
     * The tuple is an array of values of attributes which correspondence to indexes in the array.
     *
     * @return the array of values of object's attributes.
     */
    Object[] getTuple();
    
    /**
     * Returns an object representing a category of this training example.
     *
     * @return the category of the object represented by this training example.
     */
    Object getCategory();
    
    
}

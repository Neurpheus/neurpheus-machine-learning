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

import java.util.List;
import org.neurpheus.machinelearning.classification.ClassificationException;
import org.neurpheus.machinelearning.classification.Classifier;

/**
 * Represents a training process controller.
 *
 * @author Jakub Strychowski
 */
public interface Trainer {

    /**
     * Performs training process.
     * 
     * @throws TrainingException if the training process fails.
     */
    void train() throws TrainingException;
    
    /**
     * Tests a classifier.
     * 
     * @param maxNumberOfTestedExamples - The number of test examples which should be used for testing.
     * 
     * @return The quality of a classifier.
     * 
     * @throws ClassificationException if classification fails.
     * @throws TrainingSetException if an error occurrs while training set reading.
     */
    double test(int maxNumberOfTestedExamples, int numberOfExamplesToSkipInTrainingSet) throws ClassificationException, TrainingSetException;
    
    /**
     * Adds the given listener to the list of listeners receiving events from the trainer.
     * 
     * @param listener The listener which will receive events from the trainer.
     */
    void addListener(TrainingListener listener);
    
    /**
     * Removes the given listener from the list of listeners receiving events from the trainer.
     * 
     * @param listener The listener which should be removed from the list of listeners.
     */
    void removeListener(TrainingListener listener);
    
    /**
     * Returns the list of listeners receiving events from the trainer.
     * 
     * @return The list of TrainingListener objects.
     */
    List getListeners();
    
    /**
     * Breaks training or testing process.
     */
    void breakProcessing();

    int getNumberOfIterations();

    Classifier getClassifier();

    void setClassifier(Classifier classifier);
    
    
}

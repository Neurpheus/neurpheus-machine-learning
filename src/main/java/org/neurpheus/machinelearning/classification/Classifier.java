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

package org.neurpheus.machinelearning.classification;

import java.util.List;
import java.util.Properties;
import org.neurpheus.machinelearning.training.TrainingExample;
import org.neurpheus.machinelearning.training.TrainingException;

/**
 * Represents <b>a classification model.</b>.
 *
 * @author Jakub Strychowski
 */
public interface Classifier {
    
    /**
     * Classifies an object into one or several categories returned 
     * as an array of {@link ClassificationResult} objects.
     * 
     * @param obj The object which should be classified.
     *
     * @return The array of result categories.
     *
     * @throws ClassificationException if any classification error occurred.
     */
    ClassificationResult[] classify2Array(Object obj) throws ClassificationException;
    
    /**
     * Classifies an object into one or several categories returned 
     * as a list of {@link ClassificationResult} objects.
     * 
     * @param obj The object which should be classified.
     *
     * @return The list of result categories.
     *
     * @throws ClassificationException if any classification error occurred.
     */
    List classify(Object obj) throws ClassificationException;
    
    /**
     * Initialize a learning process - the classifier should prepare for the learning allocating 
     * needed resources.
     * 
     * @param trainingProperties The set of training properties.
     *
     * @throws TrainingException if a learning process cannot be initialized.
     */
    void initializeLearningProcess(Properties trainingProperties) throws TrainingException;
    
    /**
     * A trainer calls this method when a learning process begins.
     *
     * @throws TrainingException if a learning process cannot be started.
     */
    void onBeginLearning() throws TrainingException;
    
    /**
     * A trainer calls this method when this classifier should learn from the specified training example.
     * 
     * @param example The trainig example used by the classifier for learning.
     *
     * @throws TrainingException if an error occurred while learning using the given training example.
     */
    void onLearn(TrainingExample example) throws TrainingException;
    
    /**
     * A trainer calls this method when it starts a new iteration over the trainig set.
     * You can modify learning properties at the begining of the iteration.
     * 
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    void onBeginIteration(int iterationNumber) throws TrainingException;

    /**
     * A trainer calls this method when it finishes an iteration over the trainig set.
     * You can modify learning properties at the end of the iteration.
     * 
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    void onEndIteration(int iterationNumber) throws TrainingException;
    
    /**
     * A trainer calls this method when a learning process ends - the classifier should commit 
     * all changes made while the learning process.
     * 
     * @throws TrainingException if a learning process cannot be properly finished.
     */
    void onEndLearningProcess() throws TrainingException;
    
    /**
     * Finalizes a learning process - the classifier should dispose all resources 
     * allocated for the learning process.
     *
     * @throws TrainingException if a learning process cannot be properly finalized.
     */
    void finalizeLearningProcess() throws TrainingException;
    
    /**
     * A trainer calls this method when a learning process is broken - the classifier
     * should discard all changes and dispose all resources allocated for the learning process.
     * 
     * @throws TrainingException if a learning process cannot be properly broken.
     */
    void breakLearningProcess() throws TrainingException;
    

    /**
     * Sets a quality of this classifier.
     * Valid quality is a real number in the range from <code>0.0</code> to <code>100.0</code>.
     * The value <code>-1</code> means that the quality hasn't been measured yet.
     *
     * @param quality The measured quality of this classifier or <code>-1</code>.
     */
    void setQuality(double quality);
    
    /**
     * Returns a quality of this classifier.
     * The quality is a real number in the range from <code>0.0</code> to <code>100.0</code>.
     * If the quality hasn't been set this method returns <code>-1</code>.
     * 
     * @return The measured quality of this classifier or <code>-1</code>.
     */
    double getQuality();

    /**
     * Sets a root mean square error of this classifier.
     * Valid RMSE is a real number greater then <code>0.0</code>.
     * The value <code>-1</code> means that the error hasn't been measured yet.
     *
     * @param rmse The measured RMSE of this classifier or <code>-1</code>.
     */
    void setRMSE(double rmse);
    
    /**
     * Returns a root mean square error of this classifier.
     * The RMSE is a real number greater then <code>0.0</code>.
     * The value <code>-1</code> means that the error hasn't been measured yet.
     * 
     * @return The measured RMSE of this classifier or <code>-1</code>.
     */
    double getRMSE();
    
    /**
     * Sets a new value for the given property of this classifier.
     *
     * @param propertyName  The name of the property.
     * @param propertyValue The new value of the property.
     */
    void setProperty(String propertyName, Object propertyValue);
    
    /**
     * Returns a value of the given property of this classifier.
     * This method returns <code>null</code> if the given property hasn't been set for this classifier..
     *
     * @param propertyName  The name of the property.
     *
     * @return propertyValue The value of the property.
     */
    Object getProperty(String propertyName);
    
    /**
     * Initializes the classifier.
     *
     * @throws ClassificationException if the classifier cannot be initialized.
     */
    void initialize() throws ClassificationException;
    
    
    
}

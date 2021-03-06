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

import java.util.Properties;
import org.neurpheus.machinelearning.classification.Classifier;

/**
 * Represents a training process listener.
 * Instances of this interface listenes for actions generated by a trainer.
 *
 * @author Jakub Strychowski
 */
public interface TrainingListener {

    /**
     * Trainer calls this method before sending the 'initializeLearningProcess' command
     * to the given classifier.
     * 
     * @param classifier The trained classifier.     
     * @param trainingProperties The set of training properties.
     * 
     * @throws TrainingException if a learning process cannot be initialized.
     */
    void beforeInitializeLearningProcess(Classifier classifier, Properties trainingProperties)  throws TrainingException;

    /**
     * Trainer calls this method after sending the 'Initialize Learning Process' command
     * to the given classifier.
     * 
     * @param classifier The trained classifier.
     * @param trainingProperties The set of training properties.
     * 
     * @throws TrainingException if a learning process cannot be initialized.
     */
    void afterInitializeLearningProcess(Classifier classifier, Properties trainingProperties)  throws TrainingException;
    
    /**
     * Trainer calls this method before sending the 'Begin Learning' command
     * to the given classifier.
     *
     * @param classifier The trained classifier.
     * 
     * @throws TrainingException if a learning process cannot be started.
     */
    void beforeBeginLearning(Classifier classifier) throws TrainingException;

    /**
     * Trainer calls this method after sending the 'Begin Learning' command
     * to the given classifier.
     *
     * @param classifier The trained classifier.
     * 
     * @throws TrainingException if a learning process cannot be started.
     */
    void afterBeginLearning(Classifier classifier) throws TrainingException;
    
    /**
     * Trainer calls this method before sending the 'Learn' command
     * to the given classifier.
     * 
     * @param classifier The trained classifier.
     * @param example The trainig example used by the classifier for learning.
     * 
     * @throws TrainingException if an error occurred while learning using the given training example.
     */
    void beforeLearn(Classifier classifier, TrainingExample example) throws TrainingException;

    /**
     * Trainer calls this method after sending the 'Learn' command
     * to the given classifier.
     * 
     * @param classifier The trained classifier.
     * @param example The trainig example used by the classifier for learning.
     * 
     * @throws TrainingException if an error occurred while learning using the given training example.
     */
    void afterLearn(Classifier classifier, TrainingExample example) throws TrainingException;
    
    /**
     * Trainer calls this method before sending the 'Begin Iteration' command
     * to the given classifier.
     * 
     * @param classifier        The trained classifier.
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    void beforeBeginIteration(Classifier classifier, int iterationNumber) throws TrainingException;

    /**
     * Trainer calls this method before sending the 'Begin Iteration' command
     * to the given classifier.
     * 
     * @param classifier        The trained classifier.
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    void afterBeginIteration(Classifier classifier, int iterationNumber) throws TrainingException;
    
    /**
     * Trainer calls this method before sending the 'End Iteration' command
     * to the given classifier.
     * 
     * @param classifier        The trained classifier.
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    void beforeEndIteration(Classifier classifier, int iterationNumber) throws TrainingException;

    /**
     * Trainer calls this method after sending the 'End Iteration' command
     * to the given classifier.
     * 
     * @param classifier        The trained classifier.
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    void afterEndIteration(Classifier classifier, int iterationNumber) throws TrainingException;
    
    /**
     * Trainer calls this method before sending the 'End Learning Process' command
     * to the given classifier.
     * 
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly finished.
     */
    void beforeEndLearningProcess(Classifier classifier) throws TrainingException;
    
    /**
     * Trainer calls this method after sending the 'End Learning Process' command
     * to the given classifier.
     * 
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly finished.
     */
    void afterEndLearningProcess(Classifier classifier) throws TrainingException;

    /**
     * Trainer calls this method before sending the 'Finalize Learning Process' command
     * to the given classifier.
     *
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly finalized.
     */
    void beforeFinalizeLearningProcess(Classifier classifier) throws TrainingException;
    
    /**
     * Trainer calls this method after sending the 'Finalize Learning Process' command
     * to the given classifier.
     *
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly finalized.
     */
    void afterFinalizeLearningProcess(Classifier classifier) throws TrainingException;

    /**
     * Trainer calls this method before sending the 'Break Llearning Process' command
     * to the given classifier.
     * 
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly broken.
     */
    void beforeBreakLearningProcess(Classifier classifier) throws TrainingException;
    
    /**
     * Trainer calls this method after sending the 'Break Llearning Process' command
     * to the given classifier.
     * 
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly broken.
     */
    void afterBreakLearningProcess(Classifier classifier) throws TrainingException;

    Trainer getTrainer();

    void setTrainer(Trainer trainer);
    
}

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neurpheus.machinelearning.classification.ClassificationException;
import org.neurpheus.machinelearning.classification.Classifier;

/**
 * Represents the base implementation of the {@link Trainer} interface.
 *
 * @author Jakub Strychowski
 */
public class BaseTrainer implements Trainer {

    /** The logger used by this class. */
    private static Logger logger = Logger.getLogger(BaseTrainer.class.getName());
    
    private Classifier classifier;
    private TrainingSet trainingSet;
    private int numberOfIterations;
    private Properties properties;
    private int maxTrainingExamples;
    private List listeners;
    private boolean breakFlag;
    
    public BaseTrainer(final Classifier trClassifier, final TrainingSet trSet, 
            final int iterationsCount, final Properties trProperties, 
            final int maxNumberOfTrainingExamples) {
        classifier = trClassifier;
        trainingSet = trSet; 
        numberOfIterations = iterationsCount;
        properties = trProperties;
        maxTrainingExamples = maxNumberOfTrainingExamples;
        listeners = new ArrayList();
    }
    
    /**
     * Performs training process.
     * 
     * @throws TrainingException if the training process fails.
     */
    public void train() throws TrainingException {
        
        breakFlag = false;
        
        // call listeners before initialization of the learning process
        for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
            TrainingListener listener = (TrainingListener) listenerIt.next();
            listener.beforeInitializeLearningProcess(getClassifier(),properties);
        }
        // initialize learning process
        getClassifier().initializeLearningProcess(properties);
        // call listeners after initialization of the learning process
        for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
            TrainingListener listener = (TrainingListener) listenerIt.next();
            listener.afterInitializeLearningProcess(getClassifier(),properties);
        }
        
        try {
            
            // call listeners before begining the learning process
            for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                TrainingListener listener = (TrainingListener) listenerIt.next();
                listener.beforeBeginLearning(getClassifier());
            }
            // begin the learning process
            getClassifier().onBeginLearning();
            // call listeners after begining the learning process
            for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                TrainingListener listener = (TrainingListener) listenerIt.next();
                listener.afterBeginLearning(getClassifier());
            }
            
            
            // loop though all training iterations
            for (int i = 0; !breakFlag && i < getNumberOfIterations(); i++) {
                
                // call listeners before begining the iteration
                for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                    TrainingListener listener = (TrainingListener) listenerIt.next();
                    listener.beforeBeginIteration(getClassifier(), i + 1);
                }
                // begin the iteration
                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, 
                               "Learning process in progress. iteration number " + (i + 1) + "/" + getNumberOfIterations());
                }
                getClassifier().onBeginIteration(i + 1);
                // call listeners after begining the iteration
                for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                    TrainingListener listener = (TrainingListener) listenerIt.next();
                    listener.afterBeginIteration(getClassifier(), i + 1);
                }
                
                // iterate through all training examples
                int counter = 0;
                for (Iterator it = trainingSet.iterator(); !breakFlag && it.hasNext() && counter < maxTrainingExamples; counter++) {
                    TrainingExample example = (TrainingExample) it.next();
                    // call listeners before learnign with the training examples
                    for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                        TrainingListener listener = (TrainingListener) listenerIt.next();
                        listener.beforeLearn(getClassifier(),example);
                    }
                    // learn with the training example
                    getClassifier().onLearn(example);
                    if (logger.isLoggable(Level.FINE) && (counter % 10000 == 0)) {
                        logger.log(Level.FINE, 
                            "Number of processed training examples in the iteration : " + counter);
                    }
                    // call listeners after learnign with the training examples
                    for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                        TrainingListener listener = (TrainingListener) listenerIt.next();
                        listener.afterLearn(getClassifier(),example);
                    }
                }
                
                // call listeners before ending the iteration
                for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                    TrainingListener listener = (TrainingListener) listenerIt.next();
                    listener.beforeEndIteration(getClassifier(), i + 1);
                }
                // end the iteration
                getClassifier().onEndIteration(i + 1);
                // call listeners after ending the iteration
                for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                    TrainingListener listener = (TrainingListener) listenerIt.next();
                    listener.afterEndIteration(getClassifier(), i + 1);
                }
               
            }
            
            // call listeners before ending the learning process
            for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                TrainingListener listener = (TrainingListener) listenerIt.next();
                listener.beforeEndLearningProcess(getClassifier());
            }
            // end learning process
            getClassifier().onEndLearningProcess();
            // call listeners after ending the learning process
            for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                TrainingListener listener = (TrainingListener) listenerIt.next();
                listener.afterEndLearningProcess(getClassifier());
            }
            
        } catch (Exception e) {
            if (e instanceof TrainingException) {
                throw (TrainingException) e;
            } else {
                throw new TrainingException("Error while classifier learning.", e);
            }
        } finally {
            // call listeners before finalizing the learning process
            for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                TrainingListener listener = (TrainingListener) listenerIt.next();
                listener.beforeFinalizeLearningProcess(getClassifier());
            }
            // finalize the learning process
            getClassifier().finalizeLearningProcess();
            // call listeners after finalizing the learning process
            for (final Iterator listenerIt = listeners.iterator(); listenerIt.hasNext(); ) {
                TrainingListener listener = (TrainingListener) listenerIt.next();
                listener.afterFinalizeLearningProcess(getClassifier());
            }
        }
    }
    
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
    public double test(int maxNumberOfTestedExamples, int numberOfExamplesToSkipInTrainingSet) throws ClassificationException, TrainingSetException {
        Iterator it = trainingSet.iterator();
        breakFlag = false;
        int counter = 0;
        while (!breakFlag && counter < numberOfExamplesToSkipInTrainingSet && it.hasNext()) {
            ++counter;
            it.next();
        }
        int correct = 0;
        int wrong = 0;
        counter = 0;
        while (!breakFlag && counter < maxNumberOfTestedExamples && it.hasNext()) {
            ++counter;
            TrainingExample example = (TrainingExample) it.next();
            List clRes = getClassifier().classify(example);
            if (example.isCorrect(clRes)) {
                ++correct;
            } else {
                ++wrong;
            }
        }
        logger.info("The classification test has been performed using " + counter + " examples.");
        double quality = counter == 0 ? Double.NaN : 100.0 * correct / counter;
        return quality;
    }

    /**
     * Adds the given listener to the list of listeners receiving events from the trainer.
     * 
     * @param listener The listener which will receive events from the trainer.
     */
    public void addListener(final TrainingListener listener) {
        listeners.add(listener);
        listener.setTrainer(this);
    }

    /**
     * Removes the given listener from the list of listeners receiving events from the trainer.
     * 
     * @param listener The listener which should be removed from the list of listeners.
     */
    public void removeListener(TrainingListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns the list of listeners receiving events from the trainer.
     * 
     * @return The list of TrainingListener objects.
     */
    public List getListeners() {
        return listeners;
    }
    
    /**
     * Breaks training or testing process.
     */
    public void breakProcessing() {
        breakFlag = true;
    }
    
    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }
    
}

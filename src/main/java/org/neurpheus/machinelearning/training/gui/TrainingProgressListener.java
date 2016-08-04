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

package org.neurpheus.machinelearning.training.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.SwingUtilities;
import org.neurpheus.machinelearning.classification.Classifier;
import org.neurpheus.machinelearning.training.Trainer;
import org.neurpheus.machinelearning.training.TrainingExample;
import org.neurpheus.machinelearning.training.TrainingException;
import org.neurpheus.machinelearning.training.TrainingListener;

/**
 *
 * @author jstrychowski
 */
public class TrainingProgressListener implements TrainingListener, Runnable {

    
    public static final String SHOW_PROGRESS_FORM = 
            "or.neurpheus.classification.training.gui.showProgressForm";
    
    private TrainingProgressForm form;
    private boolean showProgress;
    private Trainer trainer;
    private List qualities;
    private List rmse;
    
    
    public TrainingProgressListener() {
    }
    
    
    
    /**
     * Trainer calls this method before sending the 'initializeLearningProcess' command
     * to the given classifier.
     * 
     * @param classifier The trained classifier.     
     * @param trainingProperties The set of training properties.
     * 
     * @throws TrainingException if a learning process cannot be initialized.
     */
    public void beforeInitializeLearningProcess(
            final Classifier classifier, final Properties trainingProperties)  
            throws TrainingException {
        Object obj = classifier.getProperty(SHOW_PROGRESS_FORM);
        showProgress = obj != null && Boolean.valueOf(obj.toString()).booleanValue();
        if (showProgress) {
            try {
                SwingUtilities.invokeAndWait(this);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        qualities = new ArrayList();
        rmse = new ArrayList();
    }

    /**
     * Trainer calls this method after sending the 'Initialize Learning Process' command
     * to the given classifier.
     * 
     * @param classifier The trained classifier.
     * @param trainingProperties The set of training properties.
     * 
     * @throws TrainingException if a learning process cannot be initialized.
     */
    public void afterInitializeLearningProcess(
            final Classifier classifier, final Properties trainingProperties)
            throws TrainingException {
        
    }
    
    /**
     * Trainer calls this method before sending the 'Begin Learning' command
     * to the given classifier.
     *
     * @param classifier The trained classifier.
     * 
     * @throws TrainingException if a learning process cannot be started.
     */
    public void beforeBeginLearning(final Classifier classifier)
            throws TrainingException {
        
    }

    /**
     * Trainer calls this method after sending the 'Begin Learning' command
     * to the given classifier.
     *
     * @param classifier The trained classifier.
     * 
     * @throws TrainingException if a learning process cannot be started.
     */
    public void afterBeginLearning(final Classifier classifier)
            throws TrainingException {
        
    }
    
    /**
     * Trainer calls this method before sending the 'Learn' command
     * to the given classifier.
     * 
     * @param classifier The trained classifier.
     * @param example The trainig example used by the classifier for learning.
     * 
     * @throws TrainingException if an error occurred while learning using the given training example.
     */
    public void beforeLearn(final Classifier classifier, final TrainingExample example)
            throws TrainingException {
        
    }

    /**
     * Trainer calls this method after sending the 'Learn' command
     * to the given classifier.
     * 
     * @param classifier The trained classifier.
     * @param example The trainig example used by the classifier for learning.
     * 
     * @throws TrainingException if an error occurred while learning using the given training example.
     */
    public void afterLearn(final Classifier classifier, final TrainingExample example) 
            throws TrainingException {
        
    }
    
    /**
     * Trainer calls this method before sending the 'Begin Iteration' command
     * to the given classifier.
     * 
     * @param classifier        The trained classifier.
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    public void beforeBeginIteration(final Classifier classifier, final int iterationNumber) 
            throws TrainingException {
        
    }

    /**
     * Trainer calls this method before sending the 'Begin Iteration' command
     * to the given classifier.
     * 
     * @param classifier        The trained classifier.
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    public void afterBeginIteration(final Classifier classifier, final int iterationNumber)
            throws TrainingException {
        
    }
    
    /**
     * Trainer calls this method before sending the 'End Iteration' command
     * to the given classifier.
     * 
     * @param classifier        The trained classifier.
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    public void beforeEndIteration(final Classifier classifier, final int iterationNumber)
            throws TrainingException {
        
    }

    /**
     * Trainer calls this method after sending the 'End Iteration' command
     * to the given classifier.
     * 
     * @param classifier        The trained classifier.
     * @param iterationNumber   The number of an iteration (starting from 1). 
     *
     * @throws TrainingException if an error occurred while learning.
     */
    public void afterEndIteration(final Classifier classifier, final int iterationNumber)
            throws TrainingException {
        form.setProgress(iterationNumber, trainer.getNumberOfIterations());
        qualities.add(new Double(trainer.getClassifier().getQuality()));
        form.setQualityValues(qualities);
        rmse.add(new Double(trainer.getClassifier().getRMSE()));
        form.setRMSEValues(rmse);
    }
    
    /**
     * Trainer calls this method before sending the 'End Learning Process' command
     * to the given classifier.
     * 
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly finished.
     */
    public void beforeEndLearningProcess(final Classifier classifier)
            throws TrainingException {
        
    }
    
    /**
     * Trainer calls this method after sending the 'End Learning Process' command
     * to the given classifier.
     * 
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly finished.
     */
    public void afterEndLearningProcess(final Classifier classifier) 
            throws TrainingException {
        
    }
            

    /**
     * Trainer calls this method before sending the 'Finalize Learning Process' command
     * to the given classifier.
     *
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly finalized.
     */
    public void beforeFinalizeLearningProcess(final Classifier classifier) 
            throws TrainingException {
        
    }
    
    /**
     * Trainer calls this method after sending the 'Finalize Learning Process' command
     * to the given classifier.
     *
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly finalized.
     */
    public void afterFinalizeLearningProcess(final Classifier classifier) 
            throws TrainingException {
        
    }

    /**
     * Trainer calls this method before sending the 'Break Llearning Process' command
     * to the given classifier.
     * 
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly broken.
     */
    public void beforeBreakLearningProcess(final Classifier classifier) 
            throws TrainingException {
        
    }
    
    /**
     * Trainer calls this method after sending the 'Break Llearning Process' command
     * to the given classifier.
     * 
     * @param classifier A trained classifier.
     * @throws TrainingException if a learning process cannot be properly broken.
     */
    public void afterBreakLearningProcess(final Classifier classifier) 
            throws TrainingException {
        
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void run() {
        form = new TrainingProgressForm(trainer);
        form.setVisible(true);
    }

}

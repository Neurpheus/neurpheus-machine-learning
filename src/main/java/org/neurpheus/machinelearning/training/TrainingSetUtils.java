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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 *
 * @author Jakub Strychowski
 */
public final class TrainingSetUtils {
    
    private static Logger logger = Logger.getLogger(TrainingSetUtils.class.getName());
    
    /**
     * Saves training examples from this set to the CSV file
     * This method saves training set in the CSV file where succeeding columns contains values 
     * of attributes of training examples. Each row in the result file correspondence to a single training
     * examples. First or last column in each line contains a category identifier into which the training
     * example belongs.
     * 
     * @param trainingSet       The training set containing training examples to export.
     * @param out               The output stream where csv content is written.
     * @param categoryAtEnd     If <code>true</code> category is stored at last position in the row,
     *                          if <code>false</code> category isstored at the row begining.
     * @param maxRowCount       Maximal number of output rows.
     *
     * @throws TrainingSetException if training set cannot be processed.
     * @throws IOException if an I/O error occurred.
     */
    public static void exportToCSV(final TrainingSet trainingSet, final OutputStream out, 
            final boolean categoryAtEnd, final int maxRowCount, final int categoriesCount) 
    throws TrainingSetException, IOException {
        try (PrintWriter writer = new PrintWriter(out)) {
            exportToCSV(trainingSet, writer, categoryAtEnd, maxRowCount, categoriesCount);
        }
    }
    
    /**
     * Saves training examples from this set to the CSV file
     * This method saves training set in the CSV file where succeeding columns contains values 
     * of attributes of training examples. Each row in the result file correspondence to a single training
     * examples. First or last column in each line contains a category identifier into which the training
     * example belongs.
     * 
     * @param trainingSet       The training set containing training examples to export.
     * @param writer            The writer used for writing in the CSV form.
     * @param categoryAtEnd     If <code>true</code> category is stored at last position in the row,
     *                          if <code>false</code> category isstored at the row begining.
     * @param maxRowCount       Maximal number of output rows.
     *
     * @throws TrainingSetException if training set cannot be processed.
     * @throws IOException if an I/O error occurred.
     */
    public static void exportToCSV(final TrainingSet trainingSet, final PrintWriter writer, 
            final boolean categoryAtEnd, final int maxRowCount, final int categoriesCount) 
    throws TrainingSetException, IOException {
        logger.info("Export traning set to CSV file");
        logger.info("Category at end of each row : " + categoryAtEnd);
        logger.info("Number of categories : " + categoriesCount);
        int inputSize = 0;
        int counter = 0;
        for (Iterator it = trainingSet.iterator(); counter < maxRowCount && it.hasNext(); counter++) {
            TrainingExample trainingExample = (TrainingExample) it.next();
            Object[] tuple = trainingExample.getTuple();
            inputSize = tuple.length;
            int categoryNumber = -1;
            try {
                categoryNumber = Integer.parseInt(trainingExample.getCategory().toString());
            } catch (NumberFormatException e) {}
            
            if (!categoryAtEnd) {
                if (categoryNumber == -1) {
                    writer.print(trainingExample.getCategory().toString());
                } else {
                    int i = 0;
                    while (i < categoryNumber) {
                        if (i++ > 0) {
                            writer.print(';');
                        }
                        writer.print('0');
                    }
                    if (i++ > 0) {
                        writer.print(';');
                    }
                    writer.print('1');
                    while (i++ < categoriesCount) {
                        writer.print(';');
                        writer.print('0');
                    }
                }
            }
            for (int i = 0; i < tuple.length; i++) {
                if (i > 0 || !categoryAtEnd) {
                    writer.print(';');
                }
                writer.print(tuple[i].toString());
            }
            if (categoryAtEnd) {
                if (categoryNumber == -1) {
                    writer.print(trainingExample.getCategory().toString());
                } else {
                    int i = 0;
                    while (i++ < categoryNumber) {
                        writer.print(';');
                        writer.print('0');
                    }
                    writer.print(';');
                    writer.print('1');
                    while (i++ < categoriesCount) {
                        writer.print(';');
                        writer.print('0');
                    }
                }
            }
            writer.println();
        }
        writer.flush();
        logger.info("Input size : " + inputSize);
    }

    
    
}

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
import org.neurpheus.machinelearning.classification.Classifier;


/**
 * Represents <b>a training example factory</b> which can create particular 
 * instances of the {@link TrainingExample} interface.
 *
 * @author Jakub Strychowski
 */
public interface TrainingExampleFactory {
    
    
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
    
}

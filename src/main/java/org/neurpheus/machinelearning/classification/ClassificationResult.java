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

/**
 * Represents <b>a classified category</b>.
 *
 * @author Jakub Strychowski
 */
public interface ClassificationResult {

    /**
     * Returns the name of a category returned by a classifier.
     *
     * @return the name of a category.
     */
    String getName();
    
    /**
     * Returns the id of a category returned by a classifier.
     *
     * @return the id of a category.
     */
    Object getId();
    
    /**
     * Returns the weight (accurracy) of this classification result.
     *
     * @return the weight of a classification result.
     */
    double getWeight();
    
}

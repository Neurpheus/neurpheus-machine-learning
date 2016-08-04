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
 * Represents the simple implementation of the {@link ClassificationResult} Objecterface.
 *
 * @author Jakub Strychowski
 */
public class SimpleClassificationResult implements ClassificationResult {

    /** Holds the name of a category returned as a classification result. */
    private String name;
    
    /** Holds the identifier of a category returned as a classification result. */
    private Object id;
    
    /** Holds the weight which is an accurracy of a classification result. */
    private double weight;
    
    
    /**
     * Creates a new instance of the Simple Classification Result.
     * 
     * @param crName     The name of a category returned as the classification result.
     * @param crId       The identifier of a category resturned as the classification result.
     * @param crWeight   The classification accurracy.
     */
    public SimpleClassificationResult(final String crName, final Object crId, final double crWeight) {
        this.name = crName;
        this.id = crId;
        this.weight = crWeight;
    }
    
    /**
     * Returns the name of a category returned by a classifier.
     *
     * @return the name of a category.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Sets the name of a category returned by a classifier.
     *
     * @param newName   The new name of a category.
     */
    public void setName(final String newName) {
        this.name = newName;
    }
    
    /**
     * Returns the id of a category returned by a classifier.
     *
     * @return the id of a category.
     */
    public Object getId() {
        return this.id;
    }

    /**
     * Sets the identifier of a category returned by a classifier.
     *
     * @param newId   The new identifier of a category.
     */
    public void setId(final Object newId) {
        this.id = newId;
    }
    
    /**
     * Returns the weight (accurracy) of this classification result.
     *
     * @return the weight of a classification result.
     */
    public double getWeight() {
        return this.weight;
    }
    
    /**
     * Sets the weight (accurracy) of this classification result.
     *
     * @param newWeight A new weight of the classification result.
     */
    public void setWeight(final double newWeight) {
        this.weight = newWeight;
    }
    
    
}

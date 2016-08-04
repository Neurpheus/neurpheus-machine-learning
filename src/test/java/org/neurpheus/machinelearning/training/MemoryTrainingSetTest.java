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

import org.neurpheus.machinelearning.training.MemoryTrainingSet;
import org.neurpheus.machinelearning.training.TrainingExample;
import java.util.HashSet;
import junit.framework.*;
import java.util.Iterator;

/**
 * Tests MemoryTrainingSet class.
 *
 * @author Jakub Strychowski
 */
public class MemoryTrainingSetTest extends TestCase {
    
    public MemoryTrainingSetTest(String testName) {
        super(testName);
    }

    private final int EXAMPLES_COUNT = 1000;
    private TrainingExample[] examples;
    
    protected void setUp() throws Exception {
        examples = new TrainingExample[EXAMPLES_COUNT];
        for (int i = 0; i < examples.length; i++) {
            int x = i % 5;
            int y = i / 5;
            int result = x + y;
            examples[i] = new TestTrainingExample(x + " + " + y + " = " + result, x, y, result);
        }
    }

    protected void tearDown() throws Exception {
        examples = null;
    }

    public void testAddTrainingExample() throws Exception {
        MemoryTrainingSet set = new MemoryTrainingSet();
        for (int i = 0; i < examples.length; i++) {
            set.addTrainingExample(examples[i]);
        }
        set.close();
    }

    public void testIterator() throws Exception {
        MemoryTrainingSet set = new MemoryTrainingSet();
        for (int i = 0; i < examples.length; i++) {
            set.addTrainingExample(examples[i]);
        }
        int counter = 0;
        for (Iterator it = set.iterator(); it.hasNext(); counter++) {
            TrainingExample example = (TrainingExample) it.next();
            assertEquals(examples[counter], example);
        }
        set.close();
    }

    public void testMix() throws Exception {
        HashSet allExamples = new HashSet();
        MemoryTrainingSet set = new MemoryTrainingSet();
        for (int i = 0; i < examples.length; i++) {
            set.addTrainingExample(examples[i]);
            allExamples.add(examples[i]);
        }
        set.mix();
        int counter = 0;
        int theSamePosition = 0;
        for (Iterator it = set.iterator(); it.hasNext(); counter++) {
            TrainingExample example = (TrainingExample) it.next();
            if (example.equals(examples[counter])) {
                theSamePosition++;
            }
            assertTrue(allExamples.contains(example));
            allExamples.remove(example);
        }
        assertTrue(theSamePosition < 0.1 * counter);
        set.close();
    }
    
}

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

import org.neurpheus.machinelearning.training.FileTrainingSet;
import org.neurpheus.machinelearning.training.TrainingExampleFactory;
import org.neurpheus.machinelearning.training.TrainingExample;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.zip.InflaterInputStream;
import junit.framework.*;

/**
 * Tests FileTrainingSet class.
 *
 * @author Jakub Strychowski
 */
public class FileTrainingSetTest extends TestCase {
    
    public FileTrainingSetTest(String testName) {
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
        File file = File.createTempFile("testFileTrainingSet", ".tmp");
        TrainingExampleFactory factory = new TestTrainingExample();
        FileTrainingSet set = new FileTrainingSet(file, factory);
        for (int i = 0; i < examples.length; i++) {
            set.addTrainingExample(examples[i]);
        }
        set.close();
        DataInputStream dis = new DataInputStream(new InflaterInputStream(new BufferedInputStream(new FileInputStream(file))));
        for (int i = 0; i < examples.length; i++) {
            int length = dis.readInt();
            byte[] data = new byte[length];
            dis.readFully(data);
            TrainingExample example = factory.createFromData(data);
            assertEquals(examples[i], example);
        }
        dis.close();
        set.close();
        file.delete();
    }

    public void testIterator() throws Exception {
        File file = File.createTempFile("testFileTrainingSet", ".tmp");
        FileTrainingSet set = new FileTrainingSet(file, new TestTrainingExample());
        for (int i = 0; i < examples.length; i++) {
            set.addTrainingExample(examples[i]);
        }
        int counter = 0;
        for (Iterator it = set.iterator(); it.hasNext(); counter++) {
            TrainingExample example = (TrainingExample) it.next();
            assertEquals(examples[counter], example);
        }
        set.close();
        file.delete();
    }

    public void testMix() throws Exception {
        int old = FileTrainingSet.MAX_BUFFERED_TRAINING_EXAMPLES;
        FileTrainingSet.MAX_BUFFERED_TRAINING_EXAMPLES = 150;
        
        File file = File.createTempFile("testFileTrainingSet", ".tmp");
        HashSet allExamples = new HashSet();
        FileTrainingSet set = new FileTrainingSet(file, new TestTrainingExample());
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
        file.delete();
        FileTrainingSet.MAX_BUFFERED_TRAINING_EXAMPLES = old;
    }

    
}

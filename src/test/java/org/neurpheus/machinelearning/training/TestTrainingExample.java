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

import org.neurpheus.machinelearning.training.TrainingSetException;
import org.neurpheus.machinelearning.training.TrainingExampleFactory;
import org.neurpheus.machinelearning.training.TrainingExample;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.neurpheus.machinelearning.classification.Classifier;


public class TestTrainingExample implements TrainingExample, TrainingExampleFactory {
    
    public String text;
    public int x;
    public int y;
    public int result;
    
    public TestTrainingExample() {
    }
    
    public TestTrainingExample(final String t, final int xx, final int yy, final int rresult) {
        text = t;
        x = xx;
        y = yy;
        result = rresult;
    }
    
    public void activate(Classifier classifier) throws TrainingSetException {
    }
    
    public boolean isCorrect(Object classifierResult) throws TrainingSetException {
        return result == ((Integer) classifierResult).intValue();
    }
    
    public boolean equals(Object obj) {
        TestTrainingExample b = (TestTrainingExample) obj;
        return text.equals(b.text) && x == b.x && y == b.y && result == b.result;
    }

    public int hashCode() {
        return text.hashCode();
    }
    
    /** Holds the buffer size for the {@link #getData()} method. */
    private static int maxDataSize = 32;
    
    /**
     * Returns the data stored in this raining example in the form of array of bytes.
     *
     * @return serialized training example data.
     */
    public byte[] getData() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(maxDataSize);
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeUTF(text);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(result);
            dos.flush();
            byte[] result = baos.toByteArray();
            if (result.length > maxDataSize) {
                maxDataSize = result.length;
            }
            return result;
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Creates an instance of training example from the given data.
     *
     * @param data A set of data of an training example.
     *
     * @return created training example
     *
     * @throws TrainingSetException if training example cannot be created.
     */
    public TrainingExample createFromData(final byte[] data) throws TrainingSetException {
        try {
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
            text = dis.readUTF();
            x = dis.readInt();
            y = dis.readInt();
            result = dis.readInt();
            dis.close();
        } catch (IOException e) {
            throw new TrainingSetException(e);
        }
        return new TestTrainingExample(text, x, y, result);
    }
    
    public Object getCategory() {
        return Integer.toString(result);
    }
    
    public Object[] getTuple() {
        return new Object[] {text, new Integer(x), new Integer(y), new Integer(result)};
    }
    
}
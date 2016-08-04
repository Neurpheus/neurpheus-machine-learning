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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * A set of <b>training examples persisted in a file</b>.
 *
 * @author Jakub Strychowski
 */
public class FileTrainingSet implements TrainingSet, Iterator {
    
    /** The logger for this class. */
    private static Logger logger = Logger.getLogger(FileTrainingSet.class.getName());
    
    /** Holds the input stream for reading training examples. */
    private DataInputStream in;
    
    /** Holds the output stream for adding training examples. */
    private DataOutputStream out;
    
    /** Holds the path to the training set file. */
    private File file;
    
    /** Holds the last training example fetched by the hasNetx method. */
    private TrainingExample bufferedTrainingExample;
    
    /** A flag which informs if hasNext method has fetched a training example. */
    private boolean trainingExampleBuffered;
    
    /** Holds the class of training examples stored in this training set. */
    private TrainingExampleFactory factory;
    
    /**
     * Creates a new instance of FileTrainingSet.
     *
     * @param filePath  The path of a file within which training examples are stored.
     * @param taFactory A training example factory which can create training examples stored in this set.
     *
     * @throws IOException if the file cannot be read or write.
     */
    public FileTrainingSet(final String filePath, final TrainingExampleFactory teFactory) throws IOException {
        out = null;
        in = null;
        file = new File(filePath);
        factory = teFactory;
        checkFile();
    }
    
    /**
     * Creates a new instance of FileTrainingSet.
     *
     * @param f The file within which training examples are stored.
     *
     * @throws IOException if the file cannot be read or write.
     */
    public FileTrainingSet(final File f, final TrainingExampleFactory teFactory)  throws IOException {
        out = null;
        in = null;
        file = f;
        factory = teFactory;
        checkFile();
    }
    
    /**
     * Checks if a working file was correctly specified.
     *
     * @throws IOException if file does not exist or it cannot be read or write.
     */
    private void checkFile() throws IOException {
        if (file.exists()) {
            if (!file.isFile()) {
                throw new IOException("The specified file location does not point a file:"
                        +  file.getAbsolutePath());
            }
            if (!file.canRead()) {
                throw new IOException("Cannot read from file: " + file.getAbsolutePath());
            }
            if (!file.canWrite()) {
                throw new IOException("Cannot write to file: " +  file.getAbsolutePath());
            }
        } else {
            if (!file.createNewFile()) {
                throw new IOException("Cannot create file: " + file.getAbsolutePath());
            }
        }
    }
    
    /**
     * Ensures that all resources allocated by this training set will be realesed.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    public synchronized void close() throws TrainingSetException {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                throw new TrainingSetException(
                        "Cannot close input stream used by the training set iterator.", e);
            }
            in = null;
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                throw new TrainingSetException(
                        "Cannot close output stream used for adding training examples.", e);
            }
            out = null;
        }
    }
    
    /**
     * Sets the training set to writing mode.
     *
     * @throws TrainingSetException if an output stream cannot be initialized.
     */
    private void openForWriting() throws TrainingSetException {
        close();
        try {
            out = new DataOutputStream(new DeflaterOutputStream(new BufferedOutputStream(new FileOutputStream(file, true))));
        } catch (IOException e) {
            throw new TrainingSetException("Cannot open a training set file for writing.", e);
        }
    }
    
    /**
     * Sets the training set to reading mode.
     *
     * @throws TrainingSetException if an input stream cannot be initialized.
     */
    private void openForReading() throws TrainingSetException {
        close();
        bufferedTrainingExample = null;
        trainingExampleBuffered = false;
        try {
            in = new DataInputStream(new InflaterInputStream(new BufferedInputStream(new FileInputStream(file))));
        } catch (IOException e) {
            throw new TrainingSetException("Cannot open a training set file for reading.", e);
        }
    }
    
    
    /**
     * Adds a training example to this training set.
     *
     * @param example The example which should be added.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    public synchronized void addTrainingExample(final TrainingExample example) throws TrainingSetException {
        if (out == null) {
            openForWriting();
        }
        try {
            byte[] data = example.getData();
            out.writeInt(data.length);
            out.write(data);
        } catch (IOException e) {
            throw new TrainingSetException("Cannot write training examples.", e);
        }
    }
    
    /**
     * Returns an iterator over training examples.
     *
     * @return An iterator over all training examples stored in this training set.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    public synchronized Iterator iterator() throws TrainingSetException {
        close();
        openForReading();
        return this;
    }
    
    /**
     * Returns <tt>true</tt> if the iteration has more elements. (In other
     * words, returns <tt>true</tt> if <tt>next</tt> would return an element
     * rather than throwing an exception.)
     *
     * @return <tt>true</tt> if the iterator has more elements.
     */
    public boolean hasNext() {
        if (!trainingExampleBuffered) {
            trainingExampleBuffered = true;
            bufferedTrainingExample = null;
            try {
                int dataLength = in.readInt();
                byte[] data = new byte[dataLength];
                in.readFully(data);
                bufferedTrainingExample = factory.createFromData(data);
            } catch (EOFException e) {
                return false;
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Cannot read training example from  file.", e);
                return false;
            }
        }
        return bufferedTrainingExample != null;
    }
    
    /**
     * Returns the next element in the iteration.  Calling this method
     * repeatedly until the {@link #hasNext()} method returns false will
     * return each element in the underlying collection exactly once.
     *
     * @return the next element in the iteration.
     */
    public Object next() {
        if (!trainingExampleBuffered && !hasNext()) {
            throw new NoSuchElementException("Not more training examples available in the training set.");
        }
        trainingExampleBuffered = false;
        return bufferedTrainingExample;
    }
    
    /**
     * This method of the Iterator interface is not supported by this class.
     */
    public void remove() {
        throw new UnsupportedOperationException(
                "You cannot use iterator to remove training examples from the training ser.");
    }
    
    
    public static int MAX_BUFFERED_TRAINING_EXAMPLES = 200000;
    
    /**
     * Randomizes an order of all training examples in this set.
     *
     * @throws TrainingSetException if any error occurred while training set processing.
     */
    public void mix() throws TrainingSetException {
        logger.info("Changing the order of the training set.");
        close();
        int fileNumber = 0;
        ArrayList files = new ArrayList();
        int count = 0;
        Iterator it = iterator();
        ArrayList buffer = new ArrayList();
        int bufferSize = 0;
        do {
            if (it.hasNext()) {
                TrainingExample example = (TrainingExample) it.next();
                buffer.add(example);
                ++bufferSize;
                ++count;
            }
            if (bufferSize > MAX_BUFFERED_TRAINING_EXAMPLES || !it.hasNext()) {
                TrainingExample[] tab = (TrainingExample[]) buffer.toArray(new TrainingExample[0]);
                buffer.clear();
                // randomize order of elements in the array;
                for (int i = bufferSize << 2; i > 0; i--) {
                    // swap two elements
                    int x = (int) Math.round(Math.random() * (bufferSize - 1));
                    int y = (int) Math.round(Math.random() * (bufferSize - 1));
                    TrainingExample v = tab[x];
                    tab[x] = tab[y];
                    tab[y] = v;
                }
                // write array to file
                DataOutputStream dos = null;
                try {
                    File tmpFile = File.createTempFile("mixFileTrainingSet", ".tmp" + (fileNumber++));
                    if (logger.isLoggable(Level.FINE)) {
                        logger.log(Level.FINE, 
                           "Writing portion of training examples to the file " + tmpFile.getAbsolutePath());
                    }
                    files.add(tmpFile);
                    dos = new DataOutputStream(new DeflaterOutputStream(new BufferedOutputStream(new FileOutputStream(tmpFile))));
                    for (int i = tab.length - 1; i >=0; i--) {
                        byte[] data = tab[i].getData();
                        dos.writeInt(data.length);
                        dos.write(data);
                    }
                } catch (IOException e) {
                    throw new TrainingSetException("Cannot write training examples to temporary file", e);
                } finally {
                    if (dos != null) {
                        try {
                            dos.close();
                        } catch (IOException e) {
                            logger.log(Level.SEVERE, "Cannot close output stream", e);
                        }
                    }
                }
                bufferSize = 0;
            }
        } while (it.hasNext());
        clear();
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Joining temprary files. number of files: " + files.size());
        }
        DataInputStream[] dis = new DataInputStream[files.size()];
        Arrays.fill(dis, null);
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Joining temporary files into result training set file.");
        }
        try {
            // open temporary files
            for (int i = 0; i < dis.length; i++) {
                dis[i] = new DataInputStream(new InflaterInputStream(new BufferedInputStream(new FileInputStream((File) files.get(i)))));
            }
            // add all training sets until eof reached
            boolean doJoining = true;
            do {
                doJoining = false;
                for (int i = 0; i < dis.length; i++) {
                    if (dis[i] != null) {
                        try {
                            int length = dis[i].readInt();
                            byte[] data = new byte[length];
                            dis[i].readFully(data);
                            TrainingExample example = factory.createFromData(data);
                            addTrainingExample(example);
                            doJoining = true;
                        } catch (EOFException e) {
                            // no more data to read
                            try {
                                dis[i].close();
                            } catch (IOException ex) {
                                logger.log(Level.SEVERE, "Cannot close temporary file.", ex);
                            }
                            dis[i] = null;
                        }
                    }
                }
            } while (doJoining);
        } catch (IOException e) {
            close();
            throw new TrainingSetException("Cannot create reordered file", e);
        } finally {
            // remove temporary files
            for (Iterator fit = files.iterator(); fit.hasNext(); ) {
                File f = (File) fit.next();
                f.delete();
            }
        }
        close();
    }
    
    /**
     * Removes all training elements from this set.
     *
     * @throws TrainingSetException if training set cannot be clear correctly.
     */
    public void clear() throws TrainingSetException {
        close();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.close();
        } catch (IOException e) {
            throw new TrainingSetException("Cannot clear workng file.", e);
        }
    }
    
    /**
     * Releases resources allocated by this object.
     *
     * @throws Throwable if any error occurred.
     */
    protected void finalize() throws Throwable {
        close();
        file = null;
    }
    
    
    
}

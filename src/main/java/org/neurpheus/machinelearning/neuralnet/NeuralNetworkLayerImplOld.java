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

package org.neurpheus.machinelearning.neuralnet;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author  Jakub Strychowski
 */
public class NeuralNetworkLayerImplOld implements Serializable {

    /** Unique serialization identifier of this class. */
    static final long serialVersionUID = 770608061208183740L;
    
    
    public final static int BYTES_ENCODING_DEFAULT = 1;
    public final static int BYTES_ENCODING_EUCLIDES = 1;
    
    private int inputsCount;
    private int neuronsCount;
    
    protected double[] neuronsWeightsDouble;
    protected double[][] inputsWeightsDouble;

    protected byte[] neuronsWeightsByte;
    protected byte[][] inputsWeightsByte;
    
    private double RMSE;

    
    public static double[] byte2double = null;

    /** Flaga okre¶laj±ca czy sieć przechowuje wagi w postaci liczb typu double */
    protected boolean doubleWeights;
    
    /** Creates a new instance of NeuronsLayer */
    public NeuralNetworkLayerImplOld(int iCount, int nCount, boolean doubleWeights) {
        this.neuronsCount = nCount;
        this.inputsCount = iCount;
        this.doubleWeights = doubleWeights;
        if (doubleWeights){
            neuronsWeightsDouble = new double[nCount];
            inputsWeightsDouble = new double[nCount][];
            neuronsWeightsByte = null;
            inputsWeightsByte = null;
            for (int i = 0; i < nCount; i++) inputsWeightsDouble[i] = new double[iCount];
        } else {
            neuronsWeightsByte = new byte[nCount];
            inputsWeightsByte = new byte[nCount][];
            neuronsWeightsDouble = null;
            inputsWeightsDouble = null;
            for (int i = 0; i < nCount; i++) inputsWeightsByte[i] = new byte[iCount];
        }
        if (byte2double == null){
            byte2double = new double[256];
            for (int i = 0; i < 256; i++) {
                //int sign = (i & 0x80) != 0 ? -1 : 1;
                //byte2double[i] = sign * ((i&0x7F) / 8.0);
                //byte2double[i] = ((i&0xFF) / 8.0);
                //byte2double[i] = Math.exp(((i & 0x80) != 0 ? -1 : 1) * i&0x7F);
                //byte2double[i] = ((i & 0x60) != 0 ? -1 : 1) *  Math.exp(((i & 0x80) != 0 ? -1 : 1) * i&0x3F);
                byte2double[i] = ((i&0xFF) / 255.0) * 2 -1;
            }
        }
    }
    
        
        
    /** Getter for property inputsCount.
     * @return Value of property inputsCount.
     *
     */
    public int getInputsCount() {
        return inputsCount;
    }    
    
    /** Getter for property neuronsCount.
     * @return Value of property neuronsCount.
     *
     */
    public int getNeuronsCount() {
        return neuronsCount;
    }

    private final static int[] MASK1 = {0x00, 0x80, 0xC0, 0xE0, 0xF0, 0xF8, 0xFC, 0xFE, 0xFF};
    private final static int[] MASK2 = {0xFF, 0x7F, 0x3F, 0x1F, 0x0F, 0x07, 0x03, 0x01, 0x00};
    
    
    public byte recombine(byte a, byte b, int pos){
        return (byte)((a & MASK1[pos]) | (b & MASK2[pos]));
    }
    
    public void recombine(NeuralNetworkLayerImplOld a, NeuralNetworkLayerImplOld b){
        if (doubleWeights){
            for (int i=0; i<neuronsWeightsDouble.length; i++){
                if (Math.random()>0.5){
                    neuronsWeightsDouble[i] = a.neuronsWeightsDouble[i];
                    for (int j=0; j<this.inputsCount; j++){
                        inputsWeightsDouble[i][j] = a.inputsWeightsDouble[i][j];
                    }
                } else {
                    neuronsWeightsDouble[i] = b.neuronsWeightsDouble[i];
                    for (int j=0; j<this.inputsCount; j++){
                        inputsWeightsDouble[i][j] = b.inputsWeightsDouble[i][j];
                    }
                }
            }
        } else {
            for (int i=0; i<neuronsWeightsByte.length; i++){
                if (Math.random()>0.5){
                    neuronsWeightsByte[i] = a.neuronsWeightsByte[i];
                    for (int j=0; j<this.inputsCount; j++){
                        inputsWeightsByte[i][j] = a.inputsWeightsByte[i][j];
                    }
                } else {
                    neuronsWeightsByte[i] = b.neuronsWeightsByte[i];
                    for (int j=0; j<this.inputsCount; j++){
                        inputsWeightsByte[i][j] = b.inputsWeightsByte[i][j];
                    }
                }
            }
        }
        //        int pos = 1+(int)(Math.round(Math.random())*(this.neuronsCount-1));
        //        for (int i=0; i<neuronsWeights.length; i++){
        //            neuronsWeights[i] = 1;//i<pos ? a.neuronsWeights[i] : b.neuronsWeights[i];
        //            for (int j=0; j<this.inputsCount; j++){
        //                inputsWeights[i][j] = i<pos ? a.inputsWeights[i][j] : b.inputsWeights[i][j];
        //            }
        //        }

        //        int pos = 1+(int)(Math.round(Math.random())*6);
        //        for (int i=0; i<neuronsWeights.length; i++){
        //            neuronsWeights[i] = recombine(a.neuronsWeights[i], b.neuronsWeights[i], pos);
        //            for (int j=0; j<this.inputsCount; j++){
        //                inputsWeights[i][j] = recombine(a.inputsWeights[i][j], b.inputsWeights[i][j],  pos);
        //            }
        //        }
    }

    public void mutation(){
        if (Math.random()<0.30){
            int nPos = (int)Math.round(Math.random()*(neuronsCount-1));
            if (doubleWeights){
                double[] tmp = inputsWeightsDouble[nPos];
                for (int j=0; j<inputsCount; j++){
                    tmp[j] = 2*Math.random()-1;//(byte)(java.lang.Math.random()*255-128);
                }
            } else {
                byte[] tmp = inputsWeightsByte[nPos];
                for (int j=0; j<inputsCount; j++){
                    tmp[j] = (byte)(java.lang.Math.random()*255-128);
                }
            }
        }
    }

    public void makeTransmiter(){
        if (doubleWeights){
            for (int i=0; i<neuronsCount; i++){
                neuronsWeightsDouble[i] = 1;
                for (int j=0; j<inputsCount; j++){
                    inputsWeightsDouble[i][j] = 0;
                }
                inputsWeightsDouble[i][i]=1;
            }
        } else {
            for (int i=0; i<neuronsCount; i++){
                neuronsWeightsByte[i] = 0;
                for (int j=0; j<inputsCount; j++){
                    inputsWeightsByte[i][j] = 0;
                }
                inputsWeightsByte[i][i]=1;
            }
        }
    }
    

    public void random(){
        if (doubleWeights){
            for (int i=0; i<neuronsCount; i++){
                this.neuronsWeightsDouble[i] = (maxValue - (maxValue - minValue) * Math.random()) / 2;
                double[] tmp = inputsWeightsDouble[i];
                for (int j = tmp.length - 1; j >= 0; j--) {
                    tmp[j] = (maxValue - (maxValue - minValue) * Math.random()) / 2;
                }
            }
        } else {
            for (int i=0; i<neuronsCount; i++){
                this.neuronsWeightsByte[i] =(byte)(java.lang.Math.random()*255-128);
                byte[] tmp = inputsWeightsByte[i];
                for (int j=0; j<inputsCount; j++){
                    tmp[j] = (byte)(java.lang.Math.random()*255-128);
                }
            }
        }
    }
    
    public static transient double minValue = 0;
    public static transient double maxValue = 1;
    
    public double[] calculate(double[] input){
        double[] res = new double[neuronsCount];
        if (doubleWeights){
//            Arrays.fill(res, (maxValue - minValue)/2);
//            Arrays.fill(res, 0);
            System.arraycopy(neuronsWeightsDouble, 0, res, 0, res.length);
            for (int i = inputsCount-1; i>=0; i--){
                if (input[i]!=0){
                    for (int j = neuronsCount-1;j>=0;j--){
                        res[j] += input[i] * inputsWeightsDouble[j][i];
                    }
                }
            }
            for (int i = neuronsCount - 1; i >= 0; i--) {
//                res[i] = -1 + (2/ (1+Math.exp(-2* res[i] ) ) );            
                res[i] = 1 / (1 + Math.exp(-res[i]));
            }
        } else {
            java.util.Arrays.fill(res, 0);
            for (int i = inputsCount-1; i>=0; i--){
                if (input[i]!=0){
                    for (int j = neuronsCount-1;j>=0;j--){
                        res[j] += input[i] * byte2double[inputsWeightsByte[j][i] & 0xFF];
                    }
                }
            }
//            for (int i = neuronsCount-1; i>=0; i--){
//                //res[i] *= byte2double[neuronsWeightsByte[i] &0xFF];
//                res[i] /= 20;
//            }
        }
        return res;
    }
    
    public void learn(double[] input, double[] output, double eta, double alfa){
        double[] y = calculate(input);
        if (doubleWeights){
            for (int i = output.length - 1; i >= 0; i--){
                double error = output[i]-y[i];
                if (error != 0) {
                    RMSE += error * error / 2;
//                    double delta = (1 + y[i]) * (1 - y[i]) * error * eta;
                    double delta = y[i] * (1 - y[i]) * error * eta * 20;
                    if (delta != 0) {
                        neuronsWeightsDouble[i] += delta;
                        double[] tmp = inputsWeightsDouble[i];
                        for (int j = tmp.length - 1; j >= 0; j--) {
                            tmp[j] += delta * input[j];
                        }
                    }
                }
            }
        }
    }


    
    
    public void makeByteWeights(){
        if (doubleWeights){
            normalize();
            neuronsWeightsByte = new byte[neuronsCount];
            inputsWeightsByte = new byte[neuronsCount][];
            for (int i = 0; i < neuronsCount; i++){
                neuronsWeightsByte[i] = (byte)(((int)(neuronsWeightsDouble[i]*127)) & 0xFF);
                inputsWeightsByte[i] = new byte[inputsCount];
                for (int j = 0; j < inputsCount; j++){
                    inputsWeightsByte[i][j] = (byte)(((int)(inputsWeightsDouble[i][j]*127)) & 0xFF);
                }
                inputsWeightsDouble[i] = null;
            }
            inputsWeightsDouble = null;
            neuronsWeightsDouble = null;
            doubleWeights = false;
        }
    }
    
    /** 
     *  Sprowadza wszystkie warto¶ci wag sieci neuronowej do zakresu od -1 do +1.
     */
    public void normalize(){
        if (doubleWeights){
            double maxValue = 0;
            for (int i = 0; i < neuronsCount; i++){
                maxValue = Math.max(Math.abs(neuronsWeightsDouble[i]), maxValue);
                for (int j = 0; j < inputsCount; j++){
                    maxValue = Math.max(Math.abs(inputsWeightsDouble[i][j]), maxValue);
                }
            }
            for (int i = 0; i < neuronsCount; i++){
                neuronsWeightsDouble[i] /= maxValue;
                for (int j = 0; j < inputsCount; j++){
                    inputsWeightsDouble[i][j] /= maxValue;
                }
            }
        }
    }

    public long getAllocationSize(){
        long res = 8 + 4 *16 + 1;
        if (doubleWeights){
            res += neuronsWeightsDouble.length * 24;
            for (int i = 0; i < inputsWeightsDouble.length; i++){
                res += inputsWeightsDouble[i].length * 8;
            }
        } else{
            res += neuronsWeightsByte.length * 17;
            for (int i = 0; i < inputsWeightsByte.length; i++){
                res += inputsWeightsByte[i].length;
            }
        }
        return res;
    }

    public double getRMSE() {
        return RMSE;
    }

    public void setRMSE(double RMSE) {
        this.RMSE = RMSE;
    }
    
    
}

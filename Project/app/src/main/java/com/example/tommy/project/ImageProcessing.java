package com.example.tommy.project;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.EigenvalueDecomposition;

/**
 * Created by Tommy on 19/06/2017.
 */

public class ImageProcessing {
    private byte[][] array= new byte[24][];
    private byte[] meanImage= new byte[129600];
    private byte[][] phi_i = new byte[24][];
    private double[][] doubleArray=new double[24][];
    private static int index=0;
    public ImageProcessing(){
    }

    public void AddPhoto(byte[] picture){
        array[index]= picture;
        index++;
        if(index == 24){
            GetMeanImage();
            GetPhi_i();
            GetEigenfaces();
        }
    }

    public void GetMeanImage() {
        int[] meanImageProv= new int[129600];
        for(int i=0;i<23;i++) {
            for (int j = 0; j < 129600; j++) {
                meanImageProv[i] = (array[i][j] + array[i + 1][j]) / 24;
            }
        }
        for (int i=0;i<24;i++){
            meanImage[i]=(byte) meanImageProv[i];
        }
    }

    public void GetPhi_i() {
        int[][] phi_iProv = new int[24][];
        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 129600; j++) {
                phi_iProv[i][j] = array[i][j] - meanImage[j];
            }
        }
        //Underflow?????Sevalore è < 0 ci metto 0
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 129600; j++) {
                if (phi_iProv[i][j] < 0) {
                    phi_iProv[i][j] = 0;
                }
                phi_i[i][j] = (byte) phi_iProv[i][j];
            }
        }
        //Conversione a double
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 129600; j++) {
                 doubleArray[i][j]= (double) array[i][j];
            }
        }
    }
    public void GetEigenfaces() {
        DoubleMatrix2D matrix = new DoubleMatrix2D() {
            @Override
            public double getQuick(int i, int i1) {
                return 0;
            }

            @Override
            public DoubleMatrix2D like(int i, int i1) {
                return null;
            }

            @Override
            public DoubleMatrix1D like1D(int i) {
                return null;
            }

            @Override
            protected DoubleMatrix1D like1D(int i, int i1, int i2) {
                return null;
            }

            @Override
            public void setQuick(int i, int i1, double v) {

            }

            @Override
            protected DoubleMatrix2D viewSelectionLike(int[] ints, int[] ints1) {
                return null;
            }
        };
        DoubleMatrix2D eigenVectors= new DoubleMatrix2D() {
            @Override
            public double getQuick(int i, int i1) {
                return 0;
            }

            @Override
            public DoubleMatrix2D like(int i, int i1) {
                return null;
            }

            @Override
            public DoubleMatrix1D like1D(int i) {
                return null;
            }

            @Override
            protected DoubleMatrix1D like1D(int i, int i1, int i2) {
                return null;
            }

            @Override
            public void setQuick(int i, int i1, double v) {

            }

            @Override
            protected DoubleMatrix2D viewSelectionLike(int[] ints, int[] ints1) {
                return null;
            }
        };
        EigenvalueDecomposition v= new EigenvalueDecomposition(matrix);
        matrix.assign(doubleArray);
        eigenVectors = v.getV();
    }
    public void GetPhi(){

    }
    public void GetOhmega(){

    }
    public void GetError(){

    }
}

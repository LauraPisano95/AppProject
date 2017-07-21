package com.example.tommy.project;

import android.app.Application;

import Jama.Matrix;
import Jama.EigenvalueDecomposition;

/**
 * Created by Tommy on 19/06/2017.
 */

public class ImageProcessing extends Application {
    //129600 = 360*360
    final int PHOTOSIZE = 129600;
    final int PHOTONUM = 4;
    //public byte[][] arrayImages = new byte[24][];
    public double[][] doubleArrayImages;
    //private byte[] meanImage = new byte[129600];
    private double[] doubleMeanImage;
    //private byte[][] phi_i = new byte[24][];
    private double[][] doubleArrayPhi_i;
    //private byte[] phi = new byte[129600];
    private double[] doublePhi;
    //private DoubleMatrix1D Ohmega;
    private double[] doubleOhmega;
    double[][] omegakDouble;
    // DoubleMatrix2D eigenfaces;
    //DoubleMatrix2D eigenVectors;
    Matrix eigenVectors;
    Matrix eigenVectorsProv;
    double[][] doubleEigenVectors;
    private int index = 0;

    //Properties for meanImage and eigenfaces
    public double[] getMeanImage(){
        return this.doubleMeanImage;
    }
    public double[][] getEigenfaces(){
        return this.omegakDouble;
    }
    //Constructor
    public ImageProcessing(){
        doubleArrayImages = new double[PHOTONUM ][];
    }

    //------------------------------TRAINING
    public void AddPhoto(double[] picture){
            doubleArrayImages[index] = picture;
            index++;
    }

    public void ComputeMeanImage() {
        doubleMeanImage = new double[PHOTOSIZE];
        for (int j=0;j<PHOTOSIZE;j++) {
            for (int i = 0; i < PHOTONUM; i++) {
                doubleMeanImage[j]  += (doubleArrayImages[i][j]);
            }
            doubleMeanImage[j] /= PHOTONUM;
        }
    }

    public void GetPhi_i() {
        doubleArrayPhi_i = new double[PHOTONUM ][PHOTOSIZE];
        for (int i = 0; i < PHOTONUM; i++) {
            for (int j = 0; j < PHOTOSIZE; j++) {
                doubleArrayPhi_i[i][j] = doubleArrayImages[i][j] - doubleMeanImage[j];
            }
        }
    }

    public void GetEigenVectors() {
        Matrix m1 = new Matrix(doubleArrayPhi_i, PHOTONUM, PHOTOSIZE);
        Matrix m2 = m1.transpose();
        Matrix p = m1.times(m2);
        eigenVectorsProv = new Matrix(PHOTONUM,PHOTONUM);
        EigenvalueDecomposition v = new EigenvalueDecomposition(p);
        eigenVectorsProv = v.getV();
        eigenVectors = new Matrix(PHOTOSIZE, PHOTONUM);
        eigenVectors = (m2.times(p)).timesEquals(1.0/PHOTONUM);
        //Conversion to double
        doubleEigenVectors = new double[PHOTOSIZE][PHOTONUM];
        doubleEigenVectors = eigenVectors.getArray();
    }

    public void GetEigenfacesTraining() {
        omegakDouble = new double[PHOTONUM][PHOTOSIZE];
        Matrix prov1 = new Matrix(doubleArrayPhi_i);
        int[] array = new int[PHOTOSIZE];
        Matrix w= new Matrix(1,1);
        for(int h=0;h<PHOTOSIZE;h++)
        {
            array[h]=h;
        }
        for(int k=0;k<PHOTONUM;k++) {
            for (int i = 0; i < PHOTONUM; i++) {
                w = eigenVectors.getMatrix(array, i, i).times(prov1.getMatrix(k, k, array).transpose());
                omegakDouble[k][i]= w.get(0,0);
            }
        }

       /*
        for (int f = 0; f < PHOTONUM; f++) {
            for (int k = 0; k < PHOTOSIZE; k++) {
                //adesso combinazione lineare tra autovettori e prov
                coeff.set(f,k, result.mult(eigenVectors.viewColumn(k), prov1.viewRow(f)));//secondo la luisa e la federica, la moltiplicazione 1dx1d traspone automaticamente il secondo vettore
            }
            omegakDouble[f] = coeff.viewRow(f).toArray();
        }*/
    }

    public void ComputeFeature(){
        ComputeMeanImage();
        GetPhi_i();
        GetEigenVectors();
        GetEigenfacesTraining();
    }
    //-------------------------------------RECOGNITION
    public void GetPhi(double[] newImage){
        doublePhi = new double[PHOTOSIZE];
        for (int j = 0; j < PHOTOSIZE; j++) {
            doublePhi[j] = newImage[j] - doubleMeanImage[j];
        }
    }

    /*public void GetOhmega(){
        matrixPhi.assign(doublePhi);
        eigenfacesTr = eigenfaces.viewDice();
        Ohmega = result.mult(eigenfacesTr, matrixPhi);//A*A

        //Conversion to double
        doubleOhmega = new double[PHOTOSIZE];
        for(int i=0;i<PHOTOSIZE;i++){
            doubleOhmega[i]= Ohmega.get(i);
        }
    }


    public boolean GetError(){
    double result;
        result = EuclideanDistance(doubleOhmega,omegakDouble);
        if(result < 0.1){
            return true;
        }
        else{
            return false;
        }
    }

    private double EuclideanDistance(double[] ohmega, double[][] ohmegak){
        double[] diff = new double[PHOTOSIZE];
        double[] x = new double[PHOTONUM];
        for(int i=0;i<PHOTONUM;i++){
            for(int j=0; j<PHOTOSIZE;j++){
                diff[j] = ohmega[j] - ohmegak[i][j];
            }
            x[i] = GetModule(diff);
        }
        return (GetMinimumError(x));
    }
    private double GetModule(double[] diff){
        double res = 0;
        for (int k = 0; k < PHOTOSIZE; k++){
            res += diff[k]*diff[k];
        }
        return (Math.sqrt(res));
    }
    private double GetMinimumError(double[] x){
        double min = x[0];
        for(int i= 1;i<PHOTOSIZE;i++){
            if(min > x[i]){
                min=x[i];
            }
        }
        return min;
    }*/
}

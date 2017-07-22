package com.example.tommy.project;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Color;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

/**
 * Created by Tommy on 19/06/2017.
 */

public class ImageProcessing extends Application {
    //129600 = 360*360
    final int PHOTOSIZE = 129600;
    final int PHOTONUM = 4;
    public double[][] doubleArrayImages;
    //private byte[] meanImage = new byte[129600];
    private double[] doubleMeanImage;
    //private byte[][] phi_i = new byte[24][];
    private double[][] doubleArrayPhi_i;
    public Matrix phi_i;
    Matrix matrixPhi;
    private double[] doublePhi;
    private Matrix Ohmega;
    private double[] doubleOhmega;
    double[][] omegakDouble;
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

    private void ComputeMeanImage() {
        doubleMeanImage = new double[PHOTOSIZE];
        for (int j=0;j<PHOTOSIZE;j++) {
            for (int i = 0; i < PHOTONUM; i++) {
                doubleMeanImage[j]  += (doubleArrayImages[i][j]);
            }
            doubleMeanImage[j] /= PHOTONUM;
        }
    }

    private void GetPhi_i() {
        doubleArrayPhi_i = new double[PHOTONUM ][PHOTOSIZE];
        for (int i = 0; i < PHOTONUM; i++) {
            for (int j = 0; j < PHOTOSIZE; j++) {
                doubleArrayPhi_i[i][j] = doubleArrayImages[i][j] - doubleMeanImage[j];
            }
        }
        Matrix phi_iprov = new Matrix (doubleArrayPhi_i);
        phi_i = phi_iprov.transpose();
    }

    private void GetEigenVectors() {
        Matrix m2 = phi_i.transpose();
        Matrix p = m2.times(phi_i).timesEquals(1.0/PHOTONUM);
        eigenVectorsProv = new Matrix(PHOTONUM,PHOTONUM);
        EigenvalueDecomposition v = new EigenvalueDecomposition(p);
        eigenVectorsProv = v.getV();
        eigenVectors = new Matrix(PHOTOSIZE, PHOTONUM);
        eigenVectors = (phi_i.times(eigenVectorsProv));
        //Conversion to double
        doubleEigenVectors = new double[PHOTOSIZE][PHOTONUM];
        doubleEigenVectors = eigenVectors.getArray();
    }

    private void GetEigenfacesTraining() {
        omegakDouble = new double[PHOTONUM][PHOTOSIZE];
        Matrix prov1 = new Matrix(doubleArrayPhi_i);
        Matrix prov2 = prov1.times(eigenVectors);
        omegakDouble = prov2.getArray();
    }

    public void ComputeFeatures(){
        ComputeMeanImage();
        GetPhi_i();
        GetEigenVectors();
        GetEigenfacesTraining();
    }

    private void ComputeTreshold(){

    }

    //-------------------------------------RECOGNITION
    public double[] GetPhi(double[] newImage){
        doublePhi = new double[PHOTOSIZE];
        for (int j = 0; j < PHOTOSIZE; j++) {
            doublePhi[j] = newImage[j] - doubleMeanImage[j];
        }
        return doublePhi;
    }

    public double[] GetOhmega(double[][] d){
        matrixPhi = new Matrix(PHOTOSIZE,1);
        for(int i=0;i<PHOTOSIZE;i++){
            matrixPhi.set(i,0, doublePhi[i]);
        }
        Matrix prov = new Matrix(1,PHOTOSIZE);
        Ohmega = new Matrix(1,PHOTONUM);
        for(int k=0; k<PHOTONUM;k++) {
            for (int h = 0; h < PHOTOSIZE; h++) {
                prov.set(0, h, d[h][k]);
            }
           Ohmega.set(0,k,prov.times(matrixPhi).get(0,0));
        }
        //Conversion to double
        doubleOhmega=new double[PHOTONUM];
        for(int n=0;n<PHOTONUM;n++){
            doubleOhmega[n] = Ohmega.get(0,n);
        }
        return doubleOhmega;
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

    public double EuclideanDistance(double[] ohmega, double[][] ohmegak){
        double[] diff = new double[PHOTOSIZE];
        double[] x = new double[PHOTONUM];
        for(int i=0;i<PHOTONUM;i++){
            for(int j=0; j<PHOTONUM;j++){
                diff[j] = ohmega[j] - ohmegak[j][i];
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
        for(int i= 1;i<PHOTONUM;i++){
            if(min > x[i]){
                min=x[i];
            }
        }
        return min;
    }

    //-------------------------------------MODIFY PHOTO
    public static double[] GreyScaleBitmapToDoubleArray(Bitmap src) {
        // pixel information
        int  G;
        int pixel;
        double[] greyPixels = new double[129600];
        int i=0;
        // get image size
        int width = src.getWidth();
        int height = src.getHeight();

        // scan through every single pixel
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = src.getPixel(x, y);
                // retrieve grey color
                G = Color.green(pixel);
                // take conversion up to one single value
                greyPixels[i]= G;
                i++;
            }
        }
        // return final image
        return greyPixels;
    }

    public static Bitmap ResizePhoto(Bitmap bMap){
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(360, 360, Bitmap.Config.ARGB_8888);
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = bMap.getWidth();
        int height = bMap.getHeight();

        // scan through every single pixel
        for(int x = 140; x < 500; x++) {
            for(int y = 0; y < height; y++) {
                // get one pixel color
                pixel = bMap.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // retrieve color of all channel
                // set new pixel color to output bitmap
                bmOut.setPixel(x-140, y, Color.argb(A,R,G,B));
            }
        }
        return bmOut;
    }

    public static Bitmap doGreyscale(Bitmap src) {
        // constant factors
        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;

        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = src.getWidth();
        int height = src.getHeight();

        // scan through every single pixel
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                // get one pixel color
                pixel = src.getPixel(x, y);
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A,R,G,B));
            }
        }
        // return final image
        return bmOut;
    }
}

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
            doubleMeanImage[j] /= 24;
        }
        /*//To byte
        for (int i=0;i<24;i++){
            meanImage[i]=(byte) doubleMeanImage[i];
        }*/
    }

    public void GetPhi_i() {
        doubleArrayPhi_i = new double[PHOTONUM ][PHOTOSIZE];
        for (int i = 0; i < PHOTONUM; i++) {
            for (int j = 0; j < PHOTOSIZE; j++) {
                doubleArrayPhi_i[i][j] = doubleArrayImages[i][j] - doubleMeanImage[j];
            }
        }
        /*//Possible problem of Underflow, if value is < 0, set 0
        for (int i = 0; i < PHOTONUM; i++) {
            for (int j = 0; j < PHOTOSIZE; j++) {
                if (doubleArrayPhi_i[i][j] < 0) {
                    doubleArrayPhi_i[i][j] = 0;
                }
                phi_i[i][j] = (byte) doubleArrayPhi_i[i][j];
            }
        }
        //Conversion to double
        for (int i = 0; i < PHOTONUM; i++) {
            for (int j = 0; j < PHOTOSIZE; j++) {
                 doubleArrayImages[i][j]= (double) arrayImages[i][j];
            }
        }*/
    }

    public void GetEigenVectors() {
        Matrix m1= new Matrix(doubleArrayPhi_i, PHOTONUM, PHOTOSIZE);
        Matrix m2 = m1.transpose();
        Matrix p = new Matrix(12,PHOTOSIZE);
        p = m2.times(m1);
       /* DoubleMatrix2D matrix1 = cern.colt.matrix.DoubleFactory2D.dense.make(PHOTONUM,PHOTOSIZE);
        DoubleMatrix2D matrix2 = cern.colt.matrix.DoubleFactory2D.dense.make(PHOTONUM,PHOTOSIZE);
        DoubleMatrix2D matrix2T = cern.colt.matrix.DoubleFactory2D.dense.make(PHOTOSIZE,PHOTONUM);
        DoubleMatrix2D prov = cern.colt.matrix.DoubleFactory2D.dense.make(PHOTOSIZE,PHOTOSIZE);
        Algebra result = new Algebra();*/
        eigenVectors = new Matrix(PHOTOSIZE,PHOTOSIZE);

        /*matrix1.assign(doubleArrayPhi_i);
        matrix2.assign(doubleArrayPhi_i);
        matrix2T = matrix2.viewDice();
        prov = result.mult(matrix2T, matrix1);*/
        EigenvalueDecomposition v = new EigenvalueDecomposition(p);
        eigenVectors = v.getV();
        //Conversion to double
        doubleEigenVectors = new double[PHOTONUM][PHOTOSIZE];
        doubleEigenVectors = eigenVectors.getArray();
        /*for(int i=0;i<PHOTONUM;i++) {
            doubleEigenVectors[i] = eigenVectors..getArray().toArray();
        }*/
        //Si possono usare gli autovalori per calcolare quanti autovettori usare
    }

    /*public void ComputeEigenFaces() {
        DoubleMatrix2D prov = new DoubleMatrix2D() {
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
        eigenfaces = new DoubleMatrix2D() {
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
        DoubleMatrix2D eigenVectorsTr = new DoubleMatrix2D() {
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
        Algebra result = new Algebra();

        prov.assign(doubleArrayPhi_i);
       // eigenVectorsTr.assign(Transposition(eigenVectors));
        eigenVectorsTr = eigenVectors.viewDice();
        eigenfaces=result.mult(eigenVectorsTr, prov);
    }*/

   /* public void GetEigenfacesTraining() {
        omegakDouble = new double[PHOTONUM][PHOTOSIZE];
        DoubleMatrix2D prov1 = cern.colt.matrix.DoubleFactory2D.dense.make(PHOTONUM, PHOTOSIZE);
        prov1.assign(doubleArrayPhi_i);
        DoubleMatrix2D coeff = cern.colt.matrix.DoubleFactory2D.dense.make(PHOTONUM, PHOTOSIZE);
        Algebra result = new Algebra();
        for (int f = 0; f < PHOTONUM; f++) {
            for (int k = 0; k < PHOTOSIZE; k++) {
                //adesso combinazione lineare tra autovettori e prov
                coeff.set(f,k, result.mult(eigenVectors.viewColumn(k), prov1.viewRow(f)));//secondo la luisa e la federica, la moltiplicazione 1dx1d traspone automaticamente il secondo vettore
            }
            omegakDouble[f] = coeff.viewRow(f).toArray();
        }
    }*/

    public void ComputeFeature(){
        ComputeMeanImage();
        GetPhi_i();
        GetEigenVectors();
       // GetEigenfacesTraining();
    }
    //-------------------------------------RECOGNITION
    public void GetPhi(double[] newImage){
        doublePhi = new double[PHOTOSIZE];
        for (int j = 0; j < PHOTOSIZE; j++) {
            doublePhi[j] = newImage[j] - doubleMeanImage[j];
        }
        /*//Conversion to byte
        for (int j = 0; j < PHOTOSIZE; j++) {
            if (doublePhi[j] < 0) {
                doublePhi[j] = 0;
            }
            phi[j] = (byte) doublePhi[j];
        }*/
    }

    /*public void GetOhmega(){
        DoubleMatrix2D eigenfacesTr = new DoubleMatrix2D() {
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
        Algebra result = new Algebra();
        DoubleMatrix1D matrixPhi = new DoubleMatrix1D() {
            @Override
            public double getQuick(int i) {
                return 0;
            }

            @Override
            public DoubleMatrix1D like(int i) {
                return null;
            }

            @Override
            public DoubleMatrix2D like2D(int i, int i1) {
                return null;
            }

            @Override
            public void setQuick(int i, double v) {

            }

            @Override
            protected DoubleMatrix1D viewSelectionLike(int[] ints) {
                return null;
            }
        };
        Ohmega=new DoubleMatrix1D() {
            @Override
            public double getQuick(int i) {
                return 0;
            }

            @Override
            public DoubleMatrix1D like(int i) {
                return null;
            }

            @Override
            public DoubleMatrix2D like2D(int i, int i1) {
                return null;
            }

            @Override
            public void setQuick(int i, double v) {

            }

            @Override
            protected DoubleMatrix1D viewSelectionLike(int[] ints) {
                return null;
            }
        };
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

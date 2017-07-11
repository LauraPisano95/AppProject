package com.example.tommy.project;

import android.app.Application;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.EigenvalueDecomposition;

/**
 * Created by Tommy on 19/06/2017.
 */

public class ImageProcessing extends Application {
    //129600 = 360*360
    private byte[][] arrayImages = new byte[24][];
    private double[][] doubleArrayImages = new double[24][];
    private byte[] meanImage = new byte[129600];
    double[] doubleMeanImage;
    private byte[][] phi_i = new byte[24][];
    private double[][] doubleArrayPhi_i = new double[24][];
    private byte[] phi = new byte[129600];
    private double[] doublePhi= new double[129600];
    DoubleMatrix1D Ohmega;
    private double[] doubleOhmega;
    private double[][] omegakDouble;
    DoubleMatrix2D eigenfaces;
    DoubleMatrix2D eigenVectors;
    double[][] doubleEigenVectors;
    private int index = 0;
    //Constructor
    public ImageProcessing(){}
    /*public ImageProcessing(byte[][] arrayImages){
        this.arrayImages=arrayImages;
    }*/

    //Properties of singleton
    //Methods
    public void AddPhoto(byte[] picture){
            arrayImages[index] = picture;
            index++;
    }

    public void GetMeanImage() {
        doubleMeanImage= new double[129600];
        for(int i=0;i<23;i++) {
            for (int j = 0; j < 129600; j++) {
                doubleMeanImage[i] = (arrayImages[i][j] + arrayImages[i + 1][j]) / 24;
            }
        }
        //To byte
        for (int i=0;i<24;i++){
            meanImage[i]=(byte) doubleMeanImage[i];
        }
    }

    public void GetPhi_i() {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 129600; j++) {
                doubleArrayPhi_i[i][j] = arrayImages[i][j] - meanImage[j];
            }
        }
        //Possible problem of Underflow, if value is < 0, set 0
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 129600; j++) {
                if (doubleArrayPhi_i[i][j] < 0) {
                    doubleArrayPhi_i[i][j] = 0;
                }
                phi_i[i][j] = (byte) doubleArrayPhi_i[i][j];
            }
        }
        //Conversion to double
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 129600; j++) {
                 doubleArrayImages[i][j]= (double) arrayImages[i][j];
            }
        }
    }

    public void GetEigenVectors() {
        DoubleMatrix2D matrix1 = new DoubleMatrix2D() {
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
        DoubleMatrix2D matrix2 = new DoubleMatrix2D() {
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
        DoubleMatrix2D matrix2T=new DoubleMatrix2D() {
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
        eigenVectors= new DoubleMatrix2D() {
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

        matrix1.assign(doubleArrayPhi_i);
        matrix2.assign(doubleArrayPhi_i);
        matrix2T = matrix2.viewDice();
        prov = result.mult(matrix2T, matrix1);
        EigenvalueDecomposition v = new EigenvalueDecomposition(prov);
        eigenVectors = v.getV();
        //Conversion to double
        doubleEigenVectors = new double[24][];
        for(int i=0;i<24;i++){
            doubleEigenVectors[i]= eigenVectors.viewColumn(i).toArray();
        }
        //Si possono usare gli autovalori per calcolare quanti autovettori usare
    }

    public void GetEigenFaces() {
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
    }

    public void GetEigenfacesTraining() {
        omegakDouble = new double[24][];
        DoubleMatrix1D prov1 = new DoubleMatrix1D() {
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
        DoubleMatrix1D coeff = new DoubleMatrix1D() {
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
        Algebra result = new Algebra();
        for (int f = 0; f < 24; f++) {
            for (int k = 0; k < 129600; k++) {
                //adesso combinazione lineare tra autovettori e prov
                coeff.set(k, result.mult(eigenVectors.viewColumn(k), prov1));//secondo la luisa e la federica, la moltiplicazione 1dx1d traspone automaticamente il secondo vettore
            }
            omegakDouble[f] = coeff.toArray();
        }
    }

    //-------------------------------------Da qua in poi serve la fase di Recognition
    public void GetPhi(byte[] newImage){
        for (int j = 0; j < 129600; j++) {
            doublePhi[j] = newImage[j] - meanImage[j];
        }
        //Conversion to byte
        for (int j = 0; j < 129600; j++) {
            if (doublePhi[j] < 0) {
                doublePhi[j] = 0;
            }
            phi[j] = (byte) doublePhi[j];
        }
    }

    public void GetOhmega(){
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
        doubleOhmega = new double[129600];
        for(int i=0;i<129600;i++){
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
        double[] diff = new double[129600];
        double[] x = new double[24];
        for(int i=0;i<24;i++){
            for(int j=0; j<129600;j++){
                diff[j] = ohmega[j] - ohmegak[i][j];
            }
            x[i] = GetModule(diff);
        }
        return (GetMinimumError(x));
    }
    private double GetModule(double[] diff){
        double res = 0;
        for (int k = 0; k < 129600; k++){
            res += diff[k]*diff[k];
        }
        return (Math.sqrt(res));
    }
    private double GetMinimumError(double[] x){
        double min = x[0];
        for(int i= 1;i<129600;i++){
            if(min > x[i]){
                min=x[i];
            }
        }
        return min;
    }
}

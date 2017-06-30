package com.example.tommy.project;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.EigenvalueDecomposition;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Tommy on 19/06/2017.
 */

public class ImageProcessing {
    //129600 = 360*360*24
    private byte[][] arrayImages = new byte[24][];
    private byte[] meanImage = new byte[129600];
    private byte[][] phi_i = new byte[24][];
    private byte[] phi = new byte[129600];
    private double[] doublePhi= new double[129600];
    private double[][] doubleArrayPhi_i= new double[24][];
    private double[][] doubleArrayImages= new double[24][];
    private double[] doubleOhmega;
    private double[][] arrayOhmega;
    DoubleMatrix2D eigenfaces;
    DoubleMatrix2D eigenVectors;
    DoubleMatrix1D Ohmega;
    private static int index = 0;

    //Constructor
    public ImageProcessing(){
    }
    public ImageProcessing(byte[][] arrayImages){
        this.arrayImages=arrayImages;
    }

    //Methods
    public void AddPhoto(byte[] picture){
        arrayImages[index]= picture;
        index++;
        if(index == 24){
            GetMeanImage();
            GetPhi_i();
            GetEigenVectors();
            GetEigenFaces();
        }
    }

    public void GetMeanImage() {
        int[] meanImageProv= new int[129600];
        for(int i=0;i<23;i++) {
            for (int j = 0; j < 129600; j++) {
                meanImageProv[i] = (arrayImages[i][j] + arrayImages[i + 1][j]) / 24;
            }
        }
        for (int i=0;i<24;i++){
            meanImage[i]=(byte) meanImageProv[i];
        }
    }

    public void GetPhi_i() {
        for (int i = 0; i < 23; i++) {
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

        EigenvalueDecomposition v = new EigenvalueDecomposition(matrix);
        matrix.assign(doubleArrayPhi_i);
        eigenVectors = v.getV();
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
    //Da rivedere trasposizione
    /*private DoubleMatrix2D Transposition(DoubleMatrix2D matrix){
        DoubleMatrix2D temp = new DoubleMatrix2D() {
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
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 129600; j++) {
                temp.set(i, j, matrix.get(j, i));
            }
        }
        return temp;
    }*/

    public void GetOhmega_i(){
        arrayOhmega = new double[24][];
        //mettere gli eigenfaces per colonne
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
        Ohmega = result.mult(eigenfacesTr, matrixPhi);

        //Conversion to double
        doubleOhmega = new double[129600];
        for(int i=0;i<129600;i++){
            doubleOhmega[i]= Ohmega.get(i);
        }
    }

    public boolean GetError(){
    double[] result = new double[0];
        for (int i=0;i<24;i++){
            result = EuclideanDistance(doubleOhmega,arrayOhmega[i]);
        }
        if(GetMinimumError(result) < 0.1){
            return true;
        }
        else{
            return false;
        }
    }

    private double[] EuclideanDistance(double[] a, double[] b){
        double diff;
        double[] x = new double[0];
        for(int i = 0; i < N; i++)
        {
            diff = a[i] - b[i];
            x[i] = Math.sqrt(diff * diff);
        }
        return x;
    }

    private double GetMinimumError(double[] x){
        double min = x[0];
        for(int i= 1;i<N;i++){
            if(min > x[i]){
                min=x[i];
            }
        }
        return min;
    }
}

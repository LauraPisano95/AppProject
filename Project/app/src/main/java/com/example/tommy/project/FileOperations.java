package com.example.tommy.project;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * Created by laura on 21/07/2017.
 */

public class FileOperations {
    public static String[] listAllFiles(String pathName){
        File file = new File(pathName);
        int i=0;
        File[] files = file.listFiles();
        String[] fileNames = new String[files.length];
        if(files != null){
            for(File f : files){ // loop and print all file
                fileNames[i] = f.getName(); // this is file name
                i++;
            }
        }
        return fileNames;
    }
    public static double[][] ReadFromFileToMatrix(Context context,int N, int M, String filename) {
        String ret = "";
        double[][] matrix = new double[N][M];
        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
                String[] s = ret.split(" ");
                int r =0;
                for(int i=0; i< N;i++){
                    for(int j=0;j<M;j++){
                        matrix[i][j] = Double.parseDouble(s[r]);
                        r++;
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        }
        catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        finally{
            return matrix;
        }
    }

    public static void WriteToFile(Context context, double[][] data, String TXTPATH, int N, int M, String filename) {
        try {
            final File file = new File(TXTPATH, filename);
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOut);
            for(int i=0;i<N;i++){
                for (int j=0;j<M;j++){
                    outputStreamWriter.write(data[i][j]+" ");
                }
            }
            outputStreamWriter.close();
            fOut.flush();
            fOut.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static  File getOutputMediaFile(String imgname){
        //Create a media file name
        File mediaFile = new File(imgname);
        return mediaFile;
    }
}

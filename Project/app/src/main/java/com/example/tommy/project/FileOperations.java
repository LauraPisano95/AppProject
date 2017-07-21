package com.example.tommy.project;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by laura on 21/07/2017.
 */

public class FileOperations {
    private static String[] listAllFiles(String pathName){
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
    private static String readFromFile(Context context,double[][] matrix) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("config2.txt");
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
                matrix = new double[4][129600];
                int r =0;
                for(int i=0; i< 4;i++){
                    for(int j=0;j<129600;j++){
                        matrix[i][j] = Double.parseDouble(s[r]);
                        r++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

}

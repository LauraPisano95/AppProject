package com.example.tommy.project;

import android.app.Application;

/**
 * Created by Tommy on 08/07/2017.
 */

public class Singleton extends Application {
    private ImageProcessing obj;
    public void setSingleton(ImageProcessing obj){
        this.obj= obj;
    }
    public ImageProcessing getSingleton() {
        return obj;
    }
}

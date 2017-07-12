package com.example.tommy.project;
/**
 * Created by Tommy on 17/05/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Calendar;

import cern.colt.matrix.linalg.EigenvalueDecomposition;
import io.vov.vitamio.MediaPlayer;

import static android.content.ContentValues.TAG;

/**

 *

 * Class allowing to get a frame from the MediaPlayer which streams the video and save it into the local storage of the device.

 */
public class PhotoSaver {
    String filename;
    String finalname;
    Bitmap image;
    Calendar rightNow;
    MediaPlayer mMediaPlayer;
    Context context;
    String imgname;
    EigenvalueDecomposition ed;
    ByteBuffer byteBuffer = ByteBuffer.allocate(518400);
    //ImageProcessing imgPr = new ImageProcessing();
    byte[] photo_train =new byte[129600];

    public PhotoSaver(Context c, MediaPlayer m, String name) {
        this.context = c ;
        this.mMediaPlayer = m ;
        imgname = null;
        rightNow = Calendar.getInstance();
        filename = name + ".jpeg";
    }
    /*public PhotoSaver( String name, ImageProcessing imgPr) {
        imgname = null;
        this.imgPr = imgPr;
        rightNow = Calendar.getInstance();
        filename = name + ".jpeg";
    }*/
    /**

     *

     * Get the current frame of the MediaPlayer and saves it in the local storage of the phone at
     the PNG format.

     */
    public void record(){
        if(Environment.getExternalStorageState() != null){
            try{
                image = mMediaPlayer.getCurrentFrame();
                File picture = getOutputMediaFile();
                FileOutputStream fos = new FileOutputStream(picture);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                Bitmap resized = Bitmap.createScaledBitmap(image,360,360,false);
                photo_train = doGreyscale(resized);
                //imgPr.AddPhoto(photo_train);
                Log.i(TAG, "findtime5");
                Toast.makeText(context, "Picture saved in :" + imgname , Toast.LENGTH_SHORT).show();
            }catch(FileNotFoundException e){
                Toast.makeText(context, "Picture file creation failed" , Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(context, "Unable to create picture file" , Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "Internal memory not available" , Toast.LENGTH_SHORT).show();
        }
    }

    /**

     *

     * Initializes the File which will be used to save the frame. Called in the record() method.
     * @return the File to be used.

     */
    private  File getOutputMediaFile(){

        rightNow = Calendar.getInstance();
        finalname = "DronePicture_" + filename;
        //Create a media file name
        File mediaFile;
        imgname = Environment.getExternalStorageDirectory()+ "/Pictures/" + finalname;
        mediaFile = new File(imgname);
        return mediaFile;
    }

    public static byte[] doGreyscale(Bitmap src) {
        // constant factors
        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;

        // create output bitmap
        //Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;
        byte[] greyPixels = new byte[129600];
        int i=0;
        int value=0;
        // get image size
        int width = src.getWidth();
        int height = src.getHeight();

        // scan through every single pixel
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = src.getPixel(x, y);
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                greyPixels[i]= (byte) G;
                i++;
            }
        }
        // return final image
        return greyPixels;
    }
}


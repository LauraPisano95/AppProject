package com.example.tommy.project;
/**
 * Created by Tommy on 17/05/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.vov.vitamio.MediaPlayer;

import static android.content.ContentValues.TAG;
import static com.example.tommy.project.FileOperations.getOutputMediaFile;
import static com.example.tommy.project.ImageProcessing.GreyScaleBitmapToDoubleArray;
import static com.example.tommy.project.ImageProcessing.ResizePhoto;
import static com.example.tommy.project.ImageProcessing.doGreyscale;

/**

 *

 * Class allowing to get a frame from the MediaPlayer which streams the video and save it into the local storage of the device.

 */
public class PhotoSaver {
    String filename;
    Bitmap image;
    MediaPlayer mMediaPlayer;
    Context context;
    String imgname;
    double[] train_photo = new double[129600];
    final String PATH = Environment.getExternalStorageDirectory()+"/"+"Pictures";

    public PhotoSaver(Context c, MediaPlayer m, String name) {
        this.context = c ;
        this.mMediaPlayer = m ;
        filename = "DronePicture_" + name + ".jpeg";
        imgname = Environment.getExternalStorageDirectory()+ "/Pictures/" + filename;
    }
    /**

     *

     * Get the current frame of the MediaPlayer and saves it in the local storage of the phone at
     the JPEG format.

     */
    public void record(){
        if(Environment.getExternalStorageState() != null){
            try{
                image = mMediaPlayer.getCurrentFrame();
                Log.i(TAG, "doGreyScale");
                File picture = getOutputMediaFile(imgname);
                FileOutputStream fos = new FileOutputStream(picture);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                File file = new File(PATH);
                train_photo = GreyScaleBitmapToDoubleArray(ResizePhoto(doGreyscale(BitmapFactory.decodeFile(file+"/"+filename))));
                Toast.makeText(context, "Picture saved in :" + imgname , Toast.LENGTH_SHORT).show();
            }catch(FileNotFoundException e){
                Toast.makeText(context, "Picture file creation failed" , Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(context, "Unable to create picture file" , Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(context, "Internal memory not available" , Toast.LENGTH_SHORT).show();
        }
    }
}


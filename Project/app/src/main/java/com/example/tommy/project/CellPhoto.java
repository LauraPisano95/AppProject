package com.example.tommy.project;

/**
 * Created by Tommy on 01/07/2017.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static com.example.tommy.project.PhotoSaver.doGreyscale;

public class CellPhoto extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private byte[][] array = new byte[24][];
    private static int i = 0;
    private int count =0;
    ByteBuffer byteBuffer = ByteBuffer.allocate(518400);
    //Create the processing class
    ImageProcessing imgPr = new ImageProcessing();
    private String[] names = new String[24];
    Context context;
    String imgname;
    String filename;
    String finalname;
    String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(i!=24) {
            //Take name from the previous activity
            Intent myIntent = getIntent();
            name = myIntent.getStringExtra("name");
            filename = name + ".jpeg";
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

        }
        else{
            Toast.makeText(getApplicationContext(), "Massimo range di foto raggiunto!!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap colorPhoto = (Bitmap) data.getExtras().get("data");
            //capturePhoto(null);
            PreparePhoto(colorPhoto);
        }
    }

    private void PreparePhoto(Bitmap colorPhoto) {
        if(Environment.getExternalStorageState() != null) {
            try {
                File picture = getOutputMediaFile();
                FileOutputStream fos = new FileOutputStream(picture);
                colorPhoto.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Bitmap photo = doGreyscale(colorPhoto);
                Bitmap resized = Bitmap.createScaledBitmap(photo, 360, 360, false);
                resized.copyPixelsToBuffer(byteBuffer);
                imgPr.AddPhoto(byteBuffer.array());
                Toast.makeText(context, "Picture saved in :" + imgname, Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(context, "Picture file creation failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(context, "Unable to create picture file", Toast.LENGTH_SHORT).show();
            }
        }
        else{
                Toast.makeText(context, "Internal memory not available", Toast.LENGTH_SHORT).show();
            }
    }
    private  File getOutputMediaFile(){
        finalname = "DronePicture_" + filename;
        //Create a media file name
        File mediaFile;
        imgname = Environment.getExternalStorageDirectory()+ "/Pictures/" + finalname;
        mediaFile = new File(imgname);
        return mediaFile;
    }
}

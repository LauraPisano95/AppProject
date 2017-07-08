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

import static com.example.tommy.project.PhotoSaver.doGreyscale;

public class CellPhoto extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static int i = 0;
    byte[] train_photo = new byte[129600];
    private String[] names = new String[24];
    Context context;
    String imgname;
    String filename;
    String finalname;
    String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_cell);
        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        if(i!=3) {
            //Take name from the previous activity
            Intent i_4 = getIntent();
            name = i_4.getStringExtra("name");
            names[i]=name;
            i++;
            filename = name + ".jpeg";
            context = getApplicationContext();
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
            PreparePhoto(colorPhoto);
        }
    }

    private void PreparePhoto(Bitmap colorPhoto) {
        if(Environment.getExternalStorageState() != null) {
            try {
                Bitmap resized = Bitmap.createScaledBitmap(colorPhoto, 360, 360, false);
                train_photo = doGreyscale(resized);
                File picture = getOutputMediaFile();
                FileOutputStream fos = new FileOutputStream(picture);
                resized.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                Toast.makeText(context, "Picture saved in :" + imgname, Toast.LENGTH_SHORT).show();
                finishActivity();
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
    private void finishActivity() {
        Intent i_5 = new Intent(getApplicationContext(), TrainingName.class);
        i_5.putExtra("photo", train_photo);
        startActivity(i_5);
        finish();
    }
}
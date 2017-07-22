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

import static com.example.tommy.project.FileOperations.getOutputMediaFile;

public class CellPhoto extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static int i = 0;
    double[] train_photo;
    Bitmap colorPhoto;
    Bitmap greyphoto;
    private String[] names = new String[24];
    Context context;
    String imgname;
    String filename;
    String finalname;
    String name;
    final String PATH = Environment.getExternalStorageDirectory()+"/"+"Pictures";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_cell);
        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        train_photo = null;//It return null value with finishActivity()
        if(i!=3) {
            //Take name from the previous activity
            Intent i_4 = getIntent();
            name = i_4.getStringExtra("name");
            names[i]=name;
            filename = "/" + name + ".jpeg";
            context = getApplicationContext();
            imgname = PATH + filename;
            train_photo=new double[70];
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
            colorPhoto = (Bitmap) data.getExtras().get("data");
            PreparePhoto(colorPhoto);
        }
    }

    private void PreparePhoto(Bitmap colorPhoto) {
        if(Environment.getExternalStorageState() != null) {
            try {
                File picture = getOutputMediaFile(imgname);
                FileOutputStream fos = new FileOutputStream(picture);
                colorPhoto.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                //File file = new File(PATH);
                //train_photo = GreyScaleBitmapToDoubleArray(ResizePhoto(doGreyscale(BitmapFactory.decodeFile(file+"/"+filename))));
                train_photo[i] +=1;
                i++;
                Toast.makeText(context, "Picture saved in :" + imgname, Toast.LENGTH_SHORT).show();
                FinishActivity();
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
    private void FinishActivity() {
        //Return the photo
        Intent i_7 = new Intent(getApplicationContext(), TrainingName.class);
        i_7.putExtra("photo", train_photo);
        setResult(RESULT_OK, i_7);
        finish();
    }
    @Override
    public void onBackPressed() {
        FinishActivity();
    }
}
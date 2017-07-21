package com.example.laura.fototelefono;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.laura.fototelefono.R.layout.photo;

public class Photo extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private byte[][] array= new byte[9][];
    private static int i=0;
    final int Pixelrow = 360;
    Bitmap babab;
    String finalname;
    String filename;
    String imgname;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(photo);
        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if(Environment.getExternalStorageState() != null) {
                try {
                    Bitmap colorPhoto = (Bitmap) data.getExtras().get("data");
                    babab = doGreyscale(colorPhoto);
                    //Bitmap resized = ResizePhoto(babab);
                    File picture = getOutputMediaFile();
                    FileOutputStream fos = new FileOutputStream(picture);
                    colorPhoto.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    babab.getDensity();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Picture file creation failed", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Unable to create picture file", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Internal memory not available", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static Bitmap doGreyscale(Bitmap src) {
        // constant factors
        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;

        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;

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
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }
    private Bitmap ResizePhoto(Bitmap bMap){
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(360, 360, bMap.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = bMap.getWidth();
        int height = bMap.getHeight();

        // scan through every single pixel
        for(int x = 140; x < 500; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = bMap.getPixel(x, y);
                // retrieve color of all channel
                // set new pixel color to output bitmap
                bmOut.setPixel(x-140, y, pixel);
            }
        }
        return bmOut;
    }
    private  File getOutputMediaFile(){
        filename = "d.jpeg";
        finalname = "DronePicture_" + filename;
        //Create a media file name
        File mediaFile;
        imgname = Environment.getExternalStorageDirectory()+ "/Pictures/" + finalname;
        mediaFile = new File(imgname);
        return mediaFile;
    }
}


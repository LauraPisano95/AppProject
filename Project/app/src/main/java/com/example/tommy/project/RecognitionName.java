package com.example.tommy.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

/**
 * Created by Tommy on 30/06/2017.
 */

public class RecognitionName extends AppCompatActivity {
    TextView tvPrint;
    ImageView ivPhoto;
    ImageView ivSimilar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recognition);
        String _data = null;
        tvPrint = (TextView)findViewById(R.id.tvPrint);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        ivSimilar = (ImageView) findViewById(R.id.ivPhoto);
        Bitmap bitmap=null;
        Intent myIntent = getIntent();
        boolean r = myIntent.getBooleanExtra("success",false);
        try {
            bitmap = BitmapFactory.decodeStream(openFileInput("myImage"));
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bmp2=null;
        if(r){
            _data = "nome e cognome";
            //Trovare foto dal training set
        }
        else{
            _data = "Riconoscimento non riuscito";
            bmp2= null;
        }
        ivPhoto.setImageBitmap(bitmap);
        ivSimilar.setImageBitmap(bmp2);
        tvPrint.setText("Dati: " + _data);
    }
}

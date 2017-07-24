package com.example.tommy.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import static com.example.tommy.project.FileOperations.listAllFiles;
import static com.example.tommy.project.ImageProcessing.GreyScaleBitmapToDoubleArray;
import static com.example.tommy.project.ImageProcessing.ResizePhoto;
import static com.example.tommy.project.ImageProcessing.doGreyscale;
import static com.example.tommy.project.TrainingName.imgPr;

public class MainActivity extends AppCompatActivity {
    final String PATH = Environment.getExternalStorageDirectory()+"/"+"Pictures";
    Button btt_t;//Training button
    Button btt_r;//Recognition button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btt_t = (Button) findViewById(R.id.bttTraining);
        btt_r = (Button) findViewById(R.id.bttRecognition);

        //Training activity
        btt_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_t = new Intent(getApplicationContext(), TrainingName.class);
                startActivity(i_t);
            }
        });

        //Recognition activity
        btt_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_r = new Intent(getApplicationContext(), Test.class);
                startActivity(i_r);
                LoadTrainingSet();
                GoToRecognitionPhase();
                Toast.makeText(getApplicationContext(), "Training set caricato!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LoadTrainingSet() {
        File file = new File(PATH);
        String[] fileNames = listAllFiles(PATH);
        for ( int i= 0; i< fileNames.length;i++){
            Bitmap bitmap = ResizePhoto(doGreyscale(BitmapFactory.decodeFile(file+"/"+fileNames[i])));
            imgPr.AddPhoto(GreyScaleBitmapToDoubleArray(bitmap));
        }
    }
    private void GoToRecognitionPhase() {
        imgPr.ComputeFeatures();
        Intent recIntent = new Intent(getApplicationContext(), RecognitionActivity.class);
        startActivity(recIntent);
    }
}

package com.example.tommy.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btt_t;//Training button
    //Button btt_r;//Recognition button
    double[][] ohmegak;
    byte[] meanImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btt_t = (Button) findViewById(R.id.bttTraining);
        //btt_r = (Button) findViewById(R.id.bttRecognition);

        //Training activity
        btt_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_t = new Intent(getApplicationContext(), TrainingName.class);
                startActivity(i_t);
            }
        });

/*        //Recognition activity
        btt_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the bundle
                Bundle bundle = getIntent().getExtras();

                //prendere le variabili
                Intent i_r = new Intent(getApplicationContext(), RecognitionActivity.class);
                startActivity(i_r);
            }
        });
*/
    }

   /* protected void onResume() {
        super.onResume();
        Intent myIntent = getIntent();
        double[] a = myIntent.getDoubleArrayExtra("doubleMeanImage");
        //double[][] b= myIntent;
    }*/
}

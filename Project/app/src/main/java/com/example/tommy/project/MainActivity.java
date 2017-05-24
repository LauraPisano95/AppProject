package com.example.tommy.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    Button btt_t;//Bottone Training
    Button btt_r;//Bottone Recognition

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btt_t = (Button) findViewById(R.id.bttTraining);
        btt_r = (Button) findViewById(R.id.bttRecognition);

        //Avvio Activity di Training
        btt_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_t = new Intent(getApplicationContext(), TrainingActivity.class);
                startActivity(i_t);
            }
        });

        //Avvio Activity di Recognition
        btt_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_r = new Intent(getApplicationContext(), RecognitionActivity.class);
                startActivity(i_r);
            }
        });
    }
}

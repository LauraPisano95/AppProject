package com.example.tommy.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Tommy on 30/06/2017.
 */

public class RecognitionName extends AppCompatActivity {
    TextView tvPrint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean r = false;
        String _data = null;

        tvPrint = (TextView)findViewById(R.id.tvPrint);
        if(r){
            _data = "nome e cognome";
        }
        else{
            _data = "Riconoscimento non riuscito";
        }
        tvPrint.setText("Dati: " + _data);
    }
}

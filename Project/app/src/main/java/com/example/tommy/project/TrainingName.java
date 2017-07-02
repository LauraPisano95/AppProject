package com.example.tommy.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Tommy on 19/06/2017.
 */

public class TrainingName extends AppCompatActivity {
    Button btt_c;//Confirm
    EditText etName;
    //Button btt_l;//Load existing training set
    Button btt_p;//Phone camera
    String _name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_name);
        btt_c = (Button) findViewById(R.id.bttConf);
        etName = (EditText)findViewById(R.id.etName);
        //btt_l=(Button) findViewById(R.id.bttLoad);
        btt_p =(Button) findViewById(R.id.bttCell);

        btt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _name = etName.getText().toString();
                if(_name.equals("")){
                    Toast.makeText(getApplicationContext(), "Inserire nome e cognome", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i_1 = new Intent(getApplicationContext(), PhotoHandler.class);
                    i_1.putExtra("name", _name);
                    startActivity(i_1);
                }
            }
        });

        btt_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _name = etName.getText().toString();
                if(_name.equals("")){
                    Toast.makeText(getApplicationContext(), "Inserire nome e cognome", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i_3 = new Intent(getApplicationContext(), CellPhoto.class);
                    i_3.putExtra("name", _name);
                    startActivity(i_3);
                }
            }
        });

//        btt_l.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                costruire la classe ImageProcessing con training set e lanciare i metodi
//
//            }
//        });

    }
}

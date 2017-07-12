package com.example.tommy.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    Button btt_l;//Load existing training set
    Button btt_p;//Phone camera
    String _name;//Data of the subject
    static ImageProcessing imgPr;
    byte[] photo;
    private static final String TAG = "TrainingName_Activity";
    private static boolean b = false;
    private static int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_name);
        btt_c = (Button) findViewById(R.id.bttConf);
        etName = (EditText)findViewById(R.id.etName);
        btt_l = (Button) findViewById(R.id.bttLoad);
        btt_p = (Button) findViewById(R.id.bttCell);
        Log.i(TAG, "onCreate: ");
        //ImageProcessing app = (ImageProcessing) getApplicationContext();
        //photo_training = app.getArrayImages();

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

        btt_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter==3){
                    imgPr.GetMeanImage();
                    Log.i(TAG, "MeanImage: ");
                }
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(b) {
            Log.i(TAG, "onResume: ");
            Intent myIntent = getIntent();
            photo = myIntent.getByteArrayExtra("photo");
            if(photo != null){
                imgPr.AddPhoto(photo);
                counter++;
                Log.i(TAG, "Aggiunta foto: ");
            }
        }
        else{
            b = true;
            imgPr = new ImageProcessing();
        }
    }
}

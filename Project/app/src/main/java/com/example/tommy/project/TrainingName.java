package com.example.tommy.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Tommy on 19/06/2017.
 */

public class TrainingName extends AppCompatActivity {
    Button btt_c;//Bottone di conferma
    EditText etName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_name);
        btt_c = (Button) findViewById(R.id.bttConf);
        etName = (EditText)findViewById(R.id.etName);
        btt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name= etName.getText().toString();
                Intent i_1 = new Intent(getApplicationContext(), PhotoHandler.class);
                i_1.putExtra("name", _name);
                startActivity(i_1);
            }
        });
    }
}

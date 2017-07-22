package com.example.tommy.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import static com.example.tommy.project.FileOperations.listAllFiles;
import static com.example.tommy.project.ImageProcessing.GreyScaleBitmapToDoubleArray;
import static com.example.tommy.project.ImageProcessing.ResizePhoto;
import static com.example.tommy.project.ImageProcessing.doGreyscale;

/**
 * Created by Tommy on 19/06/2017.
 */

public class TrainingName extends AppCompatActivity {
    Button btt_c;//Confirm
    EditText etName;
    Button btt_l;//Load existing training set
    //Button btt_p;//Phone camera
    String _name;//Data of the subject
    static ImageProcessing imgPr= new ImageProcessing();;
    double[] photo;
    private static final String TAG = "TrainingName_Activity";
    final String PATH = Environment.getExternalStorageDirectory()+"/"+"Pictures";
    final String TXTPATH = Environment.getExternalStorageDirectory()+"/"+"Project"+"/"+"Data";
    private static int counter = 0;
    private static boolean b = false;
    Bitmap[] bMapArray;
    Bitmap bitmap;
    //ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_name);
        btt_c = (Button) findViewById(R.id.bttConf);
        etName = (EditText)findViewById(R.id.etName);
        btt_l = (Button) findViewById(R.id.bttLoad);
        //btt_p = (Button) findViewById(R.id.bttCell);
        Log.i(TAG, "onCreate: ");
        //iv = (ImageView) this.findViewById(R.id.imageView1);

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

/*       btt_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _name = etName.getText().toString();
                if(_name.equals("")){
                    Toast.makeText(getApplicationContext(), "Inserire nome e cognome", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i_3 = new Intent(getApplicationContext(), CellPhoto.class);
                    i_3.putExtra("name", _name);
                    startActivityForResult(i_3,1);
                }
            }
        });
*/

        btt_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(PATH);
                String[] fileNames = listAllFiles(PATH);
                bMapArray = new Bitmap[fileNames.length];
                for ( int i= 0; i< fileNames.length;i++){
                    bMapArray[i] = BitmapFactory.decodeFile(file+"/"+fileNames[i]);
                    bitmap = ResizePhoto(doGreyscale(bMapArray[i]));
                    imgPr.AddPhoto(GreyScaleBitmapToDoubleArray(bitmap));
                }
                GoToRecognitionPhase();
                Toast.makeText(getApplicationContext(), "Training set caricato!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Intent myIntent = getIntent();
                photo = data.getDoubleArrayExtra("photo");
                if(photo != null) {
                    imgPr.AddPhoto(photo);
                    counter++;
                }
            }
        }
    }

  /*  @Override
    protected  void  onResume(){
        super.onResume();
        if(!b){
            b = true;
            imgPr = new ImageProcessing();
        }
    }*/

    private void GoToRecognitionPhase(){
        /*Intent recIntent = new Intent(getApplicationContext(), RecognitionActivity.class);
        recIntent.putExtra("meanImage", imgPr.getMeanImage());
        recIntent.putExtra("ohmegak", imgPr.getEigenfaces());
        startActivity(recIntent);*/
        imgPr.ComputeFeatures();
        //WriteToFile(getApplicationContext(), imgPr.omegakDouble, TXTPATH, 4, 4, "omegak.txt");
        //WriteToFile(getApplicationContext(), imgPr.doubleEigenVectors, TXTPATH, 129600, 4, "eigenvectors.txt");
    }
}

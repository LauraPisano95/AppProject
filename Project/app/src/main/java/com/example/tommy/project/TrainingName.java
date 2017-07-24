package com.example.tommy.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Tommy on 19/06/2017.
 */

public class TrainingName extends AppCompatActivity {
    TextView tv;
    EditText etName;
    EditText etNum;
    Button btt_Conf;//Confirm
    Button btt_Feat;//Compute features
    Button btt_Rec;//Go to recognition
    //Button btt_Load;//Load existing training set
    //Button btt_Phone;//Phone camera
    String _name;//Data of the subject
    static ImageProcessing imgPr = new ImageProcessing();
    double[] photo;
    private static final String TAG = "TrainingName_Activity";
    final String PATH = Environment.getExternalStorageDirectory()+"/"+"Pictures";
    final String TXTPATH = Environment.getExternalStorageDirectory()+"/"+"Project"+"/"+"Data";
    private static int counter = 0;
    Bitmap bitmap;
    //ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_name);

        tv = (TextView)findViewById(R.id.etNum);
        etNum =(EditText)findViewById(R.id.etNum);
        etName = (EditText)findViewById(R.id.etName);
        btt_Feat = (Button) findViewById(R.id.bttFeat);
        btt_Rec = (Button) findViewById(R.id.bttRec);
        btt_Conf = (Button) findViewById(R.id.bttConf);
        //btt_Load = (Button) findViewById(R.id.bttLoad);
        //btt_Phone = (Button) findViewById(R.id.bttCell);
        Log.i(TAG, "onCreate: ");
        //iv = (ImageView) this.findViewById(R.id.imageView1);

        btt_Conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _name = etName.getText().toString();
                if(_name.equals("")){
                    Toast.makeText(getApplicationContext(), "Inserire nome e cognome", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(imgPr.getIndex()==0){
                        imgPr.setPhotonum(Integer.parseInt(etNum.getText().toString()));
                    }
                    Intent i_1 = new Intent(getApplicationContext(), PhotoHandler.class);
                    i_1.putExtra("name", _name);
                    startActivityForResult(i_1, 1);
                }
            }
        });

        btt_Feat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgPr.getIndex()!=0){
                    imgPr.ComputeFeatures();
                    Toast.makeText(getApplicationContext(), "Training set caricato!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Training set vuoto!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btt_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToRecognitionPhase();
            }
        });
/*
        btt_Load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(PATH);
                String[] fileNames = listAllFiles(PATH);
                for ( int i= 0; i< fileNames.length;i++){
                    bitmap = ResizePhoto(doGreyscale(BitmapFactory.decodeFile(file+"/"+fileNames[i])));
                    imgPr.AddPhoto(GreyScaleBitmapToDoubleArray(bitmap));
                }
                bitmap.recycle();
                bitmap=null;
                GoToRecognitionPhase();
                Toast.makeText(getApplicationContext(), "Training set caricato!!", Toast.LENGTH_SHORT).show();
            }
        });

        btt_Phone.setOnClickListener(new View.OnClickListener() {
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
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        etNum.setFocusable(false);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                photo = data.getDoubleArrayExtra("photo");
                if(photo != null) {
                    imgPr.AddPhoto(photo);
                    counter++;
                }
            }
        }
        //photo=null;
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
        Intent recIntent = new Intent(getApplicationContext(), RecognitionActivity.class);
        startActivity(recIntent);
        //WriteToFile(getApplicationContext(), imgPr.omegakDouble, TXTPATH, 4, 4, "omegak.txt");
        //WriteToFile(getApplicationContext(), imgPr.doubleEigenVectors, TXTPATH, 129600, 4, "eigenvectors.txt");
    }
}

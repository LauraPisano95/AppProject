package com.example.tommy.project;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.example.tommy.project.PhotoSaver.GreyScaleBitmapToDoubleArray;
import static com.example.tommy.project.PhotoSaver.ResizePhoto;
import static com.example.tommy.project.PhotoSaver.doGreyscale;

/**
 * Created by Tommy on 19/06/2017.
 */

public class TrainingName extends AppCompatActivity {
    Button btt_c;//Confirm
    EditText etName;
    Button btt_l;//Load existing training set
    //Button btt_p;//Phone camera
    String _name;//Data of the subject
    static ImageProcessing imgPr;
    double[] photo;
    private static final String TAG = "TrainingName_Activity";
    final String PATH = Environment.getExternalStorageDirectory()+"/"+"Pictures";
    final String TXTPATH = Environment.getExternalStorageDirectory()+"/"+"Download";
    private static boolean b = false;
    private static int counter = 0;
    Bitmap[] bMapArray;
    Bitmap bitmap;
    ImageView iv;
    final int N =4;
    final int M = 129600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_name);
        btt_c = (Button) findViewById(R.id.bttConf);
        etName = (EditText)findViewById(R.id.etName);
        btt_l = (Button) findViewById(R.id.bttLoad);
        //btt_p = (Button) findViewById(R.id.bttCell);
        Log.i(TAG, "onCreate: ");
        iv = (ImageView) this.findViewById(R.id.imageView1);

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

/*        btt_p.setOnClickListener(new View.OnClickListener() {
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
                //iv.setImageBitmap(ResizePhoto(doGreyscale(bMap[0])));
                GoToRecognitionPhase();
                Toast.makeText(getApplicationContext(), "Training set caricato!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(b) {
            /*Log.i(TAG, "onResume: ");
            Intent myIntent = getIntent();
            photo = myIntent.getDoubleArrayExtra("photo");
            if(photo != null){
                imgPr.AddPhoto(photo);
                counter++;
            }*/
        }
        else{
            b = true;
            imgPr = new ImageProcessing();
        }
    }
    private static String[] listAllFiles(String pathName){
        File file = new File(pathName);
        int i=0;
        File[] files = file.listFiles();
        String[] fileNames = new String[files.length];
        if(files != null){
            for(File f : files){ // loop and print all file
                fileNames[i] = f.getName(); // this is file name
                i++;
            }
        }
        return fileNames;
    }
    private void GoToRecognitionPhase(){
        /*Intent recIntent = new Intent(getApplicationContext(), RecognitionActivity.class);
        recIntent.putExtra("meanImage", imgPr.getMeanImage());
        recIntent.putExtra("ohmegak", imgPr.getEigenfaces());
        startActivity(recIntent);*/
        imgPr.ComputeFeature();
        //writeToFile(getApplicationContext(), imgPr.doubleArrayImages);
    }
    private void writeToFile(Context context, double[][] data) {
        try {
            final File file = new File(TXTPATH, "config.txt");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOut);
            for(int i=0;i<N;i++){
                for (int j=0;j<M;j++){
                    outputStreamWriter.write(data[i][j]+" ");
                }
            }
            outputStreamWriter.close();
            fOut.flush();
            fOut.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

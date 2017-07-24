package com.example.tommy.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.tommy.project.ImageProcessing.GreyScaleBitmapToDoubleArray;
import static com.example.tommy.project.ImageProcessing.ResizePhoto;
import static com.example.tommy.project.ImageProcessing.doGreyscale;
import static com.example.tommy.project.TrainingName.imgPr;

/**
 * Created by Tommy on 22/07/2017.
 */

public class Test extends AppCompatActivity {
    final String RECPATH = Environment.getExternalStorageDirectory()+"/"+"Project"+"/"+"Recognition";
    final String TXTPATH = Environment.getExternalStorageDirectory()+"/"+"Project"+"/"+"Data";
    Button b;
    TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        b = (Button) findViewById(R.id.bttllll);
        tv =(TextView) findViewById(R.id.tv);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                double[] phi = imgPr.GetPhi(GetPhoto());
                //double[][] ohmegakDouble = ReadFromFileToMatrix(context, 4, 4, TXTPATH + "omegak.txt");
                //double[][] eigenVectors = ReadFromFileToMatrix(context, 129600, 4, TXTPATH + "eigenvectors.txt");
                double[] a = imgPr.GetOhmega(imgPr.doubleEigenVectors);
                int v = imgPr.EuclideanDistance(a,imgPr.omegakDouble);
                tv.setText(String.valueOf(v));
            }
        });
    }
    private double[] GetPhoto(){
        //MediaPlayer mMediaPlayer = mVideoView.getMediaPlayer();
        //Bitmap colorPhoto =  mMediaPlayer.getCurrentFrame();
        String imgname = RECPATH + "/"+"nn.jpeg";
        /*File picture = getOutputMediaFile(imgname);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(picture);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        colorPhoto.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Bitmap b1 = BitmapFactory.decodeFile(imgname);
        Bitmap b2 = doGreyscale(b1);
        double[] doubleArrayPhoto = GreyScaleBitmapToDoubleArray(ResizePhoto(b2));
        return doubleArrayPhoto;
    }
}

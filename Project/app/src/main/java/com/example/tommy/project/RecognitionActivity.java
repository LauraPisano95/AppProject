package com.example.tommy.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

import static com.example.tommy.project.PhotoSaver.GreyScaleBitmapToDoubleArray;

/**
 * Created by Tommy on 10/05/2017.
 */

public class RecognitionActivity extends AppCompatActivity {
    private Context context = null;
//    private DroneManager droneManager = null;
    private VideoView mVideoView;
    private final String PATH = "tcp://192.168.1.1:5555/";
//    public final String CommandeDepart = "COMMANDE_INUTILE";
//    public final int iPort = 5556;
//    public final String AdresseDrone = "192.168.1.1";
//    private TakePictureButtonView myTakePicBtt = null;
    private Button bttCapturePic = null;

    private static final String TAG = "RecognitionActivity";
    double[] doubleArrayPhoto = new double[129600];
    double[] recPhoto = new double[129600];
    double[][] ohmegak;
    double[][] eigenvectors;
    double[] meanImage;
    private byte[][] array= null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!LibsChecker.checkVitamioLibs(this))
            return;

        setContentView(R.layout.photo_handler);

        context = getApplicationContext();

//        droneManager = new DroneManager(CommandeDepart, AdresseDrone, iPort);

//        final Thread thread = new Thread() {
//            @Override
//            public void run() {
//
//                droneManager.sendUDPTrame("AT*FTRIM=1\rAT*REF=2,290718208"); // TRIM + DECOLLAGE
//                Log.i(TAG, "Send AT*CONFIG command");
//
//                droneManager.sendUDPTrame("AT*PCMD=2" + ",0,0,0,0,0"); 			// RESET WATCHDOG pour eviter le TimeOut
//
//
//            }
//        };
//
//        thread.start();

//        AT*CONFIG=%d,%s,%s<CR>
//        H264_720P_CODEC


        mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);

        //myTakePicBtt = (TakePictureButtonView) findViewById(R.id.joystickView02);
        bttCapturePic = (Button) findViewById(R.id.bttCapturePicture);

        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        mVideoView.setBufferSize(4096);
        mVideoView.setVideoPath(PATH);
        mVideoView.requestFocus();
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);


        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });


        bttCapturePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "setOnClickListener");
                recPhoto = GetPhoto();
                //prendere le variabili
                Intent i = getIntent();
                meanImage = i.getDoubleArrayExtra("meanImage");
               //ohmegak=i.getDoubleArrayExtra("ohmegak");

            }
        });
    }

    private double[] GetPhoto(){
        MediaPlayer mMediaPlayer = mVideoView.getMediaPlayer();
        Bitmap colorPhoto =  mMediaPlayer.getCurrentFrame();
        Bitmap resized = Bitmap.createScaledBitmap(colorPhoto,360,360,false);
        doubleArrayPhoto = GreyScaleBitmapToDoubleArray(resized);
        return doubleArrayPhoto;
    }

    public void GetEigenNewFace() {
        double[][] prov = new double[24][];
        for (int i = 0; i < 129600; i++) {
            //prov[i] = recPhoto[i] - meanImage[i] ;
        }
        double[] omegakDouble = new double[24];
       /*
        for (int k = 0; k < 24; k++) {
            prov1.assign(prov[k]);
            //adesso combinazione lineare tra autovettori e prov
           // coeff.set(k, result.mult(eigenVectorRow=eigenVectors.viewRow(k), prov1));//controllare se eigenvector e prov sono giuste in modo che il risultato sia 1x1, fatti passare eigenvectors
        }
        omegakDouble = coeff.toArray();//omega immagine nuova da riconoscere
    */
    }

    /*private double EuclideanDistance(double[] ohmega, double[][] ohmegak){
        double[] diff = new double[129600];
        double[] x = new double[24];
        for(int i=0;i<24;i++){
            for(int j=0; j<129600;j++){
                diff[j] = ohmega[j] - ohmegak[i][j];
            }
            x[i] = GetModule(diff);
        }
        return (GetMinimumError(x));
    }
    private double GetModule(double[] diff){
        double res = 0;
        for (int k = 0; k < 129600; k++){
            res += diff[k]*diff[k];
        }
        return (Math.sqrt(res));
    }
    private double GetMinimumError(double[] x){
        double min = x[0];
        for(int i= 1;i<129600;i++){
            if(min > x[i]){
                min=x[i];
            }
        }
        return min;
    }*/
    private static String readFromFile(Context context,double[][] matrix) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("config2.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
                String[] s = ret.split(" ");
                matrix = new double[4][129600];
                int r =0;
                for(int i=0; i< 4;i++){
                    for(int j=0;j<129600;j++){
                        matrix[i][j] = Double.parseDouble(s[r]);
                        r++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

}
package com.example.tommy.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

import static com.example.tommy.project.FileOperations.getOutputMediaFile;
import static com.example.tommy.project.ImageProcessing.GreyScaleBitmapToDoubleArray;
import static com.example.tommy.project.ImageProcessing.ResizePhoto;
import static com.example.tommy.project.ImageProcessing.doGreyscale;
import static com.example.tommy.project.TrainingName.imgPr;

/**
 * Created by Tommy on 10/05/2017.
 */

public class RecognitionActivity extends AppCompatActivity {
    private Context context = null;
    private VideoView mVideoView;
    private final String WIFIPATH = "tcp://192.168.1.1:5555/";
    private final String TAG = "RecognitionActivity";
    private final String RECPATH = Environment.getExternalStorageDirectory()+"/"+"Project"+"/"+"Recognition";
    private Button bttCapturePic = null;
    double[] recPhoto = new double[129600];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.photo_handler);
        context = getApplicationContext();
        mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);
        bttCapturePic = (Button) findViewById(R.id.bttCapturePicture);
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        mVideoView.setBufferSize(4096);
        mVideoView.setVideoPath(WIFIPATH);
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
                double [] phi = new ImageProcessing().GetPhi(recPhoto);
                double[] a = imgPr.GetOhmega(imgPr.doubleEigenVectors);
                int v = imgPr.EuclideanDistance(a,imgPr.omegakDouble);
            }
        });
    }

    private double[] GetPhoto(){
        MediaPlayer mMediaPlayer = mVideoView.getMediaPlayer();
        Bitmap colorPhoto =  mMediaPlayer.getCurrentFrame();
        String imgname = RECPATH + "/"+"nn.jpeg";
        File picture = getOutputMediaFile(imgname);
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
        }
        double[] doubleArrayPhoto = GreyScaleBitmapToDoubleArray(ResizePhoto(doGreyscale(BitmapFactory.decodeFile(imgname))));
        return doubleArrayPhoto;
    }
}
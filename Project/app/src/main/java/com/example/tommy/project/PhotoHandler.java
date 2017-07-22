package com.example.tommy.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


 /* Created by Tommy on 19/06/2017.
 */
public class PhotoHandler extends AppCompatActivity {
     private Context context = null;
     private static final String TAG = "PhotoHandler";
     private static int i = 0;
     private VideoView mVideoView;
     private final String PATH = "tcp://192.168.1.1:5555/";
     private String name;
     PhotoSaver phs;
     private Button bttCapturePic = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(i!=24) {
            //Vitamio settings
            if (!LibsChecker.checkVitamioLibs(this))
                return;
            setContentView(R.layout.photo_handler);
            context = getApplicationContext();
            mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);
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
                    capturePhoto(null);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Massimo range di foto raggiunto!!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void capturePhoto(View v) {
        //Take name from the previous activity
        Intent myIntent = getIntent();
        name = myIntent.getStringExtra("name");
        //Save the photo
        try {
            phs = new PhotoSaver(context, mVideoView.getMediaPlayer(), name);
            phs.record();
            Log.i(TAG,"record");
            FinishActivity();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Picture error!", Toast.LENGTH_SHORT).show();
        }
    }
    private void FinishActivity() {
        //Return the photo
        Intent i_7 = new Intent(getApplicationContext(), TrainingName.class);
        i_7.putExtra("photo", phs.train_photo);
        setResult(RESULT_OK, i_7);
        finish();
    }
}


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
     //Create the processing class
     ImageProcessing imgPr = new ImageProcessing();

     private String[] names = new String[24];
     private Context context = null;
//   private DroneManager droneManager = null;
     private static final String TAG = "MainActivity";
     private static int count=0;
     private static int i=0;
     private VideoView mVideoView;
     private final String PATH = "tcp://192.168.1.1:5555/";
     private String name;
//   public final String CommandeDepart = "COMMANDE_INUTILE";

//   public final int iPort = 5556;
//   public final String AdresseDrone = "192.168.1.1";


    // private TakePictureButtonView myTakePicBtt = null;
    private Button bttCapturePic = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(i!=24) {
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

            //Take name from the previous activity
            Intent myIntent = getIntent();
            name = myIntent.getStringExtra("name");
            names[i] = name;
            i++;
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
        count++;
        if (count == 4) {
            count = 0;
            finish();
        }
        else {
            try {
                new PhotoSaver(context, mVideoView.getMediaPlayer(), name, imgPr).record();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Picture error!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


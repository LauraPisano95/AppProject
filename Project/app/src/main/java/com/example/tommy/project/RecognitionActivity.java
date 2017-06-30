package com.example.tommy.project;

import android.content.Context;
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
/**
 * Created by Tommy on 10/05/2017.
 */

public class RecognitionActivity extends AppCompatActivity {
    private Context context = null;
//    private DroneManager droneManager = null;

    private static final String TAG = "MainActivity";

    private VideoView mVideoView;

    private final String PATH = "tcp://192.168.1.1:5555/";

//    public final String CommandeDepart = "COMMANDE_INUTILE";

//    public final int iPort = 5556;
//    public final String AdresseDrone = "192.168.1.1";


    // private TakePictureButtonView myTakePicBtt = null;
    private Button bttCapturePic = null;
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
                capturePhoto(null);
            }
        });
    }

    private void capturePhoto(View v) {
        try {
            new PhotoSaver(context, mVideoView.getMediaPlayer(),null, new ImageProcessing()).record();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Picture error!", Toast.LENGTH_SHORT).show();
        }
    }

}

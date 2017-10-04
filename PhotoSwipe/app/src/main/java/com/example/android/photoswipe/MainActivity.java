package com.example.android.photoswipe;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button mButtonPlayButton;
    Button mButtonStopButton;

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonPlayButton = (Button) findViewById(R.id.buttonPlayButton);
        mButtonPlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playMp3AtPath("wowow1.mp3");
            }
        });

        mButtonStopButton = (Button) findViewById(R.id.buttonStop);
        mButtonStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopPlayingIfPlaying();
            }
        });

    }
    private void stopPlayingIfPlaying() {
        if (player == null) {
            return;
        }

        if(player.isPlaying()) {
            player.stop();
        }
    }

    private boolean playMp3AtPath(String path) {
        try {
            stopPlayingIfPlaying();

            AssetFileDescriptor afd = getAssets().openFd(path);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            player.prepare();
            player.start();
            return  true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

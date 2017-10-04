package com.example.android.photoswipe;

import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button mButtonPlayButton;
    Button mButtonStopButton;
    ImageView mHeaderImage;
    ArrayList<String> files;

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mHeaderImage = (ImageView) findViewById(R.id.imageViewHeaderImage);
            mHeaderImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("langzone.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mButtonPlayButton = (Button) findViewById(R.id.buttonPlayButton);
        mButtonPlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playMp3AtPath("lang/wowow1.mp3");
            }
        });

        mButtonStopButton = (Button) findViewById(R.id.buttonStop);
        mButtonStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopPlayingIfPlaying();
            }
        });

        files = readAllFilesInAssets("lang");
    }

    public ArrayList<String> readAllFilesInAssets(String path) {
        String[] list;
        ArrayList<String> files = new ArrayList<String>();

        try {
            list = getAssets().list(path);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    files.add(file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    private void stopPlayingIfPlaying() {
        if (player == null) {
            return;
        }

        if (player.isPlaying()) {
            player.stop();
        }
    }

    private boolean playMp3AtPath(String path) {
        try {
            stopPlayingIfPlaying();

            AssetFileDescriptor afd = getAssets().openFd(path);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

package com.example.android.photoswipe;

import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LangRecyclerViewAdapter.ItemClickListener {

    LangRecyclerViewAdapter adapter;
    Button mButtonPlayButton;
    Button mButtonStopButton;
    ImageView mHeaderImage;
    ArrayList<String> files;
    RecyclerView recyclerView;

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
                playLangClip(files.get(0));
            }
        });

        mButtonStopButton = (Button) findViewById(R.id.buttonStop);
        mButtonStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopPlayingIfPlaying();
            }
        });

        files = readAllFilesInAssets("lang");

        mButtonPlayButton.setText(files.get(0));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSoundBoard);
        recyclerView.setLayoutManager(new GridLayoutManager(this, files.size()));
        adapter = new LangRecyclerViewAdapter(this, files);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<String> readAllFilesInAssets(String path) {
        String[] list;
        ArrayList<String> files = new ArrayList<String>();

        try {
            list = getAssets().list(path);
            if (list.length > 0) {
                for (String file : list) {
                    if(file.endsWith(".mp3")) {
                        file = file.substring(0, file.lastIndexOf(".mp3"));
                        files.add(file);
                    }
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

    private boolean playLangClip(String path) {
        try {
            stopPlayingIfPlaying();

            if(!path.startsWith("lang/")) {
                path = "lang/" + path;
            }

            if(!path.endsWith(".mp3")) {
                path = path + ".mp3";
            }

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

    @Override
    public void onItemClick(View view, String name) {
        playLangClip(name);
    }
}

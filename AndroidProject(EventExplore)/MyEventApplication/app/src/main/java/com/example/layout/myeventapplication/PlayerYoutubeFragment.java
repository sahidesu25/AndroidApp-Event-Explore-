package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/11/2015.
 */
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


public class PlayerYoutubeFragment extends YouTubePlayerSupportFragment {

    private String currentVideoID = "video_id";
   // public static  String VIDEO_ID = "uzO3upcQKR4";
    public static  String VIDEO_ID;
    public static final String API_KEY = "AIzaSyBBHbJPnTkCoJRQAY1qFXmXfHkmaIGRhwU";
    private YouTubePlayer activePlayer;

    public static PlayerYoutubeFragment newInstance(String url) {

        PlayerYoutubeFragment playerYouTubeFrag = new PlayerYoutubeFragment();

        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        playerYouTubeFrag.init();


        playerYouTubeFrag.setArguments(bundle);
        VIDEO_ID=url;

        return playerYouTubeFrag;
    }

    private void init() {

        initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) { }

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                activePlayer = player;
                activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                if (!wasRestored) {

                    activePlayer.loadVideo(VIDEO_ID, 0);

                }
            }
        });
    }


    public void onYouTubeVideoPaused() {
        activePlayer.pause();
    }
}
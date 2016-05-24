package com.example.bkakran.flicks;

import android.os.Bundle;
import android.util.Log;

import com.example.bkakran.flicks.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
        final Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        youTubePlayerView.initialize("AIzaSyAvJi2KqPFkkXXWsbQYh5gjKvdbZtOlT5c",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.loadVideo(movie.getVideoId());
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                        Log.e("VideoActivity", "Error Initializing video" + youTubeInitializationResult);
                    }

                });
    }
}

package com.silverlinesoftwares.intratips.util;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.silverlinesoftwares.intratips.R;

public class VideoActivity extends YouTubeBaseActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        MobileAds.initialize(this, getString(R.string.app_ads_id));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        StaticMethods.showInterestialAds(VideoActivity.this);

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);
       //YouTubePlayer.PlayerStyle.MINIMAL;


        youTubePlayerView.initialize("AIzaSyBjUiIOvyYabjFgLC9Ht6edhuYZB1GVFw8",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {


                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(getIntent().getStringExtra("video_id"));
                        youTubePlayer.play();
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }

                });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        MusicUtil.playMusic();
    }
}

package com.silverlinesoftwares.intratips.activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.NewsAdapterR;
import com.silverlinesoftwares.intratips.listeners.NewsListener;
import com.silverlinesoftwares.intratips.models.NewsModel;
import com.silverlinesoftwares.intratips.tasks.NewsTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewsActivity extends AppCompatActivity implements NewsListener {

    private ProgressBar progress;
    private ArrayList<NewsModel> newsModels;
    private NewsAdapterR newsAdapter;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        MobileAds.initialize(NewsActivity.this, initializationStatus -> {

        });
        final RecyclerView listView= findViewById(R.id.list_news);
        progress=findViewById(R.id.progress);
        newsModels=new ArrayList<>();
        listView.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
        listView.setHasFixedSize(true);
        newsAdapter=new NewsAdapterR(NewsActivity.this,listView,newsModels);
        listView.setAdapter(newsAdapter);
        NewsTask newsTask=new NewsTask(NewsActivity.this,getIntent().getStringExtra(Constant.data_text),0);
        newsTask.execute("ok");

        newsAdapter.setOnItemClickListener((v, obj, position) -> {
            Intent intent=new Intent(NewsActivity.this, NewWebActivity.class);
            intent.putExtra("url",newsModels.get(position).getLinks());
            startActivity(intent);

        });
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,NewsActivity.this);
        showInterestialAds();
    }


    @Override
    public void onSucess(List<NewsModel> data) {
        progress.setVisibility(View.GONE);
        newsModels.addAll(data);
        newsAdapter.notifyItemInserted(data.size());
        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(()-> runOnUiThread(this::showInterestialAdsView),5000);

    }

    private InterstitialAd mInterstitialAd;
    private void showInterestialAdsView() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }
    private void showInterestialAds() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.inter1), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }



    @Override
    public void onFailedNews(String msg) {

    }


}

package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.BulkDealAdapter;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.models.BulkModel;
import com.silverlinesoftwares.intratips.tasks.BlockDealTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockDealActivity extends AppCompatActivity implements ChartListener {

    private ListView listView;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_gainers);
        MobileAds.initialize(BlockDealActivity.this, initializationStatus -> {

        });
        progress=findViewById(R.id.progress);
        listView=findViewById(R.id.list_volume_gainer);
        BlockDealTask gainerLooserTask=new BlockDealTask(BlockDealActivity.this);
        gainerLooserTask.execute();

        showInterestialAds();
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,BlockDealActivity.this);


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

        InterstitialAd.load(this,getString(R.string.inter_r), adRequest,
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
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
        Document document= Jsoup.parse(data);
        Elements elements=document.getElementsByTag("tr");
        List<BulkModel> bulkModels=new ArrayList<>();
        for (int i=3;i<elements.size();i++){
            try {
                bulkModels.add(new BulkModel(elements.get(i).children().get(0).text(), elements.get(i).children().get(1).text(), elements.get(i).children().get(3).text(), elements.get(i).children().get(4).text(), elements.get(i).children().get(6).text(), elements.get(i).children().get(5).text()));
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        Collections.reverse(bulkModels);
        BulkDealAdapter bulkDealAdapter=new BulkDealAdapter(BlockDealActivity.this,bulkModels);
        listView.setAdapter(bulkDealAdapter);
        showInterestialAdsView();
    }

    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(BlockDealActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

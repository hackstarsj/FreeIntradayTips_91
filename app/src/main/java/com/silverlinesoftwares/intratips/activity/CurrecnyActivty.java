package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
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
import com.silverlinesoftwares.intratips.adapters.CurrencyAdapter;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.models.CurrencyModel;
import com.silverlinesoftwares.intratips.tasks.CurrencyTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CurrecnyActivty extends AppCompatActivity implements ChartListener {

    private ListView listView;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_gainers);
        MobileAds.initialize(CurrecnyActivty.this, initializationStatus -> {

        });
        progress=findViewById(R.id.progress);
        listView=findViewById(R.id.list_volume_gainer);
        CurrencyTask gainerLooserTask=new CurrencyTask(CurrecnyActivty.this);
        gainerLooserTask.execute();

        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,CurrecnyActivty.this);
        showInterestialAds();


    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
        Document document= Jsoup.parse(data);
        Elements elements=document.getElementsByTag("tr");
        List<CurrencyModel> bulkModels=new ArrayList<>();
        for (int i=1;i<elements.size();i++){
            try {
                bulkModels.add(new CurrencyModel(elements.get(i).children().get(1).text(), elements.get(i).children().get(2).text(), elements.get(i).children().get(3).children().get(0).text(), elements.get(i).children().get(4).children().get(0).text()));
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        CurrencyAdapter bulkDealAdapter=new CurrencyAdapter(CurrecnyActivty.this,bulkModels);
        listView.setAdapter(bulkDealAdapter);
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
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(CurrecnyActivty.this, msg, Toast.LENGTH_SHORT).show();
    }
}

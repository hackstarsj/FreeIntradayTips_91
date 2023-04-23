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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.HighLowAdapter;
import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;
import com.silverlinesoftwares.intratips.models.High_Low_Model;
import com.silverlinesoftwares.intratips.tasks.HighLowTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONObject;

import java.util.List;

public class HighLowActivit extends AppCompatActivity implements GainerLooserListener {

    ProgressBar progressBar;
    ListView listView;
    boolean is_high;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_low);
        MobileAds.initialize(HighLowActivit.this, initializationStatus -> {

        });
        progressBar=findViewById(R.id.progress);
        listView=findViewById(R.id.list_pre_market);
        if(getIntent().getStringExtra("data").equalsIgnoreCase("1")) {
            is_high=true;
            HighLowTask preMarketTask = new HighLowTask(HighLowActivit.this);
            preMarketTask.execute("1");

        }
        else{
            is_high=false;
            HighLowTask preMarketTask = new HighLowTask(HighLowActivit.this);
            preMarketTask.execute("0");

        }

        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,HighLowActivit.this);
        showInterestialAds();

    }

    @Override
    public void onSucess(String data) {
        progressBar.setVisibility(View.GONE);
        try {
            JSONObject jsonObject = new JSONObject(data);
            String strings = jsonObject.getString("data");
            TypeToken<List<High_Low_Model>> token = new TypeToken<List<High_Low_Model>>() {};
            Gson gson = new Gson();
            List<High_Low_Model> animals = gson.fromJson(strings, token.getType());
            HighLowAdapter preMarketOpenAdapter=new HighLowAdapter(HighLowActivit.this,animals,is_high);
            listView.setAdapter(preMarketOpenAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        Toast.makeText(HighLowActivit.this, ""+msg, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }
}

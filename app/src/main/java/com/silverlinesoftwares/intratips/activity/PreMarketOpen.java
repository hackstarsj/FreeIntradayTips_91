package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.PreMarketOpenAdapter;
import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;
import com.silverlinesoftwares.intratips.models.PreMarketOpenModel;
import com.silverlinesoftwares.intratips.tasks.PreMarketTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONObject;

import java.util.List;

public class PreMarketOpen extends AppCompatActivity implements GainerLooserListener {

    Spinner spinner;
    ProgressBar progressBar;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_market_open);
        MobileAds.initialize(PreMarketOpen.this, initializationStatus -> {

        });
        String[] selecs={"Nifty","FO Stocks","NIFTY Bank","SME","Other"};
        ArrayAdapter arrayAdapter=new ArrayAdapter(PreMarketOpen.this,android.R.layout.simple_spinner_item,selecs);
        spinner=findViewById(R.id.select_pre_market);
        progressBar=findViewById(R.id.progress);
        listView=findViewById(R.id.list_pre_market);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //requestWithSomeHttpHeaders();
                PreMarketTask preMarketTask=new PreMarketTask(PreMarketOpen.this);
                preMarketTask.execute(""+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,PreMarketOpen.this);
        showInterestialAds();

    }

    @Override
    public void onSucess(String data) {
        progressBar.setVisibility(View.GONE);
        try {
            JSONObject jsonObject = new JSONObject(data);
            String strings = jsonObject.getString("data");
            TypeToken<List<PreMarketOpenModel>> token = new TypeToken<List<PreMarketOpenModel>>() {};
            Gson gson = new Gson();
            List<PreMarketOpenModel> animals = gson.fromJson(strings, token.getType());
            PreMarketOpenAdapter preMarketOpenAdapter=new PreMarketOpenAdapter(PreMarketOpen.this,animals);
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
    public void onFailed(String msg) {
        Toast.makeText(PreMarketOpen.this, ""+msg, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

}


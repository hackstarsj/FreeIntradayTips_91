package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.ViewPagerAdapter;
import com.silverlinesoftwares.intratips.fragments.subfragment.LowerBandFragment;
import com.silverlinesoftwares.intratips.fragments.subfragment.UpperBandFragment;
import com.silverlinesoftwares.intratips.listeners.PriceBandListener;
import com.silverlinesoftwares.intratips.models.PriceBandHitterModel;
import com.silverlinesoftwares.intratips.tasks.PricebandTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class PriceBandActivity extends AppCompatActivity implements PriceBandListener {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_band);
        MobileAds.initialize(PriceBandActivity.this, initializationStatus -> {

        });
        tabLayout = findViewById(R.id.tab_menu);
        progress=findViewById(R.id.progress);
        viewPager=findViewById(R.id.mover_looser_page);
        PricebandTask gainerLooserTask=new PricebandTask(PriceBandActivity.this);
        gainerLooserTask.execute();
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,PriceBandActivity.this);

        showInterestialAds();
    }


    @Override
    public void onSucess(JSONObject data) {
        progress.setVisibility(View.GONE);
        try {


            JSONObject volume=data.getJSONObject("lower");
            JSONObject value=data.getJSONObject("upper");

            String strings=volume.getString("data");
            String string1=value.getString("data");
            TypeToken<List<PriceBandHitterModel>> token = new TypeToken<List<PriceBandHitterModel>>() {};
            Gson gson=new Gson();
            List<PriceBandHitterModel> volumes = gson.fromJson(strings, token.getType());
            List<PriceBandHitterModel> values = gson.fromJson(string1, token.getType());



            ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());

            UpperBandFragment topMoversFragment=new UpperBandFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable(Constant.data, (Serializable) values);
            topMoversFragment.setArguments(bundle);


            LowerBandFragment topLoserFragment=new LowerBandFragment();
            Bundle bundle1=new Bundle();
            bundle1.putSerializable(Constant.data, (Serializable) volumes);
            topLoserFragment.setArguments(bundle1);



            viewPagerAdapter.addFragment(topMoversFragment);
            viewPagerAdapter.addFragment(topLoserFragment);

            viewPagerAdapter.addTitle("Upper Band");
            viewPagerAdapter.addTitle("Lower Band");
            viewPager.setAdapter(viewPagerAdapter);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(viewPagerAdapter.getTitle(position))).attach();


        } catch (JSONException e) {
            Toast.makeText(PriceBandActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
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
        progress.setVisibility(View.GONE);
        Toast.makeText(PriceBandActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

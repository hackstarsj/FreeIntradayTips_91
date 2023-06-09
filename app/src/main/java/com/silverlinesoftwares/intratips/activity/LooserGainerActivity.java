package com.silverlinesoftwares.intratips.activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
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
import com.silverlinesoftwares.intratips.fragments.subfragment.TopLoserFragment;
import com.silverlinesoftwares.intratips.fragments.subfragment.TopMoversFragment;
import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;
import com.silverlinesoftwares.intratips.models.GainerLosserModel;
import com.silverlinesoftwares.intratips.tasks.GainerLooserTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LooserGainerActivity extends AppCompatActivity implements GainerLooserListener {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looser_gainer);
        MobileAds.initialize(LooserGainerActivity.this, initializationStatus -> {

        });
        tabLayout = findViewById(R.id.tab_menu);
        progress=findViewById(R.id.progress);
        viewPager=findViewById(R.id.mover_looser_page);
        GainerLooserTask gainerLooserTask=new GainerLooserTask(LooserGainerActivity.this);
        gainerLooserTask.execute(getIntent().getStringExtra(Constant.data_text));

        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,LooserGainerActivity.this);
        showInterestialAds();
    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
        try {
            JSONObject jsonObject=new JSONObject(data);
            String strings=jsonObject.getString("data");
            TypeToken<List<GainerLosserModel>> token = new TypeToken<List<GainerLosserModel>>() {};
            Gson gson=new Gson();
            List<GainerLosserModel> animals = gson.fromJson(strings, token.getType());
            List<GainerLosserModel> gainers=new ArrayList<>();
            List<GainerLosserModel> looser=new ArrayList<>();

            for (GainerLosserModel gainerLosserModel:animals){
                double vals=0;
                if(gainerLosserModel.getLtP()!=null && gainerLosserModel.getPreviousClose()!=null) {
                    vals = Double.parseDouble(gainerLosserModel.getLtP().replaceAll(",", "")) - Double.parseDouble(gainerLosserModel.getPreviousClose().replaceAll(",", ""));
                }
                if(vals>=0){
                    gainers.add(gainerLosserModel);
                }
                else{
                     looser.add(gainerLosserModel);
                }
            }

            ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());

            TopMoversFragment topMoversFragment=new TopMoversFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable(Constant.data, (Serializable) gainers);
            topMoversFragment.setArguments(bundle);


            TopLoserFragment topLoserFragment=new TopLoserFragment();
            Bundle bundle1=new Bundle();
            bundle1.putSerializable(Constant.data, (Serializable) looser);
            topLoserFragment.setArguments(bundle1);





            viewPagerAdapter.addFragment(topMoversFragment);
            viewPagerAdapter.addFragment(topLoserFragment);

            viewPagerAdapter.addTitle(getString(R.string.top_movers));
            viewPagerAdapter.addTitle(getString(R.string.top_loser));
            viewPager.setAdapter(viewPagerAdapter);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(viewPagerAdapter.getTitle(position))).attach();


        } catch (JSONException e) {
            Toast.makeText(LooserGainerActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
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
        progress.setVisibility(View.GONE);
        Toast.makeText(LooserGainerActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

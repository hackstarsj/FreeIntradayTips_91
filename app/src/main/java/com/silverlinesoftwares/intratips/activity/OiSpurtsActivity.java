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
import com.silverlinesoftwares.intratips.fragments.subfragment.OiContractFragment;
import com.silverlinesoftwares.intratips.fragments.subfragment.OiUnderlyingFragment;
import com.silverlinesoftwares.intratips.listeners.PriceBandListener;
import com.silverlinesoftwares.intratips.models.OiSpurtsContractsModel;
import com.silverlinesoftwares.intratips.models.OiSpurtsUnderlyingModel;
import com.silverlinesoftwares.intratips.tasks.OiSpurtsTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class OiSpurtsActivity extends AppCompatActivity implements PriceBandListener {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_band);
        MobileAds.initialize(OiSpurtsActivity.this, initializationStatus -> {

        });
        tabLayout = findViewById(R.id.tab_menu);
        progress=findViewById(R.id.progress);
        viewPager=findViewById(R.id.mover_looser_page);
        OiSpurtsTask gainerLooserTask=new OiSpurtsTask(OiSpurtsActivity.this);
        gainerLooserTask.execute();

        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,OiSpurtsActivity.this);
        showInterestialAds();
    }


    @Override
    public void onSucess(JSONObject data) {
        progress.setVisibility(View.GONE);
        try {


            JSONObject contract=data.getJSONObject("data_1");
            JSONObject underly=data.getJSONObject("data_2");

            String data_1=contract.getString("data");
            String data_2=underly.getString("data");

            String date_1=contract.getString("currentMarketDate");
            String date_2=contract.getString("previousMarketDate");


            String date_1_a=underly.getString("currentMarketDate");
            String date_2_a=underly.getString("previousMarketDate");

            TypeToken<List<OiSpurtsContractsModel>> token = new TypeToken<List<OiSpurtsContractsModel>>() {};
            TypeToken<List<OiSpurtsUnderlyingModel>> token2 = new TypeToken<List<OiSpurtsUnderlyingModel>>() {};
            Gson gson=new Gson();
            List<OiSpurtsContractsModel> volumes = gson.fromJson(data_1, token.getType());
            List<OiSpurtsUnderlyingModel> values = gson.fromJson(data_2, token2.getType());



            ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());

            OiContractFragment topMoversFragment=new OiContractFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable(Constant.data, (Serializable) volumes);
            bundle.putString(Constant.date_1,date_1);
            bundle.putString(Constant.date_2,date_2);
            topMoversFragment.setArguments(bundle);


            OiUnderlyingFragment topLoserFragment=new OiUnderlyingFragment();
            Bundle bundle1=new Bundle();
            bundle1.putSerializable(Constant.data, (Serializable) values);
            bundle1.putString(Constant.date_1,date_1_a);
            bundle1.putString(Constant.date_2,date_2_a);
            topLoserFragment.setArguments(bundle1);



            viewPagerAdapter.addFragment(topMoversFragment);
            viewPagerAdapter.addFragment(topLoserFragment);

            viewPagerAdapter.addTitle("Contract");
            viewPagerAdapter.addTitle("Underlying");
            viewPager.setAdapter(viewPagerAdapter);
            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(viewPagerAdapter.getTitle(position))).attach();




        } catch (JSONException e) {
            Toast.makeText(OiSpurtsActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(OiSpurtsActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

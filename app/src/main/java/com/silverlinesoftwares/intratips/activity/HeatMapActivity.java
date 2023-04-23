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
import com.silverlinesoftwares.intratips.fragments.subfragment.SectorFragment;
import com.silverlinesoftwares.intratips.fragments.subfragment.StockFragment;
import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;
import com.silverlinesoftwares.intratips.models.SectorStockModel;
import com.silverlinesoftwares.intratips.tasks.HeatMapTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeatMapActivity extends AppCompatActivity implements GainerLooserListener {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looser_gainer);
        MobileAds.initialize(HeatMapActivity.this, initializationStatus -> {

        });
        tabLayout = findViewById(R.id.tab_menu);
        progress=findViewById(R.id.progress);
        viewPager=findViewById(R.id.mover_looser_page);
        HeatMapTask gainerLooserTask=new HeatMapTask(HeatMapActivity.this);
        gainerLooserTask.execute(getIntent().getStringExtra(Constant.data_text));
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,HeatMapActivity.this);
        showInterestialAds();

    }

    public boolean checkInList(List<SectorStockModel> sectorStockModels,String data){
        for (SectorStockModel sectorStockModel:sectorStockModels){
            if(sectorStockModel.getSymbol().equalsIgnoreCase(data)){
                return true;
            }
        }
        return false;
    }

    public int getPosition(List<SectorStockModel> sectorStockModels,String data){
        int i=0;
        for (SectorStockModel sectorStockModel:sectorStockModels){
            if(sectorStockModel.getSymbol().equalsIgnoreCase(data)){
                return i;
            }
            i++;
        }
        return i;
    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
            TypeToken<List<SectorStockModel>> token = new TypeToken<List<SectorStockModel>>() {};
            Gson gson=new Gson();
            List<SectorStockModel> animals = gson.fromJson(data, token.getType());
            List<SectorStockModel> looser=new ArrayList<>();
            List<SectorStockModel> looser1=new ArrayList<>();

            ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());

            for (SectorStockModel sectorStockModel:animals){
                if(checkInList(looser,sectorStockModel.getSector())){
                    double data_per=Double.parseDouble(looser.get(getPosition(looser,sectorStockModel.getSector())).getPerchange())+Double.parseDouble(sectorStockModel.getPerchange());
                    double data_per1=Double.parseDouble(looser.get(getPosition(looser,sectorStockModel.getSector())).getPointchange())+Double.parseDouble(sectorStockModel.getPointchange());
                    looser.get(getPosition(looser,sectorStockModel.getSector())).setPerchange(String.valueOf(data_per));
                    looser.get(getPosition(looser,sectorStockModel.getSector())).setPointchange(String.valueOf(data_per1));
                }
                else {
                    SectorStockModel sectorStockModel1=new SectorStockModel(sectorStockModel.getSector(),sectorStockModel.getPointchange(),sectorStockModel.getPerchange());
                    looser.add(sectorStockModel1);
                }
            }


            for (SectorStockModel sectorStockModel:looser){
                sectorStockModel.setPointchange(""+Math.round(Double.parseDouble(sectorStockModel.getPointchange()) * 100.0) / 100.0);
                sectorStockModel.setPerchange(""+Math.round(Double.parseDouble(sectorStockModel.getPerchange()) * 100.0) / 100.0);
                looser1.add(sectorStockModel);
            }

        Collections.sort(looser1, (chair1, chair2) -> Double.compare(Double.parseDouble(chair1.getPerchange()),Double.parseDouble(chair2.getPerchange())));

            Collections.reverse(looser1);

            StockFragment topMoversFragment=new StockFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable(Constant.data, (Serializable) animals);
            topMoversFragment.setArguments(bundle);


            SectorFragment topLoserFragment=new SectorFragment();
            Bundle bundle1=new Bundle();
            bundle1.putSerializable(Constant.data, (Serializable) looser1);
            topLoserFragment.setArguments(bundle1);





            viewPagerAdapter.addFragment(topMoversFragment);
            viewPagerAdapter.addFragment(topLoserFragment);

            viewPagerAdapter.addTitle(getString(R.string.stock));
            viewPagerAdapter.addTitle(getString(R.string.sector));
            viewPager.setAdapter(viewPagerAdapter);
          new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(viewPagerAdapter.getTitle(position))).attach();

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
        Toast.makeText(HeatMapActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

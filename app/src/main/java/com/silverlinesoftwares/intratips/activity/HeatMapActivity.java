package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;

import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
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
import java.util.Comparator;
import java.util.List;

public class HeatMapActivity extends AppCompatActivity implements GainerLooserListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looser_gainer);
        MobileAds.initialize(HeatMapActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tab_menu);
        progress=findViewById(R.id.progress);
        viewPager=findViewById(R.id.mover_looser_page);
        HeatMapTask gainerLooserTask=new HeatMapTask(HeatMapActivity.this);
        gainerLooserTask.execute(new String[]{getIntent().getStringExtra(Constant.data_text)});
        StaticMethods.showInterestialAds(HeatMapActivity.this);
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,HeatMapActivity.this);


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

            ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

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

        Collections.sort(looser1, new Comparator<SectorStockModel>() {
            public int compare(SectorStockModel chair1, SectorStockModel chair2) {
                return Double.compare(Double.parseDouble(chair1.getPerchange()),Double.parseDouble(chair2.getPerchange()));
            }
        });

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
            tabLayout.setupWithViewPager(viewPager);



    }

    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(HeatMapActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

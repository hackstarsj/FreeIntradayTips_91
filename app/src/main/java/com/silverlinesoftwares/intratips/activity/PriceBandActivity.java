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

    private ViewPager viewPager;
    private TabLayout tabLayout;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_band);
        MobileAds.initialize(PriceBandActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tab_menu);
        progress=findViewById(R.id.progress);
        viewPager=findViewById(R.id.mover_looser_page);
        PricebandTask gainerLooserTask=new PricebandTask(PriceBandActivity.this);
        gainerLooserTask.execute();
        StaticMethods.showInterestialAds(PriceBandActivity.this);
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,PriceBandActivity.this);


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



            ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

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
            tabLayout.setupWithViewPager(viewPager);




        } catch (JSONException e) {
            Toast.makeText(PriceBandActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(PriceBandActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

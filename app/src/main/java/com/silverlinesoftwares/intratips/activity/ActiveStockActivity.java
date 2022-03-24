package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;

import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.ViewPagerAdapter;
import com.silverlinesoftwares.intratips.fragments.subfragment.ValuesFragment;
import com.silverlinesoftwares.intratips.fragments.subfragment.VolumeFragment;
import com.silverlinesoftwares.intratips.listeners.ActiveStockListener;
import com.silverlinesoftwares.intratips.models.ActiveStockModel;
import com.silverlinesoftwares.intratips.tasks.ActiveStockTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class ActiveStockActivity extends AppCompatActivity implements ActiveStockListener {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_stock);
        MobileAds.initialize(ActiveStockActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tab_menu);
        progress=findViewById(R.id.progress);
        viewPager=findViewById(R.id.mover_looser_page);
        ActiveStockTask gainerLooserTask=new ActiveStockTask(ActiveStockActivity.this);
        gainerLooserTask.execute();
        StaticMethods.showInterestialAds(ActiveStockActivity.this);

        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,ActiveStockActivity.this);
    }

    @Override
    public void onSucess(JSONObject jsonObject) {
        progress.setVisibility(View.GONE);
        try {
            JSONObject volume=jsonObject.getJSONObject("volumess");
            JSONObject value=jsonObject.getJSONObject("valuess");

            String strings=volume.getString("data");
            String string1=value.getString("data");
            TypeToken<List<ActiveStockModel>> token = new TypeToken<List<ActiveStockModel>>() {};
            Gson gson=new Gson();
            List<ActiveStockModel> volumes = gson.fromJson(strings, token.getType());
            List<ActiveStockModel> values = gson.fromJson(string1, token.getType());



            ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());

            ValuesFragment topMoversFragment=new ValuesFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable(Constant.data, (Serializable) values);
            topMoversFragment.setArguments(bundle);


            VolumeFragment topLoserFragment=new VolumeFragment();
            Bundle bundle1=new Bundle();
            bundle1.putSerializable(Constant.data, (Serializable) volumes);
            topLoserFragment.setArguments(bundle1);



            viewPagerAdapter.addFragment(topMoversFragment);
            viewPagerAdapter.addFragment(topLoserFragment);

            viewPagerAdapter.addTitle("Value");
            viewPagerAdapter.addTitle("Volume");
            viewPager.setAdapter(viewPagerAdapter);
            new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.setText(viewPagerAdapter.getTitle(position));
                }
            }).attach();



        } catch (JSONException e) {
            Toast.makeText(ActiveStockActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(ActiveStockActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

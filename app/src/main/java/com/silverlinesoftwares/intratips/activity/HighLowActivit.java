package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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
        MobileAds.initialize(HighLowActivit.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        progressBar=findViewById(R.id.progress);
        listView=findViewById(R.id.list_pre_market);
        if(getIntent().getStringExtra("data").equalsIgnoreCase("1")) {
            is_high=true;
            HighLowTask preMarketTask = new HighLowTask(HighLowActivit.this);
            preMarketTask.execute(new String[]{"1"});

        }
        else{
            is_high=false;
            HighLowTask preMarketTask = new HighLowTask(HighLowActivit.this);
            preMarketTask.execute(new String[]{"0"});

        }

        StaticMethods.showInterestialAds(HighLowActivit.this);
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,HighLowActivit.this);


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
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(HighLowActivit.this, ""+msg, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }
}

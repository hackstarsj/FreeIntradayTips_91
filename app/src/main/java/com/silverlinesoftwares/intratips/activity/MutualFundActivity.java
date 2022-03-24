package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.MutualFundAdapter;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.models.MutualFundModel;
import com.silverlinesoftwares.intratips.tasks.MutualFundTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MutualFundActivity extends AppCompatActivity implements ChartListener {

    private ListView listView;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_gainers);
        MobileAds.initialize(MutualFundActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        progress=findViewById(R.id.progress);
        listView=findViewById(R.id.list_volume_gainer);
        MutualFundTask gainerLooserTask=new MutualFundTask(MutualFundActivity.this);
        gainerLooserTask.execute();
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,MutualFundActivity.this);

    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
        Document document= Jsoup.parse(data);
        Elements elements=document.getElementsByTag("tr");
        List<MutualFundModel> bulkModels=new ArrayList<>();
        for (int i=1;i<elements.size();i++){
            try {
                bulkModels.add(new MutualFundModel(elements.get(i).children().get(1).text(),elements.get(i).children().get(2).text(),elements.get(i).children().get(3).text(), elements.get(i).children().get(4).text(), elements.get(i).children().get(5).text(), elements.get(i).children().get(6).text(), elements.get(i).children().get(7).text(), elements.get(i).children().get(8).text()));
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        MutualFundAdapter bulkDealAdapter=new MutualFundAdapter(MutualFundActivity.this,bulkModels);
        listView.setAdapter(bulkDealAdapter);

        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(()->{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StaticMethods.showInterestialAds(MutualFundActivity.this);
                }
            });
        },5000);
    }

    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(MutualFundActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

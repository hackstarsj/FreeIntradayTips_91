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
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.BulkDealAdapter;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.models.BulkModel;
import com.silverlinesoftwares.intratips.tasks.BlockDealTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockDealActivity extends AppCompatActivity implements ChartListener {

    private ListView listView;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_gainers);
        MobileAds.initialize(BlockDealActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        progress=findViewById(R.id.progress);
        listView=findViewById(R.id.list_volume_gainer);
        BlockDealTask gainerLooserTask=new BlockDealTask(BlockDealActivity.this);
        gainerLooserTask.execute();

        StaticMethods.showInterestialAds(BlockDealActivity.this);
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,BlockDealActivity.this);


    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
        Document document= Jsoup.parse(data);
        Elements elements=document.getElementsByTag("tr");
        List<BulkModel> bulkModels=new ArrayList<>();
        for (int i=3;i<elements.size();i++){
            try {
                bulkModels.add(new BulkModel(elements.get(i).children().get(0).text(), elements.get(i).children().get(1).text(), elements.get(i).children().get(3).text(), elements.get(i).children().get(4).text(), elements.get(i).children().get(6).text(), elements.get(i).children().get(5).text()));
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        Collections.reverse(bulkModels);
        BulkDealAdapter bulkDealAdapter=new BulkDealAdapter(BlockDealActivity.this,bulkModels);
        listView.setAdapter(bulkDealAdapter);
    }

    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(BlockDealActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

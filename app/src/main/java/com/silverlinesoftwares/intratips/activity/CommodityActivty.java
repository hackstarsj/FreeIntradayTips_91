package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.CommodityAdapter;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.models.CommodityModel;
import com.silverlinesoftwares.intratips.tasks.CommodityTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CommodityActivty extends AppCompatActivity implements ChartListener {

    private ListView listView;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_gainers);
        MobileAds.initialize(CommodityActivty.this,getString(R.string.app_ads_id));
        progress=findViewById(R.id.progress);
        listView=findViewById(R.id.list_volume_gainer);
        CommodityTask gainerLooserTask=new CommodityTask(CommodityActivty.this);
        gainerLooserTask.execute();
        StaticMethods.showInterestialAds(CommodityActivty.this);

        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,CommodityActivty.this);


    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
        Document document= Jsoup.parse(data);
        Elements elements=document.getElementsByTag("tr");
        List<CommodityModel> bulkModels=new ArrayList<>();
        for (int i=1;i<elements.size();i++){
            try {
                bulkModels.add(new CommodityModel(elements.get(i).children().get(1).text(), elements.get(i).children().get(2).text(), elements.get(i).children().get(5).text(), elements.get(i).children().get(4).text(), elements.get(i).children().get(6).text(), elements.get(i).children().get(7).text()));
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        CommodityAdapter bulkDealAdapter=new CommodityAdapter(CommodityActivty.this,bulkModels);
        listView.setAdapter(bulkDealAdapter);
    }

    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(CommodityActivty.this, msg, Toast.LENGTH_SHORT).show();
    }
}

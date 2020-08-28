package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.CurrencyAdapter;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.models.CurrencyModel;
import com.silverlinesoftwares.intratips.tasks.CurrencyTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CurrecnyActivty extends AppCompatActivity implements ChartListener {

    private ListView listView;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_gainers);
        MobileAds.initialize(CurrecnyActivty.this,getString(R.string.app_ads_id));
        progress=findViewById(R.id.progress);
        listView=findViewById(R.id.list_volume_gainer);
        CurrencyTask gainerLooserTask=new CurrencyTask(CurrecnyActivty.this);
        gainerLooserTask.execute();

        StaticMethods.showInterestialAds(CurrecnyActivty.this);
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,CurrecnyActivty.this);


    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
        Document document= Jsoup.parse(data);
        Elements elements=document.getElementsByTag("tr");
        List<CurrencyModel> bulkModels=new ArrayList<>();
        for (int i=1;i<elements.size();i++){
            try {
                bulkModels.add(new CurrencyModel(elements.get(i).children().get(1).text(), elements.get(i).children().get(2).text(), elements.get(i).children().get(3).children().get(0).text(), elements.get(i).children().get(4).children().get(0).text()));
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        CurrencyAdapter bulkDealAdapter=new CurrencyAdapter(CurrecnyActivty.this,bulkModels);
        listView.setAdapter(bulkDealAdapter);
    }

    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(CurrecnyActivty.this, msg, Toast.LENGTH_SHORT).show();
    }
}

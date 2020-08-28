package com.silverlinesoftwares.intratips.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.MobileAds;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.NewsAdapterR;
import com.silverlinesoftwares.intratips.listeners.NewsListener;
import com.silverlinesoftwares.intratips.models.NewsModel;
import com.silverlinesoftwares.intratips.tasks.NewsTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewsActivity extends AppCompatActivity implements NewsListener {

    private ProgressBar progress;
    private ArrayList<NewsModel> newsModels;
    private NewsAdapterR newsAdapter;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        MobileAds.initialize(NewsActivity.this,getString(R.string.app_ads_id));
        final RecyclerView listView=(RecyclerView) findViewById(R.id.list_news);
        progress=findViewById(R.id.progress);
        newsModels=new ArrayList<>();
        listView.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
        listView.setHasFixedSize(true);
        newsAdapter=new NewsAdapterR(NewsActivity.this,listView,newsModels);
        listView.setAdapter(newsAdapter);
        NewsTask newsTask=new NewsTask(NewsActivity.this,getIntent().getStringExtra(Constant.data_text),0);
        newsTask.execute(new String[]{"ok"});

        newsAdapter.setOnItemClickListener(new NewsAdapterR.OnItemClickListener() {
            @Override
            public void onItemClick(View v, NewsModel obj, int position) {
                Intent intent=new Intent(NewsActivity.this, NewWebActivity.class);
                intent.putExtra("url",newsModels.get(position).getLinks());
                startActivity(intent);

            }
        });
        StaticMethods.showInterestialAds(NewsActivity.this);
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,NewsActivity.this);


    }


    @Override
    public void onSucess(List<NewsModel> data) {
        progress.setVisibility(View.GONE);
        newsModels.addAll(data);
        newsAdapter.notifyItemInserted(data.size());


    }

    @Override
    public void onFailedNews(String msg) {

    }


}

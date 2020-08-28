package com.silverlinesoftwares.intratips.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import com.google.android.gms.ads.MobileAds;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.tasks.ScreenTask14;
import com.silverlinesoftwares.intratips.tasks.ScreenTask3a;
import com.silverlinesoftwares.intratips.tasks.ScreenTask4;
import com.silverlinesoftwares.intratips.tasks.ScreenTask4b;
import com.silverlinesoftwares.intratips.tasks.ScreenTask4c;
import com.silverlinesoftwares.intratips.tasks.ScreenTask8b;
import com.silverlinesoftwares.intratips.tasks.ScreenTask8d;
import com.silverlinesoftwares.intratips.tasks.ScreenerTask4a;
import com.silverlinesoftwares.intratips.tasks.SingleScreenTask;
import com.silverlinesoftwares.intratips.tasks.SingleTask26;
import com.silverlinesoftwares.intratips.util.StaticMethods;


public class AdvanceScreenerActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    String methods="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_screener);
        MobileAds.initialize(AdvanceScreenerActivity.this,getString(R.string.app_ads_id));
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        if(getIntent().getStringExtra("url")==null || getIntent().getStringExtra("method")==null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        methods=getIntent().getStringExtra("method");
        TableLayout tl = (TableLayout) findViewById(R.id.main_table);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        if(methods.equalsIgnoreCase("22") || methods.equalsIgnoreCase("23") || methods.equalsIgnoreCase("24") || methods.equalsIgnoreCase("20") || methods.equalsIgnoreCase("17"))
        {
            SingleTask26 singleScreenTask = new SingleTask26(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }
        else if(methods.equalsIgnoreCase("14") || methods.equalsIgnoreCase("13") || methods.equalsIgnoreCase("12") || methods.equalsIgnoreCase("3") || methods.equalsIgnoreCase("4") || methods.equalsIgnoreCase("5") || methods.equalsIgnoreCase("6") || methods.equalsIgnoreCase("74"))
        {
            ScreenTask14 singleScreenTask = new ScreenTask14(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }
        else if(methods.equalsIgnoreCase("3a")){
            ScreenTask3a singleScreenTask = new ScreenTask3a(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }
        else if(methods.equalsIgnoreCase("4a")){
            ScreenerTask4a singleScreenTask = new ScreenerTask4a(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }
        else if(methods.equalsIgnoreCase("4b")){
            ScreenTask4b singleScreenTask = new ScreenTask4b(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }
        else if(methods.equalsIgnoreCase("4c")){
            ScreenTask4 singleScreenTask = new ScreenTask4(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }
        else if(methods.equalsIgnoreCase("4d")){
            ScreenTask4b singleScreenTask = new ScreenTask4b(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }
        else if(methods.equalsIgnoreCase("8a")){
            ScreenTask4c singleScreenTask = new ScreenTask4c(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }
        else if(methods.equalsIgnoreCase("8b")){
            ScreenTask8b singleScreenTask = new ScreenTask8b(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }
        else if(methods.equalsIgnoreCase("8d")){
            ScreenTask8d singleScreenTask = new ScreenTask8d(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }
        else {
            SingleScreenTask singleScreenTask = new SingleScreenTask(AdvanceScreenerActivity.this, getIntent().getStringExtra("url"), tl, progressBar);
            ChartActivity.executeAsyncTask(singleScreenTask);
        }

        StaticMethods.showInterestialAds(AdvanceScreenerActivity.this);
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,AdvanceScreenerActivity.this);


    }
}

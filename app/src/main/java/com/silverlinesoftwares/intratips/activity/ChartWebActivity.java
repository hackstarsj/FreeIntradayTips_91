package com.silverlinesoftwares.intratips.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.ads.MobileAds;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;


public class ChartWebActivity extends AppCompatActivity {

    private static final double PIC_WIDTH = 1000;
    WebView webView;
    ProgressBar progressBar;
    String urls=null;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_web);
        MobileAds.initialize(ChartWebActivity.this, initializationStatus -> {

        });
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        if(getIntent().getStringExtra("url")==null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        else{
            urls=getIntent().getStringExtra("url");
        }
        progressBar= findViewById(R.id.progress);
        webView= findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        webView.getSettings().setAllowFileAccess(false);
        //webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.setPadding(0, 0, 0, 0);
        webView.setInitialScale(getScale());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                progressBar.setVisibility(View.GONE);
                // Inject CSS when page is done loading
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(urls);

    }


    private int getScale(){
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        double val = (double) width / PIC_WIDTH;
        val = val * 100d;
        return (int) val;
    }

}

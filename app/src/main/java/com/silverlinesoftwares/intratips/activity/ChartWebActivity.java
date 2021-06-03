package com.silverlinesoftwares.intratips.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import java.io.InputStream;


public class ChartWebActivity extends AppCompatActivity {

    private static final double PIC_WIDTH = 1000;
    WebView webView;
    ProgressBar progressBar;
    String urls=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_web);
        MobileAds.initialize(ChartWebActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
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
        progressBar=(ProgressBar)findViewById(R.id.progress);
        webView=(WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        webView.getSettings().setAllowFileAccess(false);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
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
                injectCSS();
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(urls);
        StaticMethods.showInterestialAds(ChartWebActivity.this);

    }

    private void injectCSS() {
        try {
            InputStream inputStream = getAssets().open("style_chart.css");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int getScale(){
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(PIC_WIDTH);
        val = val * 100d;
        return val.intValue();
    }

}

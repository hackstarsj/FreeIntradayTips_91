package com.silverlinesoftwares.intratips.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.ads.MobileAds;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.util.StaticMethods;


public class ScreenerNseActivity extends AppCompatActivity {

    private static final double PIC_WIDTH = 1000;
    WebView webView;
    ProgressBar progressBar;
    String urls=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_web);
        MobileAds.initialize(ScreenerNseActivity.this,getString(R.string.app_ads_id));
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
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
        setupWebViewClient("https://furthergrow.silverlinesoftwares.com/screener/market.php");
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {

                //  progressDialog.setProgress(progress);
                //textView.setText(progress + " %");
            }

        });
        webView.loadUrl("https://furthergrow.silverlinesoftwares.com/screener/market.php");

        StaticMethods.showInterestialAds(ScreenerNseActivity.this);


    }


    private int getScale(){
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(PIC_WIDTH);
        val = val * 100d;
        return val.intValue();
    }

    private void setupWebViewClient(final String url) {
        webView.setWebViewClient(new WebViewClient() {
            private int running = 0; // Could be public if you want a timer to check.

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String urlNewString) {
                running++;
                webView.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                running = Math.max(running, 1); // First request move it to 1.
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("Running",String.valueOf(running));
                if(--running == 0) { // just "running--;" if you add a timer.
                    // TODO: finished... if you want to fire a method.
                    // progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}

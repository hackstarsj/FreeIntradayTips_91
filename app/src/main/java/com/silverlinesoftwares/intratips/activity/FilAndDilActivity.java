package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.tasks.FilTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FilAndDilActivity extends AppCompatActivity implements ChartListener {

    private WebView listView;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fil);
        MobileAds.initialize(FilAndDilActivity.this, initializationStatus -> {

        });
        progress=findViewById(R.id.progress);
        listView=findViewById(R.id.list_volume_gainer);
        FilTask gainerLooserTask=new FilTask(FilAndDilActivity.this);
        gainerLooserTask.execute();

        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,FilAndDilActivity.this);

        showInterestialAds();
    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
        Document document=Jsoup.parse(data);

        Elements elements=document.getElementsByTag("table");
        String all_data="";
        if(elements.size()>0) {
            all_data = "<html>" +
                    "<head> " +
                    "<title>" +
                    "Analysis" +
                    "</title>" +
                    "<style> " +
                   "body{ margin:0px;padding:0px; }" +
                    " th{ background:dodgerblue;color:white; }" +
                    " table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; }" +
                    " th,td{ padding:10px } " +
                    "table{ margin:0px;padding:0px;border-radius:10px;width:100%;height:100%;}" +
                   " tr:nth-child(2n) {" +
                    "    background-color: rgb(244, 244, 244);" +
                    "    box-shadow: 5px 5px 5px rgb(225, 217, 217);" +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    elements
                    +"</body></html>";
        }
        listView.setWebViewClient(new WebViewClient());
        listView.setWebChromeClient(new WebChromeClient());

        listView.loadData(all_data,"text/html","UTF-8");
        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(()-> runOnUiThread(this::showInterestialAdsView),5000);
    }
    private InterstitialAd mInterstitialAd;
    private void showInterestialAdsView() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }
    private void showInterestialAds() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.inter_r), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }


    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(FilAndDilActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

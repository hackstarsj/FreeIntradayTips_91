package com.silverlinesoftwares.intratips.activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayoutMediator;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.ViewPagerAdapter;
import com.silverlinesoftwares.intratips.fragments.stockdetails.AnalysisFragment;
import com.silverlinesoftwares.intratips.fragments.stockdetails.BalanceSheetFragment;
import com.silverlinesoftwares.intratips.fragments.stockdetails.CashFlowFragment;
import com.silverlinesoftwares.intratips.fragments.stockdetails.ChartFragment;
import com.silverlinesoftwares.intratips.fragments.stockdetails.IncomeStatementFragment;
import com.silverlinesoftwares.intratips.fragments.stockdetails.ManagementFragment;
import com.silverlinesoftwares.intratips.fragments.stockdetails.ShareHoldersFragment;
import com.silverlinesoftwares.intratips.fragments.stockdetails.SummaryFragment;
import com.silverlinesoftwares.intratips.fragments.stockdetails.TechnicalAnalysisFragment;
import com.silverlinesoftwares.intratips.listeners.StockDetailListener;
import com.silverlinesoftwares.intratips.models.SummaryModel;
import com.silverlinesoftwares.intratips.tasks.StockDetailHometask;
import com.silverlinesoftwares.intratips.util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

public class StockDetailsActivity extends AppCompatActivity implements StockDetailListener {

    private TabLayout tabLayout;
    ViewPager2 viewPager;
    TextView stock_title;
    TextView price;
    TextView low;
    TextView high;
    ImageView ticker;
    TextView pr_close;
    double oldPrice=0.0;
    TextView opens;
    TextView price_per;

    String string;
    private WebView webview;
    private WebSocketClient webSocketClient;
    private double currentPrice=0.0;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);
        MobileAds.initialize(StockDetailsActivity.this, initializationStatus -> {

        });
        string=getIntent().getStringExtra(Constant.search);
        tabLayout=findViewById(R.id.tab_item);
        webview=findViewById(R.id.webview);
        price_per=findViewById(R.id.price_per);
        viewPager=findViewById(R.id.stock_page);
        stock_title=findViewById(R.id.stock_title);
        price=findViewById(R.id.price);
        ticker=findViewById(R.id.ticker);

        low=findViewById(R.id.low);
        high=findViewById(R.id.high);
        pr_close=findViewById(R.id.pr_close);

        opens=findViewById(R.id.opens);

        if(!string.contains(".NS")){
            if(!string.contains(".BO")){
                string=string+".NS";
            }
        }
        stock_title.setText(string);
        StockDetailHometask detailHometask=new StockDetailHometask(string,low,price,opens,pr_close,high);
        detailHometask.execute();
        LoadHomePage();


        webview.loadUrl("file:///android_asset/data.html");   // now it will not fail here
        webview.getSettings().setJavaScriptEnabled(true);
      
        //if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webview.addJavascriptInterface(this, "Android");
        //}

        createWebSocketClient();
        showInterestialAds();

    }

    private void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("wss://streamer.finance.yahoo.com/");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                String data="{\"subscribe\":[\""+string+"\"]}";
                Log.i("WebSocket", "Session is starting");
                Log.d("Data: ",data);
                webSocketClient.send(data);
            }
            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received"+s);
                if(s!=null && !s.isEmpty()) {
                    convertBas64toString(s);
                }
                runOnUiThread(() -> {
                    try{
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
            @Override
            public void onBinaryReceived(byte[] data) {
            }
            @Override
            public void onPingReceived(byte[] data) {
            }
            @Override
            public void onPongReceived(byte[] data) {
            }
            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }
            @Override
            public void onCloseReceived() {
                createWebSocketClient();
                Log.i("WebSocket", "Closed ");
                System.out.println("onCloseReceived");
            }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    private void convertBas64toString(String s) {
        passDatatoJs(s);
    }

    @JavascriptInterface
    public void alertJson(final String myJSON) {
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                () -> {
                    runOnUiThread(() -> {

                        Log.i("tag", "This'll run 300 milliseconds later");

                        Log.d("Data : ", "" + myJSON);
                        try {
                            JSONObject jsonObject = new JSONObject(myJSON);
                            if (price != null) {
                                if (jsonObject.has(string)) {
                                    JSONObject stock = jsonObject.getJSONObject(string);
                                    JSONObject prices = stock.getJSONObject("price");
                                    if (prices.has("regularMarketPrice")) {
                                        String regularMarketPrice = prices.getString("regularMarketPrice");


                                        try {
                                            oldPrice = currentPrice;
                                            currentPrice = Double.parseDouble(prices.getString("regularMarketPrice"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        price.setText(regularMarketPrice);


                                    }
                                    if (prices.has("regularMarketChangePercent")) {
                                        String per = prices.getString("regularMarketChangePercent");
                                        price_per.setText(String.format("%% %s", per));
                                        try {
                                            if (Double.parseDouble(per) > 0) {
                                                price.setBackgroundColor(Color.parseColor("#08C82D"));
                                                price.setTextColor(Color.parseColor("#FFFFFF"));
                                                ticker.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                                                price_per.setTextColor(Color.parseColor("#08C82D"));
                                            } else {
                                                ticker.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                                                price_per.setTextColor(Color.parseColor("#EF1003"));
                                                price.setBackgroundColor(Color.parseColor("#EF1003"));
                                                price.setTextColor(Color.parseColor("#FFFFFF"));

                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                }

                            }
                            Log.d("Json", jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    });
    // iterate through jsonarray
                },
                1000);

    }

    private void passDatatoJs(final String data) {
        webview.post(() -> {
            webview.loadUrl("javascript:printdata('"+data+"')");
            //mWebView.loadUrl(...).
        });
    }

    private void LoadHomePage(){
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        Bundle bundle=new Bundle();
        bundle.putString(Constant.search,getIntent().getStringExtra(Constant.search));

        SummaryFragment summaryFragment=new SummaryFragment();
        summaryFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(summaryFragment);
        viewPagerAdapter.addTitle("Summary");

        TechnicalAnalysisFragment technicalAnalysisFragment=new TechnicalAnalysisFragment();
        technicalAnalysisFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(technicalAnalysisFragment);
        viewPagerAdapter.addTitle("Technical");


        ChartFragment chartFragment=new ChartFragment();
        chartFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(chartFragment);
        viewPagerAdapter.addTitle("Chart");


        IncomeStatementFragment incomeStatementFragment=new IncomeStatementFragment();
        incomeStatementFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(incomeStatementFragment);
        viewPagerAdapter.addTitle("Income Statement");

        BalanceSheetFragment balanceSheetFragment=new BalanceSheetFragment();
        balanceSheetFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(balanceSheetFragment);
        viewPagerAdapter.addTitle("Balancesheet");

        CashFlowFragment cashFlowFragment=new CashFlowFragment();
        cashFlowFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(cashFlowFragment);
        viewPagerAdapter.addTitle("Cashflow");


        AnalysisFragment analysisFragment=new AnalysisFragment();
        analysisFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(analysisFragment);
        viewPagerAdapter.addTitle("Analysis");


        ShareHoldersFragment shareHoldersFragment=new ShareHoldersFragment();
        shareHoldersFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(shareHoldersFragment);
        viewPagerAdapter.addTitle("Shareholders");

        ManagementFragment managementFragment=new ManagementFragment();
        managementFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(managementFragment);
        viewPagerAdapter.addTitle("Management");

        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(viewPagerAdapter.getTitle(position))).attach();
        viewPager.setOffscreenPageLimit(viewPagerAdapter.getItemCount()-1);
        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(()-> runOnUiThread(this::showInterestialAdsView),10000);

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

        InterstitialAd.load(this,getString(R.string.inter1), adRequest,
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
    public void onSummayLoaded(SummaryModel data) {

    }

    @Override
    public void onFailed(String msg) {

    }
}

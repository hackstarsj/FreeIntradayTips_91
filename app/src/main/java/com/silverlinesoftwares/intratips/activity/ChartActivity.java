package com.silverlinesoftwares.intratips.activity;

import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.tasks.ChartTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartActivity extends AppCompatActivity implements ChartListener,View.OnClickListener {

    private WebView htmlWebView;
    private List<String> Itemsstrings;
    private ArrayAdapter LisarrayAdapter;
    private boolean started=false;

    TextView one_day,five_day,one_month,three_month,six_month,one_year,two_year,five_year,all_data,nine_month,ten_year;

    long times;
    String actives="one_day";
    String periods="1d";
    ProgressBar progress;
    Spinner interval;
    private String sym;
    long start_time=0,end_time=0;

    Map<String,String> maps=new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        MobileAds.initialize(ChartActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        htmlWebView = findViewById(R.id.webview);
        progress=findViewById(R.id.progress);


        maps.put("1M","1m");
        maps.put("2M","2m");
        maps.put("5M","5m");
        maps.put("15M","15m");
        maps.put("30M","30m");
        maps.put("60M","60m");
        maps.put("1D","1d");
        maps.put("1WK","1wk");
        maps.put("1MO","1mo");
        maps.put("3MO","3mo");

        one_day=findViewById(R.id.one_day);
        five_day=findViewById(R.id.five_day);
        interval=findViewById(R.id.interval);
        one_month=findViewById(R.id.one_month);
        three_month=findViewById(R.id.three_month);
        six_month=findViewById(R.id.six_month);
        nine_month=findViewById(R.id.nine_month);
        one_year=findViewById(R.id.one_year);
        two_year=findViewById(R.id.two_year);
        five_year=findViewById(R.id.five_year);
        all_data=findViewById(R.id.all_data);
        ten_year=findViewById(R.id.ten_year);

        one_day.setOnClickListener(this);
        five_day.setOnClickListener(this);
        one_month.setOnClickListener(this);
        one_year.setOnClickListener(this);
        six_month.setOnClickListener(this);
        nine_month.setOnClickListener(this);
        three_month.setOnClickListener(this);
        two_year.setOnClickListener(this);
        five_year.setOnClickListener(this);
        all_data.setOnClickListener(this);
        ten_year.setOnClickListener(this);





        sym=getIntent().getStringExtra("symbol");

        times=(new java.util.Date().getTime())/1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        long newdate=0;
        String dates=dateFormat.format(date);
        try {
            Date date1=dateFormat.parse(dates);
            newdate=date1.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
            newdate=(new java.util.Date().getTime())/1000;
        }
        start_time=newdate;
        end_time=newdate-86400;

        Itemsstrings=new ArrayList<>();
        Itemsstrings.clear();
        Itemsstrings.add("1M");
        Itemsstrings.add("2M");
        Itemsstrings.add("5M");
        Itemsstrings.add("15M");
        Itemsstrings.add("30M");
        Itemsstrings.add("60M");
        Itemsstrings.add("1D");
        Itemsstrings.add("5D");
        Itemsstrings.add("1WK");
        Itemsstrings.add("1MO");
        Itemsstrings.add("3MO");
        LisarrayAdapter=new ArrayAdapter(ChartActivity.this,R.layout.simple_list,Itemsstrings);
        final Spinner interval=findViewById(R.id.interval);
        interval.setAdapter(LisarrayAdapter);

        interval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(started) {
                    showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/" + sym + "?&interval=" + maps.get(interval.getSelectedItem().toString()) + "&range=" + periods, periods);
                }
                started=true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        htmlWebView.getSettings().setAllowFileAccess(true);
        htmlWebView.getSettings().setAllowContentAccess(true);
        htmlWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        htmlWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        htmlWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webSetting.setDisplayZoomControls(true);

        showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=1m&range=1d","1d");

        StaticMethods.showInterestialAds(ChartActivity.this);

    }

    public static String StreamToString(InputStream in) throws IOException {
        if(in == null) {
            return "";
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
        }
        return writer.toString();
    }

    @Override
    public void onSucess(String data) {
        String htmlFilename = "untitled.html";
        AssetManager mgr = getAssets();
        try {
            InputStream in = mgr.open(htmlFilename, AssetManager.ACCESS_BUFFER);
            String htmlContentInStringFormat = StreamToString(in);
            in.close();
            htmlContentInStringFormat=htmlContentInStringFormat.replace("$STOCK_SYMBOL_URL$",data);
            htmlWebView.loadData("","text/html","UTF-8");
            htmlWebView.loadDataWithBaseURL(null, htmlContentInStringFormat, "text/html", "utf-8", null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        progress.setVisibility(View.GONE);

    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(ChartActivity.this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {

        ResetActive();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        long newdate=0;
        String dates=dateFormat.format(date);
        try {
            Date date1=dateFormat.parse(dates);
            newdate=date1.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
            newdate=(new java.util.Date().getTime())/1000;
        }
        times=newdate;
        switch(v.getId()){
            case R.id.ten_year:
                ten_year.setBackgroundResource(R.drawable.border_bottom);

                Itemsstrings.clear();
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=1d&range=10y","10y");

                break;
            case R.id.one_day:
                one_day.setBackgroundResource(R.drawable.border_bottom);

                Itemsstrings.clear();
                Itemsstrings.add("1M");
                Itemsstrings.add("2M");
                Itemsstrings.add("5M");
                Itemsstrings.add("15M");
                Itemsstrings.add("30M");
                Itemsstrings.add("60M");
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=1m&range=1d","1d");

                break;
            case R.id.five_day:
                five_day.setBackgroundResource(R.drawable.border_bottom);
                Itemsstrings.clear();
                Itemsstrings.add("1M");
                Itemsstrings.add("2M");
                Itemsstrings.add("5M");
                Itemsstrings.add("15M");
                Itemsstrings.add("30M");
                Itemsstrings.add("60M");
                
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=1m&range=5d","5d");
                break;
            case R.id.one_month:
                one_month.setBackgroundResource(R.drawable.border_bottom);
                Itemsstrings.clear();
                Itemsstrings.add("2M");
                Itemsstrings.add("5M");
                Itemsstrings.add("15M");
                Itemsstrings.add("30M");
                Itemsstrings.add("60M");
                
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=2m&range=1mo","1mo");
                break;
            case R.id.three_month:
                three_month.setBackgroundResource(R.drawable.border_bottom);
                Itemsstrings.clear();
                Itemsstrings.add("30M");
                Itemsstrings.add("60M");
                
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=30m&range=3mo","3mo");
                break;
            case R.id.six_month:
                six_month.setBackgroundResource(R.drawable.border_bottom);
                Itemsstrings.clear();
                Itemsstrings.add("30M");
                Itemsstrings.add("60M");
                
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=30m&range=6mo","6mo");
                break;
            case R.id.nine_month:
                nine_month.setBackgroundResource(R.drawable.border_bottom);
                Itemsstrings.clear();
                Itemsstrings.add("30M");
                Itemsstrings.add("60M");
                
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=30m&range=ytd","ytd");
                break;
            case R.id.one_year:
                one_year.setBackgroundResource(R.drawable.border_bottom);
                Itemsstrings.clear();
                Itemsstrings.add("30M");
                Itemsstrings.add("60M");
                
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=30m&range=1y","1y");
                break;
            case R.id.two_year:
                two_year.setBackgroundResource(R.drawable.border_bottom);
                //   showCharts("1wk", times,times-63072000,"two_year");
                Itemsstrings.clear();
                Itemsstrings.add("30M");
                Itemsstrings.add("60M");
                
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=30m&range=2y","2y");
                break;
            case R.id.five_year:
                five_year.setBackgroundResource(R.drawable.border_bottom);
                Itemsstrings.clear();
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=1d&range=5y","5y");
                break;
            case R.id.all_data:
                all_data.setBackgroundResource(R.drawable.border_bottom);
                Itemsstrings.clear();
                Itemsstrings.add("1M");
                Itemsstrings.add("2M");
                Itemsstrings.add("5M");
                Itemsstrings.add("15M");
                Itemsstrings.add("30M");
                Itemsstrings.add("60M");
                
                Itemsstrings.add("1D");
                Itemsstrings.add("5D");
                Itemsstrings.add("1WK");
                Itemsstrings.add("1MO");
                Itemsstrings.add("3MO");
                LisarrayAdapter.notifyDataSetChanged();
                showCharts("https://partner-query.finance.yahoo.com/v8/finance/chart/"+sym+"?&interval=1m&range=max","max");
                break;

        }

    }

    private void ResetActive() {
        one_day.setBackgroundResource(0);
        one_month.setBackgroundResource(0);
        five_day.setBackgroundResource(0);
        six_month.setBackgroundResource(0);
        three_month.setBackgroundResource(0);
        one_year.setBackgroundResource(0);
        two_year.setBackgroundResource(0);
        five_year.setBackgroundResource(0);
        all_data.setBackgroundResource(0);
        nine_month.setBackgroundResource(0);
        ten_year.setBackgroundResource(0);
    }

    private void showCharts(String url,String periods) {
        progress.setVisibility(View.VISIBLE);
        this.periods=periods;
        ChartTask chartTask=new ChartTask(url,ChartActivity.this);
        chartTask.execute(new String[]{});
    }

}

package com.silverlinesoftwares.intratips.tasks;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StockDetailHometask {

    final String symbol;
    final TextView prices;
    final TextView open;
    final TextView close;
    final TextView low;
    final TextView high;

    public StockDetailHometask(String symbol,TextView low, TextView prices, TextView open, TextView close, TextView high) {
        this.symbol = symbol;
        this.low=low;
        this.prices = prices;
        this.open = open;
        this.close = close;
        this.high = high;
    }


    protected void onPostExecute(String string) {
        if(string!=null) {
            if (!string.isEmpty()) {
                try {


                    Gson gson = new GsonBuilder().create();
                    JSONObject jsonObject = new JSONObject(string);

                    JSONObject finance = jsonObject.getJSONObject("quoteSummary").getJSONArray("result").getJSONObject(0);
                    JSONObject SummaryObject = finance.getJSONObject("summaryDetail");
                    JSONObject PriceObject = finance.getJSONObject("price");

                    @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm a");
                    Date date = new Date();
                    high.setText(SummaryObject.getJSONObject("dayHigh").getString("fmt"));
                    low.setText(SummaryObject.getJSONObject("dayLow").getString("raw"));
                    close.setText(SummaryObject.getJSONObject("previousClose").getString("fmt"));
                    open.setText(SummaryObject.getJSONObject("open").getString("fmt"));
                    prices.setText(PriceObject.getJSONObject("regularMarketPrice").getString("raw"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            client.retryOnConnectionFailure();
            client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build(); // connect timeout


            Request request =
                    new Request.Builder()
                            .url("https://query2.finance.yahoo.com/v10/finance/quoteSummary/"+symbol+"?formatted=true&modules=price%2CsummaryDetail&corsDomain=in.finance.yahoo.com")
                            .addHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0")
                            .addHeader("Accept-Language","en-US,en;q=0.5")
                            .addHeader("Te","Trailers")
                            .addHeader("Connection","keep-alive")
                            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                            .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            }
        if (response != null && response.isSuccessful()) {
                try {
                    if (response.body() != null) {
                        String data=response.body().string();
                        try {

                            JSONObject jsonObject=new JSONObject(data);
                           return jsonObject.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return null;
    }

    public void execute(String... strings) {
        Executor executor = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
           String  data=doInBackground(strings);
           handler.post(()-> onPostExecute(data));
        });
    }





}

package com.silverlinesoftwares.intratips.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silverlinesoftwares.intratips.adapters.EquityAdapter;
import com.silverlinesoftwares.intratips.models.EquityModel;

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

    String symbol;
    TextView prices;
    TextView open;
    TextView close;
    TextView low;
    TextView high;

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

                    DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm a");
                    Date date = new Date();
                    high.setText(SummaryObject.getJSONObject("dayHigh").getString("fmt"));
                    low.setText(SummaryObject.getJSONObject("dayLow").getString("raw"));
                    close.setText(SummaryObject.getJSONObject("previousClose").getString("fmt"));
                    open.setText(SummaryObject.getJSONObject("open").getString("fmt"));
                    prices.setText(PriceObject.getJSONObject("regularMarketPrice").getString("raw"));

                    // System.out.println(dateFormat.format(date));
//                    equityModel.setHigh(SummaryObject.getJSONObject("dayHigh").getString("fmt"));
//                    equityModel.setLow(SummaryObject.getJSONObject("dayLow").getString("fmt"));
//                    equityModel.setPr_close(SummaryObject.getJSONObject("previousClose").getString("fmt"));
//                    equityModel.setCmp_price(PriceObject.getJSONObject("regularMarketPrice").getString("raw"));
//                    equityModel.setOpens(SummaryObject.getJSONObject("open").getString("fmt"));
//                    equityModel.setRealtime_time(dateFormat.format(date));
//                    equityModel.setPrev_close(SummaryObject.getJSONObject("previousClose").getString("raw"));
//                    equityModel.setDay_low(SummaryObject.getJSONObject("dayLow").getString("raw"));
//                    equityModel.setDay_high(SummaryObject.getJSONObject("dayHigh").getString("raw"));
//                    equityModel.setPrice(PriceObject.getJSONObject("regularMarketPrice").getString("raw"));
//                    equityModel.setOpen(SummaryObject.getJSONObject("open").getString("raw"));
//                    //equityModel.setRealtime_time(SummaryObject.getJSONObject("previousClose").getString("fmt"));
//                    equityModel.setAvg_3m_volume(SummaryObject.getJSONObject("averageVolume10days").getString("raw"));
//                    equityModel.setChange(PriceObject.getJSONObject("regularMarketChange").getString("raw"));
//                    equityModel.setChg_percent(PriceObject.getJSONObject("regularMarketChangePercent").getString("raw"));
//                    equityModel.setCurrency(PriceObject.getString("currency"));
//                    equityModel.setData_type(PriceObject.getString("quoteType"));
//                    if(SummaryObject.getJSONObject("dividendRate").has("raw")) {
//                        equityModel.setDividend_rate(SummaryObject.getJSONObject("dividendRate").getString("raw"));
//                    }
//                    equityModel.setEps_curr_year(SummaryObject.getJSONObject("previousClose").getString("raw"));
//
//
//                    equityModel.setExchange(PriceObject.getString("exchangeName"));
//                    equityModel.setExchange_external_id(PriceObject.getString("exchange"));
//
//                    if (SummaryObject.getJSONObject("dividendYield").has("raw")) {
//                        equityModel.setDividend_yield(SummaryObject.getJSONObject("dividendYield").getString("raw"));
//                    }
//                    equityModel.setExchange_id(PriceObject.getString("exchange"));
//
//                    equityModel.setIssuer_name("-");
//                    equityModel.setIssuer_name_lang("-");
//                    equityModel.setMarket_cap(SummaryObject.getJSONObject("marketCap").getString("raw"));
//                    equityModel.setPe_ratio(SummaryObject.getJSONObject("trailingPE").getString("raw"));
//                    equityModel.setPre_mkt_change("-");
//                    equityModel.setPre_mkt_chg_percent("-");
//                    equityModel.setPre_mkt_price("-");
//                    equityModel.setRealtime_change(PriceObject.getJSONObject("regularMarketChange").getString("raw"));
//                    equityModel.setRealtime_chg_percent(PriceObject.getJSONObject("regularMarketChangePercent").getString("raw"));
//                    equityModel.setRealtime_price(PriceObject.getJSONObject("regularMarketChangePercent").getString("raw"));
//                    equityModel.setTime(dateFormat.format(date));
//                    equityModel.setTs("-");
//                    equityModel.setVolume(PriceObject.getJSONObject("regularMarketVolume").getString("raw"));
//                    equityModel.setRealtime_ts("-");
//                    equityModel.setYear_high(SummaryObject.getJSONObject("fiftyTwoWeekHigh").getString("raw"));
//                    equityModel.setYear_low(SummaryObject.getJSONObject("fiftyTwoWeekLow").getString("raw"));
//                    this.homeAdapter.notifyDataSetChanged();

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
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (RuntimeException e){
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
           handler.post(()->{
               onPostExecute(data);
           });
        });
    }





}

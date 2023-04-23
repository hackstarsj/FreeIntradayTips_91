package com.silverlinesoftwares.intratips.tasks;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silverlinesoftwares.intratips.listeners.StockDetailListener;
import com.silverlinesoftwares.intratips.models.EquityModel;
import com.silverlinesoftwares.intratips.models.SummaryModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StockDetailTask {


    private final String symbol;
    private final StockDetailListener   chartListener;
    private final EquityModel equityModel=new EquityModel();

    public StockDetailTask(String symbol, StockDetailListener chartListener) {
        this.symbol=symbol;
        this.chartListener=chartListener;
    }


    public void execute(String... strings) {
        Executor executor = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> doInBackground(strings,handler));
    }



    protected void doInBackground(String[] objects,Handler handler) {
        String SummaryData=LoadSummaryData(symbol);
//        publishProgress(new String[]{"0",SummaryData});
        String SummaryData1=LoadSummaryData2(symbol);
//        publishProgress(new String[]{"1",SummaryData1});
        String SummaryData3=LoadSummaryData3(symbol);
        onPostExecute(SummaryData,SummaryData1,SummaryData3,handler);

    }

    private String LoadSummaryData2(String symbol) {
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout

        Request request =
                new Request.Builder()
                        .url("https://query2.finance.yahoo.com/v10/finance/quoteSummary/"+symbol+"?formatted=true&modules=price%2CsummaryDetail&corsDomain=in.finance.yahoo.com")
                        .addHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:67.0) Gecko/20100101 Firefox/67.0")
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
        if (response != null && response.isSuccessful()) {
            try {
                if (response.body() != null) {
                    return response.body().string();
                }
                else{
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String LoadSummaryData3(String symbol) {
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout

        Request request =
                new Request.Builder()
                        .url("https://www.nseindia.com/get-quotes/equity?symbol="+symbol.replace(".NS","").replace(".BO",""))
                        .addHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:67.0) Gecko/20100101 Firefox/67.0")
                        .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()) {
            if (response.body() != null) {
                //return response.body().string();
                List<String> header=response.headers("Set-Cookie");
                StringBuilder dd= new StringBuilder();
                for (int i=0;i<header.size();i++){
                    dd.append("").append(header.get(i)).append(";");
                }

                return LoadSummaryData5(symbol, dd.toString());
            }
            else{
                return null;
            }
        }
        return null;
    }

    private String LoadSummaryData5(String symbol,String dd) {
        try {

            OkHttpClient client2 = new OkHttpClient();
            client2.retryOnConnectionFailure();
            client2.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build(); // connect timeout

            Request request2 =
                    new Request.Builder()
                            .url("https://www.nseindia.com/api/quote-equity?symbol=" + symbol.replace(".NS", "").replace(".BO", ""))
                            .addHeader("Cookie",dd)
                            .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:67.0) Gecko/20100101 Firefox/67.0")
                            .build();

            Response response2 = null;
            try {
                response2 = client2.newCall(request2).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response2 != null && response2.isSuccessful()) {
                if (response2.body() != null) {
                    return response2.body().string();
                } else {
                    return null;
                }

            }
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    private String LoadSummaryData(String symbol) {
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout

        Request request =
                new Request.Builder()
                        .url("https://www.screener.in/company/"+symbol.replace(".NS","").replace(".BO","")+"/consolidated/#top")
                        .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()) {
            try {
                if (response.body() != null) {
                    return response.body().string();
                }
                else{
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    protected void onPostExecute(String value1, String value2, String value3, Handler handler) {
        SummaryModel summaryModel=new SummaryModel();
            if(value1!=null) {
                Document document = Jsoup.parse(value1);
                Elements data_element = document.getElementsByClass("flex-space-between");
                try {
                    summaryModel.setBook_value(data_element.get(8).children().get(1).text());
                    summaryModel.setRoce(data_element.get(10).children().get(1).text());
                    summaryModel.setRoe(data_element.get(11).children().get(1).text());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }




            if(value2!=null) {

                    Gson gson = new GsonBuilder().create();
                    try {
                        JSONObject jsonObject = new JSONObject(value2);

                        JSONObject finance = jsonObject.getJSONObject("quoteSummary").getJSONArray("result").getJSONObject(0);
                        JSONObject SummaryObject = finance.getJSONObject("summaryDetail");
                        JSONObject PriceObject = finance.getJSONObject("price");

                        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm a");
                        Date date = new Date();
                        // System.out.println(dateFormat.format(date));
                        equityModel.setHigh(SummaryObject.getJSONObject("dayHigh").getString("fmt"));
                        equityModel.setLow(SummaryObject.getJSONObject("dayLow").getString("fmt"));
                        equityModel.setPr_close(SummaryObject.getJSONObject("previousClose").getString("fmt"));
                        equityModel.setCmp_price(PriceObject.getJSONObject("regularMarketPrice").getString("raw"));
                        equityModel.setOpens(SummaryObject.getJSONObject("open").getString("fmt"));
                        equityModel.setRealtime_time(dateFormat.format(date));
                        equityModel.setPrev_close(SummaryObject.getJSONObject("previousClose").getString("raw"));
                        equityModel.setDay_low(SummaryObject.getJSONObject("dayLow").getString("raw"));
                        equityModel.setDay_high(SummaryObject.getJSONObject("dayHigh").getString("raw"));
                        equityModel.setPrice(PriceObject.getJSONObject("regularMarketPrice").getString("raw"));
                        equityModel.setOpen(SummaryObject.getJSONObject("open").getString("raw"));
                        //equityModel.setRealtime_time(SummaryObject.getJSONObject("previousClose").getString("fmt"));
                        equityModel.setAvg_3m_volume(SummaryObject.getJSONObject("averageVolume").getString("raw"));
                        equityModel.setChange(PriceObject.getJSONObject("regularMarketChange").getString("raw"));
                        equityModel.setChg_percent(PriceObject.getJSONObject("regularMarketChangePercent").getString("raw"));
                        equityModel.setCurrency(PriceObject.getString("currency"));
                        equityModel.setData_type(PriceObject.getString("quoteType"));
                        if (SummaryObject.has("dividendRate")){
                            if(SummaryObject.getJSONObject("dividendRate").has("fmt")) {
                                equityModel.setDividend_rate(SummaryObject.getJSONObject("dividendRate").getString("fmt"));
                            }
                            else{
                                equityModel.setDividend_rate("-");
                            }
                        }
                        else{
                            equityModel.setDividend_rate("-");
                        }
                        equityModel.setEps_curr_year(SummaryObject.getJSONObject("previousClose").getString("raw"));


                        equityModel.setExchange(PriceObject.getString("exchangeName"));
                        equityModel.setExchange_external_id(PriceObject.getString("exchange"));

                        if(SummaryObject.has("dividendYield")) {
                            if(SummaryObject.getJSONObject("dividendYield").has("fmt")) {
                                equityModel.setDividend_yield(SummaryObject.getJSONObject("dividendYield").getString("fmt"));
                            }
                            else{
                                equityModel.setDividend_rate("-");
                            }
                        }
                        else{
                            equityModel.setDividend_rate("-");
                        }
                        equityModel.setExchange_id(PriceObject.getString("exchange"));

                        equityModel.setIssuer_name("-");
                        equityModel.setIssuer_name_lang("-");
                        equityModel.setMarket_cap(SummaryObject.getJSONObject("marketCap").getString("raw"));
                        if(SummaryObject.has("trailingPE")) {
                            equityModel.setPe_ratio(SummaryObject.getJSONObject("trailingPE").getString("raw"));
                        }
                        else{
                            equityModel.setPe_ratio("0");
                        }
                        equityModel.setPre_mkt_change("-");
                        equityModel.setPre_mkt_chg_percent("-");
                        equityModel.setPre_mkt_price("-");
                        equityModel.setRealtime_change(PriceObject.getJSONObject("regularMarketChange").getString("raw"));
                        equityModel.setRealtime_chg_percent(PriceObject.getJSONObject("regularMarketChangePercent").getString("raw"));
                        equityModel.setRealtime_price(PriceObject.getJSONObject("regularMarketChangePercent").getString("raw"));
                        equityModel.setTime(dateFormat.format(date));
                        equityModel.setTs("-");
                        equityModel.setVolume(PriceObject.getJSONObject("regularMarketVolume").getString("raw"));
                        equityModel.setRealtime_ts("-");
                        equityModel.setYear_high(SummaryObject.getJSONObject("fiftyTwoWeekHigh").getString("raw"));
                        equityModel.setYear_low(SummaryObject.getJSONObject("fiftyTwoWeekLow").getString("raw"));



                        double amount = Double.parseDouble(equityModel.getVolume());
                        Locale locale = new Locale("en", "IN");
                        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);



                        summaryModel.setMarket_cap(""+currencyFormatter.format(Double.parseDouble(equityModel.getMarket_cap())));

                        Locale locale1 = new Locale("en", "US");
                        NumberFormat currencyFormatter1 = NumberFormat.getCurrencyInstance(locale1);
                        summaryModel.setVolume(""+currencyFormatter1.format(amount).replace("$",""));
                        summaryModel.setHigh52(Html.fromHtml(equityModel.getYear_high()));
                        summaryModel.setLow52(Html.fromHtml(equityModel.getYear_low()));
                        summaryModel.setAverageVolume(""+currencyFormatter1.format(Double.parseDouble(equityModel.getAvg_3m_volume())).replace("$",""));
                        DecimalFormat df=new DecimalFormat("##.00");
                        String formate = df.format(Double.parseDouble(equityModel.getPe_ratio()));
                        summaryModel.setStockPe(formate);
                        summaryModel.setDividendYield(equityModel.getDividend_rate()+" ("+equityModel.getDividend_yield()+"%)");

                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }


        }

            if(value3!=null) {
                try {

                    JSONObject jsonObject=new JSONObject(value3);
                        String text = "<font color=#000000>"+jsonObject.getJSONObject("priceInfo").getJSONObject("weekHighLow").getString("min")+"</font> / <font color=#d50000>"+jsonObject.getJSONObject("priceInfo").getJSONObject("weekHighLow").getString("minDate")+"</font>";
                        String text1 = "<font color=#000000>"+jsonObject.getJSONObject("priceInfo").getJSONObject("weekHighLow").getString("max")+"</font> / <font color=#00e676>"+jsonObject.getJSONObject("priceInfo").getJSONObject("weekHighLow").getString("maxDate")+"</font>";
                        summaryModel.setHigh52(Html.fromHtml(text1));
                        summaryModel.setLow52(Html.fromHtml(text));
                        summaryModel.setvWap(jsonObject.getJSONObject("priceInfo").getString("vwap"));
                    summaryModel.setFaceValue(jsonObject.getJSONObject("securityInfo").getString("faceValue"));
                    summaryModel.setLowerPriceBand(jsonObject.getJSONObject("priceInfo").getString("lowerCP"));
                     summaryModel.setUpperPriceband(jsonObject.getJSONObject("priceInfo").getString("upperCP"));
                 //   }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }


        }

            handler.post(()-> chartListener.onSummayLoaded(summaryModel));
    }
}

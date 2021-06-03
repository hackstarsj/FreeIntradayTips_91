package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.silverlinesoftwares.intratips.listeners.ActiveStockListener;
import com.silverlinesoftwares.intratips.listeners.PriceBandListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PricebandTask  {


    PriceBandListener gainerLooserListener;

    public PricebandTask(PriceBandListener gainerLooserListener){
        this.gainerLooserListener=gainerLooserListener;
    }

    protected void onPostExecute(JSONObject string) {
        if(string!=null){
            gainerLooserListener.onSucess(string);
        }
        else{
            gainerLooserListener.onFailed("Server Error");
        }

    }

    protected JSONObject doInBackground(String... strings) {
            JSONObject getVolume=getVolume();
            JSONObject getValues=getValues();
            JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("upper",getVolume);
            jsonObject.put("lower",getValues);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject getValues() {
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout
        Date date=new Date();
        long timeMilli = date.getTime();
        timeMilli=timeMilli-(1000*60);

        String url="https://www1.nseindia.com/live_market/dynaContent/live_analysis/price_band/allSecuritiesLower.json";


        Request request =
                new Request.Builder()
                        .url(url)
                        .addHeader("User-Agent","Mozilla/5.0 (compatible; Rigor/1.0.0; http://rigor.com)")
                        .addHeader("Accept","*/*")
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
                        return jsonObject;
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

    private JSONObject getVolume() {
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout
        Date date=new Date();
        long timeMilli = date.getTime();
        timeMilli=timeMilli-(1000*60);

        String url="https://www1.nseindia.com/live_market/dynaContent/live_analysis/price_band/allSecuritiesUpper.json";


        Request request =
                new Request.Builder()
                        .url(url)
                        .addHeader("User-Agent","Mozilla/5.0 (compatible; Rigor/1.0.0; http://rigor.com)")
                        .addHeader("Accept","*/*")
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
                        return jsonObject;
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
            JSONObject data=doInBackground(strings);
            handler.post(()->{
                onPostExecute(data);
            });
        });
    }

}

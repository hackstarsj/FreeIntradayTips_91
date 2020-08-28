package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;

import com.silverlinesoftwares.intratips.listeners.ActiveStockListener;
import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActiveStockTask extends AsyncTask<String ,String ,JSONObject> {


    ActiveStockListener gainerLooserListener;

    public ActiveStockTask(ActiveStockListener gainerLooserListener){
        this.gainerLooserListener=gainerLooserListener;
    }

    @Override
    protected void onPostExecute(JSONObject string) {
        super.onPostExecute(string);
        if(string!=null){
            gainerLooserListener.onSucess(string);
        }
        else{
            gainerLooserListener.onFailed("Server Error");
        }

    }

    @Override
    protected JSONObject doInBackground(String... strings) {
            JSONObject getVolume=getVolume();
            JSONObject getValues=getValues();
            JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("volumess",getVolume);
            jsonObject.put("valuess",getValues);
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

        String url="https://www1.nseindia.com/live_market/dynaContent/live_analysis/most_active/allTopValue1.json";


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

        String url="https://www1.nseindia.com/live_market/dynaContent/live_analysis/most_active/allTopVolume1.json";


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


}

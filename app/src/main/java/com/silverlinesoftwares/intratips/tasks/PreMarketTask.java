package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;

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

public class PreMarketTask  {


    GainerLooserListener gainerLooserListener;

    public PreMarketTask(GainerLooserListener gainerLooserListener){
        this.gainerLooserListener=gainerLooserListener;
    }

    protected void onPostExecute(String string) {
        if(string!=null){
            gainerLooserListener.onSucess(string);
        }
        else{
            gainerLooserListener.onFailed("Server Error");
        }

    }

    protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            client.retryOnConnectionFailure();
            client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build(); // connect timeout
        Date date=new Date();
        long timeMilli = date.getTime();
        timeMilli=timeMilli-(1000*60);

        String url="";

        if(strings[0].equalsIgnoreCase("0")) {
            url = "https://www1.nseindia.com/live_market/dynaContent/live_analysis/pre_open/nifty.json";
        }
        else if(strings[0].equalsIgnoreCase("1")) {
            url = "https://www1.nseindia.com/live_market/dynaContent/live_analysis/pre_open/fo.json";
        }
        else if(strings[0].equalsIgnoreCase("2")) {
            url = "https://www1.nseindia.com/live_market/dynaContent/live_analysis/pre_open/niftybank.json";
        }
        else if(strings[0].equalsIgnoreCase("3")) {
            url = "https://www1.nseindia.com/live_market/dynaContent/live_analysis/pre_open/sme.json";
        }
        else{
            url="https://www1.nseindia.com/live_market/dynaContent/live_analysis/pre_open/other.json";
        }





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
            String data=doInBackground(strings);
            handler.post(()->{
                onPostExecute(data);
            });
        });
    }






}

package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;

import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HighLowTask extends AsyncTask<String ,String ,String> {


    GainerLooserListener gainerLooserListener;

    public HighLowTask(GainerLooserListener gainerLooserListener){
        this.gainerLooserListener=gainerLooserListener;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        if(string!=null){
            gainerLooserListener.onSucess(string);
        }
        else{
            gainerLooserListener.onFailed("Server Error");
        }

    }

    @Override
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
            url = "https://www1.nseindia.com/products/dynaContent/equities/equities/json/online52NewLow.json";
        }
        else
            {
            url = "https://www1.nseindia.com/products/dynaContent/equities/equities/json/online52NewHigh.json";
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






}

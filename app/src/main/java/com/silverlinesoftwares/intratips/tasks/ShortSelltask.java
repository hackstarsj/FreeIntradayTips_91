package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;

import com.silverlinesoftwares.intratips.listeners.ChartListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShortSelltask extends AsyncTask<String,String ,String > {


    ChartListener chartListener;
    public ShortSelltask(ChartListener chartListener) {
        this.chartListener=chartListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout

        Request request =
                new Request.Builder()
                        .url("https://www1.nseindia.com/products/dynaContent/equities/equities/shortselling.jsp?symbol=&segmentLink=14&symbolCount=&dateRange=15days&fromDate=&toDate=&dataType=SHORT_SELLING")
                        .addHeader("User-Agent","Mozilla/5.0 (compatible; Rigor/1.0.0; http://rigor.com)")
                        .addHeader("Accept","*/*")
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s==null){
            chartListener.onFailed("Something Went Wrong!");
        }
        else if(s.isEmpty()){
            chartListener.onFailed("Something Went Wrong!");
        }
        else {
            chartListener.onSucess(s);
        }

    }



}

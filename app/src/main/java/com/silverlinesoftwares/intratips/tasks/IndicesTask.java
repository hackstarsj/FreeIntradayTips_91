package com.silverlinesoftwares.intratips.tasks;

import android.os.Handler;
import android.os.Looper;

import com.silverlinesoftwares.intratips.listeners.ChartListener;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IndicesTask  {


    final ChartListener chartListener;
    public IndicesTask(ChartListener chartListener) {
        this.chartListener=chartListener;
    }

    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build(); // connect timeout

        Request request =
                new Request.Builder()
                        .url("https://finance.yahoo.com/world-indices?guccounter=1")
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

    protected void onPostExecute(String s) {
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



    public void execute(String... strings) {
        Executor executor = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            String data=doInBackground(strings);
            handler.post(()-> onPostExecute(data));
        });
    }
}

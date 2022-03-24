package com.silverlinesoftwares.intratips.util;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;


import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoDataLoader  {

    private final MyAsyncListener myAsyncListener;

    public VideoDataLoader(MyAsyncListener myAsyncListener) {
        this.myAsyncListener = myAsyncListener;
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

    protected String doInBackground(String[] objects) {
        String vvv=BuilData();
        return vvv;
    }

    protected void onPostExecute(String s) {
        if(s!=null) {
            myAsyncListener.onSuccessfulExecute(s);
        }
        else{
            myAsyncListener.onSuccessfulExecute("");
        }
    }

    private String BuilData() {

        OkHttpClient client = new OkHttpClient();

        client.retryOnConnectionFailure();
        try {
            client.newBuilder().connectTimeout(600, TimeUnit.SECONDS)
                    .writeTimeout(600, TimeUnit.SECONDS)
                    .readTimeout(600, TimeUnit.SECONDS)
                    .build(); // connect timeout
        }
        catch (Exception e){
            e.printStackTrace();
            client.newBuilder().connectTimeout(600, TimeUnit.SECONDS)
                    .writeTimeout(600, TimeUnit.SECONDS)
                    .readTimeout(600, TimeUnit.SECONDS)
                    .build(); // connect timeout
        }

        Request request =
                new Request.Builder()
                        .url("https://furthergrow.silverlinesoftwares.com/intra_video.php")
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
                    return "failed";
                }
            } catch (IOException e) {
                if(response.body()!=null){
                    response.close();
                }
                e.printStackTrace();
            }
        }

        return "failed";

    }
}
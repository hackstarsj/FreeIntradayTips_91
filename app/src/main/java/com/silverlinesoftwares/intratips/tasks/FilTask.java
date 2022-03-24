package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.silverlinesoftwares.intratips.listeners.ChartListener;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FilTask  {


    ChartListener chartListener;
    public FilTask(ChartListener chartListener) {
        this.chartListener=chartListener;
    }

    protected String doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();
//        client.cookieJar(new JavaNetCookieJar())
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout

        Request request =

                new Request.Builder()
                        .url("https://www.fpi.nsdl.co.in/web/Reports/Monthly.aspx")
                        .addHeader("Cookie","ASP.NET_SessionId=zwugxhmmuep4kxx3wpkz0kqt; NL01ba3203=01e02e5d4a346eab4bfc926f349af862dea883d7541fbab4ecca1b000b2ca212a5bc3fcd2f990111ea122b8ac819374e84ee4e68ad; NLf964cd13027=080333663bab2000641c843f24ea3caeb49bb4f9ebeba1f8bc606f98a647a5ebff2fdbda36bc0cd60836942d9511300082d37201c01d858383fc4ff6b8487443b92b7129d07a5af1827e3be47e9255394f527579b67c4d2af6b91af92d5069e8; _fpi123456789=46002d8d968548c7b674274ed37d809c")
                        .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.82 Safari/537.36")
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
            handler.post(()->{
                onPostExecute(data);
            });
        });
    }



}

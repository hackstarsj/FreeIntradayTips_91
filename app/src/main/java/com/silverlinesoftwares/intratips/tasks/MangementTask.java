package com.silverlinesoftwares.intratips.tasks;

import android.os.Handler;
import android.os.Looper;

import com.silverlinesoftwares.intratips.listeners.ManagementListener;
import com.silverlinesoftwares.intratips.models.MajorHolderModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MangementTask  {


    final ManagementListener gainerLooserListener;

    public MangementTask(ManagementListener gainerLooserListener){
        this.gainerLooserListener=gainerLooserListener;
    }

    protected void onPostExecute(String string) {
        if(string!=null){


                List<MajorHolderModel> majorHolderModels=BuildData(string);
            gainerLooserListener.onSucess(majorHolderModels);

            // gainerLooserListener.onSucess(string);
        }
        else{
            gainerLooserListener.onFailed("Server Error!");
        }

    }

    private List<MajorHolderModel> BuildData(String string) {
        Document document= Jsoup.parse(string);
        List<MajorHolderModel> majorHolderModels=new ArrayList<>();
        try {
            Elements tables = document.getElementsByTag("table");
            Elements tr = tables.get(0).getElementsByTag("tr");
            for (int i = 0; i < tr.size(); i++) {
                String title = tr.get(i).children().get(0).text();
                String data_1 = tr.get(i).children().get(1).text();
                String data_2 = tr.get(i).children().get(2).text();
                String data_3 = tr.get(i).children().get(3).text();
                String data_4 = tr.get(i).children().get(4).text();

                if (i == 0) {
                    majorHolderModels.add(new MajorHolderModel(title, data_1, data_2, data_3, data_4, true));
                } else {
                    majorHolderModels.add(new MajorHolderModel(title, data_1, data_2, data_3, data_4, false));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return majorHolderModels;

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

        String url="https://finance.yahoo.com/quote/"+strings[0]+"/profile?p="+strings[0];

            Request request =
                    new Request.Builder()
                            .url(url)
                            .addHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0")
                            .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            }
        if (response != null && response.isSuccessful()) {
                try {
                    if (response.body() != null) {
                        return response.body().string();
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
            handler.post(()-> onPostExecute(data));
        });
    }



}

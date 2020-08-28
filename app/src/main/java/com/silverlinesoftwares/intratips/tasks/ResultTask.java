package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.silverlinesoftwares.intratips.listeners.ManagementListener;
import com.silverlinesoftwares.intratips.listeners.ResultListener;
import com.silverlinesoftwares.intratips.models.MajorHolderModel;
import com.silverlinesoftwares.intratips.models.ResultModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResultTask extends AsyncTask<String ,String ,String> {


    ResultListener gainerLooserListener;

    public ResultTask(ResultListener gainerLooserListener){
        this.gainerLooserListener=gainerLooserListener;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        if(string!=null){


                List<ResultModel> majorHolderModels=new ArrayList<>();

            Type listType = new TypeToken<List<ResultModel>>() {}.getType();

            List<ResultModel> yourList = new Gson().fromJson(string, listType);
            majorHolderModels.addAll(yourList);
                if(majorHolderModels==null){
                    gainerLooserListener.onFailed("Server Error!");
                }
                else {
                    gainerLooserListener.onSucess(majorHolderModels);
                }

            // gainerLooserListener.onSucess(string);
        }
        else{
            gainerLooserListener.onFailed("Server Error!");
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

        String url="https://furthergrow.silverlinesoftwares.com/result_data.php";

            Request request =
                    new Request.Builder()
                            .url(url)
                            .addHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0")
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
                        return data;
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

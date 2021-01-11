package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;

import com.silverlinesoftwares.intratips.listeners.IncomeStateListener;
import com.silverlinesoftwares.intratips.listeners.ShareHolderListener;
import com.silverlinesoftwares.intratips.models.DataTypeObject;
import com.silverlinesoftwares.intratips.models.IncomeStatementModel;
import com.silverlinesoftwares.intratips.models.InsiderRosterModel;
import com.silverlinesoftwares.intratips.models.InsiderTransactionModel;
import com.silverlinesoftwares.intratips.models.MajorHolderModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShareHolderTask extends AsyncTask<String ,List<Object> ,String>{


    ShareHolderListener gainerLooserListener;

    public ShareHolderTask(ShareHolderListener gainerLooserListener){
        this.gainerLooserListener=gainerLooserListener;
    }

    @Override
    protected void onPostExecute(String majorHolderModels) {
        super.onPostExecute(majorHolderModels);
    }

    @Override
    protected String doInBackground(String... strings) {

        List<MajorHolderModel> majorHolderModels=getMajorList(strings[0]);
        List<Object> objects=new ArrayList<>();
        objects.add(new DataTypeObject("1"));
        objects.add(majorHolderModels);
        publishProgress(objects);


        List<InsiderRosterModel> majorHolderModels1=getInsiderRoast(strings[0]);
        List<Object> objects1=new ArrayList<>();
        objects1.add(new DataTypeObject("2"));
        objects1.add(majorHolderModels1);
        publishProgress(objects1);
//
        List<InsiderTransactionModel> majorHolderModels2=getInsidertransaction(strings[0]);
        List<Object> objects2=new ArrayList<>();
        objects2.add(new DataTypeObject("3"));
        objects2.add(majorHolderModels2);
        publishProgress(objects2);
        return null;

    }

    @Override
    protected void onProgressUpdate(List<Object>... values) {
        super.onProgressUpdate(values);
        List<List<Object>> list= Arrays.asList(values);
        DataTypeObject list1=(DataTypeObject)list.get(0).get(0);

        if(list1.getId().equalsIgnoreCase("1")) {
            List<MajorHolderModel> list2=(List<MajorHolderModel>)(Object)list.get(0).get(1);
            if(list2!=null) {
                gainerLooserListener.onMajorLoaded(list2);
            }
            else{
                gainerLooserListener.onFailed("Failed to load Major Holders");
            }
        }
        if(list1.getId().equalsIgnoreCase("2")) {
            List<InsiderRosterModel> list2=(List<InsiderRosterModel>)(Object)list.get(0).get(1);
            if(list2!=null) {
                gainerLooserListener.onInsiderLoader(list2);
            }
            else{
                gainerLooserListener.onFailed("Failed to load Insider Roaster");
            }
        }
        if(list1.getId().equalsIgnoreCase("3")) {
            List<InsiderTransactionModel> list2=(List<InsiderTransactionModel>)(Object)list.get(0).get(1);
            if(list2!=null) {
                gainerLooserListener.onInsiderTransaction(list2);
            }
            else{
                gainerLooserListener.onFailed("Failed to load Insider Roaster");
            }
        }
    }

    private List<MajorHolderModel> getMajorList(String symbol){
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout
        Date date=new Date();
        long timeMilli = date.getTime();
        timeMilli=timeMilli-(1000*60);

        String url="https://finance.yahoo.com/quote/"+symbol+"/holders?p="+symbol;

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
                    return  BuildMajorData(data);
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    private List<MajorHolderModel> BuildMajorData(String data) {
        Document document= Jsoup.parse(data);
        List<MajorHolderModel> majorHolderModels = new ArrayList<>();
        try {
            Elements tables = document.getElementsByTag("table");
            if (tables.size() > 0) {
                Elements tr = tables.get(0).getElementsByTag("tr");

                for (int i = 0; i < tr.size(); i++) {
                    String title = tr.get(i).children().get(0).text();
                    String data_1 = tr.get(i).children().get(1).text();
                    String data_2 = tr.get(i).children().get(2).text();
                    String data_3 = tr.get(i).children().get(3).text();
                  //  String data_4 = tr.get(i).children().get(4).text();

                    if (i == 0) {
                        majorHolderModels.add(new MajorHolderModel(title, data_1, data_2, data_3, "data_4", true));
                    } else {
                        majorHolderModels.add(new MajorHolderModel(title, data_1, data_2, data_3, "data_4", false));
                    }
                }

                return majorHolderModels;
            } else {
                return majorHolderModels;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return majorHolderModels;
        }



    }

    private List<InsiderRosterModel> getInsiderRoast(String symbol){
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout
        Date date=new Date();
        long timeMilli = date.getTime();
        timeMilli=timeMilli-(1000*60);

        String url="https://finance.yahoo.com/quote/"+symbol+"/insider-roster?p="+symbol;

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
                    return  BuildInsiderRoast(data);
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    private List<InsiderRosterModel> BuildInsiderRoast(String data) {
        List<InsiderRosterModel> majorHolderModels=new ArrayList<>();

        try {
            Document document = Jsoup.parse(data);
            Elements tables = document.getElementsByTag("table");
            Elements tr = tables.get(0).getElementsByTag("tr");
            for (int i = 0; i < tr.size(); i++) {
                if (tr.get(i).children().size() > 2) {
                    String title = tr.get(i).children().get(0).text();
                    String data_1 = tr.get(i).children().get(1).text();
                    String data_2 = tr.get(i).children().get(2).text();
                    String data_3 = tr.get(i).children().get(3).text();

                    if (i == 0) {
                        majorHolderModels.add(new InsiderRosterModel(title, data_1, data_2, data_3, true));
                    } else {
                        majorHolderModels.add(new InsiderRosterModel(title, data_1, data_2, data_3, false));
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return majorHolderModels;
    }

    private List<InsiderTransactionModel> getInsidertransaction(String symbol){
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout
        Date date=new Date();
        long timeMilli = date.getTime();
        timeMilli=timeMilli-(1000*60);
        String url="https://finance.yahoo.com/quote/"+symbol+"/insider-transactions?p="+symbol;

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
                    return BuildInsiderTransaction(data);
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    private List<InsiderTransactionModel> BuildInsiderTransaction(String data) {
        Document document= Jsoup.parse(data);
        List<InsiderTransactionModel> majorHolderModels=new ArrayList<>();
        try {
            Elements tables = document.getElementsByTag("table");
         //   if(tables.size()>1){
            Elements tr = tables.get(2).getElementsByTag("tr");

            for (int i = 0; i < tr.size(); i++) {
                String title = tr.get(i).children().get(0).text();
                String data_1 = tr.get(i).children().get(1).text();
                String data_2 = tr.get(i).children().get(2).text();
                String data_3 = tr.get(i).children().get(3).text();
                String data_4 = tr.get(i).children().get(4).text();
                String data_5 = tr.get(i).children().get(5).text();

                if (i == 0) {
                    majorHolderModels.add(new InsiderTransactionModel(title, data_1, data_2, data_3, data_4, data_5, true));
                } else {
                    majorHolderModels.add(new InsiderTransactionModel(title, data_1, data_2, data_3, data_4, data_5, false));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return majorHolderModels;
    }

}

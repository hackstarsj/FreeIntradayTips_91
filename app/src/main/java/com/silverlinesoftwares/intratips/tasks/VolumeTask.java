package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;

import com.silverlinesoftwares.intratips.listeners.ChartListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VolumeTask extends AsyncTask<String,String ,String > {


    ChartListener chartListener;
    public VolumeTask(ChartListener chartListener) {
        this.chartListener=chartListener;
    }

    @Override
    protected String doInBackground(String... strings) {

        String symbol=strings[0].replace(".NS","").replace(".BO","");

        String pattern = "dd-MM-yyyy";
        Date date=new Date();
        String dateInString =new SimpleDateFormat(pattern).format(date);
        String endtime=dateInString;
        String starttime="";

        if(strings[1].equalsIgnoreCase("day")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            Date dateBefore30Days = cal.getTime();
            starttime =new SimpleDateFormat(pattern).format(dateBefore30Days);
        }
        if(strings[1].equalsIgnoreCase("week")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -7);
            Date dateBefore30Days = cal.getTime();
            starttime =new SimpleDateFormat(pattern).format(dateBefore30Days);
        }
        if(strings[1].equalsIgnoreCase("1month")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -30);
            Date dateBefore30Days = cal.getTime();
            starttime =new SimpleDateFormat(pattern).format(dateBefore30Days);
        }
        if(strings[1].equalsIgnoreCase("3month")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -90);
            Date dateBefore30Days = cal.getTime();
            starttime =new SimpleDateFormat(pattern).format(dateBefore30Days);
        }
        if(strings[1].equalsIgnoreCase("6month")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -180);
            Date dateBefore30Days = cal.getTime();
            starttime =new SimpleDateFormat(pattern).format(dateBefore30Days);
        }
        if(strings[1].equalsIgnoreCase("12month")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -365);
            Date dateBefore30Days = cal.getTime();
            starttime =new SimpleDateFormat(pattern).format(dateBefore30Days);
        }

        return LoadData(symbol,starttime,endtime);


    }

    private String LoadData(String symbol,String starttime,String endtime) {
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout

        Request request =
                new Request.Builder()
                        .url("https://www.nseindia.com/get-quotes/equity?symbol="+symbol.replace(".NS","").replace(".BO",""))
                        .addHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:67.0) Gecko/20100101 Firefox/67.0")
                        .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()) {
            if (response.body() != null) {
                //return response.body().string();
                List<String> header=response.headers("Set-Cookie");
                String dd="";
                for (int i=0;i<header.size();i++){
                    dd+=""+header.get(i)+";";
                }

                String data=getVolume(symbol,starttime,endtime,dd);
                return data;
            }
            else{
                return null;
            }
        }
        return null;
    }

    private  String getVolume(String symbol,String starttime,String endtime,String cookie){
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout

        Request request =
                new Request.Builder()
                        .url("https://www.nseindia.com/api/historical/cm/equity?symbol="+symbol+"&series=[%22EQ%22]&from="+starttime+"&to="+endtime)
                        .addHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:67.0) Gecko/20100101 Firefox/67.0")
                        .addHeader("X-Requested-With","XMLHttpRequest")
                        .addHeader("Cookie",cookie)
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

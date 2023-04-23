package com.silverlinesoftwares.intratips.tasks;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.silverlinesoftwares.intratips.listeners.ChartListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VolumeTask {


    final ChartListener chartListener;
    public VolumeTask(ChartListener chartListener) {
        this.chartListener=chartListener;
    }

    @SuppressLint("SimpleDateFormat")
    public void execute(String... strings) {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.getMainLooper());
        executor.execute(()-> {


            String symbol = strings[0].replace(".NS", "").replace(".BO", "");

            String pattern = "dd-MM-yyyy";
            Date date = new Date();
            String starttime = "";

            if (strings[1].equalsIgnoreCase("day")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, -1);
                Date dateBefore30Days = cal.getTime();
                starttime = new SimpleDateFormat(pattern).format(dateBefore30Days);
            }
            if (strings[1].equalsIgnoreCase("week")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, -7);
                Date dateBefore30Days = cal.getTime();
                starttime = new SimpleDateFormat(pattern).format(dateBefore30Days);
            }
            if (strings[1].equalsIgnoreCase("1month")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, -30);
                Date dateBefore30Days = cal.getTime();
                starttime = new SimpleDateFormat(pattern).format(dateBefore30Days);
            }
            if (strings[1].equalsIgnoreCase("3month")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, -90);
                Date dateBefore30Days = cal.getTime();
                starttime = new SimpleDateFormat(pattern).format(dateBefore30Days);
            }
            if (strings[1].equalsIgnoreCase("6month")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, -180);
                Date dateBefore30Days = cal.getTime();
                starttime = new SimpleDateFormat(pattern).format(dateBefore30Days);
            }
            if (strings[1].equalsIgnoreCase("12month")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, -365);
                Date dateBefore30Days = cal.getTime();
                starttime = new SimpleDateFormat(pattern).format(dateBefore30Days);
            }



           String  data=LoadData(symbol, starttime, new SimpleDateFormat(pattern).format(date));
            handler.post(()-> onPostExecute(data));

        });

    }

    private String  LoadData(String symbol,String starttime,String endtime) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://www.nseindia.com/get-quotes/equity?symbol=COALINDIA")
                    .method("GET", null)
                    .addHeader("Cookie", "ak_bmsc=ABD1E2745EA470D35DB3BA6B73987811~000000000000000000000000000000~YAAQDj/LF/47/d9/AQAAcZ2y5A86muWEKRF+gwFQ3sLHfH97u7Gl35qIuZCwbE7oMvrq9zSAL0SghhPp0FwM69yNgqJKU64xxfvPTB2Gn3L5Nq9HNd7jBs4AHSBqO7Z7l7M3JMUweCKGHk6sZ5ZXFYms6pt/1j2AEQbLTgQrUrBYDi8CfFqFRErclSu2diPksccpeT9jo7ymT7QjYacczzwGrPgpIV+PyT7YdNqYDAxUPkQ857InbsSFiGB2D3wNyniUseKLozyzhPcRuzOXjpgrSRUhZhVOS6CFUa86JTP84nfmiTQ+CjrCGgAfqpREcetX0Tnx8xFtqGB6fRQP+rV30hvBHxfdz+JYFg6cHhjX4g4mtxgOzSP6XVYFnA==; nseappid=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhcGkubnNlIiwiYXVkIjoiYXBpLm5zZSIsImlhdCI6MTY0ODgwOTM4NSwiZXhwIjoxNjQ4ODEyOTg1fQ.kCOZD0DczWb0-_PrRJibTLwcCn1QFNt47EFlVRY6Z5k; nsit=h-crJCiJA6nf9ogX7RUfzLjG")
                    .build();
            Response response = client.newCall(request).execute();
            //return response.body().string();
            List<String> header=response.headers("Set-Cookie");
                StringBuilder dd= new StringBuilder();
                for (int i=0;i<header.size();i++){
                    dd.append("").append(header.get(i)).append(";");
                }

            return getVolume(symbol,starttime,endtime, dd.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
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

    public void onPostExecute(String s) {
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

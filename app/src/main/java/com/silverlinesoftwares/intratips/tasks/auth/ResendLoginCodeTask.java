package com.silverlinesoftwares.intratips.tasks.auth;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silverlinesoftwares.intratips.listeners.ResendCodeListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResendLoginCodeTask {

    private final ResendCodeListener apiResponseListener;
    public ResendLoginCodeTask(ResendCodeListener apiResponseListener){
        this.apiResponseListener=apiResponseListener;

    }

    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout

        RequestBody requestBody=new FormBody.Builder()
              //  .add("token",Constant.getToken(context))
                .add("email",strings[0])
                .build();

        Request request =
                new Request.Builder()
                        .url("https://furthergrow.silverlinesoftwares.com/login/resend_verification_code")
                        .post(requestBody)
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
        if(s!=null){
            Gson gson = new GsonBuilder().create();
            ResponseModel r = gson.fromJson(s, ResponseModel.class);
            apiResponseListener.onSucessSent(r);
        }
        else{
            apiResponseListener.onFailedSent("Something Went Wrong! Try Again");
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

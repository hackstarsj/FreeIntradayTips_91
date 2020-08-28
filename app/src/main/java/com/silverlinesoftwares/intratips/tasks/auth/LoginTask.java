package com.silverlinesoftwares.intratips.tasks.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.listeners.ApiResponseListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginTask extends AsyncTask<String ,String ,String > {

    private ApiResponseListener apiResponseListener;
    public LoginTask(ApiResponseListener apiResponseListener){
        this.apiResponseListener=apiResponseListener;

    }

    @Override
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
                .add("password",strings[1])
                .build();

        Request request =
                new Request.Builder()
                        .url("https://furthergrow.silverlinesoftwares.com/login/auth")
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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null){
            Gson gson = new GsonBuilder().create();
            ResponseModel r = gson.fromJson(s, ResponseModel.class);
            apiResponseListener.onSucess(r);
        }
        else{
            apiResponseListener.onFailed("Something Went Wrong! Try Again");
        }
    }
}

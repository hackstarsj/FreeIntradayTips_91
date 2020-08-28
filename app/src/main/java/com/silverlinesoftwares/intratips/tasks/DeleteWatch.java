package com.silverlinesoftwares.intratips.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;


import com.silverlinesoftwares.intratips.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeleteWatch extends AsyncTask<String ,String ,String > {

    private final ProgressDialog progressDialog;
    Context context;
    public DeleteWatch(Context context){
        this.context=context;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

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
                .add("symbol",strings[0])
                .build();

        Request request =
                new Request.Builder()
                        .url("https://furthergrow.silverlinesoftwares.com/del_intra.php")
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
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if(s!=null){
            try {
                JSONObject jsonObject=new JSONObject(s);
                if(jsonObject.getString("error").equalsIgnoreCase("200")){
                    context.startActivity(new Intent(context, MainActivity.class).putExtra("run","2"));
                    ((Activity)context).finish();
                }
                else {
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Server Error!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(context, "Network Error Try Again!", Toast.LENGTH_SHORT).show();
        }
    }
}

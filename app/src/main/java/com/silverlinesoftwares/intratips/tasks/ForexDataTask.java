package com.silverlinesoftwares.intratips.tasks;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.silverlinesoftwares.intratips.adapters.OptionAdapter;
import com.silverlinesoftwares.intratips.models.BannerModel;
import com.silverlinesoftwares.intratips.models.OptionModel;
import com.silverlinesoftwares.intratips.models.UserModel;
import com.silverlinesoftwares.intratips.subfragment.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForexDataTask extends AsyncTask<String,String,String > {

    Context context;
    OptionAdapter equityAdapter;
    UserModel userModel;
    List<Object> equityModels;
    String token;
    RecyclerView listview;
    Button button;

    public ForexDataTask(Context context, OptionAdapter equityAdapter, List<Object> equityModels, UserModel userModel, String token, RecyclerView recyclerView, Button button) {
        this.context = context;
        this.equityAdapter = equityAdapter;
        this.token=token;
        this.equityModels = equityModels;
        this.userModel=userModel;
        this.listview=recyclerView;
        this.button=button;
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
               // .add("token",Constant.getToken(context))
                .add("user_id",userModel.getId())
                .add("login_token",token)
                .build();

        Request request =
                new Request.Builder()
                        .url("https://furthergrow.silverlinesoftwares.com/intra_forex.php")
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
        if(HomeFragment.progress!=null){
            HomeFragment.progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(HomeFragment.progress!=null){
            HomeFragment.progress.setVisibility(View.GONE);
        }
        if(s!=null){
            try {
                JSONObject jsonObject=new JSONObject(s);
                if(jsonObject.getString("error").equalsIgnoreCase("200")){
                    Gson gson=new GsonBuilder().create();
                    String finance=jsonObject.getString("data");
                    Type type = new TypeToken<List<OptionModel>>(){}.getType();
                    List<OptionModel> contactList = gson.fromJson(finance, type);
                    equityModels.clear();
                    equityModels.add(new BannerModel(""));
                    equityModels.addAll(contactList);
                    equityModels.add(new BannerModel(""));
                    equityAdapter.notifyDataSetChanged();
                    Log.d("Ok","ok");
                }
                else {
                    button.setText(""+jsonObject.getString("message"));
                    button.setVisibility(View.VISIBLE);
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

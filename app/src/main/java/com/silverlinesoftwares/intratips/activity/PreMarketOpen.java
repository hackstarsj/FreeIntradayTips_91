package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.PreMarketOpenAdapter;
import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;
import com.silverlinesoftwares.intratips.models.PreMarketOpenModel;
import com.silverlinesoftwares.intratips.tasks.PreMarketTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONObject;

import java.util.List;

public class PreMarketOpen extends AppCompatActivity implements GainerLooserListener {

    Spinner spinner;
    ProgressBar progressBar;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_market_open);
        MobileAds.initialize(PreMarketOpen.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        String[] selecs={"Nifty","FO Stocks","NIFTY Bank","SME","Other"};
        ArrayAdapter arrayAdapter=new ArrayAdapter(PreMarketOpen.this,android.R.layout.simple_spinner_item,selecs);
        spinner=findViewById(R.id.select_pre_market);
        progressBar=findViewById(R.id.progress);
        listView=findViewById(R.id.list_pre_market);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //requestWithSomeHttpHeaders();
                PreMarketTask preMarketTask=new PreMarketTask(PreMarketOpen.this);
                preMarketTask.execute(new String[]{""+position});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,PreMarketOpen.this);


    }

    @Override
    public void onSucess(String data) {
        progressBar.setVisibility(View.GONE);
        try {
            JSONObject jsonObject = new JSONObject(data);
            String strings = jsonObject.getString("data");
            TypeToken<List<PreMarketOpenModel>> token = new TypeToken<List<PreMarketOpenModel>>() {};
            Gson gson = new Gson();
            List<PreMarketOpenModel> animals = gson.fromJson(strings, token.getType());
            PreMarketOpenAdapter preMarketOpenAdapter=new PreMarketOpenAdapter(PreMarketOpen.this,animals);
            listView.setAdapter(preMarketOpenAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }

        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(()->{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StaticMethods.showInterestialAds(PreMarketOpen.this);
                }
            });
        },5000);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(PreMarketOpen.this, ""+msg, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

//    public void requestWithSomeHttpHeaders() {
//        RequestQueue queue = Volley.newRequestQueue(PreMarketOpen.this);
//        String url = "https://www1.nseindia.com/live_market/dynaContent/live_analysis/pre_open/nifty.json";
//        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        onSucess(response);
//                        //Log.d("Response", response);
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        onFailed("Server Error");
//                        // Log.d("ERROR","error => "+error.toString());
//                    }
//                }
//        ) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String>  params = new HashMap<String, String>();
//                 //params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36");
//                 params.put("User-Agent","Mozilla/5.0 (compatible; Rigor/1.0.0; http://rigor.com)");
//                 params.put("Accept","*/*");
//
//                return params;
//            }
//        };
//        queue.add(getRequest);
//
//    }
}


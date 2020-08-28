package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.VolumeGainerAdapter;
import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;
import com.silverlinesoftwares.intratips.models.VolumeGainerModel;
import com.silverlinesoftwares.intratips.tasks.VolumeGainerTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VolumeGainerActivity extends AppCompatActivity implements GainerLooserListener {

    private ListView listView;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_gainers);
        MobileAds.initialize(VolumeGainerActivity.this,getString(R.string.app_ads_id));
        progress=findViewById(R.id.progress);
        listView=findViewById(R.id.list_volume_gainer);
        VolumeGainerTask gainerLooserTask=new VolumeGainerTask(VolumeGainerActivity.this);
        gainerLooserTask.execute();
        StaticMethods.showInterestialAds(VolumeGainerActivity.this);
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,VolumeGainerActivity.this);

    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
        try {
            JSONObject jsonObject=new JSONObject(data);
            String strings=jsonObject.getString("data");
            TypeToken<List<VolumeGainerModel>> token = new TypeToken<List<VolumeGainerModel>>() {};
            Gson gson=new Gson();
            List<VolumeGainerModel> animals = gson.fromJson(strings, token.getType());
            VolumeGainerAdapter volumeGainerAdapter=new VolumeGainerAdapter(VolumeGainerActivity.this,animals);
            listView.setAdapter(volumeGainerAdapter);

        } catch (JSONException e) {
            Toast.makeText(VolumeGainerActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(VolumeGainerActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

package com.silverlinesoftwares.intratips.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.util.StaticMethods;

public class PrivacyActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.privacy_dialog);

        Button clode=findViewById(R.id.clode);
        clode.setOnClickListener(v -> {
            StaticMethods.setSecondStart(PrivacyActivity.this);
            startActivity(new Intent(PrivacyActivity.this,MainActivity.class));
            finish();
        });

        WebView privacy_content=findViewById(R.id.privacy_content);
        privacy_content.loadUrl("file:///android_asset/privacy.html");
    }

}

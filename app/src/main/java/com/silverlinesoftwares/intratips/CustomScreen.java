package com.silverlinesoftwares.intratips;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.activity.PrivacyActivity;
import com.silverlinesoftwares.intratips.util.StaticMethods;

public class CustomScreen extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_splash_screen);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if(StaticMethods.isFirstRun(CustomScreen.this)){
                StaticMethods.setFirstStart(CustomScreen.this);
                startActivity(new Intent(CustomScreen.this,WalkThroughActivity.class));
                finish();
            }
            else{
                if(StaticMethods.isPrivacyRun(CustomScreen.this)){
                    startActivity(new Intent(CustomScreen.this, PrivacyActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(CustomScreen.this, MainActivity.class));
                    finish();
                }
            }
        }, 1500);

    }




}

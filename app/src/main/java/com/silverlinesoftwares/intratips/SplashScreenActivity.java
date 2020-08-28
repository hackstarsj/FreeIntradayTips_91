package com.silverlinesoftwares.intratips;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.util.StaticMethods;

public class SplashScreenActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(StaticMethods.isFirstRun(SplashScreenActivity.this)){
                    StaticMethods.setFirstStart(SplashScreenActivity.this);
                    startActivity(new Intent(SplashScreenActivity.this,WalkThroughActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                    finish();
                }
            }}, 3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}

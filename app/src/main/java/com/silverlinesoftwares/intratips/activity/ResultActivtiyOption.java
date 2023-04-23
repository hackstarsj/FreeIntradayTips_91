package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.fragments.ResultOptionFragment;

public class ResultActivtiyOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_activtiy);
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.replace(android.R.id.content, new ResultOptionFragment());
            fragmentTransaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

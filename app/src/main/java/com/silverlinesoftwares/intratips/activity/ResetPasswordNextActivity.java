package com.silverlinesoftwares.intratips.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.google.android.gms.ads.MobileAds;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.ApiResponseListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;
import com.silverlinesoftwares.intratips.tasks.auth.ResetPasswordNextTask;

public class ResetPasswordNextActivity extends AppCompatActivity implements ApiResponseListener {

    ProgressBar progressBar;
    FloatingActionButton send_btn;
    private View parent_view;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_next);
        MobileAds.initialize(ResetPasswordNextActivity.this, initializationStatus -> {

        });
        parent_view = findViewById(android.R.id.content);

        email=getIntent().getStringExtra("email");
        final TextInputEditText codes=findViewById(R.id.verify_code);
        final TextInputEditText password=findViewById(R.id.password);


        progressBar=findViewById(R.id.progress_bar);
        send_btn=findViewById(R.id.verify_btn);

        send_btn.setOnClickListener(v -> {
            if(codes.getText()==null){
                return;
            }
            if(password.getText()==null){
                return;
            }
            if(codes.getText().toString().isEmpty()){
                codes.setError("Please Enter Verification Code");
                codes.requestFocus();
            }
            else if(password.getText().toString().isEmpty()){
                password.setError("Please Enter Password");
                password.requestFocus();
            }
            else{
                View view = ResetPasswordNextActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                send_btn.setAlpha(0f);
                progressBar.setVisibility(View.VISIBLE);

                ResetPasswordNextTask signupTask=new ResetPasswordNextTask(ResetPasswordNextActivity.this);
                signupTask.execute(email,codes.getText().toString(),password.getText().toString());
            }

        });

    }

    @Override
    public void onSucess(ResponseModel data) {
        progressBar.setVisibility(View.GONE);
        send_btn.setAlpha(1f);
        Snackbar.make(parent_view, ""+data.getMessage(), Snackbar.LENGTH_SHORT).show();
        if(data.getStatus_code().equalsIgnoreCase("200")){
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                startActivity(new Intent(ResetPasswordNextActivity.this,LoginActivity.class));
                finish();

            }, 1000);

        }

    }

    @Override
    public void onFailed(String msg) {
        progressBar.setVisibility(View.GONE);
        send_btn.setAlpha(1f);
        Snackbar.make(parent_view, ""+msg, Snackbar.LENGTH_SHORT).show();

    }


}

package com.silverlinesoftwares.intratips.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.ApiResponseListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;
import com.silverlinesoftwares.intratips.tasks.auth.SignupTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

public class SignUpActivity extends AppCompatActivity implements ApiResponseListener {

    ProgressBar progressBar;
    FloatingActionButton sigup;
    private View parent_view;

    String email_v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        MobileAds.initialize(SignUpActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        parent_view = findViewById(android.R.id.content);

        final TextInputEditText email=findViewById(R.id.email);
        final TextInputEditText phone=findViewById(R.id.phone);
        final TextInputEditText name=findViewById(R.id.name);
        final TextInputEditText password=findViewById(R.id.password);


        progressBar=findViewById(R.id.progress_bar);
        sigup=findViewById(R.id.signup_btn);

        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().isEmpty()){
                    email.setError("Please Enter Email!");
                    email.requestFocus();
                }
                else if(password.getText().toString().isEmpty()){
                    password.setError("Please Enter Password!");
                    password.requestFocus();
                }
                else if(name.getText().toString().isEmpty()){
                    name.setError("Please Enter Name!");
                    name.requestFocus();
                }
                else if(phone.getText().toString().isEmpty()){
                    phone.setError("Please Enter Phone!");
                    phone.requestFocus();
                }
                else{
                    View view = SignUpActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                    sigup.setAlpha(0f);
                    email_v=email.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);

                    SignupTask  signupTask=new SignupTask(SignUpActivity.this);
                    signupTask.execute(email.getText().toString(),password.getText().toString(),name.getText().toString(),phone.getText().toString());
                }

            }
        });

    }

    @Override
    public void onSucess(ResponseModel data) {
        progressBar.setVisibility(View.GONE);
        sigup.setAlpha(1f);
        Snackbar.make(parent_view, ""+data.getMessage(), Snackbar.LENGTH_SHORT).show();
        if(data.getStatus_code().equalsIgnoreCase("200")){
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SignUpActivity.this,SignUpActivityNext.class).putExtra("email",email_v));
                    finish();
                }
            }, 1000);

        }

    }

    @Override
    public void onFailed(String msg) {
        progressBar.setVisibility(View.GONE);
        sigup.setAlpha(1f);
        Snackbar.make(parent_view, ""+msg, Snackbar.LENGTH_SHORT).show();

    }
}

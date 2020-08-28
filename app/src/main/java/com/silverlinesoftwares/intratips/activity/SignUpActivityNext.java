package com.silverlinesoftwares.intratips.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.MobileAds;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.ApiResponseListener;
import com.silverlinesoftwares.intratips.listeners.ResendCodeListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;
import com.silverlinesoftwares.intratips.tasks.auth.ResendLoginCodeTask;
import com.silverlinesoftwares.intratips.tasks.auth.VerifyEmailTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

public class SignUpActivityNext extends AppCompatActivity implements ApiResponseListener, ResendCodeListener {

    ProgressBar progressBar;
    FloatingActionButton verify_btn;
    private View parent_view;
    LinearLayout resend_code;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_next);
        MobileAds.initialize(SignUpActivityNext.this,getString(R.string.app_ads_id));
        parent_view = findViewById(android.R.id.content);
        resend_code=findViewById(R.id.resend_code);

        final TextInputEditText codes=findViewById(R.id.code);
        email=getIntent().getStringExtra("email");


        progressBar=findViewById(R.id.progress_bar);
        verify_btn=findViewById(R.id.verify_btn);

        resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(parent_view, "Sending Code! Please Wait...", Snackbar.LENGTH_LONG).show();
                ResendLoginCodeTask resendLoginCodeTask=new ResendLoginCodeTask(SignUpActivityNext.this);
                resendLoginCodeTask.execute(email);
            }
        });

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codes.getText().toString().isEmpty()){
                    codes.setError("Please Enter Verification Code!");
                    codes.requestFocus();
                }
                else{
                    View view = SignUpActivityNext.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                    verify_btn.setAlpha(0f);
                    progressBar.setVisibility(View.VISIBLE);

                    VerifyEmailTask signupTask=new VerifyEmailTask(SignUpActivityNext.this);
                    signupTask.execute(email,codes.getText().toString());
                }

            }
        });

        StaticMethods.showInterestialAds(SignUpActivityNext.this);
    }

    @Override
    public void onSucess(ResponseModel data) {
        progressBar.setVisibility(View.GONE);
        verify_btn.setAlpha(1f);
        Snackbar.make(parent_view, ""+data.getMessage(), Snackbar.LENGTH_SHORT).show();
        if(data.getStatus_code().equalsIgnoreCase("200")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SignUpActivityNext.this,LoginActivity.class));
                    finish();

                }
            }, 1000);

        }

    }

    @Override
    public void onFailed(String msg) {
        progressBar.setVisibility(View.GONE);
        verify_btn.setAlpha(1f);
        Snackbar.make(parent_view, ""+msg, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onSucessSent(ResponseModel data) {
        Snackbar.make(parent_view, ""+data.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedSent(String msg) {
        Snackbar.make(parent_view, ""+msg, Snackbar.LENGTH_SHORT).show();
    }
}

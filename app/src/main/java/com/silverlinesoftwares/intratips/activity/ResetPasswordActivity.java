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
import android.widget.ProgressBar;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.ApiResponseListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;
import com.silverlinesoftwares.intratips.tasks.auth.ResetPasswordTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

public class ResetPasswordActivity extends AppCompatActivity implements ApiResponseListener {

    ProgressBar progressBar;
    FloatingActionButton send_btn;
    private View parent_view;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        parent_view = findViewById(android.R.id.content);

        final TextInputEditText codes=findViewById(R.id.email);


        progressBar=findViewById(R.id.progress_bar);
        send_btn=findViewById(R.id.send_btn);;

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codes.getText().toString().isEmpty()){
                    codes.setError("Please Enter Email");
                    codes.requestFocus();
                }
                else{
                    View view = ResetPasswordActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                    send_btn.setAlpha(0f);
                    progressBar.setVisibility(View.VISIBLE);

                    email=codes.getText().toString();
                    ResetPasswordTask signupTask=new ResetPasswordTask(ResetPasswordActivity.this);
                    signupTask.execute(email);
                }

            }
        });

        StaticMethods.showInterestialAds(ResetPasswordActivity.this);
    }

    @Override
    public void onSucess(ResponseModel data) {
        progressBar.setVisibility(View.GONE);
        send_btn.setAlpha(1f);
        Snackbar.make(parent_view, ""+data.getMessage(), Snackbar.LENGTH_SHORT).show();
        if(data.getStatus_code().equalsIgnoreCase("200")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(ResetPasswordActivity.this,ResetPasswordNextActivity.class).putExtra("email",email));
                    finish();

                }
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

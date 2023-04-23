package com.silverlinesoftwares.intratips.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessaging;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.ApiResponseListener;
import com.silverlinesoftwares.intratips.listeners.DetailsResponseListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;
import com.silverlinesoftwares.intratips.tasks.auth.FetchProfileTask;
import com.silverlinesoftwares.intratips.tasks.auth.LoginTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;


public class LoginActivity extends AppCompatActivity implements ApiResponseListener, DetailsResponseListener {

    ProgressBar progress_bar;
    private View parent_view;
    String emails="";
    TextView forgot,signup;
    private FloatingActionButton floatingActionButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_image_teal);
        MobileAds.initialize(LoginActivity.this, initializationStatus -> {

        });
        parent_view = findViewById(android.R.id.content);
        floatingActionButton=findViewById(R.id.fab);
        final TextInputEditText email=findViewById(R.id.email);
        forgot=findViewById(R.id.forgot);
        signup=findViewById(R.id.signup);
        progress_bar=findViewById(R.id.progress_bar);
        final TextInputEditText password=findViewById(R.id.password);
        forgot.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class)));

        signup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,SignUpActivity.class)));
        floatingActionButton.setOnClickListener(v -> {
            if(email==null){
                return;
            }
            if(email.getText()==null){
                return;
            }
            if(password.getText()==null){
                return;
            }
            if(email.getText().toString().isEmpty()){
                email.setError("Please Enter Email");
                email.requestFocus();
            }
            else if(password.getText().toString().isEmpty()){
                password.setError("Please Enter Password");
                password.requestFocus();
            }
            else{
                View view = LoginActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                emails=email.getText().toString();
                floatingActionButton.setAlpha(0f);
                progress_bar.setVisibility(View.VISIBLE);

                LoginTask loginTask=new LoginTask(LoginActivity.this);
                loginTask.execute(email.getText().toString(),password.getText().toString());
            }
        });


    }

    @Override
    public void onSucess(ResponseModel data) {
        progress_bar.setVisibility(View.GONE);
        floatingActionButton.setAlpha(1f);
        Snackbar.make(parent_view, ""+data.getMessage(), Snackbar.LENGTH_SHORT).show();
        if(data.getStatus_code().equalsIgnoreCase("200")){
            StaticMethods.setLoginToken(LoginActivity.this,data.getToken());
            ShowNextStep();
        }
        else if(data.getStatus_code().equalsIgnoreCase("402")){
            new Handler(Looper.getMainLooper()).postDelayed(() -> startActivity(new Intent(LoginActivity.this, SignUpActivityNext.class).putExtra("email", emails)),1000);
        }
    }

    private void ShowNextStep() {
        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Fetching your profile!..\nPlease Don't Close App");
        progressDialog.setTitle("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                return;
            }
            String tok=task.getResult();
            FetchProfileTask fetchProfileTask=new FetchProfileTask(LoginActivity.this);
            fetchProfileTask.execute(StaticMethods.getLoginToken(LoginActivity.this),tok);

        }).addOnFailureListener(e -> {
            FetchProfileTask fetchProfileTask=new FetchProfileTask(LoginActivity.this);
            fetchProfileTask.execute(StaticMethods.getLoginToken(LoginActivity.this),"");
        }).addOnCanceledListener(() -> {
            FetchProfileTask fetchProfileTask=new FetchProfileTask(LoginActivity.this);
            fetchProfileTask.execute(StaticMethods.getLoginToken(LoginActivity.this),"");
        });

    }
    @Override
    public void onProfile(ResponseModel data) {
        if(data.getStatus_code().equalsIgnoreCase("200")) {
            Snackbar.make(parent_view, ""+data.getMessage(), Snackbar.LENGTH_SHORT).show();

            StaticMethods.saveUserDetails(LoginActivity.this, data.getUser());
            new Handler(Looper.getMainLooper()).postDelayed(() -> {

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            },1000);
        }
        else{
            Snackbar.make(parent_view, ""+data.getMessage(), Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailedProfile(String msg) {
        if(progressDialog!=null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        Snackbar.make(parent_view, ""+msg, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onFailed(String msg) {
        if(progressDialog!=null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }

        progress_bar.setVisibility(View.GONE);
        floatingActionButton.setAlpha(1f);
        Snackbar.make(parent_view, ""+msg, Snackbar.LENGTH_SHORT).show();
    }
}

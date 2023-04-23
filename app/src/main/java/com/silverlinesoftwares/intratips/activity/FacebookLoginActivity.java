package com.silverlinesoftwares.intratips.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.DetailsResponseListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;
import com.silverlinesoftwares.intratips.tasks.auth.FetchProfileTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FacebookLoginActivity extends AppCompatActivity implements DetailsResponseListener {

    FirebaseAuth firebaseAuth;
    private LoginButton login_fb_btn;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        firebaseAuth=FirebaseAuth.getInstance();
        callbackManager= CallbackManager.Factory.create();
        progressDialog=new ProgressDialog(FacebookLoginActivity.this);
        login_fb_btn=findViewById(R.id.login_fb_btn);
        login_fb_btn.setPermissions("email");
        login_fb_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookLogin(loginResult);
            }

            @Override
            public void onCancel() {
                Toast.makeText(FacebookLoginActivity.this, "Login Failed! Try Again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(FacebookLoginActivity.this, "Login Failed! Try Again", Toast.LENGTH_SHORT).show();
            }
        });
        initFB();
    }

    private void handleFacebookLogin(LoginResult loginResult) {
        AuthCredential authCredential= FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                SendUserData(firebaseUser);
            }
        });
    }


    private void SendUserData(FirebaseUser firebaseUser) {

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser!=null) {
            mUser.getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            SendtoServerTokenFcm(idToken);

                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(FacebookLoginActivity.this, "Failed to Register Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void initFB() {


        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            SendtoServerTokenFcm(idToken);

                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(FacebookLoginActivity.this, "Failed to Register Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            LoginManager.getInstance().logInWithReadPermissions(FacebookLoginActivity.this, Arrays.asList("email","public_profile"));
            login_fb_btn.performClick();
        }
    }

    private void SendtoServerTokenFcm(String idToken) {
        if(StaticMethods.getUserDetails(FacebookLoginActivity.this)==null) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(FacebookLoginActivity.this);
            }
            progressDialog.setMessage("Logging In.. Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            RegisterFirebaseUser(idToken);
        }
    }

    public void RegisterFirebaseUser(final String token){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(FacebookLoginActivity.this);
        String url = "https://furthergrow.silverlinesoftwares.com/login/firebaseuser";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, this::processLoginNextStep, error -> {
            Toast.makeText(FacebookLoginActivity.this, "Failed to Login! Please Try Again!", Toast.LENGTH_SHORT).show();
            if(progressDialog!=null){
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("token", token);
                return MyData;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }

    private void processLoginNextStep(String response) {
        if(response!=null){
            Gson gson = new GsonBuilder().create();
            ResponseModel data = gson.fromJson(response, ResponseModel.class);
            if(data.getStatus_code().equalsIgnoreCase("200")){
                StaticMethods.setLoginToken(FacebookLoginActivity.this,data.getToken());
                if(progressDialog!=null){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
                ProcessProfileStep();

            }
            else {
                if(progressDialog!=null){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
                new Handler(Looper.getMainLooper()).postDelayed(() -> showDialog(""+data.getMessage()),1000);
            }
        }
        else{
            if(progressDialog!=null){
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
            showDialog("Failed to Login! Please Try Again!");
        }
    }

    private void showDialog(String s) {
        AlertDialog.Builder al=new AlertDialog.Builder(FacebookLoginActivity.this);
        al.setMessage(s);
        al.setTitle("Error");
        al.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());
        al.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


    private void ProcessProfileStep() {
        if(progressDialog!=null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        if(progressDialog==null) {
            progressDialog = new ProgressDialog(FacebookLoginActivity.this);
        }

        progressDialog.setMessage("Fetching your profile!..\nPlease Don't Close App");
        progressDialog.setTitle("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                return;
            }
            String tok=task.getResult();
            FetchProfileTask fetchProfileTask=new FetchProfileTask(FacebookLoginActivity.this);
            fetchProfileTask.execute(StaticMethods.getLoginToken(FacebookLoginActivity.this),tok);

        }).addOnFailureListener(e -> {
            FetchProfileTask fetchProfileTask=new FetchProfileTask(FacebookLoginActivity.this);
            fetchProfileTask.execute(StaticMethods.getLoginToken(FacebookLoginActivity.this),"");
        }).addOnCanceledListener(() -> {
            FetchProfileTask fetchProfileTask=new FetchProfileTask(FacebookLoginActivity.this);
            fetchProfileTask.execute(StaticMethods.getLoginToken(FacebookLoginActivity.this),"");
        });

    }

    @Override
    public void onProfile(ResponseModel data) {
        if(progressDialog!=null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        if(data!=null){
            if(data.getStatus_code().equalsIgnoreCase("200")){
                StaticMethods.saveUserDetails(FacebookLoginActivity.this,data.getUser());
                startActivity(new Intent(FacebookLoginActivity.this, MainActivity.class));

            }
            else{
                StaticMethods.removeUser(FacebookLoginActivity.this);
                StaticMethods.removeToken(FacebookLoginActivity.this);
                showDialog("Session Expired! Login Again.");

            }
        }
        else{
            StaticMethods.removeUser(FacebookLoginActivity.this);
            StaticMethods.removeToken(FacebookLoginActivity.this);
            showDialog("Session Expired! Login Again.");

        }
    }

    @Override
    public void onFailedProfile(String msg) {
        StaticMethods.removeUser(FacebookLoginActivity.this);
        StaticMethods.removeToken(FacebookLoginActivity.this);
        showDialog("Failed to Fetch your Profile! Login Again.");
    }
}
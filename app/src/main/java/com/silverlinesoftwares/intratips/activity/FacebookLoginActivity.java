package com.silverlinesoftwares.intratips.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.fragments.AboutFragment;
import com.silverlinesoftwares.intratips.listeners.ApiResponseListener;
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
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                    SendUserData(firebaseUser);
                }
            }
        });
    }


    private void SendUserData(FirebaseUser firebaseUser) {

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser!=null) {
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                SendtoServerTokenFcm(idToken);

                            } else {
                                // Handle error -> task.getException();
                                Toast.makeText(FacebookLoginActivity.this, "Failed to Register Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void initFB() {


        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                SendtoServerTokenFcm(idToken);

                            } else {
                                // Handle error -> task.getException();
                                Toast.makeText(FacebookLoginActivity.this, "Failed to Register Try Again!", Toast.LENGTH_SHORT).show();
                            }
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
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                processLoginNextStep(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FacebookLoginActivity.this, "Failed to Login! Please Try Again!", Toast.LENGTH_SHORT).show();
                if(progressDialog!=null){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
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
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDialog(""+data.getMessage());
                    }},1000);
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
        al.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
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


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    return;
                }
                String tok=task.getResult();
                FetchProfileTask fetchProfileTask=new FetchProfileTask(FacebookLoginActivity.this);
                fetchProfileTask.execute(StaticMethods.getLoginToken(FacebookLoginActivity.this),tok);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FetchProfileTask fetchProfileTask=new FetchProfileTask(FacebookLoginActivity.this);
                fetchProfileTask.execute(StaticMethods.getLoginToken(FacebookLoginActivity.this),"");
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                FetchProfileTask fetchProfileTask=new FetchProfileTask(FacebookLoginActivity.this);
                fetchProfileTask.execute(StaticMethods.getLoginToken(FacebookLoginActivity.this),"");
            }
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
package com.silverlinesoftwares.intratips.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.DetailsResponseListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;
import com.silverlinesoftwares.intratips.tasks.auth.FetchProfileTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import java.util.HashMap;
import java.util.Map;

public class GoogleLoginActivity extends AppCompatActivity implements DetailsResponseListener {

    private ProgressDialog progressDialog;
    private static final int RC_SIGN_IN = 9001;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);
        progressDialog=new ProgressDialog(GoogleLoginActivity.this);
        firebaseAuth=FirebaseAuth.getInstance();

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        try {
                            Task<GoogleSignInAccount> googleSignInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                            GoogleSignInAccount account = googleSignInAccountTask.getResult(ApiException.class);
                            assert account != null;
                            processGoogleLogin(account.getIdToken(), account);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

        ActivateGoogleLogin();
    }

    private void processGoogleLogin(String idToken, GoogleSignInAccount account) {
        AuthCredential authCredential= GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(GoogleLoginActivity.this, task -> {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            SendUserData(firebaseUser);
                        }
                    }
                });
    }

    private void ActivateGoogleLogin() {

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("864284917272-ogcb0hdbq37tpg8encm14vs5kl6jln8a.apps.googleusercontent.com")
                .requestEmail()
                .requestProfile()
                .build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(GoogleLoginActivity.this,googleSignInOptions);
        Intent intent=googleSignInClient.getSignInIntent();
        someActivityResultLauncher.launch(intent);

        if(progressDialog!=null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

    }

    private void SendUserData(FirebaseUser firebaseUser) {
        Log.d("Login Sucess",""+firebaseUser.getDisplayName());
        Log.d("Login Sucess",""+firebaseUser.getEmail());
        Log.d("Login Sucess",""+firebaseUser.getPhoneNumber());
        Log.d("Login Sucess",""+firebaseUser.getPhotoUrl());
        Log.d("Login Sucess",""+firebaseUser.getUid());
        Log.d("Login Sucess",""+firebaseUser.getProviderId());
        Log.d("Login Sucess",""+firebaseUser.getTenantId());

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser!=null) {
            mUser.getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            SendtoServerTokenFcm(idToken);

                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(GoogleLoginActivity.this, "Failed to Register Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void SendtoServerTokenFcm(String idToken) {
        if(StaticMethods.getUserDetails(GoogleLoginActivity.this)==null) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(GoogleLoginActivity.this);
            }
            progressDialog.setMessage("Logging In.. Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            RegisterFirebaseUser(idToken);
        }
    }

    public void RegisterFirebaseUser(final String token){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(GoogleLoginActivity.this);
        String url = "https://furthergrow.silverlinesoftwares.com/login/firebaseuser";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, this::processLoginNextStep, error -> {
            Toast.makeText(GoogleLoginActivity.this, "Failed to Login! Please Try Again!", Toast.LENGTH_SHORT).show();
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


    private void ProcessProfileStep() {
        if(progressDialog!=null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        if(progressDialog==null) {
            progressDialog = new ProgressDialog(GoogleLoginActivity.this);
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
            FetchProfileTask fetchProfileTask=new FetchProfileTask(GoogleLoginActivity.this);
            fetchProfileTask.execute(StaticMethods.getLoginToken(GoogleLoginActivity.this),tok);

        }).addOnFailureListener(e -> {
            FetchProfileTask fetchProfileTask=new FetchProfileTask(GoogleLoginActivity.this);
            fetchProfileTask.execute(StaticMethods.getLoginToken(GoogleLoginActivity.this),"");
        }).addOnCanceledListener(() -> {
            FetchProfileTask fetchProfileTask=new FetchProfileTask(GoogleLoginActivity.this);
            fetchProfileTask.execute(StaticMethods.getLoginToken(GoogleLoginActivity.this),"");
        });

    }


    private void processLoginNextStep(String response) {
        if(response!=null){
            Gson gson = new GsonBuilder().create();
            ResponseModel data = gson.fromJson(response, ResponseModel.class);
            if(data.getStatus_code().equalsIgnoreCase("200")){
                StaticMethods.setLoginToken(GoogleLoginActivity.this,data.getToken());
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
        AlertDialog.Builder al=new AlertDialog.Builder(GoogleLoginActivity.this);
        al.setMessage(s);
        al.setTitle("Error");
        al.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());
        al.show();
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
                StaticMethods.saveUserDetails(GoogleLoginActivity.this,data.getUser());
                startActivity(new Intent(GoogleLoginActivity.this, MainActivity.class));

            }
            else{
                StaticMethods.removeUser(GoogleLoginActivity.this);
                StaticMethods.removeToken(GoogleLoginActivity.this);
                showDialog("Session Expired! Login Again.");

            }
        }
        else{
            StaticMethods.removeUser(GoogleLoginActivity.this);
            StaticMethods.removeToken(GoogleLoginActivity.this);
            showDialog("Session Expired! Login Again.");

        }
    }

    @Override
    public void onFailedProfile(String msg) {
        StaticMethods.removeUser(GoogleLoginActivity.this);
        StaticMethods.removeToken(GoogleLoginActivity.this);
        showDialog("Failed to Fetch your Profile! Login Again.");
    }
}
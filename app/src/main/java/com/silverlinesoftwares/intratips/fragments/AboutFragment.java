package com.silverlinesoftwares.intratips.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gocashfree.cashfreesdk.CFPaymentService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instamojo.android.Instamojo;
import com.instamojo.android.helpers.Constants;
import com.silverlinesoftwares.intratips.BuildConfig;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.LoginActivity;
import com.silverlinesoftwares.intratips.activity.PaymentGatewayActivityCashFree;
import com.silverlinesoftwares.intratips.activity.SignUpActivity;
import com.silverlinesoftwares.intratips.activity.SignUpActivityNext;
import com.silverlinesoftwares.intratips.activity.VideoActivtiy;
import com.silverlinesoftwares.intratips.listeners.ApiResponseListener;
import com.silverlinesoftwares.intratips.listeners.DetailsResponseListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;
import com.silverlinesoftwares.intratips.models.UserModel;
import com.silverlinesoftwares.intratips.tasks.auth.FetchProfileTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;


public class AboutFragment extends Fragment implements DetailsResponseListener, Instamojo.InstamojoPaymentCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int GOOGLE_SINGIN_REQUEST = 112;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CallbackManager callbackManager;

    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager= ReviewManagerFactory.create(getContext());
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(getContext());
        callbackManager=CallbackManager.Factory.create();
        LinearLayout other_line=view.findViewById(R.id.other_line);
        LinearLayout what_line=view.findViewById(R.id.telegram);
        Button rates=view.findViewById(R.id.rate_app);
        ImageView ff=view.findViewById(R.id.image_pen);
        ImageView login_google=view.findViewById(R.id.login_google);
        ImageView login_fb=view.findViewById(R.id.fb_login);
        LinearLayout youtube=view.findViewById(R.id.youtube);
        TextView app_version=view.findViewById(R.id.app_version);
        LoginButton login_fb_btn=view.findViewById(R.id.login_fb_btn);
        SwitchCompat notification=view.findViewById(R.id.notification_on_off);

        if(StaticMethods.getNotification(getContext()).equalsIgnoreCase("1")){
            notification.setChecked(true);
        }
        else{
            notification.setChecked(false);
        }

        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    StaticMethods.setNotification(getContext(),"1");
                    FirebaseMessaging.getInstance().subscribeToTopic("allDevices");
                }
                else{
                    StaticMethods.setNotification(getContext(),"0");
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("allDevices");
                }
            }
        });

        login_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                            Toast.makeText(getContext(), "Failed to Register Try Again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                }
                else {
                    LoginManager.getInstance().logInWithReadPermissions(AboutFragment.this, Arrays.asList("email","public_profile"));
                   // login_fb_btn.performClick();
                }
            }
        });
        login_fb_btn.setPermissions("email");
        login_fb_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookLogin(loginResult);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Login Failed! Try Again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "Login Failed! Try Again", Toast.LENGTH_SHORT).show();
            }
        });

        login_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   ActivateGoogleLogin();
            }
        });


        app_version.setText(""+BuildConfig.VERSION_NAME);
        other_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareFile();
            }
        });
        rates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RateApp(getActivity());

               // Instamojo.getInstance().initialize(getActivity(), Instamojo.Environment.TEST);
                //initiateSDKPayment("a61fea7c-65e3-4b84-b2c2-9bab7d3d1ce9");

//                startActivity(new Intent(getContext(),CurrencyListTabsActivity.class));
            }
        });

        what_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://t.me/freeintradaytipsindia"));
                startActivity(intent);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/channel/UCwONI1ZvyQz_lipWKqF4mnA"));
                startActivity(intent);
            }
        });


        ff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://zerodha.com/open-account?c=ZMPCJG"));
                startActivity(intent);
            }
        });


        ImageView ff2=view.findViewById(R.id.alice_pen);
        ff2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://alicebluepartner.com/furthergrow/"));
                startActivity(intent);
            }
        });


        Button login=view.findViewById(R.id.login);
        Button logout=view.findViewById(R.id.logout);
        Button signup=view.findViewById(R.id.signup);
        LinearLayout profile_line=view.findViewById(R.id.profile_line);
        LinearLayout login_line=view.findViewById(R.id.login_line);
        LinearLayout login_line_2=view.findViewById(R.id.login_line_2);
        Button buy_1=view.findViewById(R.id.buy_pre_1_1);
        Button join_paid_premium=view.findViewById(R.id.join_paid_premium);
        Button join_pro_plus_premium=view.findViewById(R.id.join_pro_plus_premium);
        Button buy_2_2=view.findViewById(R.id.buy_pre_2_2);
        Button buy_3_3=view.findViewById(R.id.buy_pre_3_3);
        Button youtube_video=view.findViewById(R.id.youtube_video);

        youtube_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), VideoActivtiy.class));
            }
        });


        join_paid_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://t.me/joinchat/AAAAAEounjHkvNTzq9zd2w"));
                startActivity(intent);
            }
        });

        join_pro_plus_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://t.me/joinchat/AAAAAFX8ABfoZwCcydf0Pg"));
                startActivity(intent);
            }
        });




        buy_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(StaticMethods.getLoginToken(getContext())!=null) {
                    if(StaticMethods.getUserDetails(getContext())!=null) {
                        startActivity(new Intent(getContext(), PaymentGatewayActivityCashFree.class).putExtra("pay_id","1"));
                    }
                    else{
                        Toast.makeText(getContext(), "You Must Login To Buy Premium Plan!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(),LoginActivity.class));

                    }
                }
                else{
                    Toast.makeText(getContext(), "You Must Login To Buy Premium Plan!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(),LoginActivity.class));
                }
            }
        });

        buy_2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(StaticMethods.getLoginToken(getContext())!=null) {
                    if(StaticMethods.getUserDetails(getContext())!=null) {
                        startActivity(new Intent(getContext(), PaymentGatewayActivityCashFree.class).putExtra("pay_id","2"));
                    }
                    else{
                        Toast.makeText(getContext(), "You Must Login To Buy Premium Plan!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(),LoginActivity.class));

                    }
                }
                else{
                    Toast.makeText(getContext(), "You Must Login To Buy Premium Plan!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(),LoginActivity.class));
                }
            }
        });



        buy_3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(StaticMethods.getLoginToken(getContext())!=null) {
                    if(StaticMethods.getUserDetails(getContext())!=null) {
                        startActivity(new Intent(getContext(), PaymentGatewayActivityCashFree.class).putExtra("pay_id","3"));
                    }
                    else{
                        Toast.makeText(getContext(), "You Must Login To Buy PRO PLUS Plan!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(),LoginActivity.class));

                    }
                }
                else{
                    Toast.makeText(getContext(), "You Must Login To Buy PRO PLUS Plan!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(),LoginActivity.class));
                }
            }
        });

        TextView name=view.findViewById(R.id.name);
        TextView email=view.findViewById(R.id.email);
        TextView plan_type=view.findViewById(R.id.plan_type);
        TextView expire_date=view.findViewById(R.id.expired_date);
        TextView user_type=view.findViewById(R.id.user_type);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticMethods.removeToken(getContext());
                StaticMethods.removeUser(getContext());
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        if(StaticMethods.getUserDetails(getContext())!=null){
            login_line.setVisibility(View.GONE);
            login_line_2.setVisibility(View.GONE);
            profile_line.setVisibility(View.VISIBLE);

            UserModel userModel=StaticMethods.getUserDetails(getContext());
            name.setText(userModel.getName());
            email.setText(userModel.getEmail());
            plan_type.setText(userModel.getCurrent_plan());
            if(userModel.getCurrent_plan().contains("FREE")){
                user_type.setText("FREE");
                expire_date.setText("Unlimited");
            }
            else{
                join_paid_premium.setVisibility(View.VISIBLE);
                user_type.setText("PRO");
                expire_date.setText(userModel.getExpire_date());
            }

            if(userModel.getCurrent_plan().equalsIgnoreCase("PRO PREMIUM")){
                 buy_2_2.setText("PAID");
                buy_2_2.setEnabled(false);
                buy_1.setVisibility(View.GONE);

            }
            else if(userModel.getCurrent_plan().equalsIgnoreCase("PREMIUM")){
                buy_1.setText("PAID");
                buy_1.setEnabled(false);
                buy_2_2.setVisibility(View.GONE);
            }
            else{
                buy_1.setText("BUY SERVER 2");
                buy_2_2.setText("BUY SERVER 1");
                buy_1.setEnabled(true);
                buy_2_2.setEnabled(true);
            }

            if(userModel.getIs_super()!=null) {
                if (userModel.getIs_super().equalsIgnoreCase("1")) {
                    buy_3_3.setText("PAID");
                    buy_1.setText("Aleardy Purchase PRO PLUS Plan");

                    buy_2_2.setText("Aleardy Purchase PRO PLUS Plan");

                    buy_1.setEnabled(false);
                    buy_2_2.setEnabled(false);
                    buy_3_3.setEnabled(false);
                    join_pro_plus_premium.setVisibility(View.VISIBLE);
                } else {
                    join_pro_plus_premium.setVisibility(View.GONE);
                }
            }
            else{
                join_pro_plus_premium.setVisibility(View.GONE);
            }


        }
        else{
            login_line.setVisibility(View.VISIBLE);
            login_line_2.setVisibility(View.VISIBLE);
            profile_line.setVisibility(View.GONE);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
            }
        });
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

    private void ActivateGoogleLogin() {

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("864284917272-ogcb0hdbq37tpg8encm14vs5kl6jln8a.apps.googleusercontent.com")
                .requestEmail()
                .requestProfile()
                .build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(getActivity(),googleSignInOptions);
        Intent intent=googleSignInClient.getSignInIntent();
        startActivityForResult(intent,GOOGLE_SINGIN_REQUEST);
        if(progressDialog!=null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //
        if(requestCode==GOOGLE_SINGIN_REQUEST){

            try {
                Task<GoogleSignInAccount> googleSignInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = googleSignInAccountTask.getResult(ApiException.class);
                processGoogleLogin(account.getIdToken(),account);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void processGoogleLogin(String idToken, GoogleSignInAccount account) {
        AuthCredential authCredential= GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
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
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                SendtoServerTokenFcm(idToken);

                            } else {
                                // Handle error -> task.getException();
                                Toast.makeText(getContext(), "Failed to Register Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void SendtoServerTokenFcm(String idToken) {
        if(StaticMethods.getUserDetails(getContext())==null) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(getContext());
            }
            progressDialog.setMessage("Logging In.. Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            RegisterFirebaseUser(idToken);
        }
    }

    public void RegisterFirebaseUser(final String token){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        String url = "https://furthergrow.silverlinesoftwares.com/login/firebaseuser";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                processLoginNextStep(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Failed to Login! Please Try Again!", Toast.LENGTH_SHORT).show();
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
                StaticMethods.setLoginToken(getContext(),data.getToken());
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

    private void ProcessProfileStep() {
        if(progressDialog!=null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        if(progressDialog==null) {
            progressDialog = new ProgressDialog(getContext());
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
                FetchProfileTask fetchProfileTask=new FetchProfileTask(AboutFragment.this);
                fetchProfileTask.execute(StaticMethods.getLoginToken(getContext()),tok);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FetchProfileTask fetchProfileTask=new FetchProfileTask(AboutFragment.this);
                fetchProfileTask.execute(StaticMethods.getLoginToken(getContext()),"");
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                FetchProfileTask fetchProfileTask=new FetchProfileTask(AboutFragment.this);
                fetchProfileTask.execute(StaticMethods.getLoginToken(getContext()),"");
            }
        });

    }


    private void ShareFile() {
        try {

            Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, "Download Free Intraday Tips for Daily Learning Tips ... https://play.google.com/store/apps/details?id=com.silverlinesoftwares.intratips");
            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Free Intraday Tips");

            startActivity(Intent.createChooser(i,"Share Via."));
        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(getContext(),"No Activity Found", Toast.LENGTH_LONG).show();
        }
    }


    private void showDialog(String s) {
        AlertDialog.Builder al=new AlertDialog.Builder(getContext());
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


    private void ShowPaymentDialog(Bundle data) {
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());
        View view=getLayoutInflater().inflate(R.layout.dialog_payment_success,null);
        FloatingActionButton fab=view.findViewById(R.id.fab);
        TextView sorry_thanks=view.findViewById(R.id.sorry_thanks);
        TextView message=view.findViewById(R.id.message);
        TextView date=view.findViewById(R.id.date);
        TextView times=view.findViewById(R.id.times);
        TextView name=view.findViewById(R.id.name);
        TextView email=view.findViewById(R.id.email);
        TextView amount=view.findViewById(R.id.amount);
        TextView status=view.findViewById(R.id.status);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = new Date();

        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        Date date2 = new Date();
       // System.out.println(formatter.format(date));

        if(data.getString("STATUS").equalsIgnoreCase("TXN_SUCCESS")){
            sorry_thanks.setText("Thank You");
            message.setText("Your transaction was successful");
            status.setText("Completed");
        }
        else{
            sorry_thanks.setText("Sorry");
            message.setText("Your transaction was unsuccessful");
            status.setText("Failed");
        }


        date.setText(formatter.format(date1));
        times.setText(formatter1.format(date2));
        UserModel userModel=StaticMethods.getUserDetails(getContext());
        name.setText(userModel.getName());
        email.setText(userModel.getEmail());
        amount.setText(data.getString("TXNAMOUNT"));




        alertDialog.setView(view);
      final AlertDialog alertDialog1=alertDialog.show();
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               alertDialog1.dismiss();
               RefreshUI();
           }
       });

    }

    private void RefreshUI() {

        if(StaticMethods.getUserDetails(getContext())!=null){
            if(StaticMethods.getLoginToken(getContext())!=null){

                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(!task.isSuccessful()){
                            return;
                        }
                        String tok=task.getResult();
                        FetchProfileTask fetchProfileTask=new FetchProfileTask(AboutFragment.this);
                        fetchProfileTask.execute(StaticMethods.getLoginToken(getContext()),tok);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FetchProfileTask fetchProfileTask=new FetchProfileTask(AboutFragment.this);
                        fetchProfileTask.execute(StaticMethods.getLoginToken(getContext()),"");
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        FetchProfileTask fetchProfileTask=new FetchProfileTask(AboutFragment.this);
                        fetchProfileTask.execute(StaticMethods.getLoginToken(getContext()),"");
                    }
                });

            }
            else{
                StaticMethods.removeToken(getContext());
                StaticMethods.removeUser(getContext());
            }
        }

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
                StaticMethods.saveUserDetails(getContext(),data.getUser());
                startActivity(new Intent(getContext(),MainActivity.class));

            }
            else{
                StaticMethods.removeUser(getContext());
                StaticMethods.removeToken(getContext());
                showDialog("Session Expired! Login Again.");

            }
        }
        else{
            StaticMethods.removeUser(getContext());
            StaticMethods.removeToken(getContext());
            showDialog("Session Expired! Login Again.");

        }
    }

    @Override
    public void onFailedProfile(String msg) {
        StaticMethods.removeUser(getContext());
        StaticMethods.removeToken(getContext());
        showDialog("Failed to Fetch your Profile! Login Again.");
    }

    private void initiateSDKPayment(String orderID) {
        if(getActivity()!=null) {
             Instamojo.getInstance().initialize(getActivity(), Instamojo.Environment.PRODUCTION);
            Instamojo.getInstance().initiatePayment(getActivity(), orderID, AboutFragment.this);
        }
        else {
            Toast.makeText(getContext(), "Null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInstamojoPaymentComplete(String orderID, String transactionID, String paymentID, String paymentStatus) {
        //Log.d("TAG", "Payment complete");
        Log.d("Order ","Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
                + ", Payment ID:" + paymentID + ", Status: " + paymentStatus);
        sendtoserverInsta(orderID,transactionID,paymentID,paymentStatus);
    }

    @Override
    public void onPaymentCancelled() {
        //Log.d("TAG", "Payment cancelled");
        showDialog("Payment Cancel! Contact Email : creativeapptechnology@gmail.com for Support");
    }

    @Override
    public void onInitiatePaymentFailure(String s) {
        showDialog("Error Loading Payment Gateway! Please Try Again Later! Contact Email : creativeapptechnology@gmail.com for Support");
     //   Log.d("TAG", "Initiate payment failed "+s);
    }


    public void sendtoserverInsta(String  orderid, String txnid, String paymentid, final String paymentStatus){
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Please Wait Processing Payment!\n Don't Press Back Button!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.GET, "https://furthergrow.silverlinesoftwares.com/login/checkPaymentStatus_insta/"+txnid+"/"+orderid, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("Response ",""+response);
                if(response!=null) {
                    ShowPaymentDialog2(response);
                }
                else{
                    showDialog("Payment Server Not Responding ! Contact Email : creativeapptechnology@gmail.com for Support");
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                // Log.d("Errors", String.valueOf(error));
                if(paymentStatus!=null) {
                    if (paymentStatus.equalsIgnoreCase("Credit")) {
                        showDialog("Payment Successfull ! Will Reflect in Your Account Soon! Contact Email : creativeapptechnology@gmail.com for Support");
                    } else {
                        showDialog("Payment Failed ! Contact Email : creativeapptechnology@gmail.com for Support");
                    }
                }
                else{
                    showDialog("Payment Failed ! Contact Email : creativeapptechnology@gmail.com for Support");
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private void ShowPaymentDialog2(String response) {
        Log.d("Res",""+response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equalsIgnoreCase("success")) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                View view = getLayoutInflater().inflate(R.layout.dialog_payment_success, null);
                FloatingActionButton fab = view.findViewById(R.id.fab);
                TextView sorry_thanks = view.findViewById(R.id.sorry_thanks);
                TextView message = view.findViewById(R.id.message);
                TextView date = view.findViewById(R.id.date);
                TextView times = view.findViewById(R.id.times);
                TextView name = view.findViewById(R.id.name);
                TextView email = view.findViewById(R.id.email);
                TextView amount = view.findViewById(R.id.amount);
                TextView status = view.findViewById(R.id.status);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date1 = new Date();

                SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
                Date date2 = new Date();
                // System.out.println(formatter.format(date));


                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                    sorry_thanks.setText("Thank You");
                    message.setText("Your transaction was successful");
                    status.setText("Completed");
                } else {
                    sorry_thanks.setText("Sorry");
                    message.setText("Your transaction was unsuccessful");
                    status.setText("Failed");
                }


                date.setText(formatter.format(date1));
                times.setText(formatter1.format(date2));
                UserModel userModel = StaticMethods.getUserDetails(getContext());
                name.setText(userModel.getName());
                email.setText(userModel.getEmail());
                amount.setText(jsonObject.getString("amount1"));


                alertDialog.setView(view);
                final AlertDialog alertDialog1 = alertDialog.show();
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.dismiss();
                        RefreshUI();
                    }
                });
            }
            else{
                showDialog("Payment Failed ! Contact Email : creativeapptechnology@gmail.com for Support");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            showDialog("Payment Failed ! Contact Email : creativeapptechnology@gmail.com for Support");
        }

    }


    private static ReviewManager manager;
    public void RateApp(Activity context){
        if(manager!=null) {
            try {

                com.google.android.play.core.tasks.Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // We can get the ReviewInfo object
                        ReviewInfo reviewInfo = task.getResult();
                        com.google.android.play.core.tasks.Task<Void> flow = manager.launchReviewFlow(context, reviewInfo);
                        flow.addOnCompleteListener(task2 -> {
                            // The flow has finished. The API does not indicate whether the user
                            // reviewed or not, or even whether the review dialog was shown. Thus, no
                            // matter the result, we continue our app flow.
                            Log.d("TASK REVIEW ", "" + task2.getResult());
                        });
                    } else {
                        // There was some problem, log or handle the error code.
                        //  @ReviewErrorCode
                        if (task.getException() != null) {
                            task.getException().printStackTrace();
                            Log.d("Error Review : ", "" + task.getException().getMessage());

                        }
                        // int reviewErrorCode = ((TaskException) task.getException()).getErrorCode();

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=com.silverlinesoftwares.intratips"));
                        startActivity(intent);
                    }
                });

            } catch (Exception e) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.silverlinesoftwares.intratips"));
                startActivity(intent);
            }
        }
        else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.silverlinesoftwares.intratips"));
            startActivity(intent);
        }
    }

}

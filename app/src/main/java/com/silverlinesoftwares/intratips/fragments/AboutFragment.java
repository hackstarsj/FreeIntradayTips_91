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

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

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

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.silverlinesoftwares.intratips.BuildConfig;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.FacebookLoginActivity;
import com.silverlinesoftwares.intratips.activity.GoogleLoginActivity;
import com.silverlinesoftwares.intratips.activity.LoginActivity;
import com.silverlinesoftwares.intratips.activity.SignUpActivity;
import com.silverlinesoftwares.intratips.activity.VideoActivtiy;
import com.silverlinesoftwares.intratips.models.UserModel;
import com.silverlinesoftwares.intratips.util.StaticMethods;


public class AboutFragment extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int GOOGLE_SINGIN_REQUEST = 112;
    private static final int FACEBOOK_REQUEST_CODE = 113;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CallbackManager callbackManager;

    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private int requestCodeClick=112;

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
                startActivity(new Intent(getContext(), FacebookLoginActivity.class));
            }
        });
        login_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), GoogleLoginActivity.class));
            }
        });


        app_version.setText(BuildConfig.VERSION_NAME);
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
        ImageView ff3=view.findViewById(R.id.upstock);
        ff2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://alicebluepartner.com/furthergrow/"));
                startActivity(intent);
            }
        });

        ff3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://upstox.com/open-account/?f=Z1JV"));
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
                    buy_1.setText("Already Purchase PRO PLUS Plan");

                    buy_2_2.setText("Already Purchase PRO PLUS Plan");

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

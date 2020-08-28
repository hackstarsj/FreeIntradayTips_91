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
import com.gocashfree.cashfreesdk.CFPaymentService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.instamojo.android.Instamojo;
import com.instamojo.android.helpers.Constants;
import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.silverlinesoftwares.intratips.BuildConfig;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.LoginActivity;
import com.silverlinesoftwares.intratips.activity.PaymentGatewayActivityCashFree;
import com.silverlinesoftwares.intratips.activity.SignUpActivity;
import com.silverlinesoftwares.intratips.activity.VideoActivtiy;
import com.silverlinesoftwares.intratips.listeners.DetailsResponseListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;
import com.silverlinesoftwares.intratips.models.UserModel;
import com.silverlinesoftwares.intratips.tasks.auth.FetchProfileTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
        LinearLayout other_line=view.findViewById(R.id.other_line);
        LinearLayout what_line=view.findViewById(R.id.telegram);
        Button rates=view.findViewById(R.id.rate_app);
        ImageView ff=view.findViewById(R.id.image_pen);
        LinearLayout youtube=view.findViewById(R.id.youtube);
        TextView app_version=view.findViewById(R.id.app_version);


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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.silverlinesoftwares.intratips"));
                startActivity(intent);

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
        Button buy_1=view.findViewById(R.id.buy_pre_1);
        Button buy_1_1=view.findViewById(R.id.buy_pre_1_1);
        Button join_paid_premium=view.findViewById(R.id.join_paid_premium);
        Button join_pro_plus_premium=view.findViewById(R.id.join_pro_plus_premium);
        Button buy_2=view.findViewById(R.id.buy_pre_2);
        Button buy_2_2=view.findViewById(R.id.buy_pre_2_2);
        Button buy_3=view.findViewById(R.id.buy_pre_3);
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
                        ServerResponse("1");
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

        buy_1_1.setOnClickListener(new View.OnClickListener() {
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

        buy_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(StaticMethods.getLoginToken(getContext())!=null) {
                    if(StaticMethods.getUserDetails(getContext())!=null) {
                        ServerResponse("2");
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

        buy_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(StaticMethods.getLoginToken(getContext())!=null) {
                    if(StaticMethods.getUserDetails(getContext())!=null) {
                        ServerResponse2();
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
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        if(StaticMethods.getUserDetails(getContext())!=null){
            login_line.setVisibility(View.GONE);
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
                buy_2.setText("PAID");
                buy_2_2.setText("PAID");
                buy_2.setEnabled(false);
                buy_2_2.setEnabled(false);
                buy_1.setVisibility(View.GONE);
                buy_1_1.setVisibility(View.GONE);
            }
            else if(userModel.getCurrent_plan().equalsIgnoreCase("PREMIUM")){
                buy_1.setText("PAID");
                buy_1_1.setText("PAID");
                buy_1.setEnabled(false);
                buy_1_1.setEnabled(false);
                buy_2.setVisibility(View.GONE);
                buy_2_2.setVisibility(View.GONE);
            }
            else{
                buy_1.setText("BUY SERVER 2");
                buy_1_1.setText("BUY SERVER 1");
                buy_2.setText("BUY SERVER 2");
                buy_2_2.setText("BUY SERVER 1");
                buy_1.setEnabled(true);
                buy_1_1.setEnabled(true);
                buy_2.setEnabled(true);
                buy_2_2.setEnabled(true);
            }

            if(userModel.getIs_super()!=null) {
                if (userModel.getIs_super().equalsIgnoreCase("1")) {
                    buy_3.setText("PAID");
                    buy_3_3.setText("PAID");
                    buy_2.setText("Aleardy Purchase PRO PLUS Plan");
                    buy_1.setText("Aleardy Purchase PRO PLUS Plan");

                    buy_2_2.setText("Aleardy Purchase PRO PLUS Plan");
                    buy_1_1.setText("Aleardy Purchase PRO PLUS Plan");

                    buy_1.setEnabled(false);
                    buy_2.setEnabled(false);
                    buy_1_1.setEnabled(false);
                    buy_2_2.setEnabled(false);
                    buy_3.setEnabled(false);
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







    private void ServerResponse2() {

        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Redirecting to Payment Gateway\n Don't Press Back Button!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final UserModel userModel=StaticMethods.getUserDetails(getContext());

        StringRequest sr = new StringRequest(com.android.volley.Request.Method.POST, "https://furthergrow.silverlinesoftwares.com/login/buy_plan_plus", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   @Override
                // public void onResponse(String response) {
                if(progressDialog!=null) {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                try {
                    Map<String,String> map=new HashMap<>();
                    JSONObject jsonObj = new JSONObject(response);
                    if(jsonObj.length()>0) {


                        //for(int i=0;i<jsonObj.length();i++){

                        map.put("MID", jsonObj.getString("MID"));
                        // map.put("ORDER_ID","ORDER01234");
                        map.put("ORDER_ID", jsonObj.getString("ORDER_ID"));
                        map.put("CUST_ID", jsonObj.getString("CUST_ID"));
                        map.put("INDUSTRY_TYPE_ID", jsonObj.getString("INDUSTRY_TYPE_ID"));
                        map.put("CHANNEL_ID", jsonObj.getString("CHANNEL_ID"));
                        map.put("TXN_AMOUNT", jsonObj.getString("TXN_AMOUNT"));
                        map.put("WEBSITE", jsonObj.getString("WEBSITE"));
                        // map.put("CALLBACK_URL","https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=ORDER01234");
                        map.put("CALLBACK_URL", jsonObj.getString("CALLBACK_URL"));
                        map.put("EMAIL", jsonObj.getString("EMAIL"));
                        map.put("MOBILE_NO", jsonObj.getString("MOBILE_NO"));
                        map.put("CHECKSUMHASH", jsonObj.getString("CHECKSUMHASH"));
                        PaytmPay(map);
                        //  }
                    }




                } catch (JSONException e) {
                    showDialog("Payment Gateway Down! Please Try Again Later! Contact Email : creativeapptechnology@gmail.com for Support");
                    Toast.makeText(getContext(), "Failed To Parse Response", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                // Log.d("Response", response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null) {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                showDialog("Something Went Wrong! Contact Email : creativeapptechnology@gmail.com for Support");
                //Log.d("Errors", String.valueOf(error));
            }

        })
        {
            @Override
            protected Map<String,String> getParams(){
                String token=StaticMethods.getLoginToken(getContext());
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",userModel.getEmail());
                params.put("phone",userModel.getPhone());
                params.put("user_id",userModel.getId());
                if(token==null){
                    token="";
                }
                params.put("token",token);

                return params;
            } @Override
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

    public void ServerResponse(final String pay_id){
       final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Redirecting to Payment Gateway\n Don't Press Back Button!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final UserModel userModel=StaticMethods.getUserDetails(getContext());

        StringRequest sr = new StringRequest(com.android.volley.Request.Method.POST, "https://furthergrow.silverlinesoftwares.com/login/buy_plan", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   @Override
                // public void onResponse(String response) {
                if(progressDialog!=null) {
                 if(progressDialog.isShowing()) {
                     progressDialog.dismiss();
                 }
                }
                try {
                    Map<String,String> map=new HashMap<>();
                    JSONObject jsonObj = new JSONObject(response);
                    if(jsonObj.length()>0) {

                        if(jsonObj.has("payment_type")){
                            String payment_type=jsonObj.getString("payment_type");
                            if(payment_type.equalsIgnoreCase("1")){
                                JSONObject new_payment=jsonObj.getJSONObject("new_payment");
                                //Log.d("New Payment ",new_payment.toString());
                                if(new_payment.has("order_id")){
                                    initiateSDKPayment(new_payment.getString("order_id"));
                                    return;
                                }
                            }
                        }
                        //for(int i=0;i<jsonObj.length();i++){

                        map.put("MID", jsonObj.getString("MID"));
                        // map.put("ORDER_ID","ORDER01234");
                        map.put("ORDER_ID", jsonObj.getString("ORDER_ID"));
                        map.put("CUST_ID", jsonObj.getString("CUST_ID"));
                        map.put("INDUSTRY_TYPE_ID", jsonObj.getString("INDUSTRY_TYPE_ID"));
                        map.put("CHANNEL_ID", jsonObj.getString("CHANNEL_ID"));
                        map.put("TXN_AMOUNT", jsonObj.getString("TXN_AMOUNT"));
                        map.put("WEBSITE", jsonObj.getString("WEBSITE"));
                        // map.put("CALLBACK_URL","https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=ORDER01234");
                        map.put("CALLBACK_URL", jsonObj.getString("CALLBACK_URL"));
                        map.put("EMAIL", jsonObj.getString("EMAIL"));
                        map.put("MOBILE_NO", jsonObj.getString("MOBILE_NO"));
                        map.put("CHECKSUMHASH", jsonObj.getString("CHECKSUMHASH"));
                        PaytmPay(map);
                        //  }
                    }




                } catch (JSONException e) {
                    showDialog("Payment Gateway Down! Please Try Again Later! Contact Email : creativeapptechnology@gmail.com for Support");
                    Toast.makeText(getContext(), "Failed To Parse Response", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
               // Log.d("Response", response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null) {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                showDialog("Something Went Wrong! Contact Email : creativeapptechnology@gmail.com for Support");
                //Log.d("Errors", String.valueOf(error));
            }

        })
        {
            @Override
            protected Map<String,String> getParams(){
            String token=StaticMethods.getLoginToken(getContext());
            Map<String,String> params = new HashMap<String, String>();
            params.put("email",userModel.getEmail());
            params.put("phone",userModel.getPhone());
            params.put("user_id",userModel.getId());
            params.put("plan_id",pay_id);
            if(token==null){
                token="";
            }
            params.put("token",token);

            return params;
        } @Override
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

    public void sendtoserver(final Bundle  data){
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Please Wait Processing Payment!\n Don't Press Back Button!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(com.android.volley.Request.Method.POST, "https://furthergrow.silverlinesoftwares.com/login/pay_response", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("Response ",""+response);
                if(data!=null) {
                    ShowPaymentDialog(data);
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
                if(data!=null) {
                    if (data.getString("STATUS").equalsIgnoreCase("TXN_SUCCESS")) {
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
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                if (data != null) {
                    for (String key : data.keySet()) {
                        params.put(key,data.getString(key));
                    }
                }

                return params;
            } @Override
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
                String fcm="";
                fcm= FirebaseInstanceId.getInstance().getToken();
                if(fcm==null){
                    fcm="";
                }
                FetchProfileTask fetchProfileTask=new FetchProfileTask(AboutFragment.this);
                fetchProfileTask.execute(StaticMethods.getLoginToken(getContext()),fcm);
            }
            else{
                StaticMethods.removeToken(getContext());
                StaticMethods.removeUser(getContext());
            }
        }

    }

    public void PaytmPay(Map<String,String> paramMap) {
        PaytmPGService Service = null;
        Service = PaytmPGService.getProductionService();

        PaytmOrder Order = new PaytmOrder((HashMap<String, String>) paramMap);
        PaytmClientCertificate Certificate = new PaytmClientCertificate("password", "filename");
        Service.initialize(Order, null);
        Service.startPaymentTransaction(getContext(), true, true, new PaytmPaymentTransactionCallback() {

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {
                Log.d("LOG", "UI Error Occur."+inErrorMessage);
                showDialog("Something Went Wrong! Contact Email : creativeapptechnology@gmail.com for Support");
                // Toast.makeText(getContext(), " UI Error Occur. ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTransactionResponse(Bundle inResponse) {
                //Log.d("LOG", "Payment Transaction : " + inResponse);

                sendtoserver(inResponse);
                //Toast.makeText(getContext(), "Payment Transaction response "+inResponse.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void networkNotAvailable() {
                //Log.d("LOG", "UI Error Occur.");
                showDialog("Something Wrong on Internet Connection! Please Try Again Later! Contact Email : creativeapptechnology@gmail.com for Support");
                //Toast.makeText(getContext(), " UI Error Occur. ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {
                Log.d("LOG", "UI Error Occur."+inErrorMessage);
                showDialog("Payment Gateway Error! Please Try Again Later! Contact Email : creativeapptechnology@gmail.com for Support "+inErrorMessage);
                //Toast.makeText(getContext(), " Severside Error "+ inErrorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode,
                                              String inErrorMessage, String inFailingUrl) {
                Log.d("LOG",inErrorMessage);
                showDialog("Error Loading Payment Gateway! Please Try Again Later! Contact Email : creativeapptechnology@gmail.com for Support");
            }
            @Override
            public void onBackPressedCancelTransaction() {
                Log.d("LOG","Back");
                showDialog("Transaction Failed! You Pressed Back Button! Contact Email : creativeapptechnology@gmail.com for Support");
            }

            @Override
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                showDialog("Payment Cancel! Contact Email : creativeapptechnology@gmail.com for Support");
            }

        });

    }

    @Override
    public void onProfile(ResponseModel data) {
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
}

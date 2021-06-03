package com.silverlinesoftwares.intratips.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gocashfree.cashfreesdk.CFPaymentService;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.instamojo.android.Instamojo;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.fragments.AboutFragment;
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

import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;

public class PaymentGatewayActivityCashFree extends AppCompatActivity implements DetailsResponseListener, Instamojo.InstamojoPaymentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        String pay_id=getIntent().getStringExtra("pay_id");
        if(pay_id!=null) {
            if (pay_id.equalsIgnoreCase("3")){
                ServerResponseCashFree2(pay_id);
            }
            else{
                ServerResponseCashFree(pay_id);
            }
         }
    }

    private void CashFreeShowPage(Map<String, String> map, String token) {
        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);
        cfPaymentService.doPayment(PaymentGatewayActivityCashFree.this, map, token, "PROD");

    }

    private void CashFreeShowPage2(Map<String, String> map, String token) {
        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);
        cfPaymentService.doPayment(PaymentGatewayActivityCashFree.this, map, token, "PROD");
    }

    private void ServerResponseCashFree(String pay_id) {

        final ProgressDialog progressDialog=new ProgressDialog(PaymentGatewayActivityCashFree.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Redirecting to Payment Gateway\n Don't Press Back Button!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(PaymentGatewayActivityCashFree.this);
        final UserModel userModel= StaticMethods.getUserDetails(PaymentGatewayActivityCashFree.this);

        StringRequest sr = new StringRequest(com.android.volley.Request.Method.POST, "https://furthergrow.silverlinesoftwares.com/login/cash_free_payment", new com.android.volley.Response.Listener<String>() {
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


                        map.put(PARAM_APP_ID, jsonObj.getString("app_id"));
                        map.put(PARAM_ORDER_ID, jsonObj.getString("order_id"));
                        map.put(PARAM_ORDER_AMOUNT, jsonObj.getString("amount"));
                        map.put(PARAM_ORDER_NOTE, jsonObj.getString("notes"));
                        map.put(PARAM_CUSTOMER_NAME, jsonObj.getString("name"));
                        map.put(PARAM_CUSTOMER_PHONE, jsonObj.getString("phone"));
                        map.put(PARAM_CUSTOMER_EMAIL,jsonObj.getString("email"));
                        CashFreeShowPage(map,jsonObj.getString("cftoken"));
                        //  }
                    }




                } catch (JSONException e) {
                    showDialog("Payment Gateway Down! Please Try Again Later! Contact Email : creativeapptechnology@gmail.com for Support");
                    Toast.makeText(PaymentGatewayActivityCashFree.this, "Failed To Parse Response", Toast.LENGTH_SHORT).show();
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
                String token=StaticMethods.getLoginToken(PaymentGatewayActivityCashFree.this);
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


    private void ServerResponseCashFree2(String pay_id) {

        final ProgressDialog progressDialog=new ProgressDialog(PaymentGatewayActivityCashFree.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Redirecting to Payment Gateway\n Don't Press Back Button!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(PaymentGatewayActivityCashFree.this);
        final UserModel userModel= StaticMethods.getUserDetails(PaymentGatewayActivityCashFree.this);

        StringRequest sr = new StringRequest(com.android.volley.Request.Method.POST, "https://furthergrow.silverlinesoftwares.com/login/cash_free_payment_proplus", new com.android.volley.Response.Listener<String>() {
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


                        map.put(PARAM_APP_ID, jsonObj.getString("app_id"));
                        map.put(PARAM_ORDER_ID, jsonObj.getString("order_id"));
                        map.put(PARAM_ORDER_AMOUNT, jsonObj.getString("amount"));
                        map.put(PARAM_ORDER_NOTE, jsonObj.getString("notes"));
                        map.put(PARAM_CUSTOMER_NAME, jsonObj.getString("name"));
                        map.put(PARAM_CUSTOMER_PHONE, jsonObj.getString("phone"));
                        map.put(PARAM_CUSTOMER_EMAIL,jsonObj.getString("email"));
                        CashFreeShowPage2(map,jsonObj.getString("cftoken"));
                        //  }
                    }




                } catch (JSONException e) {
                    showDialog("Payment Gateway Down! Please Try Again Later! Contact Email : creativeapptechnology@gmail.com for Support");
                    Toast.makeText(PaymentGatewayActivityCashFree.this, "Failed To Parse Response", Toast.LENGTH_SHORT).show();
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
                String token=StaticMethods.getLoginToken(PaymentGatewayActivityCashFree.this);
                Map<String,String> params = new HashMap<String, String>();
                assert userModel != null;
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

    private void initiateSDKPayment(String order_id) {
            Instamojo.getInstance().initialize(PaymentGatewayActivityCashFree.this, Instamojo.Environment.PRODUCTION);
            Instamojo.getInstance().initiatePayment(PaymentGatewayActivityCashFree.this, order_id, PaymentGatewayActivityCashFree.this);
    }

    private void showDialog(String s) {
        AlertDialog.Builder al=new AlertDialog.Builder(PaymentGatewayActivityCashFree.this);
        al.setMessage(s);
        al.setTitle("Error");
        al.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(PaymentGatewayActivityCashFree.this,MainActivity.class));
                finish();
            }
        });
        al.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
//        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d("TAG", "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)
                sendtoserver(bundle);
//                for (String key : bundle.keySet()) {
//                    if (bundle.getString(key) != null) {
//                        Log.d("TAG", key + " : " + bundle.getString(key));
//                    }
//                }
        }
    }

    public void sendtoserver(final Bundle  data){
        final ProgressDialog progressDialog=new ProgressDialog(PaymentGatewayActivityCashFree.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Please Wait Processing Payment!\n Don't Press Back Button!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(PaymentGatewayActivityCashFree.this);
        StringRequest sr = new StringRequest(com.android.volley.Request.Method.POST, "https://furthergrow.silverlinesoftwares.com/login/pay_response_cashfree", new com.android.volley.Response.Listener<String>() {
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
                    if (data.getString("txStatus").equalsIgnoreCase("SUCCESS")) {
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
                StringBuilder all_response= new StringBuilder();
                if (data != null) {
                    for (String key : data.keySet()) {
                        params.put(key,data.getString(key));
                        all_response.append(" ").append(key).append(" : ").append(data.getString(key));
                    }
                }
                params.put("all_response", all_response.toString());

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
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(PaymentGatewayActivityCashFree.this);
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

        if(data.getString("txStatus").equalsIgnoreCase("SUCCESS")){
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
        UserModel userModel=StaticMethods.getUserDetails(PaymentGatewayActivityCashFree.this);
        name.setText(userModel.getName());
        email.setText(userModel.getEmail());
        amount.setText(data.getString("orderAmount"));




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

        if(StaticMethods.getUserDetails(PaymentGatewayActivityCashFree.this)!=null){
            if(StaticMethods.getLoginToken(PaymentGatewayActivityCashFree.this)!=null){

                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(!task.isSuccessful()){
                            return;
                        }
                        String tok=task.getResult();
                        FetchProfileTask fetchProfileTask=new FetchProfileTask(PaymentGatewayActivityCashFree.this);
                        fetchProfileTask.execute(StaticMethods.getLoginToken(PaymentGatewayActivityCashFree.this),tok);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FetchProfileTask fetchProfileTask=new FetchProfileTask(PaymentGatewayActivityCashFree.this);
                        fetchProfileTask.execute(StaticMethods.getLoginToken(PaymentGatewayActivityCashFree.this),"");
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        FetchProfileTask fetchProfileTask=new FetchProfileTask(PaymentGatewayActivityCashFree.this);
                        fetchProfileTask.execute(StaticMethods.getLoginToken(PaymentGatewayActivityCashFree.this),"");
                    }
                });

            }
            else{
                StaticMethods.removeToken(PaymentGatewayActivityCashFree.this);
                StaticMethods.removeUser(PaymentGatewayActivityCashFree.this);
            }
        }

    }


    @Override
    public void onProfile(ResponseModel data) {
        if(data!=null){
            if(data.getStatus_code().equalsIgnoreCase("200")){
                StaticMethods.saveUserDetails(PaymentGatewayActivityCashFree.this,data.getUser());
                startActivity(new Intent(PaymentGatewayActivityCashFree.this,MainActivity.class));

            }
            else{
                StaticMethods.removeUser(PaymentGatewayActivityCashFree.this);
                StaticMethods.removeToken(PaymentGatewayActivityCashFree.this);
                showDialog("Session Expired! Login Again.");

            }
        }
        else{
            StaticMethods.removeUser(PaymentGatewayActivityCashFree.this);
            StaticMethods.removeToken(PaymentGatewayActivityCashFree.this);
            showDialog("Session Expired! Login Again.");

        }
    }

    @Override
    public void onFailedProfile(String msg) {
        StaticMethods.removeUser(PaymentGatewayActivityCashFree.this);
        StaticMethods.removeToken(PaymentGatewayActivityCashFree.this);
        showDialog("Failed to Fetch your Profile! Login Again.");
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
        final ProgressDialog progressDialog=new ProgressDialog(PaymentGatewayActivityCashFree.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Please Wait Processing Payment!\n Don't Press Back Button!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(PaymentGatewayActivityCashFree.this);
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
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(PaymentGatewayActivityCashFree.this);
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
                UserModel userModel = StaticMethods.getUserDetails(PaymentGatewayActivityCashFree.this);
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
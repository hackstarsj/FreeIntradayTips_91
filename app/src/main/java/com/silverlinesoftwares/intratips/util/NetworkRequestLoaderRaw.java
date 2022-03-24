package com.silverlinesoftwares.intratips.util;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class NetworkRequestLoaderRaw {
    public static int running=0;

    public static void LoadData(Context context, String url, Map<String, String> headers, NetworkRequestListener networkRequestListener, int method, String postParams, String from) {
        running=0;
        networkRequestListener.onStartLoading();
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        try {
            executor.execute(() -> {
                fetchData(context,url,networkRequestListener,method,headers,postParams,handler,from);

            });
        }
        catch (Exception e){
            networkRequestListener.onErrorLoading("Something Went Wrong! While Processing Request..");
        }
    }

    private static void fetchData(Context context, String url, NetworkRequestListener networkRequestListener, int method, Map<String, String> headers, String postParams, Handler handler, String from) {
        running=running+1;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response==null){
                    if(running>=3) {
                        handler.post(() -> {

                            networkRequestListener.onErrorLoading("Server Error! Please Try Again..");
                        });
                    }
                    else {
                        fetchData(context,url,networkRequestListener,method,headers,postParams,handler,from);
                    }
                }
                else {
                    handler.post(() -> {
                        networkRequestListener.onCompletedLoading(response, from);
                    });
                }
            }
            },
            new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(running>=3) {
                    handler.post(() -> {

                        networkRequestListener.onErrorLoading("Server Error! Please Try Again..");
                    });
                }
                else {
                    fetchData(context,url,networkRequestListener,method,headers,postParams,handler,from);
                }
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return postParams.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers==null){
                  Map<String, String> headers2=new HashMap<>();
                    headers2.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
                    return headers2;
                }
                else{
                    headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
                    return headers;
                }
            }
        };

        queue.add(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

}

package com.silverlinesoftwares.intratips.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.ChartWebActivity;
import com.silverlinesoftwares.intratips.adapters.VideoAdapter;
import com.silverlinesoftwares.intratips.models.VideoMoel;
import com.silverlinesoftwares.intratips.util.MyAsyncListener;
import com.silverlinesoftwares.intratips.util.RecyclerTouchListener;
import com.silverlinesoftwares.intratips.util.StaticMethods;
import com.silverlinesoftwares.intratips.util.VideoActivity;
import com.silverlinesoftwares.intratips.util.VideoDataLoader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class TrendingFragment extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final double PIC_WIDTH = 1000;
    private WebView webView;


    public static TrendingFragment newInstance(String param1, String param2) {
        TrendingFragment fragment = new TrendingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }

    ProgressBar progress;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progress=view.findViewById(R.id.progress);

        webView=view.findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        webView.getSettings().setAllowFileAccess(false);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        webView.setPadding(0, 0, 0, 0);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                progress.setVisibility(View.GONE);
                // Inject CSS when page is done loading
                injectCSS();
                injectJS();
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(this, "Android");
        webView.loadUrl("https://in.tradingview.com/screener/");
        StaticMethods.showInterestialAds(getActivity());

    }

    private void injectCSS() {
        try {
            InputStream inputStream = getActivity().getAssets().open("style3.css");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void injectJS() {
        try {
            InputStream inputStream = getActivity().getAssets().open("js_trend.js");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('script');" +
                    "style.type = 'text/javascript';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getScale(){
        if(getActivity()!=null) {
            Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int width = display.getWidth();
            Double val = new Double(width) / new Double(PIC_WIDTH);
            val = val * 100d;
            return val.intValue();
        }
        else{
            return 0;
        }
    }

    @JavascriptInterface
    public void alertJson(final String myJSON) {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(getActivity()!=null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("Symbol", "" + myJSON);
                                    onTechnical(myJSON);
                                }
                            });

                        }
                    }
                },
                1000);
         }


    public void onTechnical(String symbol) {
        StaticMethods.showInterestialAds(getActivity());

        try {
            symbol = symbol.replace(".BO", "");
            symbol = symbol.replace(".NS", "");
            symbol = symbol.replace("NSE:", "");
            symbol = symbol.replace("BSE:", "");

            AlertDialog.Builder al = new AlertDialog.Builder(getContext());
            View view = getLayoutInflater().inflate(R.layout.weblayout_dialog, null);
            EditText edit = (EditText) view.findViewById(R.id.edit);
            final ProgressBar progress = view.findViewById(R.id.progress);
            TextView title = view.findViewById(R.id.title);
            edit.setFocusable(true);
            edit.requestFocus();

            title.setText("Technical Analysis : " + symbol);
            al.setView(view);
            Button button = view.findViewById(R.id.close);
            final WebView webView1 = view.findViewById(R.id.webview);
            try {

                InputStream is = getContext().getAssets().open("tech2.html");
                int size = is.available();

                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                String str = new String(buffer);

                str = str.replace("SYMBOL_HOLDER", symbol);

                if (Build.VERSION.SDK_INT >= 21) {
                    CookieManager.getInstance().setAcceptThirdPartyCookies(webView1, true);
                } else {
                    CookieManager.getInstance().setAcceptCookie(true);
                }
                webView1.loadUrl("about:blank");
                webView1.getSettings().setLoadWithOverviewMode(true);

                webView1.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        injectCSS2(webView1);
                        super.onPageStarted(view, url, favicon);
                        progress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
//                    injectCSS(webView);
                        super.onPageFinished(view, url);
                        progress.setVisibility(View.GONE);
                    }
                });
                webView1.getSettings().setJavaScriptEnabled(true);
                webView1.loadData(str, "text/html", "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            final AlertDialog alertDialog = al.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void injectCSS2(WebView webView) {
        try {
            InputStream inputStream = getActivity().getAssets().open("style_chart.css");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

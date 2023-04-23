package com.silverlinesoftwares.intratips.fragments.stockdetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.ChartWebActivity;
import com.silverlinesoftwares.intratips.util.Constant;

import java.io.InputStream;


public class ChartFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public ChartFragment() {
        // Required empty public constructor
    }

    public static ChartFragment newInstance(String param1, String param2) {
        ChartFragment fragment = new ChartFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button full_scrren;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_web, container, false);
    }

    WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        full_scrren=view.findViewById(R.id.full_scrren);
        Bundle bundle=getArguments();
        String symbol="";
        if (bundle != null) {
            symbol = bundle.getString(Constant.search);
        }
        webView=view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        String finalSymbol = symbol;
        full_scrren.setOnClickListener(v -> startActivity(new Intent(getContext(), ChartWebActivity.class).putExtra("url","https://in.tradingview.com/chart/?symbol=NSE%3A"+ finalSymbol.replace(".NS","").replace(".BS",""))));
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                // Inject CSS when page is done loading
                injectCSS();
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl("https://in.tradingview.com/chart/?symbol=NSE%3A"+symbol.replace(".NS","").replace(".BS",""));

    }


    private void injectCSS() {
        try {
            if(getContext()!=null) {
                InputStream inputStream = getContext().getAssets().open("style_chart.css");
                byte[] buffer = new byte[inputStream.available()];
                final int read = inputStream.read(buffer);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

package com.silverlinesoftwares.intratips.fragments.stockdetails;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.NewsListener;
import com.silverlinesoftwares.intratips.listeners.StockDetailListener;
import com.silverlinesoftwares.intratips.models.NewsModel;
import com.silverlinesoftwares.intratips.models.SummaryModel;
import com.silverlinesoftwares.intratips.tasks.NewsTask;
import com.silverlinesoftwares.intratips.tasks.StockDetailTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;


public class TechnicalAnalysisFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressBar progress;
    private WebView webView;


    public TechnicalAnalysisFragment() {
        // Required empty public constructor
    }

    public static TechnicalAnalysisFragment newInstance(String param1, String param2) {
        TechnicalAnalysisFragment fragment = new TechnicalAnalysisFragment();
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
        return inflater.inflate(R.layout.fragment_technical, container, false);
    }

    

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        progress=view.findViewById(R.id.progress);
        webView=view.findViewById(R.id.webview);
        if(bundle!=null) {
            ShowData(bundle);

        }

    }

    private void ShowData(Bundle bundle) {
        String symbol=bundle.getString(Constant.search).replace(".NS", "").replace(".BO", "");

        symbol=symbol.replace(".BO","");
        symbol=symbol.replace(".NS","");


        try {

            InputStream is = getContext().getAssets().open("tech.html");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String str = new String(buffer);

            str = str.replace("SYMBOL_HOLDER",symbol);

            if (Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            } else {
                CookieManager.getInstance().setAcceptCookie(true);
            }
            webView.loadUrl("about:blank");
            webView.getSettings().setLoadWithOverviewMode(true);

            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    injectCSS(webView);
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
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadData(str,"text/html","utf-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void injectCSS(WebView webView) {
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

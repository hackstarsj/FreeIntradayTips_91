package com.silverlinesoftwares.intratips.fragments.stockdetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.tasks.AnalysisTask;
import com.silverlinesoftwares.intratips.util.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class AnalysisFragment extends Fragment implements ChartListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String all_data="";


    public AnalysisFragment() {
        // Required empty public constructor
    }

    public static AnalysisFragment newInstance(String param1, String param2) {
        AnalysisFragment fragment = new AnalysisFragment();
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

    ProgressBar progress;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        String symbol= "";
        if (bundle != null) {
            symbol = bundle.getString(Constant.search);
        }
        progress=view.findViewById(R.id.progress);
        webView=view.findViewById(R.id.webview);


        AnalysisTask analysisTask=new AnalysisTask(AnalysisFragment.this);
        analysisTask.execute(symbol);


    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onSucess(String data) {

        progress.setVisibility(View.GONE);
        Document document= Jsoup.parse(data);
        StringBuilder ddd= new StringBuilder();
        Elements elements=document.getElementsByClass("smartphone_Pt(10px)");

        if(elements.size()>0) {
            Elements elements1=elements.get(0).getElementsByTag("table");
            for (int k=0;k<elements1.size();k++){
                ddd.append(elements1.get(k).children().toString());
            }
            all_data = "<html><head>" +
                    "<style>" +
                    " body { margin:0px;padding:0px; }" +
                  " th{ background:black;color:white; } " +
                    "table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } " +
                    "th,td{ padding:10px } " +
                    "table{ margin:0px;padding:0px;border-radius:10px;width:100%;height:100%;} " +
                    "tr:nth-child(2n) {\n" +
                    "\n" +
                   "    background-color:white;\n" +
                    "    box-shadow: 5px 5px 5px red;\n" +
                    "\n" +
                    "}" +
                    "</style>" +
                    "</head><body><table>" +ddd
                    +"</table></body></html>";
        }
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadData(all_data,"text/html","UTF-8");

    }

    @Override
    public void onFailed(String msg) {
        if(progress!=null) {
            progress.setVisibility(View.GONE);
        }

        if(getContext()!=null) {
            Toast.makeText(getContext(), "" + msg, Toast.LENGTH_SHORT).show();
        }

    }
}

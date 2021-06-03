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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.tasks.VolumeTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;


public class VolumeFragment extends Fragment implements ChartListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinner;

    ProgressBar progressBar;

    public VolumeFragment() {
        // Required empty public constructor
    }

    public static VolumeFragment newInstance(String param1, String param2) {
        VolumeFragment fragment = new VolumeFragment();
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
        return inflater.inflate(R.layout.fragment_volume, container, false);
    }
    final String[] height_data={"0","0","0","1000","3000","6000"};


    WebView webView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner=view.findViewById(R.id.volume_filter);
        webView=view.findViewById(R.id.webview);
        progressBar=view.findViewById(R.id.progress);
        String[] filter_text={"Day","Week","1 Month","3 Months","6 Months","1 year"};
        final String[] filter_data={"day","week","1month","3month","6month","12month"
        };
        Bundle bundle=getArguments();
        final String symbol=bundle.getString(Constant.search);
        ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,filter_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE
                );
                VolumeTask financialTask=new VolumeTask(VolumeFragment.this);
                financialTask.execute(new String[]{symbol,filter_data[position]});

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setSelection(2);
        VolumeTask financialTask=new VolumeTask(VolumeFragment.this);
        financialTask.execute(new String[]{symbol,"1month"});

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onSucess(String data) {
        progressBar.setVisibility(View.GONE);
        try{
        JSONObject jsonObject=new JSONObject(data);
        JSONArray jsondata=jsonObject.getJSONArray("data");

        JSONArray percentage=new JSONArray();
        JSONArray volumes=new JSONArray();
        JSONArray tradesd=new JSONArray();
        JSONArray dates=new JSONArray();
        for (int k=1;k<jsondata.length();k++){
            try {
                String eq=jsondata.getJSONObject(k).getString("CH_SERIES");
                if(eq.contains("EQ")) {
                   // percentage.put(jsondata.getJSONObject(k).children().get(14).text().replaceAll(",", ""));
                    volumes.put(jsondata.getJSONObject(k).getLong("CH_TOT_TRADED_QTY"));
                    tradesd.put((jsondata.getJSONObject(k).getLong("CH_TOTAL_TRADES")));
                    dates.put(jsondata.getJSONObject(k).getString("mTIMESTAMP"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

       // Log.d("Data 1",stringBuilder1.toString());
        //Log.d("Data 1",stringBuilder2.toString());
       // Log.d("Data 3",stringBuilder3.toString());
        //Log.d("Data 4",stringBuilder4.toString());

   //     Log.d("Data",""+data);



            InputStream is = getContext().getAssets().open("volume_chart.html");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String str = new String(buffer);
            str = str.replace("PLACEHOLDER_MON",dates.toString());
            str = str.replace("PLACEHOLDER_PER",percentage.toString());
            str = str.replace("PLACEHOLDER_D1_DATA",volumes.toString());
            str = str.replace("PLACEHOLDER_D2_DATA",tradesd.toString());
            str = str.replace("PLACEHOLDER_HEIGHT",height_data[spinner.getSelectedItemPosition()]);

//            if (android.os.Build.VERSION.SDK_INT >= 21) {
//                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
//            } else {
//                CookieManager.getInstance().setAcceptCookie(true);
//            }
            //webView.loadUrl("about:blank");
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setJavaScriptEnabled(true);
            //webView.setWebContentsDebuggingEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadData(str,"text/html","utf-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void onFailed(String msg) {
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
        }

        if(getContext()!=null) {
            Toast.makeText(getContext(), "" + msg, Toast.LENGTH_SHORT).show();
        }
    }


}

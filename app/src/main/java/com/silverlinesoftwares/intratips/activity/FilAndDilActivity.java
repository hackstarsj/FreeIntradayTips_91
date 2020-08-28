package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.tasks.FilTask;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FilAndDilActivity extends AppCompatActivity implements ChartListener {

    private WebView listView;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fil);
        MobileAds.initialize(FilAndDilActivity.this,getString(R.string.app_ads_id));
        progress=findViewById(R.id.progress);
        listView=findViewById(R.id.list_volume_gainer);
        FilTask gainerLooserTask=new FilTask(FilAndDilActivity.this);
        gainerLooserTask.execute();

        StaticMethods.showInterestialAds(FilAndDilActivity.this);
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,FilAndDilActivity.this);


    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);
        Document document=Jsoup.parse(data);

        Elements elements=document.getElementsByTag("table");
        String all_data="";
        if(elements.size()>0) {
            all_data = "<html><head> <title>Analysis</title><style> body{ margin:0px;padding:0px; } th{ background:dodgerblue;color:white; } table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } th,td{ padding:10px } table{ margin:0px;padding:0px;border-radius:10px;width:100%;height:100%;} tr:nth-child(2n) {\n" +
                    "\n" +
                    "    background-color: #f4f4f4;\n" +
                    "    box-shadow: 5px 5px 5px #e1d9d9;\n" +
                    "\n" +
                    "}</style></head><body><table>" +elements.toString()
                    +"</table></body></html>";
        }
        listView.loadData(all_data,"text/html","UTF-8");

    }

    @Override
    public void onFailed(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(FilAndDilActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

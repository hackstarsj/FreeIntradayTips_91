package com.silverlinesoftwares.intratips.activity;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.ViewPagerAdapter;
import com.silverlinesoftwares.intratips.fragments.subfragment.ForcastFragment;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.tasks.ForecastTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ForCastActivity extends AppCompatActivity implements ChartListener {

    private TabLayout tabLayout;
    ProgressBar progressBar;
    ViewPager viewPager;

    public static List<String> datas=new ArrayList<>();
    String[] titles={"Overview","GDP","Labour","Prices","Money","Trade","Government","Business","Consumer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economy);
        MobileAds.initialize(ForCastActivity.this,getString(R.string.app_ads_id));
        tabLayout=findViewById(R.id.tab_item);
        viewPager=findViewById(R.id.stock_page);
        progressBar=findViewById(R.id.progress);

        ForecastTask economyTask=new ForecastTask(ForCastActivity.this);
        economyTask.execute();

        StaticMethods.showInterestialAds(ForCastActivity.this);
        View adContainer2 = findViewById(R.id.adView2);
        StaticMethods.showBannerAds(adContainer2,ForCastActivity.this);

    }

    private void LoadHomePage(){
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

        for (int i=0;i<titles.length;i++) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.search, ""+i);
            ForcastFragment managementFragment = new ForcastFragment();
            managementFragment.setArguments(bundle);
            viewPagerAdapter.addFragment(managementFragment);
            viewPagerAdapter.addTitle(titles[i]);
        }

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(viewPagerAdapter.getCount()-1);

    }


    @Override
    public void onSucess(String data) {
        progressBar.setVisibility(View.GONE);
        final String regex = "href=\"(.*?)\"";
        //data=data.replaceAll(regex,"#");
        Document document1= Jsoup.parse(data);
        Node node =document1.removeAttr("href");
        Document document=Jsoup.parse(node.toString());
        //document.select("a[href~=(http|https)://goo.gl]").remove();
            Element elements1=document.getElementById("overview");
            Elements elements=elements1.getElementsByTag("tr");
            StringBuilder dd= new StringBuilder();
            for (int k=0;k<elements.size();k++) {
                dd.append("").append(elements.get(k).toString().replaceAll(regex,"#"));
            }
           String all_data = "<html><head> <title></title><style> body{ margin:0px;padding:0px; } th{ background:dodgerblue;color:white; } table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } th,td{ padding:5px } table{ margin:0px;padding:0px;border-radius:5px;width:100%;height:100%;} tr:nth-child(2n) {\n" +
                    "\n" +
                    "    background-color: #f4f4f4;\n" +
                    "    box-shadow: 5px 5px 5px #e1d9d9;\n" +
                    "\n" +
                    "} a { color:#000; }</style></head><body><table>"+dd.toString()+"</table></body></html>";

           datas.add(all_data);

        Element elements2=document.getElementById("gdp");
        String all_data2 = "<html><head> <title></title><style> body{ margin:0px;padding:0px; } th{ background:dodgerblue;color:white; } table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } th,td{ padding:5px } table{ margin:0px;padding:0px;border-radius:5px;width:100%;height:100%;} tr:nth-child(2n) {\n" +
                "\n" +
                "    background-color: #f4f4f4;\n" +
                "    box-shadow: 5px 5px 5px #e1d9d9;\n" +
                "\n" +
                "}</style></head><body>"+elements2.toString().replaceAll(regex,"#")+"</body></html>";

        datas.add(all_data2);

        Element elements3=document.getElementById("labour");
        String all_data3 = "<html><head> <title></title><style> body{ margin:0px;padding:0px; } th{ background:dodgerblue;color:white; } table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } th,td{ padding:5px } table{ margin:0px;padding:0px;border-radius:5px;width:100%;height:100%;} tr:nth-child(2n) {\n" +
                "\n" +
                "    background-color: #f4f4f4;\n" +
                "    box-shadow: 5px 5px 5px #e1d9d9;\n" +
                "\n" +
                "}</style></head><body>"+elements3.toString().replaceAll(regex,"#")+"</body></html>";

        datas.add(all_data3);

        Element elements4=document.getElementById("prices");
        String all_data4 = "<html><head> <title></title><style> body{ margin:0px;padding:0px; } th{ background:dodgerblue;color:white; } table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } th,td{ padding:5px } table{ margin:0px;padding:0px;border-radius:5px;width:100%;height:100%;} tr:nth-child(2n) {\n" +
                "\n" +
                "    background-color: #f4f4f4;\n" +
                "    box-shadow: 5px 5px 5px #e1d9d9;\n" +
                "\n" +
                "}</style></head><body>"+elements4.toString().replaceAll(regex,"#")+"</body></html>";

        datas.add(all_data4);

        Element elements5=document.getElementById("money");
        String all_data5 = "<html><head> <title></title><style> body{ margin:0px;padding:0px; } th{ background:dodgerblue;color:white; } table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } th,td{ padding:5px } table{ margin:0px;padding:0px;border-radius:5px;width:100%;height:100%;} tr:nth-child(2n) {\n" +
                "\n" +
                "    background-color: #f4f4f4;\n" +
                "    box-shadow: 5px 5px 5px #e1d9d9;\n" +
                "\n" +
                "}</style></head><body>"+elements5.toString().replaceAll(regex,"#")+"</body></html>";

        datas.add(all_data5);

        Element elements6=document.getElementById("trade");
        String all_data6 = "<html><head> <title></title><style> body{ margin:0px;padding:0px; } th{ background:dodgerblue;color:white; } table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } th,td{ padding:5px } table{ margin:0px;padding:0px;border-radius:5px;width:100%;height:100%;} tr:nth-child(2n) {\n" +
                "\n" +
                "    background-color: #f4f4f4;\n" +
                "    box-shadow: 5px 5px 5px #e1d9d9;\n" +
                "\n" +
                "}</style></head><body>"+elements6.toString().replaceAll(regex,"#")+"</body></html>";

        datas.add(all_data6);

        Element elements7=document.getElementById("government");
        String all_data7 = "<html><head> <title></title><style> body{ margin:0px;padding:0px; } th{ background:dodgerblue;color:white; } table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } th,td{ padding:5px } table{ margin:0px;padding:0px;border-radius:5px;width:100%;height:100%;} tr:nth-child(2n) {\n" +
                "\n" +
                "    background-color: #f4f4f4;\n" +
                "    box-shadow: 5px 5px 5px #e1d9d9;\n" +
                "\n" +
                "}</style></head><body>"+elements7.toString().replaceAll(regex,"#")+"</body></html>";

        datas.add(all_data7);

        Element elements8=document.getElementById("business");
        String all_data8 = "<html><head> <title></title><style> body{ margin:0px;padding:0px; } th{ background:dodgerblue;color:white; } table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } th,td{ padding:5px } table{ margin:0px;padding:0px;border-radius:5px;width:100%;height:100%;} tr:nth-child(2n) {\n" +
                "\n" +
                "    background-color: #f4f4f4;\n" +
                "    box-shadow: 5px 5px 5px #e1d9d9;\n" +
                "\n" +
                "}</style></head><body>"+elements8.toString().replaceAll(regex,"#")+"</body></html>";

        datas.add(all_data8);

        Element elements9=document.getElementById("consumer");
        String all_data9 = "<html><head> <title></title><style> body{ margin:0px;padding:0px; } th{ background:dodgerblue;color:white; } table,td,th{ border:1px solid black;border-collapse:collapse;white-space: nowrap; } th,td{ padding:5px } table{ margin:0px;padding:0px;border-radius:5px;width:100%;height:100%;} tr:nth-child(2n) {\n" +
                "\n" +
                "    background-color: #f4f4f4;\n" +
                "    box-shadow: 5px 5px 5px #e1d9d9;\n" +
                "\n" +
                "}</style></head><body>"+elements9.toString().replaceAll(regex,"#")+"</body></html>";

        datas.add(all_data9);
        LoadHomePage();
    }

    @Override
    public void onFailed(String msg) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(ForCastActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
    }
}

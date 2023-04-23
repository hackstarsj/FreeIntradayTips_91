package com.silverlinesoftwares.intratips.subfragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.OptionAdapter;
import com.silverlinesoftwares.intratips.listeners.AccountOpenClick;
import com.silverlinesoftwares.intratips.tasks.ForexDataTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class ForexFragment extends Fragment implements AccountOpenClick {

    public static ProgressBar progress;
    private TimerTask timerTask;
    private Timer t;
    boolean isFirstRun=true;
    boolean starts=true;
    private WebView webview;


    public ForexFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final RecyclerView listView=view.findViewById(R.id.list_item);
        progress=view.findViewById(R.id.progress);
        Button pro_plus_btn=view.findViewById(R.id.pro_plus_btn);

        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        listView.setLayoutManager(linearLayoutManager);


        ArrayList<Object> currentequityModels = new ArrayList<>();
        OptionAdapter equityAdapter = new OptionAdapter(listView, currentequityModels, getActivity(), getActivity(), ForexFragment.this);
        listView.setAdapter(equityAdapter);
        pro_plus_btn.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        ForexDataTask equityTask = new ForexDataTask(getContext(), equityAdapter, currentequityModels, listView, pro_plus_btn);
        equityTask.execute();



        pro_plus_btn.setOnClickListener(view1 -> {
            if(getContext()!=null) {
                Intent intent = new Intent().setAction("bcNewMessageDownload");
                intent.putExtra("id", "data");
                getContext().sendBroadcast(intent);
            }
            //buyButtonClick.onBuyButtonClick();
        });



    }

    private void displayTargetStopLoss(String sprice_amt,TextView stop_loss,TextView target,boolean isBuy,TextView profit,TextView loss,EditText qty){
        double price_amt=Double.parseDouble(sprice_amt);

        double stop_loss_amt=price_amt*2/100;
        double profit_amt=price_amt*4/100;
         DecimalFormat df2 = new DecimalFormat("##.##");


        if(isBuy){
            double pr=price_amt+profit_amt;
            double sr=price_amt-stop_loss_amt;
            target.setText(String.format("%s", df2.format(pr)));
            stop_loss.setText(String.format("%s", df2.format(sr)));
        }
        else{
            double pr=price_amt-profit_amt;
            double sr=price_amt+profit_amt;
            target.setText(String.format("%s", df2.format(pr)));
            stop_loss.setText(String.format("%s", df2.format(sr)));
        }

        if(qty!=null){
            if(!qty.getText().toString().isEmpty()){
                profit.setText(String.format("%s", df2.format(profit_amt * Double.parseDouble(qty.getText().toString()))));
                loss.setText(String.format("%s", df2.format(stop_loss_amt * Double.parseDouble(qty.getText().toString()))));
            }
        }

    }

    private void calculatePrice(EditText priceText, EditText qty, TextView total_price) {
        DecimalFormat df2 = new DecimalFormat("##.##");
        if(priceText!=null){
            if(qty!=null){
                if(!priceText.getText().toString().isEmpty() && !qty.getText().toString().isEmpty()){
                    total_price.setText(String.format("TOTAL PRICE : %s", df2.format(Double.parseDouble(priceText.getText().toString()) * Double.parseDouble(qty.getText().toString()))));

                }
                else{
                    total_price.setText(getString(R.string.total_price_zero));
                }
            }
            else{
                total_price.setText(getString(R.string.total_price_zero));
            }
        }
        else{
            total_price.setText(getString(R.string.total_price_zero));
        }

    }


    @Override
    public void onClick() {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://zerodha.com/open-account?c=ZMPCJG"));
                startActivity(intent);
    }

    @Override
    public void onAliceClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://alicebluepartner.com/furthergrow/"));
        startActivity(intent);
    }

    @Override
    public void onUpstockClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://upstox.com/open-account/?f=Z1JV"));
        startActivity(intent);
    }
}

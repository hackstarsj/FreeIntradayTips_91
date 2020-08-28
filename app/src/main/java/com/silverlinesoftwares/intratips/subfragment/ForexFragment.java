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
import com.silverlinesoftwares.intratips.models.UserModel;
import com.silverlinesoftwares.intratips.tasks.ForexDataTask;
import com.silverlinesoftwares.intratips.tasks.McxDataTask;
import com.silverlinesoftwares.intratips.util.BuyButtonClick;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class ForexFragment extends Fragment implements AccountOpenClick {

    public static ProgressBar progress;
    private OptionAdapter equityAdapter=null;
    private TimerTask timerTask;
    private Timer t;
    boolean isFirstRun=true;
    boolean starts=true;
    private WebView webview;
    private ArrayList<Object> currentequityModels;


    public ForexFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
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


        currentequityModels=new ArrayList<>();
        equityAdapter=new OptionAdapter(listView,currentequityModels,getActivity(),getActivity(), ForexFragment.this);
        listView.setAdapter(equityAdapter);
        UserModel userModel=StaticMethods.getUserDetails(getContext());
        if(userModel!=null) {
            if(userModel.getIs_super()!=null) {
                if (userModel.getIs_super().equalsIgnoreCase("1")) {
                    pro_plus_btn.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    ForexDataTask equityTask = new ForexDataTask(getContext(), equityAdapter, currentequityModels, userModel, StaticMethods.getLoginToken(getContext()), listView, pro_plus_btn);
                    equityTask.execute();

                } else {
                    pro_plus_btn.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    pro_plus_btn.setText("Buy PRO PLUS to Unlock Forex");
                }
            }
            else{
                pro_plus_btn.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                pro_plus_btn.setText("Buy PRO PLUS to Unlock Forex");
            }
        }
        else{
            pro_plus_btn.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);

        }


        pro_plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getContext()!=null) {
                    Intent intent = new Intent().setAction("bcNewMessageDownload");
                    intent.putExtra("id", "data");
                    getContext().sendBroadcast(intent);
                }
                //buyButtonClick.onBuyButtonClick();
            }
        });

        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                FragmentTransaction ft = null;
                if (getFragmentManager() != null) {
                    ft = getFragmentManager().beginTransaction();
                    ft.detach(ForexFragment.this).attach(ForexFragment.this).commit();
                    pullToRefresh.setRefreshing(false);
                }
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }



    private void displayTargetStopLoss(String sprice_amt,TextView stop_loss,TextView target,boolean isBuy,TextView profit,TextView loss,EditText qty){
        double price_amt=Double.parseDouble(sprice_amt);

        double stop_loss_amt=price_amt*2/100;
        double profit_amt=price_amt*4/100;
         DecimalFormat df2 = new DecimalFormat("##.##");


        if(isBuy){
            double pr=price_amt+profit_amt;
            double sr=price_amt-stop_loss_amt;
            target.setText(""+ df2.format(pr));
            stop_loss.setText(""+df2.format(sr));
        }
        else{
            double pr=price_amt-profit_amt;
            double sr=price_amt+profit_amt;
            target.setText(""+df2.format(pr));
            stop_loss.setText(""+df2.format(sr));
        }

        if(qty!=null){
            if(!qty.getText().toString().isEmpty()){
                profit.setText(""+df2.format(profit_amt*Double.parseDouble(qty.getText().toString())));
                loss.setText(""+df2.format(stop_loss_amt*Double.parseDouble(qty.getText().toString())));
            }
        }

    }

    private void calculatePrice(EditText priceText, EditText qty, TextView total_price) {
        DecimalFormat df2 = new DecimalFormat("##.##");
        if(priceText!=null){
            if(qty!=null){
                if(!priceText.getText().toString().isEmpty() && !qty.getText().toString().isEmpty()){
                    total_price.setText("TOTAL PRICE : "+df2.format(Double.parseDouble(priceText.getText().toString())*Double.parseDouble(qty.getText().toString())));

                }
                else{
                    total_price.setText("TOTAL PRICE : 0");
                }
            }
            else{
                total_price.setText("TOTAL PRICE : 0");
            }
        }
        else{
            total_price.setText("TOTAL PRICE : 0");
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
}

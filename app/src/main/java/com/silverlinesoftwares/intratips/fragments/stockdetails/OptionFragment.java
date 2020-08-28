package com.silverlinesoftwares.intratips.fragments.stockdetails;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.OptionChainAdapter;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.models.OptionChainModel;
import com.silverlinesoftwares.intratips.tasks.OptionTask;
import com.silverlinesoftwares.intratips.util.Constant;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class OptionFragment extends Fragment implements ChartListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context mContext;
    private String all_data="";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext=null;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar progress;


    public OptionFragment() {
        // Required empty public constructor
    }

    public static OptionFragment newInstance(String param1, String param2) {
        OptionFragment fragment = new OptionFragment();
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
        return inflater.inflate(R.layout.fragment_option, container, false);
    }

    ListView listView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        String symbol=bundle.getString(Constant.search);
        progress=view.findViewById(R.id.progress);
        listView=view.findViewById(R.id.list_option);

        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                FragmentTransaction ft = null;
                if (getFragmentManager() != null) {
                    ft = getFragmentManager().beginTransaction();
                    ft.detach(OptionFragment.this).attach(OptionFragment.this).commit();
                    pullToRefresh.setRefreshing(false);
                }
            }
        });



        OptionTask analysisTask=new OptionTask(OptionFragment.this);
        StaticMethods.executeAsyncTask(analysisTask,new String[]{symbol.replace(".NS","").replace(".BO","")});


    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);

        Document document= Jsoup.parse(data);
        String ddd="";
        Element elements=document.getElementById("octable");
        Elements elements1=elements.getElementsByTag("tr");
        List<OptionChainModel> optionChainModels=new ArrayList<>();
        for (int i=0;i<elements1.size();i++){
            if(i==0 || i==1){
                continue;
            }

            if(i==22){
                for (int k=0;k<elements1.get(i).children().size();k++){
                    Log.d("Pos "," "+k+" => "+elements1.get(i).children().get(k).text());
                }
            }

            try {
                String strike_price="";
                if(elements1.get(i).children().size()>10) {
                    strike_price = elements1.get(i).children().get(11).children().text();
                }
                String oi_1= elements1.get(i).children().get(1).text();
                if(oi_1.isEmpty()){
                    oi_1="-";
                }
                String chng_in_o1=elements1.get(i).children().get(2).text();
                if(chng_in_o1.isEmpty()){
                    chng_in_o1="-";
                }

                String volume= elements1.get(i).children().get(3).text();
                if(volume.isEmpty()){
                    volume="-";
                }

                String iv_1= elements1.get(i).children().get(4).text();
                if(iv_1.isEmpty()){
                    iv_1="-";
                }

                String  ltp_1= elements1.get(i).children().get(5).text();
                if(ltp_1.isEmpty()){
                    ltp_1="-";
                }


                String  net_chng=elements1.get(i).children().get(6).text();
                if(net_chng.isEmpty()){
                    net_chng="-";
                }
                String oi_2="-";
                if(elements1.get(i).children().size()>20) {
                    oi_2 = elements1.get(i).children().get(21).text();
                }
                else{
                    oi_2="-";
                }
                if(oi_2.isEmpty()){
                    oi_2="-";
                }

                String chng_in_02="-";
                if(elements1.get(i).children().size()>19) {
                    chng_in_02 = elements1.get(i).children().get(20).text();
                }
                else{
                    chng_in_02="-";
                }
                if(chng_in_02.isEmpty()){
                    chng_in_02="-";
                }

                String volume_2="";

                if(elements1.get(i).children().size()>18) {
                    volume_2= elements1.get(i).children().get(19).text();

                }
                else{
                    volume_2= "-";
                }

                if(volume_2.isEmpty()){
                    volume_2="-";
                }


                String iv_2="";
                if(elements1.get(i).children().size()>17) {
                    iv_2= elements1.get(i).children().get(18).text();

                }
                else{
                     iv_2= "-";
                }

                if(iv_2.isEmpty()){
                    iv_2="-";
                }


                String ltp_2="";
                if(elements1.get(i).children().size()>16) {
                    ltp_2=elements1.get(i).children().get(17).text();
                }
                else{
                    ltp_2="-";
                }
                if(ltp_2.isEmpty()){
                    ltp_2="-";
                }


                String net_chng_2="";
                if(elements1.get(i).children().size()>15){
                    net_chng_2= elements1.get(i).children().get(16).text();

                }
                else{
                    net_chng_2="";
                }
                if(net_chng_2.isEmpty()){
                    net_chng_2="-";
                }

                optionChainModels.add(new OptionChainModel(strike_price,oi_1, chng_in_o1,volume,iv_1,ltp_1,net_chng ,oi_2 , chng_in_02,volume_2,iv_2, ltp_2,net_chng_2));
            }
            catch (Exception e){
                e.printStackTrace();
                optionChainModels.add(new OptionChainModel());
            }
        }

        if(mContext!=null) {
            OptionChainAdapter optionChainAdapter = new OptionChainAdapter(mContext, optionChainModels);
            listView.setAdapter(optionChainAdapter);
        }
        else{
            if(getContext()!=null) {
                OptionChainAdapter optionChainAdapter = new OptionChainAdapter(getContext(), optionChainModels);
                listView.setAdapter(optionChainAdapter);
            }
        }


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

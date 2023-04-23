package com.silverlinesoftwares.intratips.fragments.stockdetails;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OptionFragment extends Fragment implements ChartListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context mContext;
    private final String all_data="";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext=null;
    }

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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
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
        String symbol= "";
        if (bundle != null) {
            symbol = bundle.getString(Constant.search);
        }
        progress=view.findViewById(R.id.progress);
        listView=view.findViewById(R.id.list_option);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            try {
                FragmentTransaction ft = null;
                ft = getParentFragmentManager().beginTransaction();
                ft.detach(OptionFragment.this).attach(OptionFragment.this).commit();
                pullToRefresh.setRefreshing(false);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });



        OptionTask analysisTask=new OptionTask(OptionFragment.this);
        analysisTask.execute(symbol.replace(".NS","").replace(".BO",""));


    }

    @Override
    public void onSucess(String data) {
        progress.setVisibility(View.GONE);

        if(data!=null) {


            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray=jsonObject.getJSONObject("records").getJSONArray("data");

                List<OptionChainModel> optionChainModels=new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++) {
                    OptionChainModel chainModel=new OptionChainModel();
                    if(jsonArray.getJSONObject(i).has("CE")){
                        chainModel.setStrike_price(jsonArray.getJSONObject(i).getJSONObject("CE").getString("strikePrice"));
                        chainModel.setOi_1(jsonArray.getJSONObject(i).getJSONObject("CE").getString("openInterest"));
                        chainModel.setChng_in_oi_1(jsonArray.getJSONObject(i).getJSONObject("CE").getString("changeinOpenInterest"));
                        chainModel.setVolume_1(jsonArray.getJSONObject(i).getJSONObject("CE").getString("totalTradedVolume"));
                        chainModel.setIv_1(jsonArray.getJSONObject(i).getJSONObject("CE").getString("impliedVolatility"));
                        chainModel.setLtp_1(jsonArray.getJSONObject(i).getJSONObject("CE").getString("lastPrice"));
                        chainModel.setNet_chng_1(jsonArray.getJSONObject(i).getJSONObject("CE").getString("change"));

                    }

                    if(jsonArray.getJSONObject(i).has("PE")){
                        chainModel.setStrike_price(jsonArray.getJSONObject(i).getJSONObject("PE").getString("strikePrice"));
                        chainModel.setOi_2(jsonArray.getJSONObject(i).getJSONObject("PE").getString("openInterest"));
                        chainModel.setChng_in_oi_2(jsonArray.getJSONObject(i).getJSONObject("PE").getString("changeinOpenInterest"));
                        chainModel.setVolume_2(jsonArray.getJSONObject(i).getJSONObject("PE").getString("totalTradedVolume"));
                        chainModel.setIv_2(jsonArray.getJSONObject(i).getJSONObject("PE").getString("impliedVolatility"));
                        chainModel.setLtp_2(jsonArray.getJSONObject(i).getJSONObject("PE").getString("lastPrice"));
                        chainModel.setNet_chng_2(jsonArray.getJSONObject(i).getJSONObject("PE").getString("change"));
                    }


                    optionChainModels.add(chainModel);
                }

                if (mContext != null) {
                    OptionChainAdapter optionChainAdapter = new OptionChainAdapter(mContext, optionChainModels);
                    listView.setAdapter(optionChainAdapter);
                } else {
                    if (getContext() != null) {
                        OptionChainAdapter optionChainAdapter = new OptionChainAdapter(getContext(), optionChainModels);
                        listView.setAdapter(optionChainAdapter);
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
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

package com.silverlinesoftwares.intratips.fragments.subfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.StrongBuySellAdapter;
import com.silverlinesoftwares.intratips.models.StrongBuySellModel;
import com.silverlinesoftwares.intratips.util.NetworkRequestListener;
import com.silverlinesoftwares.intratips.util.NetworkRequestLoaderRaw;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StrongBuyFragment extends Fragment implements NetworkRequestListener {


    // TODO: Rename and change types of parameters
    ProgressBar progressBar;
    private ListView strongSell;

    public StrongBuyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_strong_buysell, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strongSell = view.findViewById(R.id.list_looser);
        progressBar=view.findViewById(R.id.progress);

        Map<String ,String> map=new HashMap<>();
        map.put("Content-Type","text/plain");
        NetworkRequestLoaderRaw.LoadData(getContext(),"https://scanner.tradingview.com/india/scan",map , StrongBuyFragment.this, Request.Method.POST, StaticMethods.getStrongBuy(),"");
    }

    @Override
    public void onStartLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCompletedLoading(String data, String from) {
        progressBar.setVisibility(View.GONE);
        List<StrongBuySellModel> strongBuySellModels=StaticMethods.getBuySellModel(data,false);
        if(strongBuySellModels!=null) {
            StrongBuySellAdapter gainerLooserAdapter = new StrongBuySellAdapter(getContext(), strongBuySellModels);
            strongSell.setAdapter(gainerLooserAdapter);
        }
        else {
            Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onErrorLoading(String message) {
        progressBar.setVisibility(View.GONE);
    }
}

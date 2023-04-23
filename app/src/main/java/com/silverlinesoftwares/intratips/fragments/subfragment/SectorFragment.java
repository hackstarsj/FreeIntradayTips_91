package com.silverlinesoftwares.intratips.fragments.subfragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.HeatMapAdapter;
import com.silverlinesoftwares.intratips.models.SectorStockModel;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class SectorFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String DATA = "data_list";

    // TODO: Rename and change types of parameters
    List<SectorStockModel> gainerLosserModels=new ArrayList<>();
    private String mParam3;


    public SectorFragment() {
        // Required empty public constructor
    }

    public static SectorFragment newInstance(String param1, String param2) {
        SectorFragment fragment = new SectorFragment();
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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
          //  mParam3=getArguments().getString(DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stockheatmep, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView topMovers = view.findViewById(R.id.list_looser);


        if(getArguments()!=null) {
            this.gainerLosserModels = (List<SectorStockModel>) getArguments().getSerializable(Constant.data);
            HeatMapAdapter gainerLooserAdapter = new HeatMapAdapter(getContext(), gainerLosserModels);
            topMovers.setAdapter(gainerLooserAdapter);
        }

    }
}

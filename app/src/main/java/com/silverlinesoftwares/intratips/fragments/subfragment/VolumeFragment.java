package com.silverlinesoftwares.intratips.fragments.subfragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.ActiveStockAdapter;
import com.silverlinesoftwares.intratips.models.ActiveStockModel;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class VolumeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    List<ActiveStockModel> gainerLosserModels=new ArrayList<>();
    //   private String mParam3;


    public VolumeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
           // mParam3=getArguments().getString(DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_looser, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView topMovers = view.findViewById(R.id.list_looser);

        if(getArguments()!=null) {

            this.gainerLosserModels = (List<ActiveStockModel>) getArguments().getSerializable(Constant.data);
            ActiveStockAdapter gainerLooserAdapter = new ActiveStockAdapter(getContext(), gainerLosserModels);
            topMovers.setAdapter(gainerLooserAdapter);
        }

    }
}

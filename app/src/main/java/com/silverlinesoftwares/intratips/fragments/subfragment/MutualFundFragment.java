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
import com.silverlinesoftwares.intratips.adapters.MutualFundAdapter;
import com.silverlinesoftwares.intratips.models.MutualFundModel;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class MutualFundFragment extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView mutualList;
    List<MutualFundModel> gainerLosserModels=new ArrayList<>();





    public MutualFundFragment() {
        // Required empty public constructor
    }

    public static TopMoversFragment newInstance(String param1, String param2) {
        TopMoversFragment fragment = new TopMoversFragment();
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
        return inflater.inflate(R.layout.fragment_mutual_fund, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mutualList=view.findViewById(R.id.list_mutual);


        if(getArguments()!=null) {
            if (getArguments().getSerializable(Constant.data) != null) {
                this.gainerLosserModels = (List<MutualFundModel>) getArguments().getSerializable(Constant.data);
                MutualFundAdapter gainerLooserAdapter = new MutualFundAdapter(getContext(), gainerLosserModels);
                mutualList.setAdapter(gainerLooserAdapter);
            }
        }
    }

}

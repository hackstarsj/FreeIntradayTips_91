package com.silverlinesoftwares.intratips.fragments.subfragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.StockDetailsActivity;
import com.silverlinesoftwares.intratips.adapters.GainerLooserAdapter;
import com.silverlinesoftwares.intratips.models.GainerLosserModel;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TopLoserFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String DATA = "data_list";

    // TODO: Rename and change types of parameters
    private List<GainerLosserModel> gainerLosserModels=new ArrayList<>();


    public TopLoserFragment() {
        // Required empty public constructor
    }

    public static TopLoserFragment newInstance(String param1, String param2) {
        TopLoserFragment fragment = new TopLoserFragment();
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
            String mParam3 = getArguments().getString(DATA);
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
            this.gainerLosserModels = (List<GainerLosserModel>) getArguments().getSerializable(Constant.data);
            Collections.reverse(this.gainerLosserModels);
            GainerLooserAdapter gainerLooserAdapter = new GainerLooserAdapter(getContext(), gainerLosserModels);
            topMovers.setAdapter(gainerLooserAdapter);

            topMovers.setOnItemClickListener((parent, view1, position, id) -> startActivity(new Intent(getContext(), StockDetailsActivity.class).putExtra(Constant.search, gainerLosserModels.get(position).getSymbol() + ".NS")));
        }

    }
}

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
import com.silverlinesoftwares.intratips.adapters.OiSpurtsUnderlyAdapter;
import com.silverlinesoftwares.intratips.models.OiSpurtsUnderlyingModel;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class OiUnderlyingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String DATA = "data_list";

    // TODO: Rename and change types of parameters
    List<OiSpurtsUnderlyingModel> gainerLosserModels=new ArrayList<>();
    private String mParam1;
    private String mParam2;
  //  private String mParam3;


    public OiUnderlyingFragment() {
        // Required empty public constructor
    }

    public static OiUnderlyingFragment newInstance(String param1, String param2) {
        OiUnderlyingFragment fragment = new OiUnderlyingFragment();
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
            //mParam3=getArguments().getString(DATA);
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
        String date_1=getArguments().getString(Constant.date_1);
        String date_2=getArguments().getString(Constant.date_2);


        this.gainerLosserModels= (List<OiSpurtsUnderlyingModel>) getArguments().getSerializable(Constant.data);
        OiSpurtsUnderlyAdapter gainerLooserAdapter=new OiSpurtsUnderlyAdapter(getContext(),gainerLosserModels,date_1,date_2);
        topMovers.setAdapter(gainerLooserAdapter);

    }
}

package com.silverlinesoftwares.intratips.fragments.stockdetails;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.MajorHolderAdapterR;
import com.silverlinesoftwares.intratips.listeners.ManagementListener;
import com.silverlinesoftwares.intratips.models.MajorHolderModel;
import com.silverlinesoftwares.intratips.tasks.MangementTask;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.List;


public class ManagementFragment extends Fragment implements ManagementListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView list_management;
    ProgressBar progressBar;

    public ManagementFragment() {
        // Required empty public constructor
    }

    public static ManagementFragment newInstance(String param1, String param2) {
        ManagementFragment fragment = new ManagementFragment();
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
        return inflater.inflate(R.layout.fragment_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        String symbol=bundle.getString(Constant.search);

        list_management=view.findViewById(R.id.list_management);
        progressBar=view.findViewById(R.id.progress);
        list_management.setLayoutManager(new LinearLayoutManager(getContext()));
        MangementTask mangementTask=new MangementTask(ManagementFragment.this);
       // StaticMethods.executeAsyncTask(mangementTask);
        mangementTask.execute(new String[]{symbol});
    }


    @Override
    public void onSucess(List<MajorHolderModel> data) {
        progressBar.setVisibility(View.GONE);
        MajorHolderAdapterR majorHolderAdapterR=new MajorHolderAdapterR(getContext(),list_management,data);
        list_management.setAdapter(majorHolderAdapterR);
    }

    @Override
    public void onFailed(String msg) {
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
        }
        if(getContext()!=null) {
            Toast.makeText(getContext(), "" + msg, Toast.LENGTH_SHORT).show();
        }
    }
}


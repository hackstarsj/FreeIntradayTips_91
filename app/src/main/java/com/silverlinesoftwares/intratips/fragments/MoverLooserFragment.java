package com.silverlinesoftwares.intratips.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.LooserGainerActivity;
import com.silverlinesoftwares.intratips.adapters.MoverMenuAdapter;
import com.silverlinesoftwares.intratips.models.MoverLooserMenu;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.List;


public class MoverLooserFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MoverLooserFragment() {
        // Required empty public constructor
    }

    public static MoverLooserFragment newInstance(String param1, String param2) {
        MoverLooserFragment fragment = new MoverLooserFragment();
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
        return inflater.inflate(R.layout.fragment_moverlooser, container, false);
    }

    List<MoverLooserMenu> moverLooserMenus;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView griditems=view.findViewById(R.id.griditems);
        moverLooserMenus=MoverLooserMenu.getItemMenu();
        MoverMenuAdapter moverMenuAdapter=new MoverMenuAdapter(getContext(),moverLooserMenus);
        griditems.setAdapter(moverMenuAdapter);
        griditems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(), LooserGainerActivity.class).putExtra(Constant.data_title,moverLooserMenus.get(position).getName()).putExtra(Constant.data_text,moverLooserMenus.get(position).getData_text()));

           }
        });

    }
}

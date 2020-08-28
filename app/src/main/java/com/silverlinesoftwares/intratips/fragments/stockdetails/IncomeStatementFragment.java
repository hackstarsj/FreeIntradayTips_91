package com.silverlinesoftwares.intratips.fragments.stockdetails;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.IncomeStatementAdapterR;
import com.silverlinesoftwares.intratips.listeners.IncomeStateListener;
import com.silverlinesoftwares.intratips.models.IncomeStatementModel;
import com.silverlinesoftwares.intratips.tasks.IncomeStatementTask;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.List;

import static androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT;


public class IncomeStatementFragment extends Fragment implements IncomeStateListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView list_income_statement_quaterly;
    private RecyclerView list_income_statement_yearly;
    ProgressBar progress;


    public IncomeStatementFragment() {
        // Required empty public constructor
    }

    public static IncomeStatementFragment newInstance(String param1, String param2) {
        IncomeStatementFragment fragment = new IncomeStatementFragment();
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
        return inflater.inflate(R.layout.fragment_income_statement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        String symbol=bundle.getString(Constant.search);
        final CardView yearly_card=view.findViewById(R.id.yearly_card);
        progress=view.findViewById(R.id.progress);
        final CardView quaterly_card=view.findViewById(R.id.quaterly_card);

        list_income_statement_quaterly=view.findViewById(R.id.list_income_statement_quaterly);
        list_income_statement_quaterly.setLayoutManager(new LinearLayoutManager(getContext()));
        list_income_statement_yearly=view.findViewById(R.id.list_income_statement_yearly);
        list_income_statement_yearly.setLayoutManager(new LinearLayoutManager(getContext()));

        yearly_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quaterly_card.setBackgroundColor(Color.parseColor("#000000"));
                yearly_card.setBackgroundColor(Color.parseColor("#80d8ff"));
                list_income_statement_yearly.setVisibility(View.VISIBLE);
                list_income_statement_quaterly.setVisibility(View.GONE);
            }
        });

        quaterly_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearly_card.setBackgroundColor(Color.parseColor("#000000"));
                quaterly_card.setBackgroundColor(Color.parseColor("#80d8ff"));
                list_income_statement_yearly.setVisibility(View.GONE);
                list_income_statement_quaterly.setVisibility(View.VISIBLE);
            }
        });

        IncomeStatementTask financialTask=new IncomeStatementTask(IncomeStatementFragment.this);
        financialTask.execute(new String[]{symbol});

    }


    @Override
    public void onSucess(List<IncomeStatementModel> data,List<IncomeStatementModel> incomeStatementModels) {
        progress.setVisibility(View.GONE);
        IncomeStatementAdapterR incomeStatementAdapterR=new IncomeStatementAdapterR(getContext(),list_income_statement_quaterly,data);
        list_income_statement_quaterly.setAdapter(incomeStatementAdapterR);

        IncomeStatementAdapterR incomeStatementAdapterR1=new IncomeStatementAdapterR(getContext(),list_income_statement_yearly,incomeStatementModels);
        list_income_statement_yearly.setAdapter(incomeStatementAdapterR1);


    }

    @Override
    public void onFailed(String msg) {
        if(progress!=null) {
            progress.setVisibility(View.GONE);
        }

        if(getContext()!=null) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }

    }
}

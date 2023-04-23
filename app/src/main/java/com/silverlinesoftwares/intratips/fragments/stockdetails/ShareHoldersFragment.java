package com.silverlinesoftwares.intratips.fragments.stockdetails;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.InsiderRosterAdapterR;
import com.silverlinesoftwares.intratips.adapters.InsiderTransactionAdapterR;
import com.silverlinesoftwares.intratips.adapters.MajorHolderAdapterR;
import com.silverlinesoftwares.intratips.listeners.ShareHolderListener;
import com.silverlinesoftwares.intratips.models.InsiderRosterModel;
import com.silverlinesoftwares.intratips.models.InsiderTransactionModel;
import com.silverlinesoftwares.intratips.models.MajorHolderModel;
import com.silverlinesoftwares.intratips.tasks.ShareHolderTask;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.List;


public class ShareHoldersFragment extends Fragment implements ShareHolderListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView list_major_holder;
    private RecyclerView list_insider_roster;
    private RecyclerView list_insider_transaction;
    ProgressBar progressBar;

    private Context mContext;

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


    public ShareHoldersFragment() {
        // Required empty public constructor
    }

    public static ShareHoldersFragment newInstance(String param1, String param2) {
        ShareHoldersFragment fragment = new ShareHoldersFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shareholder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        String symbol= "";
        if (bundle != null) {
            symbol = bundle.getString(Constant.search);
        }

        progressBar=view.findViewById(R.id.progress);
        list_major_holder=view.findViewById(R.id.list_major_holders);
        list_insider_roster=view.findViewById(R.id.list_insider_roster);
        list_insider_transaction=view.findViewById(R.id.list_insider_transaction);

        final CardView major_card=view.findViewById(R.id.major_holders_card);
        final CardView insider_roster_card=view.findViewById(R.id.insider_roster_card);
        final CardView insider_transaction_card=view.findViewById(R.id.insider_transactions_card);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            try {
                FragmentTransaction ft = null;
                ft = getParentFragmentManager().beginTransaction();
                ft.detach(ShareHoldersFragment.this).attach(ShareHoldersFragment.this).commit();
                pullToRefresh.setRefreshing(false);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        major_card.setOnClickListener(v -> {
            insider_roster_card.setBackgroundColor(Color.parseColor("#000000"));
            insider_transaction_card.setBackgroundColor(Color.parseColor("#000000"));
            major_card.setBackgroundColor(Color.parseColor("#80d8ff"));
            showScreen(list_major_holder);
        });

        insider_roster_card.setOnClickListener(v -> {
            major_card.setBackgroundColor(Color.parseColor("#000000"));
            insider_transaction_card.setBackgroundColor(Color.parseColor("#000000"));
            insider_roster_card.setBackgroundColor(Color.parseColor("#80d8ff"));
            showScreen(list_insider_roster);
        });

        insider_transaction_card.setOnClickListener(v -> {
            major_card.setBackgroundColor(Color.parseColor("#000000"));
            insider_roster_card.setBackgroundColor(Color.parseColor("#000000"));
            insider_transaction_card.setBackgroundColor(Color.parseColor("#80d8ff"));
            showScreen(list_insider_transaction);
        });



        list_major_holder.setLayoutManager(new LinearLayoutManager(getContext()));
        list_insider_roster.setLayoutManager(new LinearLayoutManager(getContext()));
        list_insider_transaction.setLayoutManager(new LinearLayoutManager(getContext()));


        ShareHolderTask shareHolderTask=new ShareHolderTask(ShareHoldersFragment.this);
        shareHolderTask.execute(symbol);

    }

    private void showScreen(RecyclerView list_item) {
        list_major_holder.setVisibility(View.GONE);
        list_insider_roster.setVisibility(View.GONE);
        list_insider_transaction.setVisibility(View.GONE);
        list_item.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMajorLoaded(List<MajorHolderModel> data) {
        progressBar.setVisibility(View.GONE);
        MajorHolderAdapterR incomeStatementAdapterR=new MajorHolderAdapterR(getContext(),list_insider_transaction,data);
        list_major_holder.setAdapter(incomeStatementAdapterR);

    }

    @Override
    public void onInsiderLoader(List<InsiderRosterModel> insiderRosterModels) {

        progressBar.setVisibility(View.GONE);
        InsiderRosterAdapterR incomeStatementAdapterR1=new InsiderRosterAdapterR(getContext(),list_insider_roster,insiderRosterModels);
        list_insider_roster.setAdapter(incomeStatementAdapterR1);

    }

    @Override
    public void onInsiderTransaction(List<InsiderTransactionModel> list) {
        progressBar.setVisibility(View.GONE);
        InsiderTransactionAdapterR incomeStatementAdapterR1=new InsiderTransactionAdapterR(list);
        list_insider_transaction.setAdapter(incomeStatementAdapterR1);

    }

    @Override
    public void onFailed(String msg) {
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
        }
        if(mContext!=null) {
            Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
        }

    }
}

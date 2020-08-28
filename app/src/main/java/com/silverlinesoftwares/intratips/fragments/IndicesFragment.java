package com.silverlinesoftwares.intratips.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.CurrencyAdapter;
import com.silverlinesoftwares.intratips.listeners.ChartListener;
import com.silverlinesoftwares.intratips.models.CurrencyModel;
import com.silverlinesoftwares.intratips.tasks.IndicesTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class IndicesFragment extends Fragment implements ChartListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context mContext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar progressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext=null;
    }

    public IndicesFragment() {
        // Required empty public constructor
    }

    public static IndicesFragment newInstance(String param1, String param2) {
        IndicesFragment fragment = new IndicesFragment();
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
        return inflater.inflate(R.layout.fragment_indices, container, false);
    }

    ListView listView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar=view.findViewById(R.id.progress);
        listView=view.findViewById(R.id.list_volume_gainer);
        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                FragmentTransaction ft = null;
                if (getFragmentManager() != null) {
                    ft = getFragmentManager().beginTransaction();
                    ft.detach(IndicesFragment.this).attach(IndicesFragment.this).commit();
                    pullToRefresh.setRefreshing(false);
                }
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    pullToRefresh.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });

        IndicesTask indicesTask=new IndicesTask(IndicesFragment.this);
        indicesTask.execute();


    }

    @Override
    public void onSucess(String data) {
        progressBar.setVisibility(View.GONE);
        Document document= Jsoup.parse(data);
        Elements elements=document.getElementsByTag("tr");
        List<CurrencyModel> bulkModels=new ArrayList<>();
        for (int i=1;i<elements.size();i++){
            try {
                bulkModels.add(new CurrencyModel(elements.get(i).children().get(1).text(), elements.get(i).children().get(2).text(), elements.get(i).children().get(4).text(), elements.get(i).children().get(3).text()));
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        if(mContext != null) {
            CurrencyAdapter bulkDealAdapter = new CurrencyAdapter(mContext, bulkModels);
            listView.setAdapter(bulkDealAdapter);
        }
    }

    @Override
    public void onFailed(String msg) {
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
        }

        if(getContext()!=null) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

}

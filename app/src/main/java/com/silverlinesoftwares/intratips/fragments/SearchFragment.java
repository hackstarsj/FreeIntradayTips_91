package com.silverlinesoftwares.intratips.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.StockDetailsActivity;
import com.silverlinesoftwares.intratips.adapters.LocationAdapter;
import com.silverlinesoftwares.intratips.adapters.SearchAdapter;
import com.silverlinesoftwares.intratips.database.DataBaseController;
import com.silverlinesoftwares.intratips.models.SearchModel;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.ArrayList;
import java.util.List;



public class SearchFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView=view.findViewById(R.id.search_stocks);
        List<SearchModel> searchModels=new ArrayList<>();
        if(getContext()!=null){
         searchModels= DataBaseController.getBooks(getContext());
        }

        SearchAdapter locationAdapter=new SearchAdapter(getContext(),R.layout.searchrow,searchModels);
        listView.setAdapter(locationAdapter);
        final AutoCompleteTextView searchText = view.findViewById(R.id.search);
        searchText.setAdapter(new LocationAdapter(getContext(), R.layout.searchrow));
        searchText.setOnItemClickListener((parent, view12, position, id) -> {
            Log.d("Pos" ,"pos" +position);
            final SearchModel searchModel=new SearchModel();
            //searchModels.get(position);
            TextView title= view12.findViewById(R.id.txt_name);
            final TextView symbol= view12.findViewById(R.id.txt_symbol);
            TextView exchange= view12.findViewById(R.id.txt_exchange);

            searchModel.setExch(exchange.getText().toString());
            searchModel.setName(title.getText().toString());
            searchModel.setSymbol(symbol.getText().toString());
            if(getContext()!=null) {
                DataBaseController.saveSearch(getContext(), searchModel);
            }
            startActivity(new Intent(getContext(), StockDetailsActivity.class).putExtra(Constant.search,symbol.getText().toString()));

        });

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            try {
                FragmentTransaction ft = null;
                ft = getParentFragmentManager().beginTransaction();
                ft.detach(SearchFragment.this).attach(SearchFragment.this).commit();
                pullToRefresh.setRefreshing(false);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            TextView title= view1.findViewById(R.id.txt_name);
            final TextView symbol= view1.findViewById(R.id.txt_symbol);
            TextView exchange= view1.findViewById(R.id.txt_exchange);

            startActivity(new Intent(getContext(), StockDetailsActivity.class).putExtra(Constant.search,symbol.getText().toString()));
        });


    }
}

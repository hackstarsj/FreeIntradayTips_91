package com.silverlinesoftwares.intratips.fragments;

import android.content.Intent;
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

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.NewWebActivity;
import com.silverlinesoftwares.intratips.adapters.NewsAdapterR;
import com.silverlinesoftwares.intratips.listeners.NewsListener;
import com.silverlinesoftwares.intratips.models.NewsModel;
import com.silverlinesoftwares.intratips.tasks.NewsTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class NewsFragment extends Fragment implements NewsListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<NewsModel> newsModels;
    private NewsAdapterR newsAdapter;
    private TimerTask timerTask;
    private Timer timer;


    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    ProgressBar progress;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView listView = view.findViewById(R.id.list_news);
        progress=view.findViewById(R.id.progress);
        newsModels=new ArrayList<>();
        newsAdapter=new NewsAdapterR(getContext(), listView,newsModels);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setHasFixedSize(true);

        listView.setAdapter(newsAdapter);
        NewsTask newsTask=new NewsTask(NewsFragment.this,null,0);
        newsTask.execute("ok");

        newsAdapter.setOnItemClickListener((v, obj, position) -> {
            Intent intent=new Intent(getContext(), NewWebActivity.class);
            intent.putExtra("url",newsModels.get(position).getLinks());
            startActivity(intent);

        });

    }


    @Override
    public void onSucess(List<NewsModel> data) {
        progress.setVisibility(View.GONE);
        newsModels.addAll(data);
        newsAdapter.notifyItemInserted(data.size());

    }



    @Override
    public void onFailedNews(String msg) {
        progress.setVisibility(View.GONE);

    }
}

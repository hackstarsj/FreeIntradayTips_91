package com.silverlinesoftwares.intratips.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.ScreenAdapter;
import com.silverlinesoftwares.intratips.adapters.VideoAdapter;
import com.silverlinesoftwares.intratips.models.AdavanceScreenDataModel;
import com.silverlinesoftwares.intratips.models.ScreenerItem;
import com.silverlinesoftwares.intratips.models.VideoMoel;
import com.silverlinesoftwares.intratips.util.MyAsyncListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ScreenerFragment extends Fragment implements MyAsyncListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    public static ScreenerFragment newInstance(String param1, String param2) {
        ScreenerFragment fragment = new ScreenerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advance_screener, container, false);
    }

    ProgressBar progress;
    RecyclerView recyclerView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView=view.findViewById(R.id.item_screener);
        List<ScreenerItem> screenerItems= AdavanceScreenDataModel.getAdavanceScreenDataModel();
        ScreenAdapter screenAdapter=new ScreenAdapter(getContext(),screenerItems);
        listView.setAdapter(screenAdapter);

//        recyclerView=view.findViewById(R.id.video_list);
//
        progress=view.findViewById(R.id.progress);
        progress.setVisibility(View.GONE);


    }


    @Override
    public void onSuccessfulExecute(String data) {
        progress.setVisibility(View.GONE);
        if(data!=null){
            try {

                JSONArray jsonArray=new JSONArray(data);
                List<VideoMoel> cartoonModels=new ArrayList<>();
                for (int i=0;i<jsonArray.length();i++){
                    cartoonModels.add(new VideoMoel(jsonArray.getJSONObject(i).getString("title"),jsonArray.getJSONObject(i).getString("video_id")));
                }
                VideoAdapter cartoonAdapter=new VideoAdapter(getContext(),cartoonModels);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(cartoonAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
        }

    }
}

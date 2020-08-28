package com.silverlinesoftwares.intratips.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.ScreenAdapter;
import com.silverlinesoftwares.intratips.adapters.VideoAdapter;
import com.silverlinesoftwares.intratips.models.AdavanceScreenDataModel;
import com.silverlinesoftwares.intratips.models.ScreenerItem;
import com.silverlinesoftwares.intratips.models.VideoMoel;
import com.silverlinesoftwares.intratips.util.MyAsyncListener;
import com.silverlinesoftwares.intratips.util.RecyclerTouchListener;
import com.silverlinesoftwares.intratips.util.VideoActivity;
import com.silverlinesoftwares.intratips.util.VideoDataLoader;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends Fragment implements MyAsyncListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
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
        //List<ScreenerItem> screenerItems= AdavanceScreenDataModel.getAdavanceScreenDataModel();
        //ScreenAdapter screenAdapter=new ScreenAdapter(getContext(),screenerItems);
        //listView.setAdapter(screenAdapter);

        recyclerView=view.findViewById(R.id.video_list);

        progress=view.findViewById(R.id.progress);
        VideoDataLoader videoDataLoader=new VideoDataLoader(VideoFragment.this);
        videoDataLoader.execute();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TextView tt=view.findViewById(R.id.v_id);
                if(tt!=null){
                    startActivity(new Intent(getContext(), VideoActivity.class).putExtra("video_id",tt.getText().toString()));
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



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
                if(getContext()!=null) {
                    Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            if(getContext()!=null) {
                Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

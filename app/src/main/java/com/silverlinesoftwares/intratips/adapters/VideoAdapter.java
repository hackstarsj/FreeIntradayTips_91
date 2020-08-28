package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.VideoMoel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;



public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>
{
    private Context mContext;
    private List<VideoMoel> itemList;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title,video_id;
        ImageView thumb;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            video_id = (TextView) view.findViewById(R.id.v_id);
            thumb = (ImageView) view.findViewById(R.id.thumb);
        }
    }

    public VideoAdapter(Context mContext, List<VideoMoel> itemList)
    {
        this.mContext = mContext;
        this.itemList= itemList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=null;

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row, parent, false);
             return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        VideoMoel blogPost = itemList.get(position);
        holder.title.setText(blogPost.getTitle());

        holder.video_id.setText(blogPost.getVideo_id());

        Picasso.with(mContext)
                .load("https://img.youtube.com/vi/"+blogPost.getVideo_id()+"/sddefault.jpg")
                .placeholder(R.color.grey_10)
                .into(holder.thumb);





    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
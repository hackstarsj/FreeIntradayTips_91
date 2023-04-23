package com.silverlinesoftwares.intratips.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapterR extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 2;

    private List<NewsModel> items = new ArrayList<>();

    private boolean loading;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, NewsModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapterR(Context context, RecyclerView view, List<NewsModel> items) {
        this.items = items;
    }



    public static class HeadingViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final TextView title;
        public final TextView date;
        public final TextView category;
        public final TextView comment;
        public final ImageView image;
        public final ImageView thumbnail_video;
        public final RelativeLayout lyt_parent;

        public HeadingViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            date = v.findViewById(R.id.date);
            category = v.findViewById(R.id.category_name);
            comment = v.findViewById(R.id.comment);
            image = v.findViewById(R.id.image);
            thumbnail_video = v.findViewById(R.id.thumbnail_video);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

    public static class OriginalViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final TextView title;
        public final TextView date;
        public final TextView category;
        public TextView comment;
        public final ImageView image;
        public final LinearLayout line_parent;
        public final ImageView thumbnail_video;

        public OriginalViewHolder(View view) {
            super(view);
            line_parent = view.findViewById(R.id.lyt_parent);

            title= view.findViewById(R.id.title);
            category= view.findViewById(R.id.category_name);
            image= view.findViewById(R.id.image);
            date= view.findViewById(R.id.date);
            thumbnail_video= view.findViewById(R.id.thumbnail_video);
        }
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_recent, parent, false);
            vh = new OriginalViewHolder(v);
        } else
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_heading, parent, false);
            vh = new HeadingViewHolder(v);
        }
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (holder instanceof HeadingViewHolder) {
            final NewsModel p = items.get(position);
            HeadingViewHolder vItem = (HeadingViewHolder) holder;
            vItem.title.setText(Html.fromHtml(p.getTitle()));
            vItem.date.setText(p.getDates());


            vItem.category.setText(Html.fromHtml(p.getDescriptions()));


             vItem.thumbnail_video.setVisibility(View.GONE);


            if(p.getImages().contains("data:") ||  p.getImages().isEmpty()){
                byte[] decodedString = Base64.decode("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", Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                vItem.image.setImageBitmap(decodedByte);
                //
            }
            else {
                try {
                    byte[] decodedString = Base64.decode(p.getImages(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    vItem.image.setImageBitmap(decodedByte);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }


            vItem.lyt_parent.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, p, position);
                }
            });

        } else if (holder instanceof OriginalViewHolder) {
            final NewsModel p = items.get(position);
            OriginalViewHolder vItem = (OriginalViewHolder) holder;
            vItem.title.setText(Html.fromHtml(p.getTitle()));

            vItem.date.setText(p.getDates());

            vItem.category.setText(Html.fromHtml(p.getDescriptions()));

            vItem.thumbnail_video.setVisibility(View.GONE);

            if(p.getImages().contains("data:") || p.getImages().isEmpty()){
                byte[] decodedString = Base64.decode("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", Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                vItem.image.setImageBitmap(decodedByte);
            }
            else {

                try {
                    byte[] decodedString = Base64.decode(p.getImages(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    vItem.image.setImageBitmap(decodedByte);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            vItem.line_parent.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, p, position);
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        //return this.items.get(position) != null ? VIEW_ITEM : VIEW_PROG;
        if (items.get(position) != null) {
            if (position == 0) {
                return 1;
            } else {
                return VIEW_ITEM;
            }
        } else {
            return 0;
        }
    }

    public void insertData(List<NewsModel> items) {
        setLoaded();
        int positionStart = getItemCount();
        int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void setLoaded() {
        loading = false;
        for (int i = 0; i < getItemCount(); i++) {
            if (items.get(i) == null) {
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void setLoading() {
        if (getItemCount() != 0) {
            this.items.add(null);
            notifyItemInserted(getItemCount() - 1);
            loading = true;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void resetListData() {
        this.items = new ArrayList<>();
        notifyDataSetChanged();
    }


}
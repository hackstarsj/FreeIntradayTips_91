package com.silverlinesoftwares.intratips.adapters;

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

import androidx.recyclerview.widget.RecyclerView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapterR extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_PROG = 0;
    private final int VIEW_HEAD = 1;
    private final int VIEW_ITEM = 2;

    private List<NewsModel> items = new ArrayList<>();

    private boolean loading;

    private Context ctx;
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
        ctx = context;
    }



    public class HeadingViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView date;
        public TextView category;
        public TextView comment;
        public ImageView image;
        public ImageView thumbnail_video;
        public RelativeLayout lyt_parent;

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

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView date;
        public TextView category;
        public TextView comment;
        public ImageView image;
        public LinearLayout line_parent;
        public ImageView thumbnail_video;

        public OriginalViewHolder(View view) {
            super(view);
            line_parent = view.findViewById(R.id.lyt_parent);

            title=(TextView) view.findViewById(R.id.title);
            category=(TextView) view.findViewById(R.id.category_name);
            image=(ImageView) view.findViewById(R.id.image);
            date=(TextView) view.findViewById(R.id.date);
            thumbnail_video=(ImageView) view.findViewById(R.id.thumbnail_video);
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

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
//                if(p.getImages().isEmpty()){
//                    byte[] decodedString = Base64.decode("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\\x3d\\x3d", Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                    vItem.image.setImageBitmap(decodedByte);
//                }
//                else {
//                    try {
//                        String base = "data:image/jpeg;base64," + p.getImages();
//
//                        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
//
//
//                        vItem.image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
////                    byte[] decodedString = Base64.decode(p.getImages(), Base64.URL_SAFE);
////                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
////                    vItem.image.setImageBitmap(decodedByte);
//
//                }
                try {
                    byte[] decodedString = Base64.decode(p.getImages(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    vItem.image.setImageBitmap(decodedByte);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }


            vItem.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, p, position);
                    }
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
//                Picasso.with(ctx)
//                        .load(p.getImages())
//                        .placeholder(R.drawable.ic_thumbnail)
//                        .into(vItem.image);
            }

            vItem.line_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, p, position);
                    }
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
                return VIEW_HEAD;
            } else {
                return VIEW_ITEM;
            }
        } else {
            return VIEW_PROG;
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

    public void resetListData() {
        this.items = new ArrayList<>();
        notifyDataSetChanged();
    }


}
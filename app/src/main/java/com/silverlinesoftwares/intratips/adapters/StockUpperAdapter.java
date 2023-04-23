package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.StockUpperModel;

import java.util.List;


public class StockUpperAdapter extends RecyclerView.Adapter
{
    private final List<StockUpperModel> itemList;


    @Override
    public int getItemViewType(int position) {

        int VIEW_TYPE_LOADING = 1;
        int VIEW_TYPE_ITEM = 0;
        return itemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView symbol;
        public final TextView price;
        public final TextView change;
        public final TextView change_per;
        public final ImageView indicator;


        public MyViewHolder(View view) {
            super(view);
            symbol = view.findViewById(R.id.symbol);
            price = view.findViewById(R.id.ltp);
            change = view.findViewById(R.id.change);
            change_per = view.findViewById(R.id.percentage_text);
            indicator= view.findViewById(R.id.indicator);
        }
    }

    public StockUpperAdapter(RecyclerView recyclerView,Context mContext,List<StockUpperModel> itemList)
    {
        this.itemList= itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_marquee, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            StockUpperModel blogPost = itemList.get(position);
            final MyViewHolder holder1=(MyViewHolder)holder;
            holder1.symbol.setText(blogPost.getSymbol());
            holder1.price.setText(blogPost.getLtP());
            holder1.change_per.setText(String.format("%%%s", blogPost.getPer()));
            holder1.change.setText(blogPost.getPtsC());


        if(!blogPost.getPer().contains("-"))
        {
            holder1.indicator.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
            holder1.change.setTextColor(Color.parseColor("#4caf50"));
            holder1.change_per.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            holder1.indicator.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            holder1.change.setTextColor(Color.parseColor("#d50000"));
            holder1.change_per.setTextColor(Color.parseColor("#d50000"));
        }



    }


    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }



}
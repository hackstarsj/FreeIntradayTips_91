package com.silverlinesoftwares.intratips.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.IncomeStatementModel;
import com.silverlinesoftwares.intratips.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class IncomeStatementAdapterR extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<IncomeStatementModel> items = new ArrayList<>();

    private boolean loading;

    public interface OnItemClickListener {
        void onItemClick(View view, NewsModel obj, int position);
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public IncomeStatementAdapterR(Context context, RecyclerView view, List<IncomeStatementModel> items) {
        this.items = items;
    }




    public static class OriginalViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final TextView title;
        public final TextView data_1;
        public final TextView data_2;
        public final TextView data_3;
        public final TextView data_4;

        public OriginalViewHolder(View view) {
            super(view);

            title= view.findViewById(R.id.title);
            data_1= view.findViewById(R.id.data_1);
            data_2= view.findViewById(R.id.data_2);
            data_3= view.findViewById(R.id.data_3);
            data_4= view.findViewById(R.id.data_4);
         }
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_income_statement, parent, false);
            vh = new OriginalViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OriginalViewHolder) {
            final IncomeStatementModel p = items.get(position);
            OriginalViewHolder vItem = (OriginalViewHolder) holder;
            vItem.title.setText(Html.fromHtml(p.getTitle()));


            vItem.data_1.setText(p.getData_1());
            if(p.getData_1()!=null){
                if(p.getData_1().equalsIgnoreCase("0")){
                    vItem.data_1.setText("-");
                }
            }

            vItem.data_2.setText(p.getData_2());
            if(p.getData_2()!=null){
                if(p.getData_2().equalsIgnoreCase("0")){
                    vItem.data_2.setText("-");
                }
            }

            vItem.data_3.setText(p.getData_3());
            if(p.getData_3()!=null){
                if(p.getData_3().equalsIgnoreCase("0")){
                    vItem.data_3.setText("-");
                }
            }

            vItem.data_4.setText(p.getData_4());
            if(p.getData_4()!=null){
                if(p.getData_4().equalsIgnoreCase("0")){
                    vItem.data_4.setText("-");
                }
            }

            if(p.isIs_bold()){
                vItem.title.setTypeface(null, Typeface.BOLD);
                vItem.data_1.setTypeface(null,Typeface.BOLD);
                vItem.data_2.setTypeface(null,Typeface.BOLD);
                vItem.data_3.setTypeface(null,Typeface.BOLD);
                vItem.data_4.setTypeface(null,Typeface.BOLD);
            }
            else{
                vItem.title.setTypeface(null, Typeface.NORMAL);
                vItem.data_1.setTypeface(null,Typeface.NORMAL);
                vItem.data_2.setTypeface(null,Typeface.NORMAL);
                vItem.data_3.setTypeface(null,Typeface.NORMAL);
                vItem.data_4.setTypeface(null,Typeface.NORMAL);

            }

            if(p.isIs_head()){
                vItem.title.setTextSize(17f);
                vItem.title.setTypeface(null,Typeface.BOLD);
                vItem.data_1.setTypeface(null,Typeface.BOLD);
                vItem.data_2.setTypeface(null,Typeface.BOLD);
                vItem.data_3.setTypeface(null,Typeface.BOLD);
                vItem.data_4.setTypeface(null,Typeface.BOLD);

            }
            else{
                if(!p.isIs_bold()) {
                    vItem.title.setTextSize(15f);
                    vItem.title.setTypeface(null, Typeface.NORMAL);
                    vItem.data_1.setTypeface(null, Typeface.NORMAL);
                    vItem.data_2.setTypeface(null, Typeface.NORMAL);
                    vItem.data_3.setTypeface(null, Typeface.NORMAL);
                    vItem.data_4.setTypeface(null, Typeface.NORMAL);
                }
            }

            if(p.isIs_center()){
                vItem.data_1.setGravity(Gravity.CENTER);
                vItem.data_2.setGravity(Gravity.CENTER);
                vItem.data_3.setGravity(Gravity.CENTER);
                vItem.data_4.setGravity(Gravity.CENTER);
            }
            else{
                vItem.data_1.setGravity(Gravity.START);
                vItem.data_2.setGravity(Gravity.START);
                vItem.data_3.setGravity(Gravity.START);
                vItem.data_4.setGravity(Gravity.START);

            }

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
                return 2;
            }
        } else {
            return 0;
        }
    }

    public void insertData(List<IncomeStatementModel> items) {
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
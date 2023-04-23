package com.silverlinesoftwares.intratips.adapters;

import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.InsiderTransactionModel;
import com.silverlinesoftwares.intratips.models.NewsModel;

import java.util.List;

public class InsiderTransactionAdapterR extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private final List<InsiderTransactionModel> items;


    public interface OnItemClickListener {
        void onItemClick(View view, NewsModel obj, int position);
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public InsiderTransactionAdapterR(List<InsiderTransactionModel> items) {
        this.items = items;
    }




    public static class OriginalViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final TextView title;
        public final TextView data_1;
        public final TextView data_2;
        public final TextView data_3;
        public final TextView data_4;
        public final TextView data_5;

        public OriginalViewHolder(View view) {
            super(view);

            title= view.findViewById(R.id.title);
            data_1= view.findViewById(R.id.data_1);
            data_2= view.findViewById(R.id.data_2);
            data_3= view.findViewById(R.id.data_3);
            data_4= view.findViewById(R.id.data_4);
            data_5= view.findViewById(R.id.data_5);
         }
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_insider_transaction, parent, false);
            vh = new OriginalViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OriginalViewHolder) {
            final InsiderTransactionModel p = items.get(position);
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

            vItem.data_5.setText(p.getData_5());
            if(p.getData_5()!=null){
                if(p.getData_5().equalsIgnoreCase("0")){
                    vItem.data_5.setText("-");
                }
            }


            if(p.isIs_head()){
                vItem.title.setTextSize(17f);
                vItem.title.setTypeface(null,Typeface.BOLD);
                vItem.data_1.setTypeface(null,Typeface.BOLD);
                vItem.data_2.setTypeface(null,Typeface.BOLD);
                vItem.data_2.setTypeface(null,Typeface.BOLD);
                vItem.data_4.setTypeface(null,Typeface.BOLD);
                vItem.data_5.setTypeface(null,Typeface.BOLD);

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

    public void insertData(List<InsiderTransactionModel> items) {
        setLoaded();
        int positionStart = getItemCount();
        int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void setLoaded() {
        for (int i = 0; i < getItemCount(); i++) {
            if (items.get(i) == null) {
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }


}
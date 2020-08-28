package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.InsiderTransactionModel;
import com.silverlinesoftwares.intratips.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class InsiderTransactionAdapterR extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_PROG = 0;
    private final int VIEW_HEAD = 1;
    private final int VIEW_ITEM = 2;

    private List<InsiderTransactionModel> items = new ArrayList<>();

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
    public InsiderTransactionAdapterR(Context context, RecyclerView view, List<InsiderTransactionModel> items) {
        this.items = items;
        ctx = context;
    }




    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView data_1;
        public TextView data_2;
        public TextView data_3;
        public TextView data_4;
        public TextView data_5;

        public OriginalViewHolder(View view) {
            super(view);

            title=(TextView) view.findViewById(R.id.title);
            data_1=(TextView) view.findViewById(R.id.data_1);
            data_2=(TextView) view.findViewById(R.id.data_2);
            data_3=(TextView) view.findViewById(R.id.data_3);
            data_4=(TextView)view.findViewById(R.id.data_4);
            data_5=(TextView)view.findViewById(R.id.data_5);
         }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_insider_transaction, parent, false);
            vh = new OriginalViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

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
                return VIEW_HEAD;
            } else {
                return VIEW_ITEM;
            }
        } else {
            return VIEW_PROG;
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
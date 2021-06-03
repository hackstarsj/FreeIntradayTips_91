package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.AdvanceScreenerActivity;
import com.silverlinesoftwares.intratips.activity.ScreenerNseActivity;
import com.silverlinesoftwares.intratips.models.ScreenerItem;

import java.util.List;


public class ScreenAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<ScreenerItem> DataList;
    Context context;

    public ScreenAdapter(Context context, List<ScreenerItem> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public ScreenerItem getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=convertView;
        Button button_one;
        LinearLayout linearLayout;


        if(view==null){
            view=inflater.inflate(R.layout.screener_item,null);
            view.setTag(R.id.list_sub_item,view.findViewById(R.id.list_sub_item));
            view.setTag(R.id.button_clck,view.findViewById(R.id.button_clck));
        }

        linearLayout=(LinearLayout) view.getTag(R.id.list_sub_item);
        button_one=(Button) view.getTag(R.id.button_clck);

        button_one.setText(DataList.get(position).getTitle());
        if(((LinearLayout) linearLayout).getChildCount() > 0) {
            ((LinearLayout) linearLayout).removeAllViews();
        }

        button_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataList.get(position).getTitle().contains("OPEN=LOW")) {
                    context.startActivity(new Intent(context, ScreenerNseActivity.class));
                } else {
                    if (DataList.get(position).getClick()) {
                        DataList.get(position).setClick(false);
                    } else {
                        DataList.get(position).setClick(true);
                    }
                    notifyDataSetChanged();
                }
            }
        });

        for(int i=0;i<DataList.get(position).getScreenerSubItemList().size();i++) {
            View view1 = inflater.inflate(R.layout.sub_screen_row, null);
            Button btn_sub;
            final TextView methods;
            final TextView url_item;


            btn_sub = (Button) view1.findViewById(R.id.sub_item_btn);
            url_item = (TextView) view1.findViewById(R.id.text_url);
            methods = (TextView) view1.findViewById(R.id.txt_methods);

            linearLayout.addView(view1);

            btn_sub.setText(DataList.get(position).getScreenerSubItemList().get(i).getItem());
            url_item.setText(DataList.get(position).getScreenerSubItemList().get(i).getUrl());
            methods.setText(DataList.get(position).getScreenerSubItemList().get(i).getMethods());
            btn_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, AdvanceScreenerActivity.class).putExtra("url",url_item.getText().toString()).putExtra("method",methods.getText().toString()));
                }
            });

        }

        if(DataList.get(position).getClick()){
            Drawable img = context.getResources().getDrawable( R.drawable.ic_remove_circle_black_24dp);
            img.setBounds( 0, 0, 60, 60 );
            button_one.setCompoundDrawables(img,null,null,null);
         linearLayout.setVisibility(View.VISIBLE);
        }
        else{
            Drawable img = context.getResources().getDrawable( R.drawable.ic_add_circle_black_24dp);
            img.setBounds( 0, 0, 60, 60 );
            button_one.setCompoundDrawables(img,null,null,null);
            linearLayout.setVisibility(View.GONE);

        }

            return view;
    }
}


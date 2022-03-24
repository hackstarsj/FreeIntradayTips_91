package com.silverlinesoftwares.intratips.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.High_Low_Model;

import java.util.List;

public class HighLowAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<High_Low_Model> DataList;
    boolean is_high;

    public HighLowAdapter(Context context, List<High_Low_Model> equityModels,boolean is_high){
        this.DataList=equityModels;
        this.is_high=is_high;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<High_Low_Model> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public High_Low_Model getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        TextView symbol;
        TextView new_52_h_data;
        TextView prev_low_high_text;
        TextView prev_high_data;
        TextView prev_close_data;
        TextView text_high_low;
        TextView change;
        ImageView indicator;
        TextView  percentage_text;
        TextView ltp;


        if(view==null){
              view=inflater.inflate(R.layout.row_52_high_low,parent,false);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.new_52_h_data,view.findViewById(R.id.new_52_h_data));
            view.setTag(R.id.prev_low_high_text,view.findViewById(R.id.prev_low_high_text));
            view.setTag(R.id.prev_high_data,view.findViewById(R.id.prev_high_data));
            view.setTag(R.id.prev_close_data,view.findViewById(R.id.prev_close_data));
            view.setTag(R.id.text_high_low,view.findViewById(R.id.text_high_low));
            view.setTag(R.id.change,view.findViewById(R.id.change));
            view.setTag(R.id.indicator,view.findViewById(R.id.indicator));
            view.setTag(R.id.percentage_text,view.findViewById(R.id.percentage_text));
            view.setTag(R.id.ltp,view.findViewById(R.id.ltp));
        }
        High_Low_Model equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        new_52_h_data=(TextView) view.getTag(R.id.new_52_h_data);
        prev_low_high_text=(TextView)view.getTag(R.id.prev_low_high_text);
        prev_high_data=(TextView)view.getTag(R.id.prev_high_data);
        prev_close_data=(TextView) view.getTag(R.id.prev_close_data);
        text_high_low=(TextView)view.getTag(R.id.text_high_low);
        change=(TextView)view.getTag(R.id.change);
        indicator=(ImageView) view.getTag(R.id.indicator);
        percentage_text=(TextView) view.getTag(R.id.percentage_text);
        ltp=(TextView) view.getTag(R.id.ltp);



        symbol.setText(equityModel.getSymbol());
        ltp.setText(equityModel.getLtp());
        new_52_h_data.setText(equityModel.getValue());
        if(is_high) {
            prev_low_high_text.setText("New 52W/H");
            text_high_low.setText(" Prev. High ");
        }
        else{
            prev_low_high_text.setText("New 52W/L");
            text_high_low.setText("Prev. Low");
        }

        prev_high_data.setText(equityModel.getValue_old());
        prev_close_data.setText(equityModel.getPrev());
        percentage_text.setText("% "+equityModel.getpChange());
        change.setText(equityModel.getChange());



        if(!equityModel.getpChange().contains("-"))
        {
            indicator.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
            percentage_text.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            indicator.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            percentage_text.setTextColor(Color.parseColor("#d50000"));
        }


        return view;
    }
}

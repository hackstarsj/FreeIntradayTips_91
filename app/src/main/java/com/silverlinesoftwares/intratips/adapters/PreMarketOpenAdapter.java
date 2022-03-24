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
import com.silverlinesoftwares.intratips.models.PreMarketOpenModel;

import java.util.List;

public class PreMarketOpenAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<PreMarketOpenModel> DataList;

    public PreMarketOpenAdapter(Context context, List<PreMarketOpenModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<PreMarketOpenModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public PreMarketOpenModel getItem(int position) {
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
        TextView ltp;
        TextView prev_close_data;
        TextView quantity_data;
        TextView value_data;
        TextView change;
        ImageView indicator;
        TextView  percentage_text;

        if(view==null){
              view=inflater.inflate(R.layout.pre_market_open,parent,false);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.prev_close_data,view.findViewById(R.id.prev_close_data));
            view.setTag(R.id.value_data,view.findViewById(R.id.value_data));
            view.setTag(R.id.quantity_data,view.findViewById(R.id.quantity_data));
            view.setTag(R.id.change,view.findViewById(R.id.change));
            view.setTag(R.id.indicator,view.findViewById(R.id.indicator));
            view.setTag(R.id.percentage_text,view.findViewById(R.id.percentage_text));
            view.setTag(R.id.ltp,view.findViewById(R.id.ltp));
        }
        PreMarketOpenModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        ltp=(TextView) view.getTag(R.id.ltp);
        value_data=(TextView) view.getTag(R.id.value_data);
        prev_close_data=(TextView) view.getTag(R.id.prev_close_data);
        percentage_text=(TextView) view.getTag(R.id.percentage_text);
        quantity_data=(TextView) view.getTag(R.id.quantity_data);
        change=(TextView) view.getTag(R.id.change);
        indicator=(ImageView) view.getTag(R.id.indicator);

        symbol.setText(equityModel.getSymbol());
        ltp.setText(equityModel.getIep());
        prev_close_data.setText(equityModel.getpCls());
        value_data.setText(equityModel.getiVal());
        quantity_data.setText(equityModel.getFinQnty());
        change.setText(equityModel.getChn());
        percentage_text.setText("% "+equityModel.getPerChn());

        if(!equityModel.getPerChn().contains("-"))
        {
            indicator.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
            change.setTextColor(Color.parseColor("#4caf50"));
            percentage_text.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            indicator.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            change.setTextColor(Color.parseColor("#d50000"));
            percentage_text.setTextColor(Color.parseColor("#d50000"));
        }


        return view;
    }
}

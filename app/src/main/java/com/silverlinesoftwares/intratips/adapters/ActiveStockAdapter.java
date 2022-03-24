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
import com.silverlinesoftwares.intratips.models.ActiveStockModel;

import java.util.List;

public class ActiveStockAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<ActiveStockModel> DataList;

    public ActiveStockAdapter(Context context, List<ActiveStockModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<ActiveStockModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public ActiveStockModel getItem(int position) {
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
        TextView traded_qty;
        TextView prev_close;
        ImageView indicator;
        TextView  percentage_text;

        if(view==null){
              view=inflater.inflate(R.layout.active_rows,parent,false);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.traded_qty_text,view.findViewById(R.id.traded_qty_text));
            view.setTag(R.id.prev_close_text,view.findViewById(R.id.prev_close_text));
            view.setTag(R.id.change,view.findViewById(R.id.change));
            view.setTag(R.id.indicator,view.findViewById(R.id.indicator));
            view.setTag(R.id.percentage_text,view.findViewById(R.id.percentage_text));
            view.setTag(R.id.ltp,view.findViewById(R.id.ltp));
        }
        ActiveStockModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        ltp=(TextView) view.getTag(R.id.ltp);
        traded_qty=(TextView) view.getTag(R.id.traded_qty_text);
        prev_close=(TextView) view.getTag(R.id.prev_close_text);
        percentage_text=(TextView) view.getTag(R.id.percentage_text);
        indicator=(ImageView) view.getTag(R.id.indicator);

        symbol.setText(equityModel.getSymbol());
        ltp.setText(equityModel.getLtp());
        prev_close.setText(equityModel.getPreviousPrice());
        traded_qty.setText(equityModel.getTradedQuantity());
        percentage_text.setText("% "+equityModel.getNetPrice());



        if(!equityModel.getNetPrice().contains("-"))
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

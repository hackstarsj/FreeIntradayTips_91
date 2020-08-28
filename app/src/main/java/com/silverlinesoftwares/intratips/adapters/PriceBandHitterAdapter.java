package com.silverlinesoftwares.intratips.adapters;

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
import com.silverlinesoftwares.intratips.models.PriceBandHitterModel;

import java.util.List;

public class PriceBandHitterAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<PriceBandHitterModel> DataList;

    public PriceBandHitterAdapter(Context context, List<PriceBandHitterModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<PriceBandHitterModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public PriceBandHitterModel getItem(int position) {
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
        TextView high_data;
        TextView low_data;
        TextView high_52_data;
        TextView low_52;
        TextView ltp;
        TextView change;
        TextView  change_percentage;
        TextView volume_data;
        TextView value_data;
        TextView price_band_data;
        ImageView indicator_2;
        ImageView indicator;

        if(view==null){
              view=inflater.inflate(R.layout.price_band_row,null);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.high_data,view.findViewById(R.id.high_data));
            view.setTag(R.id.low_data,view.findViewById(R.id.low_data));
            view.setTag(R.id.high_52_data,view.findViewById(R.id.high_52_data));
            view.setTag(R.id.low_52,view.findViewById(R.id.low_52));
            view.setTag(R.id.change,view.findViewById(R.id.change));
            view.setTag(R.id.ltp,view.findViewById(R.id.ltp));
            view.setTag(R.id.change_percentage,view.findViewById(R.id.change_percentage));
            view.setTag(R.id.volume_data,view.findViewById(R.id.volume_data));
            view.setTag(R.id.value_data,view.findViewById(R.id.value_data));
            view.setTag(R.id.price_band_data,view.findViewById(R.id.price_band_data));
            view.setTag(R.id.indicator_2,view.findViewById(R.id.indicator_2));
            view.setTag(R.id.indicator,view.findViewById(R.id.indicator));
        }
        PriceBandHitterModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        ltp=(TextView) view.getTag(R.id.ltp);
        high_data=(TextView) view.getTag(R.id.high_data);
        low_data=(TextView) view.getTag(R.id.low_data);
        high_52_data=(TextView) view.getTag(R.id.high_52_data);
        low_52=(TextView)view.getTag(R.id.low_52);
        change=(TextView)view.getTag(R.id.change);
        change_percentage=(TextView)view.getTag(R.id.change_percentage);
        volume_data=(TextView)view.getTag(R.id.volume_data);
        value_data=(TextView)view.getTag(R.id.value_data);
        price_band_data=(TextView)view.getTag(R.id.price_band_data);
        indicator_2=(ImageView)view.getTag(R.id.indicator_2);
        indicator=(ImageView) view.getTag(R.id.indicator);

        symbol.setText(equityModel.getSymbol());
        ltp.setText(equityModel.getLtp());
        high_data.setText(equityModel.getHighPrice());
        low_data.setText(equityModel.getLowPrice());
        high_52_data.setText(equityModel.getHighPrice52());
        low_52.setText(equityModel.getLowPrice52());
        change.setText(equityModel.getChng());
        change_percentage.setText("% "+equityModel.getPerChng());
        volume_data.setText(equityModel.getTradedQuantity());
        value_data.setText(equityModel.getTurnoverInLakhs());
        price_band_data.setText("% "+equityModel.getPriceBandPer());


        if(!equityModel.getPerChng().contains("-"))
        {
            indicator.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
            change_percentage.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            indicator.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            change_percentage.setTextColor(Color.parseColor("#d50000"));
        }


        if(!equityModel.getPriceBandPer().contains("-"))
        {
            indicator_2.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
            price_band_data.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            indicator_2.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            price_band_data.setTextColor(Color.parseColor("#d50000"));
        }


        return view;
    }
}

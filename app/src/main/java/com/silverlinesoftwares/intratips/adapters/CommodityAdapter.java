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
import com.silverlinesoftwares.intratips.models.CommodityModel;

import java.util.List;

public class CommodityAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<CommodityModel> DataList;

    public CommodityAdapter(Context context, List<CommodityModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<CommodityModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public CommodityModel getItem(int position) {
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
        TextView latest_price_data;
        TextView open_interest;
        TextView volume;
        TextView change;
        ImageView indicator;
        TextView  percentage_text;

        if(view==null){
              view=inflater.inflate(R.layout.commodity_row,null);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.latest_price_data,view.findViewById(R.id.latest_price_data));
            view.setTag(R.id.change_percentage_data,view.findViewById(R.id.change_percentage_data));
            view.setTag(R.id.change_data,view.findViewById(R.id.change_data));
            view.setTag(R.id.open_interest_data,view.findViewById(R.id.open_interest_data));
            view.setTag(R.id.volume_data,view.findViewById(R.id.volume_data));
            view.setTag(R.id.indicator,view.findViewById(R.id.indicator));
        }
        CommodityModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        latest_price_data=(TextView) view.getTag(R.id.latest_price_data);
        percentage_text=(TextView) view.getTag(R.id.change_percentage_data);
        change=(TextView) view.getTag(R.id.change_data);
        open_interest=(TextView) view.getTag(R.id.open_interest_data);
        volume=(TextView) view.getTag(R.id.volume_data);
        indicator=(ImageView) view.getTag(R.id.indicator);

        symbol.setText(equityModel.getSymbol());
        latest_price_data.setText(equityModel.getPrice());
        open_interest.setText(equityModel.getOpen_interest());
        volume.setText(equityModel.getVolume());
        change.setText(equityModel.getChange());
        percentage_text.setText("% "+equityModel.getChange_percentage());


        if(!equityModel.getChange_percentage().contains("-")){
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

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
import com.silverlinesoftwares.intratips.models.CommodityModel;
import com.silverlinesoftwares.intratips.models.MutualFundModel;

import java.util.List;

public class MutualFundAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<MutualFundModel> DataList;

    public MutualFundAdapter(Context context, List<MutualFundModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<MutualFundModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public MutualFundModel getItem(int position) {
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
        TextView change;
        TextView chnage_percentage;
        TextView price;
        TextView avg_50;
        TextView avg_200;
        TextView  avg_30;
        TextView ytd;

        if(view==null){
              view=inflater.inflate(R.layout.mutual_fund_row,parent,false);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.change_data,view.findViewById(R.id.change_data));
            view.setTag(R.id.change_percentage_data,view.findViewById(R.id.change_percentage_data));
            view.setTag(R.id.price_data,view.findViewById(R.id.price_data));
            view.setTag(R.id.avg_50_data,view.findViewById(R.id.avg_50_data));
            view.setTag(R.id.avg_20_data,view.findViewById(R.id.avg_20_data));
            view.setTag(R.id.avg_30_data,view.findViewById(R.id.avg_30_data));
            view.setTag(R.id.ytd_data,view.findViewById(R.id.ytd_data));

        }
        MutualFundModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        price=(TextView) view.getTag(R.id.price_data);
        chnage_percentage=(TextView) view.getTag(R.id.change_percentage_data);
        change=(TextView) view.getTag(R.id.change_data);
        avg_30=(TextView) view.getTag(R.id.avg_30_data);
        avg_50=(TextView) view.getTag(R.id.avg_50_data);
        avg_200=(TextView) view.getTag(R.id.avg_20_data);
        ytd=(TextView) view.getTag(R.id.ytd_data);

        symbol.setText(equityModel.getSymbol());
        price.setText(equityModel.getPrices());
        change.setText(equityModel.getChnage());
        chnage_percentage.setText("% "+equityModel.getChange_percentage());
        avg_30.setText(equityModel.getAvg_30());
        avg_50.setText(equityModel.getAvg_50());
        avg_200.setText(equityModel.getAvg_200());
        ytd.setText(equityModel.getYtd());


        if(!equityModel.getChange_percentage().contains("-")){
            change.setTextColor(Color.parseColor("#4caf50"));
            chnage_percentage.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            change.setTextColor(Color.parseColor("#d50000"));
            chnage_percentage.setTextColor(Color.parseColor("#d50000"));
        }


        return view;
    }
}

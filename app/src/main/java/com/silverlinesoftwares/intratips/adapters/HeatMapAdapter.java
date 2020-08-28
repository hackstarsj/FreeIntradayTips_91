package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.SectorStockModel;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import java.util.List;

public class HeatMapAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<SectorStockModel> DataList;
    boolean is_sector;

    public HeatMapAdapter(Context context, List<SectorStockModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<SectorStockModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public SectorStockModel getItem(int position) {
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
        TextView pointchange;
        TextView  percentage;
        CardView card_stock;

        if(view==null){
              view=inflater.inflate(R.layout.sector_stock_row,null);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.percentage,view.findViewById(R.id.percentage));
            view.setTag(R.id.pointchange,view.findViewById(R.id.pointchange));
            view.setTag(R.id.card_stock,view.findViewById(R.id.card_stock));
        }
        SectorStockModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        percentage=(TextView) view.getTag(R.id.percentage);
        pointchange=(TextView) view.getTag(R.id.pointchange);
        card_stock=(CardView)view.getTag(R.id.card_stock);

        symbol.setText(equityModel.getSymbol());
        pointchange.setText(equityModel.getPointchange());
        percentage.setText("% "+equityModel.getPerchange());
        card_stock.setBackgroundColor(Color.parseColor(StaticMethods.getColor(Double.parseDouble(equityModel.getPerchange()))));




        return view;
    }
}

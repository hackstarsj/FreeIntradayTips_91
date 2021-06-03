package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.ShortSellModel;

import java.util.List;

public class ShortSellData extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<ShortSellModel> DataList;

    public ShortSellData(Context context, List<ShortSellModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<ShortSellModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public ShortSellModel getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        TextView date_data;
        TextView symbol;
        TextView price_data;

        if(view==null){
              view=inflater.inflate(R.layout.short_sell_row,null);
            view.setTag(R.id.date_data,view.findViewById(R.id.date_data));
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.price_data,view.findViewById(R.id.price_data));
        }
        ShortSellModel equityModel=DataList.get(position);

        date_data=(TextView)view.getTag(R.id.date_data);
        symbol=(TextView)view.getTag(R.id.symbol);
        price_data=(TextView)view.getTag(R.id.price_data);


        date_data.setText(equityModel.getDates());
        symbol.setText(equityModel.getSymbol());
        price_data.setText("Qty : "+equityModel.getQuantity());

        return view;
    }
}

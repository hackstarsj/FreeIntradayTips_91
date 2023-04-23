package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.BulkModel;

import java.util.List;

public class BulkDealAdapter extends BaseAdapter {

    final LayoutInflater inflater;
    final Context context;
    final List<BulkModel> DataList;

    public BulkDealAdapter(Context context, List<BulkModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<BulkModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public BulkModel getItem(int position) {
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
        TextView seller_name;
        TextView buy_sell;
        TextView traded_quantity;

        if(view==null){
              view=inflater.inflate(R.layout.bulk_deal_row,parent,false);
            view.setTag(R.id.date_data,view.findViewById(R.id.date_data));
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.price_data,view.findViewById(R.id.price_data));
            view.setTag(R.id.seller_name,view.findViewById(R.id.seller_name));
            view.setTag(R.id.buy_sell,view.findViewById(R.id.buy_sell));
            view.setTag(R.id.traded_quantity,view.findViewById(R.id.traded_quantity));
        }
        BulkModel equityModel=DataList.get(position);

        date_data=(TextView)view.getTag(R.id.date_data);
        symbol=(TextView)view.getTag(R.id.symbol);
        price_data=(TextView)view.getTag(R.id.price_data);
        seller_name=(TextView)view.getTag(R.id.seller_name);
        buy_sell=(TextView)view.getTag(R.id.buy_sell);
        traded_quantity=(TextView)view.getTag(R.id.traded_quantity);


        date_data.setText(equityModel.getDates());
        symbol.setText(equityModel.getSymbol());
        price_data.setText(String.format("Qty : %s", equityModel.getPrices()));
        seller_name.setText(equityModel.getClient_name());
        traded_quantity.setText(equityModel.getQuantity());

        buy_sell.setText(equityModel.getBuy_sell());

        if(equityModel.getBuy_sell().contains("SELL"))
        {
            buy_sell.setBackgroundColor(Color.parseColor("#E53935"));
        }
        else{
            buy_sell.setBackgroundColor(Color.parseColor("#00C853"));
        }


        return view;
    }
}

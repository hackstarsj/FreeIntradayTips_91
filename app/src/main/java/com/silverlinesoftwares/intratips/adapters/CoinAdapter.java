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
import com.silverlinesoftwares.intratips.models.CoinModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CoinAdapter extends BaseAdapter {

    final LayoutInflater inflater;
    final Context context;
    final List<CoinModel> DataList;

    public CoinAdapter(Context context, List<CoinModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<CoinModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public CoinModel getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        ImageView coin_logo;
        TextView symbol;
        TextView name;
        TextView price;
        TextView change;
        TextView change_per;
        TextView volume;
        TextView market_cap;
        TextView  volume_24;

        if(view==null){
            view=inflater.inflate(R.layout.coin_row,parent,false);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.name,view.findViewById(R.id.name));
            view.setTag(R.id.coin_logo,view.findViewById(R.id.coin_logo));
            view.setTag(R.id.price,view.findViewById(R.id.price));
            view.setTag(R.id.change,view.findViewById(R.id.change));
            view.setTag(R.id.change_percentage,view.findViewById(R.id.change_percentage));
            view.setTag(R.id.volume,view.findViewById(R.id.volume));
            view.setTag(R.id.market_cap,view.findViewById(R.id.market_cap));
            view.setTag(R.id.volume_in_24,view.findViewById(R.id.volume_in_24));
        }
        CoinModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        name=(TextView) view.getTag(R.id.name);
        price=(TextView) view.getTag(R.id.price);
        change=(TextView) view.getTag(R.id.change);
        change_per=(TextView) view.getTag(R.id.change_percentage);
        volume=(TextView) view.getTag(R.id.volume);
        volume_24=(TextView) view.getTag(R.id.volume_in_24);
        market_cap=(TextView) view.getTag(R.id.market_cap);
        coin_logo=(ImageView) view.getTag(R.id.coin_logo);

        symbol.setText(equityModel.getSymbol());
        name.setText(equityModel.getName());
        price.setText(equityModel.getPrice());
        volume.setText(equityModel.getVolume());
        change.setText(equityModel.getChange());
        volume_24.setText(equityModel.getVolume_24());
        change_per.setText(equityModel.getChange_per());
        market_cap.setText(equityModel.getMarket_cap());


        if(!equityModel.getChange_per().contains("-")){
            change.setTextColor(Color.parseColor("#4caf50"));
            change_per.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            change.setTextColor(Color.parseColor("#d50000"));
            change_per.setTextColor(Color.parseColor("#d50000"));
        }

        if(equityModel.getCoin_logo()!=null && !equityModel.getCoin_logo().isEmpty()) {
            Picasso.get().load(DataList.get(position).getCoin_logo()).placeholder(R.drawable.web_hi_res_512).into(coin_logo, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    coin_logo.setImageResource(R.drawable.web_hi_res_512);
                }

            });
        }

        return view;
    }
}

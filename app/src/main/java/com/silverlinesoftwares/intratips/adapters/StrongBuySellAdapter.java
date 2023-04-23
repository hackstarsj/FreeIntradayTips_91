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
import com.silverlinesoftwares.intratips.models.StrongBuySellModel;

import java.util.List;

public class StrongBuySellAdapter extends BaseAdapter {

    final LayoutInflater inflater;
    final Context context;
    final List<StrongBuySellModel> DataList;

    public StrongBuySellAdapter(Context context, List<StrongBuySellModel> equityModels){
        this.DataList=equityModels;
        inflater= LayoutInflater.from(context);
        this.context=context;
    }

    public List<StrongBuySellModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public StrongBuySellModel getItem(int position) {
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
        TextView market_cap;
        TextView volume;
        TextView change;
        ImageView indicator;
        TextView percentage_text;

        if(view==null){
              view=inflater.inflate(R.layout.trading_view_row,parent,false);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.market_cap,view.findViewById(R.id.market_cap));
            view.setTag(R.id.vol_text,view.findViewById(R.id.vol_text));
            view.setTag(R.id.change,view.findViewById(R.id.change));
            view.setTag(R.id.indicator,view.findViewById(R.id.indicator));
            view.setTag(R.id.percentage_text,view.findViewById(R.id.percentage_text));
            view.setTag(R.id.ltp,view.findViewById(R.id.ltp));
        }
        StrongBuySellModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        ltp=(TextView) view.getTag(R.id.ltp);
        market_cap=(TextView) view.getTag(R.id.market_cap);
        volume=(TextView) view.getTag(R.id.vol_text);
        percentage_text=(TextView) view.getTag(R.id.percentage_text);
        change=(TextView) view.getTag(R.id.change);
        indicator=(ImageView) view.getTag(R.id.indicator);

        symbol.setText(equityModel.getStock());
        ltp.setText(equityModel.getLtp());
        market_cap.setText(equityModel.getMktCap());
        volume.setText(equityModel.getVol());
        change.setText(equityModel.getChange());
        percentage_text.setText(String.format("%% %s", equityModel.getChangePer()));

        if(!equityModel.isLow()){
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

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
import com.silverlinesoftwares.intratips.models.MostDeliverModel;

import java.util.List;

public class MostDeliveryAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<MostDeliverModel> DataList;

    public MostDeliveryAdapter(Context context, List<MostDeliverModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<MostDeliverModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public MostDeliverModel getItem(int position) {
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
        TextView traded_qty;
        TextView reliable_qty;
        TextView perentage_delivey;

        if(view==null){
              view=inflater.inflate(R.layout.most_delivery_row,null);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.traded_qty,view.findViewById(R.id.traded_qty));
            view.setTag(R.id.reliable_qty,view.findViewById(R.id.reliable_qty));
            view.setTag(R.id.deliverable_qty,view.findViewById(R.id.deliverable_qty));
        }


        MostDeliverModel equityModel=DataList.get(position);

        symbol=(TextView)view.getTag(R.id.symbol);
        traded_qty=(TextView)view.getTag(R.id.traded_qty);
        perentage_delivey=(TextView)view.getTag(R.id.deliverable_qty);
        reliable_qty=(TextView)view.getTag(R.id.reliable_qty);


        perentage_delivey.setText(equityModel.getPercentage_delivery()+" %");
        symbol.setText(equityModel.getSymbol());
        traded_qty.setText(equityModel.getTraded_qty());
        reliable_qty.setText(equityModel.getReliable_qty());

        return view;
    }
}

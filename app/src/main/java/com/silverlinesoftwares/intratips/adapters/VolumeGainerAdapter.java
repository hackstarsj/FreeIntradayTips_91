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
import com.silverlinesoftwares.intratips.models.High_Low_Model;
import com.silverlinesoftwares.intratips.models.VolumeGainerModel;

import java.util.List;

public class VolumeGainerAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<VolumeGainerModel> DataList;

    public VolumeGainerAdapter(Context context, List<VolumeGainerModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<VolumeGainerModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public VolumeGainerModel getItem(int position) {
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
        TextView turn_over_data;
        TextView volume_data;
        TextView ltp;
        TextView change_percentage_data;
        TextView avg_data_1;
        TextView change_data_1;
        TextView avg_data_2;
        ImageView  indicator;
        TextView change_data_2;


        if(view==null){
              view=inflater.inflate(R.layout.volume_gainer_row,null);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.turn_over_data,view.findViewById(R.id.turn_over_data));
            view.setTag(R.id.volume_data,view.findViewById(R.id.volume_data));
            view.setTag(R.id.ltp,view.findViewById(R.id.ltp));
            view.setTag(R.id.change_percentage_data,view.findViewById(R.id.change_percentage_data));
            view.setTag(R.id.avg_data_1,view.findViewById(R.id.avg_data_1));
            view.setTag(R.id.change_data_1,view.findViewById(R.id.change_data_1));
            view.setTag(R.id.avg_data_2,view.findViewById(R.id.avg_data_2));
            view.setTag(R.id.indicator,view.findViewById(R.id.indicator));
            view.setTag(R.id.change_data_2,view.findViewById(R.id.change_data_2));
        }
        VolumeGainerModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        turn_over_data=(TextView) view.getTag(R.id.turn_over_data);
        volume_data=(TextView)view.getTag(R.id.volume_data);
        ltp=(TextView)view.getTag(R.id.ltp);
        change_percentage_data=(TextView) view.getTag(R.id.change_percentage_data);
        avg_data_1=(TextView)view.getTag(R.id.avg_data_1);
        change_data_1=(TextView)view.getTag(R.id.change_data_1);
        indicator=(ImageView) view.getTag(R.id.indicator);
        avg_data_2=(TextView)view.getTag(R.id.avg_data_2);
        change_data_2=(TextView) view.getTag(R.id.change_data_2);



        symbol.setText(equityModel.getSym());
        ltp.setText(equityModel.getLtp());
        turn_over_data.setText(equityModel.getValue());
        volume_data.setText(equityModel.getTurn_lkh());
        change_percentage_data.setText("% "+equityModel.getNetpr());
        avg_data_1.setText(equityModel.getWeek1a());
        change_data_1.setText(equityModel.getWeek1vc());
        avg_data_2.setText(equityModel.getWeek2a());
        change_data_2.setText(equityModel.getWeek2vc());



        if(!equityModel.getNetpr().contains("-"))
        {
            indicator.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
            change_percentage_data.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            indicator.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            change_percentage_data.setTextColor(Color.parseColor("#d50000"));
        }


        return view;
    }
}

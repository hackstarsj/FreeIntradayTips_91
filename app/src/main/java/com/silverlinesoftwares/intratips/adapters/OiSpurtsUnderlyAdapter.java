package com.silverlinesoftwares.intratips.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.OiSpurtsUnderlyingModel;

import java.util.List;

public class OiSpurtsUnderlyAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<OiSpurtsUnderlyingModel> DataList;
    String date_1;
    String date_2;

    public OiSpurtsUnderlyAdapter(Context context, List<OiSpurtsUnderlyingModel> equityModels, String date_1, String date_2){
        this.DataList=equityModels;
        this.date_1=date_1;
        this.date_2=date_2;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<OiSpurtsUnderlyingModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public OiSpurtsUnderlyingModel getItem(int position) {
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
        TextView date_1;
        TextView date_1_data;
        TextView date_2;
        TextView data_2_data;
        TextView change_in_oi;
        TextView change_oi_percentage;
        TextView futures_data;
        TextView opt_nat_data;
        TextView total_data;
        TextView opt_pre_data;
        TextView volume_contract_data;
        TextView underlying_cm_data;


        if(view==null){
              view=inflater.inflate(R.layout.oi_spurts_underlying_row,parent,false);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.date_1,view.findViewById(R.id.date_1));
            view.setTag(R.id.date_1_data,view.findViewById(R.id.date_1_data));
            view.setTag(R.id.date_2,view.findViewById(R.id.date_2));
            view.setTag(R.id.data_2_data,view.findViewById(R.id.data_2_data));
            view.setTag(R.id.change_in_oi,view.findViewById(R.id.change_in_oi));
            view.setTag(R.id.change_oi_percentage,view.findViewById(R.id.change_oi_percentage));
            view.setTag(R.id.futures_data,view.findViewById(R.id.futures_data));
            view.setTag(R.id.opt_nat_data,view.findViewById(R.id.opt_nat_data));
            view.setTag(R.id.total_data,view.findViewById(R.id.total_data));
            view.setTag(R.id.opt_pre_data,view.findViewById(R.id.opt_pre_data));
            view.setTag(R.id.volume_contract_data,view.findViewById(R.id.volume_contract_data));
            view.setTag(R.id.underlying_cm_data,view.findViewById(R.id.underlying_cm_data));
        }
        OiSpurtsUnderlyingModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        date_1=(TextView) view.getTag(R.id.date_1);
        date_1_data=(TextView) view.getTag(R.id.date_1_data);
        date_2=(TextView) view.getTag(R.id.date_2);
        data_2_data=(TextView) view.getTag(R.id.data_2_data);
        change_in_oi=(TextView) view.getTag(R.id.change_in_oi);
        change_oi_percentage=(TextView) view.getTag(R.id.change_oi_percentage);
        futures_data=(TextView) view.getTag(R.id.futures_data);
        opt_nat_data=(TextView) view.getTag(R.id.opt_nat_data);
        total_data=(TextView) view.getTag(R.id.total_data);
        opt_pre_data=(TextView) view.getTag(R.id.opt_pre_data);
        volume_contract_data=(TextView) view.getTag(R.id.volume_contract_data);
        underlying_cm_data=(TextView) view.getTag(R.id.underlying_cm_data);


        symbol.setText(equityModel.getSymbol());
        date_1.setText(this.date_1);
        date_1_data.setText(equityModel.getLatestOI());
        date_2.setText(this.date_2);
        data_2_data.setText(equityModel.getPrevOI());
        change_in_oi.setText(equityModel.getOiChange());
        change_oi_percentage.setText(equityModel.getPercOIchange());
        futures_data.setText(equityModel.getFUTVAL());
        opt_nat_data.setText(equityModel.getOPTVAL());
        total_data.setText(equityModel.getTOTVAL());
        opt_pre_data.setText(equityModel.getOPVAL());
        volume_contract_data.setText(equityModel.getVolume());
        underlying_cm_data.setText(equityModel.getUnderlying());



        if(!equityModel.getOiChange().contains("-"))
        {
            change_in_oi.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            change_in_oi.setTextColor(Color.parseColor("#d50000"));
        }


        if(!equityModel.getPercOIchange().contains("-"))
        {
            change_oi_percentage.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            change_oi_percentage.setTextColor(Color.parseColor("#d50000"));
        }


        return view;
    }
}

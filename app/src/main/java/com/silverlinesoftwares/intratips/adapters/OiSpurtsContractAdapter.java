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
import com.silverlinesoftwares.intratips.models.OiSpurtsContractsModel;

import java.util.List;

public class OiSpurtsContractAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<OiSpurtsContractsModel> DataList;
    String date_1;
    String date_2;

    public OiSpurtsContractAdapter(Context context, List<OiSpurtsContractsModel> equityModels,String date_1,String date_2){
        this.DataList=equityModels;
        this.date_1=date_1;
        this.date_2=date_2;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<OiSpurtsContractsModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public OiSpurtsContractsModel getItem(int position) {
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
        TextView ltp_data;
        TextView date_1;
        TextView date_1_data;
        TextView date_2;
        TextView data_2_data;
        TextView change_in_oi;
        TextView change_in_ltp;
        TextView expiry_data;
        TextView strike_price_data;
        TextView type_data;
        TextView prev_close_data;
        TextView volume_contract_data;
        TextView underlying_cm_data;


        if(view==null){
              view=inflater.inflate(R.layout.oi_spurts_contacts_row,parent,false);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.ltp_data,view.findViewById(R.id.ltp_data));
            view.setTag(R.id.date_1,view.findViewById(R.id.date_1));
            view.setTag(R.id.date_1_data,view.findViewById(R.id.date_1_data));
            view.setTag(R.id.data_2_data,view.findViewById(R.id.data_2_data));
            view.setTag(R.id.date_2,view.findViewById(R.id.date_2));
            view.setTag(R.id.change_in_oi,view.findViewById(R.id.change_in_oi));
            view.setTag(R.id.change_in_ltp,view.findViewById(R.id.change_in_ltp));
            view.setTag(R.id.expiry_data,view.findViewById(R.id.expiry_data));
            view.setTag(R.id.strike_price_data,view.findViewById(R.id.strike_price_data));
            view.setTag(R.id.type_data,view.findViewById(R.id.type_data));
            view.setTag(R.id.prev_close_data,view.findViewById(R.id.prev_close_data));
            view.setTag(R.id.volume_contract_data,view.findViewById(R.id.volume_contract_data));
            view.setTag(R.id.underlying_cm_data,view.findViewById(R.id.underlying_cm_data));
        }
        OiSpurtsContractsModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        ltp_data=(TextView) view.getTag(R.id.ltp_data);
        date_1=(TextView) view.getTag(R.id.date_1);
        date_1_data=(TextView) view.getTag(R.id.date_1_data);
        date_2=(TextView) view.getTag(R.id.date_2);
        data_2_data=(TextView) view.getTag(R.id.data_2_data);
        change_in_oi=(TextView) view.getTag(R.id.change_in_oi);
        change_in_ltp=(TextView) view.getTag(R.id.change_in_ltp);
        expiry_data=(TextView) view.getTag(R.id.expiry_data);
        strike_price_data=(TextView) view.getTag(R.id.strike_price_data);
        type_data=(TextView) view.getTag(R.id.type_data);
        prev_close_data=(TextView) view.getTag(R.id.prev_close_data);
        volume_contract_data=(TextView) view.getTag(R.id.volume_contract_data);
        underlying_cm_data=(TextView) view.getTag(R.id.underlying_cm_data);


        symbol.setText(equityModel.getSymbol());
        ltp_data.setText(equityModel.getLtp());
        date_1.setText(this.date_1);
        date_1_data.setText(equityModel.getLatestOI());
        date_2.setText(this.date_2);
        data_2_data.setText(equityModel.getPreviousOI());
        change_in_oi.setText(equityModel.getOiChange());
        change_in_ltp.setText(equityModel.getPercLtpChange());
        expiry_data.setText(equityModel.getExpiry());
        strike_price_data.setText(equityModel.getStrike());
        type_data.setText(equityModel.getOptionType());
        prev_close_data.setText(equityModel.getPrevClose());
        volume_contract_data.setText(equityModel.getVolume());
        underlying_cm_data.setText(equityModel.getUnderlyValue());



        if(!equityModel.getOiChange().contains("-"))
        {
            change_in_oi.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            change_in_oi.setTextColor(Color.parseColor("#d50000"));
        }


        if(!equityModel.getPercLtpChange().contains("-"))
        {
            change_in_ltp.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            change_in_ltp.setTextColor(Color.parseColor("#d50000"));
        }


        return view;
    }
}

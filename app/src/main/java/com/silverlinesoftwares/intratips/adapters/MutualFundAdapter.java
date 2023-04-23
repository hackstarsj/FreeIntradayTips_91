package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.MutualFundModel;

import java.util.List;

public class MutualFundAdapter extends BaseAdapter {

    final LayoutInflater inflater;
    final Context context;
    final List<MutualFundModel> DataList;

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

        TextView fund_name;
        TextView category;
        TextView aum_rs;
        TextView w1;
        TextView m1;
        TextView m3;
        TextView m6;
        TextView y1;
        TextView y3;
        TextView y5;

        if(view==null){
              view=inflater.inflate(R.layout.mutual_fund_row,parent,false);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.category_data,view.findViewById(R.id.category_data));
            view.setTag(R.id.aum_rs,view.findViewById(R.id.aum_rs));
            view.setTag(R.id.w1,view.findViewById(R.id.w1));
            view.setTag(R.id.m1,view.findViewById(R.id.m1));
            view.setTag(R.id.m3,view.findViewById(R.id.m3));
            view.setTag(R.id.m6,view.findViewById(R.id.m6));
            view.setTag(R.id.y1,view.findViewById(R.id.y1));
            view.setTag(R.id.y3,view.findViewById(R.id.y3));
            view.setTag(R.id.y5,view.findViewById(R.id.y5));

        }
        MutualFundModel equityModel=DataList.get(position);

        fund_name=(TextView) view.getTag(R.id.symbol);
        category=(TextView) view.getTag(R.id.category_data);
        aum_rs=(TextView) view.getTag(R.id.aum_rs);
        w1=(TextView) view.getTag(R.id.w1);
        m1=(TextView) view.getTag(R.id.m1);
        m3=(TextView) view.getTag(R.id.m3);
        m6=(TextView) view.getTag(R.id.m6);
        y1=(TextView) view.getTag(R.id.y1);
        y3=(TextView) view.getTag(R.id.y3);
        y5=(TextView) view.getTag(R.id.y5);

        fund_name.setText(equityModel.getFund_name());
        category.setText(equityModel.getCategory());
        aum_rs.setText(equityModel.getAum());
        w1.setText(equityModel.getChange_1w());
        m1.setText(equityModel.getChange_1m());
        m3.setText(equityModel.getChange_3m());
        m6.setText(equityModel.getChange_6m());
        y1.setText(equityModel.getChange_1y());
        y3.setText(equityModel.getChange_3y());
        y5.setText(equityModel.getChange_5y());


        if(!equityModel.getChange_1w().contains("-")){
            w1.setTextColor(Color.parseColor("#4caf50"));
            w1.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            w1.setTextColor(Color.parseColor("#d50000"));
            w1.setTextColor(Color.parseColor("#d50000"));
        }

        if(!equityModel.getChange_1m().contains("-")){
            m1.setTextColor(Color.parseColor("#4caf50"));
            m1.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            m1.setTextColor(Color.parseColor("#d50000"));
            m1.setTextColor(Color.parseColor("#d50000"));
        }

        if(!equityModel.getChange_3m().contains("-")){
            m3.setTextColor(Color.parseColor("#4caf50"));
            m3.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            m3.setTextColor(Color.parseColor("#d50000"));
            m3.setTextColor(Color.parseColor("#d50000"));
        }


        if(!equityModel.getChange_6m().contains("-")){
            m6.setTextColor(Color.parseColor("#4caf50"));
            m6.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            m6.setTextColor(Color.parseColor("#d50000"));
            m6.setTextColor(Color.parseColor("#d50000"));
        }

        if(!equityModel.getChange_1y().contains("-")){
            y1.setTextColor(Color.parseColor("#4caf50"));
            y1.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            y1.setTextColor(Color.parseColor("#d50000"));
            y1.setTextColor(Color.parseColor("#d50000"));
        }


        if(!equityModel.getChange_3y().contains("-")){
            y3.setTextColor(Color.parseColor("#4caf50"));
            y3.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            y3.setTextColor(Color.parseColor("#d50000"));
            y3.setTextColor(Color.parseColor("#d50000"));
        }



        if(!equityModel.getChange_5y().contains("-")){
            y5.setTextColor(Color.parseColor("#4caf50"));
            y5.setTextColor(Color.parseColor("#4caf50"));
        }
        else{
            y5.setTextColor(Color.parseColor("#d50000"));
            y5.setTextColor(Color.parseColor("#d50000"));
        }


        return view;
    }
}

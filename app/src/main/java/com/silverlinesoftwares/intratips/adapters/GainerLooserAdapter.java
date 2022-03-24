package com.silverlinesoftwares.intratips.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.GainerLosserModel;

import java.util.List;

public class GainerLooserAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<GainerLosserModel> DataList;

    public GainerLooserAdapter(Context context, List<GainerLosserModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<GainerLosserModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public GainerLosserModel getItem(int position) {
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
        TextView open;
        TextView close;
        TextView high;
        TextView low;
        TextView change;
        ImageView indicator;
        TextView  percentage_text;

        if(view==null){
              view=inflater.inflate(R.layout.gain_loss_row,parent,false);
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.open_text,view.findViewById(R.id.open_text));
            view.setTag(R.id.close_text,view.findViewById(R.id.close_text));
            view.setTag(R.id.low_text,view.findViewById(R.id.low_text));
            view.setTag(R.id.high_text,view.findViewById(R.id.high_text));
            view.setTag(R.id.change,view.findViewById(R.id.change));
            view.setTag(R.id.indicator,view.findViewById(R.id.indicator));
            view.setTag(R.id.percentage_text,view.findViewById(R.id.percentage_text));
            view.setTag(R.id.ltp,view.findViewById(R.id.ltp));
        }
        GainerLosserModel equityModel=DataList.get(position);

        symbol=(TextView) view.getTag(R.id.symbol);
        ltp=(TextView) view.getTag(R.id.ltp);
        open=(TextView) view.getTag(R.id.open_text);
        close=(TextView) view.getTag(R.id.close_text);
        percentage_text=(TextView) view.getTag(R.id.percentage_text);
        high=(TextView) view.getTag(R.id.high_text);
        low=(TextView) view.getTag(R.id.low_text);
        change=(TextView) view.getTag(R.id.change);
        indicator=(ImageView) view.getTag(R.id.indicator);

        symbol.setText(equityModel.getSymbol());
        ltp.setText(equityModel.getLtP());
        open.setText(equityModel.getOpen());
        close.setText(equityModel.getPreviousClose());
        high.setText(equityModel.getHigh());
        low.setText(equityModel.getLow());
        change.setText(equityModel.getIislPtsChange());
        percentage_text.setText("% "+equityModel.getPer());

        double vals=0;
        if(equityModel.getLtP()!=null && equityModel.getPreviousClose()!=null) {
            vals = Double.parseDouble(equityModel.getLtP().replaceAll(",", "")) - Double.parseDouble(equityModel.getPreviousClose().replaceAll(",", ""));
        }
        if(vals>=0){
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

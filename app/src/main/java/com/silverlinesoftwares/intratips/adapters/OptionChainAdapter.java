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
import com.silverlinesoftwares.intratips.models.GainerLosserModel;
import com.silverlinesoftwares.intratips.models.OptionChainModel;

import java.util.List;

public class OptionChainAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<OptionChainModel> DataList;

    public OptionChainAdapter(Context context, List<OptionChainModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<OptionChainModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public OptionChainModel getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        TextView oi_1;
        TextView chnage_in_oi_1;
        TextView volume_1;
        TextView iv_1;
        TextView ltp_1;
        TextView net_chnage_1;

        TextView oi_2;
        TextView chnage_in_oi_2;
        TextView volume_2;
        TextView iv_2;
        TextView ltp_2;
        TextView net_chnage_2;

        TextView strike_price;


        if(view==null){
              view=inflater.inflate(R.layout.option_chain_row,null);
            view.setTag(R.id.oi_1,view.findViewById(R.id.oi_1));
            view.setTag(R.id.change_oi_1,view.findViewById(R.id.change_oi_1));
            view.setTag(R.id.oi_2,view.findViewById(R.id.oi_2));
            view.setTag(R.id.change_oi_2,view.findViewById(R.id.change_oi_2));
            view.setTag(R.id.volume_1,view.findViewById(R.id.volume_1));
            view.setTag(R.id.volume_2,view.findViewById(R.id.volume_2));
            view.setTag(R.id.iv_1,view.findViewById(R.id.iv_1));
            view.setTag(R.id.iv_2,view.findViewById(R.id.iv_2));
            view.setTag(R.id.current_price_1,view.findViewById(R.id.current_price_1));
            view.setTag(R.id.current_price_2,view.findViewById(R.id.current_price_2));
            view.setTag(R.id.percent_1,view.findViewById(R.id.percent_1));
            view.setTag(R.id.percent_2,view.findViewById(R.id.percent_2));
            view.setTag(R.id.strike_price,view.findViewById(R.id.strike_price));
        }
        OptionChainModel equityModel=DataList.get(position);


        strike_price=(TextView)view.getTag(R.id.strike_price);
        oi_1=(TextView) view.getTag(R.id.oi_1);
        oi_2=(TextView) view.getTag(R.id.oi_2);
        chnage_in_oi_1=(TextView) view.getTag(R.id.change_oi_1);
        chnage_in_oi_2=(TextView) view.getTag(R.id.change_oi_2);
        volume_1=(TextView) view.getTag(R.id.volume_1);
        volume_2=(TextView) view.getTag(R.id.volume_2);
        iv_1=(TextView) view.getTag(R.id.iv_1);
        iv_2=(TextView) view.getTag(R.id.iv_2);
        ltp_1=(TextView) view.getTag(R.id.current_price_1);
        ltp_2=(TextView)view.getTag(R.id.current_price_2);
        net_chnage_1=(TextView)view.getTag(R.id.percent_1);
        net_chnage_2=(TextView)view.getTag(R.id.percent_2);

        oi_1.setText(equityModel.getOi_1());
        oi_2.setText(equityModel.getOi_2());
        chnage_in_oi_1.setText(equityModel.getChng_in_oi_1());
        chnage_in_oi_2.setText(equityModel.getChng_in_oi_2());
        volume_1.setText(equityModel.getVolume_1());
        volume_2.setText(equityModel.getVolume_2());
        iv_1.setText(equityModel.getIv_1());
        iv_2.setText(equityModel.getIv_2());
        ltp_1.setText(equityModel.getLtp_1());
        ltp_2.setText(equityModel.getLtp_2());

        if(equityModel.getNet_chng_1().equalsIgnoreCase("-")){
            net_chnage_1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else if(equityModel.getNet_chng_1().contains("-")){
            net_chnage_1.setBackgroundColor(Color.parseColor("#d50000"));
        }
        else{
            net_chnage_1.setBackgroundColor(Color.parseColor("#4caf50"));
        }

        if(equityModel.getNet_chng_2().equalsIgnoreCase("-")){
            net_chnage_2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else if(equityModel.getNet_chng_2().contains("-")){
            net_chnage_2.setBackgroundColor(Color.parseColor("#d50000"));
        }
        else{
            net_chnage_2.setBackgroundColor(Color.parseColor("#4caf50"));
        }
        net_chnage_1.setText(equityModel.getNet_chng_1()+" %");
        net_chnage_2.setText(equityModel.getNet_chng_2()+" %");
        strike_price.setText(equityModel.getStrike_price());
        return view;
    }
}

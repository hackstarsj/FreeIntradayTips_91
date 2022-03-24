package com.silverlinesoftwares.intratips.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.MoverLooserMenu;

import java.util.List;
import java.util.Random;

public class MoverMenuAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<MoverLooserMenu> DataList;
    String[] colors={"#d50000","#c51162","#aa00ff","#6200ea","#304ffe","#2962ff","#0091ea","#006064","#009688","#2e7d32","#558b2f","#827717","#e65100","#ff3d00","#5d4037","#455a64"};

    public MoverMenuAdapter(Context context, List<MoverLooserMenu> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<MoverLooserMenu> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public MoverLooserMenu getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        TextView mover_text;
        TextView mover_text_data;
        CardView card_item;


        if(view==null){
              view=inflater.inflate(R.layout.row_gainer_losser_menu,parent,false);
            view.setTag(R.id.mover_text,view.findViewById(R.id.mover_text));
            view.setTag(R.id.mover_text_data,view.findViewById(R.id.mover_text_data));
            view.setTag(R.id.item_mover,view.findViewById(R.id.item_mover));

        }
        MoverLooserMenu equityModel=DataList.get(position);

        mover_text=(TextView) view.getTag(R.id.mover_text);
        mover_text_data=(TextView) view.getTag(R.id.mover_text_data);
        card_item=(CardView) view.getTag(R.id.item_mover);

        mover_text.setText(equityModel.getName());
        mover_text_data.setText(equityModel.getData_text());
        Random rn=new Random();
        int no=0 + rn.nextInt((colors.length-1) - 0 + 1);
        card_item.setCardBackgroundColor(Color.parseColor(colors[no]));


        return view;
    }
}

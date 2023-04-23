package com.silverlinesoftwares.intratips.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.ChartWebActivity;
import com.silverlinesoftwares.intratips.activity.StockDetailsActivity;
import com.silverlinesoftwares.intratips.listeners.BuySellClickListener;
import com.silverlinesoftwares.intratips.models.EquityModel;
import com.silverlinesoftwares.intratips.util.Constant;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class EquityAdapter extends BaseAdapter {

    final LayoutInflater inflater;
    final Activity context;
    final List<EquityModel> DataList;
    final BuySellClickListener buySellClickListener;

    public EquityAdapter(Activity context, List<EquityModel> equityModels,BuySellClickListener buySellClickListener){
        this.DataList=equityModels;
        this.buySellClickListener=buySellClickListener;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<EquityModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public EquityModel getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        RelativeLayout rel_line;
        Button buy_btn;
        Button sell_btn;
        Button charts;
        Button fundamental;
        Button technical;
        LinearLayout line_buy_sell;
        TextView equity_title;
        TextView equity_datetime;
        TextView equity_time;
        TextView equity_price;
        TextView text_target_1;
        TextView text_target_2;
        TextView text_target_3;
        TextView equity_buy;
        TextView text_buy_1;
        TextView text_buy_2;
        TextView text_buy_3;
        TextView text_achieved_1;
        TextView text_achieved_2;
        TextView text_achieved_3;
        ImageView ticker;
        TextView equity_loss;
        TextView equity_loss_text;
        TextView stoploss;
        TextView buy_text,opens;
        LinearLayout buy_line,linesss;

        TextView high,low,pres;

        if(view==null){
              view=inflater.inflate(R.layout.equity_row,parent,false);
             view.setTag(R.id.line_buy_sell,view.findViewById(R.id.line_buy_sell));
             view.setTag(R.id.buy,view.findViewById(R.id.buy));
            view.setTag(R.id.sell,view.findViewById(R.id.sell));
            view.setTag(R.id.chart,view.findViewById(R.id.chart));
            view.setTag(R.id.fundamental,view.findViewById(R.id.fundamental));
            view.setTag(R.id.rel_line,view.findViewById(R.id.rel_line));
            view.setTag(R.id.equity_title,view.findViewById(R.id.equity_title));
            view.setTag(R.id.equity_datetime,view.findViewById(R.id.equity_datetime));
            view.setTag(R.id.equity_time,view.findViewById(R.id.equity_time));
            view.setTag(R.id.equity_price,view.findViewById(R.id.equity_price));
            view.setTag(R.id.text_target_1,view.findViewById(R.id.text_target_1));
            view.setTag(R.id.text_target_2,view.findViewById(R.id.text_target_2));
            view.setTag(R.id.text_target_3,view.findViewById(R.id.text_target_3));
            view.setTag(R.id.equity_buy,view.findViewById(R.id.equity_buy));
            view.setTag(R.id.text_buy_1,view.findViewById(R.id.text_buy_1));
            view.setTag(R.id.text_buy_2,view.findViewById(R.id.text_buy_2));
            view.setTag(R.id.text_buy_3,view.findViewById(R.id.text_buy_3));
            view.setTag(R.id.text_achieved_1,view.findViewById(R.id.text_achieved_1));
            view.setTag(R.id.text_achieved_2,view.findViewById(R.id.text_achieved_2));
            view.setTag(R.id.text_achieved_3,view.findViewById(R.id.text_achieved_3));
            view.setTag(R.id.equity_loss,view.findViewById(R.id.equity_loss));
            view.setTag(R.id.equity_loss_text,view.findViewById(R.id.equity_loss_text));
            view.setTag(R.id.stop_loss,view.findViewById(R.id.stop_loss));
            view.setTag(R.id.pr_close,view.findViewById(R.id.pr_close));
            view.setTag(R.id.low,view.findViewById(R.id.low));
            view.setTag(R.id.high,view.findViewById(R.id.high));
            view.setTag(R.id.buy_text,view.findViewById(R.id.buy_text));
            view.setTag(R.id.line_buy,view.findViewById(R.id.line_buy));
            view.setTag(R.id.linesss,view.findViewById(R.id.linesss));
            view.setTag(R.id.opens,view.findViewById(R.id.opens));
            view.setTag(R.id.ticker,view.findViewById(R.id.ticker));
            view.setTag(R.id.technical,view.findViewById(R.id.technical));
        }
        final EquityModel equityModel=DataList.get(position);
        equity_title=(TextView) view.getTag(R.id.equity_title);
        equity_datetime=(TextView) view.getTag(R.id.equity_datetime);
        equity_time=(TextView) view.getTag(R.id.equity_time);
        equity_price=(TextView) view.getTag(R.id.equity_price);
        text_target_1=(TextView) view.getTag(R.id.text_target_1);
        text_target_2=(TextView) view.getTag(R.id.text_target_2);
        text_target_3=(TextView) view.getTag(R.id.text_target_3);
        equity_buy=(TextView) view.getTag(R.id.equity_buy);
        text_buy_1=(TextView) view.getTag(R.id.text_buy_1);

        line_buy_sell=(LinearLayout)view.getTag(R.id.line_buy_sell);
        rel_line=(RelativeLayout)view.getTag(R.id.rel_line);
        buy_btn=(Button) view.getTag(R.id.buy);
        sell_btn=(Button) view.getTag(R.id.sell);
        charts=(Button) view.getTag(R.id.chart);
        fundamental=(Button) view.getTag(R.id.fundamental);
        technical=(Button) view.getTag(R.id.technical);

        text_buy_2=(TextView) view.getTag(R.id.text_buy_2);
        text_buy_3=(TextView) view.getTag(R.id.text_buy_3);
        text_achieved_1=(TextView) view.getTag(R.id.text_achieved_1);
        text_achieved_2=(TextView) view.getTag(R.id.text_achieved_2);
        text_achieved_3=(TextView) view.getTag(R.id.text_achieved_3);
        equity_loss=(TextView) view.getTag(R.id.equity_loss);
        equity_loss_text=(TextView) view.getTag(R.id.equity_loss_text);
        pres=(TextView) view.getTag(R.id.pr_close);
        high=(TextView) view.getTag(R.id.high);
        low=(TextView) view.getTag(R.id.low);
        stoploss=(TextView) view.getTag(R.id.stop_loss);
        buy_text=(TextView) view.getTag(R.id.buy_text);
        buy_line=(LinearLayout) view.getTag(R.id.line_buy);
        linesss=(LinearLayout) view.getTag(R.id.linesss);
        opens= view.findViewById(R.id.opens);
        ticker= view.findViewById(R.id.ticker);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

        equity_title.setText(equityModel.getName());
        equity_datetime.setText(equityModel.getDatetime());
        equity_time.setText(equityModel.getCmp_datetime());
        equity_price.setText(equityModel.getCmp_price());
        text_target_1.setText(equityModel.getTarget1());
        text_target_2.setText(equityModel.getTarget2());
        text_target_3.setText(equityModel.getTarget3());
        equity_buy.setText(String.format("%s", equityModel.getBuy_price()));
        text_buy_1.setText(String.format("%s", formatter.format(Double.parseDouble(equityModel.getBuy1()))));
        text_buy_2.setText(String.format("%s", formatter.format(Double.parseDouble(equityModel.getBuy2()))));
        text_buy_3.setText(String.format("%s", formatter.format(Double.parseDouble(equityModel.getBuy3()))));
        text_achieved_1.setText(equityModel.getAchieved1());
        text_achieved_2.setText(equityModel.getAchieved2());
        text_achieved_3.setText(equityModel.getAchieved3());
        equity_loss.setText(String.format("%s", formatter.format(Double.parseDouble(equityModel.getStop_loss()))));
        stoploss.setText(equityModel.getStop_loss_text());
        equity_loss_text.setText(equityModel.getStop_loss_end());
        if(equityModel.getStop_loss_end().equalsIgnoreCase("LOSS")){
            equity_loss_text.setBackgroundColor(Color.parseColor("#f44336"));
        }
        else if(equityModel.getStop_loss_end().equalsIgnoreCase("EXIT")){
            equity_loss_text.setBackgroundColor(Color.parseColor("#4caf50"));
        }
        else{
            equity_loss_text.setBackgroundColor(Color.parseColor("#BE1AFA"));
        }
        pres.setText(equityModel.getPr_close());
        high.setText(equityModel.getHigh());
        low.setText(equityModel.getLow());
        if(equityModel.getBuy_text()!=null){
           buy_text.setText(equityModel.getBuy_text());
           if(equityModel.getBuy_text().contains("SELL")){
               linesss.setBackgroundColor(Color.parseColor("#e53935"));
           }
           else{
               linesss.setBackgroundColor(Color.parseColor("#019c0e"));
           }
        }
        else {
            buy_text.setText("");
        }

        buy_btn.setOnClickListener(v -> buySellClickListener.onBuy(equityModel.getSymbol(),equityModel.getPrice()));

        sell_btn.setOnClickListener(v -> buySellClickListener.onSell(equityModel.getSymbol(),equityModel.getPrice()));

        technical.setOnClickListener(v -> buySellClickListener.onTechnical(equityModel.getSymbol()));

        low.setText(equityModel.getLow());
        low.setText(equityModel.getLow());

        if(equityModel.getAchieved1().equalsIgnoreCase("Done")){
            text_achieved_1.setBackgroundColor(Color.parseColor("#019c0e"));
            text_achieved_1.setTextColor(Color.parseColor("#ffffff"));
        }
        else {
            text_achieved_1.setBackgroundColor(Color.parseColor("#ffffff"));
            text_achieved_1.setTextColor(Color.parseColor("#000000"));
        }
        if(equityModel.getAchieved2().equalsIgnoreCase("Done")){
            text_achieved_2.setBackgroundColor(Color.parseColor("#019c0e"));
            text_achieved_2.setTextColor(Color.parseColor("#ffffff"));

        }
        else {
            text_achieved_2.setBackgroundColor(Color.parseColor("#ffffff"));
            text_achieved_2.setTextColor(Color.parseColor("#000000"));
        }
        if(equityModel.getAchieved3().equalsIgnoreCase("Done")){
            text_achieved_3.setBackgroundColor(Color.parseColor("#019c0e"));
            text_achieved_3.setTextColor(Color.parseColor("#ffffff"));

        }else {
            text_achieved_3.setBackgroundColor(Color.parseColor("#ffffff"));
            text_achieved_3.setTextColor(Color.parseColor("#000000"));
        }

        if(equityModel.getBuy1().isEmpty() || equityModel.getBuy1().equalsIgnoreCase("")){
            text_buy_1.setText("-");
        }
        if(equityModel.getBuy2().isEmpty() || equityModel.getBuy2().equalsIgnoreCase("")){
            text_buy_2.setText("-");
        }
        if(equityModel.getBuy3().isEmpty() || equityModel.getBuy3().equalsIgnoreCase("")){
            text_buy_3.setText("-");
        }

        if(equityModel.getHigh().isEmpty()){
            high.setText("-");
        }
        if(equityModel.getLow().isEmpty()){
            low.setText("-");
        }
        if(equityModel.getPr_close().isEmpty()){
            pres.setText("-");
        }
        if(equityModel.getCmp_price().isEmpty()){
            equity_price.setText("-");
        }


        if(!equityModel.getOpens().isEmpty()){
            opens.setText(equityModel.getOpens());
        }
        else{
            opens.setText("-");
        }

        if(equityModel.isIs_Open()){
            line_buy_sell.setVisibility(View.VISIBLE);
        }


        charts.setOnClickListener(v -> context.startActivity(new Intent(context, ChartWebActivity.class).putExtra("url","https://in.tradingview.com/chart/?symbol=NSE%3A"+equityModel.getSymbol().replace(".NS","").replace(".BS",""))));

        fundamental.setOnClickListener(v -> context.startActivity(new Intent(context, StockDetailsActivity.class).putExtra(Constant.search,equityModel.getSymbol())));

        rel_line.setOnClickListener(v -> {
            equityModel.setIs_Open(!equityModel.isIs_Open());
            notifyDataSetChanged();
        });

        if(equityModel.getOldprice()!=null){
            if(!equityModel.getOldprice().isEmpty()) {
                if (equityModel.getCmp_price() != null) {
                    if (!equityModel.getCmp_price().isEmpty()) {
                        if (Double.parseDouble(equityModel.getCmp_price()) > Double.parseDouble(equityModel.getOldprice())) {
                            ticker.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                            equity_price.setBackgroundColor(Color.parseColor("#08C82D"));
                            equity_price.setTextColor(Color.parseColor("#FFFFFF"));
                        } else {
                            equity_price.setBackgroundColor(Color.parseColor("#EF1003"));
                            ticker.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                            equity_price.setTextColor(Color.parseColor("#FFFFFF"));
                        }
                    }
                }
            }

        }
        else{
            equity_price.setBackgroundColor(Color.parseColor("#08C82D"));
            ticker.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
            equity_price.setTextColor(Color.parseColor("#FFFFFF"));
        }
        DataList.get(position).setOldprice(DataList.get(position).getCmp_price());

        return view;
    }
}

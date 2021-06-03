package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.ResultModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ResultOptionAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<ResultModel> DataList;

    public ResultOptionAdapter(Context context, List<ResultModel> equityModels){
        this.DataList=equityModels;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public List<ResultModel> getDataList() {
        return DataList;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public ResultModel getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        TextView date_time;
        TextView qty;
        TextView sell_buy;
        TextView symbol;
        TextView price_range;
        TextView price_avg;
        TextView text_target_1;
        TextView text_target_2;
        TextView text_target_3;
        TextView text_buy_1;
        TextView text_buy_2;
        TextView text_buy_3;
        TextView text_achieved_1;
        TextView text_achieved_2;
        TextView text_achieved_3;
        TextView text_profit_1;
        TextView text_profit_2;
        TextView text_profit3;
        TextView stop_loss_text;
        TextView stop_loss_value;
        TextView amount_profit_loss;
        LinearLayout profit_loss_line;
        TextView profit_or_loss_text;
        TextView final_value;


        if(view==null){
              view=inflater.inflate(R.layout.row_result,null);

            view.setTag(R.id.date_time,view.findViewById(R.id.date_time));
            view.setTag(R.id.qty,view.findViewById(R.id.qty));
            view.setTag(R.id.sell_buy,view.findViewById(R.id.sell_buy));
            view.setTag(R.id.symbol,view.findViewById(R.id.symbol));
            view.setTag(R.id.price_range,view.findViewById(R.id.price_range));
            view.setTag(R.id.price_avg,view.findViewById(R.id.price_avg));
            view.setTag(R.id.text_target_1,view.findViewById(R.id.text_target_1));
            view.setTag(R.id.text_target_2,view.findViewById(R.id.text_target_2));
            view.setTag(R.id.text_target_3,view.findViewById(R.id.text_target_3));
            view.setTag(R.id.text_buy_1,view.findViewById(R.id.text_buy_1));
            view.setTag(R.id.text_buy_2,view.findViewById(R.id.text_buy_2));
            view.setTag(R.id.text_buy_3,view.findViewById(R.id.text_buy_3));
            view.setTag(R.id.text_achieved_1,view.findViewById(R.id.text_achieved_1));
            view.setTag(R.id.text_achieved_2,view.findViewById(R.id.text_achieved_2));
            view.setTag(R.id.text_achieved_3,view.findViewById(R.id.text_achieved_3));
            view.setTag(R.id.text_profit_1,view.findViewById(R.id.text_profit_1));
            view.setTag(R.id.text_profit_2,view.findViewById(R.id.text_profit_2));
            view.setTag(R.id.text_profit3,view.findViewById(R.id.text_profit3));
            view.setTag(R.id.stop_loss_text,view.findViewById(R.id.stop_loss_text));
            view.setTag(R.id.stop_loss_value,view.findViewById(R.id.stop_loss_value));
            view.setTag(R.id.amount_profit_loss,view.findViewById(R.id.amount_profit_loss));
            view.setTag(R.id.profit_loss_line,view.findViewById(R.id.profit_loss_line));
            view.setTag(R.id.profit_or_loss_text,view.findViewById(R.id.profit_or_loss_text));
            view.setTag(R.id.final_value,view.findViewById(R.id.final_value));
        }
        ResultModel equityModel=DataList.get(position);

        date_time=(TextView)view.getTag(R.id.date_time);
        qty=(TextView)view.getTag(R.id.qty);
        sell_buy=(TextView)view.getTag(R.id.sell_buy);
        symbol=(TextView)view.getTag(R.id.symbol);
        price_range=(TextView)view.getTag(R.id.price_range);
        price_avg=(TextView)view.getTag(R.id.price_avg);
        text_target_1=(TextView)view.getTag(R.id.text_target_1);
        text_target_2=(TextView)view.getTag(R.id.text_target_2);
        text_target_3=(TextView)view.getTag(R.id.text_target_3);
        text_buy_1=(TextView)view.getTag(R.id.text_buy_1);
        text_buy_2=(TextView)view.getTag(R.id.text_buy_2);
        text_buy_3=(TextView)view.getTag(R.id.text_buy_3);
        text_achieved_1=(TextView)view.getTag(R.id.text_achieved_1);
        text_achieved_2=(TextView)view.getTag(R.id.text_achieved_2);
        text_achieved_3=(TextView)view.getTag(R.id.text_achieved_3);
        text_profit_1=(TextView)view.getTag(R.id.text_profit_1);
        text_profit_2=(TextView)view.getTag(R.id.text_profit_2);
        text_profit3=(TextView)view.getTag(R.id.text_profit3);
        stop_loss_text=(TextView)view.getTag(R.id.stop_loss_text);
        stop_loss_value=(TextView)view.getTag(R.id.stop_loss_value);
        amount_profit_loss=(TextView)view.getTag(R.id.amount_profit_loss);
        profit_loss_line=(LinearLayout) view.getTag(R.id.profit_loss_line);
        profit_or_loss_text=(TextView)view.getTag(R.id.profit_or_loss_text);
        final_value=(TextView)view.getTag(R.id.final_value);


        int amount=100000;
        String[] price_Range = equityModel.getBuy_price().split("-");
        double amt_1=Double.parseDouble(price_Range[0]);
        double amt_2=Double.parseDouble(price_Range[1]);
        double avg_amt=(amt_1+amt_2)/2;

        double profit_1=0.0;
        double profit_2=0.0;
        double profit_3=0.0;
        int quantity= (int) (amount/avg_amt);


        if(equityModel.getBuy_text().contains("SELL")){
                profit_1=avg_amt-Double.parseDouble(equityModel.getBuy1());
              profit_2=avg_amt-Double.parseDouble(equityModel.getBuy2());
            profit_3=avg_amt-Double.parseDouble(equityModel.getBuy3());
        }
        else{
            profit_1=Double.parseDouble(equityModel.getBuy1())-avg_amt;
            profit_2=Double.parseDouble(equityModel.getBuy2())-avg_amt;
            profit_3=Double.parseDouble(equityModel.getBuy3())-avg_amt;
        }


        if(equityModel.getBuy_text().contains("SELL")){
            sell_buy.setBackgroundColor(Color.parseColor("#F44336"));
        }
        else{
            sell_buy.setBackgroundColor(Color.parseColor("#4CAF50"));
        }

//        double qtys=amount/
        date_time.setText(equityModel.getDatetime());
        qty.setText("Qty : "+quantity);
        sell_buy.setText(equityModel.getBuy_text());
        symbol.setText(equityModel.getName());
        price_range.setText(equityModel.getBuy_price());
        text_target_1.setText(equityModel.getTarget1());
        text_target_2.setText(equityModel.getTarget2());
        text_target_3.setText(equityModel.getTarget3());

        if(!equityModel.getAchieved1().contains("Done")){
            text_achieved_1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            text_achieved_1.setTextColor(Color.parseColor("#000000"));
        }
        else{
            text_achieved_1.setBackgroundColor(Color.parseColor("#4CAF50"));
            text_achieved_1.setTextColor(Color.parseColor("#FFFFFF"));

        }

        if(!equityModel.getAchieved2().contains("Done")){
            text_achieved_2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            text_achieved_2.setTextColor(Color.parseColor("#000000"));
        }
        else{
            text_achieved_2.setBackgroundColor(Color.parseColor("#4CAF50"));
            text_achieved_2.setTextColor(Color.parseColor("#FFFFFF"));

        }

        if(!equityModel.getAchieved3().contains("Done")){
            text_achieved_3.setTextColor(Color.parseColor("#000000"));
            text_achieved_3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else{
            text_achieved_3.setBackgroundColor(Color.parseColor("#4CAF50"));
            text_achieved_3.setTextColor(Color.parseColor("#FFFFFF"));
        }

        double only_profit=0.0;
        boolean is_loss=false;

        if(equityModel.getStop_loss_end().contains("LOSS")){
            if(equityModel.getBuy_text().contains("SELL")) {
                only_profit =quantity* (Double.parseDouble(equityModel.getStop_loss())-avg_amt);
                is_loss=true;
            }
            else{
                only_profit=quantity* (avg_amt-Double.parseDouble(equityModel.getStop_loss()));
                is_loss=true;
            }
        }
        else{
            is_loss=false;
            if(equityModel.getAchieved3().contains("Done")){
                if(equityModel.getBuy_text().contains("SELL")) {
                    only_profit =quantity* (avg_amt-Double.parseDouble(equityModel.getBuy3()));
                  ////  is_loss=true;
                }
                else{
                    only_profit=quantity* (Double.parseDouble(equityModel.getBuy3())-avg_amt);
                  //  is_loss=true;
                }

            }
            else if(equityModel.getAchieved2().contains("Done")){
                if(equityModel.getBuy_text().contains("SELL")) {
                    only_profit =quantity* (avg_amt-Double.parseDouble(equityModel.getBuy2()));
                  //  is_loss=true;
                }
                else{
                    only_profit=quantity* (Double.parseDouble(equityModel.getBuy2())-avg_amt);
                  //  is_loss=true;
                }
            }
            else if(equityModel.getAchieved1().contains("Done")){
                if(equityModel.getBuy_text().contains("SELL")) {
                    only_profit =quantity* (avg_amt-Double.parseDouble(equityModel.getBuy1()));
                   //// is_loss=true;
                }
                else{
                    only_profit=quantity* (Double.parseDouble(equityModel.getBuy1())-avg_amt);
                 //   is_loss=true;
                }
            }
            else{
                only_profit=0.0;
            }
        }

        if(equityModel.getStop_loss_end().equalsIgnoreCase("EXIT")){
            profit_or_loss_text.setText("EXIT");
            profit_loss_line.setBackgroundColor(Color.parseColor("#0091ea"));
        }
        else if(is_loss){
            profit_or_loss_text.setText("LOSS");
            profit_loss_line.setBackgroundColor(Color.parseColor("#F44336"));
        }
        else{
            profit_or_loss_text.setText("PROFIT");
            profit_loss_line.setBackgroundColor(Color.parseColor("#4CAF50"));
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

        text_achieved_1.setText(equityModel.getAchieved1());
        text_achieved_2.setText(equityModel.getAchieved2());
        price_avg.setText(""+formatter.format(avg_amt));
        text_achieved_3.setText(equityModel.getAchieved3());
        text_profit_1.setText(""+formatter.format(profit_1));
        text_profit_2.setText(""+formatter.format(profit_2));
        text_profit3.setText(""+formatter.format(profit_3));
        text_buy_1.setText(""+formatter.format(Double.parseDouble(equityModel.getBuy1())));
        text_buy_2.setText(""+formatter.format(Double.parseDouble(equityModel.getBuy2())));
        text_buy_3.setText(""+formatter.format(Double.parseDouble(equityModel.getBuy3())));
        stop_loss_value.setText(""+formatter.format(Double.parseDouble(equityModel.getStop_loss())));

        stop_loss_text.setText(equityModel.getStop_loss_text());
        if(is_loss) {
            amount_profit_loss.setText("- " + formatter.format(only_profit));
        }
        else{
            amount_profit_loss.setText("+ "+ formatter.format(only_profit));
        }
        if(is_loss) {
            double final_amt=amount-only_profit;
            final_value.setText(""+ formatter.format(final_amt));
        }
        else{
            double final_amt=amount+only_profit;
            final_value.setText(""+ formatter.format(final_amt));
        }


        return view;
    }
}

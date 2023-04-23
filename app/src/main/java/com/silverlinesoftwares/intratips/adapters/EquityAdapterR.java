package com.silverlinesoftwares.intratips.adapters;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.ChartWebActivity;
import com.silverlinesoftwares.intratips.activity.StockDetailsActivity;
import com.silverlinesoftwares.intratips.listeners.AccountOpenClick;
import com.silverlinesoftwares.intratips.listeners.BuySellClickListener;
import com.silverlinesoftwares.intratips.models.BannerModel;
import com.silverlinesoftwares.intratips.models.EquityModel;
import com.silverlinesoftwares.intratips.util.Constant;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class EquityAdapterR extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int AD_TYPE = 1;
    private boolean isLoading;
    private final Activity activity;
    private final List<Object> contacts;
    private final int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private final int VIEW_TYPE_ITEM = 0;
    public final Activity context;
    private final int VIEW_TYPE_LOADING = 1;

    //private OnLoadMoreListener onLoadMoreListener;
    final BuySellClickListener buySellClickListener;
    final AccountOpenClick accountOpenClick;

    @SuppressLint("NotifyDataSetChanged")
    public void changePrice(String regularMarketPrice, int i) {
        if(contacts.get(i) instanceof EquityModel){
            Log.d("Price ","Change");
            ((EquityModel) contacts.get(i)).setCmp_price(regularMarketPrice);
            notifyDataSetChanged();
        }
    }

    private static class BannerViewHolder extends RecyclerView.ViewHolder {
        public final RelativeLayout image_pen;
        public final RelativeLayout alice_pen;
        public final RelativeLayout upstock;

        public BannerViewHolder(View view) {
            super(view);
            image_pen = view.findViewById(R.id.image_pen);
            alice_pen = view.findViewById(R.id.alice_pen);
            upstock = view.findViewById(R.id.upstock_pen);
        }
    }


    public EquityAdapterR(RecyclerView recyclerView, List<Object> contacts, Activity activity,BuySellClickListener buySellClickListener,Activity context,AccountOpenClick accountOpenClick) {
        this.contacts = contacts;
        this.context=context;
        this.activity = activity;
        this.accountOpenClick=accountOpenClick;
        this.buySellClickListener=buySellClickListener;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager != null) {
                    totalItemCount = linearLayoutManager.getItemCount();
                }
                if (linearLayoutManager != null) {
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                }
            }
        });
    }

    // "Normal item" ViewHolder
    private static class EquityAdapterRow extends RecyclerView.ViewHolder {

        public final RelativeLayout rel_line;
        public final Button buy_btn;
        public final Button sell_btn;
        public final Button charts;
        public final Button fundamental;
        public final Button technical;
        public final LinearLayout line_buy_sell;
        public final TextView equity_title;
        public final TextView equity_datetime;
        public final TextView equity_time;
        public final TextView equity_price;
        public final TextView text_target_1;
        public final TextView text_target_2;
        public final TextView text_target_3;
        public final TextView equity_buy;
        public final TextView text_buy_1;
        public final TextView text_buy_2;
        public final TextView text_buy_3;
        public final TextView text_achieved_1;
        public final TextView text_achieved_2;
        public final TextView text_achieved_3;
        public final ImageView ticker;
        public final TextView equity_loss;
        public final TextView equity_loss_text;
        public final TextView stoploss;
        public final TextView notification_message;
        public final TextView buy_text;
        public final TextView opens;
        public final LinearLayout buy_line;
        public final LinearLayout linesss;

        public final TextView high;
        public final TextView low;
        public final TextView pres;

        public EquityAdapterRow(View view) {
            super(view);
            equity_title= view.findViewById(R.id.equity_title);
            equity_datetime= view.findViewById(R.id.equity_datetime);
            equity_time= view.findViewById(R.id.equity_time);
            equity_price= view.findViewById(R.id.equity_price);
            text_target_1= view.findViewById(R.id.text_target_1);
            text_target_2= view.findViewById(R.id.text_target_2);
            text_target_3= view.findViewById(R.id.text_target_3);
            equity_buy= view.findViewById(R.id.equity_buy);
            text_buy_1= view.findViewById(R.id.text_buy_1);
            notification_message= view.findViewById(R.id.notification_text);

            line_buy_sell= view.findViewById(R.id.line_buy_sell);
            rel_line= view.findViewById(R.id.rel_line);
            buy_btn= view.findViewById(R.id.buy);
            sell_btn= view.findViewById(R.id.sell);
            charts= view.findViewById(R.id.chart);
            fundamental= view.findViewById(R.id.fundamental);
            technical= view.findViewById(R.id.technical);

            text_buy_2= view.findViewById(R.id.text_buy_2);
            text_buy_3= view.findViewById(R.id.text_buy_3);
            text_achieved_1= view.findViewById(R.id.text_achieved_1);
            text_achieved_2= view.findViewById(R.id.text_achieved_2);
            text_achieved_3= view.findViewById(R.id.text_achieved_3);
            equity_loss= view.findViewById(R.id.equity_loss);
            equity_loss_text= view.findViewById(R.id.equity_loss_text);
            pres= view.findViewById(R.id.pr_close);
            high= view.findViewById(R.id.high);
            low= view.findViewById(R.id.low);
            stoploss= view.findViewById(R.id.stop_loss);
            buy_text= view.findViewById(R.id.buy_text);
            buy_line= view.findViewById(R.id.line_buy);
            linesss= view.findViewById(R.id.linesss);
            opens= view.findViewById(R.id.opens);
            ticker= view.findViewById(R.id.ticker);

        }
    }


    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = contacts.get(position);
        if(recyclerViewItem instanceof BannerModel){
            return VIEW_TYPE_LOADING;
        }
        else{
            return VIEW_TYPE_ITEM;
        }
       // return contacts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.equity_row, parent, false);
            return new EquityAdapterRow(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.bottom_layout_open_demat, parent, false);
            return new BannerViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(activity).inflate(R.layout.bottom_layout_open_demat, parent, false);
            return new BannerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EquityAdapterRow) {
            final EquityModel contact = (EquityModel) contacts.get(position);
            EquityAdapterRow userViewHolder = (EquityAdapterRow) holder;
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

            userViewHolder.equity_title.setText(contact.getName());
            userViewHolder.equity_datetime.setText(contact.getDatetime());
            userViewHolder.equity_time.setText(contact.getCmp_datetime());
            userViewHolder.equity_price.setText(contact.getCmp_price());
            userViewHolder.text_target_1.setText(contact.getTarget1());
            userViewHolder.text_target_2.setText(contact.getTarget2());
            userViewHolder.text_target_3.setText(contact.getTarget3());
            userViewHolder.equity_buy.setText(String.format("%s", contact.getBuy_price()));
            userViewHolder.text_buy_1.setText(String.format("%s", formatter.format(Double.parseDouble(contact.getBuy1()))));
            userViewHolder.text_buy_2.setText(String.format("%s", formatter.format(Double.parseDouble(contact.getBuy2()))));
            userViewHolder.text_buy_3.setText(String.format("%s", formatter.format(Double.parseDouble(contact.getBuy3()))));
            userViewHolder.text_achieved_1.setText(contact.getAchieved1());
            userViewHolder.text_achieved_2.setText(contact.getAchieved2());
            userViewHolder.text_achieved_3.setText(contact.getAchieved3());
            userViewHolder.equity_loss.setText(String.format("%s", formatter.format(Double.parseDouble(contact.getStop_loss()))));
            userViewHolder.stoploss.setText(contact.getStop_loss_text());
            userViewHolder.equity_loss_text.setText(contact.getStop_loss_end());

            if(contact.getNotification_message()!=null) {
                if (!contact.getNotification_message().isEmpty()) {
                    userViewHolder.notification_message.setText(String.format("%s", contact.getNotification_message()));
                    userViewHolder.notification_message.setVisibility(View.VISIBLE);
                    if (contact.getBuy_text().contains("SELL")) {

                        userViewHolder.notification_message.setBackgroundColor(Color.parseColor("#e53935"));
                        manageBlinkEffect(userViewHolder.notification_message,Color.parseColor("#e53935"));
                    } else {
                        userViewHolder.notification_message.setBackgroundColor(Color.parseColor("#019c0e"));
                        manageBlinkEffect(userViewHolder.notification_message,Color.parseColor("#019c0e"));
                    }
                } else {
                    userViewHolder.notification_message.setVisibility(View.GONE);
                }
            }
            else{
                userViewHolder.notification_message.setVisibility(View.GONE);
            }

            if(contact.getStop_loss_end().equalsIgnoreCase("LOSS")){
                userViewHolder.equity_loss_text.setBackgroundColor(Color.parseColor("#f45854"));
            }
            else if(contact.getStop_loss_end().equalsIgnoreCase("EXIT")){
                userViewHolder.equity_loss_text.setBackgroundColor(Color.parseColor("#31ae36"));
            }
            else{
                userViewHolder.equity_loss_text.setBackgroundColor(Color.parseColor("#BE1AFA"));
            }
            userViewHolder.pres.setText(contact.getPr_close());
            userViewHolder.high.setText(contact.getHigh());
            userViewHolder.low.setText(contact.getLow());
            if(contact.getBuy_text()!=null){
                userViewHolder.buy_text.setText(contact.getBuy_text());
                if(contact.getBuy_text().contains("SELL")){
                    userViewHolder.linesss.setBackgroundColor(Color.parseColor("#e53935"));
                }
                else{
                    userViewHolder.linesss.setBackgroundColor(Color.parseColor("#019c0e"));
                }
            }
            else {
                userViewHolder.buy_text.setText("");
            }

            userViewHolder.buy_btn.setOnClickListener(v -> buySellClickListener.onBuy(contact.getSymbol(),contact.getPrice()));

            userViewHolder.sell_btn.setOnClickListener(v -> buySellClickListener.onSell(contact.getSymbol(),contact.getPrice()));

            userViewHolder.technical.setOnClickListener(v -> buySellClickListener.onTechnical(contact.getSymbol()));

            userViewHolder.low.setText(contact.getLow());
            userViewHolder.low.setText(contact.getLow());

            if(contact.getAchieved1().equalsIgnoreCase("Done")){
                userViewHolder.text_achieved_1.setBackgroundColor(Color.parseColor("#019c0e"));
                userViewHolder.text_achieved_1.setTextColor(Color.parseColor("#ffffff"));
            }
            else {
                userViewHolder.text_achieved_1.setBackgroundColor(Color.parseColor("#ffffff"));
                userViewHolder.text_achieved_1.setTextColor(Color.parseColor("#000000"));
            }
            if(contact.getAchieved2().equalsIgnoreCase("Done")){
                userViewHolder.text_achieved_2.setBackgroundColor(Color.parseColor("#019c0e"));
                userViewHolder.text_achieved_2.setTextColor(Color.parseColor("#ffffff"));

            }
            else {
                userViewHolder.text_achieved_2.setBackgroundColor(Color.parseColor("#ffffff"));
                userViewHolder.text_achieved_2.setTextColor(Color.parseColor("#000000"));
            }
            if(contact.getAchieved3().equalsIgnoreCase("Done")){
                userViewHolder.text_achieved_3.setBackgroundColor(Color.parseColor("#019c0e"));
                userViewHolder.text_achieved_3.setTextColor(Color.parseColor("#ffffff"));

            }else {
                userViewHolder.text_achieved_3.setBackgroundColor(Color.parseColor("#ffffff"));
                userViewHolder.text_achieved_3.setTextColor(Color.parseColor("#000000"));
            }

            if(contact.getBuy1().isEmpty() || contact.getBuy1().equalsIgnoreCase("")){
                userViewHolder.text_buy_1.setText("-");
            }
            if(contact.getBuy2().isEmpty() || contact.getBuy2().equalsIgnoreCase("")){
                userViewHolder.text_buy_2.setText("-");
            }
            if(contact.getBuy3().isEmpty() || contact.getBuy3().equalsIgnoreCase("")){
                userViewHolder.text_buy_3.setText("-");
            }

            if(contact.getHigh().isEmpty()){
                userViewHolder.high.setText("-");
            }
            if(contact.getLow().isEmpty()){
                userViewHolder.low.setText("-");
            }
            if(contact.getPr_close().isEmpty()){
                userViewHolder.pres.setText("-");
            }
            if(contact.getCmp_price().isEmpty()){
                userViewHolder.equity_price.setText("-");
            }


            if(!contact.getOpens().isEmpty()){
                userViewHolder.opens.setText(contact.getOpens());
            }
            else{
                userViewHolder.opens.setText("-");
            }

            if(contact.isIs_Open()){
                userViewHolder.line_buy_sell.setVisibility(View.VISIBLE);
            }
            ///            line_buy_sell.setVisibility(View.GONE);


            userViewHolder.charts.setOnClickListener(v -> context.startActivity(new Intent(context, ChartWebActivity.class).putExtra("url","https://in.tradingview.com/chart/?symbol=NSE%3A"+contact.getSymbol().replace(".NS","").replace(".BS",""))));

            userViewHolder.fundamental.setOnClickListener(v -> context.startActivity(new Intent(context, StockDetailsActivity.class).putExtra(Constant.search,contact.getSymbol())));

            userViewHolder.rel_line.setOnClickListener(v -> {

                contact.setIs_Open(!contact.isIs_Open());
                notifyDataSetChanged();
            });

            if(contact.getOldprice()!=null){
                if(!contact.getOldprice().isEmpty()) {
                    if (contact.getCmp_price() != null) {
                        if (!contact.getCmp_price().isEmpty()) {
                            if (Double.parseDouble(contact.getCmp_price()) > Double.parseDouble(contact.getOldprice())) {
                                userViewHolder.ticker.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                                userViewHolder.equity_price.setBackgroundColor(Color.parseColor("#08C82D"));
                                userViewHolder.equity_price.setTextColor(Color.parseColor("#FFFFFF"));
                            } else {
                                userViewHolder.equity_price.setBackgroundColor(Color.parseColor("#EF1003"));
                                userViewHolder.ticker.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                                userViewHolder.equity_price.setTextColor(Color.parseColor("#FFFFFF"));
                            }
                        }
                    }
                }

            }
            else{
                userViewHolder.equity_price.setBackgroundColor(Color.parseColor("#08C82D"));
                userViewHolder.ticker.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                userViewHolder.equity_price.setTextColor(Color.parseColor("#FFFFFF"));
            }
            ((EquityModel) contacts.get(position)).setOldPrice(((EquityModel) contacts.get(position)).getCmp_price());


        }
        else if (holder instanceof BannerViewHolder) {
            BannerModel bannerModel= (BannerModel) contacts.get(position);
            if(bannerModel.getTextData().equalsIgnoreCase("0")){
                ((BannerViewHolder) holder).alice_pen.setVisibility(View.GONE);
                ((BannerViewHolder) holder).upstock.setVisibility(View.VISIBLE);
            }
            else{
                ((BannerViewHolder) holder).alice_pen.setVisibility(View.VISIBLE);
                ((BannerViewHolder) holder).upstock.setVisibility(View.GONE);

            }
            BannerViewHolder loadingViewHolder = (BannerViewHolder) holder;
            loadingViewHolder.image_pen.setOnClickListener(view -> accountOpenClick.onClick());
            loadingViewHolder.alice_pen.setOnClickListener(view -> accountOpenClick.onAliceClick());
            loadingViewHolder.upstock.setOnClickListener(view -> accountOpenClick.onUpstockClick());
            //loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    public List<Object> getDataList() {
        return contacts;
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    private void manageBlinkEffect(TextView txt,int color) {
        ObjectAnimator anim = ObjectAnimator.ofInt(txt, "backgroundColor", Color.BLACK, color, Color.BLACK);
        anim.setDuration(2000);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }
}

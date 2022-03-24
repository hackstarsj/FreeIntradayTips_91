package com.silverlinesoftwares.intratips.adapters;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


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
import com.silverlinesoftwares.intratips.util.StaticMethods;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class EquityAdapterR extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int AD_TYPE = 1;
    private boolean isLoading;
    private Activity activity;
    private List<Object> contacts;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private final int VIEW_TYPE_ITEM = 0;
    public Activity context;
    private final int VIEW_TYPE_LOADING = 1;

    //private OnLoadMoreListener onLoadMoreListener;
    BuySellClickListener buySellClickListener;
    AccountOpenClick accountOpenClick;

    @SuppressLint("NotifyDataSetChanged")
    public void changePrice(String regularMarketPrice, int i) {
        if(contacts.get(i) instanceof EquityModel){
            Log.d("Price ","Change");
            ((EquityModel) contacts.get(i)).setCmp_price(regularMarketPrice);
            notifyDataSetChanged();
        }
    }
//    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
//        this.onLoadMoreListener = mOnLoadMoreListener;
//    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout image_pen;
        public RelativeLayout alice_pen;
        public RelativeLayout upstock;

        public BannerViewHolder(View view) {
            super(view);
            image_pen = (RelativeLayout) view.findViewById(R.id.image_pen);
            alice_pen = (RelativeLayout) view.findViewById(R.id.alice_pen);
            upstock = (RelativeLayout) view.findViewById(R.id.upstock_pen);
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
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    // "Normal item" ViewHolder
    private class EquityAdapterRow extends RecyclerView.ViewHolder {

        public RelativeLayout rel_line;
        public Button buy_btn;
        public Button sell_btn;
        public Button charts;
        public Button fundamental;
        public Button technical;
        public LinearLayout line_buy_sell;
        public TextView equity_title;
        public TextView equity_datetime;
        public TextView equity_time;
        public TextView equity_price;
        public TextView text_target_1;
        public TextView text_target_2;
        public TextView text_target_3;
        public TextView equity_buy;
        public TextView text_buy_1;
        public TextView text_buy_2;
        public TextView text_buy_3;
        public TextView text_achieved_1;
        public TextView text_achieved_2;
        public TextView text_achieved_3;
        public ImageView ticker;
        public TextView equity_loss;
        public TextView equity_loss_text;
        public TextView stoploss;
        public TextView notification_message;
        public TextView buy_text,opens;
        public LinearLayout buy_line,linesss;

        public TextView high,low,pres;

        public EquityAdapterRow(View view) {
            super(view);
            equity_title=(TextView) view.findViewById(R.id.equity_title);
            equity_datetime=(TextView) view.findViewById(R.id.equity_datetime);
            equity_time=(TextView) view.findViewById(R.id.equity_time);
            equity_price=(TextView) view.findViewById(R.id.equity_price);
            text_target_1=(TextView) view.findViewById(R.id.text_target_1);
            text_target_2=(TextView) view.findViewById(R.id.text_target_2);
            text_target_3=(TextView) view.findViewById(R.id.text_target_3);
            equity_buy=(TextView) view.findViewById(R.id.equity_buy);
            text_buy_1=(TextView) view.findViewById(R.id.text_buy_1);
            notification_message=(TextView) view.findViewById(R.id.notification_text);

            line_buy_sell=(LinearLayout)view.findViewById(R.id.line_buy_sell);
            rel_line=(RelativeLayout)view.findViewById(R.id.rel_line);
            buy_btn=(Button) view.findViewById(R.id.buy);
            sell_btn=(Button) view.findViewById(R.id.sell);
            charts=(Button) view.findViewById(R.id.chart);
            fundamental=(Button) view.findViewById(R.id.fundamental);
            technical=(Button) view.findViewById(R.id.technical);

            text_buy_2=(TextView) view.findViewById(R.id.text_buy_2);
            text_buy_3=(TextView) view.findViewById(R.id.text_buy_3);
            text_achieved_1=(TextView) view.findViewById(R.id.text_achieved_1);
            text_achieved_2=(TextView) view.findViewById(R.id.text_achieved_2);
            text_achieved_3=(TextView) view.findViewById(R.id.text_achieved_3);
            equity_loss=(TextView) view.findViewById(R.id.equity_loss);
            equity_loss_text=(TextView) view.findViewById(R.id.equity_loss_text);
            pres=(TextView) view.findViewById(R.id.pr_close);
            high=(TextView) view.findViewById(R.id.high);
            low=(TextView) view.findViewById(R.id.low);
            stoploss=(TextView) view.findViewById(R.id.stop_loss);
            buy_text=(TextView) view.findViewById(R.id.buy_text);
            buy_line=(LinearLayout) view.findViewById(R.id.line_buy);
            linesss=(LinearLayout) view.findViewById(R.id.linesss);
            opens=(TextView)view.findViewById(R.id.opens);
            ticker=(ImageView) view.findViewById(R.id.ticker);

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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.equity_row, parent, false);
            return new EquityAdapterRow(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.bottom_layout_open_demat, parent, false);
            return new BannerViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
            userViewHolder.equity_buy.setText(""+contact.getBuy_price());
            userViewHolder.text_buy_1.setText(""+formatter.format(Double.parseDouble(contact.getBuy1())));
            userViewHolder.text_buy_2.setText(""+formatter.format(Double.parseDouble(contact.getBuy2())));
            userViewHolder.text_buy_3.setText(""+formatter.format(Double.parseDouble(contact.getBuy3())));
            userViewHolder.text_achieved_1.setText(contact.getAchieved1());
            userViewHolder.text_achieved_2.setText(contact.getAchieved2());
            userViewHolder.text_achieved_3.setText(contact.getAchieved3());
            userViewHolder.equity_loss.setText(""+formatter.format(Double.parseDouble(contact.getStop_loss())));
            userViewHolder.stoploss.setText(contact.getStop_loss_text());
            userViewHolder.equity_loss_text.setText(contact.getStop_loss_end());

            if(contact.getNotification_message()!=null) {
                if (!contact.getNotification_message().isEmpty()) {
                    userViewHolder.notification_message.setText("" + contact.getNotification_message());
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

            userViewHolder.buy_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buySellClickListener.onBuy(contact.getSymbol(),contact.getPrice());
                }
            });

            userViewHolder.sell_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buySellClickListener.onSell(contact.getSymbol(),contact.getPrice());
                }
            });

            userViewHolder.technical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buySellClickListener.onTechnical(contact.getSymbol());
                }
            });

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
            else{
///            line_buy_sell.setVisibility(View.GONE);
            }

            userViewHolder.charts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, ChartWebActivity.class).putExtra("url","https://in.tradingview.com/chart/?symbol=NSE%3A"+contact.getSymbol().replace(".NS","").replace(".BS","")));
                }
            });

            userViewHolder.fundamental.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, StockDetailsActivity.class).putExtra(Constant.search,contact.getSymbol()));
                }
            });

            userViewHolder.rel_line.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    if(context!=null) {
                        StaticMethods.showInterestialAds(context);
                    }
                    contact.setIs_Open(!contact.isIs_Open());
                    notifyDataSetChanged();
                }
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
            loadingViewHolder.image_pen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    accountOpenClick.onClick();
                }
            });
            loadingViewHolder.alice_pen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    accountOpenClick.onAliceClick();
                }
            });
            loadingViewHolder.upstock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    accountOpenClick.onUpstockClick();
                }
            });
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

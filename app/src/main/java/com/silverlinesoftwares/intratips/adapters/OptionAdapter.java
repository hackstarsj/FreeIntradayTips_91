package com.silverlinesoftwares.intratips.adapters;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.listeners.AccountOpenClick;
import com.silverlinesoftwares.intratips.models.BannerModel;
import com.silverlinesoftwares.intratips.models.OptionModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OptionAdapter extends RecyclerView.Adapter{

    private static final int AD_TYPE = 1;
    private boolean isLoading;
    private final Activity activity;
    private final List<Object> contacts;
    private final int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private final int VIEW_TYPE_ITEM = 0;
    public final Activity context;
    private final int VIEW_TYPE_LOADING = 1;

    final AccountOpenClick accountOpenClick;



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


    public OptionAdapter(RecyclerView recyclerView, List<Object> contacts, Activity activity, Activity context, AccountOpenClick accountOpenClick) {
        this.contacts = contacts;
        this.context=context;
        this.activity = activity;
        this.accountOpenClick=accountOpenClick;

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
    private static class OptionAdapterRow extends RecyclerView.ViewHolder {


        public final TextView equity_title;
        public final TextView equity_datetime;
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
        public final TextView equity_loss;
        public final TextView equity_loss_text;
        public final TextView stoploss;
        public final TextView buy_text;
        public final TextView notification_message;
        public final LinearLayout buy_line;
        public final LinearLayout linesss;


        public OptionAdapterRow(View view) {
            super(view);
            equity_title= view.findViewById(R.id.equity_title);
            equity_datetime= view.findViewById(R.id.equity_datetime);
            equity_price= view.findViewById(R.id.equity_price);
            text_target_1= view.findViewById(R.id.text_target_1);
            text_target_2= view.findViewById(R.id.text_target_2);
            text_target_3= view.findViewById(R.id.text_target_3);
            equity_buy= view.findViewById(R.id.equity_buy);
            text_buy_1= view.findViewById(R.id.text_buy_1);


            text_buy_2= view.findViewById(R.id.text_buy_2);
            text_buy_3= view.findViewById(R.id.text_buy_3);
            text_achieved_1= view.findViewById(R.id.text_achieved_1);
            text_achieved_2= view.findViewById(R.id.text_achieved_2);
            text_achieved_3= view.findViewById(R.id.text_achieved_3);
            equity_loss= view.findViewById(R.id.equity_loss);
            equity_loss_text= view.findViewById(R.id.equity_loss_text);
            stoploss= view.findViewById(R.id.stop_loss);
            buy_text= view.findViewById(R.id.buy_text);
            buy_line= view.findViewById(R.id.line_buy);
            linesss= view.findViewById(R.id.linesss);
            notification_message= view.findViewById(R.id.notification_text);

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
            View view = LayoutInflater.from(activity).inflate(R.layout.option_row, parent, false);
            return new OptionAdapterRow(view);
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
        if (holder instanceof OptionAdapterRow) {
            final OptionModel contact = (OptionModel) contacts.get(position);
            OptionAdapterRow userViewHolder = (OptionAdapterRow) holder;
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

            userViewHolder.equity_title.setText(contact.getName());
            userViewHolder.equity_datetime.setText(contact.getDatetime());
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
                        userViewHolder.notification_message.setBackgroundColor(Color.parseColor("#f45854"));
                        manageBlinkEffect(userViewHolder.notification_message,Color.parseColor("#f45854"));

                    } else {
                        userViewHolder.notification_message.setBackgroundColor(Color.parseColor("#31ae36"));
                        manageBlinkEffect(userViewHolder.notification_message,Color.parseColor("#31ae36"));
                    }
                    userViewHolder.notification_message.setAnimation(getBlinkAnimation());
                } else {
                    userViewHolder.notification_message.setVisibility(View.GONE);
                }
            }
            else{
                userViewHolder.notification_message.setVisibility(View.GONE);
            }

            if(contact.getStop_loss_end().equalsIgnoreCase("LOSS")){
                userViewHolder.equity_loss_text.setBackgroundColor(Color.parseColor("#f44336"));
            }
            else if(contact.getStop_loss_end().equalsIgnoreCase("EXIT")){
                userViewHolder.equity_loss_text.setBackgroundColor(Color.parseColor("#4caf50"));
            }
            else{
                userViewHolder.equity_loss_text.setBackgroundColor(Color.parseColor("#BE1AFA"));
            }
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


            if(contact.getCmp_price().isEmpty()){
                userViewHolder.equity_price.setText("-");
            }




            userViewHolder.equity_price.setBackgroundColor(Color.parseColor("#08C82D"));
            userViewHolder.equity_price.setTextColor(Color.parseColor("#FFFFFF"));

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

    public Animation getBlinkAnimation(){
        Animation animation = new AlphaAnimation(1, 0);         // Change alpha from fully visible to invisible
        animation.setDuration(300);                             // duration - half a second
        animation.setInterpolator(new LinearInterpolator());    // do not alter animation rate
        animation.setRepeatCount(1);                            // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE);             // Reverse animation at the end so the button will fade back in

        return animation;
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

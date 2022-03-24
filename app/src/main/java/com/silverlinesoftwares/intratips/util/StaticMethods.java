package com.silverlinesoftwares.intratips.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.gson.Gson;
import com.silverlinesoftwares.intratips.models.StrongBuySellModel;
import com.silverlinesoftwares.intratips.models.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

public class StaticMethods {
    public static int failedInt=0;



    public static String getStrongSellPost(){
        return "{\"filter\":[{\"left\":\"Recommend.All\",\"operation\":\"nempty\"},{\"left\":\"type\",\"operation\":\"in_range\",\"right\":[\"stock\",\"dr\"]},{\"left\":\"subtype\",\"operation\":\"in_range\",\"right\":[\"common\",\"\",\"etf\",\"etf,odd\",\"etf,otc\",\"etf,cfd\"]}],\"options\":{\"lang\":\"en\"},\"symbols\":{\"query\":{\"types\":[]},\"tickers\":[]},\"columns\":[\"logoid\",\"name\",\"close\",\"change\",\"change_abs\",\"Recommend.All\",\"volume\",\"market_cap_basic\",\"price_earnings_ttm\",\"earnings_per_share_basic_ttm\",\"number_of_employees\",\"sector\",\"description\",\"name\",\"type\",\"subtype\",\"update_mode\",\"pricescale\",\"minmov\",\"fractional\",\"minmove2\"],\"sort\":{\"sortBy\":\"Recommend.All\",\"sortOrder\":\"asc\"},\"range\":[0,150]}";
    }

    public static String getStrongBuy(){
        return "{\"filter\":[{\"left\":\"Recommend.All\",\"operation\":\"nempty\"},{\"left\":\"type\",\"operation\":\"in_range\",\"right\":[\"stock\",\"dr\"]},{\"left\":\"subtype\",\"operation\":\"in_range\",\"right\":[\"common\",\"\",\"etf\",\"etf,odd\",\"etf,otc\",\"etf,cfd\"]}],\"options\":{\"lang\":\"en\"},\"symbols\":{\"query\":{\"types\":[]},\"tickers\":[]},\"columns\":[\"logoid\",\"name\",\"close\",\"change\",\"change_abs\",\"Recommend.All\",\"volume\",\"market_cap_basic\",\"price_earnings_ttm\",\"earnings_per_share_basic_ttm\",\"number_of_employees\",\"sector\",\"description\",\"name\",\"type\",\"subtype\",\"update_mode\",\"pricescale\",\"minmov\",\"fractional\",\"minmove2\"],\"sort\":{\"sortBy\":\"Recommend.All\",\"sortOrder\":\"desc\"},\"range\":[0,150]}";
    }

    public static List<StrongBuySellModel> getBuySellModel(String data, boolean isSell) {
        if(data==null){
            return null;
        }
        else{
            try {
                List<StrongBuySellModel> strongBuySellModels=new ArrayList<>();
                JSONObject jsonObject=new JSONObject(data);
                JSONArray jsonArray=jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    String[] stock_full=jsonArray.getJSONObject(i).getString("s").split(":");
                    String stock=stock_full[1];
                    String exh=stock_full[0];
                    JSONArray jsondata=jsonArray.getJSONObject(i).getJSONArray("d");
                    strongBuySellModels.add(new StrongBuySellModel(stock,stock+"."+exh,exh,jsondata.getString(4),String.format(Locale.getDefault(),"%.2f", Double.parseDouble(jsondata.getString(3))),jsondata.getString(2),""+coolFormat(Integer.parseInt(jsondata.getString(6))),jsondata.getString(7),isSell));
                }
                return strongBuySellModels;
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    private static final String[] c = new String[]{"K", "L", "Cr"};
    private static String coolFormat(int n) {
        int size = String.valueOf(n).length();
        if (size>=4 && size<6) {
            int value = (int) Math.pow(10, 1);
            double d = (double) Math.round(n/1000.0 * value) / value;
            return (double) Math.round(n/1000.0 * value) / value+" "+c[0];
        } else if(size>5 && size<8) {
            int value = (int) Math.pow(10, 1);
            return (double) Math.round(n/100000.0 * value) / value+" "+c[1];
        } else if(size>=8) {
            int value = (int) Math.pow(10, 1);
            return (double) Math.round(n/10000000.0 * value) / value+" "+c[2];
        } else {
            return n+"";
        }
    }

    @SuppressLint("DefaultLocale")
    public static String getCurrency(String text, String currency) {
        if (text != null) {
            Double douple=Double.parseDouble(text);
            return Utils.getCurrencySymbol(currency)+" "+String.format("%.2f",douple);
        } else {
            return "";
        }
    }

    @SuppressLint("DefaultLocale")
    public static String getCurrencywithcomma(String text, String currency) {
        if (text != null) {
            Double douple=Double.parseDouble(text);
            return Utils.getCurrencySymbol(currency)+" "+convertToComma(String.format("%.2f",douple));
        } else {
            return "";
        }
    }

    @SuppressLint("DefaultLocale")
    public static String ConverToTwoDigit(String string){
        if(string!=null){
            if(string.isEmpty()){
                return "0.00";
            }
            else {
                return String.format("%.2f",Double.parseDouble(string));
            }
        }
        else {
            return "0.00";
        }
    }
    public static String convertToComma(String text){
        if(text!=null) {
            try {
                return NumberFormat.getInstance().format(Double.parseDouble(text));
            }
            catch (java.lang.NumberFormatException e){
                e.printStackTrace();
                return text;
            }
        }
        else {
            return "";
        }
    }

    public static boolean isPrivacyRun(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        if(sharedPreferences.contains("is_second")){
            if(sharedPreferences.getString("is_second",null).equalsIgnoreCase("no")){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return true;
        }
    }

    static class Utils {
        public static SortedMap<Currency, Locale> currencyLocaleMap;

        static {
            currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
                public int compare(Currency c1, Currency c2) {
                    return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
                }
            });
            for (Locale locale : Locale.getAvailableLocales()) {
                try {
                    Currency currency = Currency.getInstance(locale);
                    currencyLocaleMap.put(currency, locale);
                } catch (Exception e) {
                }
            }
        }


        public static String getCurrencySymbol(String currencyCode) {
            Currency currency = Currency.getInstance(currencyCode);
            System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
            return currency.getSymbol(currencyLocaleMap.get(currency));
        }


    }

    public static String getColor(double nos){
        if(nos==0){
            return "#dfe8e0";
        }
        else if(nos>0.00 && nos<=0.01){
            return "#cbdacc";
        }
        else if(nos>0.01 && nos<=0.02){
            return "#c5d6c6";
        }
        else if(nos>0.02 && nos<=0.03){
            return "#b8ccb9";
        }
        else if(nos>0.03 && nos<=0.04){
            return "#a4bfa6";
        }
        else if(nos>0.04 && nos<=0.05){
            return "#90b193";
        }
        else if(nos>0.05 && nos<=0.06){
            return "#7da380";
        }
        else if(nos>0.06){
            return "#497e4d";
        }
        else if(nos>-0.05 && nos<0){
            return "#f3c3b1";
        }
        else if(nos>-0.1 && nos<=0.05){
            return "#e88e6a";
        }
        else if(nos>-0.5 && nos<=-0.1){
            return "#e37a50";
        }
        else if(nos<-0.5){
            return "#e27347";
        }
        else
        {
            return "#ffffff";
        }

    }


    public static void setLoginToken(Context context,String token){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("token",token);
        editor.apply();
    }

    public static void setNotification(Context context,String token){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("notification",token);
        editor.apply();
    }

    public static String getNotification(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        if(sharedPreferences.contains("notification")){
            return sharedPreferences.getString("notification","1");
        }
        else{
            return "1";
        }
    }

    public static String getLoginToken(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        if(sharedPreferences.contains("token")){
            return sharedPreferences.getString("token",null);
        }
        else{
            return null;
        }
    }

    public static void saveUserDetails(Context context, UserModel userModel){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userModel); // myObject - instance of MyObject
        editor.putString("user_details", json);
        editor.apply();
    }

    public static UserModel getUserDetails(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        if(sharedPreferences.contains("user_details")){
            Gson gson = new Gson();
            String json = sharedPreferences.getString("user_details", "");
            UserModel obj = gson.fromJson(json, UserModel.class);
            return obj;
        }
        else{
            return null;
        }
    }

    public static void removeUser(Context context){
        if(context!=null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", 0);

            if (sharedPreferences.contains("user_details")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user_details");
                editor.apply();
            }
        }
    }

    public static void removeToken(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);

        if(sharedPreferences.contains("token")){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.remove("token");
            editor.apply();
        }
    }


    public static boolean isFirstRun(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        if(sharedPreferences.contains("is_first")){
            if(sharedPreferences.getString("is_first",null).equalsIgnoreCase("no")){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return true;
        }
    }

    public static void setFirstStart(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("is_first","no");
        editor.apply();
    }

    public static void setSecondStart(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("is_second","no");
        editor.apply();
    }


    public static int clicks=0;

    public static boolean showAds(Context context){
        if(context!=null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", 0);
            if (sharedPreferences.contains("user_details")) {
                Gson gson = new Gson();
                String json = sharedPreferences.getString("user_details", "");
                UserModel obj = gson.fromJson(json, UserModel.class);
                if (obj != null) {
                    if (obj.getIs_pro().equalsIgnoreCase("1")) {
                        return false;
                    } else {
                        return getClickAds();
                    }
                } else {
                    return getClickAds();
                }
            } else {
                return getClickAds();
            }
        }
        else{
            return getClickAds();
        }
    }

    public static boolean showBannerAds(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        if(sharedPreferences.contains("user_details")){
            Gson gson = new Gson();
            String json = sharedPreferences.getString("user_details", "");
            UserModel obj = gson.fromJson(json, UserModel.class);
            if(obj!=null) {
                if (obj.getIs_pro().equalsIgnoreCase("1")) {
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                return true;
            }
        }
        else{
            return true;
        }
    }

    private static boolean getClickAds() {
        if(clicks==3){
            clicks=1;
            return true;
        }
        else{
            return false;
        }
    }


    private static InterstitialAd mInterstitialAd;

    public static void showInterestialAds(Activity context){
//         if(showAds(context))
//         {
//             try {
//                 if (mInterstitialAd == null) {
//                     LoadAds(context);
//                 } else {
//                         mInterstitialAd.show(context);
//                         Handler handler=new Handler(Looper.getMainLooper());
//                        handler.postDelayed(()->{
//                            LoadAds(context);
//                        },5000);
//                 }
//             }
//             catch (Exception e){
//                 e.printStackTrace();
//             }
//         }
//         clicks=clicks+1;
    }

    public static void showRewardAds(Activity context, OnUserEarnedRewardListener onUserEarnedRewardListener){
//        if(showAds(context))
//        {
//            try {
//                if (rewardedInterstitialAd == null) {
//                    LoadAdsReward(context);
//                } else {
//                    rewardedInterstitialAd.show(context,onUserEarnedRewardListener);
//                    Log.d("REWARD","REWARD");
//                    Handler handler=new Handler(Looper.getMainLooper());
//                    handler.postDelayed(()->{
//                        LoadAdsReward(context);
//                    },5000);
//                }
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }

    public static int failedreward=0;
    private static void LoadAdsReward(Activity context) {
//        RewardedInterstitialAd.load(context, "ca-app-pub-8515817249593489/9036584202",
//                new AdRequest.Builder().build(),  new RewardedInterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(RewardedInterstitialAd ad) {
//                        failedreward=0;
//                        rewardedInterstitialAd = ad;
//                    }
//                    @Override
//                    public void onAdFailedToLoad(LoadAdError loadAdError) {
//                        rewardedInterstitialAd=null;
//                        if(failedreward<=10) {
//                            failedreward=failedreward+1;
//                            LoadAdsReward(context);
//                        }
//                    }
//                });
    }

    private static void LoadAds(Activity context) {
//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(context,"ca-app-pub-8515817249593489/9989773924", adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                failedInt=0;
//                mInterstitialAd = interstitialAd;
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//                mInterstitialAd=null;
//                if(failedInt<=10) {
//                    failedInt=failedInt+1;
//                    LoadAds(context);
//                }
//            }
//        });
    }



    public static void showBannerAds(View adContainer2,Context context){
        if(showBannerAds(context)) {
            final AdView mAdView2 = new AdView(context);
            mAdView2.setAdSize(AdSize.BANNER);
            mAdView2.setAdUnitId("ca-app-pub-8515817249593489/2685998979");
            ((RelativeLayout) adContainer2).addView(mAdView2);
            AdRequest adRequest2 = new AdRequest.Builder().build();
            mAdView2.loadAd(adRequest2);
            mAdView2.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mAdView2.setVisibility(View.VISIBLE);
                }
            });
        }
        else{
            adContainer2.setVisibility(View.GONE);
        }

    }
}


package com.silverlinesoftwares.intratips.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.UserModel;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

public class StaticMethods {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
    public static <T> void executeAsyncTask(AsyncTask<T, ?, ?> asyncTask, T... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            try {
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
            }
            catch (java.util.concurrent.RejectedExecutionException e){
                e.printStackTrace();
            }
        else
            asyncTask.execute(params);
    }

    public static String getCurrency(String text, String currency) {
        if (text != null) {
            Double douple=Double.parseDouble(text);
            return Utils.getCurrencySymbol(currency)+" "+String.format("%.2f",douple);
        } else {
            return "";
        }
    }

    public static String getCurrencywithcomma(String text, String currency) {
        if (text != null) {
            Double douple=Double.parseDouble(text);
            return Utils.getCurrencySymbol(currency)+" "+convertToComma(String.format("%.2f",douple));
        } else {
            return "";
        }
    }

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
         if(showAds(context))
         {
             try {
                 if (mInterstitialAd == null) {
                     mInterstitialAd = new InterstitialAd(context);
                     mInterstitialAd.setAdUnitId("ca-app-pub-8515817249593489/9989773924");
                     mInterstitialAd.loadAd(new AdRequest.Builder().build());

                 } else {
                     if (mInterstitialAd.isLoaded()) {
                         mInterstitialAd.show();
                         mInterstitialAd.loadAd(new AdRequest.Builder().build());
                     } else {

                         mInterstitialAd.loadAd(new AdRequest.Builder().build());
                     }
                 }
             }
             catch (Exception e){
                 e.printStackTrace();
             }
         }
         clicks=clicks+1;
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


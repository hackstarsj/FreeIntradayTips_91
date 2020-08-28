package com.silverlinesoftwares.intratips.models;

public class OptionChainModel {
    private String strike_price="0";
    private String oi_1="0";
    private String chng_in_oi_1="0";
    private String volume_1="0";
    private String iv_1="0";
    private String ltp_1="0";
    private String net_chng_1="0";
    private String oi_2="0";
    private String chng_in_oi_2="0";
    private String volume_2="0";
    private String iv_2="0";
    private String ltp_2="0";
    private String net_chng_2="0";

    public String getStrike_price() {
        return strike_price;
    }

    public void setStrike_price(String strike_price) {
        this.strike_price = strike_price;
    }

    public String getOi_1() {
        return oi_1;
    }

    public void setOi_1(String oi_1) {
        this.oi_1 = oi_1;
    }

    public String getChng_in_oi_1() {
        return chng_in_oi_1;
    }

    public void setChng_in_oi_1(String chng_in_oi_1) {
        this.chng_in_oi_1 = chng_in_oi_1;
    }

    public String getVolume_1() {
        return volume_1;
    }

    public void setVolume_1(String volume_1) {
        this.volume_1 = volume_1;
    }

    public String getIv_1() {
        return iv_1;
    }

    public void setIv_1(String iv_1) {
        this.iv_1 = iv_1;
    }

    public String getLtp_1() {
        return ltp_1;
    }

    public void setLtp_1(String ltp_1) {
        this.ltp_1 = ltp_1;
    }

    public String getNet_chng_1() {
        return net_chng_1;
    }

    public void setNet_chng_1(String net_chng_1) {
        this.net_chng_1 = net_chng_1;
    }

    public String getOi_2() {
        return oi_2;
    }

    public void setOi_2(String oi_2) {
        this.oi_2 = oi_2;
    }

    public String getChng_in_oi_2() {
        return chng_in_oi_2;
    }

    public void setChng_in_oi_2(String chng_in_oi_2) {
        this.chng_in_oi_2 = chng_in_oi_2;
    }

    public String getVolume_2() {
        return volume_2;
    }

    public void setVolume_2(String volume_2) {
        this.volume_2 = volume_2;
    }

    public String getIv_2() {
        return iv_2;
    }

    public void setIv_2(String iv_2) {
        this.iv_2 = iv_2;
    }

    public String getLtp_2() {
        return ltp_2;
    }

    public void setLtp_2(String ltp_2) {
        this.ltp_2 = ltp_2;
    }

    public String getNet_chng_2() {
        return net_chng_2;
    }

    public void setNet_chng_2(String net_chng_2) {
        this.net_chng_2 = net_chng_2;
    }

    public OptionChainModel(){

    }

    public OptionChainModel(String strike_price, String oi_1, String chng_in_oi_1, String volume_1, String iv_1, String ltp_1, String net_chng_1, String oi_2, String chng_in_oi_2, String volume_2, String iv_2, String ltp_2, String net_chng_2) {
        this.strike_price = strike_price;
        this.oi_1 = oi_1;
        this.chng_in_oi_1 = chng_in_oi_1;
        this.volume_1 = volume_1;
        this.iv_1 = iv_1;
        this.ltp_1 = ltp_1;
        this.net_chng_1 = net_chng_1;
        this.oi_2 = oi_2;
        this.chng_in_oi_2 = chng_in_oi_2;
        this.volume_2 = volume_2;
        this.iv_2 = iv_2;
        this.ltp_2 = ltp_2;
        this.net_chng_2 = net_chng_2;
    }
}

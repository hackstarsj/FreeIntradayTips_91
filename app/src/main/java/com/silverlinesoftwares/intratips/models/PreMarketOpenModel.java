package com.silverlinesoftwares.intratips.models;

import java.io.Serializable;

public class PreMarketOpenModel implements Serializable {

    String symbol;
    String series;
    String xDt;
    String caAct;
    String iep;
    String chn;
    String perChn;
    String pCls;
    String trdQnty;
    String iVal;
    String mktCap;
    String yHigh;
    String yLow;
    String sumVal;
    String sumQnty;
    String finQnty;
    String sumfinQnty;


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getxDt() {
        return xDt;
    }

    public void setxDt(String xDt) {
        this.xDt = xDt;
    }

    public String getCaAct() {
        return caAct;
    }

    public void setCaAct(String caAct) {
        this.caAct = caAct;
    }

    public String getIep() {
        return iep;
    }

    public void setIep(String iep) {
        this.iep = iep;
    }

    public String getChn() {
        return chn;
    }

    public void setChn(String chn) {
        this.chn = chn;
    }

    public String getPerChn() {
        return perChn;
    }

    public void setPerChn(String perChn) {
        this.perChn = perChn;
    }

    public String getpCls() {
        return pCls;
    }

    public void setpCls(String pCls) {
        this.pCls = pCls;
    }

    public String getTrdQnty() {
        return trdQnty;
    }

    public void setTrdQnty(String trdQnty) {
        this.trdQnty = trdQnty;
    }

    public String getiVal() {
        return iVal;
    }

    public void setiVal(String iVal) {
        this.iVal = iVal;
    }

    public String getMktCap() {
        return mktCap;
    }

    public void setMktCap(String mktCap) {
        this.mktCap = mktCap;
    }

    public String getyHigh() {
        return yHigh;
    }

    public void setyHigh(String yHigh) {
        this.yHigh = yHigh;
    }

    public String getyLow() {
        return yLow;
    }

    public void setyLow(String yLow) {
        this.yLow = yLow;
    }

    public String getSumVal() {
        return sumVal;
    }

    public void setSumVal(String sumVal) {
        this.sumVal = sumVal;
    }

    public String getSumQnty() {
        return sumQnty;
    }

    public void setSumQnty(String sumQnty) {
        this.sumQnty = sumQnty;
    }

    public String getFinQnty() {
        return finQnty;
    }

    public void setFinQnty(String finQnty) {
        this.finQnty = finQnty;
    }

    public String getSumfinQnty() {
        return sumfinQnty;
    }

    public void setSumfinQnty(String sumfinQnty) {
        this.sumfinQnty = sumfinQnty;
    }

    public PreMarketOpenModel(String symbol, String series, String xDt, String caAct, String iep, String chn, String perChn, String pCls, String trdQnty, String iVal, String mktCap, String yHigh, String yLow, String sumVal, String sumQnty, String finQnty, String sumfinQnty) {
        this.symbol = symbol;
        this.series = series;
        this.xDt = xDt;
        this.caAct = caAct;
        this.iep = iep;
        this.chn = chn;
        this.perChn = perChn;
        this.pCls = pCls;
        this.trdQnty = trdQnty;
        this.iVal = iVal;
        this.mktCap = mktCap;
        this.yHigh = yHigh;
        this.yLow = yLow;
        this.sumVal = sumVal;
        this.sumQnty = sumQnty;
        this.finQnty = finQnty;
        this.sumfinQnty = sumfinQnty;
    }
}

package com.silverlinesoftwares.intratips.models;

import java.io.Serializable;

public class GainerLosserModel implements Serializable {
      String ptsC;
      String wklo;
      String iislPtsChange;
      String symbol;
      String cAct;
      String mPC;
      String wkhi;
      String wklocm_adj;
      String trdVolM;
      String wkhicm_adj;
      String mVal;
      String ltP;
      String xDt;
      String trdVol;
      String ntP;
      String yPC;
      String previousClose;
      String dayEndClose;
      String high;
      String iislPercChange;
      String low;
      String per;
      String open;

    public GainerLosserModel(String ptsC, String wklo, String iislPtsChange, String symbol, String cAct, String mPC, String wkhi, String wklocm_adj, String trdVolM, String wkhicm_adj, String mVal, String ltP, String xDt, String trdVol, String ntP, String yPC, String previousClose, String dayEndClose, String high, String iislPercChange, String low, String per, String open) {
        this.ptsC = ptsC;
        this.wklo = wklo;
        this.iislPtsChange = iislPtsChange;
        this.symbol = symbol;
        this.cAct = cAct;
        this.mPC = mPC;
        this.wkhi = wkhi;
        this.wklocm_adj = wklocm_adj;
        this.trdVolM = trdVolM;
        this.wkhicm_adj = wkhicm_adj;
        this.mVal = mVal;
        this.ltP = ltP;
        this.xDt = xDt;
        this.trdVol = trdVol;
        this.ntP = ntP;
        this.yPC = yPC;
        this.previousClose = previousClose;
        this.dayEndClose = dayEndClose;
        this.high = high;
        this.iislPercChange = iislPercChange;
        this.low = low;
        this.per = per;
        this.open = open;
    }

    public String getPtsC() {
        return ptsC;
    }

    public void setPtsC(String ptsC) {
        this.ptsC = ptsC;
    }

    public String getWklo() {
        return wklo;
    }

    public void setWklo(String wklo) {
        this.wklo = wklo;
    }

    public String getIislPtsChange() {
        return iislPtsChange;
    }

    public void setIislPtsChange(String iislPtsChange) {
        this.iislPtsChange = iislPtsChange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getcAct() {
        return cAct;
    }

    public void setcAct(String cAct) {
        this.cAct = cAct;
    }

    public String getmPC() {
        return mPC;
    }

    public void setmPC(String mPC) {
        this.mPC = mPC;
    }

    public String getWkhi() {
        return wkhi;
    }

    public void setWkhi(String wkhi) {
        this.wkhi = wkhi;
    }

    public String getWklocm_adj() {
        return wklocm_adj;
    }

    public void setWklocm_adj(String wklocm_adj) {
        this.wklocm_adj = wklocm_adj;
    }

    public String getTrdVolM() {
        return trdVolM;
    }

    public void setTrdVolM(String trdVolM) {
        this.trdVolM = trdVolM;
    }

    public String getWkhicm_adj() {
        return wkhicm_adj;
    }

    public void setWkhicm_adj(String wkhicm_adj) {
        this.wkhicm_adj = wkhicm_adj;
    }

    public String getmVal() {
        return mVal;
    }

    public void setmVal(String mVal) {
        this.mVal = mVal;
    }

    public String getLtP() {
        return ltP;
    }

    public void setLtP(String ltP) {
        this.ltP = ltP;
    }

    public String getxDt() {
        return xDt;
    }

    public void setxDt(String xDt) {
        this.xDt = xDt;
    }

    public String getTrdVol() {
        return trdVol;
    }

    public void setTrdVol(String trdVol) {
        this.trdVol = trdVol;
    }

    public String getNtP() {
        return ntP;
    }

    public void setNtP(String ntP) {
        this.ntP = ntP;
    }

    public String getyPC() {
        return yPC;
    }

    public void setyPC(String yPC) {
        this.yPC = yPC;
    }

    public String getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(String previousClose) {
        this.previousClose = previousClose;
    }

    public String getDayEndClose() {
        return dayEndClose;
    }

    public void setDayEndClose(String dayEndClose) {
        this.dayEndClose = dayEndClose;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getIislPercChange() {
        return iislPercChange;
    }

    public void setIislPercChange(String iislPercChange) {
        this.iislPercChange = iislPercChange;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}

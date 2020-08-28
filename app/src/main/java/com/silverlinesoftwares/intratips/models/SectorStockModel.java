package com.silverlinesoftwares.intratips.models;

import java.io.Serializable;

public class SectorStockModel implements Serializable {
    String per;
    String ptsC;
    String change;
    String high;
    String low;
    String previousClose;
    String sector;
    String time;
    String iislPtsChange;
    String iislPercChange;
    String ltP;
    String cappingFactor;
    String adjustedClosePrice;
    String dayEndClose;
    String indexDivisor;
    String sharesOutstanding;
    String symbol;
    String investableWeightFactor;
    String Indexmcap_yst;
    String Indexmcap_today;
    String priceperchange;
    String NewIndexValue;
    String pointchange;
    String perchange;


    public SectorStockModel(String symbol, String pointchange, String perchange) {
        this.symbol = symbol;
        this.pointchange = pointchange;
        this.perchange = perchange;
    }

    public SectorStockModel(String per, String ptsC, String change, String high, String low, String previousClose, String sector, String time, String iislPtsChange, String iislPercChange, String ltP, String cappingFactor, String adjustedClosePrice, String dayEndClose, String indexDivisor, String sharesOutstanding, String symbol, String investableWeightFactor, String indexmcap_yst, String indexmcap_today, String priceperchange, String newIndexValue, String pointchange, String perchange) {
        this.per = per;
        this.ptsC = ptsC;
        this.change = change;
        this.high = high;
        this.low = low;
        this.previousClose = previousClose;
        this.sector = sector;
        this.time = time;
        this.iislPtsChange = iislPtsChange;
        this.iislPercChange = iislPercChange;
        this.ltP = ltP;
        this.cappingFactor = cappingFactor;
        this.adjustedClosePrice = adjustedClosePrice;
        this.dayEndClose = dayEndClose;
        this.indexDivisor = indexDivisor;
        this.sharesOutstanding = sharesOutstanding;
        this.symbol = symbol;
        this.investableWeightFactor = investableWeightFactor;
        Indexmcap_yst = indexmcap_yst;
        Indexmcap_today = indexmcap_today;
        this.priceperchange = priceperchange;
        NewIndexValue = newIndexValue;
        this.pointchange = pointchange;
        this.perchange = perchange;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getPtsC() {
        return ptsC;
    }

    public void setPtsC(String ptsC) {
        this.ptsC = ptsC;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(String previousClose) {
        this.previousClose = previousClose;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIislPtsChange() {
        return iislPtsChange;
    }

    public void setIislPtsChange(String iislPtsChange) {
        this.iislPtsChange = iislPtsChange;
    }

    public String getIislPercChange() {
        return iislPercChange;
    }

    public void setIislPercChange(String iislPercChange) {
        this.iislPercChange = iislPercChange;
    }

    public String getLtP() {
        return ltP;
    }

    public void setLtP(String ltP) {
        this.ltP = ltP;
    }

    public String getCappingFactor() {
        return cappingFactor;
    }

    public void setCappingFactor(String cappingFactor) {
        this.cappingFactor = cappingFactor;
    }

    public String getAdjustedClosePrice() {
        return adjustedClosePrice;
    }

    public void setAdjustedClosePrice(String adjustedClosePrice) {
        this.adjustedClosePrice = adjustedClosePrice;
    }

    public String getDayEndClose() {
        return dayEndClose;
    }

    public void setDayEndClose(String dayEndClose) {
        this.dayEndClose = dayEndClose;
    }

    public String getIndexDivisor() {
        return indexDivisor;
    }

    public void setIndexDivisor(String indexDivisor) {
        this.indexDivisor = indexDivisor;
    }

    public String getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(String sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getInvestableWeightFactor() {
        return investableWeightFactor;
    }

    public void setInvestableWeightFactor(String investableWeightFactor) {
        this.investableWeightFactor = investableWeightFactor;
    }

    public String getIndexmcap_yst() {
        return Indexmcap_yst;
    }

    public void setIndexmcap_yst(String indexmcap_yst) {
        Indexmcap_yst = indexmcap_yst;
    }

    public String getIndexmcap_today() {
        return Indexmcap_today;
    }

    public void setIndexmcap_today(String indexmcap_today) {
        Indexmcap_today = indexmcap_today;
    }

    public String getPriceperchange() {
        return priceperchange;
    }

    public void setPriceperchange(String priceperchange) {
        this.priceperchange = priceperchange;
    }

    public String getNewIndexValue() {
        return NewIndexValue;
    }

    public void setNewIndexValue(String newIndexValue) {
        NewIndexValue = newIndexValue;
    }

    public String getPointchange() {
        return pointchange;
    }

    public void setPointchange(String pointchange) {
        this.pointchange = pointchange;
    }

    public String getPerchange() {
        return perchange;
    }

    public void setPerchange(String perchange) {
        this.perchange = perchange;
    }
}

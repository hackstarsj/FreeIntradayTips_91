package com.silverlinesoftwares.intratips.models;

public class ActiveStockModel {
    String symbol;
    String series;
    String openPrice;
    String highPrice;
    String ltp;
    String previousPrice;
    String netPrice;
    String tradedQuantity;
    String turnoverInLakhs;
    String lastCorpAnnouncementDate;
    String lastCorpAnnouncement;


    public String getSymbol() {
        return symbol;
    }

    public String getLtp() {
        return ltp;
    }

    public String getPreviousPrice() {
        return previousPrice;
    }

    public String getNetPrice() {
        return netPrice;
    }

    public String getTradedQuantity() {
        return tradedQuantity;
    }

}

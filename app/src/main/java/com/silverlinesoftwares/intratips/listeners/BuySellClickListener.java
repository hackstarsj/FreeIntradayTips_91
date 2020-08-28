package com.silverlinesoftwares.intratips.listeners;

public interface BuySellClickListener {
    void onBuy(String symbol,String price);
    void onSell(String symbol,String price);
    void onTechnical(String symbol);
}

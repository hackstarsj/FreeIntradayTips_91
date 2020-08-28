package com.silverlinesoftwares.intratips.models;

import java.util.ArrayList;
import java.util.List;

public class MoverLooserMenu {
    String name;
    String data_text;

    public MoverLooserMenu(String name, String data_text) {
        this.name = name;
        this.data_text = data_text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData_text() {
        return data_text;
    }

    public void setData_text(String data_text) {
        this.data_text = data_text;
    }

    public static List<MoverLooserMenu> getItemMenu(){
        List<MoverLooserMenu> looserMenus=new ArrayList<>();
        looserMenus.add(new MoverLooserMenu("NIFTY 50","NIFTY%2050"));
        looserMenus.add(new MoverLooserMenu("NIFTY Next 50","NIFTY%20NEXT%2050"));
        looserMenus.add(new MoverLooserMenu("NIFTY 100","NIFTY%20100"));
        looserMenus.add(new MoverLooserMenu("NIFTY 200","NIFTY%20200"));
        looserMenus.add(new MoverLooserMenu("NIFTY 500","NIFTY%20500"));
        looserMenus.add(new MoverLooserMenu("NIFTY Midcap 150","NIFTY%20MIDCAP%20150"));
        looserMenus.add(new MoverLooserMenu("NIFTY Midcap 50","NIFTY%20MIDCAP%2050"));
        looserMenus.add(new MoverLooserMenu("NIFTY Midcap 100","NIFTY%20MIDCAP%20100"));
        looserMenus.add(new MoverLooserMenu("NIFTY Smallcap 250","NIFTY%20SMALLCAP%20250"));
        looserMenus.add(new MoverLooserMenu("NIFTY Smallcap 50","NIFTY%20SMALLCAP%2050"));
        looserMenus.add(new MoverLooserMenu("NIFTY Smallcap 100","NIFTY%20SMLCAP%20100"));
        looserMenus.add(new MoverLooserMenu("NIFTY LargeMidcap 250","NIFTY%20LARGEMIDCAP%20250"));
        looserMenus.add(new MoverLooserMenu("NIFTY MidSmallcap 400","NIFTY%20MIDSMALLCAP%20400"));
        looserMenus.add(new MoverLooserMenu("NIFTY Auto","NIFTY%20AUTO"));
        looserMenus.add(new MoverLooserMenu("NIFTY Bank","NIFTY%20BANK"));
        looserMenus.add(new MoverLooserMenu("NIFTY Financial Services","NIFTY%20FINANCIAL%20SERVICES"));
        looserMenus.add(new MoverLooserMenu("NIFTY FMCG","NIFTY%20FMCG"));
        looserMenus.add(new MoverLooserMenu("NIFTY IT","NIFTY%20IT"));
        looserMenus.add(new MoverLooserMenu("NIFTY Media","NIFTY%20MEDIA"));
        looserMenus.add(new MoverLooserMenu("NIFTY Metal","NIFTY%20METAL"));
        looserMenus.add(new MoverLooserMenu("NIFTY Pharma","NIFTY%20PHARMA"));
        looserMenus.add(new MoverLooserMenu("NIFTY Private Bank","NIFTY%20PVT%20BANK"));
        looserMenus.add(new MoverLooserMenu("NIFTY PSU Bank","NIFTY%20PSU%20BANK"));
        looserMenus.add(new MoverLooserMenu("NIFTY Realty","NIFTY%20REALTY"));
        looserMenus.add(new MoverLooserMenu("NIFTY Aditya Birla Group","NIFTY%20ADITYA%20BIRLA%20GROUP"));
        looserMenus.add(new MoverLooserMenu("NIFTY Commodities","NIFTY%20COMMODITIES"));
        looserMenus.add(new MoverLooserMenu("NIFTY CPSE","NIFTY%20CPSE"));
        looserMenus.add(new MoverLooserMenu("NIFTY Energy","NIFTY%20ENERGY"));
        looserMenus.add(new MoverLooserMenu("NIFTY India Consumption","NIFTY%20CONSUMPTION"));
        looserMenus.add(new MoverLooserMenu("NIFTY Infrastructure","NIFTY%20INFRA"));
        looserMenus.add(new MoverLooserMenu("NIFTY Mahindra Group","NIFTY%20MAHINDRA%20Group"));
        looserMenus.add(new MoverLooserMenu("NIFTY Midcap Liquid 15","NIFTY%20MID%20LIQ%2015"));
        looserMenus.add(new MoverLooserMenu("NIFTY MNC","NIFTY%20MNC"));
        looserMenus.add(new MoverLooserMenu("NIFTY PSE","NIFTY%20PSE"));
        looserMenus.add(new MoverLooserMenu("NIFTY Services Sector","NIFTY%20SERV%20SECTOR"));
        looserMenus.add(new MoverLooserMenu("NIFTY Shariah 25","NIFTY%20SHARIAH%2025"));
        looserMenus.add(new MoverLooserMenu("NIFTY Tata Group","NIFTY%20TATA%20GROUP"));
        looserMenus.add(new MoverLooserMenu("NIFTY Tata Group 25% Cap","NIFTY%20TATA%20GROUP%2025%%20CAP"));
        looserMenus.add(new MoverLooserMenu("NIFTY100 Liquid 15","NIFTY100%20LIQ%2015"));
        looserMenus.add(new MoverLooserMenu("NIFTY 50 Shariah","NIFTY50%20SHARIAH"));
        looserMenus.add(new MoverLooserMenu("NIFTY 500 Shariah","NIFTY500%20SHARIAH"));
        looserMenus.add(new MoverLooserMenu("NIFTY SME EMERGE","NIFTY%20SME%20EMERGE"));
        looserMenus.add(new MoverLooserMenu("NIFTY 100 Equal Weight","NIFTY100%20EQL%20WGT"));
        looserMenus.add(new MoverLooserMenu("Nifty100 Low Volatility 30","Nifty100%20LOW%20VOLATILITY%2030"));
        looserMenus.add(new MoverLooserMenu("NIFTY Alpha 50","NIFTY%20ALPHA%2050"));
        looserMenus.add(new MoverLooserMenu("NIFTY Dividend Opportunities 50","NIFTY%20DIV%20OPPS%2050"));
        looserMenus.add(new MoverLooserMenu("NIFTY High Beta 50","NIFTY%20High%20Beta%2050"));
        looserMenus.add(new MoverLooserMenu("NIFTY Low Volatility 50","NIFTY%20Low%20Volatility%2050"));
        looserMenus.add(new MoverLooserMenu("NIFTY Alpha Low-Volatility 30","NIFTY%20Alpha%20Low-Volatility%2030"));
        looserMenus.add(new MoverLooserMenu("NIFTY Quality Low-Volatility 30","NIFTY%20Quality%20Low-Volatility%2030"));
        looserMenus.add(new MoverLooserMenu("NIFTY Alpha Quality Value Low-Volatility 30","NIFTY%20Alpha%20Quality%20Value%20Low-Volatility%2030"));
        looserMenus.add(new MoverLooserMenu("NIFTY Alpha Quality Low-Volatility 30","NIFTY%20Alpha%20Quality%20Low-Volatility%2030"));
        looserMenus.add(new MoverLooserMenu("NIFTY100 Quality 30","NIFTY100%20QUALTY30"));
        looserMenus.add(new MoverLooserMenu("NIFTY Growth Sectors 15","NIFTY%20GROWSECT%2015"));
        looserMenus.add(new MoverLooserMenu("NIFTY50 Dividend Points","NIFTY50%20DIV%20POINT"));
        looserMenus.add(new MoverLooserMenu("NIFTY50 Equal Weight","NIFTY50%20Equal%20Weight"));
        looserMenus.add(new MoverLooserMenu("NIFTY50 Value 20","NIFTY50%20Value%2020"));
        looserMenus.add(new MoverLooserMenu("NIFTY500 VALUE 50","NIFTY500%20VALUE%2050"));
        return looserMenus;
    }
}

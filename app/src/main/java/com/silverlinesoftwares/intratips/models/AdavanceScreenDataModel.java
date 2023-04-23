package com.silverlinesoftwares.intratips.models;

import java.util.ArrayList;
import java.util.List;

public class AdavanceScreenDataModel {
    public static List<ScreenerItem> getAdavanceScreenDataModel(){
        List<ScreenerItem> allItems=new ArrayList<>();
        List<ScreenerSubItem> screenerSubItems111=new ArrayList<>();
        allItems.add(new ScreenerItem("OPEN=LOW=HIGH",screenerSubItems111));

        List<ScreenerSubItem> screenerSubItems=new ArrayList<>();
        screenerSubItems.add(new ScreenerSubItem("High Market Cap","https://www.topstockresearch.com/KeyFundamentals/HighMarketCapStocks.html","1"));
        screenerSubItems.add(new ScreenerSubItem("Low Market Cap","https://www.topstockresearch.com/KeyFundamentals/LowMarketCapStocks.html","1"));
        screenerSubItems.add(new ScreenerSubItem("High EPS","https://www.topstockresearch.com/KeyFundamentals/StocksWithHighEpsInFiscalYear.html","1"));
        screenerSubItems.add(new ScreenerSubItem("High EPS TTM","https://www.topstockresearch.com/KeyFundamentals/StocksWithHighEpsTtm.html","1"));
        screenerSubItems.add(new ScreenerSubItem("PE Between 10 to 20","https://www.topstockresearch.com/KeyFundamentals/StocksWithPEBetween10to20.html","1"));
        allItems.add(new ScreenerItem("Key Financial Data",screenerSubItems));

        List<ScreenerSubItem> screenerSubItems2=new ArrayList<>();
        screenerSubItems2.add(new ScreenerSubItem("Bullish Engulfing","https://www.topstockresearch.com/CandleStickPatterns/ReportOnIndianStockFormingBullishEngulfingPattern.html","3a"));
        screenerSubItems2.add(new ScreenerSubItem("Bullish Harami","https://www.topstockresearch.com/CandleStickPatterns/ReportOnIndianStockFormingBullishHaramiPattern.html","3a"));
        screenerSubItems2.add(new ScreenerSubItem("Morning Star","https://www.topstockresearch.com/CandleStickPatterns/ReportOnIndianStockFormingMorningStarPattern.html","3a"));
        screenerSubItems2.add(new ScreenerSubItem("Bullish Piercing","https://www.topstockresearch.com/CandleStickPatterns/ReportOnIndianStockFormingThreeWhiteSoldierPattern.html","3a"));
        screenerSubItems2.add(new ScreenerSubItem("Three White Soldiers","https://www.topstockresearch.com/CandleStickPatterns/ReportOnIndianStockFormingThreeWhiteSoldierPattern.html","3a"));
        allItems.add(new ScreenerItem("Bullish Candlestick Pattern",screenerSubItems2));

        List<ScreenerSubItem> screenerSubItems3=new ArrayList<>();
        screenerSubItems3.add(new ScreenerSubItem("Golden Cross-50/200 MA","https://www.topstockresearch.com/EMACrossOver/FiftyDayExponentialMovingAverageAboveTwoHundredDays.html","4a"));
        screenerSubItems3.add(new ScreenerSubItem("Stock Above 100 SMA","https://www.topstockresearch.com/StocksSimpleMovingAverageReport/StocksJustAboveHundredDaysSimpleMovingAverage.html","4b"));
        screenerSubItems3.add(new ScreenerSubItem("Trending Up By SMA","https://www.topstockresearch.com/TrendingStocks/RisingFifteenDayMovingAverage.html","4c"));
        screenerSubItems3.add(new ScreenerSubItem("5 EMA Above 20 EMA ","https://www.topstockresearch.com/EMACrossOver/FiveDayExponentialMovingAverageAboveTwentyDays.html","4c"));
        screenerSubItems3.add(new ScreenerSubItem("13 EMA Above 34 EMA ","https://www.topstockresearch.com/EMACrossOver/ThirteenDayExponentialMovingAverageAboveThirtyFourDays.html","4c"));
        allItems.add(new ScreenerItem("Bullish By Moving Average",screenerSubItems3));

        List<ScreenerSubItem> screenerSubItems4=new ArrayList<>();
        screenerSubItems4.add(new ScreenerSubItem("Macd Above Signal","https://www.topstockresearch.com/MACDIndicatorReports/MACDCrossingAboveSignalLine.html","4c"));
        screenerSubItems4.add(new ScreenerSubItem("Trending ADX Indicator","https://www.topstockresearch.com/AverageDirectionalIndex/VeryStrongTrendingStocksByADXIndicatorInIndianStocks.html","5"));
        screenerSubItems4.add(new ScreenerSubItem("Buying Pressure by CMF","https://www.topstockresearch.com/ChaikinMoneyFlow/CMFVeryStrongBuyingPressureInIndianMarket.html","5"));
        screenerSubItems4.add(new ScreenerSubItem("Above Bollinger Bands","https://www.topstockresearch.com/StockSupportAndResistanceReport/StocksBrokeResistanceUsingBollingerBand.html","4c"));
        screenerSubItems4.add(new ScreenerSubItem("PSAR Bullish Reversal","https://www.topstockresearch.com/PSAR/PSARBullishReversalDailyChart.html","4c"));
        allItems.add(new ScreenerItem("Bullish Technical Indicator",screenerSubItems4));

        List<ScreenerSubItem> screenerSubItems5=new ArrayList<>();
        screenerSubItems5.add(new ScreenerSubItem("By Rising RSI","https://www.topstockresearch.com/StocksOverSoldReport/OverSoldStockWithRisingRSI.html","4c"));
//        screenerSubItems5.add(new ScreenerSubItem("By Williams %R","https://www.topstockresearch.com/WilliamsR/OverSoldSharesByWilliamsR.html","6"));
        screenerSubItems5.add(new ScreenerSubItem("By Money Flow Index","https://www.topstockresearch.com/MoneyFlowIndex/OversoldStocksByMFIInIndianMarket.html","6"));
        screenerSubItems5.add(new ScreenerSubItem("By Fast Stochastic","https://www.topstockresearch.com/StocksOverBoughtReport/OverBoughtSignalByFastStochastic.html","6"));
        screenerSubItems5.add(new ScreenerSubItem("BY CCI","https://www.topstockresearch.com/CCI/OversoldStocksByCCI.html","6"));
        allItems.add(new ScreenerItem("Oversold Stocks",screenerSubItems5));


        List<ScreenerSubItem> screenerSubItems6=new ArrayList<>();
        screenerSubItems6.add(new ScreenerSubItem("Daily Closing Higher","https://www.topstockresearch.com/StockDailyTrending/StockDailyClosingHigher.html","4c"));
//        screenerSubItems6.add(new ScreenerSubItem("Higher Highs/Higher Lows","https://www.topstockresearch.com/HigherHighHigherLows/HigherHighsHigherLowsST.html","4c"));
        allItems.add(new ScreenerItem("Trending Stocks",screenerSubItems6));


        List<ScreenerSubItem> screenerSubItems7=new ArrayList<>();
        screenerSubItems7.add(new ScreenerSubItem("By Pivot Point","https://www.topstockresearch.com/PivotPoint/IntradaySupportAndResistanceUsingPivotPoint.html","8a"));
        screenerSubItems7.add(new ScreenerSubItem("Fibonacci 38.2 Support Level","https://www.topstockresearch.com/fibonacci/NearFib382RetracementLevelSTSupport.html","8b"));
        screenerSubItems7.add(new ScreenerSubItem("Near Fibonacci 61.8 Support Level","https://www.topstockresearch.com/fibonacci/NearFib618RetracementLevelSTSupport.html","8b"));
        screenerSubItems7.add(new ScreenerSubItem("By Camarilla Pivot Point","https://www.topstockresearch.com/PivotPoint/CamarillaPivotPointIndianStocks.html","8d"));
        screenerSubItems7.add(new ScreenerSubItem("By Woodies Pivot Point","https://www.topstockresearch.com/PivotPoint/WoodiesPivotPointIndianStocks.html","8d"));
        allItems.add(new ScreenerItem("Support & Resistance ",screenerSubItems7));

        List<ScreenerSubItem> screenerSubItems8=new ArrayList<>();
        screenerSubItems8.add(new ScreenerSubItem("High Beta Stocks","https://www.topstockresearch.com/BetaStocks/HighBetaStocksLongTerm.html","8d"));
        screenerSubItems8.add(new ScreenerSubItem("Volatile On Daily Period","https://www.topstockresearch.com/MostVolatieShare/MostVolatileShareInLastFiveDaysPeriod.html","4c"));
        screenerSubItems8.add(new ScreenerSubItem("Volatile On Weekly Period","https://www.topstockresearch.com/MostVolatieShare/MostVolatileShareInLastTenWeeksPeriod.html","4c"));
        screenerSubItems8.add(new ScreenerSubItem("Volatile On Monthly Period","https://www.topstockresearch.com/MostVolatieShare/MostVolatileShareInLastSixMonthsPeriod.html","4c"));
        screenerSubItems8.add(new ScreenerSubItem("Low Beta Stocks","https://www.topstockresearch.com/BetaStocks/LowBetaStocksLongTerm.html","8d"));
        allItems.add(new ScreenerItem("Volatile/Beta Shares",screenerSubItems8));


        List<ScreenerSubItem> screenerSubItems10=new ArrayList<>();
        screenerSubItems10.add(new ScreenerSubItem("Bearish Engulfing","https://www.topstockresearch.com/CandleStickPatterns/ReportOnIndianStockFormingBearishEngulfingPattern.html","4c"));
        screenerSubItems10.add(new ScreenerSubItem("Bearish Harami","https://www.topstockresearch.com/CandleStickPatterns/ReportOnIndianStockFormingBearishHaramiPattern.html","4c"));
        screenerSubItems10.add(new ScreenerSubItem("Dark Cloud Cover","https://www.topstockresearch.com/CandleStickPatterns/ReportOnIndianStockFormingBearishHaramiPattern.html","4c"));
        screenerSubItems10.add(new ScreenerSubItem("Evening Star","https://www.topstockresearch.com/CandleStickPatterns/ReportOnIndianStockFormingEveningStarPattern.html","4c"));
        screenerSubItems10.add(new ScreenerSubItem("Three Dark Crows","https://www.topstockresearch.com/CandleStickPatterns/ReportOnIndianStockFormingThreeDarkCrowsPattern.html","4c"));
        allItems.add(new ScreenerItem("Bearish Candlestick Pattern  ",screenerSubItems10));


        List<ScreenerSubItem> screenerSubItems11=new ArrayList<>();
        screenerSubItems11.add(new ScreenerSubItem("Dead Cross-50/200 MA","https://www.topstockresearch.com/EMACrossOver/FiftyDayExponentialMovingAverageBelowTwoHundredDays.html","4c"));
        screenerSubItems11.add(new ScreenerSubItem("Stock Below 100 SMA","https://www.topstockresearch.com/EMACrossOver/FiftyDayExponentialMovingAverageBelowTwoHundredDays.html","4c"));
        screenerSubItems11.add(new ScreenerSubItem("Trending Down By SMA","https://www.topstockresearch.com/TrendingStocks/FallingFiftyDayMovingAverage.html","4c"));
        screenerSubItems11.add(new ScreenerSubItem("3 EMA Below 13 EMA ","https://www.topstockresearch.com/EMACrossOver/ThreeDayExponentialMovingAverageBelowThirteenDays.html","4c"));
        screenerSubItems11.add(new ScreenerSubItem("5 EMA Below 20 EMA ","https://www.topstockresearch.com/EMACrossOver/FiveDayExponentialMovingAverageBelowTwentyDays.html","4c"));
        allItems.add(new ScreenerItem("Bearish By Moving Average ",screenerSubItems11));


        List<ScreenerSubItem> screenerSubItems12=new ArrayList<>();
        screenerSubItems12.add(new ScreenerSubItem("Macd Below Signal","https://www.topstockresearch.com/ChaikinMoneyFlow/CMFStrongSelingPressureInIndianMarket.html","4c"));
        screenerSubItems12.add(new ScreenerSubItem("Bearish Reversal BY ADX ","https://www.topstockresearch.com/AverageDirectionalIndex/ADXPlusDICrossAbovePlusDIInIndianStocks.html","4c"));
        screenerSubItems12.add(new ScreenerSubItem("Selling Pressure by CMF","https://www.topstockresearch.com/ChaikinMoneyFlow/CMFStrongSelingPressureInIndianMarket.html","4c"));
        screenerSubItems12.add(new ScreenerSubItem("Below Bollinger Bands","https://www.topstockresearch.com/StockSupportAndResistanceReport/StocksBrokeSupportUsingBollingerBands.html","4c"));
        screenerSubItems12.add(new ScreenerSubItem("PSAR Bearish Reversal","https://www.topstockresearch.com/PSAR/PSARBearishReversalDailyChart.html","4c"));
        allItems.add(new ScreenerItem("Bearish Technical Indicator",screenerSubItems12));


        List<ScreenerSubItem> screenerSubItems13=new ArrayList<>();
        screenerSubItems13.add(new ScreenerSubItem("By Falling RSI","https://www.topstockresearch.com/StocksOverBoughtReport/OverBoughtStockWithFallingRSI.html","4c"));
        screenerSubItems13.add(new ScreenerSubItem("By Williams %R","https://www.topstockresearch.com/WilliamsR/OverboughtStocksByWilliamsR.html","4c"));
        screenerSubItems13.add(new ScreenerSubItem("By Money Flow Index","https://www.topstockresearch.com/MoneyFlowIndex/OverboughtStockByMFIInIndianMarket.html","4c"));
        screenerSubItems13.add(new ScreenerSubItem("By Fast Stochastic","https://www.topstockresearch.com/StocksOverBoughtReport/OverBoughtSignalByFastStochastic.html","4c"));
        screenerSubItems13.add(new ScreenerSubItem("BY CCI","https://www.topstockresearch.com/CCI/OverboughtStocksByCCI.html","4c"));
        allItems.add(new ScreenerItem("Overbought Stocks",screenerSubItems13));

        List<ScreenerSubItem> screenerSubItems14=new ArrayList<>();
        screenerSubItems14.add(new ScreenerSubItem("EBITDA  ","https://www.topstockresearch.com/QoQN2P/EbidtaQN2P.html","15"));
        screenerSubItems14.add(new ScreenerSubItem("Sales/Revenue","https://www.topstockresearch.com/QoQN2P/EPSQN2P.html","15"));
        screenerSubItems14.add(new ScreenerSubItem("Operting Profit","https://www.topstockresearch.com/QoQN2P/OperatingProfitQN2P.html","15"));
        allItems.add(new ScreenerItem("Qtrly Financial Data Turning ",screenerSubItems14));


        List<ScreenerSubItem> screenerSubItems16=new ArrayList<>();
        screenerSubItems16.add(new ScreenerSubItem("Profit Before Tax","https://www.topstockresearch.com/Y0YN2P/ProfitBeforeTax1YN2P.html","17"));
        screenerSubItems16.add(new ScreenerSubItem("Net Profit","https://www.topstockresearch.com/Y0YN2P/NetProfit1YN2P.html","17"));
        screenerSubItems16.add(new ScreenerSubItem("Operting Profit","https://www.topstockresearch.com/Y0YN2P/OperatingProfit1YN2P.html","17"));
        allItems.add(new ScreenerItem("Annual Financial Data Turning  ",screenerSubItems16));

        List<ScreenerSubItem> screenerSubItems17=new ArrayList<>();
        screenerSubItems17.add(new ScreenerSubItem("Earning Per Share(EPS)","https://www.topstockresearch.com/QoQContinousGrowth/ContinousRiseEPS.html","18"));
        screenerSubItems17.add(new ScreenerSubItem("Revenue/Sales","https://www.topstockresearch.com/QoQContinousGrowth/ContinousRiseRevenue.html","18"));
        screenerSubItems17.add(new ScreenerSubItem("EBITDA","https://www.topstockresearch.com/QoQContinousGrowth/ContinousRiseEbidta.html","18"));
        allItems.add(new ScreenerItem("Continous Growth For Quarters",screenerSubItems17));

        List<ScreenerSubItem> screenerSubItems18=new ArrayList<>();
        screenerSubItems18.add(new ScreenerSubItem("Net Cash Flow","https://www.topstockresearch.com/YoYContinousGrowth/ContinousRiseNetCashFlow.html","19"));
        screenerSubItems18.add(new ScreenerSubItem("EBITDA","https://www.topstockresearch.com/YoYContinousGrowth/ContinousEBITDAJump.html","19"));
        screenerSubItems18.add(new ScreenerSubItem("Revenue/Sales","https://www.topstockresearch.com/YoYContinousGrowth/ContinousRevenueJump.html","19"));
        allItems.add(new ScreenerItem("Continous Growth for Years",screenerSubItems18));

        List<ScreenerSubItem> screenerSubItems19=new ArrayList<>();
        screenerSubItems19.add(new ScreenerSubItem("Book Value/Share","https://www.topstockresearch.com/YoYGrowth/HighBookValueGrowth.html","20"));
        screenerSubItems19.add(new ScreenerSubItem("Revenue/Sales","https://www.topstockresearch.com/YoYGrowth/JumpInRevenue.html","20"));
        screenerSubItems19.add(new ScreenerSubItem("Assets","https://www.topstockresearch.com/YoYGrowth/TotalAssetsGrowth.html","20"));
        allItems.add(new ScreenerItem("YoY Growth",screenerSubItems19));

        List<ScreenerSubItem> screenerSubItems20=new ArrayList<>();
        screenerSubItems20.add(new ScreenerSubItem("Earning Per Share(EPS)","https://www.topstockresearch.com/QoQGrowth/EPS1QGrowth.html","21"));
        screenerSubItems20.add(new ScreenerSubItem("Revenue/Sales","https://www.topstockresearch.com/QoQGrowth/Revenue1QGrowth.html","21"));
        screenerSubItems20.add(new ScreenerSubItem("Total Income","https://www.topstockresearch.com/QoQGrowth/TotalIncome1QGrowth.html","21"));
        allItems.add(new ScreenerItem("QoQ Growth",screenerSubItems20));

        List<ScreenerSubItem> screenerSubItems21=new ArrayList<>();
        screenerSubItems21.add(new ScreenerSubItem("3 Yrs Book Value/Share Growth","https://www.topstockresearch.com/YoYGrowth/BookValue3YrCagrGrowth.html","22"));
        screenerSubItems21.add(new ScreenerSubItem("3 Yrs Revenue/Sales Growth","https://www.topstockresearch.com/YoYGrowth/Revenue3YrCagrGrowth.html","22"));
        screenerSubItems21.add(new ScreenerSubItem("3 Yrs PBT Growth","https://www.topstockresearch.com/YoYGrowth/ProfitBeforeTax3YrCagrGrowth.html","22"));
        allItems.add(new ScreenerItem("CAGR Growth 3 Yrs",screenerSubItems21));


        List<ScreenerSubItem> screenerSubItems22=new ArrayList<>();
        screenerSubItems22.add(new ScreenerSubItem("5 Yrs Book Value/Share Growth","https://www.topstockresearch.com/YoYGrowth/BookValue5YrCagrGrowth.html","23"));
        screenerSubItems22.add(new ScreenerSubItem("5 Yrs Revenue/Sales Growth","https://www.topstockresearch.com/YoYGrowth/Revenue5YrCagrGrowth.html","23"));
        screenerSubItems22.add(new ScreenerSubItem("5 Yrs PBT Growth","https://www.topstockresearch.com/YoYGrowth/ProfitBeforeTax5YrCagrGrowth.html","23"));
        allItems.add(new ScreenerItem("CAGR Growth 5 Yrs",screenerSubItems22));

        List<ScreenerSubItem> screenerSubItems23=new ArrayList<>();
        screenerSubItems23.add(new ScreenerSubItem("10 Yrs Book Value/Share Growth","https://www.topstockresearch.com/YoYGrowth/BookValue10YrCagrGrowth.html","24"));
        screenerSubItems23.add(new ScreenerSubItem("10 Yrs Revenue/Sales Growth","https://www.topstockresearch.com/YoYGrowth/Revenue10YrCagrGrowth.html","24"));
        screenerSubItems23.add(new ScreenerSubItem("10 Yrs PBT Growth","https://www.topstockresearch.com/YoYGrowth/ProfitBeforeTax10YrCagrGrowth.html","24"));
        allItems.add(new ScreenerItem("CAGR Growth 10 Yrs",screenerSubItems23));


        return allItems;
    }

}

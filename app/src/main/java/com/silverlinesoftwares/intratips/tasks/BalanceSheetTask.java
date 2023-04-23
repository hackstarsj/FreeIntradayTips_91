package com.silverlinesoftwares.intratips.tasks;

import android.os.Handler;
import android.os.Looper;

import com.silverlinesoftwares.intratips.listeners.IncomeStateListener;
import com.silverlinesoftwares.intratips.models.IncomeStatementModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BalanceSheetTask {


    final IncomeStateListener gainerLooserListener;

    public BalanceSheetTask(IncomeStateListener gainerLooserListener){
        this.gainerLooserListener=gainerLooserListener;
    }

    protected void onPostExecute(String string) {
        if(string!=null){
            try {
                JSONObject jsonObject1=new JSONObject(string);
                JSONObject jsonObject=jsonObject1.getJSONObject("quoteSummary");
                JSONArray result=jsonObject.getJSONArray("result");
                JSONObject incomeStatementHistoryQuarterly=result.getJSONObject(0).getJSONObject("incomeStatementHistoryQuarterly");
                JSONObject cashflowStatementHistory=result.getJSONObject(0).getJSONObject("cashflowStatementHistory");
                JSONObject cashflowStatementHistoryQuarterly=result.getJSONObject(0).getJSONObject("cashflowStatementHistoryQuarterly");
                JSONObject balanceSheetHistoryQuarterly=result.getJSONObject(0).getJSONObject("balanceSheetHistoryQuarterly");
                JSONObject balanceSheetHistory=result.getJSONObject(0).getJSONObject("balanceSheetHistory");
                JSONObject incomeStatementHistory=result.getJSONObject(0).getJSONObject("incomeStatementHistory");


                List<IncomeStatementModel> incomeStatementModels_q=getIncomestatementYearly(balanceSheetHistoryQuarterly);

                List<IncomeStatementModel> incomeStatementModels_y=getIncomestatementYearly(balanceSheetHistory);


                gainerLooserListener.onSucess(incomeStatementModels_q,incomeStatementModels_y);

            } catch (JSONException e) {
                e.printStackTrace();
                gainerLooserListener.onFailed("Server Error!");
            }

            // gainerLooserListener.onSucess(string);
        }
        else{
            gainerLooserListener.onFailed("Server Error!");
        }

    }


    public void execute(String... strings) {
        Executor executor = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            String data=doInBackground(strings);
            handler.post(()-> onPostExecute(data));
        });
    }

    protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            client.retryOnConnectionFailure();
            client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build(); // connect timeout
        Date date=new Date();
        long timeMilli = date.getTime();
        timeMilli=timeMilli-(1000*60);

        String url="https://query1.finance.yahoo.com/v10/finance/quoteSummary/"+strings[0]+"?formatted=true&crumb=4K5Yj4CtKsW&lang=en-US&region=US&modules=incomeStatementHistory%2CcashflowStatementHistory%2CbalanceSheetHistory%2CincomeStatementHistoryQuarterly%2CcashflowStatementHistoryQuarterly%2CbalanceSheetHistoryQuarterly&corsDomain=finance.yahoo.com";

            Request request =
                    new Request.Builder()
                            .url(url)
                            .addHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0")
                            .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            }
        if (response != null && response.isSuccessful()) {
                try {
                    if (response.body() != null) {
                        return response.body().string();
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return null;
    }




    private List<IncomeStatementModel> getIncomestatementYearly(JSONObject incomeStatementHistoryQuarterly){

        List<IncomeStatementModel> incomeStatementModels=new ArrayList<>();
        try {
            JSONArray incomeStatementHistoryQuarterly_data = incomeStatementHistoryQuarterly.getJSONArray("balanceSheetStatements");


            for (int i = 0; i < incomeStatementHistoryQuarterly_data.length(); i++) {


                if (i == 0) {

                    IncomeStatementModel incomeStatementModel1 = new IncomeStatementModel();
                    incomeStatementModel1.setTitle("Period Ending");
                    incomeStatementModel1.setSymbol("endDate");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("endDate")) {
                        JSONObject researchDevelopment1 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("endDate");
                        if (researchDevelopment1.has("fmt")) {
                            incomeStatementModel1.setData_1(researchDevelopment1.getString("fmt"));
                        } else {
                            incomeStatementModel1.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel1.setData_1("-");
                    }
                    incomeStatementModel1.setIs_bold(true);
                    incomeStatementModel1.setIs_head(true);
                    incomeStatementModel1.setIs_center(true);
                    incomeStatementModels.add(incomeStatementModel1);

                    IncomeStatementModel incomeStatementModel2 = new IncomeStatementModel();
                    incomeStatementModel2.setTitle("Current Assets");
                    incomeStatementModel2.setSymbol("current_assets");
                    incomeStatementModel2.setData_1("");
                    incomeStatementModel2.setIs_bold(true);
                    incomeStatementModel2.setIs_head(true);
                    incomeStatementModel2.setIs_center(true);
                    incomeStatementModels.add(incomeStatementModel2);

                    IncomeStatementModel incomeStatementModel3 = new IncomeStatementModel();
                    incomeStatementModel3.setTitle("Cash And Cash Equivalents");
                    incomeStatementModel3.setSymbol("cash");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("cash")) {
                        JSONObject researchDevelopment2 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("cash");
                        if (researchDevelopment2.has("longFmt")) {
                            incomeStatementModel3.setData_1(researchDevelopment2.getString("longFmt"));
                        } else {
                            incomeStatementModel3.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel3.setData_1("-");
                    }
                    incomeStatementModel3.setIs_bold(false);
                    incomeStatementModel3.setIs_head(false);
                    incomeStatementModel3.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel3);


                    IncomeStatementModel incomeStatementModel4 = new IncomeStatementModel();
                    incomeStatementModel4.setTitle("Short Term Investments");
                    incomeStatementModel4.setSymbol("shortTermInvestments");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("shortTermInvestments")) {
                        JSONObject researchDevelopment3 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("shortTermInvestments");
                        if (researchDevelopment3.has("longFmt")) {
                            incomeStatementModel4.setData_1(researchDevelopment3.getString("longFmt"));
                        } else {
                            incomeStatementModel4.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel4.setData_1("-");
                    }
                    incomeStatementModel4.setIs_bold(false);
                    incomeStatementModel4.setIs_head(false);
                    incomeStatementModel4.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel4);


                    IncomeStatementModel incomeStatementModel5 = new IncomeStatementModel();
                    incomeStatementModel5.setTitle("Net Receivables");
                    incomeStatementModel5.setSymbol("netReceivables");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("netReceivables")) {
                        JSONObject researchDevelopment4 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netReceivables");
                        if (researchDevelopment4.has("longFmt")) {
                            incomeStatementModel5.setData_1(researchDevelopment4.getString("longFmt"));
                        } else {
                            incomeStatementModel5.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel5.setData_1("-");
                    }
                    incomeStatementModel5.setIs_bold(false);
                    incomeStatementModel5.setIs_head(false);
                    incomeStatementModel5.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel5);


                    IncomeStatementModel incomeStatementModel6 = new IncomeStatementModel();
                    incomeStatementModel6.setTitle("Inventory");
                    incomeStatementModel6.setSymbol("inventory");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("inventory")) {
                        JSONObject researchDevelopment5 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("inventory");
                        if (researchDevelopment5.has("longFmt")) {
                            incomeStatementModel6.setData_1(researchDevelopment5.getString("longFmt"));
                        } else {
                            incomeStatementModel6.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel6.setData_1("-");
                    }
                    incomeStatementModel6.setIs_bold(false);
                    incomeStatementModel6.setIs_head(false);
                    incomeStatementModel6.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel6);

                    IncomeStatementModel incomeStatementModel7 = new IncomeStatementModel();
                    incomeStatementModel7.setTitle("Other Current Assets");
                    incomeStatementModel7.setSymbol("otherCurrentAssets");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("otherCurrentAssets")) {
                        JSONObject researchDevelopment6 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherCurrentAssets");
                        if (researchDevelopment6.has("longFmt")) {
                            incomeStatementModel7.setData_1(researchDevelopment6.getString("longFmt"));
                        } else {
                            incomeStatementModel7.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel7.setData_1("-");
                    }
                    incomeStatementModel7.setIs_bold(false);
                    incomeStatementModel7.setIs_head(false);
                    incomeStatementModel7.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel7);

                    IncomeStatementModel incomeStatementModel8 = new IncomeStatementModel();
                    incomeStatementModel8.setTitle("Total Current Assets");
                    incomeStatementModel8.setSymbol("totalCurrentAssets");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("totalCurrentAssets")) {
                        JSONObject researchDevelopment7 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalCurrentAssets");
                        if (researchDevelopment7.has("longFmt")) {
                            incomeStatementModel8.setData_1(researchDevelopment7.getString("longFmt"));
                        } else {
                            incomeStatementModel8.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel8.setData_1("-");
                    }

                    incomeStatementModel8.setIs_bold(true);
                    incomeStatementModel8.setIs_head(false);
                    incomeStatementModel8.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel8);


                    IncomeStatementModel incomeStatementModel9 = new IncomeStatementModel();
                    incomeStatementModel9.setTitle("Long Term Investments");
                    incomeStatementModel9.setSymbol("longTermInvestments");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("longTermInvestments")) {
                        JSONObject researchDevelopment8 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("longTermInvestments");
                        if (researchDevelopment8.has("longFmt")) {
                            incomeStatementModel9.setData_1(researchDevelopment8.getString("longFmt"));
                        } else {
                            incomeStatementModel9.setData_1("-");
                        }
                    }
                    else{

                        incomeStatementModel9.setData_1("-");
                    }

                    incomeStatementModel9.setIs_bold(false);
                    incomeStatementModel9.setIs_head(false);
                    incomeStatementModel9.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel9);


                    IncomeStatementModel incomeStatementModel10 = new IncomeStatementModel();
                    incomeStatementModel10.setTitle("Property Plant and Equipment");
                    incomeStatementModel10.setSymbol("propertyPlantEquipment");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("propertyPlantEquipment")) {
                        JSONObject researchDevelopment10 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("propertyPlantEquipment");
                        if (researchDevelopment10.has("longFmt")) {
                            incomeStatementModel10.setData_1(researchDevelopment10.getString("longFmt"));
                        } else {
                            incomeStatementModel10.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel10.setData_1("-");
                    }
                    incomeStatementModel10.setIs_bold(false);
                    incomeStatementModel10.setIs_head(false);
                    incomeStatementModel10.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel10);


                    IncomeStatementModel incomeStatementModel11 = new IncomeStatementModel();
                    incomeStatementModel11.setTitle("Intangible Assets");
                    incomeStatementModel11.setSymbol("intangibleAssets");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("intangibleAssets")) {
                        JSONObject researchDevelopment11 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("intangibleAssets");
                        if (researchDevelopment11.has("longFmt")) {
                            incomeStatementModel11.setData_1(researchDevelopment11.getString("longFmt"));
                        } else {
                            incomeStatementModel11.setData_1("-");
                        }
                    }
                    else
                    {
                        incomeStatementModel11.setData_1("-");
                    }
                    incomeStatementModel11.setIs_bold(false);
                    incomeStatementModel11.setIs_head(false);
                    incomeStatementModel11.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel11);

                    IncomeStatementModel incomeStatementModel12 = new IncomeStatementModel();
                    incomeStatementModel12.setTitle("Other Assets");
                    incomeStatementModel12.setSymbol("otherAssets");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("otherAssets")) {
                        JSONObject researchDevelopment12 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherAssets");
                        if (researchDevelopment12.has("longFmt")) {
                            incomeStatementModel12.setData_1(researchDevelopment12.getString("longFmt"));
                        } else {
                            incomeStatementModel12.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel12.setData_1("-");
                    }
                    incomeStatementModel12.setIs_bold(false);
                    incomeStatementModel12.setIs_head(false);
                    incomeStatementModel12.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel12);


                    IncomeStatementModel incomeStatementModel13 = new IncomeStatementModel();
                    incomeStatementModel13.setTitle("Deferred Long Term Asset Charges");
                    incomeStatementModel13.setSymbol("deferredLongTermAssetCharges");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("deferredLongTermAssetCharges")) {
                        JSONObject researchDevelopment13 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("deferredLongTermAssetCharges");
                        if (researchDevelopment13.has("longFmt")) {
                            incomeStatementModel13.setData_1(researchDevelopment13.getString("longFmt"));
                        } else {
                            incomeStatementModel13.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel13.setData_1("-");
                    }
                    incomeStatementModel13.setIs_bold(false);
                    incomeStatementModel13.setIs_head(false);
                    incomeStatementModel13.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel13);


                    IncomeStatementModel incomeStatementModel14 = new IncomeStatementModel();
                    incomeStatementModel14.setTitle("Total Assets");
                    incomeStatementModel14.setSymbol("totalAssets");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("totalAssets")) {
                        JSONObject researchDevelopment14 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalAssets");
                        if (researchDevelopment14.has("longFmt")) {
                            incomeStatementModel14.setData_1(researchDevelopment14.getString("longFmt"));
                        }
                        else {
                            incomeStatementModel14.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel14.setData_1("-");
                    }
                    incomeStatementModel14.setIs_bold(true);
                    incomeStatementModel14.setIs_head(false);
                    incomeStatementModel14.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel14);


                    IncomeStatementModel incomeStatementModel15 = new IncomeStatementModel();
                    incomeStatementModel15.setTitle("Current Liabilities");
                    incomeStatementModel15.setSymbol("current_liabilities");
                     incomeStatementModel15.setData_1("");
                    incomeStatementModel15.setIs_bold(true);
                    incomeStatementModel15.setIs_head(true);
                    incomeStatementModel15.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel15);



                    IncomeStatementModel incomeStatementModel16 = new IncomeStatementModel();
                    incomeStatementModel16.setTitle("Accounts Payable");
                    incomeStatementModel16.setSymbol("accountsPayable");

                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("accountsPayable")) {
                        JSONObject researchDevelopment16 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("accountsPayable");
                        if (researchDevelopment16.has("longFmt")) {
                            incomeStatementModel16.setData_1(researchDevelopment16.getString("longFmt"));
                        } else {
                            incomeStatementModel16.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel16.setData_1("-");
                    }
                    incomeStatementModel16.setIs_bold(true);
                    incomeStatementModel16.setIs_head(false);
                    incomeStatementModel16.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel16);


                    IncomeStatementModel incomeStatementModel17 = new IncomeStatementModel();
                    incomeStatementModel17.setTitle("Short/Current Long Term Debt");
                    incomeStatementModel17.setSymbol("shortLongTermDebt");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("shortLongTermDebt")) {
                        JSONObject researchDevelopment17 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("shortLongTermDebt");
                        if (researchDevelopment17.has("longFmt")) {
                            incomeStatementModel17.setData_1(researchDevelopment17.getString("longFmt"));
                        } else {
                            incomeStatementModel17.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel17.setData_1("-");
                    }
                    incomeStatementModel17.setIs_bold(true);
                    incomeStatementModel17.setIs_head(false);
                    incomeStatementModel17.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel17);

                    IncomeStatementModel incomeStatementModel18 = new IncomeStatementModel();
                    incomeStatementModel18.setTitle("Other Current Liabilities");
                    incomeStatementModel18.setSymbol("otherCurrentLiab");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("otherCurrentLiab")) {
                        JSONObject researchDevelopment18 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherCurrentLiab");
                        if (researchDevelopment18.has("longFmt")) {
                            incomeStatementModel18.setData_1(researchDevelopment18.getString("longFmt"));
                        } else {
                            incomeStatementModel18.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel18.setData_1("-");
                    }
                    incomeStatementModel18.setIs_bold(false);
                    incomeStatementModel18.setIs_head(false);
                    incomeStatementModel18.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel18);

                    IncomeStatementModel incomeStatementModel19 = new IncomeStatementModel();
                    incomeStatementModel19.setTitle("Total Current Liabilities");
                    incomeStatementModel19.setSymbol("totalCurrentLiabilities");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("totalCurrentLiabilities")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalCurrentLiabilities");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel19.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel19.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel19.setData_1("-");
                    }
                    incomeStatementModel19.setIs_bold(true);
                    incomeStatementModel19.setIs_head(false);
                    incomeStatementModel19.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel19);


                    IncomeStatementModel incomeStatementModel20 = new IncomeStatementModel();
                    incomeStatementModel20.setTitle("Long Term Debt");
                    incomeStatementModel20.setSymbol("longTermDebt");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("longTermDebt")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("longTermDebt");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel20.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel20.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel20.setData_1("-");
                    }
                    incomeStatementModel20.setIs_bold(false);
                    incomeStatementModel20.setIs_head(false);
                    incomeStatementModel20.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel20);



                    IncomeStatementModel incomeStatementModel21 = new IncomeStatementModel();
                    incomeStatementModel21.setTitle("Other Liabilities");
                    incomeStatementModel21.setSymbol("otherLiab");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("otherLiab")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherLiab");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel21.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel21.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel21.setData_1("-");
                    }
                    incomeStatementModel21.setIs_bold(false);
                    incomeStatementModel21.setIs_head(false);
                    incomeStatementModel21.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel21);


                    IncomeStatementModel incomeStatementModel22 = new IncomeStatementModel();
                    incomeStatementModel22.setTitle("Minority Interest");
                    incomeStatementModel22.setSymbol("minorityInterest");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("minorityInterest")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("minorityInterest");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel22.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel22.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel22.setData_1("-");
                    }
                    incomeStatementModel22.setIs_bold(false);
                    incomeStatementModel22.setIs_head(false);
                    incomeStatementModel22.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel22);


                    IncomeStatementModel incomeStatementModel23 = new IncomeStatementModel();
                    incomeStatementModel23.setTitle("Total Liabilities");
                    incomeStatementModel23.setSymbol("totalLiab");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("totalLiab")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalLiab");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel23.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel23.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel23.setData_1("-");
                    }
                    incomeStatementModel23.setIs_bold(false);
                    incomeStatementModel23.setIs_head(false);
                    incomeStatementModel23.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel23);



                    IncomeStatementModel incomeStatementModel24 = new IncomeStatementModel();
                    incomeStatementModel24.setTitle("Stockholders' Equity");
                    incomeStatementModel24.setSymbol("stockholders_equity");
                    incomeStatementModel24.setData_1("-");
                    incomeStatementModel24.setIs_bold(false);
                    incomeStatementModel24.setIs_head(true);
                    incomeStatementModel24.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel24);

                    IncomeStatementModel incomeStatementModel25 = new IncomeStatementModel();
                    incomeStatementModel25.setTitle("Common Stock");
                    incomeStatementModel25.setSymbol("commonStock");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("commonStock")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("commonStock");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel25.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel25.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel25.setData_1("-");
                    }
                    incomeStatementModel25.setIs_bold(false);
                    incomeStatementModel25.setIs_head(false);
                    incomeStatementModel25.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel25);


                    IncomeStatementModel incomeStatementModel26 = new IncomeStatementModel();
                    incomeStatementModel26.setTitle("Treasury Stock");
                    incomeStatementModel26.setSymbol("treasuryStock");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("treasuryStock")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("treasuryStock");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel26.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel26.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel26.setData_1("-");
                    }
                    incomeStatementModel26.setIs_bold(false);
                    incomeStatementModel26.setIs_head(false);
                    incomeStatementModel26.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel26);


                    IncomeStatementModel incomeStatementModel27 = new IncomeStatementModel();
                    incomeStatementModel27.setTitle("Other Stockholder Equity");
                    incomeStatementModel27.setSymbol("otherStockholderEquity");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("otherStockholderEquity")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherStockholderEquity");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel27.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel27.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel27.setData_1("-");
                    }
                    incomeStatementModel27.setIs_bold(false);
                    incomeStatementModel27.setIs_head(false);
                    incomeStatementModel27.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel27);

                    IncomeStatementModel incomeStatementModel28 = new IncomeStatementModel();
                    incomeStatementModel28.setTitle("Total Stockholder Equity");
                    incomeStatementModel28.setSymbol("totalStockholderEquity");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("totalStockholderEquity")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalStockholderEquity");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel28.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel28.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel28.setData_1("-");
                    }
                    incomeStatementModel28.setIs_bold(false);
                    incomeStatementModel28.setIs_head(false);
                    incomeStatementModel28.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel28);

                    IncomeStatementModel incomeStatementModel29 = new IncomeStatementModel();
                    incomeStatementModel29.setTitle("Net Tangible Assets");
                    incomeStatementModel29.setSymbol("netTangibleAssets");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("netTangibleAssets")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netTangibleAssets");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel29.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel29.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel29.setData_1("-");
                    }
                    incomeStatementModel29.setIs_bold(true);
                    incomeStatementModel29.setIs_head(false);
                    incomeStatementModel29.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel29);


                } else if (i == 1) {

                    for (int l=0;l<incomeStatementModels.size();l++){
                        IncomeStatementModel incomeStatementModel22 =incomeStatementModels.get(l);
                        if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has(incomeStatementModels.get(l).getSymbol())) {
                            JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject(incomeStatementModels.get(l).getSymbol());
                            if (researchDevelopment19.has("longFmt")) {
                                incomeStatementModel22.setData_2(researchDevelopment19.getString("longFmt"));
                            }
                            else if(researchDevelopment19.has("fmt"))
                            {
                                incomeStatementModel22.setData_2(researchDevelopment19.getString("fmt"));
                            }
                            else {
                                incomeStatementModel22.setData_2("-");
                            }
                        }
                        else{
                            incomeStatementModel22.setData_2("-");
                        }
                        incomeStatementModels.set(l,incomeStatementModel22);
                    }


                } else if (i == 2) {
                    for (int l=0;l<incomeStatementModels.size();l++){
                        IncomeStatementModel incomeStatementModel22 =incomeStatementModels.get(l);
                        if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has(incomeStatementModels.get(l).getSymbol())) {
                            JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject(incomeStatementModels.get(l).getSymbol());
                            if (researchDevelopment19.has("longFmt")) {
                                incomeStatementModel22.setData_3(researchDevelopment19.getString("longFmt"));
                            }
                            else if(researchDevelopment19.has("fmt"))
                            {
                                incomeStatementModel22.setData_3(researchDevelopment19.getString("fmt"));
                            }
                            else {
                                incomeStatementModel22.setData_3("-");
                            }
                        }
                        else{
                            incomeStatementModel22.setData_3("-");
                        }
                        incomeStatementModels.set(l,incomeStatementModel22);
                    }




                } else if (i == 3) {

                    for (int l=0;l<incomeStatementModels.size();l++){
                        IncomeStatementModel incomeStatementModel22 =incomeStatementModels.get(l);
                        if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has(incomeStatementModels.get(l).getSymbol())) {
                            JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject(incomeStatementModels.get(l).getSymbol());
                            if (researchDevelopment19.has("longFmt")) {
                                incomeStatementModel22.setData_4(researchDevelopment19.getString("longFmt"));
                            }
                            else if(researchDevelopment19.has("fmt"))
                            {
                                incomeStatementModel22.setData_4(researchDevelopment19.getString("fmt"));
                            }
                            else {
                                incomeStatementModel22.setData_4("-");
                            }
                        }
                        else{
                            incomeStatementModel22.setData_4("-");
                        }
                        incomeStatementModels.set(l,incomeStatementModel22);
                    }

                }


            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return incomeStatementModels;
    }


}

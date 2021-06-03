package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;
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

public class IncomeStatementTask  {


    IncomeStateListener gainerLooserListener;

    public IncomeStatementTask(IncomeStateListener gainerLooserListener){
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


                List<IncomeStatementModel> incomeStatementModels_q=getIncomestatementYearly(incomeStatementHistoryQuarterly);

                List<IncomeStatementModel> incomeStatementModels_y=getIncomestatementYearly(incomeStatementHistory);


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
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (RuntimeException e){
                e.printStackTrace();
            }
            if (response != null && response.isSuccessful()) {
                try {
                    if (response.body() != null) {
                        String data=response.body().string();
                        return data;
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
            JSONArray incomeStatementHistoryQuarterly_data = incomeStatementHistoryQuarterly.getJSONArray("incomeStatementHistory");


            for (int i = 0; i < incomeStatementHistoryQuarterly_data.length(); i++) {


                if (i == 0) {


                    IncomeStatementModel incomeStatementModel3 = new IncomeStatementModel();
                    incomeStatementModel3.setTitle("Revenue");
                    incomeStatementModel3.setData_1(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("endDate").getString("fmt"));
                    incomeStatementModel3.setIs_bold(true);
                    incomeStatementModel3.setIs_center(false);
                    incomeStatementModel3.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel3);

                    IncomeStatementModel incomeStatementModel = new IncomeStatementModel();
                    incomeStatementModel.setTitle("Total Revenue");
                    incomeStatementModel.setData_1(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalRevenue").getString("longFmt"));
                    incomeStatementModel.setIs_bold(false);
                    incomeStatementModel.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel);


                    IncomeStatementModel incomeStatementModel1 = new IncomeStatementModel();
                    incomeStatementModel1.setTitle("Cost of Revenue");
                    incomeStatementModel1.setData_1(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("costOfRevenue").getString("longFmt"));
                    incomeStatementModel1.setIs_bold(false);
                    incomeStatementModel1.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel1);


                    IncomeStatementModel incomeStatementModel2 = new IncomeStatementModel();
                    incomeStatementModel2.setTitle("Gross Profit");
                    incomeStatementModel2.setData_1(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("grossProfit").getString("longFmt"));
                    incomeStatementModel2.setIs_bold(true);
                    incomeStatementModel2.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel2);


                    IncomeStatementModel incomeStatementModel4 = new IncomeStatementModel();
                    incomeStatementModel4.setTitle("Operating Expenses");
                    incomeStatementModel4.setData_1("");
                    incomeStatementModel4.setIs_bold(false);
                    incomeStatementModel4.setIs_head(true);
                    incomeStatementModels.add(incomeStatementModel4);


                    IncomeStatementModel incomeStatementModel5 = new IncomeStatementModel();
                    incomeStatementModel5.setTitle("Research Development");
                    JSONObject researchDevelopment = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("researchDevelopment");
                    if (researchDevelopment.has("longFmt")) {
                        incomeStatementModel5.setData_1(researchDevelopment.getString("longFmt"));
                    } else {
                        incomeStatementModel5.setData_1("-");
                    }
                    incomeStatementModel5.setIs_bold(false);
                    incomeStatementModel5.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel5);


                    IncomeStatementModel incomeStatementModel6 = new IncomeStatementModel();
                    incomeStatementModel6.setTitle("Selling General and Administrative");
                    JSONObject researchDevelopment1 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("sellingGeneralAdministrative");
                    if (researchDevelopment1.has("longFmt")) {
                        incomeStatementModel6.setData_1(researchDevelopment1.getString("longFmt"));
                    } else {
                        incomeStatementModel6.setData_1("-");
                    }
                    incomeStatementModel6.setIs_bold(false);
                    incomeStatementModel6.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel6);


                    IncomeStatementModel incomeStatementModel7 = new IncomeStatementModel();
                    incomeStatementModel7.setTitle("Non Recurring");
                    JSONObject researchDevelopment2 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("nonRecurring");
                    if (researchDevelopment2.has("longFmt")) {
                        incomeStatementModel7.setData_1(researchDevelopment2.getString("longFmt"));
                    } else {
                        incomeStatementModel7.setData_1("-");
                    }
                    incomeStatementModel7.setIs_bold(false);
                    incomeStatementModel7.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel7);


                    IncomeStatementModel incomeStatementModel8 = new IncomeStatementModel();
                    incomeStatementModel8.setTitle("Others");
                    JSONObject researchDevelopment3 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherOperatingExpenses");
                    if (researchDevelopment3.has("longFmt")) {
                        incomeStatementModel8.setData_1(researchDevelopment3.getString("longFmt"));
                    } else {
                        incomeStatementModel8.setData_1("-");
                    }
                    incomeStatementModel8.setIs_bold(false);
                    incomeStatementModel8.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel8);


                    IncomeStatementModel incomeStatementModel9 = new IncomeStatementModel();
                    incomeStatementModel9.setTitle("Total Operating Expenses");
                    JSONObject researchDevelopment4 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalOperatingExpenses");
                    if (researchDevelopment4.has("longFmt")) {
                        incomeStatementModel9.setData_1(researchDevelopment4.getString("longFmt"));
                    } else {
                        incomeStatementModel9.setData_1("-");
                    }
                    incomeStatementModel9.setIs_bold(false);
                    incomeStatementModel9.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel9);


                    IncomeStatementModel incomeStatementModel10 = new IncomeStatementModel();
                    incomeStatementModel10.setTitle("Operating Income or Loss");
                    JSONObject researchDevelopment5 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("operatingIncome");
                    if (researchDevelopment5.has("longFmt")) {
                        incomeStatementModel10.setData_1(researchDevelopment5.getString("longFmt"));
                    } else {
                        incomeStatementModel10.setData_1("-");
                    }
                    incomeStatementModel10.setIs_bold(true);
                    incomeStatementModel10.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel10);


                    IncomeStatementModel incomeStatementModel11 = new IncomeStatementModel();
                    incomeStatementModel11.setTitle("Income from Continuing Operations");
                    incomeStatementModel11.setData_1("-");
                    incomeStatementModel11.setIs_bold(false);
                    incomeStatementModel11.setIs_head(true);
                    incomeStatementModels.add(incomeStatementModel11);


                    IncomeStatementModel incomeStatementModel12 = new IncomeStatementModel();
                    incomeStatementModel12.setTitle("Total Other Income/Expenses Net");
                    JSONObject researchDevelopment6 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalOtherIncomeExpenseNet");
                    if (researchDevelopment6.has("longFmt")) {
                        incomeStatementModel12.setData_1(researchDevelopment6.getString("longFmt"));
                    } else {
                        incomeStatementModel12.setData_1("-");
                    }
                    incomeStatementModel12.setIs_bold(false);
                    incomeStatementModel12.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel12);


                    IncomeStatementModel incomeStatementModel13 = new IncomeStatementModel();
                    incomeStatementModel13.setTitle("Earnings Before Interest and Taxes");
                    JSONObject researchDevelopment7 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("ebit");
                    if (researchDevelopment7.has("longFmt")) {
                        incomeStatementModel13.setData_1(researchDevelopment7.getString("longFmt"));
                    } else {
                        incomeStatementModel13.setData_1("-");
                    }
                    incomeStatementModel13.setIs_bold(false);
                    incomeStatementModel13.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel13);


                    IncomeStatementModel incomeStatementModel14 = new IncomeStatementModel();
                    incomeStatementModel14.setTitle("Interest Expense");
                    JSONObject researchDevelopment8 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("interestExpense");
                    if (researchDevelopment8.has("longFmt")) {
                        incomeStatementModel14.setData_1(researchDevelopment8.getString("longFmt"));
                    } else {
                        incomeStatementModel14.setData_1("-");
                    }
                    incomeStatementModel14.setIs_bold(false);
                    incomeStatementModel14.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel14);


                    IncomeStatementModel incomeStatementModel15 = new IncomeStatementModel();
                    incomeStatementModel15.setTitle("Income Before Tax");
                    JSONObject researchDevelopment9 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("incomeBeforeTax");
                    if (researchDevelopment9.has("longFmt")) {
                        incomeStatementModel15.setData_1(researchDevelopment9.getString("longFmt"));
                    } else {
                        incomeStatementModel15.setData_1("-");
                    }
                    incomeStatementModel15.setIs_bold(false);
                    incomeStatementModel15.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel15);


                    IncomeStatementModel incomeStatementModel16 = new IncomeStatementModel();
                    incomeStatementModel16.setTitle("Income Tax Expense");
                    JSONObject researchDevelopment10 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("incomeTaxExpense");
                    if (researchDevelopment10.has("longFmt")) {
                        incomeStatementModel16.setData_1(researchDevelopment10.getString("longFmt"));
                    } else {
                        incomeStatementModel16.setData_1("-");
                    }
                    incomeStatementModel16.setIs_bold(false);
                    incomeStatementModel16.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel16);


                    IncomeStatementModel incomeStatementModel17 = new IncomeStatementModel();
                    incomeStatementModel17.setTitle("Minority Interest");
                    JSONObject researchDevelopment11 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("minorityInterest");
                    if (researchDevelopment11.has("longFmt")) {
                        incomeStatementModel17.setData_1(researchDevelopment11.getString("longFmt"));
                    } else {
                        incomeStatementModel17.setData_1("-");
                    }
                    incomeStatementModel17.setIs_bold(false);
                    incomeStatementModel17.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel17);


                    IncomeStatementModel incomeStatementModel18 = new IncomeStatementModel();
                    incomeStatementModel18.setTitle("Net Income From Continuing Ops");
                    JSONObject researchDevelopment12 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncomeFromContinuingOps");
                    if (researchDevelopment12.has("longFmt")) {
                        incomeStatementModel18.setData_1(researchDevelopment12.getString("longFmt"));
                    } else {
                        incomeStatementModel18.setData_1("-");
                    }
                    incomeStatementModel18.setIs_bold(true);
                    incomeStatementModel18.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel18);


                    IncomeStatementModel incomeStatementModel19 = new IncomeStatementModel();
                    incomeStatementModel19.setTitle("Non-recurring Events");
                    incomeStatementModel19.setData_1("");
                    incomeStatementModel19.setIs_bold(true);
                    incomeStatementModel19.setIs_head(true);
                    incomeStatementModels.add(incomeStatementModel19);

                    IncomeStatementModel incomeStatementModel20 = new IncomeStatementModel();
                    incomeStatementModel20.setTitle("Discontinued Operations");
                    JSONObject researchDevelopment13 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("discontinuedOperations");
                    if (researchDevelopment13.has("longFmt")) {
                        incomeStatementModel20.setData_1(researchDevelopment13.getString("longFmt"));
                    } else {
                        incomeStatementModel20.setData_1("-");
                    }
                    incomeStatementModel20.setIs_bold(false);
                    incomeStatementModel20.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel20);


                    IncomeStatementModel incomeStatementModel21 = new IncomeStatementModel();
                    incomeStatementModel21.setTitle("Extraordinary Items");
                    JSONObject researchDevelopment14 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("extraordinaryItems");
                    if (researchDevelopment14.has("longFmt")) {
                        incomeStatementModel21.setData_1(researchDevelopment14.getString("longFmt"));
                    } else {
                        incomeStatementModel21.setData_1("-");
                    }
                    incomeStatementModel21.setIs_bold(false);
                    incomeStatementModel21.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel21);


                    IncomeStatementModel incomeStatementModel22 = new IncomeStatementModel();
                    incomeStatementModel22.setTitle("Effect Of Accounting Changes");
                    JSONObject researchDevelopment15 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("effectOfAccountingCharges");
                    if (researchDevelopment15.has("longFmt")) {
                        incomeStatementModel22.setData_1(researchDevelopment15.getString("longFmt"));
                    } else {
                        incomeStatementModel22.setData_1("-");
                    }
                    incomeStatementModel22.setIs_bold(false);
                    incomeStatementModel22.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel22);


                    IncomeStatementModel incomeStatementModel23 = new IncomeStatementModel();
                    incomeStatementModel23.setTitle("Other Items");
                    JSONObject researchDevelopment16 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherItems");
                    if (researchDevelopment16.has("longFmt")) {
                        incomeStatementModel23.setData_1(researchDevelopment16.getString("longFmt"));
                    } else {
                        incomeStatementModel23.setData_1("-");
                    }
                    incomeStatementModel23.setIs_bold(false);
                    incomeStatementModel23.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel23);


                    IncomeStatementModel incomeStatementModel24 = new IncomeStatementModel();
                    incomeStatementModel24.setTitle("Net Income");
                    incomeStatementModel24.setData_1("");
                    incomeStatementModel24.setIs_bold(true);
                    incomeStatementModel24.setIs_head(true);
                    incomeStatementModels.add(incomeStatementModel24);


                    IncomeStatementModel incomeStatementModel25 = new IncomeStatementModel();
                    incomeStatementModel25.setTitle("Net Income");
                    JSONObject researchDevelopment18 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncome");
                    if (researchDevelopment18.has("longFmt")) {
                        incomeStatementModel25.setData_1(researchDevelopment18.getString("longFmt"));
                    } else {
                        incomeStatementModel25.setData_1("-");
                    }
                    incomeStatementModel25.setIs_bold(true);
                    incomeStatementModel25.setIs_head(false);
                    incomeStatementModels.add(incomeStatementModel25);


                    IncomeStatementModel incomeStatementModel26 = new IncomeStatementModel();
                    incomeStatementModel26.setTitle("Net Income Applicable To Common Shares");
                    JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncomeApplicableToCommonShares");
                    if (researchDevelopment19.has("longFmt")) {
                        incomeStatementModel26.setData_1(researchDevelopment19.getString("longFmt"));
                    } else {
                        incomeStatementModel26.setData_1("-");
                    }
                    incomeStatementModel26.setIs_bold(true);
                    incomeStatementModel26.setIs_head(true);
                    incomeStatementModels.add(incomeStatementModel26);


                } else if (i == 1) {

                    IncomeStatementModel incomeStatementModel3 = incomeStatementModels.get(0);
                    incomeStatementModel3.setTitle("Revenue");
                    incomeStatementModel3.setData_2(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("endDate").getString("fmt"));
                    incomeStatementModel3.setIs_bold(true);
                    incomeStatementModel3.setIs_center(true);
                    incomeStatementModel3.setIs_head(true);
                    incomeStatementModels.set(0, incomeStatementModel3);


                    IncomeStatementModel incomeStatementModel = incomeStatementModels.get(1);
                    incomeStatementModel.setTitle("Total Revenue");
                    incomeStatementModel.setData_2(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalRevenue").getString("longFmt"));
                    incomeStatementModel.setIs_bold(false);
                    incomeStatementModel.setIs_head(false);
                    incomeStatementModels.set(1, incomeStatementModel);


                    IncomeStatementModel incomeStatementModel1 = incomeStatementModels.get(2);
                    incomeStatementModel1.setTitle("Cost of Revenue");
                    incomeStatementModel1.setData_2(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("costOfRevenue").getString("longFmt"));
                    incomeStatementModel1.setIs_bold(false);
                    incomeStatementModel1.setIs_head(false);
                    incomeStatementModels.set(2, incomeStatementModel1);


                    IncomeStatementModel incomeStatementModel2 = incomeStatementModels.get(3);
                    incomeStatementModel2.setTitle("Gross Profit");
                    incomeStatementModel2.setData_2(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("grossProfit").getString("longFmt"));
                    incomeStatementModel2.setIs_bold(true);
                    incomeStatementModel2.setIs_head(false);
                    incomeStatementModels.set(3, incomeStatementModel2);


                    IncomeStatementModel incomeStatementModel4 = incomeStatementModels.get(4);
                    incomeStatementModel4.setTitle("Operating Expenses");
                    incomeStatementModel4.setData_2("");
                    incomeStatementModel4.setIs_bold(true);
                    incomeStatementModel4.setIs_head(false);
                    incomeStatementModels.set(4, incomeStatementModel4);


                    IncomeStatementModel incomeStatementModel5 = incomeStatementModels.get(5);
                    incomeStatementModel5.setTitle("Research Development");
                    JSONObject researchDevelopment = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("researchDevelopment");
                    if (researchDevelopment.has("longFmt")) {
                        incomeStatementModel5.setData_2(researchDevelopment.getString("longFmt"));
                    } else {
                        incomeStatementModel5.setData_2("-");
                    }
                    incomeStatementModel5.setIs_bold(false);
                    incomeStatementModel5.setIs_head(false);
                    incomeStatementModels.set(5, incomeStatementModel5);


                    IncomeStatementModel incomeStatementModel6 = incomeStatementModels.get(6);
                    incomeStatementModel6.setTitle("Selling General and Administrative");
                    JSONObject researchDevelopment1 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("sellingGeneralAdministrative");
                    if (researchDevelopment1.has("longFmt")) {
                        incomeStatementModel6.setData_2(researchDevelopment1.getString("longFmt"));
                    } else {
                        incomeStatementModel6.setData_2("-");
                    }
                    incomeStatementModel6.setIs_bold(false);
                    incomeStatementModel6.setIs_head(false);
                    incomeStatementModels.set(6, incomeStatementModel6);

                    IncomeStatementModel incomeStatementModel7 = incomeStatementModels.get(7);
                    incomeStatementModel7.setTitle("Non Recurring");
                    JSONObject researchDevelopment2 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("nonRecurring");
                    if (researchDevelopment2.has("longFmt")) {
                        incomeStatementModel7.setData_2(researchDevelopment2.getString("longFmt"));
                    } else {
                        incomeStatementModel7.setData_2("-");
                    }
                    incomeStatementModel7.setIs_bold(false);
                    incomeStatementModel7.setIs_head(false);
                    incomeStatementModels.set(7, incomeStatementModel7);


                    IncomeStatementModel incomeStatementModel8 = incomeStatementModels.get(8);
                    incomeStatementModel8.setTitle("Others");
                    JSONObject researchDevelopment3 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherOperatingExpenses");
                    if (researchDevelopment3.has("longFmt")) {
                        incomeStatementModel8.setData_2(researchDevelopment3.getString("longFmt"));
                    } else {
                        incomeStatementModel8.setData_2("-");
                    }
                    incomeStatementModel8.setIs_bold(false);
                    incomeStatementModel8.setIs_head(false);
                    incomeStatementModels.set(8, incomeStatementModel8);


                    IncomeStatementModel incomeStatementModel9 = incomeStatementModels.get(9);
                    incomeStatementModel9.setTitle("Total Operating Expenses");
                    JSONObject researchDevelopment4 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalOperatingExpenses");
                    if (researchDevelopment4.has("longFmt")) {
                        incomeStatementModel9.setData_2(researchDevelopment4.getString("longFmt"));
                    } else {
                        incomeStatementModel9.setData_2("-");
                    }
                    incomeStatementModel9.setIs_bold(false);
                    incomeStatementModel9.setIs_head(false);
                    incomeStatementModels.set(9, incomeStatementModel9);


                    IncomeStatementModel incomeStatementModel10 = incomeStatementModels.get(10);
                    incomeStatementModel10.setTitle("Operating Income or Loss");
                    JSONObject researchDevelopment5 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("operatingIncome");
                    if (researchDevelopment5.has("longFmt")) {
                        incomeStatementModel10.setData_2(researchDevelopment5.getString("longFmt"));
                    } else {
                        incomeStatementModel10.setData_2("-");
                    }
                    incomeStatementModel10.setIs_bold(true);
                    incomeStatementModel10.setIs_head(false);
                    incomeStatementModels.set(10, incomeStatementModel10);


                    IncomeStatementModel incomeStatementModel11 = incomeStatementModels.get(11);
                    incomeStatementModel11.setTitle("Income from Continuing Operations");
                    incomeStatementModel11.setData_2("-");
                    incomeStatementModel11.setIs_bold(true);
                    incomeStatementModel11.setIs_head(true);
                    incomeStatementModels.set(11, incomeStatementModel11);


                    IncomeStatementModel incomeStatementModel12 = incomeStatementModels.get(12);
                    incomeStatementModel12.setTitle("Total Other Income/Expenses Net");
                    JSONObject researchDevelopment6 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalOtherIncomeExpenseNet");
                    if (researchDevelopment6.has("longFmt")) {
                        incomeStatementModel12.setData_2(researchDevelopment6.getString("longFmt"));
                    } else {
                        incomeStatementModel12.setData_2("-");
                    }
                    incomeStatementModel12.setIs_bold(false);
                    incomeStatementModel12.setIs_head(false);
                    incomeStatementModels.set(12, incomeStatementModel12);


                    IncomeStatementModel incomeStatementModel13 = incomeStatementModels.get(13);
                    incomeStatementModel13.setTitle("Earnings Before Interest and Taxes");
                    JSONObject researchDevelopment7 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("ebit");
                    if (researchDevelopment7.has("longFmt")) {
                        incomeStatementModel13.setData_2(researchDevelopment7.getString("longFmt"));
                    } else {
                        incomeStatementModel13.setData_2("-");
                    }
                    incomeStatementModel13.setIs_bold(false);
                    incomeStatementModel13.setIs_head(false);
                    incomeStatementModels.set(13, incomeStatementModel13);


                    IncomeStatementModel incomeStatementModel14 = incomeStatementModels.get(14);
                    incomeStatementModel14.setTitle("Interest Expense");
                    JSONObject researchDevelopment8 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("interestExpense");
                    if (researchDevelopment8.has("longFmt")) {
                        incomeStatementModel14.setData_2(researchDevelopment8.getString("longFmt"));
                    } else {
                        incomeStatementModel14.setData_2("-");
                    }
                    incomeStatementModel14.setIs_bold(false);
                    incomeStatementModel14.setIs_head(false);
                    incomeStatementModels.set(14, incomeStatementModel14);


                    IncomeStatementModel incomeStatementModel15 = incomeStatementModels.get(15);
                    incomeStatementModel15.setTitle("Income Before Tax");
                    JSONObject researchDevelopment9 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("incomeBeforeTax");
                    if (researchDevelopment9.has("longFmt")) {
                        incomeStatementModel15.setData_2(researchDevelopment9.getString("longFmt"));
                    } else {
                        incomeStatementModel15.setData_2("-");
                    }
                    incomeStatementModel15.setIs_bold(false);
                    incomeStatementModel15.setIs_head(false);
                    incomeStatementModels.set(15, incomeStatementModel15);


                    IncomeStatementModel incomeStatementModel16 = incomeStatementModels.get(16);
                    incomeStatementModel16.setTitle("Income Tax Expense");
                    JSONObject researchDevelopment10 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("incomeTaxExpense");
                    if (researchDevelopment10.has("longFmt")) {
                        incomeStatementModel16.setData_2(researchDevelopment10.getString("longFmt"));
                    } else {
                        incomeStatementModel16.setData_2("-");
                    }
                    incomeStatementModel16.setIs_bold(false);
                    incomeStatementModel16.setIs_head(false);
                    incomeStatementModels.set(16, incomeStatementModel16);


                    IncomeStatementModel incomeStatementModel17 = incomeStatementModels.get(17);
                    incomeStatementModel17.setTitle("Minority Interest");
                    JSONObject researchDevelopment11 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("minorityInterest");
                    if (researchDevelopment11.has("longFmt")) {
                        incomeStatementModel17.setData_2(researchDevelopment11.getString("longFmt"));
                    } else {
                        incomeStatementModel17.setData_2("-");
                    }
                    incomeStatementModel17.setIs_bold(false);
                    incomeStatementModel17.setIs_head(false);
                    incomeStatementModels.set(17, incomeStatementModel17);


                    IncomeStatementModel incomeStatementModel18 = incomeStatementModels.get(18);
                    incomeStatementModel18.setTitle("Net Income From Continuing Ops");
                    JSONObject researchDevelopment12 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncomeFromContinuingOps");
                    if (researchDevelopment12.has("longFmt")) {
                        incomeStatementModel18.setData_2(researchDevelopment12.getString("longFmt"));
                    } else {
                        incomeStatementModel18.setData_2("-");
                    }
                    incomeStatementModel18.setIs_bold(true);
                    incomeStatementModel18.setIs_head(false);
                    incomeStatementModels.set(18, incomeStatementModel18);


                    IncomeStatementModel incomeStatementModel19 = incomeStatementModels.get(19);
                    incomeStatementModel19.setTitle("Non-recurring Events");
                    incomeStatementModel19.setData_2("");
                    incomeStatementModel19.setIs_bold(true);
                    incomeStatementModel19.setIs_head(true);
                    incomeStatementModels.set(19, incomeStatementModel19);

                    IncomeStatementModel incomeStatementModel20 = incomeStatementModels.get(20);
                    incomeStatementModel20.setTitle("Discontinued Operations");
                    JSONObject researchDevelopment13 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("discontinuedOperations");
                    if (researchDevelopment13.has("longFmt")) {
                        incomeStatementModel20.setData_2(researchDevelopment13.getString("longFmt"));
                    } else {
                        incomeStatementModel20.setData_2("-");
                    }
                    incomeStatementModel20.setIs_bold(false);
                    incomeStatementModel20.setIs_head(false);
                    incomeStatementModels.set(20, incomeStatementModel20);


                    IncomeStatementModel incomeStatementModel21 = incomeStatementModels.get(21);
                    incomeStatementModel21.setTitle("Extraordinary Items");
                    JSONObject researchDevelopment14 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("extraordinaryItems");
                    if (researchDevelopment14.has("longFmt")) {
                        incomeStatementModel21.setData_2(researchDevelopment14.getString("longFmt"));
                    } else {
                        incomeStatementModel21.setData_2("-");
                    }
                    incomeStatementModel21.setIs_bold(false);
                    incomeStatementModel21.setIs_head(false);
                    incomeStatementModels.set(21, incomeStatementModel21);


                    IncomeStatementModel incomeStatementModel22 = incomeStatementModels.get(22);
                    ;
                    incomeStatementModel22.setTitle("Effect Of Accounting Changes");
                    JSONObject researchDevelopment15 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("effectOfAccountingCharges");
                    if (researchDevelopment15.has("longFmt")) {
                        incomeStatementModel22.setData_2(researchDevelopment15.getString("longFmt"));
                    } else {
                        incomeStatementModel22.setData_2("-");
                    }
                    incomeStatementModel22.setIs_bold(false);
                    incomeStatementModel22.setIs_head(false);
                    incomeStatementModels.set(22, incomeStatementModel22);


                    IncomeStatementModel incomeStatementModel23 = incomeStatementModels.get(23);
                    ;
                    incomeStatementModel23.setTitle("Other Items");
                    JSONObject researchDevelopment16 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherItems");
                    if (researchDevelopment16.has("longFmt")) {
                        incomeStatementModel23.setData_2(researchDevelopment16.getString("longFmt"));
                    } else {
                        incomeStatementModel23.setData_2("-");
                    }
                    incomeStatementModel23.setIs_bold(false);
                    incomeStatementModel23.setIs_head(false);
                    incomeStatementModels.set(23, incomeStatementModel23);


                    IncomeStatementModel incomeStatementModel24 = incomeStatementModels.get(24);
                    ;
                    incomeStatementModel24.setTitle("Net Income");
                    incomeStatementModel24.setData_2("");
                    incomeStatementModel24.setIs_bold(true);
                    incomeStatementModel24.setIs_head(true);
                    incomeStatementModels.set(24, incomeStatementModel24);


                    IncomeStatementModel incomeStatementModel25 = incomeStatementModels.get(25);
                    ;
                    incomeStatementModel25.setTitle("Net Income");
                    JSONObject researchDevelopment18 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncome");
                    if (researchDevelopment18.has("longFmt")) {
                        incomeStatementModel25.setData_2(researchDevelopment18.getString("longFmt"));
                    } else {
                        incomeStatementModel25.setData_2("-");
                    }
                    incomeStatementModel25.setIs_bold(true);
                    incomeStatementModel25.setIs_head(false);
                    incomeStatementModels.set(25, incomeStatementModel25);


                    IncomeStatementModel incomeStatementModel26 = incomeStatementModels.get(26);
                    incomeStatementModel26.setTitle("Net Income Applicable To Common Shares");
                    JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncomeApplicableToCommonShares");
                    if (researchDevelopment19.has("longFmt")) {
                        incomeStatementModel26.setData_2(researchDevelopment19.getString("longFmt"));
                    } else {
                        incomeStatementModel26.setData_2("-");
                    }
                    incomeStatementModel26.setIs_bold(true);
                    incomeStatementModel26.setIs_head(true);
                    incomeStatementModels.set(26, incomeStatementModel26);


                } else if (i == 2) {

                    IncomeStatementModel incomeStatementModel3 = incomeStatementModels.get(0);
                    incomeStatementModel3.setTitle("Revenue");
                    incomeStatementModel3.setData_3(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("endDate").getString("fmt"));
                    incomeStatementModel3.setIs_bold(true);
                    incomeStatementModel3.setIs_head(false);
                    incomeStatementModel3.setIs_center(true);
                    incomeStatementModels.set(0, incomeStatementModel3);


                    IncomeStatementModel incomeStatementModel = incomeStatementModels.get(1);
                    incomeStatementModel.setTitle("Total Revenue");
                    incomeStatementModel.setData_3(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalRevenue").getString("longFmt"));
                    incomeStatementModel.setIs_bold(false);
                    incomeStatementModel.setIs_head(false);
                    incomeStatementModels.set(1, incomeStatementModel);


                    IncomeStatementModel incomeStatementModel1 = incomeStatementModels.get(2);
                    incomeStatementModel1.setTitle("Cost of Revenue");
                    incomeStatementModel1.setData_3(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("costOfRevenue").getString("longFmt"));
                    incomeStatementModel1.setIs_bold(false);
                    incomeStatementModel1.setIs_head(false);
                    incomeStatementModels.set(2, incomeStatementModel1);


                    IncomeStatementModel incomeStatementModel2 = incomeStatementModels.get(3);
                    incomeStatementModel2.setTitle("Gross Profit");
                    incomeStatementModel2.setData_3(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("grossProfit").getString("longFmt"));
                    incomeStatementModel2.setIs_bold(true);
                    incomeStatementModel2.setIs_head(false);
                    incomeStatementModels.set(3, incomeStatementModel2);


                    IncomeStatementModel incomeStatementModel4 = incomeStatementModels.get(4);
                    incomeStatementModel4.setTitle("Operating Expenses");
                    incomeStatementModel4.setData_3("");
                    incomeStatementModel4.setIs_bold(true);
                    incomeStatementModel4.setIs_head(false);
                    incomeStatementModels.set(4, incomeStatementModel4);

                    IncomeStatementModel incomeStatementModel5 = incomeStatementModels.get(5);
                    incomeStatementModel5.setTitle("Research Development");
                    JSONObject researchDevelopment = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("researchDevelopment");
                    if (researchDevelopment.has("longFmt")) {
                        incomeStatementModel5.setData_3(researchDevelopment.getString("longFmt"));
                    } else {
                        incomeStatementModel5.setData_3("-");
                    }
                    incomeStatementModel5.setIs_bold(false);
                    incomeStatementModel5.setIs_head(false);
                    incomeStatementModels.set(5, incomeStatementModel5);


                    IncomeStatementModel incomeStatementModel6 = incomeStatementModels.get(6);
                    incomeStatementModel6.setTitle("Selling General and Administrative");
                    JSONObject researchDevelopment1 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("sellingGeneralAdministrative");
                    if (researchDevelopment1.has("longFmt")) {
                        incomeStatementModel6.setData_3(researchDevelopment1.getString("longFmt"));
                    } else {
                        incomeStatementModel6.setData_3("-");
                    }
                    incomeStatementModel6.setIs_bold(false);
                    incomeStatementModel6.setIs_head(false);
                    incomeStatementModels.set(6, incomeStatementModel6);


                    IncomeStatementModel incomeStatementModel7 = incomeStatementModels.get(7);
                    incomeStatementModel7.setTitle("Non Recurring");
                    JSONObject researchDevelopment2 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("nonRecurring");
                    if (researchDevelopment2.has("longFmt")) {
                        incomeStatementModel7.setData_3(researchDevelopment2.getString("longFmt"));
                    } else {
                        incomeStatementModel7.setData_3("-");
                    }
                    incomeStatementModel7.setIs_bold(false);
                    incomeStatementModel7.setIs_head(false);
                    incomeStatementModels.set(7, incomeStatementModel7);

                    IncomeStatementModel incomeStatementModel8 = incomeStatementModels.get(8);
                    incomeStatementModel8.setTitle("Others");
                    JSONObject researchDevelopment3 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherOperatingExpenses");
                    if (researchDevelopment3.has("longFmt")) {
                        incomeStatementModel8.setData_3(researchDevelopment3.getString("longFmt"));
                    } else {
                        incomeStatementModel8.setData_3("-");
                    }
                    incomeStatementModel8.setIs_bold(false);
                    incomeStatementModel8.setIs_head(false);
                    incomeStatementModels.set(8, incomeStatementModel8);


                    IncomeStatementModel incomeStatementModel9 = incomeStatementModels.get(9);
                    incomeStatementModel9.setTitle("Total Operating Expenses");
                    JSONObject researchDevelopment4 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalOperatingExpenses");
                    if (researchDevelopment4.has("longFmt")) {
                        incomeStatementModel9.setData_3(researchDevelopment4.getString("longFmt"));
                    } else {
                        incomeStatementModel9.setData_3("-");
                    }
                    incomeStatementModel9.setIs_bold(false);
                    incomeStatementModel9.setIs_head(false);
                    incomeStatementModels.set(9, incomeStatementModel9);


                    IncomeStatementModel incomeStatementModel10 = incomeStatementModels.get(10);
                    incomeStatementModel10.setTitle("Operating Income or Loss");
                    JSONObject researchDevelopment5 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("operatingIncome");
                    if (researchDevelopment5.has("longFmt")) {
                        incomeStatementModel10.setData_3(researchDevelopment5.getString("longFmt"));
                    } else {
                        incomeStatementModel10.setData_3("-");
                    }
                    incomeStatementModel10.setIs_bold(true);
                    incomeStatementModel10.setIs_head(false);
                    incomeStatementModels.set(10, incomeStatementModel10);


                    IncomeStatementModel incomeStatementModel11 = incomeStatementModels.get(11);
                    incomeStatementModel11.setTitle("Income from Continuing Operations");
                    incomeStatementModel11.setData_3("-");
                    incomeStatementModel11.setIs_bold(true);
                    incomeStatementModel11.setIs_head(true);
                    incomeStatementModels.set(11, incomeStatementModel11);


                    IncomeStatementModel incomeStatementModel12 = incomeStatementModels.get(12);
                    incomeStatementModel12.setTitle("Total Other Income/Expenses Net");
                    JSONObject researchDevelopment6 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalOtherIncomeExpenseNet");
                    if (researchDevelopment6.has("longFmt")) {
                        incomeStatementModel12.setData_3(researchDevelopment6.getString("longFmt"));
                    } else {
                        incomeStatementModel12.setData_3("-");
                    }
                    incomeStatementModel12.setIs_bold(false);
                    incomeStatementModel12.setIs_head(false);
                    incomeStatementModels.set(12, incomeStatementModel12);


                    IncomeStatementModel incomeStatementModel13 = incomeStatementModels.get(13);
                    incomeStatementModel13.setTitle("Earnings Before Interest and Taxes");
                    JSONObject researchDevelopment7 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("ebit");
                    if (researchDevelopment7.has("longFmt")) {
                        incomeStatementModel13.setData_3(researchDevelopment7.getString("longFmt"));
                    } else {
                        incomeStatementModel13.setData_3("-");
                    }
                    incomeStatementModel13.setIs_bold(false);
                    incomeStatementModel13.setIs_head(false);
                    incomeStatementModels.set(13, incomeStatementModel13);


                    IncomeStatementModel incomeStatementModel14 = incomeStatementModels.get(14);
                    incomeStatementModel14.setTitle("Interest Expense");
                    JSONObject researchDevelopment8 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("interestExpense");
                    if (researchDevelopment8.has("longFmt")) {
                        incomeStatementModel14.setData_3(researchDevelopment8.getString("longFmt"));
                    } else {
                        incomeStatementModel14.setData_3("-");
                    }
                    incomeStatementModel14.setIs_bold(false);
                    incomeStatementModel14.setIs_head(false);
                    incomeStatementModels.set(14, incomeStatementModel14);


                    IncomeStatementModel incomeStatementModel15 = incomeStatementModels.get(15);
                    incomeStatementModel15.setTitle("Income Before Tax");
                    JSONObject researchDevelopment9 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("incomeBeforeTax");
                    if (researchDevelopment9.has("longFmt")) {
                        incomeStatementModel15.setData_3(researchDevelopment9.getString("longFmt"));
                    } else {
                        incomeStatementModel15.setData_3("-");
                    }
                    incomeStatementModel15.setIs_bold(false);
                    incomeStatementModel15.setIs_head(false);
                    incomeStatementModels.set(15, incomeStatementModel15);


                    IncomeStatementModel incomeStatementModel16 = incomeStatementModels.get(16);
                    incomeStatementModel16.setTitle("Income Tax Expense");
                    JSONObject researchDevelopment10 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("incomeTaxExpense");
                    if (researchDevelopment10.has("longFmt")) {
                        incomeStatementModel16.setData_3(researchDevelopment10.getString("longFmt"));
                    } else {
                        incomeStatementModel16.setData_3("-");
                    }
                    incomeStatementModel16.setIs_bold(false);
                    incomeStatementModel16.setIs_head(false);
                    incomeStatementModels.set(16, incomeStatementModel16);


                    IncomeStatementModel incomeStatementModel17 = incomeStatementModels.get(17);
                    ;
                    incomeStatementModel17.setTitle("Minority Interest");
                    JSONObject researchDevelopment11 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("minorityInterest");
                    if (researchDevelopment11.has("longFmt")) {
                        incomeStatementModel17.setData_3(researchDevelopment11.getString("longFmt"));
                    } else {
                        incomeStatementModel17.setData_3("-");
                    }
                    incomeStatementModel17.setIs_bold(false);
                    incomeStatementModel17.setIs_head(false);
                    incomeStatementModels.set(17, incomeStatementModel17);


                    IncomeStatementModel incomeStatementModel18 = incomeStatementModels.get(18);
                    ;
                    incomeStatementModel18.setTitle("Net Income From Continuing Ops");
                    JSONObject researchDevelopment12 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncomeFromContinuingOps");
                    if (researchDevelopment12.has("longFmt")) {
                        incomeStatementModel18.setData_3(researchDevelopment12.getString("longFmt"));
                    } else {
                        incomeStatementModel18.setData_3("-");
                    }
                    incomeStatementModel18.setIs_bold(true);
                    incomeStatementModel18.setIs_head(false);
                    incomeStatementModels.set(18, incomeStatementModel18);


                    IncomeStatementModel incomeStatementModel19 = incomeStatementModels.get(19);
                    ;
                    incomeStatementModel19.setTitle("Non-recurring Events");
                    incomeStatementModel19.setData_3("");
                    incomeStatementModel19.setIs_bold(true);
                    incomeStatementModel19.setIs_head(true);
                    incomeStatementModels.set(19, incomeStatementModel19);

                    IncomeStatementModel incomeStatementModel20 = incomeStatementModels.get(20);
                    ;
                    incomeStatementModel20.setTitle("Discontinued Operations");
                    JSONObject researchDevelopment13 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("discontinuedOperations");
                    if (researchDevelopment13.has("longFmt")) {
                        incomeStatementModel20.setData_3(researchDevelopment13.getString("longFmt"));
                    } else {
                        incomeStatementModel20.setData_3("-");
                    }
                    incomeStatementModel20.setIs_bold(false);
                    incomeStatementModel20.setIs_head(false);
                    incomeStatementModels.set(20, incomeStatementModel20);


                    IncomeStatementModel incomeStatementModel21 = incomeStatementModels.get(21);
                    ;
                    incomeStatementModel21.setTitle("Extraordinary Items");
                    JSONObject researchDevelopment14 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("extraordinaryItems");
                    if (researchDevelopment14.has("longFmt")) {
                        incomeStatementModel21.setData_3(researchDevelopment14.getString("longFmt"));
                    } else {
                        incomeStatementModel21.setData_3("-");
                    }
                    incomeStatementModel21.setIs_bold(false);
                    incomeStatementModel21.setIs_head(false);
                    incomeStatementModels.set(21, incomeStatementModel21);


                    IncomeStatementModel incomeStatementModel22 = incomeStatementModels.get(22);
                    ;
                    incomeStatementModel22.setTitle("Effect Of Accounting Changes");
                    JSONObject researchDevelopment15 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("effectOfAccountingCharges");
                    if (researchDevelopment15.has("longFmt")) {
                        incomeStatementModel22.setData_3(researchDevelopment15.getString("longFmt"));
                    } else {
                        incomeStatementModel22.setData_3("-");
                    }
                    incomeStatementModel22.setIs_bold(false);
                    incomeStatementModel22.setIs_head(false);
                    incomeStatementModels.set(22, incomeStatementModel22);


                    IncomeStatementModel incomeStatementModel23 = incomeStatementModels.get(23);
                    incomeStatementModel23.setTitle("Other Items");
                    JSONObject researchDevelopment16 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherItems");
                    if (researchDevelopment16.has("longFmt")) {
                        incomeStatementModel23.setData_3(researchDevelopment16.getString("longFmt"));
                    } else {
                        incomeStatementModel23.setData_3("-");
                    }
                    incomeStatementModel23.setIs_bold(false);
                    incomeStatementModel23.setIs_head(false);
                    incomeStatementModels.set(23, incomeStatementModel23);


                    IncomeStatementModel incomeStatementModel24 = incomeStatementModels.get(24);
                    ;
                    incomeStatementModel24.setTitle("Net Income");
                    incomeStatementModel24.setData_3("");
                    incomeStatementModel24.setIs_bold(true);
                    incomeStatementModel24.setIs_head(true);
                    incomeStatementModels.set(24, incomeStatementModel24);


                    IncomeStatementModel incomeStatementModel25 = incomeStatementModels.get(25);
                    ;
                    incomeStatementModel25.setTitle("Net Income");
                    JSONObject researchDevelopment18 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncome");
                    if (researchDevelopment18.has("longFmt")) {
                        incomeStatementModel25.setData_3(researchDevelopment18.getString("longFmt"));
                    } else {
                        incomeStatementModel25.setData_3("-");
                    }
                    incomeStatementModel25.setIs_bold(true);
                    incomeStatementModel25.setIs_head(false);
                    incomeStatementModels.set(25, incomeStatementModel25);


                    IncomeStatementModel incomeStatementModel26 = incomeStatementModels.get(26);
                    ;
                    incomeStatementModel26.setTitle("Net Income Applicable To Common Shares");
                    JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncomeApplicableToCommonShares");
                    if (researchDevelopment19.has("longFmt")) {
                        incomeStatementModel26.setData_3(researchDevelopment19.getString("longFmt"));
                    } else {
                        incomeStatementModel26.setData_3("-");
                    }
                    incomeStatementModel26.setIs_bold(true);
                    incomeStatementModel26.setIs_head(true);
                    incomeStatementModels.set(26, incomeStatementModel26);


                } else if (i == 3) {

                    IncomeStatementModel incomeStatementModel3 = incomeStatementModels.get(0);
                    incomeStatementModel3.setTitle("Revenue");
                    incomeStatementModel3.setData_4(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("endDate").getString("fmt"));
                    incomeStatementModel3.setIs_bold(true);
                    incomeStatementModel3.setIs_center(true);
                    incomeStatementModel3.setIs_head(true);
                    incomeStatementModels.set(0, incomeStatementModel3);


                    IncomeStatementModel incomeStatementModel = incomeStatementModels.get(1);
                    incomeStatementModel.setTitle("Total Revenue");
                    incomeStatementModel.setData_4(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalRevenue").getString("longFmt"));
                    incomeStatementModel.setIs_bold(false);
                    incomeStatementModel.setIs_head(false);
                    incomeStatementModels.set(1, incomeStatementModel);


                    IncomeStatementModel incomeStatementModel1 = incomeStatementModels.get(2);
                    incomeStatementModel1.setTitle("Cost of Revenue");
                    incomeStatementModel1.setData_4(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("costOfRevenue").getString("longFmt"));
                    incomeStatementModel1.setIs_bold(false);
                    incomeStatementModel1.setIs_head(false);
                    incomeStatementModels.set(2, incomeStatementModel1);


                    IncomeStatementModel incomeStatementModel2 = incomeStatementModels.get(3);
                    incomeStatementModel2.setTitle("Gross Profit");
                    incomeStatementModel2.setData_4(incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("grossProfit").getString("longFmt"));
                    incomeStatementModel2.setIs_bold(true);
                    incomeStatementModel2.setIs_head(false);
                    incomeStatementModels.set(3, incomeStatementModel2);

                    IncomeStatementModel incomeStatementModel4 = incomeStatementModels.get(4);
                    incomeStatementModel4.setTitle("Operating Expenses");
                    incomeStatementModel4.setData_4("");
                    incomeStatementModel4.setIs_bold(true);
                    incomeStatementModel4.setIs_head(false);
                    incomeStatementModels.set(4, incomeStatementModel4);

                    IncomeStatementModel incomeStatementModel5 = incomeStatementModels.get(5);
                    incomeStatementModel5.setTitle("Research Development");
                    JSONObject researchDevelopment = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("researchDevelopment");
                    if (researchDevelopment.has("longFmt")) {
                        incomeStatementModel5.setData_4(researchDevelopment.getString("longFmt"));
                    } else {
                        incomeStatementModel5.setData_4("-");
                    }
                    incomeStatementModel5.setIs_bold(false);
                    incomeStatementModel5.setIs_head(false);
                    incomeStatementModels.set(5, incomeStatementModel5);

                    IncomeStatementModel incomeStatementModel6 = incomeStatementModels.get(6);
                    incomeStatementModel6.setTitle("Selling General and Administrative");
                    JSONObject researchDevelopment1 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("sellingGeneralAdministrative");
                    if (researchDevelopment1.has("longFmt")) {
                        incomeStatementModel6.setData_4(researchDevelopment1.getString("longFmt"));
                    } else {
                        incomeStatementModel6.setData_4("-");
                    }
                    incomeStatementModel6.setIs_bold(false);
                    incomeStatementModel6.setIs_head(false);
                    incomeStatementModels.set(6, incomeStatementModel6);


                    IncomeStatementModel incomeStatementModel7 = incomeStatementModels.get(7);
                    incomeStatementModel7.setTitle("Non Recurring");
                    JSONObject researchDevelopment2 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("nonRecurring");
                    if (researchDevelopment2.has("longFmt")) {
                        incomeStatementModel7.setData_4(researchDevelopment2.getString("longFmt"));
                    } else {
                        incomeStatementModel7.setData_4("-");
                    }
                    incomeStatementModel7.setIs_bold(false);
                    incomeStatementModel7.setIs_head(false);
                    incomeStatementModels.set(7, incomeStatementModel7);


                    IncomeStatementModel incomeStatementModel8 = incomeStatementModels.get(8);
                    incomeStatementModel8.setTitle("Others");
                    JSONObject researchDevelopment3 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherOperatingExpenses");
                    if (researchDevelopment3.has("longFmt")) {
                        incomeStatementModel8.setData_4(researchDevelopment3.getString("longFmt"));
                    } else {
                        incomeStatementModel8.setData_4("-");
                    }
                    incomeStatementModel8.setIs_bold(false);
                    incomeStatementModel8.setIs_head(false);
                    incomeStatementModels.set(8, incomeStatementModel8);


                    IncomeStatementModel incomeStatementModel9 = incomeStatementModels.get(9);
                    incomeStatementModel9.setTitle("Total Operating Expenses");
                    JSONObject researchDevelopment4 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalOperatingExpenses");
                    if (researchDevelopment4.has("longFmt")) {
                        incomeStatementModel9.setData_4(researchDevelopment4.getString("longFmt"));
                    } else {
                        incomeStatementModel9.setData_4("-");
                    }
                    incomeStatementModel9.setIs_bold(false);
                    incomeStatementModel9.setIs_head(false);
                    incomeStatementModels.set(9, incomeStatementModel9);


                    IncomeStatementModel incomeStatementModel10 = incomeStatementModels.get(10);
                    incomeStatementModel10.setTitle("Operating Income or Loss");
                    JSONObject researchDevelopment5 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("operatingIncome");
                    if (researchDevelopment5.has("longFmt")) {
                        incomeStatementModel10.setData_4(researchDevelopment5.getString("longFmt"));
                    } else {
                        incomeStatementModel10.setData_4("-");
                    }
                    incomeStatementModel10.setIs_bold(true);
                    incomeStatementModel10.setIs_head(false);
                    incomeStatementModels.set(10, incomeStatementModel10);


                    IncomeStatementModel incomeStatementModel11 = incomeStatementModels.get(11);
                    incomeStatementModel11.setTitle("Income from Continuing Operations");
                    incomeStatementModel11.setData_4("-");
                    incomeStatementModel11.setIs_bold(true);
                    incomeStatementModel11.setIs_head(true);
                    incomeStatementModels.set(11, incomeStatementModel11);


                    IncomeStatementModel incomeStatementModel12 = incomeStatementModels.get(12);
                    ;
                    incomeStatementModel12.setTitle("Total Other Income/Expenses Net");
                    JSONObject researchDevelopment6 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalOtherIncomeExpenseNet");
                    if (researchDevelopment6.has("longFmt")) {
                        incomeStatementModel12.setData_4(researchDevelopment6.getString("longFmt"));
                    } else {
                        incomeStatementModel12.setData_4("-");
                    }
                    incomeStatementModel12.setIs_bold(false);
                    incomeStatementModel12.setIs_head(false);
                    incomeStatementModels.set(12, incomeStatementModel12);


                    IncomeStatementModel incomeStatementModel13 = incomeStatementModels.get(13);
                    ;
                    incomeStatementModel13.setTitle("Earnings Before Interest and Taxes");
                    JSONObject researchDevelopment7 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("ebit");
                    if (researchDevelopment7.has("longFmt")) {
                        incomeStatementModel13.setData_4(researchDevelopment7.getString("longFmt"));
                    } else {
                        incomeStatementModel13.setData_4("-");
                    }
                    incomeStatementModel13.setIs_bold(false);
                    incomeStatementModel13.setIs_head(false);
                    incomeStatementModels.set(13, incomeStatementModel13);


                    IncomeStatementModel incomeStatementModel14 = incomeStatementModels.get(14);
                    ;
                    incomeStatementModel14.setTitle("Interest Expense");
                    JSONObject researchDevelopment8 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("interestExpense");
                    if (researchDevelopment8.has("longFmt")) {
                        incomeStatementModel14.setData_4(researchDevelopment8.getString("longFmt"));
                    } else {
                        incomeStatementModel14.setData_4("-");
                    }
                    incomeStatementModel14.setIs_bold(false);
                    incomeStatementModel14.setIs_head(false);
                    incomeStatementModels.set(14, incomeStatementModel14);


                    IncomeStatementModel incomeStatementModel15 = incomeStatementModels.get(15);
                    incomeStatementModel15.setTitle("Income Before Tax");
                    JSONObject researchDevelopment9 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("incomeBeforeTax");
                    if (researchDevelopment9.has("longFmt")) {
                        incomeStatementModel15.setData_4(researchDevelopment9.getString("longFmt"));
                    } else {
                        incomeStatementModel15.setData_4("-");
                    }
                    incomeStatementModel15.setIs_bold(false);
                    incomeStatementModel15.setIs_head(false);
                    incomeStatementModels.set(15, incomeStatementModel15);


                    IncomeStatementModel incomeStatementModel16 = incomeStatementModels.get(16);
                    incomeStatementModel16.setTitle("Income Tax Expense");
                    JSONObject researchDevelopment10 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("incomeTaxExpense");
                    if (researchDevelopment10.has("longFmt")) {
                        incomeStatementModel16.setData_2(researchDevelopment10.getString("longFmt"));
                    } else {
                        incomeStatementModel16.setData_2("-");
                    }
                    incomeStatementModel16.setIs_bold(false);
                    incomeStatementModel16.setIs_head(false);
                    incomeStatementModels.set(16, incomeStatementModel16);


                    IncomeStatementModel incomeStatementModel17 = incomeStatementModels.get(17);
                    incomeStatementModel17.setTitle("Minority Interest");
                    JSONObject researchDevelopment11 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("minorityInterest");
                    if (researchDevelopment11.has("longFmt")) {
                        incomeStatementModel17.setData_4(researchDevelopment11.getString("longFmt"));
                    } else {
                        incomeStatementModel17.setData_4("-");
                    }
                    incomeStatementModel17.setIs_bold(false);
                    incomeStatementModel17.setIs_head(false);
                    incomeStatementModels.set(17, incomeStatementModel17);


                    IncomeStatementModel incomeStatementModel18 = incomeStatementModels.get(18);
                    incomeStatementModel18.setTitle("Net Income From Continuing Ops");
                    JSONObject researchDevelopment12 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncomeFromContinuingOps");
                    if (researchDevelopment12.has("longFmt")) {
                        incomeStatementModel18.setData_4(researchDevelopment12.getString("longFmt"));
                    } else {
                        incomeStatementModel18.setData_4("-");
                    }
                    incomeStatementModel18.setIs_bold(true);
                    incomeStatementModel18.setIs_head(false);
                    incomeStatementModels.set(18, incomeStatementModel18);


                    IncomeStatementModel incomeStatementModel19 = incomeStatementModels.get(19);
                    incomeStatementModel19.setTitle("Non-recurring Events");
                    incomeStatementModel19.setData_4("");
                    incomeStatementModel19.setIs_bold(true);
                    incomeStatementModel19.setIs_head(true);
                    incomeStatementModels.set(19, incomeStatementModel19);

                    IncomeStatementModel incomeStatementModel20 = incomeStatementModels.get(20);
                    incomeStatementModel20.setTitle("Discontinued Operations");
                    JSONObject researchDevelopment13 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("discontinuedOperations");
                    if (researchDevelopment13.has("longFmt")) {
                        incomeStatementModel20.setData_4(researchDevelopment13.getString("longFmt"));
                    } else {
                        incomeStatementModel20.setData_4("-");
                    }
                    incomeStatementModel20.setIs_bold(false);
                    incomeStatementModel20.setIs_head(false);
                    incomeStatementModels.set(20, incomeStatementModel20);


                    IncomeStatementModel incomeStatementModel21 = incomeStatementModels.get(21);
                    incomeStatementModel21.setTitle("Extraordinary Items");
                    JSONObject researchDevelopment14 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("extraordinaryItems");
                    if (researchDevelopment14.has("longFmt")) {
                        incomeStatementModel21.setData_4(researchDevelopment14.getString("longFmt"));
                    } else {
                        incomeStatementModel21.setData_4("-");
                    }
                    incomeStatementModel21.setIs_bold(false);
                    incomeStatementModel21.setIs_head(false);
                    incomeStatementModels.set(21, incomeStatementModel21);


                    IncomeStatementModel incomeStatementModel22 = incomeStatementModels.get(22);
                    incomeStatementModel22.setTitle("Effect Of Accounting Changes");
                    JSONObject researchDevelopment15 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("effectOfAccountingCharges");
                    if (researchDevelopment15.has("longFmt")) {
                        incomeStatementModel22.setData_4(researchDevelopment15.getString("longFmt"));
                    } else {
                        incomeStatementModel22.setData_4("-");
                    }
                    incomeStatementModel22.setIs_bold(false);
                    incomeStatementModel22.setIs_head(false);
                    incomeStatementModels.set(22, incomeStatementModel22);


                    IncomeStatementModel incomeStatementModel23 = incomeStatementModels.get(23);
                    incomeStatementModel23.setTitle("Other Items");
                    JSONObject researchDevelopment16 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherItems");
                    if (researchDevelopment16.has("longFmt")) {
                        incomeStatementModel23.setData_4(researchDevelopment16.getString("longFmt"));
                    } else {
                        incomeStatementModel23.setData_4("-");
                    }
                    incomeStatementModel23.setIs_bold(false);
                    incomeStatementModel23.setIs_head(false);
                    incomeStatementModels.set(23, incomeStatementModel23);


                    IncomeStatementModel incomeStatementModel24 = incomeStatementModels.get(24);
                    incomeStatementModel24.setTitle("Net Income");
                    incomeStatementModel24.setData_4("");
                    incomeStatementModel24.setIs_bold(true);
                    incomeStatementModel24.setIs_head(true);
                    incomeStatementModels.set(24, incomeStatementModel24);


                    IncomeStatementModel incomeStatementModel25 = incomeStatementModels.get(25);
                    incomeStatementModel25.setTitle("Net Income");
                    JSONObject researchDevelopment18 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncome");
                    if (researchDevelopment18.has("longFmt")) {
                        incomeStatementModel25.setData_4(researchDevelopment18.getString("longFmt"));
                    } else {
                        incomeStatementModel25.setData_4("-");
                    }
                    incomeStatementModel25.setIs_bold(true);
                    incomeStatementModel25.setIs_head(false);
                    incomeStatementModels.set(25, incomeStatementModel25);


                    IncomeStatementModel incomeStatementModel26 = incomeStatementModels.get(26);
                    incomeStatementModel26.setTitle("Net Income Applicable To Common Shares");
                    JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncomeApplicableToCommonShares");
                    if (researchDevelopment19.has("longFmt")) {
                        incomeStatementModel26.setData_4(researchDevelopment19.getString("longFmt"));
                    } else {
                        incomeStatementModel26.setData_4("-");
                    }
                    incomeStatementModel26.setIs_bold(true);
                    incomeStatementModel26.setIs_head(true);
                    incomeStatementModels.set(26, incomeStatementModel26);


                }


            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return incomeStatementModels;
    }


    public void execute(String... strings) {
        Executor executor = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            String data=doInBackground(strings);
            handler.post(()->{
                onPostExecute(data);
            });
        });
    }

}

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

public class CahsFlowTask  {


    final IncomeStateListener gainerLooserListener;

    public CahsFlowTask(IncomeStateListener gainerLooserListener){
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


                List<IncomeStatementModel> incomeStatementModels_q=getIncomestatementYearly(cashflowStatementHistoryQuarterly);

                List<IncomeStatementModel> incomeStatementModels_y=getIncomestatementYearly(cashflowStatementHistory);


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
            JSONArray incomeStatementHistoryQuarterly_data = incomeStatementHistoryQuarterly.getJSONArray("cashflowStatements");


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
                    incomeStatementModel2.setTitle("Net Income");
                    incomeStatementModel2.setSymbol("netIncome");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("netIncome")) {
                        JSONObject researchDevelopment2 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netIncome");
                        if (researchDevelopment2.has("longFmt")) {
                            incomeStatementModel2.setData_1(researchDevelopment2.getString("longFmt"));
                        } else {
                            incomeStatementModel2.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel2.setData_1("-");
                    }
                    incomeStatementModel2.setIs_bold(true);
                    incomeStatementModel2.setIs_head(true);
                    incomeStatementModel2.setIs_center(true);
                    incomeStatementModels.add(incomeStatementModel2);

                    IncomeStatementModel incomeStatementModel3 = new IncomeStatementModel();
                    incomeStatementModel3.setTitle("Operating Activities, Cash Flows Provided By or Used In");
                    incomeStatementModel3.setSymbol("operating_wctivities_cash");
                    incomeStatementModel3.setData_1("");
                    incomeStatementModel3.setIs_bold(false);
                    incomeStatementModel3.setIs_head(false);
                    incomeStatementModel3.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel3);


                    IncomeStatementModel incomeStatementModel4 = new IncomeStatementModel();
                    incomeStatementModel4.setTitle("Depreciation");
                    incomeStatementModel4.setSymbol("depreciation");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("depreciation")) {
                        JSONObject researchDevelopment3 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("depreciation");
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
                    incomeStatementModel5.setTitle("Adjustments To Net Income");
                    incomeStatementModel5.setSymbol("changeToNetincome");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("changeToNetincome")) {
                        JSONObject researchDevelopment4 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("changeToNetincome");
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
                    incomeStatementModel6.setTitle("Changes In Accounts Receivables");
                    incomeStatementModel6.setSymbol("changeToAccountReceivables");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("changeToAccountReceivables")) {
                        JSONObject researchDevelopment5 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("changeToAccountReceivables");
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
                    incomeStatementModel7.setTitle("Changes In Liabilities");
                    incomeStatementModel7.setSymbol("changeToLiabilities");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("changeToLiabilities")) {
                        JSONObject researchDevelopment6 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("changeToLiabilities");
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
                    incomeStatementModel9.setTitle("Changes In Inventories");
                    incomeStatementModel9.setSymbol("changeToInventory");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("changeToInventory")) {
                        JSONObject researchDevelopment8 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("changeToInventory");
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
                    incomeStatementModel10.setTitle("Changes In Other Operating Activities");
                    incomeStatementModel10.setSymbol("changeToOperatingActivities");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("changeToOperatingActivities")) {
                        JSONObject researchDevelopment10 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("changeToOperatingActivities");
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
                    incomeStatementModel11.setTitle("Total Cash Flow From Operating Activities");
                    incomeStatementModel11.setSymbol("totalCashFromOperatingActivities");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("totalCashFromOperatingActivities")) {
                        JSONObject researchDevelopment11 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalCashFromOperatingActivities");
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
                    incomeStatementModel12.setTitle("Investing Activities, Cash Flows Provided By or Used In");
                    incomeStatementModel12.setSymbol("investing_activities_cash");
                     incomeStatementModel12.setData_1("");
                    incomeStatementModel12.setIs_bold(false);
                    incomeStatementModel12.setIs_head(true);
                    incomeStatementModel12.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel12);


                    IncomeStatementModel incomeStatementModel13 = new IncomeStatementModel();
                    incomeStatementModel13.setTitle("Capital Expenditures");
                    incomeStatementModel13.setSymbol("capitalExpenditures");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("capitalExpenditures")) {
                        JSONObject researchDevelopment13 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("capitalExpenditures");
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
                    incomeStatementModel14.setTitle("Investments");
                    incomeStatementModel14.setSymbol("investments");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("investments")) {
                        JSONObject researchDevelopment14 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("investments");
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
                    incomeStatementModel14.setIs_bold(false);
                    incomeStatementModel14.setIs_head(false);
                    incomeStatementModel14.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel14);


                    IncomeStatementModel incomeStatementModel15 = new IncomeStatementModel();
                    incomeStatementModel15.setSymbol("otherCashflowsFromInvestingActivities");
                    incomeStatementModel15.setTitle("Other Cash flows from Investing Activities");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("otherCashflowsFromInvestingActivities")) {
                        JSONObject researchDevelopment14 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherCashflowsFromInvestingActivities");
                        if (researchDevelopment14.has("longFmt")) {
                            incomeStatementModel15.setData_1(researchDevelopment14.getString("longFmt"));
                        }
                        else {
                            incomeStatementModel15.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel15.setData_1("-");
                    }

                    incomeStatementModel15.setIs_bold(false);
                    incomeStatementModel15.setIs_head(false);
                    incomeStatementModel15.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel15);



                    IncomeStatementModel incomeStatementModel16 = new IncomeStatementModel();
                    incomeStatementModel16.setTitle("Total Cash Flows From Investing Activities");
                    incomeStatementModel16.setSymbol("totalCashflowsFromInvestingActivities");

                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("totalCashflowsFromInvestingActivities")) {
                        JSONObject researchDevelopment16 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalCashflowsFromInvestingActivities");
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
                    incomeStatementModel17.setTitle("Financing Activities, Cash Flows Provided By or Used In");
                    incomeStatementModel17.setSymbol("financing_activities_cash");
                    incomeStatementModel17.setData_1("");
                    incomeStatementModel17.setIs_bold(false);
                    incomeStatementModel17.setIs_head(true);
                    incomeStatementModel17.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel17);

                    IncomeStatementModel incomeStatementModel18 = new IncomeStatementModel();
                    incomeStatementModel18.setTitle("Dividends Paid");
                    incomeStatementModel18.setSymbol("dividendsPaid");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("dividendsPaid")) {
                        JSONObject researchDevelopment18 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("dividendsPaid");
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
                    incomeStatementModel19.setTitle("Net Borrowings");
                    incomeStatementModel19.setSymbol("netBorrowings");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("netBorrowings")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("netBorrowings");
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
                    incomeStatementModel21.setTitle("Other Cash Flows from Financing Activities");
                    incomeStatementModel21.setSymbol("otherCashflowsFromFinancingActivities");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("otherCashflowsFromFinancingActivities")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("otherCashflowsFromFinancingActivities");
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
                    incomeStatementModel22.setTitle("Total Cash Flows From Financing Activities");
                    incomeStatementModel22.setSymbol("totalCashFromFinancingActivities");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("totalCashFromFinancingActivities")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("totalCashFromFinancingActivities");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel22.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel22.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel22.setData_1("-");
                    }
                    incomeStatementModel22.setIs_bold(true);
                    incomeStatementModel22.setIs_head(false);
                    incomeStatementModel22.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel22);


                    IncomeStatementModel incomeStatementModel23 = new IncomeStatementModel();
                    incomeStatementModel23.setTitle("Change In Cash and Cash Equivalents");
                    incomeStatementModel23.setSymbol("changeInCash");
                    if(incomeStatementHistoryQuarterly_data.getJSONObject(i).has("changeInCash")) {
                        JSONObject researchDevelopment19 = incomeStatementHistoryQuarterly_data.getJSONObject(i).getJSONObject("changeInCash");
                        if (researchDevelopment19.has("longFmt")) {
                            incomeStatementModel23.setData_1(researchDevelopment19.getString("longFmt"));
                        } else {
                            incomeStatementModel23.setData_1("-");
                        }
                    }
                    else{
                        incomeStatementModel23.setData_1("-");
                    }
                    incomeStatementModel23.setIs_bold(true);
                    incomeStatementModel23.setIs_head(false);
                    incomeStatementModel23.setIs_center(false);
                    incomeStatementModels.add(incomeStatementModel23);




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

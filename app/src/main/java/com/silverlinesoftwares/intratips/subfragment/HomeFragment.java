package com.silverlinesoftwares.intratips.subfragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.EquityAdapterR;
import com.silverlinesoftwares.intratips.listeners.AccountOpenClick;
import com.silverlinesoftwares.intratips.listeners.BuySellClickListener;
import com.silverlinesoftwares.intratips.models.EquityModel;
import com.silverlinesoftwares.intratips.models.UserModel;
import com.silverlinesoftwares.intratips.tasks.EquityTask;
import com.silverlinesoftwares.intratips.tasks.IntraHighTask;
import com.silverlinesoftwares.intratips.ui.CustomRecyclerView;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import tech.gusavila92.websocketclient.WebSocketClient;


public class HomeFragment extends Fragment implements BuySellClickListener, AccountOpenClick {

    public static ProgressBar progress;
    private EquityAdapterR equityAdapter=null;
    private TimerTask timerTask;
    private Timer t;
    boolean isFirstRun=true;
    boolean starts=true;
    private WebSocketClient webSocketClient;
    private WebView webview;
    private ArrayList<Object> currentequityModels;


    public HomeFragment() {
        // Required empty public constructor
    }



    public void liveUpdates(){
        Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                //    Toast.makeText(getContext(), "Intra running!", Toast.LENGTH_SHORT).show();
                // This is where you do your work in the UI thread.
                // Your worker tells you in the message what to do.
            }
        };
        Message message = mHandler.obtainMessage(0, null);
        message.sendToTarget();

        if(equityAdapter!=null) {

            if(isFirstRun)
            {
                Log.d("Loop","Runner");

                try {
                    for (int i=0;i<currentequityModels.size();i++) {
                        if(currentequityModels.get(i) != null) {
                            if(currentequityModels.get(i) instanceof EquityModel) {
                                if (!((EquityModel) currentequityModels.get(i)).getSymbol().isEmpty()) {
                                    IntraHighTask intraHighTask = new IntraHighTask(getContext(), equityAdapter, (EquityModel) currentequityModels.get(i));
                                    intraHighTask.execute();
                                    // StaticMethods.executeAsyncTask(intraHighTask);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(equityAdapter.getDataList().size()>0) {

                    isFirstRun = false;
                    JSONArray jsonArray = new JSONArray();
                    for (int i=0;i<currentequityModels.size();i++) {
                        if(currentequityModels.get(i) != null) {
                            if (currentequityModels.get(i) instanceof EquityModel) {
                                jsonArray.put(((EquityModel) currentequityModels.get(i)).getSymbol());
                            }
                        }
                    }
                    makeSocketConnection(jsonArray.toString());
                }
            }


        }



    }

    private void makeSocketConnection(String topic) {
        createWebSocketClient(topic);

    }

    private void createWebSocketClient(final String topic) {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("wss://streamer.finance.yahoo.com/");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                String data="{\"subscribe\":"+topic+"}";
                Log.i("WebSocket", "Session is starting");
                Log.d("Data: ",data);
                webSocketClient.send(data);
            }
            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received"+s);
                if(s!=null && !s.isEmpty()) {
                    convertBas64toString(s);
                }
                if(getActivity()!=null) {
                    getActivity().runOnUiThread(() -> {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
            @Override
            public void onBinaryReceived(byte[] data) {
            }
            @Override
            public void onPingReceived(byte[] data) {
            }
            @Override
            public void onPongReceived(byte[] data) {
            }
            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }
            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Closed ");
                System.out.println("onCloseReceived");
            }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    private void convertBas64toString(String s) {
        passDatatoJs(s);
    }

    private void passDatatoJs(final String data) {
        webview.post(() -> {
            webview.loadUrl("javascript:printdata('"+data+"')");
            //mWebView.loadUrl(...).
        });
    }




    private void stopLiveUpdates(){
        isFirstRun=true;
        if(webSocketClient!=null) {
            try {
                webSocketClient.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        starts=true;
        //Toast.makeText(getContext(), "Stop Timer", Toast.LENGTH_SHORT).show();
        if(t!=null) {
            t.cancel();
            t.purge();
        }
        if(timerTask!=null){
            timerTask.cancel();
        }

    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        stopLiveUpdates();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopLiveUpdates();

    }

    @Override
    public void onStop() {
        super.onStop();
        stopLiveUpdates();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_equity, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("1353519AECCDBB17080F5B14D4A6DE8C");

        final RecyclerView listView=view.findViewById(R.id.list_item);
        progress=view.findViewById(R.id.progress);
        webview=view.findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/data.html");   // now it will not fail here
        webview.getSettings().setJavaScriptEnabled(true);
        //if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN_MR1) {
        webview.addJavascriptInterface(this, "Android");

        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        listView.setLayoutManager(linearLayoutManager);


        currentequityModels=new ArrayList<>();
       // currentequityModels.add(new BannerModel(""));
        equityAdapter=new EquityAdapterR(listView,currentequityModels,getActivity(),HomeFragment.this,getActivity(),HomeFragment.this);
        listView.setAdapter(equityAdapter);
        UserModel userModel=null;
        if(getContext()!=null) {
            userModel = StaticMethods.getUserDetails(getContext());
        }
        String userid="";
        String token="";
        if(userModel!=null){
            userid=userModel.getId();
        }
        if(StaticMethods.getLoginToken(getContext())!=null){
            token=StaticMethods.getLoginToken(getContext());
        }
        EquityTask equityTask=new EquityTask(getContext(),equityAdapter,currentequityModels,userid,token);

        equityTask.execute();







        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                this::StartTimer,
                12000);


    }

    private void ShowBuySellStock(String symbol) {
        if(getContext()!=null) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
            @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.show_button_dialog, null);
            bottomSheetDialog.setContentView(view);
            bottomSheetDialog.show();
        }

    }

    @JavascriptInterface
    public void alertJson(final String myJSON) {
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                () -> {
                    Log.i("tag", "This'll run 300 milliseconds later");

                    for (int i=0;i<currentequityModels.size();i++){
                        if(currentequityModels.get(i) instanceof EquityModel) {
                            Log.d("Price Symbol : ", "" + ((EquityModel) currentequityModels.get(i)).getPrice() + " : " + ((EquityModel) currentequityModels.get(i)).getSymbol());
                        }
                    }
                    if(getActivity()!=null) {
                        getActivity().runOnUiThread(() -> {
                            Log.d("Data : ", "" + myJSON);
                            try {
                                JSONObject jsonObject = new JSONObject(myJSON);


                                for (int i = 0; i < currentequityModels.size(); i++) {
                                    if (currentequityModels.get(i) instanceof EquityModel) {
                                        if (jsonObject.has(((EquityModel) currentequityModels.get(i)).getSymbol())) {
                                            Log.d("Updating", "Updating");
                                            JSONObject stock = jsonObject.getJSONObject(((EquityModel) currentequityModels.get(i)).getSymbol());
                                            JSONObject prices = stock.getJSONObject("price");
                                            if (prices.has("regularMarketPrice")) {
                                                String regularMarketPrice = prices.getString("regularMarketPrice");
                                                Log.d("Price", "" + regularMarketPrice);


                                                try {
                                                    Double currentPrice = Double.parseDouble(prices.getString("regularMarketPrice"));

                                                    equityAdapter.changePrice(regularMarketPrice, i);
                                                    ///((EquityModel) currentequityModels.get(i)).setPrice(regularMarketPrice);
                                                    //equityAdapter.notifyDataSetChanged();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        });
                    }
                },
                1000);

    }

    @Override
    public void onStart() {
        super.onStart();
        StartTimer();
    }

    private void StartTimer() {
        if(starts) {
            // Toast.makeText(getContext(), "Started Timer", Toast.LENGTH_SHORT).show();
            starts=false;
            t = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    liveUpdates();
                }
            };
            t.scheduleAtFixedRate(timerTask, 0,12000);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLiveUpdates();


    }

    @Override
    public void onResume() {
        super.onResume();
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                this::StartTimer,
                10000);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLiveUpdates();

    }

    @Override
    public void onBuy(String symbol,String price) {

        ShowDialog(symbol,price,true);
    }

    @Override
    public void onSell(String symbol,String price) {
        ShowDialog(symbol,price,false);

    }

    private void injectCSS(WebView webView) {
        try {
            if(getContext()!=null) {
                InputStream inputStream = getContext().getAssets().open("style_chart.css");
                byte[] buffer = new byte[inputStream.available()];
                final int read = inputStream.read(buffer);
                inputStream.close();
                String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
                webView.loadUrl("javascript:(function() {" +
                        "var parent = document.getElementsByTagName('head').item(0);" +
                        "var style = document.createElement('style');" +
                        "style.type = 'text/css';" +
                        // Tell the browser to BASE64-decode the string into your script !!!
                        "style.innerHTML = window.atob('" + encoded + "');" +
                        "parent.appendChild(style)" +
                        "})()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void injectCSS2(WebView webView) {
        try {
            if(getActivity()!=null) {
                InputStream inputStream = getActivity().getAssets().open("style2.css");
                byte[] buffer = new byte[inputStream.available()];
                final int read=inputStream.read(buffer);
                inputStream.close();
                String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
                webView.loadUrl("javascript:(function() {" +
                        "var parent = document.getElementsByTagName('head').item(0);" +
                        "var style = document.createElement('style');" +
                        "style.type = 'text/css';" +
                        // Tell the browser to BASE64-decode the string into your script !!!
                        "style.innerHTML = window.atob('" + encoded + "');" +
                        "parent.appendChild(style)" +
                        "})()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onTechnical(String symbol) {

        symbol=symbol.replace(".BO","");
        symbol=symbol.replace(".NS","");
        AlertDialog.Builder al = null;
        if(getContext()!=null) {
            al = new AlertDialog.Builder(getContext());
        }
        View view=getLayoutInflater().inflate(R.layout.weblayout_dialog,null);
        EditText edit = view.findViewById(R.id.edit);
        final ProgressBar progress=view.findViewById(R.id.progress);
        TextView title=view.findViewById(R.id.title);
        edit.setFocusable(true);
        edit.requestFocus();

        title.setText(String.format("Technical Analysis : %s", symbol));
        if(al!=null) {
            al.setView(view);
        }
        Button button=view.findViewById(R.id.close);
        final WebView webView=view.findViewById(R.id.webview);
        try {

            InputStream is = getContext().getAssets().open("tech.html");
            int size = is.available();

            byte[] buffer = new byte[size];
            final int read=is.read(buffer);
            is.close();

            String str = new String(buffer);

            str = str.replace("SYMBOL_HOLDER",symbol);

            if (Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            } else {
                CookieManager.getInstance().setAcceptCookie(true);
            }
            webView.loadUrl("about:blank");
            webView.getSettings().setLoadWithOverviewMode(true);

            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    injectCSS(webView);
                    super.onPageStarted(view, url, favicon);
                    progress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
//                    injectCSS(webView);
                    super.onPageFinished(view, url);
                    progress.setVisibility(View.GONE);
                }
            });
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadData(str,"text/html","utf-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(al!=null) {
            final AlertDialog alertDialog = al.show();

            button.setOnClickListener(v -> alertDialog.dismiss());
        }

    }

    private void ShowDialog(String symbol, final String price, final boolean isBuy) {

        symbol=symbol.replace(".BO","");
        symbol=symbol.replace(".NS","");
        AlertDialog.Builder al=null;
        if(getContext()!=null) {
            al = new AlertDialog.Builder(getContext());
        }
        View view=getLayoutInflater().inflate(R.layout.buy_sell_dialog,null);
        if(al!=null) {
            al.setView(view);
        }
        Button button=view.findViewById(R.id.close);
        final EditText priceText=view.findViewById(R.id.entry_price);
        final EditText target=view.findViewById(R.id.target);
        final EditText stop_loss=view.findViewById(R.id.stop_loss);
        final EditText qty=view.findViewById(R.id.quantity);

        RadioButton bo=view.findViewById(R.id.bo);
        RadioButton co=view.findViewById(R.id.co);
        RadioButton reg=view.findViewById(R.id.reg);
        RadioButton amo=view.findViewById(R.id.amo);


        RadioButton limit=view.findViewById(R.id.limit);
        RadioButton market_price=view.findViewById(R.id.market);


        final TextView profit=view.findViewById(R.id.profit);
        final TextView loss=view.findViewById(R.id.loss);
        TextView risk=view.findViewById(R.id.risk_reward);
        Button zerodha=view.findViewById(R.id.open_zerodha);
        zerodha.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://zerodha.com/open-account?c=ZMPCJG"));
            startActivity(intent);
        });


        Button buy_button=view.findViewById(R.id.buy_sell);

        final TextView total_price=view.findViewById(R.id.totalprice);

        if(isBuy){
            buy_button.setText(getString(R.string.buy));
        }
        else{
            buy_button.setText(getString(R.string.selll));
        }
        final String[] order_type = {"LIMIT"};
        final String[] varity = {"bo"};


        RadioGroup radioGroup=view.findViewById(R.id.order_type);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId==R.id.limit){
                order_type[0] ="LIMIT";
            }
            else{
                order_type[0] ="MARKET";
            }
        });


        RadioGroup radioGroup2=view.findViewById(R.id.varity);

        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId==R.id.reg){
                varity[0] ="regular";
            }
            else if(checkedId==R.id.amo){
                varity[0] ="amo";
            }
            else if(checkedId==R.id.co){
                varity[0] ="co";
            }
            else{
                varity[0]="bo";
            }
        });




        priceText.setText(String.format("%s", price));

        if(price!=null) {
            if (!price.isEmpty()) {
                calculatePrice(priceText,qty,total_price);
                displayTargetStopLoss(price, stop_loss, target, isBuy, profit, loss, qty);
            }
        }



        priceText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                if(!priceText.getText().toString().isEmpty()) {
                    displayTargetStopLoss(priceText.getText().toString(), stop_loss, target,isBuy,profit,loss,qty);
                }
            }
            public void afterTextChanged(Editable editable) { }
            public void beforeTextChanged(CharSequence cs, int i, int j, int
                    k) { }
        });



        priceText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int s, int b, int c) {
               calculatePrice(priceText,qty,total_price);
            }
            public void afterTextChanged(Editable editable) { }
            public void beforeTextChanged(CharSequence cs, int i, int j, int
                    k) { }
        });


        qty.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                calculatePrice(priceText,qty,total_price);
                if(!qty.getText().toString().isEmpty() && !priceText.getText().toString().isEmpty()){
                    displayTargetStopLoss(priceText.getText().toString(), stop_loss, target,isBuy,profit,loss,qty);
                }
            }
            public void afterTextChanged(Editable editable) { }
            public void beforeTextChanged(CharSequence cs, int i, int j, int
                    k) { }
        });


        if(al!=null) {
            final AlertDialog alertDialog = al.show();

            button.setOnClickListener(v -> alertDialog.dismiss());


            final String finalSymbol = symbol;
            buy_button.setOnClickListener(v -> {
                alertDialog.dismiss();
                if (isBuy) {
                    ShowBuyDialog(finalSymbol, "BUY", qty.getText().toString(), order_type[0], varity[0], priceText.getText().toString(), stop_loss.getText().toString(), target.getText().toString());
                } else {
                    ShowBuyDialog(finalSymbol, "SELL", qty.getText().toString(), order_type[0], varity[0], priceText.getText().toString(), stop_loss.getText().toString(), target.getText().toString());
                }
            });

        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void ShowBuyDialog(String symbol, String buy, String qty, String ordertype, String varity, String price, String stop_loss, String target) {
        symbol=symbol.replace(".BO","");
        symbol=symbol.replace(".NS","");
        AlertDialog.Builder al=null;
        if(getContext()!=null) {
            al = new AlertDialog.Builder(getContext());
        }
        View view=getLayoutInflater().inflate(R.layout.weblayout_dialog,null);
        EditText edit = view.findViewById(R.id.edit);
        edit.setFocusable(true);
        edit.requestFocus();
        if(al!=null) {
            al.setView(view);
        }

        Button zerodha=view.findViewById(R.id.open_zerodha);
        zerodha.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://zerodha.com/open-account?c=ZMPCJG"));
            startActivity(intent);
        });
        Button button=view.findViewById(R.id.close);
        final WebView webView=view.findViewById(R.id.webview);
        try {

            InputStream is = getContext().getAssets().open("zerodha.html");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String str = new String(buffer);

            str = str.replace("SYMBOL_HOLDER",symbol);
            str = str.replace("BUY_SELL_HOLDER",buy);
            str = str.replace("QUANTITY_HOLDER",qty);
            str = str.replace("ORDER_HOLDER",ordertype);
            str = str.replace("VARITY_HOLDER",varity);
            str = str.replace("PRICE_HOLDER",price);
            str = str.replace("STOPLOSS_HOLDER",stop_loss);
            str = str.replace("TARGET_HOLDER",target);



            if (android.os.Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            } else {
                CookieManager.getInstance().setAcceptCookie(true);
            }

            webView.loadUrl("about:blank");
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {

                    super.onPageStarted(view, url, favicon);
                    injectCSS2(webView);
                }

                @Override
                public void onPageFinished(WebView view, String url) {

                    super.onPageFinished(view, url);
                    injectCSS2(webView);
                }
            });
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadData(str,"text/html","utf-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(al!=null) {
            final AlertDialog alertDialog = al.show();

            button.setOnClickListener(v -> alertDialog.dismiss());
        }
    }

    private void displayTargetStopLoss(String sprice_amt,TextView stop_loss,TextView target,boolean isBuy,TextView profit,TextView loss,EditText qty){
        double price_amt=Double.parseDouble(sprice_amt);

        double stop_loss_amt=price_amt*2/100;
        double profit_amt=price_amt*4/100;
         DecimalFormat df2 = new DecimalFormat("##.##");


        if(isBuy){
            double pr=price_amt+profit_amt;
            double sr=price_amt-stop_loss_amt;
            target.setText(String.format("%s", df2.format(pr)));
            stop_loss.setText(String.format("%s", df2.format(sr)));
        }
        else{
            double pr=price_amt-profit_amt;
            double sr=price_amt+profit_amt;
            target.setText(String.format("%s", df2.format(pr)));
            stop_loss.setText(String.format("%s", df2.format(sr)));
        }

        if(qty!=null){
            if(!qty.getText().toString().isEmpty()){
                profit.setText(String.format("%s", df2.format(profit_amt * Double.parseDouble(qty.getText().toString()))));
                loss.setText(String.format("%s", df2.format(stop_loss_amt * Double.parseDouble(qty.getText().toString()))));
            }
        }

    }

    private void calculatePrice(EditText priceText, EditText qty, TextView total_price) {
        DecimalFormat df2 = new DecimalFormat("##.##");
        if(priceText!=null){
            if(qty!=null){
                if(!priceText.getText().toString().isEmpty() && !qty.getText().toString().isEmpty()){
                    total_price.setText(String.format("TOTAL PRICE : %s", df2.format(Double.parseDouble(priceText.getText().toString()) * Double.parseDouble(qty.getText().toString()))));

                }
                else{
                    total_price.setText(getString(R.string.total_price_zero));
                }
            }
            else{
                total_price.setText(getString(R.string.total_price_zero));
            }
        }
        else{
            total_price.setText(getString(R.string.total_price_zero));
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void ShowDialog2(String symbol) {

        symbol=symbol.replace(".BO","");
        symbol=symbol.replace(".NS","");
        AlertDialog.Builder al=null;
        if(getContext()!=null) {
            al = new AlertDialog.Builder(getContext());
        }
        View view=getLayoutInflater().inflate(R.layout.weblayout_dialog,null);
        EditText edit = view.findViewById(R.id.edit);
        edit.setFocusable(true);
        edit.requestFocus();

        if(al!=null) {
            al.setView(view);
        }
        Button button=view.findViewById(R.id.close);
        WebView webView=view.findViewById(R.id.webview);
        try {

            InputStream is = getContext().getAssets().open("zerodha.html");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String str = new String(buffer);

            str = str.replace("SYMBOL_HOLDER",symbol);

            if (android.os.Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            } else {
                CookieManager.getInstance().setAcceptCookie(true);
            }
            webView.loadUrl("about:blank");
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadData(str,"text/html","utf-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(al!=null) {
            final AlertDialog alertDialog = al.show();

            button.setOnClickListener(v -> alertDialog.dismiss());
        }

    }

    @Override
    public void onClick() {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://zerodha.com/open-account?c=ZMPCJG"));
                startActivity(intent);
    }

    @Override
    public void onAliceClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://alicebluepartner.com/furthergrow/"));
        startActivity(intent);
    }

    @Override
    public void onUpstockClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://upstox.com/open-account/?f=Z1JV"));
        startActivity(intent);
    }
}

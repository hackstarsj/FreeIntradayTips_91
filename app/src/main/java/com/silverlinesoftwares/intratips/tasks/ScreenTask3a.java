package com.silverlinesoftwares.intratips.tasks;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScreenTask3a extends AsyncTask<String ,String,String> {

    Context context;
    String url;
    ProgressBar progressBar;
    TableLayout tl;
    int columns_size=0;
    List<String > list=new ArrayList<>();
    public ScreenTask3a(Context context,String  url,TableLayout layout,ProgressBar progressBar){
        this.context=context;
        this.url=url;
        this.progressBar=progressBar;
        tl=layout;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null) {
            BuildHeader(s);
            BuildFirstStep(s);
        }
        if(progressBar!=null){
            progressBar.setVisibility(View.GONE);
        }

    }

    private void BuildHeader(String s) {
        final String regex = "<thead><tr><th null>(.*?)<\\/tr><\\/thead>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(0));
                BuildHeader2(matcher.group(0));
                break;
            }
            break;
        }
    }

    private void BuildHeader2(String group) {
        TableRow tr_head = new TableRow(context);

        final String regex = "<th (.*?)>(.*?)<\\/th>";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(group);
        while (matcher.find()) {
            if(columns_size==7){
                break;
            }
            System.out.println("Full match: " + matcher.group(0));
            for (int i = 2; i <= matcher.groupCount(); i++) {
                columns_size++;

                System.out.println("Group " + i + ": " + matcher.group(i));
                TextView label_weight_kg8 = new TextView(context);
                String il=matcher.group(i);
                if(android.text.Html.fromHtml(il).toString().contains("Chart")){
                    continue;
                }
                //  label_weight_kg.setId(21);// define id that must be unique
                label_weight_kg8.setText(android.text.Html.fromHtml(il).toString()); // set the text for the header
                label_weight_kg8.setTextColor(Color.WHITE); // set the color
                label_weight_kg8.setBackgroundResource(R.drawable.border_grey);
                label_weight_kg8.setPadding(10, 5, 10, 5); // set the padding (if required)
                tr_head.addView(label_weight_kg8);
                break;
            }
        }

        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(progressBar!=null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void BuildFirstStep(String s) {
        final String regex = "<tr(.*?)>(.*?)<\\/tr>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            if(matcher.groupCount()>=2) {
                BuildSecondStep(matcher.group(2));
            }
        }

        showTheData();

    }

    private void BuildSecondStep(String group) {
        final String regex = "<td(.*?)>(.*?)<\\/td>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(group);

        StringBuilder append=new StringBuilder();
        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            // for(int i=0;i<matcher.groupCount();i++){
            append.append(matcher.group(2)).append(";");

            // }
        }

        list.add(append.toString());

    }


    private void showTheData(){
        if(list!=null){
            for(int i=0;i<list.size();i++){

                if(tl!=null) {
                    String[] items = list.get(i).split(";");
                    if (items.length >= columns_size) {

                        TableRow tr = new TableRow(context);
                        tr.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.FILL_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));


                        int in=0;
                        for (String item : items) {

                            if(in==7){
                                break;
                            }
                            in++;
                            TextView labelDATE = new TextView(context);
                            if(android.text.Html.fromHtml(item).toString().contains("Chart")){
                                continue;
                            }
                            labelDATE.setText(android.text.Html.fromHtml(item).toString());
                            labelDATE.setGravity(Gravity.CENTER);
                            labelDATE.setPadding(10, 5, 10, 5);
                            tr.addView(labelDATE);
                            if (i % 2 != 0) {
                                labelDATE.setBackgroundResource(R.drawable.border_white);
                                labelDATE.setTextColor(Color.WHITE);


                            } else {
                                labelDATE.setBackgroundResource(R.drawable.border_black);
                                labelDATE.setTextColor(Color.BLACK);

                            }

                        }


                        tl.addView(tr, new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.FILL_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                    }
                }

            }
        }
    }


    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(); // connect timeout



        Request request =
                new Request.Builder()
                        .url(url)
                        .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()) {
            try {
                if (response.body() != null) {
                    return response.body().string();
                }
                else{
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}


package com.silverlinesoftwares.intratips.adapters;

import android.util.Log;

import com.silverlinesoftwares.intratips.models.SearchModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sanjeev on 4/2/18.
 */

public class LocationModel {

    private static final String TAG = LocationModel.class.getSimpleName();
    public static ArrayList<String> arrayList=null;

    private static final String URL = "https://furthergrow.silverlinesoftwares.com/symbol.php?symbol=hdf";
    private static final String API_TYPE = "search";
    private JSONArray PredictArray=new JSONArray();

    public List<SearchModel> autocomplete (String input) {

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
//            StringBuilder sb = new StringBuilder(URL  API_TYPE);

            URL url = new URL("https://furthergrow.silverlinesoftwares.com/symbol.php?symbol="+URLEncoder.encode(input, "utf8"));
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:67.0) Gecko/20100101 Firefox/67.0");
            conn.setRequestProperty("If-None-Match","W/\"47b-SFzZkBfOPA1H11FBxWlqrw");
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Api Error", e);
        } catch (IOException e) {
            Log.e(TAG, "Connection Error", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
           // JSONObject Object = new JSONObject(jsonResults.toString());
            PredictArray = new JSONArray(jsonResults.toString());
           // JSONArray PredictArrayID = new JSONArray(jsonResults.toString());
        } catch (JSONException e) {
            Log.e(TAG, "Cannot Parse Json", e);
        }
        List<SearchModel> arrayList=new ArrayList<SearchModel>();
        for(int i=0;i<PredictArray.length();i++){
            try {

                if(PredictArray.getJSONObject(i).getString("symbol").endsWith(".NS"))
                {
                    SearchModel searchModel = new SearchModel(PredictArray.getJSONObject(i).getString("symbol"), PredictArray.getJSONObject(i).getString("name"), PredictArray.getJSONObject(i).getString("exch"), PredictArray.getJSONObject(i).getString("type"), PredictArray.getJSONObject(i).getString("exchDisp"), PredictArray.getJSONObject(i).getString("typeDisp"));
                    arrayList.add(searchModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }
}
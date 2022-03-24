package com.silverlinesoftwares.intratips.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.silverlinesoftwares.intratips.models.SearchModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {
    public static List<SearchModel> getBooks(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        List<SearchModel> arrayItems=new ArrayList<>();
        String serializedObject = sharedPreferences.getString("search_list", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SearchModel>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }

    public static void saveSearch(Context context,SearchModel searchModel) {
        List<SearchModel> arrayItems=new ArrayList<>();
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        String serializedObject = sharedPreferences.getString("search_list", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SearchModel>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        arrayItems.add(searchModel);
        Gson gson = new Gson();
        String json = gson.toJson(arrayItems);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("search_list", json);
        editor.apply();
    }

}

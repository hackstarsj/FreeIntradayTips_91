package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.SearchModel;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends ArrayAdapter<String> {

        List<SearchModel> resultList=new ArrayList<>();

        Context mContext;
        LayoutInflater inflater;
        int mResource;

        LocationModel mPlaceAPI = new LocationModel();

        public SearchAdapter(Context context, int resource,List<SearchModel> searchModels) {
                super(context, resource);

                mContext = context;
                mResource = resource;
                inflater= LayoutInflater.from(context);
                resultList.addAll(searchModels);
        }

            @Override
            public int getCount() {
                    // Last item will be the footer
                    return resultList.size();
            }

        @Override
        public String getItem(int position) {

            try {
                if(resultList.size()>position) {
                    return resultList.get(position).getSymbol();
                }
                else
                {
                    return "";
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    return "";
            }
        }



        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v=convertView;
                TextView title;
                TextView symbol;
                TextView exchange;
                if(v==null){
                v= inflater.inflate(R.layout.searchrow,parent,false);
                v.setTag(R.id.txt_symbol,v.findViewById(R.id.txt_symbol));
                v.setTag(R.id.txt_exchange,v.findViewById(R.id.txt_exchange));
                v.setTag(R.id.txt_name,v.findViewById(R.id.txt_name));
                }
                title=(TextView)v.getTag(R.id.txt_name);
                symbol=(TextView)v.getTag(R.id.txt_symbol);
                exchange=(TextView)v.getTag(R.id.txt_exchange);

                SearchModel downloadPending=resultList.get(position);
                title.setText(downloadPending.getName());
                symbol.setText(downloadPending.getSymbol());
                exchange.setText(downloadPending.getExchDisp());

                return v;

                }
        }
package com.silverlinesoftwares.intratips.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.models.SearchModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sanjeev on 4/2/18.
 */

public class LocationAdapter extends ArrayAdapter<String> implements Filterable {

    List<SearchModel> resultList=new ArrayList<>();

    final Context mContext;
    final LayoutInflater inflater;
    final int mResource;

    final LocationModel mPlaceAPI = new LocationModel();

    public LocationAdapter(Context context, int resource) {
        super(context, resource);

        mContext = context;
        mResource = resource;
        inflater=LayoutInflater.from(context);
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
            else {
                return "";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return "";
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // resultList = map.get("address");
                    resultList= mPlaceAPI.autocomplete(constraint.toString());
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
//                    return filterResults;
                }
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }




    @NonNull
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
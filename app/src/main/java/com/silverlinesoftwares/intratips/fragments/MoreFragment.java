package com.silverlinesoftwares.intratips.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.ActiveStockActivity;
import com.silverlinesoftwares.intratips.activity.BlockDealActivity;
import com.silverlinesoftwares.intratips.activity.BulkDealActivity;
import com.silverlinesoftwares.intratips.activity.CoinActivity;
import com.silverlinesoftwares.intratips.activity.CommodityActivty;
import com.silverlinesoftwares.intratips.activity.CurrecnyActivty;
import com.silverlinesoftwares.intratips.activity.EconomyActivity;
import com.silverlinesoftwares.intratips.activity.FilAndDilActivity;
import com.silverlinesoftwares.intratips.activity.ForCastActivity;
import com.silverlinesoftwares.intratips.activity.HighLowActivit;
import com.silverlinesoftwares.intratips.activity.MostDeliveryActivity;
import com.silverlinesoftwares.intratips.activity.NewWebActivity;
import com.silverlinesoftwares.intratips.activity.NewsActivity;
import com.silverlinesoftwares.intratips.activity.OiSpurtsActivity;
import com.silverlinesoftwares.intratips.activity.PreMarketOpen;
import com.silverlinesoftwares.intratips.activity.PriceBandActivity;
import com.silverlinesoftwares.intratips.activity.ResultActivtiy;
import com.silverlinesoftwares.intratips.activity.ResultActivtiyOption;
import com.silverlinesoftwares.intratips.activity.ShortSellActivity;
import com.silverlinesoftwares.intratips.activity.VolumeGainerActivity;
import com.silverlinesoftwares.intratips.util.Constant;


public class MoreFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public MoreFragment() {
        // Required empty public constructor
    }

    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button global_news=view.findViewById(R.id.global_news);
        Button stock_news=view.findViewById(R.id.stock_news);
        Button startup_news=view.findViewById(R.id.startup_news);
        Button internation_news=view.findViewById(R.id.internation_news);
        Button sip_news=view.findViewById(R.id.sip_news);
        Button politics_news=view.findViewById(R.id.politics_news);
        Button result=view.findViewById(R.id.result);
        Button option=view.findViewById(R.id.result_option);
        Button coins=view.findViewById(R.id.coins);

        Button sport_news=view.findViewById(R.id.sport_news);
        Button comodity_news=view.findViewById(R.id.comodity_news);
        Button business_news=view.findViewById(R.id.business_news);
        Button currency_news=view.findViewById(R.id.currency_news);
        Button earning_news=view.findViewById(R.id.earning_news);
        Button economy_news=view.findViewById(R.id.economy_news);
        Button active_stock=view.findViewById(R.id.active_stock);
        Button premarket_open=view.findViewById(R.id.premarket_open);
        Button high_week=view.findViewById(R.id.high_week);
        Button low_week=view.findViewById(R.id.low_week);
        Button volume_gainers=view.findViewById(R.id.volume_gainers);
        Button price_band=view.findViewById(R.id.price_band_hitters);
        Button oi_spurts=view.findViewById(R.id.oi_spurts);
        Button bulk_deal=view.findViewById(R.id.bulk_deals);
        Button block_deal=view.findViewById(R.id.block_deals);
        Button fil_app=view.findViewById(R.id.fidi);
        Button most_deliver=view.findViewById(R.id.most_delivery);
        Button short_sell=view.findViewById(R.id.short_selling);
        Button ecomy=view.findViewById(R.id.economy);
        Button comodity=view.findViewById(R.id.comodity);
        Button forecast=view.findViewById(R.id.forecast);
        Button currency=view.findViewById(R.id.currency);
        Button mutualfund=view.findViewById(R.id.mutul_fund);
        Button learn=view.findViewById(R.id.learn);

        result.setOnClickListener(view13 -> startActivity(new Intent(getContext(), ResultActivtiy.class)));

        coins.setOnClickListener(view12 -> startActivity(new Intent(getContext(), CoinActivity.class)));

        option.setOnClickListener(view1 -> startActivity(new Intent(getContext(), ResultActivtiyOption.class)));
        learn.setOnClickListener(v -> startActivity(new Intent(getContext(), NewWebActivity.class).putExtra("url","http://www.furthergrow.in/")));



        currency.setOnClickListener(v -> startActivity(new Intent(getContext(), CurrecnyActivty.class)));

        comodity.setOnClickListener(v -> startActivity(new Intent(getContext(), CommodityActivty.class)));
        forecast.setOnClickListener(v -> startActivity(new Intent(getContext(), ForCastActivity.class)));

        ecomy.setOnClickListener(v -> startActivity(new Intent(getContext(), EconomyActivity.class)));

        short_sell.setOnClickListener(v -> startActivity(new Intent(getContext(), ShortSellActivity.class)));

        most_deliver.setOnClickListener(v -> startActivity(new Intent(getContext(), MostDeliveryActivity.class)));

        fil_app.setOnClickListener(v -> startActivity(new Intent(getContext(), FilAndDilActivity.class)));

        block_deal.setOnClickListener(v -> startActivity(new Intent(getContext(), BlockDealActivity.class)));

        bulk_deal.setOnClickListener(v -> startActivity(new Intent(getContext(), BulkDealActivity.class)));


        oi_spurts.setOnClickListener(v -> startActivity(new Intent(getContext(), OiSpurtsActivity.class)));

        price_band.setOnClickListener(v -> startActivity(new Intent(getContext(), PriceBandActivity.class)));

        volume_gainers.setOnClickListener(v -> startActivity(new Intent(getContext(), VolumeGainerActivity.class)));

        high_week.setOnClickListener(v -> startActivity(new Intent(getContext(), HighLowActivit.class).putExtra("data","1")));

        low_week.setOnClickListener(v -> startActivity(new Intent(getContext(), HighLowActivit.class).putExtra("data","0")));

        active_stock.setOnClickListener(v -> startActivity(new Intent(getContext(), ActiveStockActivity.class)));

        premarket_open.setOnClickListener(v -> startActivity(new Intent(getContext(), PreMarketOpen.class)));

        global_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Global News")));


        stock_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Stock News")));

        startup_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Startup News")));


        internation_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"International News")));

        sip_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Sip News")));

        politics_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Political News")));

        sport_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Sport News")));

        comodity_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Commodity News")));

        business_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Business News")));

        currency_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Currency News")));

        earning_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Earning News")));


        economy_news.setOnClickListener(v -> startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Economy News")));


    }
}

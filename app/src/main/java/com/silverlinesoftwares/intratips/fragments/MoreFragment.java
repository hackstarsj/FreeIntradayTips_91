package com.silverlinesoftwares.intratips.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.ActiveStockActivity;
import com.silverlinesoftwares.intratips.activity.BlockDealActivity;
import com.silverlinesoftwares.intratips.activity.BulkDealActivity;
import com.silverlinesoftwares.intratips.activity.CommodityActivty;
import com.silverlinesoftwares.intratips.activity.CurrecnyActivty;
import com.silverlinesoftwares.intratips.activity.EconomyActivity;
import com.silverlinesoftwares.intratips.activity.FilAndDilActivity;
import com.silverlinesoftwares.intratips.activity.ForCastActivity;
import com.silverlinesoftwares.intratips.activity.HighLowActivit;
import com.silverlinesoftwares.intratips.activity.MostDeliveryActivity;
import com.silverlinesoftwares.intratips.activity.MutualFundActivity;
import com.silverlinesoftwares.intratips.activity.NewWebActivity;
import com.silverlinesoftwares.intratips.activity.NewsActivity;
import com.silverlinesoftwares.intratips.activity.OiSpurtsActivity;
import com.silverlinesoftwares.intratips.activity.PreMarketOpen;
import com.silverlinesoftwares.intratips.activity.PriceBandActivity;
import com.silverlinesoftwares.intratips.activity.ResultActivtiy;
import com.silverlinesoftwares.intratips.activity.ShortSellActivity;
import com.silverlinesoftwares.intratips.activity.VolumeGainerActivity;
import com.silverlinesoftwares.intratips.util.Constant;


public class MoreFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        CardView global_news=view.findViewById(R.id.global_news);
        CardView stock_news=view.findViewById(R.id.stock_news);
        CardView startup_news=view.findViewById(R.id.startup_news);
        CardView internation_news=view.findViewById(R.id.internation_news);
        CardView sip_news=view.findViewById(R.id.sip_news);
        CardView politics_news=view.findViewById(R.id.politics_news);
        CardView result=view.findViewById(R.id.result);

        CardView sport_news=view.findViewById(R.id.sport_news);
        CardView comodity_news=view.findViewById(R.id.comodity_news);
        CardView business_news=view.findViewById(R.id.business_news);
        CardView currency_news=view.findViewById(R.id.currency_news);
        CardView earning_news=view.findViewById(R.id.earning_news);
        CardView economy_news=view.findViewById(R.id.economy_news);
        CardView active_stock=view.findViewById(R.id.active_stock);
        CardView premarket_open=view.findViewById(R.id.premarket_open);
        CardView high_week=view.findViewById(R.id.high_week);
        CardView low_week=view.findViewById(R.id.low_week);
        CardView volume_gainers=view.findViewById(R.id.volume_gainers);
        CardView price_band=view.findViewById(R.id.price_band_hitters);
        CardView oi_spurts=view.findViewById(R.id.oi_spurts);
        CardView bulk_deal=view.findViewById(R.id.bulk_deals);
        CardView block_deal=view.findViewById(R.id.block_deals);
        CardView fil_app=view.findViewById(R.id.fidi);
        CardView most_deliver=view.findViewById(R.id.most_delivery);
        CardView short_sell=view.findViewById(R.id.short_selling);
        CardView ecomy=view.findViewById(R.id.economy);
        CardView comodity=view.findViewById(R.id.comodity);
        CardView forecast=view.findViewById(R.id.forecast);
        CardView currency=view.findViewById(R.id.currency);
        CardView mutualfund=view.findViewById(R.id.mutul_fund);
        CardView learn=view.findViewById(R.id.learn);

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ResultActivtiy.class));
            }
        });
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), NewWebActivity.class).putExtra("url","http://www.furthergrow.in/"));
            }
        });

        mutualfund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MutualFundActivity.class));
            }
        });

        currency.setOnClickListener(new
                                            View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    startActivity(new Intent(getContext(), CurrecnyActivty.class));
                                                }
                                            });

        comodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CommodityActivty.class));
            }
        });
        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ForCastActivity.class));
            }
        });

        ecomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EconomyActivity.class));
            }
        });

        short_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ShortSellActivity.class));
            }
        });

        most_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MostDeliveryActivity.class));
            }
        });

        fil_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FilAndDilActivity.class));
            }
        });

        block_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BlockDealActivity.class));
            }
        });

        bulk_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BulkDealActivity.class));
            }
        });


        oi_spurts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OiSpurtsActivity.class));
            }
        });

        price_band.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PriceBandActivity.class));
            }
        });

        volume_gainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), VolumeGainerActivity.class));
            }
        });

        high_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HighLowActivit.class).putExtra("data","1"));
            }
        });

        low_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HighLowActivit.class).putExtra("data","0"));
            }
        });

        active_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActiveStockActivity.class));
            }
        });

        premarket_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PreMarketOpen.class));
            }
        });

        global_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Global News"));
            }
        });


        stock_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Stock News"));
            }
        });

        startup_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Startup News"));
            }
        });


        internation_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"International News"));
            }
        });

        sip_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Sip News"));
            }
        });

        politics_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Political News"));
            }
        });

        sport_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Sport News"));
            }
        });

        comodity_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Commodity News"));
            }
        });

        business_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Business News"));
            }
        });

        currency_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Currency News"));
            }
        });

        earning_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Earning News"));
            }
        });


        economy_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewsActivity.class).putExtra(Constant.data_text,"Economy News"));
            }
        });


    }
}

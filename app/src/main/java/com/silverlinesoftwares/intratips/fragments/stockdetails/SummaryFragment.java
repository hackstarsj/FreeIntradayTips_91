package com.silverlinesoftwares.intratips.fragments.stockdetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.NewWebActivity;
import com.silverlinesoftwares.intratips.listeners.NewsListener;
import com.silverlinesoftwares.intratips.listeners.StockDetailListener;
import com.silverlinesoftwares.intratips.models.NewsModel;
import com.silverlinesoftwares.intratips.models.SummaryModel;
import com.silverlinesoftwares.intratips.tasks.NewsTask;
import com.silverlinesoftwares.intratips.tasks.StockDetailTask;
import com.silverlinesoftwares.intratips.util.Constant;

import java.util.List;


public class SummaryFragment extends Fragment implements StockDetailListener, NewsListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public SummaryFragment() {
        // Required empty public constructor
    }

    public static SummaryFragment newInstance(String param1, String param2) {
        SummaryFragment fragment = new SummaryFragment();
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
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }




    TextView market_cap_summary_value;
    TextView stock_pe_summary_value;
    TextView book_value_summary_value;
    TextView roce_summary_value;
    TextView face_value_summary_value;
    TextView roe_summary_value;
    TextView dividend_yield_summary_value;
    TextView volume_summary_value;
    TextView average_volume_summary_value;
    TextView vwap_summary_value;
    TextView week_52_high_summary_value;
    TextView week_52_low_summary_value;
    TextView upper_circuit_summary_value;
    TextView lower_circuit_value;
    ProgressBar progress;
    LinearLayout new_content;
    ProgressBar progress_news;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null) {
            ShowData(bundle);

        }




         market_cap_summary_value=view.findViewById(R.id.market_cap_summary_value);
         stock_pe_summary_value=view.findViewById(R.id.stock_pe_summary_value);
         book_value_summary_value=view.findViewById(R.id.book_value_summary_value);
         roce_summary_value=view.findViewById(R.id.roce_summary_value);
         face_value_summary_value=view.findViewById(R.id.face_value_summary_value);
         roe_summary_value=view.findViewById(R.id.roe_summary_value);
         dividend_yield_summary_value=view.findViewById(R.id.dividend_yield_summary_value);
         volume_summary_value=view.findViewById(R.id.volume_summary_value);
         average_volume_summary_value=view.findViewById(R.id.average_volume_summary_value);
         vwap_summary_value=view.findViewById(R.id.vwap_summary_value);
         week_52_high_summary_value=view.findViewById(R.id.week_52_high_summary_value);
         week_52_low_summary_value=view.findViewById(R.id.week_52_low_summary_value);
         upper_circuit_summary_value=view.findViewById(R.id.upper_circuit_summary_value);
         lower_circuit_value=view.findViewById(R.id.lower_circuit_value);
        progress=view.findViewById(R.id.progress);
        progress_news=view.findViewById(R.id.progress_news);
        new_content=view.findViewById(R.id.new_conntent);
        showInterestialAds();

    }

    private void ShowData(Bundle bundle) {

        try {
            StockDetailTask stockDetailTask = new StockDetailTask(bundle.getString(Constant.search), SummaryFragment.this);
            stockDetailTask.execute();
            NewsTask newDetails = new NewsTask(SummaryFragment.this, bundle.getString(Constant.search).replace(".NS", "").replace(".BO", ""), 10);
            newDetails.execute();
        }
        catch (Exception e){
            e.printStackTrace();
            ShowData(bundle);
        }

    }

    @Override
    public void onSummayLoaded(SummaryModel data) {
        progress.setVisibility(View.GONE);
        market_cap_summary_value.setText(data.getMarket_cap());
        stock_pe_summary_value.setText(data.getStocPe());
        book_value_summary_value.setText(data.getBook_value());
        roce_summary_value.setText(data.getRoce());
        face_value_summary_value.setText(data.getFaceValue());
        roe_summary_value.setText(data.getRoe());
        dividend_yield_summary_value.setText(data.getDividendYield());
        volume_summary_value.setText(data.getVolume());
        average_volume_summary_value.setText(data.getAverageVolume());
        vwap_summary_value.setText(data.getvWap());
        week_52_high_summary_value.setText(data.getHigh52());
        week_52_low_summary_value.setText(data.getLow52());
        upper_circuit_summary_value.setText(data.getUpperPriceband());
        lower_circuit_value.setText(data.getLowerPriceBand());



    }

    @SuppressLint("InflateParams")
    @Override
    public void onSucess(List<NewsModel> data) {
        progress_news.setVisibility(View.GONE);

        for (final NewsModel newsModel:data){

            View view=null;
            if(getActivity()!=null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.news_rows, null);

                TextView title = view.findViewById(R.id.title);
                TextView category = view.findViewById(R.id.description);
                ImageView image = view.findViewById(R.id.images);
                CardView news_card=view.findViewById(R.id.news_card);
                TextView date = view.findViewById(R.id.dates);
                ImageView thumbnail_video = view.findViewById(R.id.progress);


                title.setText(Html.fromHtml(newsModel.getTitle()));
                date.setText(newsModel.getDates());


                category.setText(Html.fromHtml(newsModel.getDescriptions()));


                thumbnail_video.setVisibility(View.GONE);


                if (newsModel.getImages().contains("data:")) {
                    byte[] decodedString = Base64.decode("/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBwgHBgkIBwgKCgkLDRYPDQwMDRsUFRAWIB0iIiAdHx8kKDQsJCYxJx8fLT0tMTU3Ojo6Iys/RD84QzQ5OjcBCgoKDQwNGg8PGjclHyU3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3N//AABEIAGQAZAMBIgACEQEDEQH/xAAbAAAABwEAAAAAAAAAAAAAAAAAAgMEBQYHAf/EAEEQAAIBAwIDBAYGBwcFAAAAAAECAwAEEQUhBhIxE0FRYRQiI3GRoQeBscHR8BUWMjNCUmIkcqKy0uHxNFNzkpT/xAAYAQADAQEAAAAAAAAAAAAAAAAAAQIDBP/EACARAAICAQUBAQEAAAAAAAAAAAABAhESAyExQVFhIhP/2gAMAwEAAhEDEQA/AJjXbsSSyMCCHkwD/SNh+PxqIkuRa9ncMM9nKhx7iGPyFG1OXtLsorA8mF69/fTS9y0UcZPrEcxGfHYfZQ9kSt2aRMwcB0IKsMg+IpsxqA4Y4ggjsxpeoeqyN/Zpz3r/ACnzFTsoK9dq0g7JaphWNJtXSaIetWIKxotGKkmiEHHTagTOUWunrXMVaEFNcxRq4aACkb0KHtD+ypI91CpsdFSHtJOYjLse/wAaXkSSe8aGNWd9lAAPcN/+abaRKL11dVIAO2PEVYbRIrGBVYRekTjmJkRpQ5/phX1pB7yF8M9a5dSVm8FYTSuH5LqZGbtJirAmO1j7TcdxkJCA+5j9pa3+jsxkN3BJa4wVEk8bE+OQucfGohUvrllaayvp0Ueq2o6iLKED/wAUefmKibuG05iZbfg6Nt/V7XtD8SMmoUmlsW4pkzdyzwSuIrGW4iGMSRSpk/USPyKJBcxz59WWJgcFZUKkfWdjVda2hdibW14fcd3ot4Ym+QrjvfWa9NQt18Jh6XD8R6wFUtWQnpot0lsAnaDm5R17jSMjIIuUZznaofSuIYZLn0FrhXWUAxOGyjdxCn3g+qdxipWRSGO1dMHkrMJqthPGTXSKcQqFB6b91JycpbIGKuyaEQKBFKAUdImZshScUmwonbWVDbp2YGAAOlCmdu0qxgKnKPChWJpRhOk8RvpcbrCAzuxMTSYwoz3jx+VWU8fW8aLiK/tkf956G0XPMfFpGy3dVW0KWygv7X9IWFvd2UrASh85UVrMXBXCV5BHLBo47OQZVlbAx8ajCyk2ioW/GvCi5eTQNRncnJe5kWYn/wBnpb9e+GwCF0cQqO5rCM/ZJVpb6OOFjjFrKmOmJjTK5+jbhdcKZrqPmBAyy4+yjAeRAvxZwrLk3FlD7v0eR9hNJLxDwep5rO/vLCQ99ukuPgQR8qjuK+E7LTtUjtNNAuY2tzPI88vJyAH31CnQouUkR2ZPlfr97VOC7HkyzXOt6FeW/Lca/FIyH2cjWssbAdd8LjOd9sVa7LjLhxrOEXWs2/pAQCRsOAx8d1FZV+hQpy1vb8v9Ooxfe1Hh0ZWlQdjKELAMyXcTYGeuxq08eCWk+TW4uJ+HHI5dass+cwFO4dY0OVgw1SyI8BMu9UQ/R3YuA0GqSsCPVOFbI8qaTfRvvhNUG/QNAPxrX9E1E1FrvTbh/Y3tpjymX8aXTslX1LiM/wBxgax1/o2mCjGo22/88GPnmkX+jm+U5W6s/L2ZBqXl4FR9N3gVOyHtAx7zkUKwscD66NkvYseUrr8qFT+h7EfwZp76tqz2M4k5YR2nIDjB6Hr5ZratMaa006C39Gc9mvLu4J7/AI9B8RWUaPaSWfG1zK9u3ZMXGF22bb/f6q0aGWF3DG2uVBbI22X3j6z49KuKJZMi8d2CtDIm2ckbfH89DSc8iyxskm6nzx8Kj1vYoUClLlRufaqSe/bP1URb+GYkRMSQASMGrSFZEa3c2rWutxNJELoWjCJWYdoQsbHbO9Zql/KFxgg+Q61aL7Sry74lutSity1vHBLG0gI2zC4Hn1NQMsM4KskLtlcnKnrj8ayfLLXAmNWYRPFJHzKylTkeNRQVFGcnm7qlI47trlVe3mEZ2z2Z8Pd40wa1uWZglvMdydkJ2pDNO+jG7STh5oiMyW0zIM9yn1h8yfhVr7RGOXjVgBsGFZp9G8k9tf3lrPDKkc0QYF0IGVOMe/DZ+qr+baUjPMd+76/71aRexmx8TFLGUECKgx6uNjmm3o1pao8rRog2BYjO3QfdSUdhKh51k38CRv76S1C0uLuMwdsYU6sVGMjwz4U7GkOEmtG5sSx+qSCM4xQqnX2j61azCOCdZF5QcsAfLv8AdXajJjGdzPm5D53Iyc1a9NuCbWLfOV7qoE5iWRnuETssDD9pzE/PI3p3oGoXNzforXENtZRErzSS/tD3HfOPcKrNNUKi+PdgZw4z5Go281GKDBkbBc4XHU01tra1jnmnOt20zyjf1go+2uNp0BmMy6hBzlv+6u3l1qo32TaEH4i0qyttSsLq8EVzIeXBiZ8ZXHd5HxqhteWkSqEjjYeAmuBj/H9lT2tcIi+1S5uf01Ywdo4wrk5zygYz07qb/qGvJzNxJZcviEyP81ZuEm7LziRA1C3z+4H/ANE/+ujjUbXH/Tpjv/tE3+qpA8D55guv2bMuc+qO7r/FSJ4FCk9rr2nqN98E9PrpYSDOI64XurAaqJo4faRRMcK8jHGwOxP5OK0uz1O3mhDLJlf4dj0rPNB4ah0q/wC2k1vT5F5WRgh3+3yqzWsmkW+XS/tSCRzYbr3eNaRjSpkuS6LD6XETtJ8jUZxDqN3Hbwx2Dr2ssgRdjkd5+VJnUtIO/p9sBjOzikzq2lLHk6jGAw5c8x2z4UNL0MhGWwCCP0nVJ+0KAn2zL8htQphPJoiytz34yd9wW+dCopjsr7/qjk82qagEY+tEsZOPrznrSEv6shB6HLeSOx6GI+rt54qtusStiSQhs75B/CnOlpE+o2yRuSWlHed9/wDajNdRQYfROG4SJ2ysWckAHBPz3ovbyLOzwydnHkkrsQPLetZ0N7m5tlklwIghQBkALHbfyG1STW8JRgY4wT0PL02/GjDcqzGodQiM0U9wBKVky6DlHNgeHSpT9YLSYNzaVER3DCjb34/CtEurEkEwuEUkHAQZAxuPjillt0ifKEt4Z+dT/H6WpfDOPS1uFkEGl28aSRLypybgjO/TJOe+nenadNqq3t5dyxO+wWCV3UHGOh6A4xtVzvXulj5oXJ5AVZMZJHcR54qncS319cX6tDIsa24Dckr8vN47dN9x9dKWniF2MbrVIJbWWzitIYhjlwI8cu/xzUYLeM4Bk38lNSerRxSRW2qWaIsd0PaIUUlW7+o8c/k0wDyHc488IB91RwMTZTFH7ORHZWxylSNj35pKKQzMYzHH62QDvsf+R86WcuZkXnKhjj3U+/RaoWZ5ZG5WBbB3A8fz4UrC0iEJcMw5QpzuCNwaFaa4u2CPAkkiugYleXY943oVqoE2vDKZBin/AA3vr1kD/OT/AITQoUkI1/Sf3DL3K5x8q7qE7wCIx49eVEOfAsAftrtCt+xBbmSQO4EhABwBgeFGtmZ4AXOTk712hQCCPsWI8jVQ4hhjsZ7rUYEX0ntECsyhghYbsAe/zrtCs9ThDK+bydtCXtG7QvcYcuMk7E9e7fPTxpBxy4O++9ChWLLiJTDmI3OzCpaOaSR3DOf2ADjbPv8AjXKFBLLvwjM76HDznmwBgnf+EH76FChWi4Ef/9k=", Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    image.setImageBitmap(decodedByte);
                } else {
                    if (newsModel.getImages().isEmpty()) {
                        byte[] decodedString = Base64.decode("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", Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        image.setImageBitmap(decodedByte);

                    } else {
                        try {
                            byte[] decodedString = Base64.decode(newsModel.getImages(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            image.setImageBitmap(decodedByte);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }

                news_card.setOnClickListener(v -> {
                    showInterestialAdsView();
                    Intent intent=new Intent(getContext(), NewWebActivity.class);
                    intent.putExtra("url",newsModel.getLinks());
                    startActivity(intent);
                });

                new_content.addView(view);


            }
            else if(getContext()!=null){
                    view = getActivity().getLayoutInflater().inflate(R.layout.news_rows, null);

                    TextView title = view.findViewById(R.id.title);
                    TextView category = view.findViewById(R.id.description);
                    ImageView image = view.findViewById(R.id.images);
                    TextView date = view.findViewById(R.id.dates);
                CardView news_card=view.findViewById(R.id.news_card);

                ImageView thumbnail_video = view.findViewById(R.id.progress);


                    title.setText(Html.fromHtml(newsModel.getTitle()));
                    date.setText(newsModel.getDates());


                    category.setText(Html.fromHtml(newsModel.getDescriptions()));


                    thumbnail_video.setVisibility(View.GONE);


                    if (newsModel.getImages().contains("data:")) {
                        byte[] decodedString = Base64.decode("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", Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        image.setImageBitmap(decodedByte);
                    } else {
                        if (newsModel.getImages().isEmpty()) {
                            byte[] decodedString = Base64.decode("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", Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            image.setImageBitmap(decodedByte);

                        } else {
                            try{
                                byte[] decodedString = Base64.decode(newsModel.getImages(), Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                image.setImageBitmap(decodedByte);

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }

                news_card.setOnClickListener(v -> {
                    showInterestialAdsView();
                    Intent intent=new Intent(getContext(), NewWebActivity.class);
                    intent.putExtra("url",newsModel.getLinks());
                    startActivity(intent);
                });
                    new_content.addView(view);


            }
        }

    }

    private InterstitialAd mInterstitialAd;
    private void showInterestialAdsView() {
        if (mInterstitialAd != null) {
            if(getActivity()!=null) {
                mInterstitialAd.show(getActivity());
            }
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }
    private void showInterestialAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        if(getContext()!=null) {
            InterstitialAd.load(getContext(), getString(R.string.inter_r), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.i("TAG", loadAdError.getMessage());
                            mInterstitialAd = null;
                        }
                    });
        }
    }


    @Override
    public void onFailedNews(String msg) {
        if(progress_news!=null) {
            progress_news.setVisibility(View.GONE);
        }
        if(getContext()!=null) {
            Toast.makeText(getContext(), "Failed to Load News", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailed(String msg) {

        if(progress!=null) {
            progress.setVisibility(View.GONE);
        }
        if(getContext()!=null) {
            Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.silverlinesoftwares.intratips.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.ChartWebActivity;
import com.silverlinesoftwares.intratips.adapters.VideoAdapter;
import com.silverlinesoftwares.intratips.adapters.ViewPagerAdapter;
import com.silverlinesoftwares.intratips.fragments.subfragment.StrongBuyFragment;
import com.silverlinesoftwares.intratips.fragments.subfragment.StrongSellFragment;
import com.silverlinesoftwares.intratips.models.VideoMoel;
import com.silverlinesoftwares.intratips.util.MyAsyncListener;
import com.silverlinesoftwares.intratips.util.RecyclerTouchListener;
import com.silverlinesoftwares.intratips.util.StaticMethods;
import com.silverlinesoftwares.intratips.util.VideoActivity;
import com.silverlinesoftwares.intratips.util.VideoDataLoader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class TrendingFragment extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final double PIC_WIDTH = 1000;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ProgressBar progress;

    public static TrendingFragment newInstance(String param1, String param2) {
        TrendingFragment fragment = new TrendingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_menu);
        viewPager=view.findViewById(R.id.mover_looser_page);


        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager(),getLifecycle());

        StrongBuyFragment topMoversFragment=new StrongBuyFragment();
        StrongSellFragment topLoserFragment=new StrongSellFragment();





        viewPagerAdapter.addFragment(topMoversFragment);
        viewPagerAdapter.addFragment(topLoserFragment);

        viewPagerAdapter.addTitle(getString(R.string.strong_buy));
        viewPagerAdapter.addTitle(getString(R.string.strong_sell));
        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(viewPagerAdapter.getTitle(position));
            }
        }).attach();

    }


}

package com.silverlinesoftwares.intratips.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.ViewPagerAdapter;
import com.silverlinesoftwares.intratips.fragments.subfragment.StrongBuyFragment;
import com.silverlinesoftwares.intratips.fragments.subfragment.StrongSellFragment;


public class TrendingFragment extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final double PIC_WIDTH = 1000;

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
        TabLayout tabLayout = view.findViewById(R.id.tab_menu);
        ViewPager2 viewPager = view.findViewById(R.id.mover_looser_page);


        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager(),getLifecycle());

        StrongBuyFragment topMoversFragment=new StrongBuyFragment();
        StrongSellFragment topLoserFragment=new StrongSellFragment();





        viewPagerAdapter.addFragment(topMoversFragment);
        viewPagerAdapter.addFragment(topLoserFragment);

        viewPagerAdapter.addTitle(getString(R.string.strong_buy));
        viewPagerAdapter.addTitle(getString(R.string.strong_sell));
        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(viewPagerAdapter.getTitle(position))).attach();

    }


}

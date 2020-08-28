package com.silverlinesoftwares.intratips.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.ViewPagerAdapter;
import com.silverlinesoftwares.intratips.subfragment.DeliveryFragment;
import com.silverlinesoftwares.intratips.subfragment.ForexFragment;
import com.silverlinesoftwares.intratips.subfragment.HomeFragment;
import com.silverlinesoftwares.intratips.subfragment.McxFragment;
import com.silverlinesoftwares.intratips.subfragment.OptionFragment;
import com.silverlinesoftwares.intratips.util.BuyButtonClick;

public class HomeTabFragment extends Fragment {

    public HomeTabFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_tab, container, false);
    }

    private ViewPager mViewPager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.addFragment(new HomeFragment());
        viewPagerAdapter.addFragment(new OptionFragment());
        viewPagerAdapter.addFragment(new McxFragment());
        viewPagerAdapter.addFragment(new ForexFragment());
        viewPagerAdapter.addFragment(new DeliveryFragment());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.page);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(viewPagerAdapter);


        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

}

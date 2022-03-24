package com.silverlinesoftwares.intratips.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager(),getLifecycle());
        viewPagerAdapter.addFragment(new HomeFragment());
        viewPagerAdapter.addFragment(new OptionFragment());
        viewPagerAdapter.addFragment(new McxFragment());
        viewPagerAdapter.addFragment(new ForexFragment());
        viewPagerAdapter.addFragment(new DeliveryFragment());

        viewPagerAdapter.addTitle("EQUITY");
        viewPagerAdapter.addTitle("OPTION");
        viewPagerAdapter.addTitle("MCX");
        viewPagerAdapter.addTitle("FOREX");
        viewPagerAdapter.addTitle("DELIVERY");


        // Set up the ViewPager with the sections adapter.
        ViewPager2 mViewPager = (ViewPager2) view.findViewById(R.id.page);
        mViewPager.setUserInputEnabled(true);
        mViewPager.setAdapter(viewPagerAdapter);


        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab);

       // mViewPager.registerOnPageChangeCallback(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
       // tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        new TabLayoutMediator(tabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(viewPagerAdapter.getTitle(position));
            }
        }).attach();

        mViewPager.setOffscreenPageLimit(5);


    }

}

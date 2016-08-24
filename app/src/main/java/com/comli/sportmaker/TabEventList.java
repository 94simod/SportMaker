package com.comli.sportmaker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Casa on 10/08/2016.
 */
public class TabEventList extends Fragment {
        private ViewPager viewPager;
        private TabLayout tabLayout;

        @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View rootView=inflater.inflate(R.layout.fragment_tabeventi, container, false);
            viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
            setupViewPager(viewPager);
            tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

        return rootView;
        }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(TabSport.newInstance("http://sportmaker.altervista.org/php/listCalcio.php"), "Calcio");
        adapter.addFragment(TabSport.newInstance("http://sportmaker.altervista.org/php/listPallavolo.php"), "Pallavolo");
        adapter.addFragment(TabSport.newInstance("http://sportmaker.altervista.org/php/listNuoto.php"), "Nuoto");
        viewPager.setAdapter(adapter);
    }
}
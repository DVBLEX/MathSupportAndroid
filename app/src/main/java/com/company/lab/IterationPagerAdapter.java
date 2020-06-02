package com.company.lab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class IterationPagerAdapter extends FragmentPagerAdapter {

    private List<CalculationFragment> fragmentList;

    public IterationPagerAdapter(FragmentManager fm) {
        super(fm);

        fragmentList = new ArrayList<>();
        fragmentList.add(new Formula5Fragment());
        fragmentList.add(new Formula7Fragment());
        fragmentList.add(new Formula8Fragment());
        fragmentList.add(new Formula9Fragment());
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public void calculate(List<Double> items) {
        for (CalculationFragment fragment : fragmentList) {
            fragment.calculate(items);
        }
    }
}

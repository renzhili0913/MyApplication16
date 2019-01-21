package com.bawei.renzhili.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bawei.renzhili.fragment.CarFragment;
import com.bawei.renzhili.fragment.ClassFragment;
import com.bawei.renzhili.fragment.HomeFragment;
import com.bawei.renzhili.fragment.MyFragment;
import com.bawei.renzhili.fragment.SearchFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String[] meues = new String[]{"首页","分类","觅Me","购物车","我的"};
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new HomeFragment();
            case 1:
                return new ClassFragment();
            case 2:
                return new SearchFragment();
                case 3:
                return new CarFragment();
            case 4:
                return new MyFragment();
             default:
                 return new Fragment();

        }
    }

    @Override
    public int getCount() {
        return meues.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return meues[position];
    }
}

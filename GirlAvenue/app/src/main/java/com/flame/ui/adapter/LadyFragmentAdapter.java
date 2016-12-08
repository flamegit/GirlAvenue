package com.flame.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.flame.datasource.RemoteLadyFetcher;
import com.flame.presenter.GirlPresenter;
import com.flame.ui.GirlListFragment;

/**
 * Created by Administrator on 2016/8/13.
 */
public class LadyFragmentAdapter extends FragmentPagerAdapter {

    String[] titles={"最新","最热","推荐"};
    String[] categories={"","/hot","/best"};
    public LadyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
       // return super.getPageTitle(position);
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }


    @Override
    public Fragment getItem(int position) {
        GirlListFragment fragment= new GirlListFragment();
        new GirlPresenter(fragment,RemoteLadyFetcher.getInstance()).setBaseUrl(categories[position]);
        return fragment;
    }


}

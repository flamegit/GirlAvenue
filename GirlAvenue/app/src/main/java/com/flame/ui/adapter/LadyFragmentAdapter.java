package com.flame.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flame.datasource.Fetcher;
import com.flame.datasource.RemoteLadylFetcher;
import com.flame.presenter.GirlPresenter;
import com.flame.ui.GirlListFragment;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

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
        new GirlPresenter(fragment,RemoteLadylFetcher.getInstance()).setBaseUrl(categories[position]);
        return fragment;
    }


}

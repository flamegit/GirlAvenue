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
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/8/13.
 */
public class LadyFragmentAdapter extends FragmentPagerAdapter {

    public LadyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getCount() {
        return 0;
    }


    @Override
    public Fragment getItem(int position) {
        return null;
    }


}

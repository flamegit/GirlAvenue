package com.flame.girlavenue;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.flame.Adapter.GirlAdapter;
import com.flame.Adapter.GirlListAdapter;
import com.flame.datasource.RemoteGirlFetcher;
import com.flame.model.Response;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 */
public class GirlPageFragment extends BaseFragment {

    GirlAdapter mAdapter;
    public GirlPageFragment(){
    }
    @Override
    public void showProgress() {
    }
    @Override
    public void hideProgress() {
    }

    @Override
    public void fillView(List results) {
        mAdapter.addItems(results);
    }

    @Override
    int getLayout() {
        return R.layout.girl_list;
    }
    @Override
    void initView(View view) {
        final ViewPager viewPager=(ViewPager) view.findViewById(R.id.view_pager);
        mAdapter=new GirlAdapter();
        viewPager.setAdapter(mAdapter);
    }
}

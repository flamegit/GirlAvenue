package com.flame.ui;


import android.support.v4.view.ViewPager;
import android.view.View;


import com.flame.ui.adapter.GirlAdapter;

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

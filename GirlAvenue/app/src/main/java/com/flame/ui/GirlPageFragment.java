package com.flame.ui;



import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;


import com.flame.ui.adapter.GirlAdapter;
import com.flame.ui.adapter.LadyPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 */
public class GirlPageFragment extends BaseFragment {

   // GirlAdapter mAdapter;
    LadyPagerAdapter mAdapter;

    public GirlPageFragment(){
    }

    public static GirlPageFragment Instance(String url){
        GirlPageFragment fragment=new GirlPageFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void showProgress() {
    }
    @Override
    public void hideProgress() {
    }

    @Override
    public void onResume() {
        super.onResume();
        String arg=getArguments().getString("url");
        mPresenter.getLadyImages(arg);
    }

    @Override
    public void fillView(String item) {
        mAdapter.addItem(item);
    }

    @Override
    int getLayout() {
        return R.layout.girl_pager;
    }

    @Override
    void initView(View view) {
        final ViewPager viewPager=(ViewPager) view.findViewById(R.id.view_pager);
        final TextView textView=(TextView)view.findViewById(R.id.index_view);
        mAdapter=new LadyPagerAdapter();
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int num=mAdapter.getCount();
                textView.setText("1/"+num);
            }
        });
        viewPager.setAdapter(mAdapter);
    }
}

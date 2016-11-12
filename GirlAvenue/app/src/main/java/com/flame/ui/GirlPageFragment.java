package com.flame.ui;



import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.flame.ui.adapter.GirlAdapter;
import com.flame.ui.adapter.LadyPagerAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 */
public class GirlPageFragment extends BaseFragment {

    LadyPagerAdapter mAdapter;
    int mIndex;
    String mUrl;

    public GirlPageFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getActivity().getIntent();
        mUrl=intent.getStringExtra("url");
        mIndex=intent.getIntExtra("index",0);
        Log.d("fxlts","index"+mIndex);
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                int p=position+1;
                textView.setText(p + "/"+mAdapter.getCount());
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mAdapter=new LadyPagerAdapter(mUrl);
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int num=mAdapter.getCount();
                textView.setText("1/"+num);

            }
        });
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(mIndex);
    }


}

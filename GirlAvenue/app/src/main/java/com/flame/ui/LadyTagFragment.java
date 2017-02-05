package com.flame.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.flame.Constants;
import com.flame.presenter.GirlPresenter;
import com.flame.ui.adapter.LadyAdapter;
import com.flame.ui.adapter.SpaceItemDecoration;

import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 */
public class LadyTagFragment extends BaseFragment {

    public static LadyTagFragment Instance(String url) {
        LadyTagFragment fragment = new LadyTagFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    int getLayout() {
        return R.layout.girl_preview;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mPresenter=new GirlPresenter(this, getArguments().getString(Constants.URL));
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.view_list);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(6));
        mAdapter=new LadyAdapter<>(getContext(),LadyAdapter.TAG_TYPE);
        recyclerView.setAdapter(mAdapter);
        mPresenter.getLadyTags(getArguments().getString(Constants.URL));
    }
}

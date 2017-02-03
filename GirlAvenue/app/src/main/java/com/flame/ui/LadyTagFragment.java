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
    LadyAdapter mAdapter;
    SwipeRefreshLayout mRefreshLayout;
    public LadyTagFragment(){}

    @Override
    int getLayout() {
        return R.layout.girl_preview;
    }

    public static LadyTagFragment Instance(String url) {
        LadyTagFragment fragment = new LadyTagFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void initView(View view) {
        mPresenter=new GirlPresenter(this, getArguments().getString(Constants.URL));
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.view_list);
        mRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(7));
        mAdapter=new LadyAdapter<>(getContext(),LadyAdapter.TAG_TYPE);
        recyclerView.setAdapter(mAdapter);
        mRefreshLayout.setProgressViewOffset(true, 0, 50);
        mRefreshLayout.setDistanceToTriggerSync(1000);
        mPresenter.getLadyTags(getArguments().getString(Constants.URL));
    }
    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }
    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setEnabled(false);
    }

    @Override
    public void fillView(List results) {
        mAdapter.addItems(results);
    }

    @Override
    public void fillView(String item) {
        mAdapter.addItem(item);
    }

    @Override
    public void onDestroy() {
        super.onStop();
        mPresenter.cancel();
        Log.d("fxlts","ladypreviewfragment destory");
    }
}

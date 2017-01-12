package com.flame.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.flame.presenter.GirlPresenter;
import com.flame.ui.adapter.LadyPreViewAdapter;
import com.flame.ui.adapter.SpaceItemDecoration;
import com.flame.Constants;
import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 */
public class LadyPreViewFragment extends BaseFragment {
    LadyPreViewAdapter mAdapter;
    SwipeRefreshLayout mRefreshLayout;
    public LadyPreViewFragment(){}

    @Override
    int getLayout() {
        return R.layout.girl_preview;
    }

    public static LadyPreViewFragment Instance(String url,String desc) {
        LadyPreViewFragment fragment = new LadyPreViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url);
        bundle.putString(Constants.DEC, desc);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getArguments().getString(Constants.DEC));
    }

    @Override
    void initView(View view) {
        mPresenter=new GirlPresenter(this, "");
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.view_list);
        mRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(3));
        mAdapter=new LadyPreViewAdapter(getContext(),getArguments().getString("url"));
        recyclerView.setAdapter(mAdapter);
        mRefreshLayout.setProgressViewOffset(true, 0, 50);
        mRefreshLayout.setDistanceToTriggerSync(recyclerView.getHeight());
        mPresenter.getLadyImages(getArguments().getString("url"));
    }
    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }
    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
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

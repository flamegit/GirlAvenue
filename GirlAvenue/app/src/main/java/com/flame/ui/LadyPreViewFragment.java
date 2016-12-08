package com.flame.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.flame.ui.adapter.LadyPreViewAdapter;
import com.flame.ui.adapter.SpaceItemDecoration;

import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 *
 *
 */
public class LadyPreViewFragment extends BaseFragment {

    LadyPreViewAdapter mAdapter;

    public LadyPreViewFragment(){
    }
    @Override
    int getLayout() {
        return R.layout.girl_list;
    }

    public static LadyPreViewFragment Instance(String url,String desc) {
        LadyPreViewFragment fragment = new LadyPreViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("desc", desc);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getArguments().getString("desc"));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String arg=getArguments().getString("url");
        mPresenter.getLadyImages(arg);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    void initView(View view) {
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.view_list);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(3));
        mAdapter=new LadyPreViewAdapter(getContext(),getArguments().getString("url"));
        recyclerView.setAdapter(mAdapter);

    }
    @Override
    public void showProgress() {
        //mAdapter.showFooter();
    }
    @Override
    public void hideProgress() {
       // mAdapter.hideFooter();
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

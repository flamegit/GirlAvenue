package com.flame.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.flame.presenter.GirlContract;
import com.flame.ui.adapter.LadyAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/10/8.
 */
public abstract class BaseFragment extends Fragment implements GirlContract.View {

    protected GirlContract.Presenter mPresenter;
    protected View mProgressBar;
    protected LadyAdapter mAdapter;
    public BaseFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void setPresenter(GirlContract.Presenter presenter) {
        mPresenter=presenter;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(getLayout(),container,false);
        initView(view);
        return view;
    }
    protected void initView(View view){
        mProgressBar=view.findViewById(R.id.progress_bar);
    }
    abstract int getLayout();
    public int getOptionMenu(){
        return R.menu.menu_main;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(getOptionMenu(), menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            getActivity().onBackPressed();
        }
        return true;
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
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
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void onDestroy() {
        super.onStop();
        if(mPresenter!=null){
            mPresenter.cancel();
        }
    }
}

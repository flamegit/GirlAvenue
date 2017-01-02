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

import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */
public abstract class BaseFragment extends Fragment implements GirlContract.View {
    protected GirlContract.Presenter mPresenter;
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
    @Override
    public void fillView(String item){}
    @Override
    public void fillView(List items){}
    abstract void initView(View view);
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
    public Context getViewContext() {
        return getContext();
    }
}

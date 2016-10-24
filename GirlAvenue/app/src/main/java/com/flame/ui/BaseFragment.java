package com.flame.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flame.presenter.GirlContract;

/**
 * Created by Administrator on 2016/10/8.
 */
public abstract class BaseFragment extends Fragment implements GirlContract.View {

    protected GirlContract.Presenter mPresenter;
    public BaseFragment(){
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
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    abstract void initView(View view);
    abstract int getLayout();
}

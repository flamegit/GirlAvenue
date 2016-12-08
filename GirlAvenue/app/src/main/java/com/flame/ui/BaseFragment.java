package com.flame.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
       // Picasso picasso= Picasso.with(getContext());
       // picasso.setLoggingEnabled(true);
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

//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//        try {
//            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
}

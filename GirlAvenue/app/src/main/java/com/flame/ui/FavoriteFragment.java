package com.flame.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.flame.Constants;
import com.flame.datasource.LocalGirlFetcher;
import com.flame.model.Lady;
import com.flame.presenter.GirlPresenter;
import com.flame.ui.adapter.LadyAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 *
 *
 */
public class FavoriteFragment extends GirlListFragment{
    @Override
    protected void createPresenter() {
        mPresenter=new GirlPresenter(this, new LocalGirlFetcher(getContext()));
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mAdapter.setDeleteItem(true);
//        getContext().getContentResolver().registerContentObserver(GirlData.GirlInfo.CONTENT_URI,
//                false,new ContentObserver(null){
//                   @Override
//                    public void onChange(boolean selfChange) {
//                        super.onChange(selfChange);
//                        mPresenter.getGirlList();
//                    }
//                });
    }
}

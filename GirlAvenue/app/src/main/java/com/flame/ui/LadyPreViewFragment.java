package com.flame.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.flame.presenter.GirlPresenter;
import com.flame.ui.adapter.LadyAdapter;
import com.flame.ui.adapter.SpaceItemDecoration;


/**
 * Created by Administrator on 2016/10/7.
 */
public class LadyPreViewFragment extends BaseFragment {

    @Override
    int getLayout() {
        return R.layout.girl_preview;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        getActivity().setTitle(LadyViewActivity.sDes);
        mPresenter=new GirlPresenter(this, "");
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.view_list);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(3));
        mAdapter=new LadyAdapter<>(getContext(),LadyAdapter.STRING_TYPE);
        recyclerView.setAdapter(mAdapter);
        mPresenter.getLadyImages(LadyViewActivity.sUrl);
    }
}

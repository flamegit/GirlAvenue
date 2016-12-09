package com.flame.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import com.flame.model.Lady;
import com.flame.ui.adapter.GirlListAdapter;
import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 *
 *
 */
public class GirlListFragment extends BaseFragment implements View.OnClickListener{

    GirlListAdapter mAdapter;
    RecyclerView mRecyclerView;

    View mPreviousView;
    public  GirlListFragment(){
    }
    public static GirlListFragment Instance(String url){
        GirlListFragment fragment=new GirlListFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    int getLayout() {
        return R.layout.girl_list;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter=new GirlListAdapter<Lady>(getContext(),mPresenter.getPage());
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.start();
        Log.d("ListFragment","onload");
    }

    @Override
    void initView(View view) {
        mRecyclerView=(RecyclerView)view.findViewById(R.id.view_list);
        final View bottomView=view.findViewById(R.id.bottom_nav_view);
        View nextView=bottomView.findViewById(R.id.next_view);
        mPreviousView=bottomView.findViewById(R.id.previous_view);
        mPreviousView.setClickable(false);

        mPreviousView.setOnClickListener(this);
        nextView.setOnClickListener(this);

        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)){
                    bottomView.setVisibility(View.VISIBLE);
                }else if(Math.abs(dy)>10){
                    bottomView.setVisibility(View.GONE);
                }
            }
        });
        mRecyclerView.setHasFixedSize(true);
        //bug no show progress
//        mRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mRefreshLayout.setRefreshing(true);
//            }
//        });
    }
    @Override
    public void showProgress() {
        mAdapter.showFooter();
    }
    @Override
    public void hideProgress() {
        mAdapter.hideFooter();
    }

    @Override
    public void fillView(List results) {
        mAdapter.addItems(results);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.next_view){
            mPresenter.getNext();
        }
        if(v.getId()==R.id.previous_view){
            mPresenter.getPrevious();
        }

        if(mPresenter.getPage()>1){
            mPreviousView.setClickable(true);
            mPreviousView.setVisibility(View.VISIBLE);
        }else {
            mPreviousView.setClickable(false);
            mPreviousView.setVisibility(View.INVISIBLE);
        }
    }
}

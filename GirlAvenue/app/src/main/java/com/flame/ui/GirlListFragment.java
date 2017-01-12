package com.flame.ui;

import android.database.ContentObserver;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.flame.database.GirlData;
import com.flame.datasource.LocalGirlFetcher;
import com.flame.presenter.GirlPresenter;
import com.flame.ui.adapter.GirlListAdapter;
import com.flame.Constants;

import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 *
 *
 */
public class GirlListFragment extends BaseFragment implements View.OnClickListener{

    GirlListAdapter mAdapter;
    RecyclerView mRecyclerView;
    TextView mPreviousView;
    public  GirlListFragment(){
    }
    public static GirlListFragment Instance(String baseUrl,int type){
        Bundle bundle=new Bundle();
        bundle.putString(Constants.URL,baseUrl);
        bundle.putInt(Constants.TYPE,type);
        GirlListFragment fragment=new GirlListFragment();
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
    void initView(View view) {
        if(getArguments().getInt(Constants.TYPE)==Constants.FAVORITE){
            mPresenter=new GirlPresenter(this, new LocalGirlFetcher(getContext()));
            getContext().getContentResolver().registerContentObserver(GirlData.GirlInfo.CONTENT_URI,
                false,new ContentObserver(null){
                    @Override
                    public void onChange(boolean selfChange) {
                        super.onChange(selfChange);
                        mPresenter.getGirlList();
                    }
                });
        }else {
            mPresenter=new GirlPresenter(this,getArguments().getString(Constants.URL));
        }
        mRecyclerView=(RecyclerView)view.findViewById(R.id.view_list);
        final View bottomView=view.findViewById(R.id.bottom_nav_view);
        View nextView=bottomView.findViewById(R.id.next_view);
        mPreviousView=(TextView)bottomView.findViewById(R.id.previous_view);
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
        mAdapter=new GirlListAdapter<>(getActivity(),mPresenter.getPage());
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getGirlList();
        Log.d("ListFragment","onCreateView");
    }

    @Override
    public void showProgress() {
        mAdapter.clearItems();
        mAdapter.showFooter();
    }
    @Override
    public void hideProgress() {
        mRecyclerView.scrollToPosition(0);
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
            mPreviousView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else {
            mPreviousView.setClickable(false);
            mPreviousView.setTextColor(getResources().getColor(R.color.gray));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

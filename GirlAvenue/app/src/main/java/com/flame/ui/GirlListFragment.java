package com.flame.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.flame.presenter.GirlPresenter;
import com.flame.Constants;
import com.flame.ui.adapter.LadyAdapter;

/**
 * Created by Administrator on 2016/10/7.
 *
 *
 */
public class GirlListFragment extends BaseFragment implements View.OnClickListener{
    RecyclerView mRecyclerView;
    TextView mPreviousView;

    public static GirlListFragment Instance(String baseUrl){
        Bundle bundle=new Bundle();
        bundle.putString(Constants.URL,baseUrl);
        GirlListFragment fragment=new GirlListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    int getLayout() {
        return R.layout.girl_list;
    }

    protected void createPresenter(){
        mPresenter=new GirlPresenter(this,getArguments().getString(Constants.URL));
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        createPresenter();
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
                if(!recyclerView.canScrollVertically(1) && mPresenter.getPageNum()>10){
                    bottomView.setVisibility(View.VISIBLE);
                }else if(Math.abs(dy)>10){
                    bottomView.setVisibility(View.GONE);
                }
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mAdapter=new LadyAdapter<>(getActivity(),LadyAdapter.LADY_TYPE);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getGirlList();
        Log.d("ListFragment","onCreateView");
    }

    @Override
    public void showProgress() {
        mAdapter.clearItems();
        mProgressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideProgress() {
        mRecyclerView.scrollToPosition(0);
        mProgressBar.setVisibility(View.GONE);
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
}

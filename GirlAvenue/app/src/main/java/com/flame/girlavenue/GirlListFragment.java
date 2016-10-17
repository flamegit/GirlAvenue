package com.flame.girlavenue;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import com.flame.Adapter.GirlListAdapter;
import com.flame.model.Lady;
import com.flame.model.Response;


import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 *
 *
 */
public class GirlListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    GirlListAdapter mAdapter;
    SwipeRefreshLayout mRefreshLayout;
    public  GirlListFragment(){
    }
    @Override
    int getLayout() {
        return R.layout.girl_list;
    }
    @Override
    void initView(View view) {
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.view_list);
        mRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mRefreshLayout.setOnRefreshListener(this);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)){
                    mPresenter.refresh();
                }
            }
        });
        recyclerView.setHasFixedSize(true);
        mAdapter=new GirlListAdapter<Lady>(getContext());
        recyclerView.setAdapter(mAdapter);
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }
    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
        mAdapter.showFooter();
    }
    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
        //mAdapter.hideFooter();
    }

    @Override
    public void fillView(List results) {
        mAdapter.addItems(results);
    }

    @Override
    public void onRefresh() {
       // mPresenter.refresh();

    }
}

package com.flame.presenter;
import com.flame.datasource.Fetcher;
import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */

public class GirlPresenter implements GirlContract.Presenter,Fetcher.Callback {
    GirlContract.View mView;
    volatile boolean isLoading;
    Fetcher mFetcher;
    public GirlPresenter(GirlContract.View view,Fetcher fetcher){
        mView=view;
        mView.setPresenter(this);
    }

    @Override
    public void onLoad(List results) {
        mView.fillView(results);
        mView.hideProgress();
        isLoading=false;
    }

    @Override
    public void onError() {
        mView.hideProgress();
        isLoading=false;
    }

    @Override
    public void getGirlList() {
        if(isLoading){
            return;
        }
        mView.showProgress();
        isLoading=true;
        mFetcher.loadData(this);
    }

    @Override
    public void getNext(){
        if(isLoading){
            return;
        }
        isLoading=true;
        mFetcher.loadNextPage(this);
    }

    @Override
    public void start() {
        getGirlList();
    }
}

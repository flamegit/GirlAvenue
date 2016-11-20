package com.flame.presenter;
import com.flame.datasource.Fetcher;
import com.flame.model.Lady;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */

public class GirlPresenter implements GirlContract.Presenter,Fetcher.Callback {
    GirlContract.View mView;
    volatile boolean isLoading;
    Fetcher mFetcher;
    private int mPage;

    private static final String ENDURL="http://www.mzitu.com/";
    private String path="page/";
    private String baseUrl;

    public GirlPresenter(GirlContract.View view,Fetcher fetcher){
        mView=view;
        mFetcher=fetcher;
        mView.setPresenter(this);
        mPage=1;
        baseUrl=ENDURL;
    }

    @Override
    public void onLoad(List results) {
        mView.fillView(results);
        mView.hideProgress();
        isLoading=false;
    }

    public int getPage(){
        return mPage;
    }

    private String getCurrentPageUrl(){
        if(mPage==1){
            return baseUrl;
        }
        return baseUrl+path+mPage;
    }

    @Override
    public void onLoad(String item) {
        mView.fillView(item);
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
        mFetcher.loadData(getCurrentPageUrl(),this);
    }

    @Override
    public void getLadyImages(String url) {
        mView.showProgress();
        mFetcher.loadPagerData(url,this);
    }

    @Override
    public void getNext(){
        mPage++;
        getGirlList();
    }

    @Override
    public void getPrevious(){
        mPage--;
        getGirlList();
    }

    @Override
    public void cancel() {
        mFetcher.cancel();
    }

    @Override
    public void start() {
        getGirlList();
    }
}

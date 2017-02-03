package com.flame.presenter;

import android.util.Log;
import android.widget.Toast;

import com.flame.datasource.Fetcher;
import com.flame.datasource.RemoteLadyFetcher;
import com.flame.utils.NetWorkUtils;

import java.util.List;

import static com.flame.Constants.ENDURL;
import static com.flame.Constants.PATH;

/**
 * Created by Administrator on 2016/10/9.
 */

public class GirlPresenter implements GirlContract.Presenter,Fetcher.Callback {
    GirlContract.View mView;
    volatile boolean isLoading;
    Fetcher mFetcher;
    private int mPage;
    private String mUrl;

    public GirlPresenter(GirlContract.View view,String url){
        mView=view;
        mFetcher= new RemoteLadyFetcher(mView.getViewContext());
        mPage=1;
        mUrl=url;
    }
    public GirlPresenter(GirlContract.View view,Fetcher fetcher){
        mView=view;
        mFetcher=fetcher;
        mPage=1;
        mUrl=ENDURL;
    }

    @Override
    public void onLoad(List results) {
        if(results!=null&& results.size()>0){
            mView.fillView(results);
            isLoading=false;
        }else {
            Toast.makeText(mView.getViewContext(), "No Content", Toast.LENGTH_SHORT).show();
        }
        mView.hideProgress();

    }

    public int getPage(){
        return mPage;
    }

    private String getCurrentPageUrl(){
        if(mPage==1){
            return mUrl;
        }
        return mUrl+PATH+mPage;
    }

    @Override
    public void onLoad(String item)
    {
        mView.hideProgress();
        mView.fillView(item);
        isLoading=false;
    }

    @Override
    public void onError() {
        mView.hideProgress();
        isLoading=false;
    }

    private boolean checkNetWork(){
        if(!NetWorkUtils.isNetworkConnected(mView.getViewContext())){
            Toast.makeText(mView.getViewContext(), "NetWork not available", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void getGirlList() {
        if(isLoading){
            return;
        }
        if(!checkNetWork()){
            return;
        }
        mView.showProgress();
        isLoading=true;
        mFetcher.loadData(getCurrentPageUrl(),this);
    }
    @Override
    public void getLadyImages(String url) {
        if(!checkNetWork()){
            return;
        }
        mView.showProgress();
        mFetcher.loadPagerData(url,this);
    }

    @Override
    public void getLadyTags(String url) {
        Log.d("fxlts","loadtag");
        if(!checkNetWork()){
            return;
        }
        mView.showProgress();
        mFetcher.loadTags(url,this);
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
    public int getPageNum() {
        return mFetcher.getPageNum();
    }

    @Override
    public void cancel() {
        mFetcher.cancel();
    }

}

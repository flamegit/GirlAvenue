package com.flame.Presenter;

import android.support.design.widget.Snackbar;

import com.flame.datasource.HtmlParse;
import com.flame.datasource.RemoteGirlFetcher;
import com.flame.model.Lady;
import com.flame.model.Response;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/9.
 */

public class GirlPresenter implements GirlContract.Presenter,RemoteGirlFetcher.CallBack {

    GirlContract.View mView;
    boolean isLoading;
    public GirlPresenter(GirlContract.View view){
        mView=view;
        mView.setPresenter(this);
    }

    @Override
    public void onLoad(List<Response.Girl> results) {
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
        isLoading=true;
        RemoteGirlFetcher.getInstance().getGirlList(this);
    }

    public void getLadyList(){
        Observable.create(new Observable.OnSubscribe<List<Lady>>() {
            @Override
            public void call(Subscriber<? super List<Lady>> subscriber) {
                List<Lady> ladys=HtmlParse.getHtmlContent("http://www.mzitu.com/");
                subscriber.onNext(ladys);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Lady>>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(List<Lady> ladies) {
                        mView.fillView(ladies);
                    }
                });
    }

    @Override
    public void refresh(){
        if(isLoading){
            return;
        }
        mView.showProgress();
        isLoading=true;
        int page= RemoteGirlFetcher.getInstance().getNextPage(this);

    }

    @Override
    public void start() {
       // getGirlList();
        getLadyList();
    }
}

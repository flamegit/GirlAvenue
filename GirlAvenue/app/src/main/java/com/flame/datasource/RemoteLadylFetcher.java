package com.flame.datasource;

import android.util.Log;

import com.flame.model.Girl;
import com.flame.model.Lady;
import com.flame.model.Response;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/3.
 */
public class RemoteLadylFetcher extends Fetcher {

    private RemoteLadylFetcher(){
    }

    public static RemoteLadylFetcher getInstance(){
        return new RemoteLadylFetcher();
    }

    @Override
    public void loadData(Callback callback) {
        Observable.create(new Observable.OnSubscribe<List<Lady>>() {
            @Override
            public void call(Subscriber<? super List<Lady>> subscriber) {
                List<Lady> ladys = HtmlParse.getHtmlContent("http://www.mzitu.com/");
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
                    }
                });
    }
}


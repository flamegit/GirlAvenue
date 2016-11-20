package com.flame.datasource;

import android.util.Log;

import com.flame.model.Girl;
import com.flame.model.Lady;
import com.flame.model.Response;

import java.util.ArrayList;
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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/3.
 */
public class RemoteLadylFetcher extends Fetcher {
    private static volatile Fetcher mInstance;

    Callback mCallback;
    List<Lady> mLadies;
    Subscription mCover;
    Subscription mList;
    public static Fetcher getInstance(){
        if(mInstance==null) {
            synchronized (RemoteGirlFetcher.class){
                if(mInstance==null){
                   mInstance=new RemoteLadylFetcher();
                }
            }
        }
        return mInstance;
    }

    private RemoteLadylFetcher(){
    }

    public void setCallback(Callback callback){
        mCallback=callback;
    }

    public Lady getLady(String url){
        Lady tmp=new Lady();
        tmp.mUrl=url;
        int index=mLadies.indexOf(tmp);
        return mLadies.get(index);
    }

    @Override
    public void loadData(final String url,final Callback callback) {
        mCover= Observable.create(new Observable.OnSubscribe<List<Lady>>() {
            @Override
            public void call(Subscriber<? super List<Lady>> subscriber) {
                List<Lady> ladys = HtmlParse.getLadyCover(url);
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
                        mLadies=ladies;
                        callback.onLoad(ladies);
                    }
                });

    }

    public void cancel(){
        mList.unsubscribe();
    }


    @Override
    public void loadPagerData(final String url, final Callback callback) {
        final Lady lady=getLady(url);
        mList= Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                int num=HtmlParse.getLadyNum(url);
                for(int i=1;i<=num;i++){
                    subscriber.onNext( HtmlParse.getLadyImage(url,i));
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(String src) {
                        callback.onLoad(src);
                        if(lady.mList==null){
                            lady.mList=new ArrayList<String>();
                        }
                        lady.mList.add(src);
                        if( mCallback!=null){
                            mCallback.onLoad(src);
                        }
                    }
                });
        }
}


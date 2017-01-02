package com.flame.datasource;

import android.content.Context;
import android.util.Log;

import com.flame.database.GirlDAO;
import com.flame.model.Lady;
import com.flame.utils.CacheManager;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/3.
 */

public class RemoteLadyFetcher extends Fetcher {
    private static volatile Fetcher mInstance;
    Callback mCallback;
    Subscription mCover;
    Subscription mList;
    CacheManager mCaches;
    Context mContext;

    public static Fetcher getInstance(Context context){
        if(mInstance==null) {
            synchronized (RemoteGirlFetcher.class){
                if(mInstance==null){
                   mInstance=new RemoteLadyFetcher(context);
                }
            }
        }
        return mInstance;
    }

    private RemoteLadyFetcher(Context context){
        mCaches=new CacheManager();
        mContext=context;
    }
    public void setCallback(Callback callback){
        mCallback=callback;
    }

    public Lady getLady(String url){
        Lady lady=mCaches.get(url);
        if(lady==null){
            lady=new Lady();
            mCaches.save(url,lady);
        }
        return lady;
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
                .doOnNext(new Action1<List<Lady>>() {
                    @Override
                    public void call(List<Lady> ladies) {
                        GirlDAO girlDao=GirlDAO.getInstance(mContext);
                        List<Lady> favLadies=girlDao.query();
                        for(Lady lady:ladies){
                            if(favLadies.contains(lady)){
                                lady.isFavorite=true;
                            }
                        }
                    }
                })
                .subscribe(new Observer<List<Lady>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(List<Lady> ladies) {
                        callback.onLoad(ladies);
                    }
                });

    }

    public void cancel(){
        mList.unsubscribe();
    }

    private boolean loadFromCache(String url,Callback callback){
        if(mCaches.isCached(url)){
            Lady lady=getLady(url);
            callback.onLoad(lady.mList);
            return  true;
        }
        return false;
    }


    @Override
    public void loadPagerData(final String url, final Callback callback) {
        if(loadFromCache(url,callback)){
            return;
        }
        final Lady lady=getLady(url);
        // avoid duplicate
        if(lady.mList!=null && lady.mList.size()>0){
            lady.mList.clear();
        }
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
                        mCaches.loadComplete(url);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("fxlts",e.toString());
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


package com.flame.datasource;

import android.content.Context;
import android.util.Log;

import com.flame.database.GirlDAO;
import com.flame.model.Lady;
import com.flame.model.Tag;

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
    private Subscription mCover;
    private Subscription mList;
    private CacheManager mCaches;
    private Context mContext;

    public RemoteLadyFetcher(Context context){
        mCaches=CacheManager.getInstance();
        mContext=context;
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
                        Log.d("fxlts",e.toString());
                    }
                    @Override
                    public void onNext(List<Lady> ladies) {
                        callback.onLoad(ladies);
                    }
                });

    }

    public void cancel(){
        if(mList!=null){
            mList.unsubscribe();
        }

    }

    //TODO 获取页面数量。
    @Override
    public int getPageNum() {
        return 30;
    }

    @Override
    public void loadPagerData(final String url, final Callback callback) {
        if(mCaches.loadFromCache(url,callback)){
            return;
        }
        final Lady lady=mCaches.getLady(url);
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
                        mCaches.setCallback(null);
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

                        if(mCaches.getCallback()!=null){
                            mCaches.getCallback().onLoad(src);
                        }
                    }
                });
        }

    public void loadTags(final String url,final Callback callback){
         Observable.create(new Observable.OnSubscribe<List<Tag>>() {
            @Override
            public void call(Subscriber<? super List<Tag>> subscriber) {
                List<Tag> tags = HtmlParse.getLadyTags(url);
                subscriber.onNext(tags);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Tag>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("fxlts",e.toString());
                    }
                    @Override
                    public void onNext(List<Tag> tags) {
                        callback.onLoad(tags);
                    }
                });
    }
}


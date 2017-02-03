package com.flame.datasource;

import android.content.Context;
import android.database.ContentObserver;

import com.flame.database.GirlDAO;
import com.flame.database.GirlData;
import com.flame.model.Lady;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/13.
 */

public class LocalGirlFetcher extends Fetcher {
    Subscription mCover;
    Context mContext;
    public LocalGirlFetcher(Context context){
        mContext=context;

    }

    @Override
    public void loadData(final String url, final Callback callback) {
        mCover= Observable.create(new Observable.OnSubscribe<List<Lady>>() {
            @Override
            public void call(Subscriber<? super List<Lady>> subscriber) {
//                 int page=0;
//                int end=url.lastIndexOf("/");
//                if(end!=-1){
//                    page=Integer.valueOf(url.substring(end+1));
//               }
                List<Lady> ladys = GirlDAO.getInstance(mContext).query(0);
                subscriber.onNext(ladys);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<List<Lady>>() {
                    @Override
                    public void call(List<Lady> ladies) {
                        for(Lady lady:ladies){
                            lady.isFavorite=true;
                        }
                    }
                })
                .subscribe(new Observer<List<Lady>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        callback.onError();
                    }
                    @Override
                    public void onNext(List<Lady> ladies) {
                        callback.onLoad(ladies);
                    }
                });

    }

    @Override
    public int getPageNum() {
        return GirlDAO.getInstance(mContext).count();
    }
}

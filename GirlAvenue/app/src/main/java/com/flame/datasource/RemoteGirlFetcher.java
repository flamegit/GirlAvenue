package com.flame.datasource;

import android.util.Log;

import com.flame.model.Girl;
import com.flame.model.Response;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/3.
 */
public class RemoteGirlFetcher extends Fetcher{

    interface NetApi {
        @GET("data/福利/10/{page}")
        Observable<Response> getGirlList(@Path("page")int page);
    }
    private  Retrofit mRetrofit;
    private RemoteGirlFetcher(){
       mRetrofit= new  Retrofit.Builder().baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mPage=1;
    }

    public static RemoteGirlFetcher getInstance(){
        return new RemoteGirlFetcher();
    }

    public void loadData(final Fetcher.Callback callBack){
      mRetrofit.create(NetApi.class).getGirlList(mPage)
              .map(new Func1<Response, List<Girl>>() {
                  @Override
                  public List<Girl> call(Response response) {
                     return response.getResults();
                  }
              })
              .delay(30, TimeUnit.SECONDS)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Observer<List<Girl>>() {
                  @Override
                  public void onCompleted() {
                  }
                  @Override
                  public void onError(Throwable e) {
                      Log.d("fxlts",e.toString());
                      callBack.onError();
                  }
                  @Override
                  public void onNext(List<Girl> results) {
                       callBack.onLoad(results);
                  }
              });
    }
}

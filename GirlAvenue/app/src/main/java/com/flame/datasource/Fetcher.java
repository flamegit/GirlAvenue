package com.flame.datasource;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public  class Fetcher {

    public interface Callback{
        void onLoad(String item);
        void onLoad(List results);
        void onError();
    }

    public  void  loadData(String url,Callback callback){

    }
    public  void  loadData(Context context, Callback callback){

    }

    public  void  loadPagerData(String url,Callback callback){
    }

    public void loadTags(String url,Callback callback){

    }

    public int  getPageNum(){
        return 10;
    }

    public void cancel(){

    }

}

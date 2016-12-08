package com.flame.datasource;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public abstract class Fetcher {

    public interface Callback{
        void onLoad(String item);
        void onLoad(List results);
        void onError();
    }

    public abstract void  loadData(String url,Callback callback);

    public  void  loadPagerData(String url,Callback callback){
    }

    public void cancel(){

    }

}

package com.flame.datasource;

import android.support.v4.widget.ViewDragHelper;

import com.flame.model.Girl;
import com.flame.model.Lady;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public abstract class Fetcher {

    protected int mPage;

    public interface Callback{
        void onLoad(String item);
        void onLoad(List results);
        void onError();
    }

    public int loadNextPage(Callback callback){
        mPage++;
        loadData(callback);
        return mPage;
    }

    public abstract void  loadData(Callback callback);

    public  void  loadPagerData(String url,Callback callback){
    }

    public void cancel(){

    }

}

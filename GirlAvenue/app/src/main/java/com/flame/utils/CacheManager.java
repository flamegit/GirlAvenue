package com.flame.utils;

import com.flame.datasource.Fetcher;
import com.flame.model.Lady;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AVAZUHOLDING on 2016/12/7.
 */

public class CacheManager {

    private static volatile CacheManager mInstance;
    private LinkedHashMap<String,Lady> mCache;
    private List<String> mUrls;

    private Fetcher.Callback mCallback;

    public static CacheManager getInstance() {
        if (mInstance == null) {
            synchronized (CacheManager.class) {
                if (mInstance == null) {
                    mInstance = new CacheManager();
                }
            }
        }
        return mInstance;
    }

    private CacheManager(){
        mCache= new LinkedHashMap<>();
        mUrls=new ArrayList<>();
    }

    public void setCallback(Fetcher.Callback callback){
        mCallback=callback;
    }

    public Fetcher.Callback getCallback(){
        return mCallback;
    }



    public void save(String key, Lady value){
        mCache.put(key,value);
        if(mCache.size()>5){
           String  first=mCache.keySet().iterator().next();
            mCache.remove(first);
            mUrls.remove(first);
        }
    }

    public Lady getLady(String url){
        Lady lady=mCache.get(url);
        if(lady==null){
            lady=new Lady();
            save(url,lady);
        }
        return lady;
    }

    public boolean loadFromCache(String url,Fetcher.Callback callback){
        if(isCached(url)){
            Lady lady=getLady(url);
            callback.onLoad(lady.mList);
            return  true;
        }
        return false;
    }

    public void loadComplete(String url){
        mUrls.add(url);
    }

    private boolean isCached(String url){
        return mUrls.contains(url);
    }
}

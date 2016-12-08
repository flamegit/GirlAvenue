package com.flame.utils;

import com.flame.model.Lady;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AVAZUHOLDING on 2016/12/7.
 */

public class CacheManager {

    private LinkedHashMap<String,Lady> mCache;
    private List<String> mUrls;

    public CacheManager(){
        mCache= new LinkedHashMap<String,Lady>();
        mUrls=new ArrayList<String>();
    }

    public void save(String key, Lady value){
        mCache.put(key,value);
        if(mCache.size()>5){
           String  first=mCache.keySet().iterator().next();
            mCache.remove(first);
            mUrls.remove(first);
        }
    }

    public Lady get(String key){
        return mCache.get(key);
    }

    public void loadComplete(String url){
        mUrls.add(url);
    }

    public boolean isCached(String url){
        return mUrls.contains(url);
    }
}

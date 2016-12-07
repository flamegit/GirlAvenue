package com.flame.utils;

import com.flame.model.Lady;
import com.flame.ui.adapter.SpaceItemDecoration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by AVAZUHOLDING on 2016/12/7.
 */

public class CacheManager {

    private LinkedHashMap<String,Lady> mCache;

    public CacheManager(){
        mCache= new LinkedHashMap<String,Lady>();
    }

    public void save(String key, Lady value){
        mCache.put(key,value);
        if(mCache.size()>5){
           String  first=mCache.keySet().iterator().next();
            mCache.remove(first);
        }
    }

    public Lady get(String key){
        return mCache.get(key);
    }
}

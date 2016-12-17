package com.flame.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.flame.datasource.Fetcher;
import com.flame.datasource.RemoteGirlFetcher;
import com.flame.datasource.RemoteLadyFetcher;
import com.flame.model.Lady;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AVAZUHOLDING on 2016/12/13.
 */

public class GirlDAO {

    private Context mContext;
    private static volatile GirlDAO mInstance;

    private GirlDAO( Context context){
        mContext=context;
    }
    public static GirlDAO getInstance(Context context){
        if(mInstance==null) {
            synchronized (GirlDAO.class){
                if(mInstance==null){
                    mInstance=new GirlDAO(context);
                }
            }
        }
        return mInstance;
    }

    private static final String[] GIRL_PROJECTION = new String[] {
            GirlData.GirlInfo._ID,             // Projection position 0, the note's id
            GirlData.GirlInfo.COLUMN_DES,  // Projection position 1, the note's content
            GirlData.GirlInfo.COLUMN_COVER_URL,// Projection position 2, the note's title
            GirlData.GirlInfo.COLUMN_DETAIL_URL
    };
    private static final int GIRL_DES_INDEX = 1;
    private static final int GIRL_COVER_URL_INDEX = 2;
    private static final int GIRL_DETAIL_URL_INDEX = 2;


    public void insert(Lady lady){
        ContentValues values=new ContentValues();
        values.put(GirlData.GirlInfo.COLUMN_DES,lady.mDes);
        values.put(GirlData.GirlInfo.COLUMN_COVER_URL,lady.mThumbUrl);
        values.put(GirlData.GirlInfo.COLUMN_DETAIL_URL,lady.mUrl);
        mContext.getContentResolver().insert(GirlData.GirlInfo.CONTENT_URI,values);
    }

    public void delete(Lady lady){
        mContext.getContentResolver().delete(GirlData.GirlInfo.CONTENT_URI,GirlData.GirlInfo.COLUMN_COVER_URL+"=?",new String[]{lady.mThumbUrl});
    }

    public List<Lady> query(){
        List<Lady> list=new ArrayList<>();
        Cursor c= mContext.getContentResolver().query(GirlData.GirlInfo.CONTENT_URI,GIRL_PROJECTION,null,null,null);
        while (c.moveToNext()){
            Lady tmp=new Lady();
            tmp.mDes=c.getString(GIRL_DES_INDEX);
            tmp.mThumbUrl=c.getString(GIRL_COVER_URL_INDEX);
            tmp.mUrl=c.getString(GIRL_DETAIL_URL_INDEX);
            list.add(tmp);
        }
        return list;
    }
}

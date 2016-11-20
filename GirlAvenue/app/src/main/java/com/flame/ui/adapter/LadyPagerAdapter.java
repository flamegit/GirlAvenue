package com.flame.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flame.datasource.Fetcher;
import com.flame.datasource.RemoteGirlFetcher;
import com.flame.datasource.RemoteLadylFetcher;
import com.flame.model.Girl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/8/13.
 */
public class LadyPagerAdapter extends android.support.v4.view.PagerAdapter {

    List<String> mResults;
    List mCacheView;
    ImageView mCurrItem;

    public LadyPagerAdapter(String url){
        RemoteLadylFetcher fetcher=(RemoteLadylFetcher)RemoteLadylFetcher.getInstance();
        mResults= fetcher.getLady(url).mList;
        fetcher.setCallback(new Fetcher.Callback() {
            @Override
            public void onLoad(String item) {
                notifyDataSetChanged();
                Log.d("fxlts","callback");
            }
            @Override
            public void onLoad(List results) {
            }
            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrItem=(ImageView)object;
    }

    public ImageView getPrimaryItem(){
        return mCurrItem;
    }

    public void addItem(String item){
        mResults.add(item);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        mCacheView.add(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView;
        if(mCacheView==null){
            mCacheView=new LinkedList<>();
        }
        if(mCacheView.size()>0){
            photoView= (PhotoView) mCacheView.remove(0);
        }else {
            photoView=new PhotoView(container.getContext());
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        Picasso.with(container.getContext())
                .load(mResults.get(position))
                .placeholder(new ColorDrawable(Color.GRAY))
                .error(new ColorDrawable(Color.BLUE))
                .into(photoView);
        container.addView(photoView);
        return photoView;
    }
}

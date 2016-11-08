package com.flame.ui.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flame.model.Girl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/13.
 */
public class LadyPagerAdapter extends android.support.v4.view.PagerAdapter {

    List<String> mResults;
    List mCacheView;

    public LadyPagerAdapter(){
        mResults=new ArrayList<>(10);
    }

    public void addItem( String item){
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
        ImageView imageView;
        if(mCacheView==null){
            mCacheView=new LinkedList<>();
        }
        if(mCacheView.size()>0){
            imageView= (ImageView)mCacheView.remove(0);
        }else {
            imageView=new ImageView(container.getContext());
        }
        Picasso.with(container.getContext()).load(mResults.get(position))
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }
}
package com.flame.Adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flame.model.Response;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/13.
 */
public class GirlAdapter extends android.support.v4.view.PagerAdapter {

    List<Response.Girl> mResults;
    List mCacheView;

    public GirlAdapter(){
        mResults=new ArrayList<>(10);
       //
    }

    public void addItems( List<Response.Girl> items){
       // if(mResults.size()>=20)
        Log.d("Girl",items.size()+"");
        mResults.addAll(items);

        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mResults.size();
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

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
        Picasso.with(container.getContext()).load(mResults.get(position).url)
                .into(imageView);

        container.addView(imageView);
        return imageView;

    }
}

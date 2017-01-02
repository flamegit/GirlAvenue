package com.flame.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.flame.datasource.Fetcher;
import com.flame.datasource.RemoteLadyFetcher;
import com.squareup.picasso.Picasso;
import java.util.LinkedList;
import java.util.List;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by Administrator on 2016/8/13.
 */
public class LadyPagerAdapter extends android.support.v4.view.PagerAdapter {

    List<String> mResults;
    List mCacheView;
    ImageView mCurrItem;
    Context mContext;
    PhotoViewAttacher.OnViewTapListener mListener;

    public LadyPagerAdapter(Context context){
        mContext=context;
    }
    public LadyPagerAdapter(String url){
        RemoteLadyFetcher fetcher=(RemoteLadyFetcher)RemoteLadyFetcher.getInstance(mContext);
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
    public void setTapListener(PhotoViewAttacher.OnViewTapListener listener){
        mListener=listener;
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
        PhotoView photoView=(PhotoView)object;
        container.removeView(photoView);
        //photoView.setOnViewTapListener(mListener); //test
       // mCacheView.add(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView;
//        if(mCacheView==null){
//            mCacheView=new LinkedList<>();
//        }
//        if(mCacheView.size()>0){
//            photoView= (PhotoView) mCacheView.remove(0);
//        }else {
            photoView=new PhotoView(container.getContext());
            //TODO　bug
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            photoView.setOnViewTapListener(mListener);
           // photoView.callOnClick()
//        }

        Picasso.with(container.getContext())
                .load(mResults.get(position))
                .placeholder(new ColorDrawable(Color.GRAY))
                .memoryPolicy(NO_CACHE, NO_STORE)
                .error(new ColorDrawable(Color.BLUE))
                .into(photoView);
        container.addView(photoView);
        return photoView;
    }
}

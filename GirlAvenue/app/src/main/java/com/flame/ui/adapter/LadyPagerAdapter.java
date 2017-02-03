package com.flame.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.flame.datasource.CacheManager;
import com.flame.ui.LadyViewActivity;
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
    PhotoView mCurrItem;
    PhotoViewAttacher.OnViewTapListener mListener;



    public LadyPagerAdapter(){
        mResults= CacheManager.getInstance().getLady(LadyViewActivity.sUrl).mList;
    }

    public void setTapListener(PhotoViewAttacher.OnViewTapListener listener){
        mListener=listener;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrItem=(PhotoView) object;
        mCurrItem.setOnViewTapListener(mListener);
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
                .memoryPolicy(NO_CACHE, NO_STORE)
                .error(new ColorDrawable(Color.BLUE))
                .into(photoView);
        container.addView(photoView);
        return photoView;
    }
}

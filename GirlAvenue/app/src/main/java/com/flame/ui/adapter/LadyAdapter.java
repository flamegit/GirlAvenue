package com.flame.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flame.Constants;
import com.flame.model.Lady;
import com.flame.ui.LadyViewActivity;
import com.flame.ui.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/4.
 */
public class LadyAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    private int mType;

    public final static int PROGRESS=0;
    public final static int LADY_TYPE=1;
    public final static int STRING_TYPE=2;
    public final static int TAG_TYPE=3;
    private List<T> mResults;
    private Context mContext;
    private View.OnClickListener mListener;

    boolean isShowProgress;
    public LadyAdapter(Context context,int type){
        mType=type;
        mContext=context;
        mResults=new ArrayList<>(10);
    }

    public void showProgress(){
        notifyItemInserted(0);
        isShowProgress=true;
    }
    public void hideProgress(){
        isShowProgress=false;
        notifyItemRemoved(0);
    }

    public void clearItems(){
        int count=mResults.size();
        if(count>0){
            mResults.clear();
            notifyItemRangeRemoved(0,count);
        }
    }

    public void setListener(View.OnClickListener listener){
        mListener=listener;
    }

    @Override
    public int getItemCount() {
        return isShowProgress ? 1:mResults.size();
    }
    public void addItem( T items){
        mResults.add(items);
        notifyItemInserted(getItemCount()-1);
    }
    public void addItems( List<T> items){
        if(mResults.size()>0){
            mResults.clear();
        }
        mResults.addAll(items);
        notifyItemRangeInserted(0,items.size());
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        if(getItemViewType(position)==PROGRESS){
            RecyclerView.LayoutParams layoutParams= (RecyclerView.LayoutParams)holder.itemView.getLayoutParams();
            if(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
                ((StaggeredGridLayoutManager.LayoutParams)layoutParams).setFullSpan(true);
            }
            return;
        }

        holder.bindViewHolder(mResults.get(position),position);
        if(mType==LADY_TYPE){
            final Lady lady=(Lady)mResults.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, LadyViewActivity.class);
                    LadyViewActivity.sUrl=lady.mUrl;
                    LadyViewActivity.sDes=lady.mDes;
                    intent.putExtra(Constants.URL,lady.mUrl).putExtra(Constants.DEC,lady.mDes);
                    if(mContext instanceof Activity){
                        ActivityOptionsCompat options = ActivityOptionsCompat
                                .makeScaleUpAnimation( holder.itemView,  holder.itemView.getWidth() / 2,  holder.itemView.getHeight() / 2, 0, 0);
                        ActivityCompat.startActivity((Activity)mContext, intent, options.toBundle());
                    }else {
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
       if(isShowProgress && position==0){
           return PROGRESS;
       }else {
           return mType;
       }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==PROGRESS){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view,parent,false);
            return new ProgressViewHolder(view);
        }
        if(mType==LADY_TYPE){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view,parent,false);
            return new LadyViewHolder(view);
        }
        if(mType==STRING_TYPE){
            ImageView imageView=new ImageView(parent.getContext());
            int h=parent.getHeight()/4;
            GridLayoutManager.LayoutParams params=new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new StringViewHolder(imageView);
        }
        if(mType==TAG_TYPE){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item,null);
            int h=parent.getHeight()/3;
            GridLayoutManager.LayoutParams params=new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h);
            view.setLayoutParams(params);
            return new TagViewHolder(view);

        }
        return null;
    }
}

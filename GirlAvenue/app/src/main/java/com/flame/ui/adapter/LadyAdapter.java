package com.flame.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flame.Constants;
import com.flame.database.GirlDAO;
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
    public final static int LADY_TYPE=1;
    public final static int STRING_TYPE=2;
    public final static int TAG_TYPE=3;
    private List<T> mResults;
    private Context mContext;
    boolean isDelete;
    private View.OnClickListener mListener;
    public LadyAdapter(Context context,int type){
        mType=type;
        mContext=context;
        isDelete=false;
        mResults=new ArrayList<>();

    }

    public void clearItems(){
        int count=mResults.size();
        if(count>0){
            mResults.clear();
            notifyItemRangeRemoved(0,count);
        }
    }

    public void setDeleteItem(boolean isDelete){
        this.isDelete=isDelete;
    }

    @Override
    public int getItemCount() {
       // return isShowProgress ? 1:mResults.size();
        return mResults.size();
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

    private void deleteItem(int position){
        mResults.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {

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
            final ImageView favoriteView=((LadyViewHolder)holder).favoriteView;
            ((LadyViewHolder)holder).favoriteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lady.isFavorite) {
                        if (!GirlDAO.getInstance(mContext).delete(lady)) {
                            return;
                        }
                        lady.isFavorite = false;
                        favoriteView.setImageResource(R.mipmap.favorite);
                        if(isDelete){
                            deleteItem(position);
                        }
                    } else {
                        GirlDAO.getInstance(mContext).insert(lady);
                        lady.isFavorite = true;
                        favoriteView.setImageResource(R.mipmap.favorite_press);
                    }
                }
            });
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mType==LADY_TYPE){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false);
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

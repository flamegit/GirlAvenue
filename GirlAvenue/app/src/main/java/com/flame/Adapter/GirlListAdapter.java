package com.flame.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flame.Presenter.GirlContract;
import com.flame.girlavenue.R;
import com.flame.model.Lady;
import com.flame.model.Response;
import com.squareup.picasso.Cache;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


/**
 * Created by Administrator on 2016/10/4.
 */
public class GirlListAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{
    List<T> mResults;
    boolean isShowFooter;
    private final static int FOOTER=1;
    private final static int HEADER=2;
    public GirlListAdapter(){
        mResults=new ArrayList<>(10);
    }
    public void showFooter(){
        isShowFooter=true;
        notifyItemInserted(getItemCount());
    }
    public void hideFooter(){
        isShowFooter=false;
        notifyItemMoved(getItemCount()-1,getItemCount());
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==FOOTER){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view,parent,false);
        }else {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view,parent,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
       return  new BaseViewHolder.GirlViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return isShowFooter ? mResults.size()+1:mResults.size();
    }
    public void addItems( List<T> items){
        Log.d("Girl",items.size()+"");
        mResults.addAll(items);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1 && isShowFooter){
            return FOOTER;
        }
        return 0;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(getItemViewType(position)==FOOTER){
            StaggeredGridLayoutManager.LayoutParams layoutParams= (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            return;
        }
        holder.onBind(mResults.get(position));
    }
}

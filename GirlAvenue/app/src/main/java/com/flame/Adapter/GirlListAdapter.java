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
public class GirlListAdapter<T> extends RecyclerView.Adapter<GirlListAdapter.ViewHolder> {

    List<T> mResults;
    Context mContext;
    boolean isShowFooter;


    public GirlListAdapter(Context context){
        mContext=context;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==1){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view,parent,false);
            view.findViewById(R.id.progress_bar);
        }else {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view,parent,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        return new ViewHolder(view);
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
            return 1;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position)==1){
            Log.d("fxlts","footer");
            StaggeredGridLayoutManager.LayoutParams layoutParams= (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            return;
        }
        T t=mResults.get(position);
        if(t instanceof Response.Girl){
            Response.Girl girl=(Response.Girl)t;
            Picasso picasso= Picasso.with(mContext);
            picasso.setLoggingEnabled(true);
            picasso.load(girl.url)
                    .noFade()
                    .into((ImageView)holder.imageView);
        }
        if(t instanceof Lady){
            Lady lady=(Lady) t;
            Picasso picasso= Picasso.with(mContext);
            picasso.setLoggingEnabled(true);
            picasso.load(lady.mThumbUrl)
                    .noFade()
                    .into((ImageView)holder.imageView);
        }

    }

     static class ViewHolder extends RecyclerView.ViewHolder{
        public View imageView;
        ViewHolder(View view){
            super(view);
            imageView=view.findViewById(R.id.image);
      }
    }


}

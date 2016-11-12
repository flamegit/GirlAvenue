package com.flame.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flame.model.Girl;
import com.flame.model.Lady;
import com.flame.ui.LadyViewActivity;
import com.flame.ui.R;
import com.flame.utils.RxBus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/4.
 */
public class LadyPreViewAdapter extends RecyclerView.Adapter<LadyPreViewAdapter.ViewHolder> {
    List<String> mResults;
    Context mContext;
    String mUrl;


    public LadyPreViewAdapter(Context context,String url){
        mContext=context;
        mUrl=url;
        mResults=new ArrayList<>(10);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView=new ImageView(parent.getContext());
        GridLayoutManager.LayoutParams params=new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ViewHolder(imageView);
    }

    @Override
    public int getItemCount() {
        return  mResults.size();
    }

    public void addItem(String items){
        mResults.add(items);
        notifyItemInserted(mResults.size()-1);
        Log.d("fxlts","add item");
    }

    public void addItems( List<String> items){
        mResults.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso picasso= Picasso.with(mContext);
        picasso.setLoggingEnabled(true);
        picasso.load(mResults.get(position))
                .noFade() //load animation
                .placeholder(new ColorDrawable(Color.GRAY))
                .into((ImageView)holder.itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(mContext, LadyViewActivity.class);
               intent.putExtra("url",mUrl).putExtra("index",position);
               mContext.startActivity(intent);
            }
        });
    }

     static class ViewHolder extends RecyclerView.ViewHolder{
        ViewHolder(View view){
            super(view);
      }
    }
}
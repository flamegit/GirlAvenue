package com.flame.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.flame.ui.R;

/**
 * Created by Administrator on 2016/11/19.
 */
public class LadyViewHolder extends RecyclerView.ViewHolder{
    public View imageView;
    public TextView textView;

    LadyViewHolder(View view){
        super(view);
        imageView=view.findViewById(R.id.image);
        textView=(TextView)view.findViewById(R.id.des_view);
    }
}

package com.flame.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/2/2.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements IBindViewHolder<T> {

    public BaseViewHolder(View view){
        super(view);
    }
}

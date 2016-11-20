package com.flame.ui.adapter;

import android.content.Context;
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
import com.flame.ui.R;
import com.flame.utils.RxBus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/4.
 */
public abstract class BaseRecyclerViewAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> mResults;
    protected Context mContext;
    public BaseRecyclerViewAdapter(Context context){
        mContext=context;
        mResults=new ArrayList<>(10);
    }

    @Override
    public int getItemCount() {
        return  mResults.size();
    }
    public void addItem( T items){
        mResults.add(items);
        notifyItemInserted(getItemCount()-1);
    }
    public void addItems( List<T> items){
        mResults.addAll(items);
        notifyDataSetChanged();
    }
}

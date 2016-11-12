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
import com.flame.ui.R;
import com.flame.model.Lady;
import com.flame.model.Response;
import com.flame.utils.RxBus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/4.
 */
public class GirlListAdapter<T> extends RecyclerView.Adapter<GirlListAdapter.ViewHolder> {
    List<T> mResults;
    Context mContext;
    boolean isShowFooter;
    private View.OnClickListener mListener;

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
        }else {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view,parent,false);
        }

        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return isShowFooter ? mResults.size()+1:mResults.size();
    }

    public void addItem( T items){
        mResults.add(items);
        notifyDataSetChanged();
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
        String url=null,desc=null;
        final T t=mResults.get(position);
        if(t instanceof Girl){
            Girl girl=(Girl)t;
            url=girl.url;
            desc=girl.desc;
        }
        if(t instanceof Lady){
            Lady lady=(Lady) t;
            url=lady.mThumbUrl;
            desc=lady.mDes;
        }

        Picasso picasso= Picasso.with(mContext);
        picasso.setLoggingEnabled(true);
        picasso.load(url)
                .noFade()
                .into((ImageView)holder.imageView);
        holder.textView.setText(desc);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(t);
            }
        });
    }

     static class ViewHolder extends RecyclerView.ViewHolder{
         public View imageView;
         public TextView textView;
        ViewHolder(View view){
            super(view);
            imageView=view.findViewById(R.id.image);
            textView=(TextView)view.findViewById(R.id.des_view);
      }
    }
}

package com.flame.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flame.database.GirlDAO;
import com.flame.database.GirlData;
import com.flame.model.Girl;
import com.flame.model.Lady;
import com.flame.ui.LadyViewActivity;
import com.flame.ui.R;
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
    View.OnClickListener mOnClickListener;
    int mPage;

    private static final int FOOTER=1;
    private static final int NAVIGATION=2;

    public GirlListAdapter(Context context, int page){
        mContext=context;
        mResults=new ArrayList<>(10);
        mPage=page;
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
        if(viewType==FOOTER){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view,parent,false);
        } else {
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
        if(mResults.size()>0){
            mResults.clear();
        }
        mResults.addAll(items);
        //notifyItemRangeInserted(0,items.size());
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(getItemViewType(position)==FOOTER){
            StaggeredGridLayoutManager.LayoutParams layoutParams= (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            return;
        }
//        if(getItemViewType(position)==NAVIGATION){
//            StaggeredGridLayoutManager.LayoutParams layoutParams= (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
//            layoutParams.setFullSpan(true);
//            holder.itemView.setOnClickListener(mOnClickListener);
////            View next= holder.itemView.findViewById(R.id.next_view);
////            next.setOnClickListener(mOnClickListener);
//            View previous=holder.itemView.findViewById(R.id.previous_view);
////            previous.setOnClickListener(mOnClickListener);
//            previous.setClickable(false);
//
//            return;
//        }
        String url=null,desc=null;
        final T t=mResults.get(position);
        if(t instanceof Girl){
            Girl girl=(Girl)t;
            url=girl.url;
            desc=girl.desc;
        }
        if(t instanceof Lady){
            final Lady lady=(Lady) t;
            url=lady.mThumbUrl;
            desc=lady.mDes;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, LadyViewActivity.class);
                    intent.putExtra("url",lady.mUrl).putExtra("desc",lady.mDes);
                    mContext.startActivity(intent);
                }
            });
            int resId=lady.isFavorite? R.mipmap.favorite_press:R.mipmap.favorite;
            holder.favoriteView.setImageResource(resId);
            holder.favoriteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lady.isFavorite){
                        if(! GirlDAO.getInstance(mContext).delete(lady)) {
                            return;
                        }
                        lady.isFavorite=false;
                        holder.favoriteView.setImageResource(R.mipmap.favorite);
                    }else {
                        GirlDAO.getInstance(mContext).insert(lady);
                        lady.isFavorite=true;
                        holder.favoriteView.setImageResource(R.mipmap.favorite_press);
                    }
                }
            });
        }
        Picasso picasso= Picasso.with(mContext);
        picasso.setLoggingEnabled(true);
        picasso.load(url)
                .noFade()
                .into((ImageView)holder.imageView);
        holder.textView.setText(desc);

    }

     static class ViewHolder extends RecyclerView.ViewHolder{
         public View imageView;
         public TextView textView;
         public ImageView favoriteView;
        ViewHolder(View view){
            super(view);
            imageView=view.findViewById(R.id.image);
            textView=(TextView)view.findViewById(R.id.des_view);
            favoriteView=(ImageView)view.findViewById(R.id.favorite_view);
      }
    }
}

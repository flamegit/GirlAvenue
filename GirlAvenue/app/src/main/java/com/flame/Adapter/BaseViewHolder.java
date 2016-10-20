package com.flame.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flame.Presenter.GirlContract;
import com.flame.girlavenue.R;
import com.flame.model.Response;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/10/20.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements IViewHolder<T>{

  // SparseArray<View> caches;

   public BaseViewHolder(View itemView) {
      super(itemView);
      //caches=new SparseArray<View>();
   }

//   protected <T> T findViewById(View view, int id){
//      T item=(T)caches.get(id);
//      if(item!=null){
//         return item;
//      }
//      item =(T)view.findViewById(id);
//      return item;
//   }



   public static class GirlViewHolder extends BaseViewHolder<Response.Girl>{
      public ImageView imageView;
      public TextView textView;

      GirlViewHolder(View itemView){
         super(itemView);
      }
      @Override
      public void onBind(Response.Girl girl) {
          if(imageView==null){
             imageView=(ImageView)imageView.findViewById(R.id.image);
          }
         Picasso picasso= Picasso.with(itemView.getContext());
            picasso.setLoggingEnabled(true);
           picasso.load(girl.url)
                    .noFade()
                    .into(imageView);
      }

   }

}

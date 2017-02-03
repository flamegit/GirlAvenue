package com.flame.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.flame.Constants;
import com.flame.ui.LadyViewActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/2/2.
 */

public class StringViewHolder extends BaseViewHolder<String> {

    public StringViewHolder(View view){
        super(view);
    }

    @Override
    public void bindViewHolder(final String url,final int position) {
        final Context context=itemView.getContext();
        Picasso picasso= Picasso.with(context);
        picasso.setLoggingEnabled(true);
        picasso.load(url)
                .noFade() //load animation
                .placeholder(new ColorDrawable(Color.GRAY))
                .config(Bitmap.Config.RGB_565)
                .fit()
                .into((ImageView)itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LadyViewActivity.jumpToActivity(context, Constants.SHOW_DETAIL_ACTION,LadyViewActivity.sUrl,
                        " ",position);
            }
        });
    }
}

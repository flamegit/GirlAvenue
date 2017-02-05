package com.flame.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flame.Constants;
import com.flame.database.GirlDAO;
import com.flame.model.Lady;
import com.flame.ui.LadyViewActivity;
import com.flame.ui.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/11/19.
 */
public class LadyViewHolder extends BaseViewHolder<Lady> {
    public View imageView;
    public TextView textView;
    public ImageView favoriteView;

    LadyViewHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.des_view);
        favoriteView = (ImageView) view.findViewById(R.id.favorite_view);
    }

    @Override
    public void bindViewHolder(final Lady lady,int position) {
        final Context context=imageView.getContext();
        int resId = lady.isFavorite ? R.mipmap.favorite_press : R.mipmap.favorite;
        favoriteView.setImageResource(resId);
        Picasso picasso = Picasso.with(context);
        picasso.setLoggingEnabled(true);
        picasso.load(lady.mThumbUrl)
                .noFade()
                .placeholder(new ColorDrawable(Color.GRAY))
                .into((ImageView) imageView);
        textView.setText(lady.mDes);
    }
}

package com.flame.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flame.Constants;
import com.flame.database.GirlDAO;
import com.flame.model.Lady;
import com.flame.model.Tag;
import com.flame.ui.LadyViewActivity;
import com.flame.ui.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/11/19.
 */
public class TagViewHolder extends BaseViewHolder<Tag> {
    public View imageView;
    public TextView tagName;
    public TextView tagNum;

    TagViewHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.tag_image);
        tagName = (TextView) view.findViewById(R.id.tag_name);
        tagNum = (TextView) view.findViewById(R.id.tag_num);
    }

    @Override
    public void bindViewHolder(final Tag tag, int position) {
        final Context context=imageView.getContext();
        Picasso picasso = Picasso.with(context);
        picasso.setLoggingEnabled(true);
        picasso.load(tag.mThumbUrl)
                .noFade()
                .placeholder(new ColorDrawable(Color.GRAY))
                .into((ImageView) imageView);
        tagName.setText(tag.name);
        tagNum.setText(tag.num);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LadyViewActivity.jumpToActivity(context, Constants.SHOW_TAG_ACTION,tag.url,tag.name,0);
            }
        });
    }
}

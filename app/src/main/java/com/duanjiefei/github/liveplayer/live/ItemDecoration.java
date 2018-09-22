package com.duanjiefei.github.liveplayer.live;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private Drawable mDivider;

    private static final int[] ATTR = new int[] {
            android.R.attr.listDivider
    };

    public ItemDecoration(Context context){
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(ATTR);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }




}

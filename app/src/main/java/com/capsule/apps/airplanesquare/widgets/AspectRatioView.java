package com.capsule.apps.airplanesquare.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.capsule.apps.airplanesquare.R;

public class AspectRatioView extends View {

    private float mAspectRatio = 0f;

    public AspectRatioView(Context context) {
        this (context, null, 0);
    }

    public AspectRatioView(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public AspectRatioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioView, defStyleAttr, 0);
        mAspectRatio = array.getFloat(R.styleable.AspectRatioView_aspectRatio, 0);

        setClickable(true);
        if (mAspectRatio == 0f) {
            throw new IllegalArgumentException("You must specify an aspect ratio when using AspectRatioView.");
        }
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAspectRatio != 0) {
            final int width = MeasureSpec.getSize(widthMeasureSpec);
            final int height = (int) (width / mAspectRatio);

            super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

package com.capsule.apps.airplanesquare.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.capsule.apps.airplanesquare.R;

public class MapCellView extends View {

    private static final String TAG = MapCellView.class.getSimpleName();

    private static final int[] STATE_SELECTABLE = {
            android.R.attr.selectable
    };

    private boolean mIsSelectable = false;
    private float mAspectRatio = 0f;

    public MapCellView(@NonNull Context context) {
        this (context, null, 0);
    }

    public MapCellView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public MapCellView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MapCellView, defStyleAttr, 0);
        setClickable(true);

        mAspectRatio = array.getFloat(R.styleable.MapCellView_aspectFrameRatio, 0);

        if (mAspectRatio == 0f) {
            throw new IllegalArgumentException("You must specify an aspect ratio when using CellView.");
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

package com.capsule.apps.airplanesquare.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.capsule.apps.airplanesquare.R;

public class SeatCellView extends View {

    private float mAspectRatio = 0f;
    private boolean mIsLegRoom = false;

    public SeatCellView(Context context) {
        this (context, null, 0);
    }

    public SeatCellView(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public SeatCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    public boolean isLegRoom() {
        return mIsLegRoom;
    }

    public void setLegRoom(boolean legRoom) {
        if (mIsLegRoom != legRoom) {
            mIsLegRoom = legRoom;

            setAspectRatio(mIsLegRoom ? 0.64f : 1.24f);
            setBackground(mIsLegRoom ? getResources().getDrawable(R.drawable.ic_oval_selector) : getResources().getDrawable(R.drawable.ic_circle_selector));
            invalidate();
        }
    }

    public float getAspectRatio() {
        return mAspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        if (mAspectRatio != aspectRatio) {
            mAspectRatio = aspectRatio;
            invalidate();
        }
    }
}

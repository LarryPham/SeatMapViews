package com.capsule.apps.airplanesquare.widgets;

import android.content.Context;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MapRowView extends ViewGroup implements View.OnClickListener {

    private static final String TAG = MapRowView.class.getSimpleName();
    private boolean mIsShowTitleRow;
    private TextView mTitleView;
    private OnSelectedListener mListener;

    public MapRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        child.setOnClickListener(this);
        super.addView(child, index, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long start = System.currentTimeMillis();
        final int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int rowHeight = 0;

        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            int l = (index * totalWidth) / 7;
            int r = ((index + 1) * totalWidth) / 7;
            int cellSize = r - l;

            int cellWidthSpec = MeasureSpec.makeMeasureSpec(cellSize, MeasureSpec.EXACTLY);
            int cellHeightSpec = MeasureSpec.makeMeasureSpec(child.getHeight(), MeasureSpec.UNSPECIFIED);

            //int cellHeightSpec = mIsShowTitleRow ? MeasureSpec.makeMeasureSpec(cellSize, MeasureSpec.AT_MOST) : cellWidthSpec;
            child.measure(cellWidthSpec, cellHeightSpec);

            if (child.getMeasuredHeight() > rowHeight) {
                rowHeight = child.getMeasuredHeight();
            }
        }
        final int widthWithPadding = totalWidth + getPaddingLeft() + getPaddingRight();
        final int heightWithPadding = rowHeight + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthWithPadding, heightWithPadding);
        Log.d(TAG, String.format("Row.onMeasure %d ms", System.currentTimeMillis() - start));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        long start = System.currentTimeMillis();
        int cellHeight = bottom - top;
        int width = (right - left);
        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            int l = ((index) * width) / 7;
            int r = ((index + 1) * width) / 7;
            child.layout(l, 0, r, cellHeight);
        }
        Log.d(TAG, String.format("Row.onLayout %d ms", System.currentTimeMillis() - start));
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.handleClick(view);
        }
    }

    public OnSelectedListener getListener() {
        return mListener;
    }

    public void setListener(OnSelectedListener listener) {
        mListener = listener;
    }

    public interface OnSelectedListener {
        void handleClick(View view);
    }
}

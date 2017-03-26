package com.capsule.apps.airplanesquare.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.percent.PercentLayoutHelper;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class PercentLinearLayout extends LinearLayout {

    private final PercentLayoutHelper mPercentHelper;

    public PercentLinearLayout(Context context) {
        super(context);
        mPercentHelper = new PercentLayoutHelper(this);
    }

    public PercentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPercentHelper = new PercentLayoutHelper(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mPercentHelper.adjustChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mPercentHelper.handleMeasuredStateTooSmall()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mPercentHelper.restoreOriginalParams();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams implements PercentLayoutHelper.PercentLayoutParams {

        private PercentLayoutHelper.PercentLayoutInfo mPercentLayoutInfo;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            mPercentLayoutInfo = PercentLayoutHelper.getPercentLayoutInfo(context, attrs);
        }

        public LayoutParams(int width, int height) {
            super (width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super (source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super (source);
        }

        @Override
        public PercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo() {
            return mPercentLayoutInfo;
        }

        @Override
        protected void setBaseAttributes(TypedArray array, int widthAttr, int heightAttr) {
            PercentLayoutHelper.fetchWidthAndHeight(this, array, widthAttr, heightAttr);
        }
    }
}

package com.capsule.apps.airplanesquare.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capsule.apps.airplanesquare.R;


public class MapsRowLayout extends ViewGroup {

    private final TextView mRowNumber;
    private final View mFirstItemView, mSecondItemView, mThirdItemView, mFourthItemView, mFifthItemView, mSixItemView;

    public MapsRowLayout(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public MapsRowLayout(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super (context, attributeSet, defStyleAttr);

        LayoutInflater.from(getContext()).inflate(R.layout.sample_row_view, this);
        mRowNumber = (TextView) findViewById(R.id.row_number);
        mFirstItemView = findViewById(R.id.first_item_view);
        mSecondItemView = findViewById(R.id.second_item_view);
        mThirdItemView = findViewById(R.id.third_item_view);
        mFourthItemView = findViewById(R.id.fourth_item_view);
        mFifthItemView = findViewById(R.id.fifth_item_view);
        mSixItemView = findViewById(R.id.six_item_view);
    }

    private void layoutView(View view, int left, int top, int width, int height) {
        MarginLayoutParams margins = (MarginLayoutParams) view.getLayoutParams();
        final int leftWithMargins = left + margins.leftMargin;
        final int topWithMargins = top + margins.topMargin;

        view.layout(leftWithMargins, topWithMargins, leftWithMargins + width, topWithMargins + height);
    }

    private int getWidthWithMargins(View child) {
        final MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
        return child.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
    }

    private int getHeightWithMargins(View child) {
        final MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    private int getMeasuredWidthWithMargins(View child) {
        final MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
    }

    private int getMeasuredHeightWithMargins(View child) {
        final MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();

        int currentTop = paddingTop;
        layoutView(mFirstItemView, paddingLeft, currentTop, mFirstItemView.getMeasuredWidth(), mFirstItemView.getMeasuredHeight());

        int contentLeft = getWidthWithMargins(mFirstItemView) + paddingLeft;
        int contentWidth = right - left - contentLeft - getPaddingRight();

        layoutView(mSecondItemView, contentLeft, currentTop, contentWidth, mSecondItemView.getMeasuredHeight());

        contentLeft = getWidthWithMargins(mSecondItemView) + paddingLeft;
        contentWidth = right - left - contentLeft - getPaddingRight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int usedWidth = 0;
        int usedHeight = 0;

        measureChildWithMargins(mFirstItemView, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
        usedWidth += getMeasuredWidthWithMargins(mFirstItemView);

        measureChildWithMargins(mSecondItemView, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
        usedWidth += getMeasuredWidthWithMargins(mSecondItemView);

        measureChildWithMargins(mThirdItemView, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
        usedWidth += getMeasuredWidthWithMargins(mThirdItemView);

        measureChildWithMargins(mRowNumber, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
        usedWidth += getMeasuredWidthWithMargins(mRowNumber);

        measureChildWithMargins(mFourthItemView, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
        usedWidth += getMeasuredWidthWithMargins(mFourthItemView);

        measureChildWithMargins(mFifthItemView, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
        usedWidth += getMeasuredWidthWithMargins(mFifthItemView);

        measureChildWithMargins(mSixItemView, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
        usedWidth += getMeasuredWidthWithMargins(mSixItemView);
        usedHeight =  Math.max(getMeasuredHeightWithMargins(mFirstItemView), getMeasuredHeightWithMargins(mSixItemView));
        int heightSize = usedHeight + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        //noinspection ResourceType
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public static <E extends Comparable<E>> E max(E[] list) {
        E max = list[0];
        for (int index = 1; index < list.length; index++) {
            if (list[index].compareTo(max) > 0) {
                max = list[index];
            }
        }
        return max;
    }
}

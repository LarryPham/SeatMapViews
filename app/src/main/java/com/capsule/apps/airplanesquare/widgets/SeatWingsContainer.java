package com.capsule.apps.airplanesquare.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.capsule.apps.airplanesquare.R;

import java.util.ArrayList;


public class SeatWingsContainer extends ViewGroup {

    private final String TAG = SeatWingsContainer.class.getSimpleName();

    private ArrayList<SeatRowView> mSeatRowViews = new ArrayList<>();
    private View mLeftWingView, mRightWingView;
    private LinearLayout mRowsContainer;

    public SeatWingsContainer(Context context) {
        this (context, null, 0);
    }

    public SeatWingsContainer(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public SeatWingsContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_seat_wings_container, this, true);
        mLeftWingView = findViewById(R.id.left_wing_view);
        mRightWingView = findViewById(R.id.right_wing_view);
        mRowsContainer = (LinearLayout) findViewById(R.id.wings_row_container);
    }

    public void addSeatRowView(SeatRowView seatRowView) {
        mSeatRowViews.add(seatRowView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int widthUsed = 0;
        int heightUsed = 0;
        measureChildWithMargins(mLeftWingView, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
        heightUsed += getMeasuredHeightWithMargins(mLeftWingView);

        measureChildWithMargins(mRowsContainer, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
        heightUsed += getMeasuredHeightWithMargins(mRowsContainer);

        measureChildWithMargins(mRightWingView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
        heightUsed += getMeasuredHeightWithMargins(mRightWingView);

        int heightSize = heightUsed + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthSize, heightSize);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int paddingLeft = getPaddingLeft();
        int currentTop = getPaddingTop();

        layoutView(mLeftWingView, paddingLeft, currentTop, mLeftWingView.getMeasuredWidth(), mLeftWingView.getMeasuredHeight());
        currentTop += getHeightWithMargins(mLeftWingView);
        layoutView(mRowsContainer, paddingLeft, currentTop, right - left - paddingLeft, bottom - top - currentTop);
    }

    private void layoutView(View view, int left, int top, int width, int height) {
        MarginLayoutParams margins = (MarginLayoutParams) view.getLayoutParams();
        final int leftWithMargins = left + margins.leftMargin;
        final int topWithMargins = top + margins.topMargin;

        view.layout(leftWithMargins, topWithMargins,
                leftWithMargins + width, topWithMargins + height);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    private int getHeightWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
    }

    private int getMeasuredHeightWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
    }

}

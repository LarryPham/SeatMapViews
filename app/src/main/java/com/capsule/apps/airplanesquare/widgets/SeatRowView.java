package com.capsule.apps.airplanesquare.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capsule.apps.airplanesquare.R;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

public class SeatRowView extends ViewGroup {

    private static final String TAG = SeatRowView.class.getSimpleName();
    public static final String FIRST_SEAT_VIEW_TAG = "FirstSeatView";
    public static final String SECOND_SEAT_VIEW_TAG = "SecondSeatView";
    public static final String THIRD_SEAT_VIEW_TAG = "ThirdSeatView";
    public static final String FOURTH_SEAT_VIEW_TAG = "FourthSeatView";
    public static final String FIFTH_SEAT_VIEW_TAG = "FifthSeatView";
    public static final String SIXTH_SEAT_VIEW_TAG = "SixthSeatView";

    private TextView mRowNumberView, mRowTitleView;
    private View mFirstCellView, mSecondCellView, mThirdCellView, mFourthCellView, mFifthCellView, mSixCellView;
    private LinearLayout mCellsContainer;
    private HashMap<String, View> mSeatViewStore = new HashMap<>();
    private boolean mIsShowTitle = false;

    public SeatRowView(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public SeatRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_seat_row, this, true);

        mRowTitleView = (TextView) findViewById(R.id.seat_row_title);
        mRowNumberView = (TextView) findViewById(R.id.row_number_view);
        mFirstCellView = findViewById(R.id.first_seat_cell_view);
        mSecondCellView = findViewById(R.id.second_seat_cell_view);
        mThirdCellView = findViewById(R.id.third_seat_cell_view);
        mFourthCellView = findViewById(R.id.fourth_seat_cell_view);
        mFifthCellView = findViewById(R.id.fifth_seat_cell_view);
        mSixCellView = findViewById(R.id.six_seat_cell_view);
        mCellsContainer = (LinearLayout) findViewById(R.id.seat_cell_container);
        addViews();
    }

    private void addViews() {
        mSeatViewStore.clear();
        mSeatViewStore.put(FIRST_SEAT_VIEW_TAG, mFirstCellView);
        mSeatViewStore.put(SECOND_SEAT_VIEW_TAG, mSecondCellView);
        mSeatViewStore.put(THIRD_SEAT_VIEW_TAG, mThirdCellView);
        mSeatViewStore.put(FOURTH_SEAT_VIEW_TAG, mFourthCellView);
        mSeatViewStore.put(FIFTH_SEAT_VIEW_TAG, mFifthCellView);
        mSeatViewStore.put(SIXTH_SEAT_VIEW_TAG, mSixCellView);
    }

    public HashMap<String, View> getSeatViewStore() {
        return mSeatViewStore;
    }

    public void setRowNumber(int rowNumber) {
        mRowNumberView.setText(String.valueOf(rowNumber));
        mRowNumberView.invalidate();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int widthUsed = 0;
        int heightUsed = 0;
        measureChildWithMargins(mRowTitleView, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
        heightUsed += getMeasuredHeightWithMargins(mRowTitleView);

        measureChildWithMargins(mCellsContainer, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
        heightUsed += getMeasuredHeightWithMargins(mCellsContainer);

        int heightSize = heightUsed + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int paddingLeft = getPaddingLeft();
        int currentTop = getPaddingTop();

        layoutView(mRowTitleView, paddingLeft, currentTop, mRowTitleView.getMeasuredWidth(), mRowTitleView.getMeasuredHeight());
        currentTop += getHeightWithMargins(mRowTitleView);
        layoutView(mCellsContainer, paddingLeft, currentTop, right - left - paddingLeft, bottom - top - currentTop);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    private void layoutView(View view, int left, int top, int width, int height) {
        MarginLayoutParams margins = (MarginLayoutParams) view.getLayoutParams();
        final int leftWithMargins = left + margins.leftMargin;
        final int topWithMargins = top + margins.topMargin;

        view.layout(leftWithMargins, topWithMargins,
                leftWithMargins + width, topWithMargins + height);
    }

    private int getHeightWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
    }

    private int getMeasuredHeightWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public boolean isShowTitle() {
        return mIsShowTitle;
    }

    public void setShowTitle(boolean showTitle) {
        if (mIsShowTitle != showTitle) {
            mIsShowTitle = showTitle;
            mRowTitleView.setVisibility(showTitle ? View.GONE : View.VISIBLE);
            invalidate();
        }
    }
}

package com.capsule.apps.airplanesquare.widgets;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v4.view.ScrollingView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.capsule.apps.airplanesquare.R;

import java.util.ArrayList;


public class SeatMapView extends ViewGroup implements ScrollingView{

    private static final String TAG = SeatMapView.class.getSimpleName();

    private Camera mCamera;
    private Matrix mMatrix;
    private Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);

    private LinearLayout mBodyViewContainer;
    private ImageView mHeaderViewContainer, mFooterViewContainer;

    private ArrayList<SeatRowView> mSeatRowViews = new ArrayList<>();

    public SeatMapView(Context context) {
        this (context, null, 0);
        onInitialize();
    }

    public SeatMapView(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
        onInitialize();
    }

    public SeatMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_seat_map, this, true);
        mHeaderViewContainer = (ImageView) findViewById(R.id.header_plane_imageview);
        mFooterViewContainer = (ImageView) findViewById(R.id.footer_plane_view);
        mBodyViewContainer = (LinearLayout) findViewById(R.id.body_seat_views_container);
        onInitialize();
    }

    private void onInitialize() {
        mCamera = new Camera();
        mMatrix = new Matrix();
        mPaint.setAntiAlias(true);
    }

    public void addSeatRowView(final SeatRowView seatRowView) {
        mSeatRowViews.add(seatRowView);
        mBodyViewContainer.addView(seatRowView);
        mBodyViewContainer.invalidate();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightUsed = 0;
        measureChildWithMargins(mHeaderViewContainer, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
        heightUsed += getMeasuredHeightWithMargins(mHeaderViewContainer);

        measureChildWithMargins(mBodyViewContainer, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
        heightUsed += getMeasuredHeightWithMargins(mBodyViewContainer);

        measureChildWithMargins(mFooterViewContainer, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
        heightUsed += getMeasuredHeightWithMargins(mBodyViewContainer);

        int heightSize = heightUsed + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int paddingLeft = getPaddingLeft();
        int currentTop = getPaddingTop();

        layoutView(mHeaderViewContainer, paddingLeft, currentTop, mHeaderViewContainer.getMeasuredWidth(), mHeaderViewContainer.getMeasuredHeight());
        currentTop += getHeightWithMargins(mHeaderViewContainer);

        layoutView(mBodyViewContainer, paddingLeft, currentTop, right - left - paddingLeft, mBodyViewContainer.getMeasuredHeight());
        currentTop += getHeightWithMargins(mBodyViewContainer);

        layoutView(mFooterViewContainer, paddingLeft, currentTop, mFooterViewContainer.getMeasuredWidth(), mFooterViewContainer.getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mMatrix.reset();
        mCamera.save();

        mCamera.rotate(2, 0, 0);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
        mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);

        canvas.concat(mMatrix);
        super.draw(canvas);
    }

    @Override
    public int computeHorizontalScrollRange() {
        return mSeatRowViews.get(0).getMeasuredHeight();
    }

    @Override
    public int computeHorizontalScrollOffset() {
        return 0;
    }

    @Override
    public int computeHorizontalScrollExtent() {
        return 0;
    }

    @Override
    public int computeVerticalScrollRange() {
        return 0;
    }

    @Override
    public int computeVerticalScrollOffset() {
        return 0;
    }

    @Override
    public int computeVerticalScrollExtent() {
        return 0;
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
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
}

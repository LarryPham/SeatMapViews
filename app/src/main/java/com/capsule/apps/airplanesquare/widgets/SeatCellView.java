package com.capsule.apps.airplanesquare.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.capsule.apps.airplanesquare.R;

public class SeatCellView extends View {

    private static final String TAG = MapCellView.class.getSimpleName();
    private static final int DEFAULT_STROKE_COLOR = 0xFFDEDEDE;
    private static final int DEFAULT_FILLED_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_SELECTED_COLOR = 0x991989;
    private static final int DEFAULT_GREEN_COLOR = 0xFF35DC2C;

    private static final float DEFAULT_RADIUS_SIZE = 30f;
    private static final int DEFAULT_STROKE_WIDTH = 6;

    private int mStrokeWidth;
    private int mStrokeColor = DEFAULT_STROKE_COLOR;
    private int mFilledColor = DEFAULT_FILLED_COLOR;

    private float mDefineRadius = DEFAULT_RADIUS_SIZE;
    private final Paint mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mFilledPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mAspectRatio = 0f;

    private boolean mIsLegRoom = false;

    private RectF mOvalRect;
    private Rect mRoundedRect;
    private RectF mRoundedRectangle;

    private boolean mIsEnabled = false;
    private boolean mIsSelected = true;

    public SeatCellView(Context context) {
        this (context, null, 0);
        onInitialized(context, null, 0);
    }

    public SeatCellView(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
        onInitialized(context, attrs, 0);
    }

    public SeatCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInitialized(context, attrs, defStyleAttr);
    }

    private void onInitialized(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioView, defStyleAttr, 0);
        setClickable(true);

        mAspectRatio = array.getFloat(R.styleable.AspectRatioView_aspectRatio, 0);

        mStrokeColor = array.getColor(R.styleable.AspectRatioView_aspectStrokeColor, DEFAULT_GREEN_COLOR);
        mStrokeWidth = array.getInt(R.styleable.AspectRatioView_aspectStrokeWidth, DEFAULT_STROKE_WIDTH);
        mFilledColor = array.getInt(R.styleable.AspectRatioView_aspectFilledColor, DEFAULT_SELECTED_COLOR);
        mIsLegRoom = array.getBoolean(R.styleable.AspectRatioView_isLegRoom, false);
        mDefineRadius = array.getDimension(R.styleable.AspectRatioView_aspectRadius, DEFAULT_RADIUS_SIZE);

        mFilledPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mFilledPaint.setColor(mFilledColor);

        mStrokePaint.setStrokeWidth(mStrokeWidth);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(mStrokeColor);
        if (isLegRoom()) {
            mRoundedRectangle = new RectF(10, 10, getWidth() + 10, getHeight() + 10);
        } else {
            mOvalRect = new RectF(10, 10, getWidth(), getHeight());
        }
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isLegRoom()) {
            if (mOvalRect != null) {
                mOvalRect.right = getWidth() - 10;
                mOvalRect.bottom = getHeight() - 10;
                if (isSelected()) {
                    canvas.drawOval(mOvalRect, mFilledPaint);
                } else if (isEnabled()) {
                    canvas.drawOval(mOvalRect, mStrokePaint);
                } else  {
                    mStrokePaint.setColor(DEFAULT_STROKE_COLOR);
                    canvas.drawOval(mOvalRect, mStrokePaint);
                }
            }
        } else {
            if (mRoundedRectangle != null) {
                mRoundedRectangle.right = getWidth() - 10;
                mRoundedRectangle.bottom = getHeight() - 10;
                if (isSelected()) {
                    canvas.drawRoundRect(mRoundedRectangle, 50, 50, mFilledPaint);
                } else if (isEnabled()) {
                    canvas.drawRoundRect(mRoundedRectangle, 50, 50, mStrokePaint);
                } else {
                    mStrokePaint.setColor(DEFAULT_STROKE_COLOR);
                    canvas.drawRoundRect(mRoundedRectangle, 50, 50, mStrokePaint);
                }
            }
        }
    }

    public boolean isLegRoom() {
        return mIsLegRoom;
    }

    public void setLegRoom(boolean legRoom) {
        mIsLegRoom = legRoom;
    }
    public void setAspectRatio(float aspectRatio) {
        if (mAspectRatio != aspectRatio) {
            mAspectRatio = aspectRatio;
            invalidate();
        }
    }

    @Override
    public boolean isEnabled() {
        return mIsEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
        invalidate();
    }

    @Override
    public boolean isSelected() {
        return mIsSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        mIsSelected = selected;
        invalidate();
    }
}

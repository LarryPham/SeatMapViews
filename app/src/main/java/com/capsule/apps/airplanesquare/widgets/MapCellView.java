package com.capsule.apps.airplanesquare.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.capsule.apps.airplanesquare.R;

public class MapCellView extends View {

    private static final String TAG = MapCellView.class.getSimpleName();
    private static final int DEFAULT_STROKE_COLOR = 0xFFDEDEDE;
    private static final int DEFAULT_FILLED_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_SELECTED_COLOR = 0x991989;
    private static final int DEFAULT_GREEN_COLOR = 0x368450;

    private static final float DEFAULT_RADIUS_SIZE = 30f;
    private static final int DEFAULT_STROKE_WIDTH = 2;

    private int mStrokeWidth;
    private int mStrokeColor = DEFAULT_STROKE_COLOR;
    private int mFilledColor = DEFAULT_FILLED_COLOR;

    private float mDefineRadius = DEFAULT_RADIUS_SIZE;
    private final Paint mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mFilledPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mAspectRatio = 0f;

    private boolean mIsLegRoom = false;

    private RectF mOvalRect;
    private RectF mRoundedRectangle;

    public MapCellView(@NonNull Context context) {
        this (context, null, 0);
        onInitialized(context, null, 0);
    }

    public MapCellView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
        onInitialized(context, attrs, 0);
    }

    public MapCellView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInitialized(context, attrs, defStyleAttr);
    }

    private void onInitialized(Context context, AttributeSet attrs, int defStyleAttr) {
    }

}

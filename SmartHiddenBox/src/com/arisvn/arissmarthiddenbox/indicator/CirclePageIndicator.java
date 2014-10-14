/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: NhuHoang
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.indicator - CirclePageIndicator.java
 * Date create: 2:47:44 PM - Mar 17, 2014 - 2014
 * 
 * 
 */


package com.arisvn.arissmarthiddenbox.indicator;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.arisvn.arissmarthiddenbox.R;

// TODO: Auto-generated Javadoc
/**
 * Draws circles (one for each view). The current view position is filled and
 * others are only stroked.
 */
public class CirclePageIndicator extends View implements PageIndicator {

    /** The Constant INVALID_POINTER. */
    private static final int INVALID_POINTER = -1;

    /** The m radius. */
    private float mRadius;

    /** The m paint page fill. */
    private final Paint mPaintPageFill = new Paint(ANTI_ALIAS_FLAG);

    /** The m paint stroke. */
    private final Paint mPaintStroke = new Paint(ANTI_ALIAS_FLAG);

    /** The m paint fill. */
    private final Paint mPaintFill = new Paint(ANTI_ALIAS_FLAG);

    /** The m view pager. */
    private ViewPager mViewPager;

    /** The m listener. */
    private ViewPager.OnPageChangeListener mListener;

    /** The m current page. */
    private int mCurrentPage;

    /** The m snap page. */
    private int mSnapPage;

    /** The m page offset. */
    private float mPageOffset;

    /** The m scroll state. */
    private int mScrollState;

    /** The m orientation. */
    private int mOrientation;

    /** The m centered. */
    private boolean mCentered;

    /** The m snap. */
    private boolean mSnap;

    /** The m touch slop. */
    private int mTouchSlop;

    /** The m last motion x. */
    private float mLastMotionX = -1;

    /** The m active pointer id. */
    private int mActivePointerId = INVALID_POINTER;

    /** The m is dragging. */
    private boolean mIsDragging;

    /**
     * Instantiates a new circle page indicator.
     *
     * @param context the context
     */
    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new circle page indicator.
     *
     * @param context the context
     * @param attrs the attrs
     */
    public CirclePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.vpiCirclePageIndicatorStyle);
    }

    /**
     * Instantiates a new circle page indicator.
     *
     * @param context the context
     * @param attrs the attrs
     * @param defStyle the def style
     */
    public CirclePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode())
            return;

        // Load defaults from resources
        final Resources res = getResources();
        final int defaultPageColor = res.getColor(R.color.default_circle_indicator_page_color);
        final int defaultFillColor = res.getColor(R.color.default_circle_indicator_fill_color);
        final int defaultOrientation = res
                .getInteger(R.integer.default_circle_indicator_orientation);
        final int defaultStrokeColor = res.getColor(R.color.default_circle_indicator_stroke_color);
        final float defaultStrokeWidth = res
                .getDimension(R.dimen.default_circle_indicator_stroke_width);
        final float defaultRadius = res.getDimension(R.dimen.default_circle_indicator_radius);
        final boolean defaultCentered = res.getBoolean(R.bool.default_circle_indicator_centered);
        final boolean defaultSnap = res.getBoolean(R.bool.default_circle_indicator_snap);

        // Retrieve styles attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator,
                defStyle, 0);

        mCentered = a.getBoolean(R.styleable.CirclePageIndicator_centered, defaultCentered);
        mOrientation = a.getInt(R.styleable.CirclePageIndicator_android_orientation,
                defaultOrientation);
        mPaintPageFill.setStyle(Style.FILL);
        mPaintPageFill.setColor(a.getColor(R.styleable.CirclePageIndicator_pageColor,
                defaultPageColor));
        mPaintStroke.setStyle(Style.STROKE);
        mPaintStroke.setColor(a.getColor(R.styleable.CirclePageIndicator_strokeColor,
                defaultStrokeColor));
        mPaintStroke.setStrokeWidth(a.getDimension(R.styleable.CirclePageIndicator_strokeWidth,
                defaultStrokeWidth));
        mPaintFill.setStyle(Style.FILL);
        mPaintFill
        .setColor(a.getColor(R.styleable.CirclePageIndicator_fillColor, defaultFillColor));
        mRadius = a.getDimension(R.styleable.CirclePageIndicator_radius_circle, defaultRadius);
        mSnap = a.getBoolean(R.styleable.CirclePageIndicator_snap, defaultSnap);

        a.recycle();

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    /**
     * Sets the centered.
     *
     * @param centered the new centered
     */
    public void setCentered(boolean centered) {
        mCentered = centered;
        invalidate();
    }

    /**
     * Checks if is centered.
     *
     * @return true, if is centered
     */
    public boolean isCentered() {
        return mCentered;
    }

    /**
     * Sets the page color.
     *
     * @param pageColor the new page color
     */
    public void setPageColor(int pageColor) {
        mPaintPageFill.setColor(pageColor);
        invalidate();
    }

    /**
     * Gets the page color.
     *
     * @return the page color
     */
    public int getPageColor() {
        return mPaintPageFill.getColor();
    }

    /**
     * Sets the fill color.
     *
     * @param fillColor the new fill color
     */
    public void setFillColor(int fillColor) {
        mPaintFill.setColor(fillColor);
        invalidate();
    }

    /**
     * Gets the fill color.
     *
     * @return the fill color
     */
    public int getFillColor() {
        return mPaintFill.getColor();
    }

    /**
     * Sets the orientation.
     *
     * @param orientation the new orientation
     */
    public void setOrientation(int orientation) {
        switch (orientation) {
            case HORIZONTAL:
            case VERTICAL:
                mOrientation = orientation;
                requestLayout();
                break;

            default:
                throw new IllegalArgumentException(
                        "Orientation must be either HORIZONTAL or VERTICAL.");
        }
    }

    /**
     * Gets the orientation.
     *
     * @return the orientation
     */
    public int getOrientation() {
        return mOrientation;
    }

    /**
     * Sets the stroke color.
     *
     * @param strokeColor the new stroke color
     */
    public void setStrokeColor(int strokeColor) {
        mPaintStroke.setColor(strokeColor);
        invalidate();
    }

    /**
     * Gets the stroke color.
     *
     * @return the stroke color
     */
    public int getStrokeColor() {
        return mPaintStroke.getColor();
    }

    /**
     * Sets the stroke width.
     *
     * @param strokeWidth the new stroke width
     */
    public void setStrokeWidth(float strokeWidth) {
        mPaintStroke.setStrokeWidth(strokeWidth);
        invalidate();
    }

    /**
     * Gets the stroke width.
     *
     * @return the stroke width
     */
    public float getStrokeWidth() {
        return mPaintStroke.getStrokeWidth();
    }

    /**
     * Sets the radius.
     *
     * @param radius the new radius
     */
    public void setRadius(float radius) {
        mRadius = radius;
        invalidate();
    }

    /**
     * Gets the radius.
     *
     * @return the radius
     */
    public float getRadius() {
        return mRadius;
    }

    /**
     * Sets the snap.
     *
     * @param snap the new snap
     */
    public void setSnap(boolean snap) {
        mSnap = snap;
        invalidate();
    }

    /**
     * Checks if is snap.
     *
     * @return true, if is snap
     */
    public boolean isSnap() {
        return mSnap;
    }

    /* (non-Javadoc)
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mViewPager == null) {
            return;
        }
        final int count = mViewPager.getAdapter().getCount();
        if (count == 0) {
            return;
        }

        if (mCurrentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }

        int longSize;
        int longPaddingBefore;
        int longPaddingAfter;
        int shortPaddingBefore;
        if (mOrientation == HORIZONTAL) {
            longSize = getWidth();
            longPaddingBefore = getPaddingLeft();
            longPaddingAfter = getPaddingRight();
            shortPaddingBefore = getPaddingTop();
        } else {
            longSize = getHeight();
            longPaddingBefore = getPaddingTop();
            longPaddingAfter = getPaddingBottom();
            shortPaddingBefore = getPaddingLeft();
        }

        final float threeRadius = mRadius * 3;
        final float shortOffset = shortPaddingBefore + mRadius;
        float longOffset = longPaddingBefore + mRadius;
        if (mCentered) {
            longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f)
                    - ((count * threeRadius) / 2.0f);
        }

        float dX;
        float dY;

        float pageFillRadius = mRadius;
        if (mPaintStroke.getStrokeWidth() > 0) {
            pageFillRadius -= mPaintStroke.getStrokeWidth() / 2.0f;
        }

        // Draw stroked circles
        for (int iLoop = 0; iLoop < count; iLoop++) {
            float drawLong = longOffset + (iLoop * threeRadius);
            if (mOrientation == HORIZONTAL) {
                dX = drawLong;
                dY = shortOffset;
            } else {
                dX = shortOffset;
                dY = drawLong;
            }
            // Only paint fill if not completely transparent
            if (mPaintPageFill.getAlpha() > 0) {
                canvas.drawCircle(dX, dY, pageFillRadius, mPaintPageFill);
            }

            // Only paint stroke if a stroke width was non-zero
            if (pageFillRadius != mRadius) {
                canvas.drawCircle(dX, dY, (float)(mRadius/1.5), mPaintStroke);
            }
        }

        // Draw the filled circle according to the current scroll
        float cx = (mSnap ? mSnapPage : mCurrentPage) * threeRadius;
        if (!mSnap) {
            cx += mPageOffset * threeRadius;
        }
        if (mOrientation == HORIZONTAL) {
            dX = longOffset + cx;
            dY = shortOffset;
        } else {
            dX = shortOffset;
            dY = longOffset + cx;
        }
        canvas.drawCircle(dX, dY, mRadius*1.5f, mPaintFill);
    }

    /* (non-Javadoc)
     * @see android.view.View#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(android.view.MotionEvent ev) {
        if (super.onTouchEvent(ev)) {
            return true;
        }
        if ((mViewPager == null) || (mViewPager.getAdapter().getCount() == 0)) {
            return false;
        }

        final int action = ev.getAction();

        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mLastMotionX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE: {
                final int activePointerIndex = MotionEventCompat.findPointerIndex(ev,
                        mActivePointerId);
                final float x = MotionEventCompat.getX(ev, activePointerIndex);
                final float deltaX = x - mLastMotionX;

                if (!mIsDragging) {
                    if (Math.abs(deltaX) > mTouchSlop) {
                        mIsDragging = true;
                    }
                }

                if (mIsDragging) {
                    mLastMotionX = x;
                    if (mViewPager.isFakeDragging() || mViewPager.beginFakeDrag()) {
                        mViewPager.fakeDragBy(deltaX);
                    }
                }

                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!mIsDragging) {
                    final int count = mViewPager.getAdapter().getCount();
                    final int width = getWidth();
                    final float halfWidth = width / 2f;
                    final float sixthWidth = width / 6f;

                    if ((mCurrentPage > 0) && (ev.getX() < halfWidth - sixthWidth)) {
                        mViewPager.setCurrentItem(mCurrentPage - 1);
                        return true;
                    } else if ((mCurrentPage < count - 1) && (ev.getX() > halfWidth + sixthWidth)) {
                        mViewPager.setCurrentItem(mCurrentPage + 1);
                        return true;
                    }
                }

                mIsDragging = false;
                mActivePointerId = INVALID_POINTER;
                if (mViewPager.isFakeDragging())
                    mViewPager.endFakeDrag();
                break;

            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(ev);
                final float x = MotionEventCompat.getX(ev, index);
                mLastMotionX = x;
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }
                mLastMotionX = MotionEventCompat.getX(ev,
                        MotionEventCompat.findPointerIndex(ev, mActivePointerId));
                break;
        }

        return true;
    };

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.indicator.PageIndicator#setViewPager(android.support.v4.view.ViewPager)
     */
    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        if (view.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        mViewPager.setOnPageChangeListener(this);
        invalidate();
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.indicator.PageIndicator#setViewPager(android.support.v4.view.ViewPager, int)
     */
    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.indicator.PageIndicator#setCurrentItem(int)
     */
    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mViewPager.setCurrentItem(item);
        mCurrentPage = item;
        invalidate();
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.indicator.PageIndicator#notifyDataSetChanged()
     */
    @Override
    public void notifyDataSetChanged() {
        invalidate();
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;

        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = position;
        mPageOffset = positionOffset;
        invalidate();

        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
     */
    @Override
    public void onPageSelected(int position) {
        if (mSnap || mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentPage = position;
            mSnapPage = position;
            invalidate();
        }

        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.indicator.PageIndicator#setOnPageChangeListener(android.support.v4.view.ViewPager.OnPageChangeListener)
     */
    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOrientation == HORIZONTAL) {
            setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
        } else {
            setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec));
        }
    }

    /**
     * Determines the width of this view.
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureLong(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (mViewPager == null)) {
            // We were told how big to be
            result = specSize;
        } else {
            // Calculate the width according the views count
            final int count = mViewPager.getAdapter().getCount();
            result = (int) (getPaddingLeft() + getPaddingRight()
                    + (count * 2 * mRadius) + (count - 1) * mRadius + 1);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view.
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureShort(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the height
            result = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see android.view.View#onRestoreInstanceState(android.os.Parcelable)
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        mSnapPage = savedState.currentPage;
        requestLayout();
    }

    /* (non-Javadoc)
     * @see android.view.View#onSaveInstanceState()
     */
    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }

    /**
     * The Class SavedState.
     */
    static class SavedState extends BaseSavedState {

        /** The current page. */
        int currentPage;

        /**
         * Instantiates a new saved state.
         *
         * @param superState the super state
         */
        public SavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Instantiates a new saved state.
         *
         * @param in the in
         */
        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        /* (non-Javadoc)
         * @see android.view.AbsSavedState#writeToParcel(android.os.Parcel, int)
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }

        /** The Constant CREATOR. */
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}

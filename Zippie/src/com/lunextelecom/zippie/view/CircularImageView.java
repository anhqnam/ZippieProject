/*
 * @author Jake Wharton
 * @custom Vuong Huynh
 * Copyright (C) 2012 Jake Wharton
 * http://viewpagerindicator.com/
 */
package com.lunextelecom.zippie.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lunextelecom.zippie.R;

// TODO: Auto-generated Javadoc
/**
 * The Class CircularImageView.
 */
@SuppressLint("DrawAllocation")
public class CircularImageView extends ImageView {

    /** The border width. */
    private int borderWidth;

    /** The canvas size. */
    private int canvasSize;

    /** The image. */
    private Bitmap image;

    /** The paint. */
    private Paint paint;

    /** The paint border. */
    private Paint paintBorder;

    /**
     * Instantiates a new circular image view.
     * 
     * @param context the context
     */
    public CircularImageView(final Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new circular image view.
     * 
     * @param context the context
     * @param attrs the attrs
     */
    public CircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.circularImageViewStyle);
    }

    /**
     * Instantiates a new circular image view.
     * 
     * @param context the context
     * @param attrs the attrs
     * @param defStyle the def style
     */
    @SuppressLint("Recycle")
    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // init paint
        paint = new Paint();
        paint.setAntiAlias(true);

        paintBorder = new Paint();
        paintBorder.setAntiAlias(true);

        // load the styled attributes and set their properties
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, defStyle, 0);

        if(attributes.getBoolean(R.styleable.CircularImageView_border, true)) {
            int defaultBorderSize = (int) (4 * getContext().getResources().getDisplayMetrics().density + 0.5f);
            setBorderWidth(attributes.getDimensionPixelOffset(R.styleable.CircularImageView_border_width, defaultBorderSize));
            setBorderColor(attributes.getColor(R.styleable.CircularImageView_border_color, Color.WHITE));
        }

        if(attributes.getBoolean(R.styleable.CircularImageView_shadow, false))
            addShadow();
    }

    /**
     * Sets the border width.
     * 
     * @param borderWidth the new border width
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        this.requestLayout();
        this.invalidate();
    }

    /**
     * Sets the border color.
     * 
     * @param borderColor the new border color
     */
    @SuppressLint("DrawAllocation") public void setBorderColor(int borderColor) {
        if (paintBorder != null)
            paintBorder.setColor(borderColor);
        this.invalidate();
    }

    /**
     * Adds the shadow.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addShadow() {
        setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
        paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
    }

    /* (non-Javadoc)
     * @see android.widget.ImageView#onDraw(android.graphics.Canvas)
     */
    @Override
    public void onDraw(Canvas canvas) {
        // load the bitmap
        image = drawableToBitmap(getDrawable());

        // init shader
        if (image != null) {

            canvasSize = canvas.getWidth();
            if(canvas.getHeight()<canvasSize)
                canvasSize = canvas.getHeight();

            BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvasSize, canvasSize, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);

            // circleCenter is the x or y of the view's center
            // radius is the radius in pixels of the cirle to be drawn
            // paint contains the shader that will texture the shape
            int circleCenter = (canvasSize - (borderWidth * 2)) / 2;
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, ((canvasSize - (borderWidth * 2)) / 2) + borderWidth - 4.0f, paintBorder);
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, ((canvasSize - (borderWidth * 2)) / 2) - 4.0f, paint);
        }
    }

    /* (non-Javadoc)
     * @see android.widget.ImageView#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * Measure width.
     * 
     * @param measureSpec the measure spec
     * @return the int
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // The parent has determined an exact size for the child.
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            //result = specSize;
            image = drawableToBitmap(getDrawable());
            if(image != null){
                result = image.getWidth();
            }else{
                result = 0;
            }
        } else {
            // The parent has not imposed any constraint on the child.
            result = canvasSize;
        }

        return result;
    }

    /**
     * Measure height.
     * 
     * @param measureSpecHeight the measure spec height
     * @return the int
     */
    private int measureHeight(int measureSpecHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            //result = specSize;
            image = drawableToBitmap(getDrawable());
            if(image != null){
                result = image.getHeight();
            }else{
                result = 0;
            }
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = canvasSize;
        }

        return (result + 2);
    }

    /**
     * Drawable to bitmap.
     * 
     * @param drawable the drawable
     * @return the bitmap
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
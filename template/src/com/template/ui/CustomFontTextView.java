package com.template.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import com.template.R;
import com.template.utils.TypefacesUtils;

import java.lang.reflect.Field;

public class CustomFontTextView extends TextView {

    private static final String TAG = CustomFontTextView.class.getSimpleName();
    private static final float MIN_TEXT_SIZE = 1.0f;
    private static final Canvas TEXT_RESIZE_CANVAS = new Canvas();
    private boolean autoSize = false;
    private boolean mNeedsResize = true;
    private float mSpacingMult = 1.0f;
    private float mSpacingAdd = 0.0f;
    private float defaultSize;
    private boolean resizeHeight = false;
    private int height = 0, width = 0;

    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttribiute(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttribiute(context, attrs);
    }

    private void parseAttribiute(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        String customFont = a.getString(R.styleable.CustomFontTextView_customFont);
        autoSize = a.getBoolean(R.styleable.CustomFontTextView_autoSize, false);

        if (customFont != null && !customFont.equals("")) {
            setCustomFont(ctx, customFont);
        }
        defaultSize = getTextSize();

        a.recycle();
        // Improve custom font apperance.
        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    public boolean setCustomFont(Context ctx, String asset) {
        try {
            Typeface tf = TypefacesUtils.get(asset);
            setTypeface(tf);
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        mSpacingMult = mult;
        mSpacingAdd = add;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        resizeHeight = MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.UNSPECIFIED;
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    public boolean onPreDraw() {
        if ((mNeedsResize) && autoSize) {
            int mHeight = 0;
            int mWidth = 0;
            try {
                mHeight = height - getTotalPaddingBottom() - getTotalPaddingTop();
                mWidth = width - getTotalPaddingLeft() - getTotalPaddingRight();
            } catch (NullPointerException e) {
                mHeight = height - getPaddingBottom() - getPaddingTop();
                mWidth = width - getPaddingLeft() - getPaddingRight();
            }
            resizeText(mWidth, mHeight);
        }
        return super.onPreDraw();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mNeedsResize = true;
        }
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        super.onTextChanged(text, start, before, after);
        if (autoSize) {
            mNeedsResize = true;
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            mNeedsResize = true;
        }
    }

    private void resizeText(int widthPx, int heightPx) {
        CharSequence text = getText();
        // Do not resize if the view does not have dimensions or there is no text
        if (text == null || text.length() == 0 || widthPx <= 0) {
            return;
        }


        // Get the text view's paint object (as a copy, so we don't modify it)
        TextPaint textPaint = new TextPaint();
        textPaint.set(getPaint());

        // If there is a max text size set, use that; otherwise, base the max text size
        // on the current text size.
        float targetTextSize = defaultSize;


        int lineCount = getTextLineCount(text, textPaint, widthPx, targetTextSize);
        int maxLine = getMaxLine();
        while (lineCount > maxLine && targetTextSize > MIN_TEXT_SIZE) {
            targetTextSize -= 1;
            lineCount = getTextLineCount(text, textPaint, widthPx, targetTextSize);
        }
        float widthTextSize = targetTextSize;

        if (getTag() != null && getTag().equals("abc")) {
            Log.i("Measure", "widthTextSize:" + widthTextSize + " maxLine:" + maxLine);
        }

        if (resizeHeight) {
            int height = getTextHeight(text, textPaint, widthPx, targetTextSize);
            while (height > heightPx && targetTextSize > MIN_TEXT_SIZE) {
                targetTextSize -= 1;
                height = getTextHeight(text, textPaint, widthPx, targetTextSize);
            }

        }

        // Some devices try to auto adjust line spacing, so force default line spacing
        // and invalidate the layout as a side effect
        setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize);
        if (getTag() != null && getTag().equals("abc")) {
            Log.i("Measure", "targetTextSize:" + targetTextSize);
        }
        setLineSpacing(mSpacingAdd, mSpacingMult);
        // Reset force resize flag
        mNeedsResize = false;
        requestLayout();
    }


    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultSize);
    }

    private int getMaxLine() {
        try {
            Field f = getClass().getSuperclass().getDeclaredField("mMaximum");
            f.setAccessible(true);
            return (Integer) f.get(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private int getTextLineCount(CharSequence source, TextPaint paint, int widthPx, float textSize) {
        // Update the text paint object
        paint.setTextSize(textSize);

        // Draw using a static layout
        StaticLayout layout = new StaticLayout(source, paint, widthPx, Layout.Alignment.ALIGN_NORMAL, mSpacingMult,
                mSpacingAdd, true);

        try {
            layout.draw(TEXT_RESIZE_CANVAS);
        } catch (Exception e) {
            return 1;
        }
        return layout.getLineCount();

    }

    // Set the text size of the text paint object and use a static layout to render text off screen before measuring
    private int getTextHeight(CharSequence source, TextPaint paint, int width, float textSize) {
        // Update the text paint object
        paint.setTextSize(textSize);

        // Draw using a static layout
        StaticLayout layout = new StaticLayout(source, paint, width, Layout.Alignment.ALIGN_NORMAL, mSpacingMult,
                mSpacingAdd, true);
        try {
            layout.draw(TEXT_RESIZE_CANVAS);
        } catch (Exception e) {
            return (int) textSize;
        }
        return layout.getHeight();
    }
}

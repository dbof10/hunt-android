package com.ctech.eaty.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.ctech.eaty.R;
import com.ctech.eaty.entity.Product;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class BaselineGridTextView extends TextView {

    private final float FOUR_DIP;

    private float lineHeightMultiplierHint = 1f;
    private float lineHeightHint = 0f;
    private boolean maxLinesByHeight = false;
    private int extraTopPadding = 0;
    private int extraBottomPadding = 0;

    public BaselineGridTextView(Context context) {
        this(context, null);
    }

    public BaselineGridTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public BaselineGridTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BaselineGridTextView(Context context, AttributeSet attrs,
                                int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.BaselineGridTextView, defStyleAttr, defStyleRes);

        lineHeightMultiplierHint =
                a.getFloat(R.styleable.BaselineGridTextView_lineHeightMultiplierHint, 1f);
        lineHeightHint = a.getDimension(R.styleable.BaselineGridTextView_lineHeightHint, 0);
        maxLinesByHeight = a.getBoolean(R.styleable.BaselineGridTextView_maxLinesByHeight, false);
        a.recycle();

        FOUR_DIP = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        computeLineHeight();

        Observable.just(1)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                        return null;
                    }
                });
    }

    public float getLineHeightMultiplierHint() {
        return lineHeightMultiplierHint;
    }

    public void setLineHeightMultiplierHint(float lineHeightMultiplierHint) {
        this.lineHeightMultiplierHint = lineHeightMultiplierHint;
        computeLineHeight();
    }

    public float getLineHeightHint() {
        return lineHeightHint;
    }

    public void setLineHeightHint(float lineHeightHint) {
        this.lineHeightHint = lineHeightHint;
        computeLineHeight();
    }

    public boolean getMaxLinesByHeight() {
        return maxLinesByHeight;
    }

    public void setMaxLinesByHeight(boolean maxLinesByHeight) {
        this.maxLinesByHeight = maxLinesByHeight;
        requestLayout();
    }

    @Override
    public int getCompoundPaddingTop() {
        // include extra padding to place the first line's baseline on the grid
        return super.getCompoundPaddingTop() + extraTopPadding;
    }

    @Override
    public int getCompoundPaddingBottom() {
        // include extra padding to make the height a multiple of 4dp
        return super.getCompoundPaddingBottom() + extraBottomPadding;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        extraTopPadding = 0;
        extraBottomPadding = 0;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        height += ensureBaselineOnGrid();
        height += ensureHeightGridAligned(height);
        setMeasuredDimension(getMeasuredWidth(), height);
        checkMaxLines(height, MeasureSpec.getMode(heightMeasureSpec));
    }

    /**
     Ensures line height is a multiple of 4dp.
     */
    private void computeLineHeight() {
        final Paint.FontMetrics fm = getPaint().getFontMetrics();
        final float fontHeight = Math.abs(fm.ascent - fm.descent) + fm.leading;
        final float desiredLineHeight = (lineHeightHint > 0)
                ? lineHeightHint
                : lineHeightMultiplierHint * fontHeight;

        final int baselineAlignedLineHeight =
                (int) ((FOUR_DIP * (float) Math.ceil(desiredLineHeight / FOUR_DIP)) + 0.5f);
        setLineSpacing(baselineAlignedLineHeight - fontHeight, 1f);
    }

    /**
     Ensure that the first line of text sits on the 4dp grid.
     */
    private int ensureBaselineOnGrid() {
        float baseline = getBaseline();
        float gridAlign = baseline % FOUR_DIP;
        if (gridAlign != 0) {
            extraTopPadding = (int) (FOUR_DIP - Math.ceil(gridAlign));
        }
        return extraTopPadding;
    }

    /**
     Ensure that height is a multiple of 4dp.
     */
    private int ensureHeightGridAligned(int height) {
        float gridOverhang = height % FOUR_DIP;
        if (gridOverhang != 0) {
            extraBottomPadding = (int) (FOUR_DIP - Math.ceil(gridOverhang));
        }
        return extraBottomPadding;
    }

    /**
     When measured with an exact height, text can be vertically clipped mid-line. Prevent
     this by setting the {@code maxLines} property based on the available space.
     */
    private void checkMaxLines(int height, int heightMode) {
        if (!maxLinesByHeight || heightMode != MeasureSpec.EXACTLY) {
            return;
        }

        int textHeight = height - getCompoundPaddingTop() - getCompoundPaddingBottom();
        int completeLines = (int) Math.floor(textHeight / getLineHeight());
        setMaxLines(completeLines);
    }
}
package com.kitty.kitty_base.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.kitty.kitty_base.R;


/**
 * 按照比例展示宽高的自定义控件
 *
 * @author ningkang
 */
public class RatioFragment extends FrameLayout {

    private float ratio;

    public RatioFragment(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RatioFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载自定义属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RatioFragment);
        // 根据属性id获取属性值, 方式: R.styleable.名称_属性
        ratio = typedArray.getFloat(R.styleable.RatioFragment_ratio_fragment, 0);
        // 回收TypedArray, 释放内存
        typedArray.recycle();
    }

    public RatioFragment(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // MeasureSpec.EXACTLY 确定值, 比如把宽高值写死,或者match_parent
        // MeasureSpec.AT_MOST 至多, 能撑多大就多大, 类似wrap_content
        // MeasureSpec.UNSPECIFIED 未指定大小

        if (widthMode == MeasureSpec.EXACTLY
                && heightMode != MeasureSpec.EXACTLY && ratio != 0) {
            // 1. 根据布局宽度推算图片宽度
            int imageWidth = widthSize - getPaddingLeft() - getPaddingRight();
            // 2. 根据图片宽度和宽高比,推算图片高度
            int imageHeight = (int) (imageWidth / ratio);
            // 3. 根据图片高度, 推算布局高度
            heightSize = imageHeight + getPaddingTop() + getPaddingBottom();
            // 4. 根据布局高度, 推算height；
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                    MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

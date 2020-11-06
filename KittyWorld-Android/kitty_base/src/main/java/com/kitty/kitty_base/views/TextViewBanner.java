package com.kitty.kitty_base.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;

import com.kitty.kitty_base.model.NoticeModel;
import com.kitty.kitty_base.utils.Utils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lombok.Setter;

public class TextViewBanner extends AppCompatTextView {


    private int mY = 0;
    private int mIndex = 0;
    private Paint mPaintFront;
    private boolean isMove = true;
    private boolean hasInit = false;

    @Setter
    private int mDuration;

    @Setter
    private int mInterval;

    @Setter
    private List<String> mTexts;

    @Setter
    private TextBannerClickListener itemClickListener;


    public TextViewBanner(Context context) {
        this(context, null);
    }

    public TextViewBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        if (action == MotionEvent.ACTION_UP && itemClickListener != null
//                && mTexts != null && mTexts.size() > mIndex) {
//            itemClickListener.onClick(mTexts.get(mIndex).getId());
//        }
        return true;
    }

    public void setFrontColor(int mFrontColor) {
        mPaintFront.setColor(mFrontColor);
    }

    private void init() {
        mDuration = 300;
        mInterval = 500;
        mIndex = 0;
        mPaintFront = new Paint();
        mPaintFront.setAntiAlias(true);
        mPaintFront.setDither(true);
        mPaintFront.setTextSize(Utils.sp2px(11));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mTexts == null || mTexts.size() <= mIndex) {
            return;
        }

        String title = mTexts.get(mIndex);
        if (TextUtils.isEmpty(title)) {
            return;
        }

        Rect indexBound = new Rect();
        mPaintFront.getTextBounds(title, 0, title.length(), indexBound);

        if (mY == 0 && hasInit == false) {
            mY = getMeasuredHeight() - indexBound.top;
            hasInit = true;
        }
        if (mY == 0 - indexBound.bottom) {
            mY = getMeasuredHeight() - indexBound.top;
            mIndex++;
        }
        canvas.drawText(title, 0, title.length(), 10, mY, mPaintFront);

        if (mY == getMeasuredHeight() / 2 - (indexBound.top + indexBound.bottom) / 2) {
            //停止移动
            isMove = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    postInvalidate();
                    isMove = true;
                }
            }, mInterval);
        }

        mY -= 1;

        if (mIndex == mTexts.size()) {
            mIndex = 0;
        }

        if (isMove) {
            postInvalidateDelayed(mDuration / getMeasuredHeight());
        }
    }

    /**
     * 文本点击监听
     */
    public interface TextBannerClickListener {
        void onClick(int id);
    }

}


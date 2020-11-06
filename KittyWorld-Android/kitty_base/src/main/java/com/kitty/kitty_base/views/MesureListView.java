package com.kitty.kitty_base.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MesureListView extends ListView {

    public MesureListView(Context context) {
        super(context);
    }

    public MesureListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MesureListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("Range")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //問題是解決下，但缺點也是很明細，就是ListView的複用機制不起作用了
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE << 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

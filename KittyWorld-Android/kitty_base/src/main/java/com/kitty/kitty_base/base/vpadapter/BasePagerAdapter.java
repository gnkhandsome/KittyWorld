package com.kitty.kitty_base.base.vpadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * ViewPager适配器基类
 *
 * @author hoolai
 * @date 2017/6/14
 */

public abstract class BasePagerAdapter<T> extends PagerAdapter {

    protected List<T> data;
    protected Context mContext;

    public BasePagerAdapter(Context context, List<T> data) {
        this.data = data;
        this.mContext = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return setDataCount(data);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return setPagerContent(container, position);
    }

    /**
     * 设置viewpager页面的展示内容。
     */
    protected abstract View setPagerContent(ViewGroup container, int position);

    /**
     * 设置资源数目，决定是否需要无限轮播。
     */
    protected abstract int setDataCount(List<T> data);

}

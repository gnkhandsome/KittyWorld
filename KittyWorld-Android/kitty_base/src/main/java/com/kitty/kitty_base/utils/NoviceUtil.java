package com.kitty.kitty_base.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.ui.AdventureActivity;

import io.reactivex.Observable;

/**
 * 新手相关信息工具类
 *
 * @author ningkang
 * @date 2018/1/22
 */

public class NoviceUtil {
    /**
     * 是否显示首页浮标新手标按钮
     */
    public static boolean canAdventure() {
        int level = SPUtils.getInt(Utils.getContext(), SPConfig.MAX_LEVEL, 0);
        return level >= 38;
    }


    /**
     * 获取按钮触摸监听2
     */
    @NonNull
    public static View.OnTouchListener getOnTouchListener(Activity activity, final int screenWidth, final int screenHeight, int[][] btnPosition) {
        return new View.OnTouchListener() {
            int lastX, lastY;
            long startTouch;
            long endTouch;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        startTouch = System.currentTimeMillis();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        int left = v.getLeft() + dx;
                        int top = v.getTop() + dy;
                        int right = v.getRight() + dx;
                        int bottom = v.getBottom() + dy;
                        if (left < 0) {
                            left = 0;
                            right = left + v.getWidth();
                        }
                        if (right > screenWidth) {
                            right = screenWidth;
                            left = right - v.getWidth();
                        }
                        if (top < 0) {
                            top = 0;
                            bottom = top + v.getHeight();
                        }
                        if (bottom > screenHeight) {
                            bottom = screenHeight;
                            top = bottom - v.getHeight();
                        }
                        v.layout(left, top, right, bottom);
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        btnPosition[0] = new int[]{left, top, right, bottom};

                        break;
                    case MotionEvent.ACTION_UP:
                        endTouch = System.currentTimeMillis();
                        if (endTouch - startTouch <= 200) {
                            Intent intent = new Intent(activity, AdventureActivity.class);
                            activity.startActivity(intent);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        };
    }


    /**
     * 服务器返回的业务状态监听
     *
     * @author ningkang
     */
    public interface StatusResponseListener {
        /**
         * 服务端业务状态返回
         *
         * @param status
         */
        void onResponseSuccess(int status);
    }
}

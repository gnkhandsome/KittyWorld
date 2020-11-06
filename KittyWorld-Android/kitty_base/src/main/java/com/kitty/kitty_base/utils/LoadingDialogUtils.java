package com.kitty.kitty_base.utils;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.views.zloading.ZLoadingDialog;
import com.kitty.kitty_base.views.zloading.Z_TYPE;

public class LoadingDialogUtils {

    /***
     * 显示加载中loading
     *
     * @param type
     */
    public static void showIndeterminateProgressDialog(ZLoadingDialog dialog, final Z_TYPE type) {
        try {
            dialog.setLoadingBuilder(type)
                    .setLoadingColor(Utils.getColor(R.color.color_5D75FF))
                    //     .setHintTextSize(12) // 设置字体大小
                    .setHintTextColor(Utils.getColor(R.color.color_5D75FF))  // 设置字体颜色
                    .setDurationTime(0.8) // 设置动画时间百分比
                    // .setDialogBackgroundColor(Utils.getColor(R.color.z_transparent)) // 设置背景色
                    .show();
        } catch (Exception e) {
        }
    }

    /***
     * 显示加载中loading
     *
     * @param type
     */
    public static void showIndeterminateProgressDialog(ZLoadingDialog dialog, String customtext, final Z_TYPE type) {
        try {
            dialog.setLoadingBuilder(type)
                    .setLoadingColor(Utils.getColor(R.color.color_5D75FF))
                    .setHintText(customtext)
                    //     .setHintTextSize(12) // 设置字体大小
                    .setHintTextColor(Utils.getColor(R.color.color_5D75FF))  // 设置字体颜色
                    .setDurationTime(0.8) // 设置动画时间百分比
                    // .setDialogBackgroundColor(Utils.getColor(R.color.z_transparent)) // 设置背景色
                    .show();
        } catch (Exception e) {

        }
    }
}

package com.kitty.kitty_base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kitty.kitty_ads.union_test.toutiao.utils.UIUtils;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.base.BaseCenterTipDialog;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.fragmentdialog.PermissionRequestDialog;
import com.kitty.kitty_base.model.TipContentModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SettingUtil {
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * 启动应用通知权限设置页
     */
    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;

        try {

            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 启动相册页面
     */
    public static void startAppSettings(Activity activity, int resultCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, resultCode);
    }

    /**
     * 启动当前应用设置页面
     */
    public static void startAppSettings(Activity activity) {
        try {
            Dialog dialog = new Dialog(activity, R.style.ActionSheetDialogStyle);
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View menuView = inflater.inflate(R.layout.fragment_dialog_permission_request, null);
            TextView tvSetting = (TextView) menuView.findViewById(R.id.tv_tip_dismiss);
            tvSetting.setOnClickListener(v -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(intent);
                dialog.dismiss();
            });
            dialog.setContentView(menuView);
            dialog.setCancelable(true);
            Window dialogWindow = dialog.getWindow();
            assert dialogWindow != null;
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.y = Utils.dip2px(10);
            dialogWindow.setAttributes(lp);
            dialog.show();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
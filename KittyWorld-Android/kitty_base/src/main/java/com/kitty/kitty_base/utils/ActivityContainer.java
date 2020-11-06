package com.kitty.kitty_base.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.kitty.kitty_base.ws.utils.SocketManager;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ActivityContainer {

    private static ActivityContainer instance = new ActivityContainer();

    private static List<Activity> activityStack = new ArrayList<>();

    public static ActivityContainer getInstance() {
        return instance;
    }

    public void addActivity(Activity aty) {
        activityStack.add(aty);
    }

    public void removeActivity(Activity aty) {
        activityStack.remove(aty);
    }

    /**
     * 结束指定Activity
     */
    public void endActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity(boolean reset) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        SocketManager.disConnect(reset);
        activityStack.clear();
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 判断activity对象数组是否为空
     */
    private static boolean isActivityStackEmpty() {
        return activityStack.size() <= 0;
    }

    /**
     * 获得栈中最顶层的Activity
     */
    public static Activity getTopActivity() {
        if (isActivityStackEmpty()) {
            return null;
        }
        return activityStack.get(activityStack.size() - 1);
    }

    /**
     * 获得栈中最顶层的Activity
     */
    private static String getTopActivityLocalName() {
        if (isActivityStackEmpty()) {
            return null;
        }
        return activityStack.get(activityStack.size() - 1).getLocalClassName();
    }

    /**
     * 判断传入的activity的localClassName是否和顶部Activity的相同
     */
    public static boolean isTopActivity(String localClassName) {
        if (TextUtils.equals(localClassName, ActivityContainer.getTopActivityLocalName())) {
            return true;
        } else {
            return false;
        }
    }
}

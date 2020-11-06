package com.kitty.kitty_base.utils;

import com.kitty.kitty_base.R;

public class TimeDescUtil {

//    public static String getTimeDesc(int amount){
//        long time = amount / 60;
//        String timeDesc;
//        if (time >= 60) {
//            time = time / 60;
//            timeDesc = time + Utils.getString(R.string.hour);
//        } else if (time >= 1) {
//            timeDesc = time + Utils.getString(R.string.minite);
//        } else if(time>=0){
//            timeDesc = amount + Utils.getString(R.string.second);
//        }else{
//            timeDesc = Utils.getString(R.string.forever);
//        }
//        return timeDesc;
//    }

    public static String getTimeDesc(int time) {
        String timeStr = null;
        int minute = 0;
        int second = 0;
        if (time < 0)
            timeStr = Utils.getString(R.string.forever);
        else {
            minute = time / 60;
            if (minute < 1) {
                second = time;
                timeStr = "0分" + second + Utils.getString(R.string.second);
            } else {
                second = time % 60;
                timeStr = minute + Utils.getString(R.string.minite) + (second > 0 ? second + Utils.getString(R.string.second) : "");
            }
        }
        return timeStr;
    }


    public static String getTimeKittyDesc(int time) {
        String timeStr = null;
        int minute = 0;
        int second = 0;
        if (time < 0)
            timeStr = Utils.getString(R.string.forever_kitty);
        else {
            minute = time / 60;
            if (minute < 1) {
                second = time;
                timeStr = "0分" + second + Utils.getString(R.string.second) + Utils.getString(R.string.limit_fortune_kitty);
            } else {
                second = time % 60;
                timeStr = minute + Utils.getString(R.string.minite) + (second > 0 ? second + Utils.getString(R.string.second) : "") + Utils.getString(R.string.limit_fortune_kitty);
            }
        }
        return timeStr;
    }
}

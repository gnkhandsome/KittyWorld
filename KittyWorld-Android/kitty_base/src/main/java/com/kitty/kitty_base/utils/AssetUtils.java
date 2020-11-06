package com.kitty.kitty_base.utils;

import android.content.res.AssetManager;

import java.io.IOException;

public class AssetUtils {

    public static boolean isFileExists(String filename) {
        AssetManager assetManager = Utils.getContext().getAssets();
        String[] names = new String[0];
        try {
            names = assetManager.list(filename);
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(filename.trim())) {
                    ToastUtils.showLong(filename + "存在");
                    return true;
                }
            }
        } catch (IOException e) {
            ToastUtils.showLong(filename + "不存在");
            return false;
        }
        ToastUtils.showLong(filename + "不存在");
        return false;
    }

//    public static boolean isFileExists2(String filename) {
//        AssetManager assetManager = Utils.getContext().getAssets();
//        String[] names = new String[0];
//        try {
//            names = assetManager.list(filename);
//
//            for (int i = 0; i < names.length; i++) {
//                if (names[i].equals(filename.trim())) {
//                    ToastUtils.showLong(filename + "存在");
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            ToastUtils.showLong(filename + "不存在");
//            return false;
//        }
//        ToastUtils.showLong(filename + "不存在");
//        return false;
//    }
}

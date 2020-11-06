package com.kitty.kitty_base.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class TypeFaceUtils {

    private static final HashMap<String, Typeface> cache = new HashMap<>();

    public static final String NUM_TYPE = "fonts/CondBold.ttf";

    /**
     * 字体的转换工具
     *
     * @param assetPath
     * @return
     */
    public static Typeface getNumberTypeFace(String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface mTypeface = Typeface.createFromAsset(Utils.getContext().getAssets(), assetPath);
                    cache.put(assetPath, mTypeface);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }
}

package com.kitty.kitty_base.utils.singleton;

import android.os.Handler;
import android.os.Looper;


public class MainHandler extends Handler {

    private static volatile MainHandler mInstance;

    public MainHandler() {
        super(Looper.getMainLooper());
    }

    public static MainHandler getInstance() {
        if (mInstance == null) {
            synchronized (MainHandler.class) {
                if (mInstance == null) {
                    mInstance = new MainHandler();
                }
            }
        }
        return mInstance;
    }
}

package com.kitty.kittyworld;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseActivity;

public class WelComeActivity extends BaseActivity {
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {
//        ttAdManager.requestPermissionIfNecessary(getApplicationContext());
//        TTAdNative ttNativeAd = ttAdManager.createAdNative(getApplicationContext());
//        ttNativeAd.loadFullScreenVideoAd();
    }
}

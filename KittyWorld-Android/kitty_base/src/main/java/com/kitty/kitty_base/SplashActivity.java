package com.kitty.kitty_base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.MainThread;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.kitty.kitty_ads.R;
import com.kitty.kitty_ads.union_test.toutiao.config.TTAdManagerHolder;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.router.RouterActivityPath;
import com.kitty.kitty_base.utils.ActivityContainer;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.StatusBarUtil;
import com.kitty.kitty_base.utils.StatusBarUtils;
import com.kitty.kitty_base.utils.singleton.MainHandler;
import com.kitty.kitty_base.ws.utils.SocketManager;

import me.jessyan.autosize.utils.ScreenUtils;

/**
 * 开屏广告Activity示例
 */
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";
    private TTAdNative mTTAdNative;
    private FrameLayout mSplashContainer;
    private ImageView iv_splash;
    //是否强制跳转到主页面
    private boolean mForceGoMain;

    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 3000;
    private String mCodeId = "887308065";
//    private boolean mIsExpress = false; //是否请求模板广告

    @SuppressWarnings("RedundantCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        addToContainer();
        initConnect();
        initWidow();
        setContentView(R.layout.activity_splash);
        mSplashContainer = (FrameLayout) findViewById(R.id.splash_container);
        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        //step2:创建TTAdNative对象
        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);
        //在合适的时机申请权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题
        //在开屏时候申请不太合适，因为该页面倒计时结束或者请求超时会跳转，在该页面申请权限，体验不好
        // TTAdManagerHolder.getInstance(this).requestPermissionIfNecessary(this);
        //加载开屏广告
        loadSplashAd();
        String token = SPUtils.getString(this, SPConfig.TOKEN);
        if (!TextUtils.isEmpty(token)) {
            HttpUtils.postRegisterId(token);
        }
    }

    protected void addToContainer() {
        ActivityContainer.getInstance().addActivity(this);
    }

    private void initConnect() {
        String uid = SPUtils.getString(this, SPConfig.UID);
        if (!TextUtils.isEmpty(uid)) {
            SocketManager.init(uid);
        }
    }

    protected void initWidow() {
        StatusBarUtils.with(this).init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            StatusBarUtil.setStatusBarLightMode(getWindow());
        }
    }


    @Override
    protected void onResume() {
        //判断是否该跳转到主页面
        if (mForceGoMain) {
            goToMainActivity();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mForceGoMain = true;
    }

    /**
     * 加载开屏广告
     */
    private void loadSplashAd() {
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
//        AdSlot adSlot = null;
//        if (mIsExpress) {
//            //个性化模板广告需要传入期望广告view的宽、高，单位dp，请传入实际需要的大小，
//            //比如：广告下方拼接logo、适配刘海屏等，需要考虑实际广告大小
//            float expressViewWidth = UIUtils.getScreenWidthDp(this);
//            float expressViewHeight = UIUtils.getHeight(this);
//            adSlot = new AdSlot.Builder()
//                    .setCodeId(mCodeId)
//                    .setSupportDeepLink(true)
//                    .setImageAcceptedSize(1080, 1920)
//                    //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
//                    .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight)
//                    .build();
//        } else {
        int[] screenSize = ScreenUtils.getRawScreenSize(getApplicationContext());
        int statusBarHeight = ScreenUtils.getStatusBarHeight();
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(mCodeId)
                .setMediaExtra("android")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(screenSize[0], screenSize[1] + statusBarHeight)
                .build();
//        }

        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onTimeout() {
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                if (ad == null) {
                    goToMainActivity();
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                if (mSplashContainer != null && !SplashActivity.this.isFinishing()) {
                    mSplashContainer.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    mSplashContainer.addView(view);
                    iv_splash.setVisibility(View.INVISIBLE);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                } else {
                    goToMainActivity();
                }

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        Log.d(TAG, "onAdClicked");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.d(TAG, "onAdShow");
                    }

                    @Override
                    public void onAdSkip() {
                        Log.d(TAG, "onAdSkip");
                        goToMainActivity();

                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "onAdTimeOver");
                        goToMainActivity();
                    }
                });
                if (ad.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ad.setDownloadListener(new TTAppDownloadListener() {
                        boolean hasShow = false;

                        @Override
                        public void onIdle() {
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!hasShow) {
                                hasShow = true;
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {

                        }
                    });
                }
            }
        }, AD_TIME_OUT);
    }

    /**
     * 跳转到主页面
     */
    private void goToMainActivity() {
        String uid = SPUtils.getString(SplashActivity.this, SPConfig.UID);
        String token = SPUtils.getString(SplashActivity.this, SPConfig.TOKEN);
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(token)) {
            ARouter.getInstance().build(RouterActivityPath.ACTIVITY_LOGIN_PATH).navigation();
        } else {
            ARouter.getInstance().build(RouterActivityPath.ACTIVITY_MAIN_PATH).navigation();
        }
        MainHandler.getInstance().postDelayed(() -> {
            mSplashContainer.removeAllViews();
            finish();
        }, 500);
    }

}

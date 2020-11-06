package com.kitty.kittyworld;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.kitty.kitty_ads.union_test.toutiao.config.TTAdManagerHolder;
import com.kitty.kitty_base.SplashActivity;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.interfaces.ForegroundListener;
import com.kitty.kitty_base.receiver.NetStateReceiver;
import com.kitty.kitty_base.utils.ForegroundCallbacks;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_crash.crash.CaocConfig;
import com.kitty.kitty_crash.crash.DefaultErrorActivity;
import com.kitty.websocket.WebSocketHandler;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.E;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;
import me.jessyan.autosize.AutoSizeConfig;

public class KittyWorldApplication extends Application {

    public static IWXAPI api;
    public static TTAdManager ttAdManager;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new BezierCircleHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new FalsifyFooter(context);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 友盟社会化分享初始化、
        UMConfigure.init(this, "5e996a9f895cca73c60005e7", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin("wx1af0c5054da7d6b6", "25b366fd89fcff7a9610bf5b4d712ff9");
        api = WXAPIFactory.createWXAPI(this, "wx1af0c5054da7d6b6", false);
        api.registerApp("wx1af0c5054da7d6b6");
        Utils.init(this);
        JPushInterface.setDebugMode(BuildConfig.IS_TEST_ENV);
        JPushInterface.init(this);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        FormDataUtil.initFormData();
        try {
            if (BuildConfig.IS_TEST_ENV) {
                ARouter.openLog();
                ARouter.openDebug();
                ARouter.printStackTrace();
            }
            ARouter.init(this);
        } catch (Exception e) {
            e.printStackTrace();
            //异常后建议清除映射表官方文档说 开发模式会清除
            ARouter.openDebug();
            ARouter.init(this);
        }

        //配置全局异常崩溃操作
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
                .enabled(true) //是否启动全局异常捕获
                .showRestartButton(true) //是否显示重启按钮
                .showErrorDetails(true)
                .trackActivities(true) //是否跟踪Activity
                .restartActivity(SplashActivity.class) //重新启动后的activity
                .errorActivity(DefaultErrorActivity.class) //崩溃后的错误activity
                .apply();

        //穿山甲SDK初始化
        TTAdManagerHolder.init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
//        RefWatcher mRefWatcher = LeakCanary.install(this);
//        Utils.setRefWatcher(mRefWatcher);

        //前后台切换逻辑判断
        ForegroundCallbacks.init(KittyWorldApplication.this);
        ForegroundCallbacks.get().addListener(new ForegroundListener() {
            @Override
            public void onBecameForeground() {
                try {
                    NetStateReceiver.registerNetworkStateReceiver(KittyWorldApplication.this);
                } catch (Exception e) {
                }
            }

            @Override
            public void onBecameBackground() {
                try {
                    NetStateReceiver.unRegisterStateReceiver(KittyWorldApplication.this);
                } catch (Exception e) {
                }
            }
        });
        //配置url测试正式
        String type =  BuildConfig.URL_TYPE;
        SPUtils.putString(Utils.getContext(),SPConfig.URL_TYPE,type);
    }

    @Override

    public void onTerminate() {
        super.onTerminate();
    }

}

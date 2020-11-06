package com.kitty.kitty_base.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.http.bus.event.EventBusCarrier;
import com.kitty.kitty_base.receiver.NetChangeObserver;
import com.kitty.kitty_base.receiver.NetStateReceiver;
import com.kitty.kitty_base.utils.ActivityContainer;
import com.kitty.kitty_base.utils.LoadingDialogUtils;
import com.kitty.kitty_base.utils.NetUtils;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.StatusBarUtil;
import com.kitty.kitty_base.utils.StatusBarUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.views.zloading.ZLoadingDialog;
import com.kitty.kitty_base.views.zloading.Z_TYPE;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.websocket.SocketListener;
import com.kitty.websocket.response.ErrorResponse;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.framing.Framedata;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import butterknife.ButterKnife;
import kitty_protos.C2SSyncKittyInfo;
import kitty_protos.MessageOuterClass;

public abstract class BaseActivity extends AppCompatActivity implements SocketListener {

    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    private ZLoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        setContentView(setLayoutResourceID());
        ButterKnife.bind(this);
        initData();
        setListener();
        NetChangeObserver netChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType netType) {
                C2SSyncKittyInfo.C2S_SyncKittyInfo.Builder c2S_syncKittyInfo = C2SSyncKittyInfo.C2S_SyncKittyInfo.newBuilder().setCallTimestamp(System.currentTimeMillis() / 1000);
                MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SSyncKittyInfo(c2S_syncKittyInfo).setMessageId(MessageOuterClass.Message.C2SSYNCKITTYINFO_FIELD_NUMBER);
                SocketManager.send(callObject);
            }

            @Override
            public void onNetDisConnect() {
            }
        };
        NetStateReceiver.registerObserver(netChangeObserver);
//        registerNavigationBarObserver();
    }


    protected void init(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initWidow();
        addToContainer();
        EventBus.getDefault().register(this);
        if (null != SocketManager.getWebSocketManager()) {
            SocketManager.getWebSocketManager().addListener(this);
        }
    }

    protected void addToContainer() {
        ActivityContainer.getInstance().addActivity(this);
    }

    protected abstract int setLayoutResourceID();

    protected void setListener() {
    }

    protected abstract void initData();

    /**
     * [绑定控件]
     *
     * @param resId
     * @return
     */
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }


    protected void initWidow() {
        StatusBarUtils.with(this).init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            StatusBarUtil.setStatusBarLightMode(getWindow());
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        this.startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        MobclickAgent.onResume(this);
//        ToastUtils.showShortCenter(String.valueOf(checkDeviceHasNavigationBar(BaseActivity.this)));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (null != SocketManager.getWebSocketManager()) {
            SocketManager.getWebSocketManager().removeListener(this);
        }
        ActivityContainer.getInstance().removeActivity(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleEvent(EventBusCarrier busCarrier) {
    }

    @Override
    public void onConnected() {
        Log.i("onConnected", "onConnected");

    }

    @Override
    public void onConnectFailed(Throwable e) {
        Log.i("onConnectFailed", "onConnectFailed");
    }

    @Override
    public void onDisconnect() {
        Log.i("onDisconnect", "onDisconnect");
    }

    @Override
    public void onSendDataError(ErrorResponse errorResponse) {
        Log.i("onSendDataError", "onSendDataError");
    }

    @Override
    public <T> void onMessage(String message, T data) {
        Log.i("onMessage", "onMessage");
    }

    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        Log.i("onMessage bytes", "onMessage");
    }

    @Override
    public void onPing(Framedata framedata) {
        Log.i("onPing bytes", "onPing");
    }

    @Override
    public void onPong(Framedata framedata) {
        Log.i("onPing bytes", "onPing");
    }

    public void showDialogEachTime() {
        try {
            if (dialog == null) {
                dialog = new ZLoadingDialog(this);
                LoadingDialogUtils.showIndeterminateProgressDialog(dialog, Z_TYPE.SINGLE_CIRCLE);
            } else {
                LoadingDialogUtils.showIndeterminateProgressDialog(dialog, Z_TYPE.SINGLE_CIRCLE);
            }
        } catch (Exception e) {
        }
    }


    public void showDialogEachTime(String customText) {
        try {
            if (dialog == null) {
                dialog = new ZLoadingDialog(this);
                LoadingDialogUtils.showIndeterminateProgressDialog(dialog, customText, Z_TYPE.SINGLE_CIRCLE);
            } else {
                LoadingDialogUtils.showIndeterminateProgressDialog(dialog, customText, Z_TYPE.SINGLE_CIRCLE);
            }
        } catch (Exception e) {
        }
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

//    private void registerNavigationBarObserver() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            getContentResolver().registerContentObserver(Settings.System.getUriFor
//                    ("navigationbar_is_min"), true, mNavigationBarObserver);
//        } else {
//            getContentResolver().registerContentObserver(Settings.Global.getUriFor
//                    ("navigationbar_is_min"), true, mNavigationBarObserver);
//        }
//    }
//
//    private ContentObserver mNavigationBarObserver = new ContentObserver(new Handler()) {
//        @Override
//        public void onChange(boolean selfChange) {
//            int navigationBarIsMin = 0;
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                navigationBarIsMin = Settings.System.getInt(getContentResolver(),
//                        "navigationbar_is_min", 0);
//            } else {
//                navigationBarIsMin = Settings.Global.getInt(getContentResolver(),
//                        "navigationbar_is_min", 0);
//            }
//            Log.e(TAG, "onChange: " + navigationBarIsMin);
//            if (navigationBarIsMin == 1) {
//                //导航键隐藏了
//                ToastUtils.showShortCenter("导航键隐藏了");
//                navigationBarHide();
//            } else {
//                //导航键显示了
//                ToastUtils.showShortCenter("导航键显示了");
//                navigationBarShow();
//            }
//        }
//    };
//
//    protected abstract void navigationBarHide();
//
//    protected abstract void navigationBarShow();

//    public static boolean checkDeviceHasNavigationBar(Context context) {
//        boolean hasNavigationBar = false;
//        try {
//            Resources rs = context.getResources();
//            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
//            if (id > 0) {
//                hasNavigationBar = rs.getBoolean(id);
//            }
//            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
//            Method m = systemPropertiesClass.getMethod("get", String.class);
//            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
//            if ("1".equals(navBarOverride)) {
//                hasNavigationBar = false;
//            } else if ("0".equals(navBarOverride)) {
//                hasNavigationBar = true;
//            }
//        } catch (Exception e) {
//
//        }
//        return hasNavigationBar;
//    }
}


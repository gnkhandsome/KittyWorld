package com.kitty.kitty_base.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.kitty.kitty_base.http.http.bus.event.EventBusCarrier;
import com.kitty.kitty_base.utils.ActivityContainer;
import com.kitty.kitty_base.utils.LoadingDialogUtils;
import com.kitty.kitty_base.utils.StatusBarUtil;
import com.kitty.kitty_base.utils.StatusBarUtils;
import com.kitty.kitty_base.views.zloading.ZLoadingDialog;
import com.kitty.kitty_base.views.zloading.Z_TYPE;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.websocket.SocketListener;
import com.kitty.websocket.response.ErrorResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;

import butterknife.ButterKnife;

public abstract class BaseDialogActivity extends FragmentActivity implements SocketListener {

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
    }

    protected void init(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        steepStatusBar();
        addToContainer();
        initWidow();
        EventBus.getDefault().register(this);
        if (null != SocketManager.getWebSocketManager()) {
            SocketManager.getWebSocketManager().addListener(this);
        }
    }

    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        this.startActivity(new Intent(BaseDialogActivity.this, clz));
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
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
        Log.i("onConnectFailed", e.getMessage());
    }

    @Override
    public void onDisconnect() {
        Log.i("onDisconnect", "onDisconnect");
    }

    @Override
    public void onSendDataError(ErrorResponse errorResponse) {
        Log.i("onSendDataError", errorResponse.toString());
    }

    @Override
    public <T> void onMessage(String message, T data) {
        Log.i("onMessage", message);
    }

    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        Log.i("onMessage", "onMessage");
    }

    @Override
    public void onPing(Framedata framedata) {
        Log.i("onPing", "onPing");
    }

    @Override
    public void onPong(Framedata framedata) {
        Log.i("onPong", "onPong");
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
}

package com.kitty.kitty_base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.kitty.kitty_base.http.http.bus.event.EventBusCarrier;
import com.kitty.kitty_base.utils.LoadingDialogUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.zloading.ZLoadingDialog;
import com.kitty.kitty_base.views.zloading.Z_TYPE;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.websocket.SocketListener;
import com.kitty.websocket.response.ErrorResponse;
import com.kitty.websocket.util.LogUtil;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @创建 HaiJia
 * @时间 2017/3/6 9:24
 * @描述 BaseFragment
 */

public abstract class BaseFragment extends Fragment implements SocketListener {

    private Unbinder unbinder;
    public Activity activity;
    protected final String TAG = this.getClass().getSimpleName();

    private boolean VisibleToUser;
    private ZLoadingDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    protected abstract int getContentViewLayoutID();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (null != SocketManager.getWebSocketManager()) {
            SocketManager.getWebSocketManager().addListener(this);
        }
        EventBus.getDefault().register(this);
        initData();
        iniListener();
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            MobclickAgent.onPageStart(TAG); //统计页面("MainScreen"为页面名称，可自定义)
        } catch (Exception e) {
        }
        if (getUserVisibleHint()) {
            onUserVisibleResume();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        try {
            MobclickAgent.onPageEnd(TAG);
        } catch (Exception e) {
        }
    }

    protected void onUserVisibleResume() {

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        VisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            onUserVisible();
        } else {
            onUserInVisible();
        }
    }

    protected void onUserInVisible() {
    }

    protected abstract void onUserVisible();

    protected abstract void initData();

    protected abstract void iniListener();

    @Override
    public void onDestroy() {
        DestroyViewAndThing();
        super.onDestroy();
//        try {
//            RefWatcher refWatcher = Utils.mRefWatcher;
//            refWatcher.watch(this);
//        } catch (Exception e) {
//        }
    }

    protected void DestroyViewAndThing() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (null != SocketManager.getWebSocketManager()) {
            SocketManager.getWebSocketManager().removeListener(this);
        }
        EventBus.getDefault().unregister(this);
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
                dialog = new ZLoadingDialog(getContext());
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
                dialog = new ZLoadingDialog(getContext());
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
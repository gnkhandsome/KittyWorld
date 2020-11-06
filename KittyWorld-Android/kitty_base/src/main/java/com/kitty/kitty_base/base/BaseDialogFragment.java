package com.kitty.kitty_base.base;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.gyf.barlibrary.ImmersionBar;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.http.http.bus.event.EventBusCarrier;
import com.kitty.kitty_base.utils.LoadingDialogUtils;
import com.kitty.kitty_base.views.zloading.ZLoadingDialog;
import com.kitty.kitty_base.views.zloading.Z_TYPE;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.SocketListener;
import com.kitty.websocket.response.ErrorResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 弹窗基类
 *
 * @author hoolai
 * @date 2017/7/22
 */

public abstract class BaseDialogFragment extends DialogFragment implements SocketListener {
    private View mainView;
    private Unbinder unbinder;
    protected BaseDialogFragment.IOnClickListener iOnClickListener;
    protected IDismissListener iDismissListener;
    private ZLoadingDialog dialog;

    public Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != SocketManager.getWebSocketManager()) {
            SocketManager.getWebSocketManager().addListener(this);
        }
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(isCancelableTouchOutSide());
        Objects.requireNonNull(getDialog().getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        backPressForbid(needForbidBackPress());
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        if (!autoShowNavitation()) {
            // 去除一开始的导航栏显示
            // see https://stackoverflow.com/questions/22794049/how-do-i-maintain-the-immersive-mode-in-dialogs
            getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            getDialog().getWindow().getDecorView().setSystemUiVisibility(getActivity().getWindow().getDecorView().getSystemUiVisibility());
            getDialog().setOnShowListener(dialog -> {
                // Clear the not focusable flag from the window
                try {
                    getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Update the WindowManager with the new attributes (no nicer way I know of to do this)..
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                try {
                    assert wm != null;
                    wm.updateViewLayout(getDialog().getWindow().getDecorView(), getDialog().getWindow().getAttributes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        mainView = inflater.inflate(getPopUpLayoutId(), container);
        mainView.setOnSystemUiVisibilityChangeListener(i -> {
            if (!autoShowNavitation()) {
                hideSystemUI(mainView);
            }
        });
        unbinder = ButterKnife.bind(this, mainView);
        initData();
        return mainView;
    }
    // 是否需要全屏
    protected boolean needFullScreen() {
        return false;
    }
    // 是否需要沉浸式
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    /**
     * 是否需要自动隐藏导航栏
     *
     * @return
     */
    protected boolean autoShowNavitation() {
        return false;
    }

    /**
     * 是否需要禁掉返回键
     *
     * @return 是否需要禁用返回
     */
    protected abstract boolean needForbidBackPress();

    /**
     * 点击外部区域弹窗是否消失
     *
     * @return 点击外部区域弹窗是否消失
     */
    protected abstract boolean isCancelableTouchOutSide();

    /**
     * 获取弹窗布局Id
     *
     * @return 获取弹窗布局Id
     */
    protected abstract int getPopUpLayoutId();

    /**
     * 初始化弹窗数据
     */
    protected void initData() {
    }

    @Override
    public void dismiss() {
        try {
            getDialog().dismiss();
            if (null != iDismissListener) {
                iDismissListener.dismissed();
            }
        } catch (NullPointerException e) {
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleEvent(EventBusCarrier busCarrier) {

    }

    /**
     * 返回键事件
     * param: needForbid 是否需要禁掉返回键
     */
    private void backPressForbid(boolean needForbid) {
        if (!needForbid) {
            return;
        }
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
            return false;
        });
    }

    // 沉浸式状态栏可重写设置其他方法
    protected void initImmersionBar() {
        ImmersionBar.with(mActivity).init();
        ImmersionBar.with(this).init();
    }

    private void hideSystemUI(View view) {
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (needFullScreen()) {
            Dialog dialog = getDialog();
            if (dialog != null) {
                //在5.0以下的版本会出现白色背景边框，若在5.0以上设置则会造成文字部分的背景也变成透明
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    //目前只有这两个dialog会出现边框
                    if (dialog instanceof ProgressDialog || dialog instanceof DatePickerDialog) {
                        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                }
                Window window = getDialog().getWindow();
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                window.setLayout(width, height);
                WindowManager.LayoutParams windowParams = window.getAttributes();
                windowParams.dimAmount = 0.0f;
                window.setAttributes(windowParams);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (null != SocketManager.getWebSocketManager()) {
            SocketManager.getWebSocketManager().removeListener(this);
        }
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (RuntimeException e) {
            // ignore
        }
    }


    public void setIOnClickListener(BaseDialogFragment.IOnClickListener iOnClickListener) {
        this.iOnClickListener = iOnClickListener;
    }

    public void setOnDismissListener(IDismissListener onDismissListener) {
        this.iDismissListener = onDismissListener;
    }


    public interface IOnClickListener {
        void onDismissClick();

        void onConfirmClick();
    }

    public interface IDismissListener {
        void dismissed();
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
                dialog = new ZLoadingDialog(mActivity);
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
                dialog = new ZLoadingDialog(mActivity);
                LoadingDialogUtils.showIndeterminateProgressDialog(dialog, customText, Z_TYPE.SINGLE_CIRCLE);
            } else {
                LoadingDialogUtils.showIndeterminateProgressDialog(dialog, customText, Z_TYPE.SINGLE_CIRCLE);
            }
        } catch (Exception e) {
        }
    }

    public void dismissDialog() {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
        } catch (Exception e) {
        }
    }
}

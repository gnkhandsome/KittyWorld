package com.kitty.kitty_base.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

import com.kitty.websocket.SocketListener;
import com.kitty.websocket.response.ErrorResponse;

import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;

import butterknife.ButterKnife;

/**
 * dialog基类
 * created by Jiang on 2020/5/28 09:42
 */
public abstract class BaseDialog extends Dialog implements SocketListener {
    public BaseDialog(@NonNull Context context) {
        super(context);
        init();
        initData();
        initListener();
    }




    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentId());
        ButterKnife.bind(this);
    }


    protected abstract int getContentId();
    protected abstract void initData();
    protected abstract void initListener();

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnectFailed(Throwable e) {

    }

    @Override
    public void onDisconnect() {

    }

    @Override
    public void onSendDataError(ErrorResponse errorResponse) {

    }

    @Override
    public <T> void onMessage(String message, T data) {

    }

    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {

    }

    @Override
    public void onPing(Framedata framedata) {

    }

    @Override
    public void onPong(Framedata framedata) {

    }
}

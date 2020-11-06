package com.kitty.kitty_base.ws;

import com.kitty.websocket.SocketListener;
import com.kitty.websocket.response.ErrorResponse;

import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;

public abstract class SocketAdapterListener implements SocketListener {

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

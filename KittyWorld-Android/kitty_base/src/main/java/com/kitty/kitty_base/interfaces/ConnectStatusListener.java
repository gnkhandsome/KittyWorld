package com.kitty.kitty_base.interfaces;

import org.java_websocket.handshake.ServerHandshake;

import java.nio.ByteBuffer;

public interface ConnectStatusListener {

    void onOpen(ServerHandshake serverHandshake);

    void onMessage(String s);

    void onMessage(ByteBuffer bytes);

    void onClose(int i, String s, boolean b);

    void onError(Exception e);
}

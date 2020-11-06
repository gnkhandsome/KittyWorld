package com.kitty.kitty_base.ws.utils;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.kitty.kitty_base.BuildConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.api.UrlContentInstant;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.UiUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.ws.AppResponseDispatcher;
import com.kitty.websocket.WebSocketHandler;
import com.kitty.websocket.WebSocketManager;
import com.kitty.websocket.WebSocketSetting;
import com.kitty.websocket.util.LogUtil;
import com.kitty.websocket.util.PermissionUtil;

import java.util.concurrent.atomic.AtomicInteger;

import kitty_protos.MessageOuterClass;


public class SocketManager {

    private static AtomicInteger mnCallId = new AtomicInteger(1);

    public static WebSocketManager getWebSocketManager() {
        return WebSocketHandler.getDefault();
    }

    public static void init(String uid) {
        try {
            WebSocketSetting setting = new WebSocketSetting();
//            setting.setConnectUrl(BuildConfig.WS_URL + "?uid=" + uid);
            setting.setConnectUrl(UrlContentInstant.newInstance().getWSUrl() + "?uid=" + uid);
            setting.setConnectTimeout(10 * 1000);
            setting.setConnectionLostTimeout(0);
            setting.setReconnectFrequency(1);
            setting.setResponseProcessDispatcher(new AppResponseDispatcher());
            setting.setProcessDataOnBackground(true);
            setting.setReconnectWithNetworkChanged(true);
            WebSocketHandler.init(setting);
        } catch (Exception e) {
        }
    }

    public static void send(MessageOuterClass.Message.Builder message) {
        message.setRequireId(mnCallId.getAndIncrement());
        WebSocketManager webSocketManager = getWebSocketManager();
        if (null != webSocketManager) {
            webSocketManager.send(message.build().toByteArray());
        } else {
            String uid = SPUtils.getString(Utils.getContext(), SPConfig.UID);
            if (!TextUtils.isEmpty(uid)) {
                SocketManager.init(uid);
            }
        }
    }


    public static void reconnect() {
        try {
            WebSocketManager webSocketManager = getWebSocketManager();
            webSocketManager.reconnect();
        } catch (Exception e) {
        }
    }

    public static void disConnect(boolean reset) {
        try {
            WebSocketManager webSocketManager = getWebSocketManager();
            if (reset) {
                WebSocketHandler.reset();
                if (null != webSocketManager) {
                    webSocketManager.destroy();
                }
            } else {
                if (null != webSocketManager) {
                    webSocketManager.disConnect();
                }
            }
        } catch (Exception e) {
        }
    }
}

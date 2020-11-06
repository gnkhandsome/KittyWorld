package com.kitty.websocket;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.kitty.websocket.dispatcher.ResponseProcessEngine;
import com.kitty.websocket.util.LogImpl;
import com.kitty.websocket.util.LogUtil;
import com.kitty.websocket.util.Logable;
import com.kitty.websocket.util.PermissionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket 用户控制句柄
 * <p>
 * Created by ZhangKe on 2018/12/29.
 */
public class WebSocketHandler {

    private final static String TAG = "WebSocketHandler";

    /**
     * 消息发送引擎
     */
    private static WebSocketEngine webSocketEngine;
    /**
     * 消息处理引擎
     */
    private static ResponseProcessEngine responseProcessEngine;
    /**
     * 默认的 WebSocket 连接
     */
    private static WebSocketManager defaultWebSocket;

    /**
     * 对 {@link #mWebSocketMap} 操作时的锁
     */
    private static final Object WS_MAP_BLOCK = new HashMap<>();

    /**
     * 通过 Map 存储 WSM 对象，以此支持多个连接
     */
    private static Map<String, WebSocketManager> mWebSocketMap;

    private static Logable mLog;

    /**
     * 初始化默认的 WebSocket 连接
     *
     * @param setting 该连接的相关设置参数
     */
    public static WebSocketManager init(WebSocketSetting setting) {
        if (defaultWebSocket == null) {
            synchronized (WebSocketHandler.class) {
                if (webSocketEngine == null) {
                    webSocketEngine = new WebSocketEngine();
                }
                if (responseProcessEngine == null) {
                    responseProcessEngine = new ResponseProcessEngine();
                }
                if (defaultWebSocket == null) {
                    defaultWebSocket = new WebSocketManager(setting,
                            webSocketEngine,
                            responseProcessEngine);
                }
            }
        } else {
            LogUtil.e(TAG, "Default WebSocketManager exists!do not start again!");
        }
        return defaultWebSocket;
    }


    public static void reset() {
        if (null != webSocketEngine) {
            webSocketEngine = null;
        }
        if (null != responseProcessEngine) {
            responseProcessEngine = null;
        }
        if (null != defaultWebSocket) {
            defaultWebSocket = null;
        }
    }

    /**
     * 通过唯一标识符新建一个 WebSocket 连接
     *
     * @param key     该 WebSocketManager 的唯一标识符，
     *                后面需要通过这个 key 来获取到对应的 WebSocketManager
     * @param setting 该连接的相关设置参数
     */
    public static WebSocketManager initGeneralWebSocket(String key, WebSocketSetting setting) {
        checkEngineNullAndInit();
        checkWebSocketMapNullAndInit();
        synchronized (WS_MAP_BLOCK) {
            if (mWebSocketMap.containsKey(key)) {
                LogUtil.e(TAG, "WebSocketManager exists!do not start again!");
                return mWebSocketMap.get(key);
            } else {
                WebSocketManager wsm = new WebSocketManager(setting,
                        webSocketEngine,
                        responseProcessEngine);
                mWebSocketMap.put(key, wsm);
                return wsm;
            }
        }
    }

    /**
     * 获取默认的 WebSocket 连接，
     * 调用此方法之前需要先调用 {@link #init(WebSocketSetting)} 方法初始化
     *
     * @return 返回一个 {@link WebSocketManager} 实例
     */
    public static WebSocketManager getDefault() {
        return defaultWebSocket;
    }

    /**
     * 获取 WebSocketManager 对象
     *
     * @param key 该 WebSocketManager 的 key
     * @return 可能为空，代表该 WebSocketManager 对象不存在或已移除
     */
    public static WebSocketManager getWebSocket(String key) {
        checkWebSocketMapNullAndInit();
        if (mWebSocketMap.containsKey(key)) {
            return mWebSocketMap.get(key);
        } else {
            return null;
        }
    }

    /**
     * 获取所有 WebSocketManager（defaultWebSocketManager 除外）
     */
    public static Map<String, WebSocketManager> getAllWebSocket() {
        checkWebSocketMapNullAndInit();
        return mWebSocketMap;
    }

    /**
     * 移除一个 WebSocketManager 对象
     *
     * @param key 该 WebSocketManager 的 key
     * @return 返回移除的 WebSocketManager，可能为空
     */
    public static WebSocketManager removeWebSocket(String key) {
        checkWebSocketMapNullAndInit();
        if (mWebSocketMap.containsKey(key)) {
            WebSocketManager removed = mWebSocketMap.get(key);
            synchronized (WS_MAP_BLOCK) {
                mWebSocketMap.remove(key);
            }
            return removed;
        } else {
            return null;
        }
    }


    /**
     * 初始化引擎
     */
    private static void checkEngineNullAndInit() {
        if (webSocketEngine == null || responseProcessEngine == null) {
            synchronized (WebSocketHandler.class) {
                if (webSocketEngine == null) {
                    webSocketEngine = new WebSocketEngine();
                }
                if (responseProcessEngine == null) {
                    responseProcessEngine = new ResponseProcessEngine();
                }
            }
        }
    }

    /**
     * 初始化 mWebSocketMap
     */
    private static void checkWebSocketMapNullAndInit() {
        if (mWebSocketMap == null) {
            synchronized (WS_MAP_BLOCK) {
                if (mWebSocketMap == null) {
                    mWebSocketMap = new HashMap<>();
                }
            }
        }
    }

    /**
     * 设置打印日志实现类，设置完成后内部运行日志会通过设置的实现类打印。
     * 需实现 {@link Logable} 接口
     */
    public static void setLogable(Logable logable) {
        mLog = logable;
    }

    public static Logable getLogable() {
        if (mLog == null) {
            mLog = new LogImpl();
        }
        return mLog;
    }

}

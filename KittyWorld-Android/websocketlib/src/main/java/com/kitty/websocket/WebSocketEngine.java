package com.kitty.websocket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.kitty.websocket.request.Request;
import com.kitty.websocket.response.ErrorResponse;
import com.kitty.websocket.util.LogUtil;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 使用操作线程发送数据
 * <p>
 * Created by ZhangKe on 2019/3/29.
 */
public class WebSocketEngine {

    private static final String TAG = "WSWebSocketEngine";

    private static OptionHandler mHandler;

    private OptionThread mOptionThread;

    WebSocketEngine() {
        mOptionThread = new OptionThread();
        mOptionThread.start();
    }

    void sendRequest(WebSocketWrapper webSocket,
                     Request request,
                     SocketWrapperListener listener) {
        if (mHandler == null) {
            listener.onSendDataError(request,
                    ErrorResponse.ERROR_UN_INIT,
                    null);
        } else {
            ReRunnable runnable = ReRunnable.obtain();
            runnable.type = 0;
            runnable.request = request;
            runnable.webSocketWrapper = webSocket;
            mHandler.post(runnable);
        }
    }

    void connect(WebSocketWrapper webSocket,
                 SocketWrapperListener listener) {
        if (mHandler == null) {
            Looper.prepare();
            mHandler = new OptionHandler();
            Looper.loop();
            ReRunnable runnable = ReRunnable.obtain();
            runnable.type = 1;
            runnable.webSocketWrapper = webSocket;
            mHandler.post(runnable);
//            LogUtil.e("WSWrapper", "mOptionThread.mHandler == null");
//            listener.onConnectFailed(new Exception("WebSocketEngine not start!"));
        } else {
            LogUtil.e("WSWrapper", "connect");
            ReRunnable runnable = ReRunnable.obtain();
            runnable.type = 1;
            runnable.webSocketWrapper = webSocket;
            mHandler.post(runnable);
        }
    }

    void disConnect(WebSocketWrapper webSocket,
                    SocketWrapperListener listener) {
        if (mHandler != null) {
            ReRunnable runnable = ReRunnable.obtain();
            runnable.type = 2;
            runnable.webSocketWrapper = webSocket;
            mHandler.post(runnable);
        } else {
            LogUtil.e(TAG, "WebSocketEngine not start!");
        }
    }

    void destroyWebSocket(WebSocketWrapper webSocket) {
        if (mHandler != null) {
            ReRunnable runnable = ReRunnable.obtain();
            runnable.type = 3;
            runnable.webSocketWrapper = webSocket;
            mHandler.post(runnable);
        } else {
            LogUtil.e(TAG, "WebSocketEngine not start!");
        }
    }

    public void destroy() {
        if (mOptionThread != null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(OptionHandler.QUIT);
            }
        }
    }

    private class OptionThread extends Thread {

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            mHandler = new OptionHandler();
            Looper.loop();
        }
    }

    private static class OptionHandler extends Handler {

        private static final int QUIT = 1;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    private static class ReRunnable implements Runnable {

        private static Queue<ReRunnable> POOL = new ArrayDeque<>(1);

        static ReRunnable obtain() {
            ReRunnable runnable = POOL.poll();
            if (runnable == null) {
                runnable = new ReRunnable();
            }
            return runnable;
        }

        /**
         * 0-发送数据；
         * 1-连接；
         * 2-断开连接；
         * 3-销毁 WebSocketWrapper 对象。
         */
        private int type;
        private WebSocketWrapper webSocketWrapper;
        private Request request;

        @Override
        public void run() {
            try {
                if (webSocketWrapper == null) return;
                if (type == 0 && request == null) return;
                if (type == 0) {
                    webSocketWrapper.send(request);
                    LogUtil.e("WSWrapper", "发送数据");
                } else if (type == 1) {
                    LogUtil.e("WSWrapper", "reconnect11");
                    webSocketWrapper.reconnect();
                } else if (type == 2) {
                    webSocketWrapper.disConnect();
                } else if (type == 3) {
                    LogUtil.e("WSWrapper", "销毁 WebSocketWrapper 对象");
                    webSocketWrapper.destroy();
                }
            } finally {
                webSocketWrapper = null;
                request = null;
                release();
            }
        }

        void release() {
            POOL.offer(this);
        }
    }
}

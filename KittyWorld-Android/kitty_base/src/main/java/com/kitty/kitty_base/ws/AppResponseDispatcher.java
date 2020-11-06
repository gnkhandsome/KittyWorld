package com.kitty.kitty_base.ws;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.protobuf.InvalidProtocolBufferException;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.model.UserLoginModel;
import com.kitty.kitty_base.router.RouterActivityPath;
import com.kitty.kitty_base.utils.ActivityContainer;
import com.kitty.kitty_base.utils.AlertDialogUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.websocket.SimpleDispatcher;
import com.kitty.websocket.dispatcher.ResponseDelivery;
import com.kitty.websocket.response.ErrorResponse;
import com.kitty.websocket.response.Response;
import com.kitty.websocket.response.ResponseFactory;
import com.kitty.websocket.util.LogUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import kitty_protos.MessageOuterClass;
import kitty_protos.S2COnUserLoginSync;
import kitty_protos.S2CStopServer;
import kitty_protos.StopServerOuterClass;
import kitty_protos.UserKittyInfoOuterClass;

public class AppResponseDispatcher extends SimpleDispatcher {


    /**
     * INVALID_PROTOCOL 码错误
     */
    public static final int INVALID_PROTOCOL = 11;


    @Override
    public void onMessage(String message, ResponseDelivery delivery) {

    }

    @Override
    public void onMessage(ByteBuffer byteBuffer, ResponseDelivery delivery) {
        try {
            MessageOuterClass.Message message = MessageOuterClass.Message.parseFrom(byteBuffer);
            delivery.onMessage(byteBuffer, message);
            if (message.getMessageId() == MessageOuterClass.Message.S2CONUSERLOGINSYNC_FIELD_NUMBER) {
                UserLoginModel userLoginModel = new UserLoginModel();
                List<Integer> kittyInfos = new ArrayList<>();
                S2COnUserLoginSync.S2C_OnUserLoginSync s2C_onUserLoginSync = message.getS2COnUserLoginSync();
                userLoginModel.hourBonusCount = s2C_onUserLoginSync.getHourBonusCount();
                userLoginModel.luckyWheelCount = s2C_onUserLoginSync.getLuckyWheelCount();
                userLoginModel.adRemainNum = s2C_onUserLoginSync.getAdRemainNum();
                userLoginModel.offlineTime = s2C_onUserLoginSync.getOfflineTime();
                userLoginModel.lastHourBonusRefreshTime = s2C_onUserLoginSync.getLastHourBonusRefreshTime();
                userLoginModel.lastHourBonusTime = s2C_onUserLoginSync.getLastHourBonusTime();
                userLoginModel.redPacket = s2C_onUserLoginSync.getRedPacket();
                userLoginModel.todayDividend = s2C_onUserLoginSync.getTodayDividend();
                for (UserKittyInfoOuterClass.KittyInfo kittyInfo : s2C_onUserLoginSync.getKittiesList()) {
                    kittyInfos.add(kittyInfo.getLevel());
                }
                List<Boolean> unlocks = new ArrayList<>(s2C_onUserLoginSync.getUnlockList());
                userLoginModel.unlockList = unlocks;
                userLoginModel.kittyInfos = kittyInfos;
                SPUtils.putInt(Utils.getContext(), SPConfig.AD_REMAIN_COUNT, s2C_onUserLoginSync.getAdRemainNum());
                if (s2C_onUserLoginSync.getTodayDividend() > 0) {
                    SPUtils.putInt(Utils.getContext(), SPConfig.TODAY_DIVIDE, s2C_onUserLoginSync.getTodayDividend());
                }
                SPUtils.put(Utils.getContext(), SPConfig.USER_LOGIN_MODEL, userLoginModel);
            } else if (message.getMessageId() == MessageOuterClass.Message.S2CSTOPSERVER_FIELD_NUMBER) {
                try {
                    S2CStopServer.S2C_StopServer s2C_stopServer = message.getS2CStopServer();
                    if (s2C_stopServer.getCode() == 0) {
                        SPUtils.clear();
                        ActivityContainer.finishAllActivity(true);
                        ARouter.getInstance().build(RouterActivityPath.ACTIVITY_LOGIN_PATH).navigation();
                    }
                } catch (Exception e) {
                }
            }
        } catch (InvalidProtocolBufferException e) {
            ErrorResponse errorResponse = ResponseFactory.createErrorResponse();
            Response<ByteBuffer> byteBufferResponse = ResponseFactory.createByteBufferResponse();
            byteBufferResponse.setResponseData(byteBuffer);
            errorResponse.setResponseData(byteBufferResponse);
            errorResponse.setErrorCode(INVALID_PROTOCOL);
            errorResponse.setCause(e);
            onSendDataError(errorResponse, delivery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 统一处理错误信息，
     * 界面上可使用 ErrorResponse#getDescription() 来当做提示语
     */
    @Override
    public void onSendDataError(ErrorResponse error, ResponseDelivery delivery) {
        switch (error.getErrorCode()) {
            case ErrorResponse.ERROR_NO_CONNECT:
                error.setDescription("网络错误");
                break;
            case ErrorResponse.ERROR_UN_INIT:
                error.setDescription("连接未初始化");
                break;
            case ErrorResponse.ERROR_UNKNOWN:
                error.setDescription("未知错误");
                break;
            case INVALID_PROTOCOL:
                error.setDescription("协议无效");
                break;
        }
        LogUtil.i("onSendDataError", error.toString());
        delivery.onSendDataError(error);
    }
}

package com.kitty.kitty_base.http;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.model.StopServerModel;
import com.kitty.kitty_base.http.api.TestUrlApi;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.AlipaySignModel;
import com.kitty.kitty_base.model.FriendProfitModel;
import com.kitty.kitty_base.model.KittyMoneyModel;
import com.kitty.kitty_base.model.MapModel;
import com.kitty.kitty_base.model.MessageModel;
import com.kitty.kitty_base.model.MyInviteModel;
import com.kitty.kitty_base.model.TaskModel;
import com.kitty.kitty_base.model.UpdateInfo;
import com.kitty.kitty_base.model.WithDrawDataModel;
import com.kitty.kitty_base.router.RouterActivityPath;
import com.kitty.kitty_base.http.api.WxUrlApi;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.http.callback.BaseObserver;
import com.kitty.kitty_base.http.http.HttpMethods;
import com.kitty.kitty_base.model.TokenModel;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.websocket.util.LogUtil;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author ningkang.guo
 */
public class HttpUtils {


    /**
     * postRegisterId
     */
    public static void postRegisterId(String token) {
        try {
            String registerId = JPushInterface.getRegistrationID(Utils.getContext());
            JSONObject requestData = new JSONObject();
            requestData.put("platform", "Android");
            requestData.put("registration_id", registerId);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().postRegisterId(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                }
            });
        } catch (Exception e) {
        }
    }


    /**
     * postWxCode
     */
    public static void postWxCode(String code) {
        try {
            //配置url测试正式
            JSONObject requestData = new JSONObject();
            requestData.put("code", code);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse<TokenModel>> observable = WxUrlApi.getApiBaseService().sendCodeToWX(requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<TokenModel>>() {

                @Override
                protected void onBaseNext(BaseResponse<TokenModel> data) {
                    if (null != data && null != data.data) {
                        SPUtils.putString(Utils.getContext(), SPConfig.TOKEN, data.data.getAccess_token());
                        HttpUtils.postRegisterId(data.data.getAccess_token());
                        getAccountInfo(data.data.getAccess_token(), new IResponse<UserProfile>() {
                            @Override
                            public void onSuccess(BaseResponse<UserProfile> data) {
                                if (null != data && null != data.data) {
                                    SocketManager.init(data.data.getUuid());
                                    SPUtils.putLong(Utils.getContext(), SPConfig.USER_ID, data.data.getId());
                                    SPUtils.putString(Utils.getContext(), SPConfig.UID, data.data.getUuid());
                                    ARouter.getInstance().build(RouterActivityPath.ACTIVITY_MAIN_PATH).navigation();
                                } else {
                                    ToastUtils.showLong("获取用户信息失败");
                                }
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                ToastUtils.showLong("获取用户信息失败");
                            }
                        });

                    } else {
                        ToastUtils.showLong("获取用户信息失败");
                    }
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    ToastUtils.showLong("获取用户信息失败");
                }
            });
        } catch (Exception e) {
            ToastUtils.showShort(e.getMessage());
            LogUtil.i("Exception", e.getMessage());
        }
    }


    /**
     * postWxCode
     */
    public static void getAccountInfo(String token, IResponse<UserProfile> iResponse) {
        try {
            Observable<BaseResponse<UserProfile>> observable = WxUrlApi.getApiBaseService().getAccountInfo(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<UserProfile>>() {
                @Override
                protected void onBaseNext(BaseResponse<UserProfile> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * postWxCode
     */
    public static void getAccountInfo(IResponse<UserProfile> iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse<UserProfile>> observable = WxUrlApi.getApiBaseService().getAccountInfo(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<UserProfile>>() {
                @Override
                protected void onBaseNext(BaseResponse<UserProfile> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * postWxCode
     */
    public static void getFriendProfitInfo(IResponse<FriendProfitModel> iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse<FriendProfitModel>> observable = WxUrlApi.getApiBaseService().getFriendsProfitInfo(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<FriendProfitModel>>() {
                @Override
                protected void onBaseNext(BaseResponse<FriendProfitModel> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    /**
     * postWxCode
     */
    public static void setUserInfo(String wxString, String qqCode, IResponse iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject requestData = new JSONObject();
            requestData.put("wechat", wxString);
            requestData.put("qq", qqCode);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().setUserInfo(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void submitInviterCode(long inviteId, IResponse iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject requestData = new JSONObject();
            requestData.put("invite_id", inviteId);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().setInviter(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }

    public static void setNickName(String nickName, IResponse iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject requestData = new JSONObject();
            requestData.put("nickname", nickName);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().setNickName(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void setPrivacy(String privacy, IResponse iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject requestData = new JSONObject();
            requestData.put("privacy", privacy);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().setPrivacy(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void getMyInvite(IResponse<MyInviteModel> iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse<MyInviteModel>> observable = WxUrlApi.getApiBaseService().getMyInvite(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<MyInviteModel>>() {
                @Override
                protected void onBaseNext(BaseResponse<MyInviteModel> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }

    public static void getMessages(IResponse<MessageModel> iResponse, int page, int limit) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse<MessageModel>> observable = WxUrlApi.getApiBaseService().getMessages(page, limit, token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<MessageModel>>() {
                @Override
                protected void onBaseNext(BaseResponse<MessageModel> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }

    public static void readMessage(IResponse<BaseResponse> iResponse, int message_id) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject requestData = new JSONObject();
            requestData.put("message_id", message_id);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().readMessage(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void getTaskModel(IResponse<TaskModel> iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse<TaskModel>> observable = WxUrlApi.getApiBaseService().getTaskModel(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<TaskModel>>() {
                @Override
                protected void onBaseNext(BaseResponse<TaskModel> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void logOut(IResponse iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().logOut(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }

    public static void signTask(IResponse iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().signTask(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }

    public static void getMapInfo(IResponse<MapModel> iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse<MapModel>> observable = WxUrlApi.getApiBaseService().getMapInfo(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<MapModel>>() {
                @Override
                protected void onBaseNext(BaseResponse<MapModel> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void getMapGift(IResponse iResponse, int giftId, int giftIndex) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject requestData = new JSONObject();
            requestData.put("gift_id", giftId);
            requestData.put("gift_index", giftIndex);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().getMapGift(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void receiveTask(IResponse iResponse, String task) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject requestData = new JSONObject();
            requestData.put("task", task);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().receiveTask(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void receiveLevelTask(IResponse iResponse, int id) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject requestData = new JSONObject();
            requestData.put("id", id);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().receiveLevelTask(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void getAliPaySign(IResponse<AlipaySignModel> iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse<AlipaySignModel>> observable = WxUrlApi.getApiBaseService().getAliPaySign(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<AlipaySignModel>>() {
                @Override
                protected void onBaseNext(BaseResponse<AlipaySignModel> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }

    public static void loginAlipay(IResponse iResponse, String user_id, String auth_code) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject requestData = new JSONObject();
            requestData.put("user_id", user_id);
            requestData.put("auth_code", auth_code);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().loginAlipay(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }

    public static void withDraw(IResponse iResponse, int amount) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject requestData = new JSONObject();
            requestData.put("amount", amount);
            String reqData1 = requestData.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqData1);
            Observable<BaseResponse> observable = WxUrlApi.getApiBaseService().withDraw(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void getAssetCenterInfo(IResponse<WithDrawDataModel> iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse<WithDrawDataModel>> observable = WxUrlApi.getApiBaseService().getAssetCenterInfo(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<WithDrawDataModel>>() {
                @Override
                protected void onBaseNext(BaseResponse<WithDrawDataModel> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }

    public static void getUpdateInfo(IResponse<UpdateInfo> iResponse) {
        try {
            Observable<BaseResponse<UpdateInfo>> observable = WxUrlApi.getApiBaseService().getUpdateInfo(2001, "Android");
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<UpdateInfo>>() {
                @Override
                protected void onBaseNext(BaseResponse<UpdateInfo> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void setVideoOneKey(IResponse iResponse, long userid, String rewardName) {
        try {
            Observable<BaseResponse> observable = TestUrlApi.getApiBaseService().setVideoOneKey(userid,
                    "3657f32e30114044ac28ab9183575e96", rewardName, "9a24ed9834d2d66e4b46de409e120ce165a4d99a2b4cfe8d5ce3c29e61d113f9");
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse>() {
                @Override
                protected void onBaseNext(BaseResponse data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }

    public static void getKittyMoney(IResponse<KittyMoneyModel> iResponse) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            Observable<BaseResponse<KittyMoneyModel>> observable = WxUrlApi.getApiBaseService().getKittyMoney(token);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<KittyMoneyModel>>() {
                @Override
                protected void onBaseNext(BaseResponse<KittyMoneyModel> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }


    public static void getStopServer(IResponse<StopServerModel> iResponse) {
        try {
            Observable<BaseResponse<StopServerModel>> observable = WxUrlApi.getApiBaseService().getStopServer();
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<StopServerModel>>() {
                @Override
                protected void onBaseNext(BaseResponse<StopServerModel> data) {
                    iResponse.onSuccess(data);
                }

                @Override
                protected void onBaseError(Throwable t) {
                    super.onBaseError(t);
                    iResponse.onError(t);
                }
            });
        } catch (Exception e) {
            iResponse.onError(e);
        }
    }
}

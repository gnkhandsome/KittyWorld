package com.kitty.kitty_base.http.apiserver;


import com.kitty.kitty_base.model.StopServerModel;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.model.AlipaySignModel;
import com.kitty.kitty_base.model.FriendProfitModel;
import com.kitty.kitty_base.model.KittyMoneyModel;
import com.kitty.kitty_base.model.MapModel;
import com.kitty.kitty_base.model.MessageModel;
import com.kitty.kitty_base.model.MyInviteModel;
import com.kitty.kitty_base.model.TaskModel;
import com.kitty.kitty_base.model.TokenModel;
import com.kitty.kitty_base.model.UpdateInfo;
import com.kitty.kitty_base.model.UploadModel;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.model.WithDrawDataModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Api  接口
 *
 * @author ningkang
 */

public interface ApiServer {

    /**
     * get currency rate
     */
    @POST("login/wechat")
    Observable<BaseResponse<TokenModel>> sendCodeToWX(@Body RequestBody requestBody);


    /**
     * get currency rate
     */
    @GET("user/profile")
    Observable<BaseResponse<UserProfile>> getAccountInfo(@Query("token") String token);

    /**
     * get friend info
     */
    @GET("user/friends")
    Observable<BaseResponse<FriendProfitModel>> getFriendsProfitInfo(@Query("token") String token);

    /**
     * sett user info
     */
    @POST("user/social")
    Observable<BaseResponse> setUserInfo(@Query("token") String token, @Body RequestBody requestBody);

    /**
     * sett user info
     */
    @POST("user/set_inviter")
    Observable<BaseResponse> setInviter(@Query("token") String token, @Body RequestBody requestBody);

    /**
     * sett user info
     */
    @POST("user/nickname")
    Observable<BaseResponse> setNickName(@Query("token") String token, @Body RequestBody requestBody);

    /**
     * sett user info
     */
    @POST("user/privacy")
    Observable<BaseResponse> setPrivacy(@Query("token") String token, @Body RequestBody requestBody);

    /**
     * sett user info
     */
    @GET("user/logout")
    Observable<BaseResponse> logOut(@Query("token") String token);

    /**
     * H
     */
    @POST("avatar/upload")
    Observable<BaseResponse<UploadModel>> uploadFile(@Query("token") String token, @Body RequestBody requestBody);

    /**
     * getMyInvite
     */
    @GET("me/invitation")
    Observable<BaseResponse<MyInviteModel>> getMyInvite(@Query("token") String token);

    /**
     * getMessages
     *
     * @param page
     * @param limit
     * @param token
     * @return
     */
    @GET("messages")
    Observable<BaseResponse<MessageModel>> getMessages(@Query("page") int page, @Query("limit") int limit, @Query("token") String token);

    @POST("messages/read")
    Observable<BaseResponse> readMessage(@Query("token") String token, @Body RequestBody requestBody);

    @GET("tasks")
    Observable<BaseResponse<TaskModel>> getTaskModel(@Query("token") String token);

    @POST("tasks/sign")
    Observable<BaseResponse> signTask(@Query("token") String token);

    @POST("tasks/receive")
    Observable<BaseResponse> receiveTask(@Query("token") String token, @Body RequestBody requestBody);

    @POST("tasks/level/receive")
    Observable<BaseResponse> receiveLevelTask(@Query("token") String token, @Body RequestBody requestBody);

    @GET("map/mapinfo")
    Observable<BaseResponse<MapModel>> getMapInfo(@Query("token") String token);

    @POST("map/gift")
    Observable<BaseResponse> getMapGift(@Query("token") String token, @Body RequestBody requestBody);

    @GET("alipay/sign")
    Observable<BaseResponse<AlipaySignModel>> getAliPaySign(@Query("token") String token);

    @POST("login/alipay")
    Observable<BaseResponse> loginAlipay(@Query("token") String token, @Body RequestBody requestBody);

    @POST("alipay/withdraw")
    Observable<BaseResponse> withDraw(@Query("token") String token, @Body RequestBody requestBody);

    @GET("withdraw/stage")
    Observable<BaseResponse<WithDrawDataModel>> getAssetCenterInfo(@Query("token") String token);

    @GET("pangolin/callback/test")
    Observable<BaseResponse> setVideoOneKey(@Query("user_id") long user_id, @Query("trans_id") String trans_id, @Query("reward_name") String reward_name, @Query("sign") String sign);

    @GET("version")
    Observable<BaseResponse<UpdateInfo>> getUpdateInfo(@Query("channel") long channel, @Query("platform") String platform);

    @GET("user/money_kitty")
    Observable<BaseResponse<KittyMoneyModel>> getKittyMoney(@Query("token") String token);

    /**
     * 游客登陆
     *
     * @return
     */
    @POST("login/guest")
    Observable<BaseResponse<TokenModel>> loginTemperary(@Body RequestBody requestBody);

    @GET("announcement")
    Observable<BaseResponse<StopServerModel>> getStopServer();


    /**
     * 游客登陆
     *
     * @return
     */
    @POST("user/extra")
    Observable<BaseResponse> postRegisterId(@Query("token") String token,@Body RequestBody requestBody);
}

package com.kitty.kitty_base.http.api;

import com.kitty.kitty_base.BuildConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.apiserver.ApiServer;
import com.kitty.kitty_base.http.convert.CustomGsonConverterFactory;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.websocket.util.LogUtil;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * retrofit 设置Base64转换器
 *
 * @author ningkang
 */
public class WxUrlApi {

    private static ApiServer apiServer;

    public static ApiServer getApiBaseService() {
        if (apiServer == null) {
            synchronized (WxUrlApi.class) {
                if (apiServer == null) {
                    new WxUrlApi();
                }
            }
        }
        return apiServer;
    }


    private WxUrlApi() {

        Retrofit baseUrlRetrofit = new Retrofit.Builder()
//                .baseUrl(BuildConfig.WX_URL)
                .baseUrl(UrlContentInstant.newInstance().getWXUrl())
                .client(BaseApi.getClient())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiServer = baseUrlRetrofit.create(ApiServer.class);
    }
}

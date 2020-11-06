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

public class TestUrlApi {


    private static ApiServer apiServer;

    public static ApiServer getApiBaseService() {
        if (apiServer == null) {
            synchronized (TestUrlApi.class) {
                if (apiServer == null) {
                    new TestUrlApi();
                }
            }
        }
        return apiServer;
    }


    private TestUrlApi() {
        String url = SPUtils.getString(Utils.getContext(), SPConfig.URL_TYPE);
        LogUtil.d("测试url路径1==",url);
        Retrofit baseUrlRetrofit = new Retrofit.Builder()
//                .baseUrl(BuildConfig.API_BASE)
                .baseUrl(UrlContentInstant.newInstance().getBaseUrl())
                .client(BaseApi.getClient())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiServer = baseUrlRetrofit.create(ApiServer.class);
    }


}

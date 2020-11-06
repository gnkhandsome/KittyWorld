package com.kitty.kitty_base.http.api;

import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.Utils;

/**
 * url路径配置
 * created by Jiang on 2020/5/26 17:11
 */
public class UrlContentInstant {
    
    public static final String BASE_URL = "http://kitty.dev.cocosbcx.net/api/";//基础url

    public static final String WX_URL = "https://kittyworld.cn/api/api/v1/";//测试url
    public static final String WS_URL = "wss://kittyworld.cn/ws/game/login";//
    public static final String INVITE_URL = "https://kittyworld.cn/invite/wechat?user_id=";//
    public static final String WEB_URL = "https://kmc.mykittyworld.cn/";
    //  帮助中心\排行榜\怎么玩\邀请记录\图鉴
    public static final String WEB_OTHER_URL = "https://mkw.mykittyworld.cn/";


    public static final String TEST_WX_URL = "http://kitty.dev.cocosbcx.net/api/api/v1/";
    public static final String TEST_WS_URL = "ws://kitty.dev.cocosbcx.net/ws/game/login";
    public static final String TEST_INVITE_URL = "http://kitty.dev.cocosbcx.net/invite/wechat?user_id=";
    public static final String TEST_WEB_URL = "http://mkitty.dev.cocosbcx.net/";


    public static final String TEST_NET = "test_net";//测试环境
    public static final String RELEASE_NET = "release_net";//正式环境

    private UrlContentInstant() {
    }

    private static volatile UrlContentInstant instant;

    public static UrlContentInstant newInstance() {

        if (instant == null) {
            synchronized (UrlContentInstant.class) {
                if (instant == null) {
                    instant = new UrlContentInstant();
                }
            }
        }

        return instant;
    }

    /**
     * 判断是否是测试环境
     *
     * @return
     */
    public boolean isTest() {
        String type = SPUtils.getString(Utils.getContext(), SPConfig.URL_TYPE);
        return type.equals(TEST_NET);
    }

    /**
     * 获取基础url
     *
     * @return
     */
    public String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * 获取wxURL
     *
     * @return
     */
    public String getWXUrl() {
        if (isTest()) {
            return TEST_WX_URL;
        } else {
            return WX_URL;
        }
    }

    /**
     * 获取ws
     *
     * @return
     */
    public String getWSUrl() {
        if (isTest()) {
            return TEST_WS_URL;
        } else {
            return WS_URL;
        }
    }

    /**
     * 获取INVITE_URL
     *
     * @return
     */
    public String getInvite_url() {
        if (isTest()) {
            return TEST_INVITE_URL;
        } else {
            return INVITE_URL;
        }
    }

    /**
     * 获取WEB_URL
     *
     * @return
     */
    public String getWeb_url() {
        if (isTest()) {
            return TEST_WEB_URL;
        } else {
            return WEB_URL;
        }
    }

    /**
     * 获取WEB_OTHER_URL
     *
     * @return
     */
    public String getWebOther_url() {
        if (isTest()) {
            return TEST_WEB_URL;
        } else {
            return WEB_OTHER_URL;
        }
    }
}

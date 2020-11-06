package com.kitty.kitty_base.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.google.gson.reflect.TypeToken;
import com.kitty.kitty_ads.union_test.toutiao.config.TTAdManagerHolder;
import com.kitty.kitty_base.BuildConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.api.UrlContentInstant;
import com.kitty.kitty_base.interfaces.IAdsLoaded;
import com.kitty.kitty_base.model.AdsSettingModel;
import com.kitty.kitty_base.model.RewardModel;
import com.kitty.kitty_base.utils.singleton.GsonSingleInstance;

import java.util.HashMap;
import java.util.List;

public class AdviseUtil {

    private static boolean mHasShowDownloadActive = false;
    private static TTAdNative mTTAdNative;
    private static TTRewardVideoAd mttRewardVideoAd;

    private static void init(Context context) {
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        ttAdManager.requestPermissionIfNecessary(context);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(context);
    }

    private static void loadAd(String codeId, final String adsType, int orientation) {
        long userID = SPUtils.getLong(Utils.getContext(), SPConfig.USER_ID, 0L);
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot;
//        if (true) {
//            //个性化模板广告需要传入期望广告view的宽、高，单位dp，
        adSlot = new AdSlot.Builder()
//                    .setCodeId("945093101")
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setRewardName(adsType) //奖励的名称
                //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                .setExpressViewAcceptedSize(500, 500)
                .setUserID(String.valueOf(userID))//用户id,必传参数
                .setMediaExtra("android")
                .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
//        } else {
        //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
//        adSlot = new AdSlot.Builder()
//                .setCodeId(codeId)
//                .setSupportDeepLink(true)
//                .setRewardName(adsType) //奖励的名称
//                .setUserID(String.valueOf(userID))//用户id,必传参数
//                .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
//                .build();
//        }
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {

            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                mttRewardVideoAd = ad;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                    }

                    @Override
                    public void onAdVideoBarClick() {
                    }

                    @Override
                    public void onAdClose() {
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                    }

                    @Override
                    public void onVideoError() {
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                    }

                    @Override
                    public void onSkippedVideo() {
                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {

                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadPaused===totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFailed==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFinished==totalBytes=" + totalBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        Log.d("DML", "onInstalled==" + ",fileName=" + fileName + ",appName=" + appName);
                    }
                });
            }
        });
    }

    private static void show(IAdsLoaded iAdsLoaded) {
        if (mttRewardVideoAd != null) {
            iAdsLoaded.onLoaded();
        }
    }

    public static void playRewardVedio(Activity activity, String adType, IAdsLoaded iAdsLoaded) {
        HashMap<String, AdsSettingModel> stringAdsSettingModelHashMap = FormDataUtil.getAdsTypeSetting();
        AdsSettingModel adsSettingModel = stringAdsSettingModelHashMap.get(adType);
        RewardModel<Double> rewardModel = FormDataUtil.getRewardModelMap().get("get_reward_interval");
        Double time = rewardModel.getContent();
        long lastTime = SPUtils.getLong(activity, SPConfig.LAST_VEDIO_TIME, 0);
        if ((System.currentTimeMillis() - lastTime) < time * 1000) {
            ToastUtils.showLong("歇一歇，再等" + (int) (30 - (System.currentTimeMillis() - lastTime) / 1000) + "秒");
            return;
        }
        // 消耗广告次数
        if (adsSettingModel.getExpend_times() > 0) {
            int adTimes = SPUtils.getInt(Utils.getContext(), SPConfig.AD_REMAIN_COUNT, 0);
            if (adTimes <= 0) {
                ToastUtils.showLong("广告次数不足");
                return;
            }
            AdviseUtil.init(activity);
//            AdviseUtil.loadAd(BuildConfig.IS_TEST_ENV ? "945159480" : "945159480", adType, TTAdConstant.VERTICAL);
            AdviseUtil.loadAd(UrlContentInstant.newInstance().isTest() ? "945159480" : "945093101", adType, TTAdConstant.VERTICAL);
            AdviseUtil.show(() -> {
                iAdsLoaded.onLoaded();
                mttRewardVideoAd.showRewardVideoAd(activity);
                mttRewardVideoAd = null;
            });
            // 不消耗广告次数
        } else {
            //945159480 ：测试
            //945093101 : 正式
            AdviseUtil.init(activity);
//            AdviseUtil.loadAd(BuildConfig.IS_TEST_ENV ? "945159480" : "945159480", adType, TTAdConstant.VERTICAL);
            AdviseUtil.loadAd(UrlContentInstant.newInstance().isTest() ? "945159480" : "945093101", adType, TTAdConstant.VERTICAL);
            AdviseUtil.show(() -> {
                iAdsLoaded.onLoaded();
                mttRewardVideoAd.showRewardVideoAd(activity);
                mttRewardVideoAd = null;
            });
        }
    }

}

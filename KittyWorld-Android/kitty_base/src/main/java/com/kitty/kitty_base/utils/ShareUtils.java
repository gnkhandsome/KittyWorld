package com.kitty.kitty_base.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.interfaces.OnShareResultListener;
import com.kitty.kitty_base.model.ShareInfo;
import com.kitty.kitty_base.utils.singleton.MainHandler;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import lombok.Getter;
import lombok.Setter;

public class ShareUtils {


    @Setter
    @Getter
    OnShareResultListener shareResultListener;

    /**
     * 分享链接
     * param WebUrl:连接地址
     * param title:标题
     * param description:描述
     * param imageUrl:网络缩略图
     * param imageID:本地缩略图
     */
    @SuppressLint("CheckResult")
    public void shareWeb(final Activity activity, String WebUrl, String title, String description, String imageUrl, SHARE_MEDIA platform) {
        try {
            RxPermissions rxPermissions = new RxPermissions((FragmentActivity) activity);
            rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(permission -> {
                if (permission.granted) {
                    UMWeb web = new UMWeb(WebUrl);
                    web.setTitle(title);
                    web.setDescription(description);
                    if (TextUtils.isEmpty(imageUrl)) {
                        web.setThumb(new UMImage(activity, R.mipmap.icon));
                    } else {
                        web.setThumb(new UMImage(activity, imageUrl));
                    }
                    new ShareAction(activity)
                            .setPlatform(platform)
                            .withMedia(web)
                            .setCallback(getUMShareListener(activity))
                            .share();
                } else if (permission.shouldShowRequestPermissionRationale) {
                } else {
                    SettingUtil.startAppSettings(activity);
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * 分享图片
     * param platform:分享平台
     * param shareBitmap:分享的图片内容
     */
    public static void shareImage(final Activity activity, SHARE_MEDIA platform, Bitmap shareBitmap) {
        if (shareBitmap == null) {
            ToastUtils.showLong("图片资源为空");
            return;
        }
        UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                ToastUtils.showLong(" 分享成功");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                if (!checkPlatformInstall(activity, share_media)) {
                    return;
                }
                ToastUtils.showLong(" 分享失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                ToastUtils.showLong(" 分享取消");
            }
        };
        UMImage image = new UMImage(activity, shareBitmap);
        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(image)
                .setCallback(umShareListener)
                .share();
    }


    /**
     * 获取分享结果回调
     */
    private UMShareListener getUMShareListener(Activity activity) {

        return new UMShareListener() {

            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onResult(final SHARE_MEDIA share_media) {
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        if (getShareResultListener() != null) {
                            getShareResultListener().onShareSuccess();
                        }
                        if ("WEIXIN_FAVORITE".equals(share_media.name())) {
                            ToastUtils.showLong("收藏成功！");
                        } else {
                            ToastUtils.showLong("分享成功！");
                        }
                    }
                });
            }

            @Override
            public void onError(final SHARE_MEDIA share_media, final Throwable throwable) {
                if (!checkPlatformInstall(activity, share_media)) {
                    return;
                }
                ToastUtils.showLong("分享失败！");
            }

            @Override
            public void onCancel(final SHARE_MEDIA share_media) {
                ToastUtils.showLong("分享取消！");
            }
        };
    }


    /**
     * 是否安装某平台应用
     *
     * @param activity 校验的activity
     * @param platform 平台
     * @return 是否安装某平台应用
     */
    public static boolean checkPlatformInstall(Activity activity, SHARE_MEDIA platform) {
        UMShareAPI umShareAPI = UMShareAPI.get(activity);
        if (!umShareAPI.isInstall(activity, platform)) {
            ToastUtils.showLong("没有安装应用！");
            return false;
        }
        return true;
    }


    public void shareTo(String platform, ShareInfo shareInfo, Activity activity, OnShareResultListener onShareResultListener) {
        if (shareInfo != null) {
            SHARE_MEDIA shareMedia = SHARE_MEDIA.valueOf(platform);
            setShareResultListener(onShareResultListener);
            shareWeb(activity, shareInfo.getLink(), shareInfo.getTitle(), shareInfo.getDesc(), shareInfo.getImgUrl(), shareMedia);
        }
    }
}

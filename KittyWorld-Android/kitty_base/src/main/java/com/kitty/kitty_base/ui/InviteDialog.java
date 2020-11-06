package com.kitty.kitty_base.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.kitty.kitty_base.BuildConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.api.UrlContentInstant;
import com.kitty.kitty_base.model.FriendProfitModel;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.SettingUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.compress.BitmapMergeUtil;
import com.kitty.kitty_base.zxing.utils.QRCodeHelper;
import com.kitty.kitty_res.R2;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class InviteDialog extends BaseDialogFragment {
    @BindView(R2.id.iv_qr_icon)
    ImageView ivQrIcon;

    private Bitmap qrBitmap;

    @Override
    protected void initData() {
        try {
            Bundle bunndle = getArguments();
            FriendProfitModel friendProfitModel = (FriendProfitModel) bunndle.getSerializable(IntentConfig.FRIENDPROFITMODEL);
            if (null != friendProfitModel) {
                refreshQrCodeImage(friendProfitModel);
            }
        } catch (Exception e) {
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            dismiss();
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(Utils.getContext(), Utils.getString(R.string.share_success), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(Utils.getContext(), Utils.getString(R.string.share_failed), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(Utils.getContext(), Utils.getString(R.string.share_cancel), Toast.LENGTH_LONG).show();
        }
    };

    @SuppressLint("CheckResult")
    @OnClick(R2.id.tv_share)
    public void share() {
        try {
            RxPermissions rxPermissions = new RxPermissions(InviteDialog.this);
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) {
                    if (aBoolean) {
                        if (null != qrBitmap) {
                            UMImage image = new UMImage(Utils.getContext(), qrBitmap);//网络图片
                            new ShareAction(getActivity())
                                    .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                                    .withMedia(image)//分享内容
                                    .setCallback(shareListener)//回调监听器
                                    .share();
                        }
                    } else {
                        SettingUtil.startAppSettings(getActivity());
                    }
                }
            });
        } catch (Exception e) {
        }
    }


    private void refreshQrCodeImage(FriendProfitModel friendProfitModel) {
        try {
            long user_id = SPUtils.getLong(Utils.getContext(), SPConfig.USER_ID, 0L);
//            String message = BuildConfig.INVITE_URL + user_id;
            String message = UrlContentInstant.newInstance().getInvite_url() + user_id;
            qrBitmap = QRCodeHelper.generateBitmap(message, Utils.dip2px(199), Utils.dip2px(199));
            ivQrIcon.setImageBitmap(qrBitmap);
        } catch (Exception e) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BitmapMergeUtil.recycleBitmap(qrBitmap);
    }

    @Override
    protected boolean needForbidBackPress() {
        return false;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return true;
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.invite_dialog_fragment;
    }
}

package com.kitty.kitty_base.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kitty.kitty_base.BuildConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseCenterTipDialog;
import com.kitty.kitty_base.base.BaseDialogActivity;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.api.UrlContentInstant;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.TipContentModel;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.utils.GlideUtils;
import com.kitty.kitty_base.utils.SettingUtil;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.compress.BitmapMergeUtil;
import com.kitty.kitty_base.utils.singleton.MainHandler;
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
import me.jessyan.autosize.utils.ScreenUtils;


public class PostShareActivity extends BaseDialogActivity {

    @BindView(R2.id.tv_my_invite_code)
    TextView tvMyInviteCode;
    @BindView(R2.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R2.id.tv_my_nickname)
    TextView tvMyNickname;
    @BindView(R2.id.iv_ard_code)
    ImageView ivArdCode;
    @BindView(R2.id.iv_post)
    ImageView ivPost;
    @BindView(R2.id.rl_post_share)
    FrameLayout rlPostShare;
    @BindView(R2.id.fl_share_info)
    FrameLayout fl_share_info;
    private Bitmap bitmap;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_post_share;
    }

    @Override
    protected void initData() {
        int[] ints = ScreenUtils.getRawScreenSize(getApplicationContext());
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fl_share_info.getLayoutParams();
        int backWidth = ints[0] - Utils.dip2px(120);
        int backHeight = (backWidth * 2001) / 1125;
        layoutParams.height = backHeight;
        layoutParams.width = backWidth;
        fl_share_info.setLayoutParams(layoutParams);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.post_back, options);
        Bitmap postIm = BitmapMergeUtil.toRoundCornerImage(bitmap, Utils.dip2px(16));
        ivPost.setImageBitmap(postIm);

        refreshQrCodeImage();
    }

    private void refreshQrCodeImage() {
        try {
            HttpUtils.getAccountInfo(new IResponse<UserProfile>() {
                @Override
                public void onSuccess(BaseResponse<UserProfile> baseResponse) {
                    if (null != baseResponse && null != baseResponse.data) {
                        UserProfile userProfile = baseResponse.data;
//                        String message = BuildConfig.INVITE_URL + userProfile.getId();
                        String message = UrlContentInstant.newInstance().getInvite_url() + userProfile.getId();
                        Bitmap qrBitmap = QRCodeHelper.generateBitmap(message, Utils.dip2px(55), Utils.dip2px(55));
                        ivArdCode.setImageBitmap(qrBitmap);
                        tvMyNickname.setText(Utils.getString(R.string.nick_name_desc_default) + userProfile.getNickname());
                        tvMyInviteCode.setText(Utils.getString(R.string.my_invite_code) + userProfile.getId());
                        GlideUtils.loadCirclePic(getApplicationContext(), userProfile.getAvatar(), ivUserIcon);
                        fl_share_info.setVisibility(View.VISIBLE);
                        MainHandler.getInstance().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bitmap = BitmapMergeUtil.getViewBitmap(rlPostShare);
                            }
                        }, 1000);

                    }
                }

                @Override
                public void onError(Throwable throwable) {

                }
            });
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.iv_close)
    public void close(View view) {
        finish();
    }

    @SuppressLint("CheckResult")
    @OnClick(R2.id.ll_share_to_wx)
    public void toWx(View view) {
        try {
            RxPermissions rxPermissions = new RxPermissions(PostShareActivity.this);
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) {
                    if (aBoolean) {
                        if (null != bitmap) {
                            UMImage image = new UMImage(Utils.getContext(), bitmap);//网络图片
                            new ShareAction(PostShareActivity.this)
                                    .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                                    .withMedia(image)//分享内容
                                    .setCallback(shareListener)//回调监听器
                                    .share();
                        } else {
                            ToastUtils.showShortCenter("bitmap is null");
                        }
                    } else {
                        SettingUtil.startAppSettings(PostShareActivity.this);
                    }
                }
            });
        } catch (Exception e) {
            ToastUtils.showShortCenter("error");
        }
    }

    @SuppressLint("CheckResult")
    @OnClick(R2.id.ll_share_to_wx_circle)
    public void toWxCircle(View view) {
        try {
            RxPermissions rxPermissions = new RxPermissions(PostShareActivity.this);
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) {
                    if (aBoolean) {
                        if (null != bitmap) {
                            UMImage image = new UMImage(Utils.getContext(), bitmap);//网络图片
                            new ShareAction(PostShareActivity.this)
                                    .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                                    .withMedia(image)//分享内容
                                    .setCallback(shareListener)//回调监听器
                                    .share();
                        }
                    } else {
                        SettingUtil.startAppSettings(PostShareActivity.this);
                    }
                }
            });
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
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(Utils.getContext(), Utils.getString(com.kitty.kitty_base.R.string.share_success), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(Utils.getContext(), Utils.getString(com.kitty.kitty_base.R.string.share_failed), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(Utils.getContext(), Utils.getString(com.kitty.kitty_base.R.string.share_cancel), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapMergeUtil.recycleBitmap(bitmap);
    }
}

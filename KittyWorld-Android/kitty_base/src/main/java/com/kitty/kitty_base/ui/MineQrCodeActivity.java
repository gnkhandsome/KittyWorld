package com.kitty.kitty_base.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.BuildConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.api.UrlContentInstant;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.utils.GlideUtils;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.compress.BitmapMergeUtil;
import com.kitty.kitty_base.zxing.utils.QRCodeHelper;
import com.kitty.kitty_res.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class MineQrCodeActivity extends BaseActivity {


    @BindView(R2.id.iv_qr_code)
    ImageView iv_qr_code;
    @BindView(R2.id.tv_nick_name)
    TextView tv_nick_name;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_user_icon)
    ImageView ivUserIcon;
    private Bitmap qrBitmap;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_qr_code;
    }


    @Override
    protected void initData() {
        try {
            tvTitle.setText(R.string.my_qr_code);
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            UserProfile userProfile = (UserProfile) bundle.getSerializable(IntentConfig.USERPROFILE);
            if (null == userProfile) {
                loadMineData();
            } else {
                tv_nick_name.setText(userProfile.getNickname());
                GlideUtils.loadCirclePic(MineQrCodeActivity.this, userProfile.getAvatar(), ivUserIcon);
            }
        } catch (Exception e) {
        }
        refreshQrCodeImage();
    }


    private void loadMineData() {
        String token = SPUtils.getString(MineQrCodeActivity.this, SPConfig.TOKEN);
        HttpUtils.getAccountInfo(token, new IResponse<UserProfile>() {
            @Override
            public void onSuccess(BaseResponse<UserProfile> baseResponse) {
                try {
                    if (null != baseResponse && null != baseResponse.data) {
                        tv_nick_name.setText(baseResponse.data.getNickname());
                        GlideUtils.loadCirclePic(MineQrCodeActivity.this, baseResponse.data.getAvatar(), ivUserIcon);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

    }


    @OnClick(R2.id.iv_back)
    public void back() {
        finish();
    }


    private void refreshQrCodeImage() {
        try {
            long user_id = SPUtils.getLong(Utils.getContext(), SPConfig.USER_ID, 0L);
//            String message = BuildConfig.INVITE_URL + user_id;
            String message = UrlContentInstant.newInstance().getInvite_url() + user_id;
            qrBitmap = QRCodeHelper.generateBitmap(message, Utils.dip2px(199), Utils.dip2px(199));
            iv_qr_code.setImageBitmap(qrBitmap);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapMergeUtil.recycleBitmap(qrBitmap);
    }
}

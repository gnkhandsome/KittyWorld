package com.kitty.kitty_base.ui;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.router.RouterActivityPath;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_res.R;
import com.kitty.kitty_res.R2;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterActivityPath.ACTIVITY_SOCIAL_INFO)
public class SocialInfoActivity extends BaseActivity {
    @BindView(R2.id.et_wx_code)
    EditText etWxCode;
    @BindView(R2.id.et_qq_code)
    EditText etQqCode;
    @BindView(R2.id.set_social_info)
    TextView setSocialInfo;
    @BindView(R2.id.tv_title)
    TextView tvTitle;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_social;
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.social_info_title);
        loadMineData();
    }

    private void loadMineData() {
        String token = SPUtils.getString(SocialInfoActivity.this, SPConfig.TOKEN);
        HttpUtils.getAccountInfo(token, new IResponse<UserProfile>() {
            @Override
            public void onSuccess(BaseResponse<UserProfile> baseResponse) {
                try {
                    if (null != baseResponse && null != baseResponse.data) {
                        if (!TextUtils.isEmpty(baseResponse.data.getWechat())) {
                            etWxCode.setText(baseResponse.data.getWechat());
                        }
                        if (!TextUtils.isEmpty(baseResponse.data.getQq())) {
                            etQqCode.setText(baseResponse.data.getQq());
                        }
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
    public void onViewClicked() {
        finish();
    }


    @OnClick(R2.id.set_social_info)
    public void seSocialInfo() {
        try {
            if (TextUtils.isEmpty(etWxCode.getText()) && TextUtils.isEmpty(etQqCode.getText())) {
                ToastUtils.showLong(R.string.set_social_info);
                return;
            }
            if (etQqCode.getText().length() < 5) {
                ToastUtils.showLong(R.string.qq_code_length);
                return;
            }
            if (etWxCode.getText().length() < 6) {
                ToastUtils.showLong(R.string.wx_code_length);
                return;
            }
            HttpUtils.setUserInfo(etWxCode.getText().toString(), etQqCode.getText().toString(), new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                    if (baseResponse.code == 0) {
                        ToastUtils.showLong(R.string.set_social_sucess);
                        finish();
                    } else {
                        ToastUtils.showLong(R.string.sett_social_failed);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    ToastUtils.showLong(R.string.sett_social_failed);
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(R.string.sett_social_failed);
        }
    }
}

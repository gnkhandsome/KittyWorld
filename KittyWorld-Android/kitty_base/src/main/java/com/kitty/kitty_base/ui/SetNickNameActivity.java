package com.kitty.kitty_base.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_res.R2;
import butterknife.BindView;
import butterknife.OnClick;

public class SetNickNameActivity extends BaseActivity {
    @BindView(R2.id.edt_nickname)
    EditText edtNickname;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvSave;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_nickname;
    }

    @Override
    protected void initData() {
        try {
            tvTitle.setText(R.string.setting_title);
            tvSave.setText(R.string.save);
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            UserProfile userProfile = (UserProfile) bundle.getSerializable(IntentConfig.USERPROFILE);
            if (null == userProfile) {
                loadMineData();
            } else {
                edtNickname.setText(userProfile.getNickname());
            }
        } catch (Exception e) {
        }
    }

    private void loadMineData() {

        String token = SPUtils.getString(SetNickNameActivity.this, SPConfig.TOKEN);
        HttpUtils.getAccountInfo(token, new IResponse<UserProfile>() {
            @Override
            public void onSuccess(BaseResponse<UserProfile> baseResponse) {
                try {
                    if (null != baseResponse && null != baseResponse.data) {
                        edtNickname.setText(baseResponse.data.getNickname());
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
    public void back(View view) {
        finish();
    }


    @OnClick(R2.id.tv_right)
    public void saveNickname(View view) {
        try {
            if (TextUtils.isEmpty(edtNickname.getText().toString())) {
                ToastUtils.showLong(R.string.empry_nick_name);
                return;
            }
            HttpUtils.setNickName(edtNickname.getText().toString(), new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                    if (baseResponse.code == 0) {
                        ToastUtils.showLong(R.string.inviter_set_success);
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
        }
    }

    @OnClick(R2.id.iv_clear)
    public void iv_clear(View view) {
        if (null != edtNickname) {
            edtNickname.clearFocus();
            edtNickname.setText(null);
        }
    }
}

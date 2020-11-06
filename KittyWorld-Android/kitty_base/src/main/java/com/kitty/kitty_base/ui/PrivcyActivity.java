package com.kitty.kitty_base.ui;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_res.R;
import com.kitty.kitty_res.R2;

import butterknife.BindView;
import butterknife.OnClick;


public class PrivcyActivity extends BaseActivity {


    @BindView(R2.id.master_visible)
    ImageView masterVisible;
    @BindView(R2.id.friend_visible)
    ImageView friendVisible;
    @BindView(R2.id.tv_title)
    TextView tvTitle;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_privcy;
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.social_info_title);
        loadMineData();
    }

    private void loadMineData() {
        String token = SPUtils.getString(PrivcyActivity.this, SPConfig.TOKEN);
        HttpUtils.getAccountInfo(token, new IResponse<UserProfile>() {
            @Override
            public void onSuccess(BaseResponse<UserProfile> baseResponse) {
                try {
                    if (null != baseResponse && null != baseResponse.data) {
                        masterVisible.setImageResource(baseResponse.data.getParent_visit() == 1 ? R.drawable.sound_switch_open_icon : R.drawable.sound_switch_close_icon);
                        friendVisible.setImageResource(baseResponse.data.getChild_visit() == 1 ? R.drawable.sound_switch_open_icon : R.drawable.sound_switch_close_icon);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    @OnClick(R2.id.master_visible)
    public void masterVisible() {
        try {
            HttpUtils.setPrivacy("parent_visit", new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                    loadMineData();
                }

                @Override
                public void onError(Throwable throwable) {

                }
            });

        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_set_other_info)
    public void llSetOtherInfo() {
        Intent intent = new Intent(PrivcyActivity.this, SocialInfoActivity.class);
        startActivity(intent);
    }


    @OnClick(R2.id.iv_back)
    public void back() {
        finish();
    }

    @OnClick(R2.id.friend_visible)
    public void friendVisible() {
        try {
            HttpUtils.setPrivacy("child_visit", new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                    loadMineData();
                }

                @Override
                public void onError(Throwable throwable) {

                }
            });
        } catch (Exception e) {
        }
    }
}

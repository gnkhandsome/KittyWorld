package com.kitty.kitty_base.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.AlipaySignModel;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.model.WithDrawModel;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.aliauth.AuthResult;
import com.kitty.kitty_res.R2;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindAlPayActivity extends BaseActivity {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    private static final int SDK_AUTH_FLAG = 1;
    @BindView(R2.id.ll_draw_author)
    LinearLayout llDrawAuthor;
    @BindView(R2.id.ll_authored)
    LinearLayout llAuthored;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_bind_alipay;
    }

    @Override
    protected void initData() {
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            UserProfile userProfile = (UserProfile) bundle.getSerializable(IntentConfig.USERPROFILE);
            if (null == userProfile) {
                loadMineData();
            } else {
                if (userProfile.getUnnamed() == 0) {
                    llDrawAuthor.setVisibility(View.INVISIBLE);
                    llAuthored.setVisibility(View.VISIBLE);
                } else {
                    llDrawAuthor.setVisibility(View.VISIBLE);
                    llAuthored.setVisibility(View.INVISIBLE);
                }
            }
            tvTitle.setText(Utils.getString(R.string.bind_alipay_author_title));
        } catch (Exception e) {
        }
    }

    private void loadMineData() {
        try {
            String token = SPUtils.getString(BindAlPayActivity.this, SPConfig.TOKEN);
            HttpUtils.getAccountInfo(token, new IResponse<UserProfile>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(BaseResponse<UserProfile> baseResponse) {
                    if (null != baseResponse && null != baseResponse.data) {
                        if (baseResponse.data.getUnnamed() == 0) {
                            llDrawAuthor.setVisibility(View.INVISIBLE);
                            llAuthored.setVisibility(View.VISIBLE);
                        } else {
                            llDrawAuthor.setVisibility(View.VISIBLE);
                            llAuthored.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                }
            });
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.tv_author_alipay)
    public void alipayAuthor(View view) {
        HttpUtils.getAliPaySign(new IResponse<AlipaySignModel>() {
            @Override
            public void onSuccess(BaseResponse<AlipaySignModel> baseResponse) {
                if (baseResponse.code == 0) {
                    auth(baseResponse.data.getSign());
                } else {
                    ToastUtils.showLong(R.string.author_sign_fail);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.showLong(R.string.author_sign_fail);
            }
        });

    }

    @OnClick(R2.id.iv_back)
    public void iv_back(View view) {
        finish();
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        HttpUtils.loginAlipay(new IResponse() {
                            @Override
                            public void onSuccess(BaseResponse baseResponse) {
                                if (baseResponse.code == 0) {
                                    ToastUtils.showLong(R.string.auth_success);
                                    loadMineData();
                                } else {
                                    ToastUtils.showLong(R.string.auth_failed);
                                }
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                ToastUtils.showLong(R.string.auth_failed);
                            }
                        }, authResult.getUser_id(), authResult.getAuthCode());
                    } else {
                        ToastUtils.showLong(R.string.auth_failed);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    /**
     * 支付宝账户授权业务示例
     *
     * @param signInfo
     */
    public void auth(String signInfo) {
        final String authInfo = signInfo;
        Runnable authRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(BindAlPayActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

}

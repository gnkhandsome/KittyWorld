package com.kitty.kitty_base.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.kitty.kitty_base.adapter.WithDrawAdapter;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.base.BaseTipDialog;
import com.kitty.kitty_base.base.WebViewActivity;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.AlipaySignModel;
import com.kitty.kitty_base.model.TipContentModel;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.model.WebViewModel;
import com.kitty.kitty_base.model.WithDrawDataModel;
import com.kitty.kitty_base.model.WithDrawModel;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.UiUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.aliauth.AuthResult;
import com.kitty.kitty_res.R;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssetCenterActivity extends BaseActivity {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_current_money)
    TextView tvCurrentMoney;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.wx_withdraw)
    TextView wxWithdraw;
    @BindView(R2.id.ll_wx_withdraw)
    LinearLayout llWxWithdraw;
    @BindView(R2.id.alipay_withdraw)
    TextView alipayWithdraw;
    @BindView(R2.id.ll_alipay_withdraw)
    LinearLayout llAlipayWithdraw;
    @BindView(R2.id.iv_wx_icon)
    ImageView ivWxIcon;
    @BindView(R2.id.iv_alipay_icon)
    ImageView ivAlipayIcon;
    @BindView(R2.id.gv_draw)
    GridView gvDraw;
    @BindView(R2.id.tv_curent_level_for_withdraw)
    TextView tvCurentLevelForWithdraw;
    @BindView(R2.id.tv_withdraw_condition)
    TextView tv_withdraw_condition;
    @BindView(R2.id.tv_withdraw_condition_title)
    TextView tv_withdraw_condition_title;
    @BindView(R2.id.with_draw)
    TextView with_draw;
    @BindView(R2.id.pb_withdraw)
    ProgressBar pbWithdraw;
    @BindView(R2.id.ll_withdraw_condition)
    LinearLayout llWithdrawCondition;
    @BindView(R2.id.tv_with_draw_fee)
    TextView tv_with_draw_fee;
    private WithDrawModel withDrawModel;
    private WithDrawAdapter withDrawAdapter;
    private String withDrawWay;
    private UserProfile userProfile;
    private static final int SDK_AUTH_FLAG = 1;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_asset_center;
    }

    @Override
    protected void initData() {
        try {
            tvTitle.setText(Utils.getString(R.string.mine_asset));
            tvRight.setText(Utils.getString(R.string.asset_deal));
            initBtn();
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            userProfile = (UserProfile) bundle.getSerializable(IntentConfig.USERPROFILE);
            if (null == userProfile) {
                loadMineData();
            } else {
                tvCurrentMoney.setText(NumberUtil.getWanDividedNumber(userProfile.getMoney(), 2).toString());
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void setListener() {
        gvDraw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    WithDrawAdapter withDrawAdapter = (WithDrawAdapter) parent.getAdapter();
                    withDrawModel = withDrawAdapter.getItem(position);
                    withDrawAdapter.setSelectedPosition(position);
                    withDrawAdapter.notifyDataSetChanged();
                    tv_with_draw_fee.setText(position == 0 && withDrawModel.amount < 200000 ? Utils.getString(R.string.with_draw_fee_new_text) : Utils.getString(R.string.with_draw_fee_text));
                    llWithdrawCondition.setVisibility(position == 0 && withDrawModel.amount < 200000 ? View.VISIBLE : View.INVISIBLE);
                    tv_withdraw_condition.setVisibility(position == 0 && withDrawModel.amount < 200000 ? View.VISIBLE : View.INVISIBLE);
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMineData();
        loadGvData();
    }

    private void loadMineData() {
        try {
            String token = SPUtils.getString(AssetCenterActivity.this, SPConfig.TOKEN);
            HttpUtils.getAccountInfo(token, new IResponse<UserProfile>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(BaseResponse<UserProfile> baseResponse) {
                    if (null != baseResponse && null != baseResponse.data) {
                        try {
                            userProfile = baseResponse.data;
                            tvCurrentMoney.setText(NumberUtil.getWanDividedNumber(baseResponse.data.getMoney(), 2).toString());
                        } catch (Exception e) {
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

    @OnClick(R2.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R2.id.with_draw)
    public void with_draw() {
        try {
            showDialogEachTime();
            if (null != withDrawModel) {
                HttpUtils.withDraw(new IResponse() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        try {
                            int code = baseResponse.code;
                            withDrawModel.way = Utils.getString(R.string.alipay);
                            if (code == 0) {
//                              withDrawModel.isFirst = withDrawModel.amount>30000;
                                ToastUtils.showShortCenter(R.string.withdraw_success);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(IntentConfig.WITHDRAW_MODEL, withDrawModel);
                                Intent intent = new Intent(AssetCenterActivity.this, WithdrawSuccessActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                withDrawModel = null;
                                loadMineData();
                                loadGvData();
                            } else if (code == 1015 || code == 1016) {
                                try {
                                    alipayAuthor();
                                } catch (Exception e) {
                                }
                            } else if (code == 1018) {
                                ToastUtils.showShortCenter(com.kitty.kitty_base.R.string.money_not_enough);
                            } else if (code == 1019) {
                                ToastUtils.showShortCenter(com.kitty.kitty_base.R.string.level_not_enough);
                            }
                            dismissDialog();
                        } catch (Exception e) {
                            ToastUtils.showShortCenter(R.string.op_failed);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.showShortCenter(R.string.op_failed);
                    }
                }, withDrawModel.amount);
            }
        } catch (Exception e) {
            ToastUtils.showShortCenter(R.string.op_failed);
        }
    }


    @OnClick(R2.id.tv_right)
    public void tvRight() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.asset_deal));
            webViewModel.setUrl(Utils.getString(R.string.asset_deal_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(AssetCenterActivity.this, WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.ll_wx_withdraw)
    public void ll_wx_withdraw(View view) {
        setWxDrawStatus();
    }

    private void setWxDrawStatus() {
        try {
            llWxWithdraw.setBackgroundResource(R.drawable.wx_draw_back_selected);
            llAlipayWithdraw.setBackgroundResource(R.drawable.alipay_draw_back_normal);
            wxWithdraw.setTextColor(Utils.getColor(R.color.white));
            alipayWithdraw.setTextColor(Utils.getColor(R.color.color_46B1FF));
            ivWxIcon.setImageResource(R.drawable.wx_draw_icon);
            ivAlipayIcon.setImageResource(R.drawable.alipay_draw_icon);
//            withDrawWay = Utils.getString(R.string.wx);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_alipay_withdraw)
    public void ll_alipay_withdraw(View view) {
        setAliPayDrawStatus();
    }

    private void setAliPayDrawStatus() {
        try {
            llWxWithdraw.setBackgroundResource(R.drawable.wx_withdraw_normal);
            llAlipayWithdraw.setBackgroundResource(R.drawable.alipay_withdraw_selected);
            wxWithdraw.setTextColor(Utils.getColor(R.color.color_43DB82));
            alipayWithdraw.setTextColor(Utils.getColor(R.color.white));
            ivWxIcon.setImageResource(R.drawable.weixindefuben);
            ivAlipayIcon.setImageResource(R.drawable.ali_withdraw_selected_icon);
//            withDrawWay = Utils.getString(R.string.alipay);
        } catch (Exception e) {
        }
    }


    private void initBtn() {
        try {
            initGv();
            loadGvData();
            llWxWithdraw.setBackgroundResource(R.drawable.wx_withdraw_normal);
            llAlipayWithdraw.setBackgroundResource(R.drawable.alipay_withdraw_selected);
            wxWithdraw.setTextColor(Utils.getColor(R.color.color_43DB82));
            alipayWithdraw.setTextColor(Utils.getColor(R.color.white));
            ivWxIcon.setImageResource(R.drawable.weixindefuben);
            ivAlipayIcon.setImageResource(R.drawable.ali_withdraw_selected_icon);
        } catch (Exception e) {
        }
    }

    private void loadGvData() {
        HttpUtils.getAssetCenterInfo(new IResponse<WithDrawDataModel>() {
            @Override
            public void onSuccess(BaseResponse<WithDrawDataModel> baseResponse) {
                try {
                    if (null != baseResponse && null != baseResponse.data) {
                        WithDrawDataModel withDrawDataModel = baseResponse.data;
                        List<Integer> integerList = withDrawDataModel.getAmount();
                        WithDrawDataModel.ConditionBean conditionBean = withDrawDataModel.getCondition();
                        llWithdrawCondition.setVisibility(null == conditionBean || !withDrawDataModel.is_new_player() ? View.INVISIBLE : View.VISIBLE);
                        tv_withdraw_condition.setVisibility(null == conditionBean || !withDrawDataModel.is_new_player() ? View.INVISIBLE : View.VISIBLE);
                        tv_with_draw_fee.setText(null == conditionBean || !withDrawDataModel.is_new_player() ? Utils.getString(R.string.with_draw_fee_text) : Utils.getString(R.string.with_draw_fee_new_text));
                        if (null != conditionBean && conditionBean.getNumber() != 0) {
                            pbWithdraw.setMax(conditionBean.getNumber());
                            pbWithdraw.setProgress(conditionBean.getCurr_number() > conditionBean.getNumber() ? conditionBean.getNumber() : conditionBean.getCurr_number());
                            tvCurentLevelForWithdraw.setText(TextUtils.equals(conditionBean.getType(), "build") ? Utils.getString(R.string.current_verise_count_subtitle) + (conditionBean.getCurr_number() > conditionBean.getNumber() ? conditionBean.getNumber() : conditionBean.getCurr_number()) + "/" + conditionBean.getNumber() : Utils.getString(R.string.curent_level_for_withdraw_tip) + (conditionBean.getCurr_number() > conditionBean.getNumber() ? conditionBean.getNumber() : conditionBean.getCurr_number()) + "/" + conditionBean.getNumber());
                            tv_withdraw_condition_title.setText(TextUtils.equals(conditionBean.getType(), "build") ? Utils.getString(R.string.current_verise_count) + conditionBean.getNumber() + Utils.getString(R.string.withdraw_condition_title_before_build) : Utils.getString(R.string.withdraw_condition_title_font) + conditionBean.getNumber() + Utils.getString(R.string.withdraw_condition_title_before));
                        }

                        List<WithDrawModel> initWithDraw = new ArrayList<>();
                        if (null == withDrawModel) {
                            withDrawModel = new WithDrawModel();
                            withDrawModel.setAmount(integerList.get(0));
                        }
                        for (int i = 0; i < integerList.size(); i++) {
                            WithDrawModel gv_draw = new WithDrawModel();
                            gv_draw.setAmount(integerList.get(i));
                            initWithDraw.add(gv_draw);
                        }
                        WithDrawAdapter withDrawAdapter = new WithDrawAdapter(initWithDraw, getApplicationContext());
                        gvDraw.setAdapter(withDrawAdapter);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }

    private void initGv() {
        try {
            List<WithDrawModel> initWithDraw = new ArrayList<>();
            int[] amouns = {
                    30000,
                    2000000,
                    5000000,
                    10000000,
                    50000000,
                    100000000
            };
            for (int i = 0; i < 6; i++) {
                WithDrawModel gv_draw = new WithDrawModel();
                gv_draw.setAmount(amouns[i]);
                initWithDraw.add(gv_draw);
            }
            withDrawAdapter = new WithDrawAdapter(initWithDraw, getApplicationContext());
            gvDraw.setAdapter(withDrawAdapter);
            gvDraw.setSelection(0);
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.tv_with_draw_fee)
    public void tv_with_draw_fee() {
        try {
            TipContentModel tipContentModel = new TipContentModel();
            tipContentModel.setTitle(Utils.getString(com.kitty.kitty_base.R.string.withdraw_tip_title));
            tipContentModel.setContent(Utils.getString(com.kitty.kitty_base.R.string.with_draw_tips));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.TIPCONENTMODEL, tipContentModel);
            BaseTipDialog baseTipDialog = new BaseTipDialog();
            baseTipDialog.setArguments(bundle);
            baseTipDialog.show(getSupportFragmentManager(), "baseTipDialog");
        } catch (Exception e) {
        }
    }


    public void alipayAuthor() {
        HttpUtils.getAliPaySign(new IResponse<AlipaySignModel>() {
            @Override
            public void onSuccess(BaseResponse<AlipaySignModel> baseResponse) {
                if (baseResponse.code == 0) {
                    auth(baseResponse.data.getSign());
                } else {
                    ToastUtils.showLong(com.kitty.kitty_base.R.string.author_sign_fail);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.showLong(com.kitty.kitty_base.R.string.author_sign_fail);
            }
        });
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
                                LogUtil.i("baseResponse", baseResponse.message);
                                LogUtil.i("baseResponse", String.valueOf(baseResponse.code));
                                try {
                                    if (baseResponse.code == 0) {
                                        ToastUtils.showLong(com.kitty.kitty_base.R.string.auth_success);
                                        if (null != withDrawModel) {
                                            showDialogEachTime();
                                            withdraw();
                                        }
                                    } else {
                                        ToastUtils.showLong(com.kitty.kitty_base.R.string.auth_failed);
                                    }
                                } catch (Exception e) {
                                }
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                ToastUtils.showLong(R.string.auth_failed);
                            }
                        }, authResult.getUser_id(), authResult.getAuthCode());
                    } else {
                        ToastUtils.showLong(com.kitty.kitty_base.R.string.auth_failed);
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
                AuthTask authTask = new AuthTask(AssetCenterActivity.this);
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

    private void withdraw() {
        try {
            HttpUtils.withDraw(new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                    try {
                        int code = baseResponse.code;
                        if (code == 0) {
                            ToastUtils.showShortCenter(com.kitty.kitty_res.R.string.withdraw_success);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(IntentConfig.WITHDRAW_MODEL, withDrawModel);
                            Intent intent = new Intent(AssetCenterActivity.this, WithdrawSuccessActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            withDrawModel = null;
                        } else if (code == 1018) {
                            ToastUtils.showShortCenter(com.kitty.kitty_base.R.string.money_not_enough);
                        } else if (code == 1019) {
                            ToastUtils.showShortCenter(com.kitty.kitty_base.R.string.level_not_enough);
                        } else {
                            ToastUtils.showShortCenter(com.kitty.kitty_res.R.string.op_failed);
                        }
                        dismissDialog();
                        finish();
                    } catch (Exception e) {
                        ToastUtils.showShortCenter(com.kitty.kitty_res.R.string.op_failed);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    ToastUtils.showShortCenter(com.kitty.kitty_res.R.string.op_failed);
                }
            }, withDrawModel.amount);
        } catch (Exception e) {
            ToastUtils.showShortCenter(com.kitty.kitty_res.R.string.op_failed);
        }
    }
}

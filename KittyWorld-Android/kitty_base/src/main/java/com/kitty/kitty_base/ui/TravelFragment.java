package com.kitty.kitty_base.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseFragment;
import com.kitty.kitty_base.base.BaseTipDialog;
import com.kitty.kitty_base.base.WebViewActivity;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.fragmentdialog.MyInviterDialog;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.FriendLevelModel;
import com.kitty.kitty_base.model.FriendLevelProfitModel;
import com.kitty.kitty_base.model.FriendProfitModel;
import com.kitty.kitty_base.model.TipContentModel;
import com.kitty.kitty_base.model.WebViewModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.GlideUtils;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class TravelFragment extends BaseFragment {
    @BindView(R2.id.tv_invite_desc)
    TextView tvInviteDesc;
    @BindView(R2.id.total_profit)
    TextView totalProfit;
    @BindView(R2.id.current_profit)
    TextView currentProfit;
    @BindView(R2.id.once_profit)
    TextView onceProfit;
    @BindView(R2.id.today_profit_total)
    TextView todayProfitTotal;
    @BindView(R2.id.profit_from_instant)
    TextView profitFromInstant;
    @BindView(R2.id.profit_from_expand)
    TextView profitFromExpand;
    @BindView(R2.id.today_profit)
    TextView todayProfit;
    @BindView(R2.id.tv_friend_count)
    TextView tvFriendCount;
    @BindView(R2.id.ll_invite_info)
    LinearLayout llInviteInfo;
    @BindView(R2.id.ll_invite_empty)
    LinearLayout llInviteEmpty;
    @BindView(R2.id.touch_inviter)
    TextView tvTouchInviter;
    @BindView(R2.id.iv_my_invite_icon)
    ImageView ivMyInviterIcon;
    @BindView(R2.id.tv_state_profit_tip)
    TextView tv_state_profit_tip;
    @BindView(R2.id.tv_unlock_part)
    TextView unlockPart;
    @BindView(R2.id.unlock_progress)
    ProgressBar unlockProgress;
    @BindView(R2.id.iv_friend_icon_three)
    ImageView iv_friend_icon_three;
    @BindView(R2.id.iv_friend_icon_two)
    ImageView iv_friend_icon_two;
    @BindView(R2.id.iv_friend_icon_one)
    ImageView iv_friend_icon_one;
    @BindView(R2.id.tv_speed_muti)
    TextView tv_speed_muti;
    @BindView(R2.id.refreshLayout)
    RefreshLayout refreshLayout;

    private FriendProfitModel friendProfitModel;

    public static TravelFragment newInstance() {

        Bundle args = new Bundle();

        TravelFragment fragment = new TravelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_travel;
    }

    @Override
    protected void onUserVisible() {
        refresh();
    }


    @Override
    protected void onUserVisibleResume() {
        refresh();
    }

    @Override
    protected void initData() {
        try {
            refresh();
            tvFriendCount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            currentProfit.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            onceProfit.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            totalProfit.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            todayProfitTotal.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            profitFromInstant.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            profitFromExpand.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    refresh();
                    refreshLayout.finishRefresh(1000);
                }
            });
            refreshLayout.setEnableLoadMore(false);
        } catch (Exception e) {
        }
    }


    private void getFriendInfo() {
        HttpUtils.getFriendProfitInfo(new IResponse<FriendProfitModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(BaseResponse<FriendProfitModel> baseResponse) {
                if (null != baseResponse && null != baseResponse.data) {
                    try {
                        friendProfitModel = baseResponse.data;
                        tvFriendCount.setText(String.valueOf(friendProfitModel.getFriend_count()));
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("当前未提现好友" + friendProfitModel.getUnnamed_count() + "人，已替您产生收入<font color=\"#FF4B5D\">").append(NumberUtil.getWanDividedNumber(friendProfitModel.getWill_income(), 2)).append("元</font>，通知好友<font color=\"#FF4B5D\">完成认证</font>即可获得收入>");
                        String htmlStr = stringBuffer.toString();
                        tvInviteDesc.setText(Html.fromHtml(htmlStr));
                        currentProfit.setText(NumberUtil.getWanDividedNumber(friendProfitModel.getFriend_income(), 2).toString());
                    } catch (Exception e) {
                    }

                    try {
                        HashMap<String, FriendLevelModel> friendLevel = CatHelperUtil.getFriendLevel();
                        FriendLevelModel friendLevelModel = friendLevel.get(String.valueOf(friendProfitModel.getFriend_stage()));
                        onceProfit.setText(String.valueOf(NumberUtil.getWanDividedNumber(friendLevelModel.getAmount())));
                        tv_state_profit_tip.setText("第" + NumberUtil.formatInteger(friendProfitModel.getFriend_stage()) + "阶段x" + friendLevelModel.getMultiplier() + "倍加速");
                        tv_speed_muti.setText(friendLevelModel.getMultiplier() + "倍加速中");
                        BigDecimal stateTotal = NumberUtil.getWanDividedNumber(friendLevelModel.getAmount());
                        BigDecimal progess = NumberUtil.getWanDividedNumber(friendProfitModel.getFriend_income()).divide(stateTotal, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_DOWN);
                        unlockPart.setText("已解锁" + progess + "%，解锁后" + stateTotal.toString() + "元现金将自动存入钱包");
                        unlockProgress.setProgress(progess.compareTo(BigDecimal.ZERO) > 0 && progess.compareTo(BigDecimal.ONE) < 0 ? 1 : progess.intValue());
                    } catch (Exception e) {
                    }

                    try {
                        totalProfit.setText(String.valueOf(NumberUtil.getWanDividedNumber(friendProfitModel.getIncome())));
                        todayProfitTotal.setText(String.valueOf(NumberUtil.getWanDividedNumber(friendProfitModel.getToday_friend_income())));
                        profitFromInstant.setText(String.valueOf(NumberUtil.getWanDividedNumber(friendProfitModel.getToday_direct_friend_income())));
                        profitFromExpand.setText(String.valueOf(NumberUtil.getWanDividedNumber(friendProfitModel.getToday_second_friend_income())));
                    } catch (Exception e) {
                    }

                    try {
                        if (friendProfitModel.getParent_info().getId() == 0) {
                            llInviteEmpty.setVisibility(View.VISIBLE);
                            llInviteInfo.setVisibility(View.GONE);
                            tvTouchInviter.setVisibility(View.INVISIBLE);
                        } else {
                            llInviteEmpty.setVisibility(View.GONE);
                            llInviteInfo.setVisibility(View.VISIBLE);
                            tvTouchInviter.setVisibility(View.VISIBLE);
                            todayProfit.setText(Html.fromHtml("他邀请了" + friendProfitModel.getParent_info().getInvite_count() + "人，累计收益" + "<font color=\"#FF4B5D\">" + NumberUtil.getWanDividedNumber(friendProfitModel.getParent_info().getIncome(), 2).toString() + "元</font>"));
                            GlideUtils.loadCirclePic(getActivity(), friendProfitModel.getParent_info().getAvatar(), ivMyInviterIcon);
                        }
                    } catch (Exception e) {
                    }

                    try {
                        if (friendProfitModel.getLatest_friends().size() > 0) {
                            for (int i = 0; i < friendProfitModel.getLatest_friends().size(); i++) {
                                FriendProfitModel.LatestFriendsBean latestFriendsBean = friendProfitModel.getLatest_friends().get(i);
                                if (i == 0) {
                                    GlideUtils.loadCirclePic(getActivity(), latestFriendsBean.getAvatar(), iv_friend_icon_one);
                                } else if (i == 1) {
                                    GlideUtils.loadCirclePic(getActivity(), latestFriendsBean.getAvatar(), iv_friend_icon_two);
                                } else if (i == 2) {
                                    GlideUtils.loadCirclePic(getActivity(), latestFriendsBean.getAvatar(), iv_friend_icon_three);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }

            }

            @Override
            public void onError(Throwable throwable) {
            }
        });

    }

    @Override
    protected void iniListener() {

    }

    @OnClick(R2.id.iv_total_profit)
    public void checkTotal() {
        try {
            TipContentModel tipContentModel = new TipContentModel();
            tipContentModel.setTitle(Utils.getString(R.string.total_profit_tips_title));
            tipContentModel.setContent(Utils.getString(R.string.total_profit_tips_content));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.TIPCONENTMODEL, tipContentModel);
            BaseTipDialog baseTipDialog = new BaseTipDialog();
            baseTipDialog.setArguments(bundle);
            baseTipDialog.show(getFragmentManager(), "baseTipDialog");
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.tv_current_profit)
    public void checkCurrentTotal() {
        try {
            TipContentModel tipContentModel = new TipContentModel();
            tipContentModel.setTitle(Utils.getString(R.string.total_friend_profit_tips_title));
            tipContentModel.setContent(Utils.getString(R.string.total_friend_profit_tips_content));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.TIPCONENTMODEL, tipContentModel);
            BaseTipDialog baseTipDialog = new BaseTipDialog();
            baseTipDialog.setArguments(bundle);
            baseTipDialog.show(getFragmentManager(), "baseTipDialog");
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.tv_state_profit_tip)
    public void stateProfitTip() {
        try {
            TipContentModel tipContentModel = new TipContentModel();
            tipContentModel.setTitle(Utils.getString(R.string.state_profit_tips_title));
            tipContentModel.setContent(Utils.getString(R.string.state_profit_tips_content));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.TIPCONENTMODEL, tipContentModel);
            BaseTipDialog baseTipDialog = new BaseTipDialog();
            baseTipDialog.setArguments(bundle);
            baseTipDialog.show(getFragmentManager(), "baseTipDialog");
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.set_invite_info)
    public void setLlInviteInfo() {
        // 设置邀请人
        SetInviteDialog setInviteDialog = new SetInviteDialog();
        setInviteDialog.show(getFragmentManager(), "setInviteDialog");
        setInviteDialog.setIUpdateListener(new SetInviteDialog.IUpdateListener() {
            @Override
            public void update() {
                refresh();
            }
        });
    }

    @OnClick(R2.id.invite_friend)
    public void invite() {
//        Intent intent = new Intent(getActivity(), PostShareActivity.class);
//        startActivity(intent);
        PostShareDialog postShareDialog = PostShareDialog.newInstance();
        postShareDialog.show(getFragmentManager(),"");
    }


    @OnClick(R2.id.rl_touch_inviter)
    public void touchInviteClick() {
        try {
            if (null != friendProfitModel) {
                FriendProfitModel.ParentInfoBean parentInfoBean = friendProfitModel.getParent_info();
                if (null != parentInfoBean && parentInfoBean.getId() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(IntentConfig.PARENTINFOBEAN, parentInfoBean);
                    MyInviterDialog myInviterDialog = new MyInviterDialog();
                    myInviterDialog.setArguments(bundle);
                    myInviterDialog.show(getFragmentManager(), "myInviterDialog");
                } else {
                    SetInviteDialog setInviteDialog = new SetInviteDialog();
                    setInviteDialog.show(getFragmentManager(), "setInviteDialog");
                    setInviteDialog.setIUpdateListener(new SetInviteDialog.IUpdateListener() {
                        @Override
                        public void update() {
                            refresh();
                        }
                    });
                }
            }
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.tv_play_rule)
    public void toPlayRule() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.travel_play_rule));
            webViewModel.setUrl(Utils.getString(R.string.travel_play_rule_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick({R2.id.ll_friend_added_profit, R2.id.ll_today_profit})
    public void friendProfit() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.friend_profit));
            webViewModel.setUrl(Utils.getString(R.string.friend_profit_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_invite_history)
    public void inviteHistory() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.invite_history));
            webViewModel.setUrl(Utils.getString(R.string.invite_history_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.rl_total_profit)
    public void rl_total_profit() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.friend_profit));
            webViewModel.setUrl(Utils.getString(R.string.friend_profit_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void refresh() {
        getFriendInfo();
    }
}

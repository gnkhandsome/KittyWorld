package com.kitty.kitty_base.fragmentdialog;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.model.DragResourceViewModel;
import com.kitty.kitty_base.ui.FortuneKittyProfitActivity;
import com.kitty.kitty_base.ui.MessageCenterActivity;
import com.kitty.kitty_base.utils.CountDownTimerUtil;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.TimeKittyTickUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 点击首页上方 猫的范围弹窗
 */
public class DividingKittyDialog extends BaseDialogFragment {

    @BindView(R2.id.tv_reward_kitty_desc)
    TextView tvRewardKittyDesc;
    @BindView(R2.id.tv_kitty_time)
    TextView tvKittyTime;
    @BindView(R2.id.to_receive)
    TextView to_receive;
    @BindView(R2.id.tv_current_divided_money)
    TextView tv_current_divided_money;
    @BindView(R2.id.ll_dividing)
    LinearLayout ll_dividing;

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            DragResourceViewModel dragResourceViewModel = (DragResourceViewModel) bundle.getSerializable(IntentConfig.DRAG_MODEL);
            String timeDesc = TimeDescUtil.getTimeKittyDesc((int) dragResourceViewModel.getExpireIn());
            tvRewardKittyDesc.setText(timeDesc);
            to_receive.setText(Html.fromHtml("38级可获得<font color=\"#FF4B5D\">永久</font>招财猫 >"));

            if (dragResourceViewModel.getExpireTime() == -1) {
                tvKittyTime.setVisibility(View.GONE);
                ll_dividing.setVisibility(View.GONE);
            } else {
                // 计时器
                tvKittyTime.setVisibility(View.VISIBLE);
                ll_dividing.setVisibility(View.VISIBLE);
                CountDownTimerUtil timeTickUtil = new CountDownTimerUtil(0, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        String time = getTimeFromInt(millisUntilFinished);
                        if (null != tvKittyTime) {
                            tvKittyTime.setText(time);
                        }
                    }

                    @Override
                    public void onFinish() {
                        dismiss();
                    }
                };
                timeTickUtil.setCountdownInterval(1000);
                timeTickUtil.setMillisInFuture(dragResourceViewModel.getExpireTime() * 1000 - System.currentTimeMillis());
                timeTickUtil.start();

                // 分红中
                float dividingMoney = (float) bundle.getFloat(IntentConfig.DIVIDING_MONEY);
                int todayDivide = SPUtils.getInt(Utils.getContext(), SPConfig.TODAY_DIVIDE, 0);
//                float divideMoney = ((dragResourceViewModel.getExpireTime() * 1000) - System.currentTimeMillis()) * todayDivide / 86400000;
                float divideMoney = (dragResourceViewModel.getExpireIn() * 1000) * todayDivide / 86400000;
                BigDecimal bigDecimal = NumberUtil.getWanDividedNumber(BigDecimal.valueOf(divideMoney));
                ValueAnimator anim = ValueAnimator.ofFloat(dividingMoney, bigDecimal.floatValue());
                anim.setDuration((dragResourceViewModel.getExpireTime() * 1000) - System.currentTimeMillis());
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float divideMoney = (float) animation.getAnimatedValue();
                        BigDecimal bigDecimal1 = new BigDecimal(divideMoney).setScale(5, RoundingMode.HALF_EVEN);
                        if (null != tv_current_divided_money) {
                            tv_current_divided_money.setText(bigDecimal1.toString() + "元");
                        }
                    }
                });
                anim.start();
            }
        } catch (Exception e) {
        }
    }


    @Override
    protected boolean needForbidBackPress() {
        return false;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return false;
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.fragment_dialog_dividing;
    }

    @OnClick(R2.id.close_dialog)
    public void close(View view) {
        dismiss();
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);
            Objects.requireNonNull(dialog.getWindow()).setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @OnClick(R2.id.to_receive)
    public void onViewClicked() {
        try {
            Intent intent = new Intent(getActivity(), FortuneKittyProfitActivity.class);
            Objects.requireNonNull(getActivity()).startActivity(intent);
        } catch (Exception e) {
        }
    }
}

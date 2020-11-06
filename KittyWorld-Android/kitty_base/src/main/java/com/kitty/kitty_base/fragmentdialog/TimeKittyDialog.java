package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.base.WebViewActivity;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.ui.FortuneKittyProfitActivity;
import com.kitty.kitty_base.ui.MessageCenterActivity;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.TimeKittyTickUtil;
import com.kitty.kitty_base.utils.TimeTickUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class TimeKittyDialog extends BaseDialogFragment {

    @BindView(R2.id.tv_reward_kitty_desc)
    TextView tvRewardKittyDesc;
    @BindView(R2.id.tv_kitty_time)
    TextView tvKittyTime;
    @BindView(R2.id.to_receive)
    TextView to_receive;


    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        RewardObject luckWheelModel = (RewardObject) bundle.getSerializable(IntentConfig.LUCK_WHEEL_MODEL);
        String timeDesc = TimeDescUtil.getTimeKittyDesc(Integer.parseInt(luckWheelModel.amount));
        TimeKittyTickUtil timeTickUtil = new TimeKittyTickUtil(tvKittyTime, Long.valueOf(luckWheelModel.amount) * 1000, 1000);
        timeTickUtil.start();
        tvRewardKittyDesc.setText(Utils.getString(R.string.goog_to_gain) + timeDesc);
        to_receive.setText(Html.fromHtml("38级可获得<font color=\"#FF4B5D\">永久</font>招财猫 >"));
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
        return R.layout.fragment_dialog_timekitty;
    }

    @OnClick(R2.id.close_dialog)
    public void close(View view) {
        dismiss();
    }

    @OnClick(R2.id.tv_benfit_confirm)
    public void confirm(View view) {
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
            dismiss();
        } catch (Exception e) {
        }
    }
}

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
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.model.DragResourceViewModel;
import com.kitty.kitty_base.ui.FortuneKittyProfitActivity;
import com.kitty.kitty_base.ui.MessageCenterActivity;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.TimeKittyTickUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.util.LogUtil;
import com.umeng.commonsdk.debug.E;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class DividedKittyDialog extends BaseDialogFragment {

    @BindView(R2.id.tv_reward_kitty_desc)
    TextView tvRewardKittyDesc;
    @BindView(R2.id.to_receive)
    TextView to_receive;


    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            DragResourceViewModel dragResourceViewModel = (DragResourceViewModel) bundle.getSerializable(IntentConfig.DRAG_MODEL);
            String timeDesc = TimeDescUtil.getTimeDesc((int) dragResourceViewModel.getExpireIn());
            int todayDivide = SPUtils.getInt(Utils.getContext(), SPConfig.TODAY_DIVIDE, 0);
            float divideMoney = (dragResourceViewModel.getExpireIn() * 1000) * todayDivide / 86400000;
            BigDecimal bigDecimal = NumberUtil.getWanDividedNumber(BigDecimal.valueOf(divideMoney));
            tvRewardKittyDesc.setText(Html.fromHtml(Utils.getString(R.string.goog_to_gain) + timeDesc + Utils.getString(R.string.ads_divide_text) + "<font color=\"#FF4B5D\">" + bigDecimal.setScale(2, RoundingMode.HALF_EVEN).toString() + "</font>" + Utils.getString(R.string.cny_unit)));
            to_receive.setText(Html.fromHtml("38级可获得<font color=\"#FF4B5D\">永久</font>招财猫 >"));
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
        return R.layout.fragment_dialog_divided;
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
            dismiss();
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.tv_benfit_confirm)
    public void confirm() {
        try {
            dismiss();
        } catch (Exception e) {
        }
    }
}

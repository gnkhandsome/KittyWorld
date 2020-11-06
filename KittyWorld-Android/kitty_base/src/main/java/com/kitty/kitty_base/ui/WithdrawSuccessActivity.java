package com.kitty.kitty_base.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kitty.kitty_res.R;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.model.WithDrawModel;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawSuccessActivity extends BaseActivity {
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_withdraw_amount)
    TextView tvWithdrawAmount;
    @BindView(R2.id.tv_withdraw_fee_amount)
    TextView tvWithdrawFeeAmount;
    @BindView(R2.id.tv_withdraw_to)
    TextView tvWithdrawTo;
    @BindView(R2.id.tv_receive_date)
    TextView tv_receive_date;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_withdraw_success;
    }

    @Override
    protected void initData() {
        tvTitle.setText(Utils.getString(R.string.withdraw));
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            WithDrawModel withDrawModel = (WithDrawModel) bundle.getSerializable(IntentConfig.WITHDRAW_MODEL);
            if (null != withDrawModel) {
                try {
                    tvWithdrawAmount.setText(String.valueOf(NumberUtil.getWithDrawWanDividedNumber(BigDecimal.valueOf(withDrawModel.amount))));
                    tvWithdrawFeeAmount.setText("￥" + (withDrawModel.amount < 200000 ? 0 : NumberUtil.getWithDrawWanDividedNumber(BigDecimal.valueOf(withDrawModel.amount)).multiply(BigDecimal.valueOf(0.03)).toString()));
                    tvWithdrawTo.setText(withDrawModel.way);
                    tv_receive_date.setVisibility(withDrawModel.amount < 200000 ? View.GONE : View.VISIBLE);
                } catch (Exception e) {
                }
            }
        }
    }

    @OnClick(R2.id.iv_back)
    public void iv_back() {
        finish();
    }

    @OnClick(R2.id.tv_complete)
    public void tv_complete() {
        finish();
    }
}

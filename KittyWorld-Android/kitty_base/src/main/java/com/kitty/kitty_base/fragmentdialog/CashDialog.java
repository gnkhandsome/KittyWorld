package com.kitty.kitty_base.fragmentdialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.ui.AssetCenterActivity;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_res.R2;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

public class CashDialog extends BaseDialogFragment {
    @BindView(R2.id.tv_pack_amount)
    TextView tvPackAmount;

    @Override
    protected boolean needForbidBackPress() {
        return true;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return false;
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.fragment_dialog_cash;
    }

    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            int amount = bundle.getInt(IntentConfig.REDPACK_AMOUNT);
            tvPackAmount.setText(NumberUtil.getWanDividedNumber(BigDecimal.valueOf(amount)).toString() + "元");
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.tv_to_asset_center)
    public void tv_to_asset_center(View view) {
        try {
            Intent intent = new Intent(getActivity(), AssetCenterActivity.class);
            startActivity(intent);
            dismiss();
        } catch (Exception e) {
        }
    }

}

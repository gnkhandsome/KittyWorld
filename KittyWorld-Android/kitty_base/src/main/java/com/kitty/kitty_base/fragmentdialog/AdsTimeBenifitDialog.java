package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.model.KittyBase;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R;
import com.kitty.kitty_res.R2;

import java.math.BigDecimal;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ningkang
 */

public class AdsTimeBenifitDialog extends BaseDialogFragment {

    @BindView(R2.id.tv_reward_value)
    TextView rewardValue;
    @BindView(R2.id.tv_bonus_count)
    TextView bonusCount;

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            RewardObject rewardObject = (RewardObject) bundle.getSerializable(IntentConfig.LUCK_WHEEL_MODEL);
            int maxLevel = SPUtils.getInt(getContext(), SPConfig.MAX_LEVEL, 1);
            KittyBase kittyBase = CatHelperUtil.getKittyBaseByLevel(maxLevel);
            rewardValue.setText(TimeDescUtil.getTimeDesc(kittyBase.getAds_rewards().multiply(BigDecimal.valueOf(rewardObject.value)).intValue()) + Utils.getString(R.string.minute_profit));
            BigDecimal bigDecimal = CatHelperUtil.getTotalOutPutAmountInteger(rewardObject.kittyLevelList).multiply(kittyBase.getAds_rewards()).multiply(BigDecimal.valueOf(rewardObject.value));
            String bonus = CatHelperUtil.getDispalyedNumber(bigDecimal.toString());
            bonusCount.setText(bonus);
            bonusCount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
        } catch (Exception e) {
        }
    }


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
        return R.layout.fragment_dialog_ads_time_benfit;
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

    @OnClick(R2.id.close_dialog)
    public void onCancelClicked() {
        dismiss();
    }

    @OnClick(R2.id.tv_benfit_confirm)
    public void onConfirmClicked() {
        dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}

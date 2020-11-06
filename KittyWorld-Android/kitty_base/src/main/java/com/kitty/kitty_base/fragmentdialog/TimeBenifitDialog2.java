package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.model.RewardTimeBonuObject;
import com.kitty.kitty_base.utils.CatHelperUtil;
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

public class TimeBenifitDialog2 extends BaseDialogFragment {

    @BindView(R2.id.tv_bonus_count)
    TextView tvBonusCount;

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            if (null != bundle) {
                RewardObject rewardObject = (RewardObject) bundle.getSerializable(IntentConfig.LUCK_WHEEL_MODEL);
                if (rewardObject.rewardType == 2) {
                    String bonus = CatHelperUtil.getDispalyedNumber(BigDecimal.valueOf(Long.valueOf(rewardObject.amount)).toString());
                    tvBonusCount.setText(bonus);
                }
            }
            tvBonusCount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
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
        return R.layout.fragment_dialog_hour_bonus;
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

    @OnClick({R2.id.close_dialog, R2.id.tv_benfit_confirm})
    public void onViewClicked(View view) {
        dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}

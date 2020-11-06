package com.kitty.kitty_base.fragmentdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class BonusDialog extends BaseDialogFragment {
    @BindView(R2.id.tv_bonus_count)
    TextView tvBonusCount;


    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        try {
            if (null != bundle) {
                RewardObject luckWheelModel = (RewardObject) bundle.getSerializable(IntentConfig.LUCK_WHEEL_MODEL);
                tvBonusCount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
                tvBonusCount.setText(CatHelperUtil.getDispalyedNumber(luckWheelModel.amount));
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
    protected int getPopUpLayoutId() {
        return R.layout.fragment_dialog_bonus;
    }

    @OnClick({R2.id.close_dialog, R2.id.tv_bonus_confirm})
    public void onViewClicked(View view) {
        dismiss();
    }
}

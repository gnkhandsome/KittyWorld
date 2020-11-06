package com.kitty.kitty_base.fragmentdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class KittyDialog extends BaseDialogFragment {

    @BindView(R2.id.iv_kitty_icon)
    ImageView ivKittyIcon;
    @BindView(R2.id.tv_kitty_level)
    TextView tvKittyLevel;
    @BindView(R2.id.tv_reward_value)
    TextView tvRewardValue;

    @Override
    protected void initData() {
        try {
        Bundle bundle = getArguments();
            if (null != bundle) {
                RewardObject rewardObject = (RewardObject) bundle.getSerializable(IntentConfig.LUCK_WHEEL_MODEL);
                ivKittyIcon.setImageResource(ImageSourceUtil.getResorceByLevel(rewardObject.rewardID));
                tvKittyLevel.setText(String.valueOf(rewardObject.rewardID));
                tvRewardValue.setText(CatHelperUtil.getKittyNameByLevel(rewardObject.rewardID));
            }
            tvKittyLevel.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            tvRewardValue.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
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
        return R.layout.fragment_dialog_kitty;
    }

    @OnClick(R2.id.close_dialog)
    public void close(View view) {
        dismiss();
    }

    @OnClick( R2.id.tv_benfit_confirm)
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
}

package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.model.CatNameModel;
import com.kitty.kitty_base.model.SpecialLevelRewardModel;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class SpecialKittyDialog extends BaseDialogFragment {


    @BindView(R2.id.iv_kitty_icon)
    ImageView ivKittyIcon;
    @BindView(R2.id.tv_kitty_name)
    TextView tvKittyName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            SpecialLevelRewardModel luckWheelModel = (SpecialLevelRewardModel) bundle.getSerializable(IntentConfig.SPECIALLEVELREWARDMODEL);
            if (null != luckWheelModel){
                ivKittyIcon.setImageResource(ImageSourceUtil.getTurnImageByName(luckWheelModel.special_kitty_icon));
                CatNameModel catNameModel = FormDataUtil.getKittyStringMap().get(luckWheelModel.special_kitty_key);
                tvKittyName.setText(catNameModel.getName_cn());
            }
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
        return R.layout.fragment_special_dialog_timekitty;
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
}

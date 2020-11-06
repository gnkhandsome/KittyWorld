package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.model.RewardModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.singleton.GsonSingleInstance;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.util.LogUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class KittyInfoDialog extends BaseDialogFragment {

    @BindView(R2.id.iv_kitty_icon)
    ImageView ivKittyIcon;
    @BindView(R2.id.tv_kitty_name)
    TextView tvKittyName;
    @BindView(R2.id.tv_online_output)
    TextView tvOnlineOutput;
    @BindView(R2.id.tv_offline_output)
    TextView tvOfflineOutput;

    @Override
    protected boolean needForbidBackPress() {
        return false;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return true;
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.fragent_dialog_kitty_info;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        try {
            tvOnlineOutput.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            tvOfflineOutput.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            if (null != bundle) {
                int animation_level = bundle.getInt(IntentConfig.ANIMATION_LEVEL);
                RewardModel<Double> rewardModel = FormDataUtil.getRewardModelMap().get("offline_rate");
                Double offline_rate = rewardModel.getContent();
                ivKittyIcon.setImageResource(ImageSourceUtil.getResorceByLevel(animation_level));
                tvKittyName.setText("Lv." + (animation_level > 38 ? 38 : animation_level) + "  " + CatHelperUtil.getKittyNameByLevel(animation_level));
                String onlineOutput = CatHelperUtil.getDisplayKittyOutput(animation_level);
                BigDecimal kittyOutput = CatHelperUtil.getKittyOutput(animation_level);
                tvOnlineOutput.setText(onlineOutput + "/秒");
                tvOfflineOutput.setText(CatHelperUtil.getDispalyedNumber(kittyOutput.multiply(new BigDecimal(offline_rate)).toString()) + "/秒");
            }
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_close)
    public void onViewClicked() {
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);
            Objects.requireNonNull(dialog.getWindow()).setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}

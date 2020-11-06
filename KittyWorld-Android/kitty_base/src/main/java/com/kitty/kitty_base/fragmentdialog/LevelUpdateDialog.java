package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.model.CatNameModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R2;
import com.umeng.commonsdk.debug.E;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class LevelUpdateDialog extends BaseDialogFragment {

    @BindView(R2.id.iv_kitty_icon)
    ImageView ivKittyIcon;
    @BindView(R2.id.tv_kitty_level)
    TextView tvKittyLevel;
    @BindView(R2.id.tv_kitty_name)
    TextView tvKittyName;
    @BindView(R2.id.tv_stage_limit)
    TextView tvStageLimit;
    @BindView(R2.id.today_bonus_count)
    TextView todayBonusCount;

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
        return R.layout.dialog_level_update;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        try {
            if (null != bundle) {
                int level = bundle.getInt(IntentConfig.MERGE_LEVEL) + 1;
                int todayDivide = SPUtils.getInt(Utils.getContext(), SPConfig.TODAY_DIVIDE, 0);
                ivKittyIcon.setImageResource(ImageSourceUtil.getBuyResorceByLevel(level));
                tvKittyName.setText(CatHelperUtil.getKittyNameByLevel(level));
                tvKittyLevel.setText("Lv." + level);
                tvStageLimit.setText(String.valueOf(38 - level));
                todayBonusCount.setText(NumberUtil.getWanDividedNumber(BigDecimal.valueOf(todayDivide)).toString() + Utils.getString(R.string.cny_unit));
            }
            todayBonusCount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            tvKittyLevel.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
        } catch (Exception e) {
        }
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

    @OnClick(R2.id.iv_confirm)
    public void confirm(View view) {
        dismiss();
    }
}

package com.kitty.kitty_base.fragmentdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.model.TaskModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class SignLogDailog extends BaseDialogFragment {

    @BindView(R2.id.tv_sign_days)
    TextView tvSignDays;
    @BindView(R2.id.tv_bonus_count)
    TextView tvBonusCount;

    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            if (null != bundle) {
                TaskModel.UserSignTaskBean.RewardBean rewardBean = (TaskModel.UserSignTaskBean.RewardBean) bundle.getSerializable(IntentConfig.SIGN_TASK_REWARD);
                int signDay = bundle.getInt(IntentConfig.SIGN_TASK_DAY);
                List<Integer> arrayList = bundle.getIntegerArrayList(IntentConfig.KITTY_LEVEL_LIST);
                tvSignDays.setText(Utils.getString(R.string.task_contine_font) + signDay + Utils.getString(R.string.task_contines_last));
                String bonus = CatHelperUtil.getDispalyedNumber(BigDecimal.valueOf(Long.valueOf(rewardBean.getAmount())).multiply(CatHelperUtil.getTotalOutPutAmountInteger(arrayList)).toString());
                tvBonusCount.setText(bonus);
                tvBonusCount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
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
        return true;
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.dialog_task_sign_log;
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
}

package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.constant.AdsType;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.interfaces.IAdsLoaded;
import com.kitty.kitty_base.model.RewardModel;
import com.kitty.kitty_base.utils.AdviseUtil;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.singleton.GsonSingleInstance;
import com.kitty.kitty_res.R;
import com.kitty.kitty_res.R2;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ningkang
 */

public class TreasureBoxDialog extends BaseDialogFragment {


    @BindView(R2.id.tv_reward_value)
    TextView rewardValue;
    @BindView(R2.id.tv_bonus_box_amount)
    TextView tv_bonus_box_amount;
    @BindView(R2.id.tv_remain_wheel_count)
    TextView tv_remain_wheel_count;

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            RewardObject luckWheelModel = (RewardObject) bundle.getSerializable(IntentConfig.LUCK_WHEEL_MODEL);
            rewardValue.setText(luckWheelModel.amount + Utils.getString(R.string.box_ratio));
            tv_bonus_box_amount.setText("发财啦，下次抽奖金翻" + NumberUtil.formatInteger(Integer.valueOf(luckWheelModel.amount)) + "倍");

            int adTimes = SPUtils.getInt(Utils.getContext(), SPConfig.AD_REMAIN_COUNT, 0);
            RewardModel<String> rewardModel = FormDataUtil.getRewardModelMap().get("ad_time_update");
            String time = rewardModel.getContent();
            List<Long> resetTimes = GsonSingleInstance.buildGson().fromJson(time, new TypeToken<List<Long>>() {
            }.getType());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < resetTimes.size(); i++) {
                stringBuilder.append(resetTimes.get(i) + (i == resetTimes.size() - 1 ? "点" : "点,"));
            }
            tv_remain_wheel_count.setText("每天" + stringBuilder.toString() + "重置视频次数（剩余" + adTimes + "次）");
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
        return R.layout.fragment_dialog_treasure_box;
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

    @OnClick(R2.id.ll_watch_vidio_get)
    public void onWatchVedioClicked() {
        AdviseUtil.playRewardVedio(getActivity(), AdsType.WHEEL_BOX, new IAdsLoaded() {
            @Override
            public void onLoaded() {
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}

package com.kitty.kitty_base.fragmentdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.AdsType;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.interfaces.IAdsLoaded;
import com.kitty.kitty_base.model.KittyBase;
import com.kitty.kitty_base.model.RewardModel;
import com.kitty.kitty_base.utils.AdviseUtil;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.singleton.GsonSingleInstance;
import com.kitty.kitty_res.R2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MoreCoinDialog extends BaseDialogFragment {


    @BindView(R2.id.tv_bonus_time)
    TextView tvBonusTime;
    @BindView(R2.id.tv_bonus_count)
    TextView tvBonusCount;
    @BindView(R2.id.tv_remain_wheel_count)
    TextView tvRemainWheelCount;

    @Override
    protected boolean needForbidBackPress() {
        return false;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return false;
    }

    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            int maxLevel = SPUtils.getInt(getActivity(), SPConfig.MAX_LEVEL, 1);
            ArrayList<Integer> integers = bundle.getIntegerArrayList(IntentConfig.KITTY_LEVEL_LIST);
            KittyBase kittyBase = CatHelperUtil.getKittyBaseByLevel(maxLevel);
            BigDecimal adsRewards = kittyBase.getAds_rewards();
            String bonus = CatHelperUtil.getDispalyedNumber(adsRewards.multiply(CatHelperUtil.getTotalOutPutAmountInteger(integers)).toString());
            tvBonusCount.setText(bonus);
            tvBonusTime.setText(Utils.getString(R.string.get_instant) + TimeDescUtil.getTimeDesc(kittyBase.getAds_rewards().intValue()) + Utils.getString(R.string.benfit));
            tvBonusCount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));

            int adTimes = SPUtils.getInt(Utils.getContext(), SPConfig.AD_REMAIN_COUNT, 0);
            RewardModel<String> rewardModel = FormDataUtil.getRewardModelMap().get("ad_time_update");
            String time = rewardModel.getContent();
            List<Long> resetTimes = GsonSingleInstance.buildGson().fromJson(time, new TypeToken<List<Long>>() {
            }.getType());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < resetTimes.size(); i++) {
                stringBuilder.append(resetTimes.get(i) + (i == resetTimes.size() - 1 ? "点" : "点,"));
            }
            tvRemainWheelCount.setText("每天" + stringBuilder.toString() + "重置视频次数（剩余" + NumberUtil.getPositiveInteger(adTimes) + "次）");
        } catch (Exception e) {
        }
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.dialog_more_coin;
    }

    @OnClick(R2.id.ll_get_bonus_confirm)
    public void getBonusConfirm() {
        AdviseUtil.playRewardVedio(getActivity(), AdsType.MORE_COIN, new IAdsLoaded() {
            @Override
            public void onLoaded() {
                dismiss();
            }
        });
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
    public void close() {
        dismiss();
    }
}

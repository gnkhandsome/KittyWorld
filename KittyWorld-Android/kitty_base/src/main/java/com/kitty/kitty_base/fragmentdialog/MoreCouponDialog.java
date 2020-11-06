package com.kitty.kitty_base.fragmentdialog;

import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
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
import com.kitty.kitty_res.R2;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MoreCouponDialog extends BaseDialogFragment {

    @BindView(R2.id.ll_watch_vidio_get)
    LinearLayout llWatchVidioGet;
    @BindView(R2.id.tv_remain_wheel_count)
    TextView tvRemainWheelCount;


    @Override
    protected void initData() {
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
        return R.layout.dialog_more_coupon;
    }

    @OnClick(R2.id.close_dialog)
    public void close() {
        dismiss();
    }

    @OnClick(R2.id.ll_watch_vidio_get)
    public void vedio() {
        AdviseUtil.playRewardVedio(getActivity(), AdsType.WHEEL_TIMES, new IAdsLoaded() {
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
}

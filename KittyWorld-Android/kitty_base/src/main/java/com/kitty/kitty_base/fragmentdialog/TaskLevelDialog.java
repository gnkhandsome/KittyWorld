package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.ui.HomeFragment;
import com.kitty.kitty_base.ui.PostShareActivity;
import com.kitty.kitty_base.ui.PostShareDialog;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.singleton.MainHandler;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskLevelDialog extends BaseDialogFragment {

    @BindView(R2.id.tv_reward_kitty_desc)
    TextView tvRewardKittyDesc;

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        RewardObject luckWheelModel = (RewardObject) bundle.getSerializable(IntentConfig.LUCK_WHEEL_MODEL);
        String timeDesc = TimeDescUtil.getTimeKittyDesc(Integer.parseInt(luckWheelModel.amount));
        tvRewardKittyDesc.setText(timeDesc);
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
        return R.layout.fragment_dialog_task_level;
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
    public void close(View view) {
        dismiss();
    }

    @OnClick(R2.id.tv_invite)
    public void tv_invite(View view) {
        try {
            dismiss();
//            Intent intent = new Intent(getActivity(), PostShareActivity.class);
//            Objects.requireNonNull(getActivity()).startActivity(intent);
            PostShareDialog postShareDialog = PostShareDialog.newInstance();
            postShareDialog.show(getFragmentManager(), "");
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.to_task)
    public void to_task(View view) {
        dismiss();
        MainHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null != HomeFragment.homeFragment) {
                    HomeFragment.homeFragment.toTask();
                }
            }
        }, 200);
    }
}

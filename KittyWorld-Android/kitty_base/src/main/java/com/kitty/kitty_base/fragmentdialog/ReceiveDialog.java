package com.kitty.kitty_base.fragmentdialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.model.CatNameModel;
import com.kitty.kitty_base.model.MessageModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R2;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import kitty_protos.C2SReceiveMessage;
import kitty_protos.MessageOuterClass;

public class ReceiveDialog extends BaseDialogFragment {

    @BindView(R2.id.iv_kitty_icon)
    ImageView ivKittyIcon;
    @BindView(R2.id.tv_kitty_level)
    TextView tvKittyLevel;
    @BindView(R2.id.tv_reward_value)
    TextView tvRewardValue;

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        try {
            if (null != bundle) {
                RewardObject rewardObject = (RewardObject) bundle.getSerializable(IntentConfig.REWARD_OBJECT_MODEL);
                ivKittyIcon.setImageResource(getImageFromType(rewardObject));
            }
        } catch (Exception e) {
        }
    }


    private int getImageFromType(RewardObject rewardInfoBean) {
        long type = rewardInfoBean.rewardType;
         if (type == 3) {
            return R.drawable.hour_bonus_icon;
        } else if (type == 1) {
            tvKittyLevel.setVisibility(View.VISIBLE);
            tvKittyLevel.setText(String.valueOf(rewardInfoBean.rewardID));
            return ImageSourceUtil.getResorceByLevel(rewardInfoBean.rewardID);
        }
        return 0;
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
        return R.layout.dialog_receive;
    }

    @OnClick(R2.id.close_dialog)
    public void close(View view) {
        dismiss();
    }

}

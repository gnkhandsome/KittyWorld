package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.AdsType;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.interfaces.IAdsLoaded;
import com.kitty.kitty_base.model.RewardTimeBonuObject;
import com.kitty.kitty_base.utils.AdviseUtil;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import kitty_protos.C2SGiveUpOfflineCoin;
import kitty_protos.C2SSyncKittyInfo;
import kitty_protos.MessageOuterClass;
import kitty_protos.S2CGiveUpOfflineCoin;

public class OutLineBonusDialog extends BaseDialogFragment {
    @BindView(R2.id.tv_bonus_count)
    TextView tvBonusCount;
    //    @BindView(R2.id.tv_outline_bonus_time)
//    TextView tvOutlineBonusTime;
    @BindView(R2.id.iv_image_action)
    ImageView ivImageAction;
    @BindView(R2.id.tv_reward_confirm_text)
    TextView tvRewardConfirmText;
    private RewardTimeBonuObject bonusCount;


    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            if (null != bundle) {
                bonusCount = (RewardTimeBonuObject) bundle.getSerializable(IntentConfig.REWARD_OBJECT_MODEL);
                tvBonusCount.setText(CatHelperUtil.getDispalyedNumber(bonusCount.amount));
//                String text = Utils.getString(R.string.already_to_gain) + "<font color=\"#FF5E6E\">" + TimeDescUtil.getTimeDesc((int) bonusCount.bonusTime) + Utils.getString(R.string.out_line_text);
//                tvOutlineBonusTime.setText(Html.fromHtml(text));
                ivImageAction.setImageResource(R.drawable.vedio_icon);
                tvRewardConfirmText.setText(R.string.vedio_to_get_double);
            }
            tvBonusCount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
        } catch (Exception e) {
        }
    }

    @Override
    protected boolean needForbidBackPress() {
        return true;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return false;
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.fragment_dialog_outline_bonus;
    }

    @OnClick(R2.id.close_dialog)
    public void close(View view) {
        C2SGiveUpOfflineCoin.C2S_GiveUpOfflineCoin.Builder builder = C2SGiveUpOfflineCoin.C2S_GiveUpOfflineCoin.newBuilder().setCalTimestamp((int) (System.currentTimeMillis() / 1000));
        MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SGiveUpOfflineCoin(builder).setMessageId(MessageOuterClass.Message.C2SGIVEUPOFFLINECOIN_FIELD_NUMBER);
        SocketManager.send(callObject);
        dismiss();
    }


    @OnClick(R2.id.ll_benfit_confirm)
    public void getMoreCoin(View view) {
        if (null != bonusCount) {
            AdviseUtil.playRewardVedio(getActivity(), AdsType.ON_HOOK, new IAdsLoaded() {
                @Override
                public void onLoaded() {
                    dismiss();
                }
            });
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

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }
}

package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.model.ShareInfo;
import com.kitty.kitty_base.utils.ShareUtils;
import com.kitty.kitty_res.R;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ningkang
 */

public class RedPackDialog extends BaseDialogFragment {


    private ShareInfo shareInfo;

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            shareInfo = (ShareInfo) bundle.getSerializable(IntentConfig.SHARE_INFO);
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
        return R.layout.fragment_dialog_red_pack;
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

    @OnClick(R2.id.tv_share_red_pack)
    public void shareRedPack() {
        if (null != shareInfo) {
            ShareUtils shareUtils = new ShareUtils();
            shareUtils.shareTo("WEIXIN", shareInfo, getActivity(), null);
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}

package com.kitty.kitty_base.fragmentdialog;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.model.TipContentModel;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class PermissionRequestDialog extends BaseDialogFragment {

    @BindView(R2.id.tv_tip_title)
    TextView tvTipTitle;
    @BindView(R2.id.tv_tip_content)
    TextView tvTipContent;

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
        return R.layout.fragment_dialog_permission_request;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        TipContentModel tipContentModel = (TipContentModel) bundle.getSerializable(IntentConfig.TIPCONENTMODEL);
        tvTipTitle.setText(tipContentModel.getTitle());
        tvTipContent.setText(tipContentModel.getContent());
    }


    @OnClick(R2.id.tv_tip_dismiss)
    public void onViewClicked() {
        try {

            dismiss();
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
            Objects.requireNonNull(dialog.getWindow()).setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}

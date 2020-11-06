package com.kitty.kitty_base.base;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.model.TipContentModel;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class BaseTipDialog extends BaseDialogFragment {

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
        return R.layout.fragment_dialog_base_tip;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        TipContentModel tipContentModel = (TipContentModel) bundle.getSerializable(IntentConfig.TIPCONENTMODEL);
        tvTipTitle.setText(tipContentModel.getTitle());
        tvTipContent.setText(tipContentModel.isHtml() ? Html.fromHtml(tipContentModel.getContent()) : tipContentModel.getContent());
    }

    @OnClick(R2.id.tv_tip_dismiss)
    public void onViewClicked() {
        dismiss();
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

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
import com.kitty.kitty_base.model.TipContentModel;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class StopServerDialog extends BaseDialogFragment {


    @BindView(R2.id.tv_tip_title)
    TextView tvTipTitle;
    @BindView(R2.id.tv_tip_content)
    TextView tvTipContent;
    @BindView(R2.id.tv_tip_dismiss)
    TextView tv_tip_dismiss;

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
        return R.layout.fragment_dialog_stopserver;
    }

    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            TipContentModel tipContentModel = (TipContentModel) bundle.getSerializable(IntentConfig.TIPCONENTMODEL);
            tvTipTitle.setText(tipContentModel.getTitle());
            tvTipContent.setText(tipContentModel.getContent());
            tv_tip_dismiss.setVisibility(tipContentModel.getType() == 1 ? View.GONE : View.VISIBLE);
        } catch (Exception e) {
        }
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
            Objects.requireNonNull(dialog.getWindow()).setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}

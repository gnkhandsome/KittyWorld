package com.kitty.kitty_base.fragmentdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.model.DragResourceViewModel;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ningkang
 */

public class AddRepertoryDialog extends BaseDialogFragment {

    @BindView(R2.id.tv_add_to_repertory_icon)
    ImageView tvAddToRepertoryIcon;
    @BindView(R2.id.tv_add_to_repertory_level)
    TextView tvAddToRepertoryLevel;
    @BindView(R2.id.tv_add_to_repertory_confirm)
    TextView tvAddToRepertoryConfirm;
    @BindView(R2.id.ll_add_to_repertory_dialog_close_icon)
    LinearLayout llAddToRepertoryDialogCloseIcon;

    @Override
    protected boolean needForbidBackPress() {
        return false;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return true;
    }

    @Override
    protected void initData() {
        try {
            Bundle bundle = getArguments();
            DragResourceViewModel payAmountReason = (DragResourceViewModel) bundle.getSerializable(IntentConfig.DRAGRESOURCEVIEWMODEL);
            tvAddToRepertoryIcon.setImageResource(ImageSourceUtil.getResorceByLevel(payAmountReason.getLevel()));
            tvAddToRepertoryLevel.setText(String.valueOf(payAmountReason.getLevel() > 37 ? 38 : payAmountReason.getLevel()));
            tvAddToRepertoryLevel.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
        } catch (Exception e) {
        }
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.fragment_dialog_add_to_repertory;
    }


    @OnClick(R2.id.tv_add_to_repertory_confirm)
    public void onRepertoryConfirmClicked(View view) {
        showDialogEachTime();
        iOnClickListener.onConfirmClick();
        dismissDialog();
        dismiss();
    }

    @OnClick(R2.id.ll_add_to_repertory_dialog_close_icon)
    public void onDialogCloseClicked(View view) {
        iOnClickListener.onDismissClick();
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

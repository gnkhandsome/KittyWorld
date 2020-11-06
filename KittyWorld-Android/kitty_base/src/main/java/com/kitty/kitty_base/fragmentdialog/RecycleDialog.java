package com.kitty.kitty_base.fragmentdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.model.DragResourceViewModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
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

public class RecycleDialog extends BaseDialogFragment {

    @BindView(R2.id.iv_recycle_icon)
    ImageView ivRecycleIcon;
    @BindView(R2.id.tv_recycle_prive)
    TextView tvRecyclePrive;

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
            ivRecycleIcon.setImageResource(ImageSourceUtil.getBuyResorceByLevel(payAmountReason.getLevel()));
            tvRecyclePrive.setText(String.valueOf(CatHelperUtil.getRecyclePriceByLevel(payAmountReason.getLevel())));
            tvRecyclePrive.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
        } catch (Exception e) {
        }
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.fragment_dialog_recycle;
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


    @OnClick(R2.id.iv_dismiss)
    public void close(View view) {
        if (null != iOnClickListener) {
            iOnClickListener.onDismissClick();
        }
        dismiss();
    }

    @OnClick(R2.id.tv_add_to_repertory_confirm)
    public void confirm(View view) {
        if (null != iOnClickListener) {
            showDialogEachTime();
            iOnClickListener.onConfirmClick();
            dismissDialog();
        }
        dismiss();
    }
}

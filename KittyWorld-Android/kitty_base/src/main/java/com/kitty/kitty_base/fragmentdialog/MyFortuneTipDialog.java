package com.kitty.kitty_base.fragmentdialog;

import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFortuneTipDialog extends BaseDialogFragment {
    @BindView(R2.id.tv_tip_title)
    TextView tvTipTitle;
    @BindView(R2.id.tv_tip_title_one)
    TextView tvTipTitleOne;
    @BindView(R2.id.tv_tip_content)
    TextView tvTipContent;
    @BindView(R2.id.tv_tip_title_two)
    TextView tvTipTitleTwo;
    @BindView(R2.id.tv_tip_content_two)
    TextView tvTipContentTwo;


    @Override
    protected void initData() {
        try {
            tvTipTitle.setText(Utils.getString(R.string.my_forever_fortune));
            tvTipTitleOne.setText(Utils.getString(R.string.tv_tip_title_one));
            tvTipTitleTwo.setText(Utils.getString(R.string.tv_tip_title_two));
            tvTipContent.setText(Utils.getString(R.string.tv_tip_content));
            tvTipContentTwo.setText(Utils.getString(R.string.tv_tip_content_two));
        } catch (Exception e) {
        }
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
        return R.layout.dialog_fragment_myfortune_tip;
    }

    @OnClick(R2.id.tv_tip_dismiss)
    public void tv_tip_dismiss() {
        dismiss();
    }
}

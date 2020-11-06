package com.kitty.kitty_base.fragmentdialog;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.WebViewActivity;
import com.kitty.kitty_base.ui.AssetCenterActivity;
import com.kitty.kitty_res.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class CuponKittyDialog extends BaseDialogFragment {
    @BindView(R2.id.tv_pack_amount)
    TextView tvPackAmount;

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
        return R.layout.fragment_dialog_cuponkitty;
    }

    @Override
    protected void initData() {
//        tvPackAmount
    }

    @OnClick(R2.id.iv_close)
    public void iv_close(View view) {
        dismiss();
    }

    @OnClick(R2.id.tv_to_asset_center)
    public void tv_to_asset_center(View view) {
        try {
            Intent intent = new Intent(getActivity(), AssetCenterActivity.class);
            startActivity(intent);
            dismiss();
        } catch (Exception e) {
        }
    }

}

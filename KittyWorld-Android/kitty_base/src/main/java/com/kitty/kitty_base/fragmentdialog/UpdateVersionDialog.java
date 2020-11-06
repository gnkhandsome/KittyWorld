package com.kitty.kitty_base.fragmentdialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.model.UpdateInfo;
import com.kitty.kitty_base.utils.VersionUtil;
import com.kitty.kitty_res.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateVersionDialog extends BaseDialogFragment {
    @BindView(R2.id.tv_update_version)//
    TextView tvUpdateVersion;
    @BindView(R2.id.tv_update_info)
    TextView tvUpdateInfo;
    @BindView(R2.id.tv_not_update)
    TextView tvNotUpdate;
    @BindView(R2.id.tv_version_name)
    TextView tv_version_name;
    private UpdateInfo updateInfo;

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
        return R.layout.dialog_fragment_update_version;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        updateInfo = (UpdateInfo) bundle.getSerializable(IntentConfig.UPDATE_INFO);
        if (null != updateInfo){
            tv_version_name.setText("V"+ updateInfo.getVersion());
            tvUpdateInfo.setText(updateInfo.getInfo());
            tvNotUpdate.setVisibility(updateInfo.getIs_force() == 0?View.VISIBLE:View.INVISIBLE);
        }
    }

    @OnClick(R2.id.tv_update)
    public void tv_update(View view) {
        VersionUtil.downFile(updateInfo.getDownload_url(),getActivity());
    }

    @OnClick(R2.id.tv_not_update)
    public void tv_not_update(View view) {
         dismiss();
    }
}

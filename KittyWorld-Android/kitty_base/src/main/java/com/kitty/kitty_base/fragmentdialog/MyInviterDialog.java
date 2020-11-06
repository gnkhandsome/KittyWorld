package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.model.FriendProfitModel;
import com.kitty.kitty_base.ui.SocialInfoActivity;
import com.kitty.kitty_base.utils.GlideUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R;
import com.kitty.kitty_res.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ningkang
 */

public class MyInviterDialog extends BaseDialogFragment {


    @BindView(R2.id.iv_my_inviter_icon)
    ImageView myInviterIcon;
    @BindView(R2.id.tv_level_and_name)
    TextView tvLevelAndName;
    @BindView(R2.id.tv_inviter_wx_number)
    TextView tvInviterWxNumber;
    @BindView(R2.id.tv_inviter_qq_number)
    TextView tvInviterQqNumber;
    ClipboardManager mClipboardManager;
    @BindView(R2.id.ll_invite_wx)
    RelativeLayout llInviteWx;
    @BindView(R2.id.ll_invite_qq)
    RelativeLayout llInviteQq;
    private FriendProfitModel.ParentInfoBean parentInfoBean;

    @Override
    protected boolean needForbidBackPress() {
        return false;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        try {
            mClipboardManager = (ClipboardManager) Utils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            Bundle bundle = getArguments();
            parentInfoBean = (FriendProfitModel.ParentInfoBean) bundle.getSerializable(IntentConfig.PARENTINFOBEAN);
            tvLevelAndName.setText("Lv." + parentInfoBean.getMax_level() + " " + parentInfoBean.getNickname());
            llInviteWx.setVisibility(TextUtils.isEmpty(parentInfoBean.getWechat()) ? View.GONE : View.VISIBLE);
            llInviteQq.setVisibility(TextUtils.isEmpty(parentInfoBean.getQq()) ? View.GONE : View.VISIBLE);
            GlideUtils.loadCirclePic(getActivity(), parentInfoBean.getAvatar(), myInviterIcon);
            tvInviterWxNumber.setText(parentInfoBean.getWechat());
            tvInviterQqNumber.setText(parentInfoBean.getQq());
        } catch (Exception e) {
        }
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.fragment_dialog_my_invite;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);
            Objects.requireNonNull(dialog.getWindow()).setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @OnClick(R2.id.set_invite_info_too)
    public void setMyInfo() {
        try {
//            WebViewModel webViewModel = new WebViewModel();
//            webViewModel.setTitle(Utils.getString(R.string.home_image_share));
//            webViewModel.setUrl(Utils.getString(R.string.home_image_share_url));
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), SocialInfoActivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.btn_copy_wx)
    public void copyWx(View view) {
        if (null != parentInfoBean) {
            ClipData mClipData = ClipData.newPlainText("Label", parentInfoBean.getWechat());
            mClipboardManager.setPrimaryClip(mClipData);
            ToastUtils.showShort(R.string.copy_success);
        }
    }


    @OnClick(R2.id.btn_copy_qq)
    public void copyQQ(View view) {
        if (null != parentInfoBean) {
            ClipData mClipData = ClipData.newPlainText("Label", parentInfoBean.getQq());
            mClipboardManager.setPrimaryClip(mClipData);
            ToastUtils.showShort(R.string.copy_success);
        }
    }
}

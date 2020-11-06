package com.kitty.kitty_base.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.fragmentdialog.MyInviterDialog;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.MyInviteModel;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class MyInviteCodeAcivity extends BaseActivity {
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.tv_my_invite_code)
    TextView tvMyInviteCode;
    @BindView(R2.id.tv_direct_friend)
    TextView tvDirectFriend;
    @BindView(R2.id.tv_indirect_friend)
    TextView tvIndirectFriend;
    private ClipboardManager mClipboardManager;
    private MyInviteModel myInviteModel;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_invitor;
    }

    @Override
    protected void initData() {
        mClipboardManager = (ClipboardManager) Utils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        tvTitle.setText(Utils.getString(R.string.my_inviter_code_title));
        tvRight.setText(Utils.getString(R.string.my_inviter_title));
        refreshMyInviteData();
    }

    private void refreshMyInviteData() {
        HttpUtils.getMyInvite(new IResponse<MyInviteModel>() {
            @Override
            public void onSuccess(BaseResponse<MyInviteModel> baseResponse) {
                try {
                    if (null != baseResponse && null != baseResponse.data) {
                        try {
                            myInviteModel = baseResponse.data;
                            tvMyInviteCode.setText(String.valueOf(myInviteModel.getInvite_code()));
                            tvDirectFriend.setText(String.valueOf(myInviteModel.getDirect_friend()));
                            tvIndirectFriend.setText(String.valueOf(myInviteModel.getSecond_friend()));
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }


    @OnClick(R2.id.iv_back)
    public void back(View view) {
        finish();
    }

    @OnClick(R2.id.tv_right)
    public void myInviter(View view) {
        try {
            if (myInviteModel.getParent_social().getId() == 0) {
                SetInviteDialog setInviteDialog = new SetInviteDialog();
                setInviteDialog.show(getSupportFragmentManager(), "setInviteDialog");
                setInviteDialog.setIUpdateListener(new SetInviteDialog.IUpdateListener() {
                    @Override
                    public void update() {
                        refreshMyInviteData();
                    }
                });
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentConfig.PARENTINFOBEAN, myInviteModel.getParent_social());
                MyInviterDialog myInviterDialog = new MyInviterDialog();
                myInviterDialog.setArguments(bundle);
                myInviterDialog.show(getSupportFragmentManager(), "myInviterDialog");
            }
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.tv_copy)
    public void copyCode(View view) {
        if (!TextUtils.isEmpty(tvMyInviteCode.getText())) {
            ClipData mClipData = ClipData.newPlainText("Label", tvMyInviteCode.getText());
            mClipboardManager.setPrimaryClip(mClipData);
            ToastUtils.showShort(com.kitty.kitty_res.R.string.copy_success);
        }
    }

    @OnClick(R2.id.tv_invite_friend)
    public void invite(View view) {
//        Intent intent = new Intent(MyInviteCodeAcivity.this, PostShareActivity.class);
//        startActivity(intent);
        PostShareDialog postShareDialog = PostShareDialog.newInstance();
        postShareDialog.show(getSupportFragmentManager(),"");
    }
}

package com.kitty.kitty_base.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_res.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class SetInviteDialog extends BaseDialogFragment {


    @BindView(R2.id.edt_invite_code)
    EditText edtInviteCode;
    private IUpdateListener iUpdateListener;

    @Override
    protected void initData() {
        edtInviteCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText _e = (EditText) v;
                if (!hasFocus) {
                    _e.setHint(_e.getTag().toString());
                } else {
                    _e.setTag(_e.getHint().toString());
                    _e.setHint("");
                }
            }
        });
    }

    public interface IUpdateListener {
        void update();
    }

    public void setIUpdateListener(IUpdateListener iUpdateListener) {
        this.iUpdateListener = iUpdateListener;
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
        return R.layout.dialog_fragment_set_invite;
    }


    @OnClick(R2.id.iv_dismiss)
    public void dismiss(View view) {
        dismiss();
    }

    @OnClick(R2.id.submit_inviter)
    public void submitInviter(View view) {
        try {
            if (TextUtils.isEmpty(edtInviteCode.getText().toString())) {
                ToastUtils.showLong(R.string.inviter_code_is_empty);
                return;
            }
            long userId;
            if (edtInviteCode.getText().toString().contains(".")) {
                userId = Long.parseLong(edtInviteCode.getText().toString().split(".")[0]);
            }
            userId = Long.parseLong(edtInviteCode.getText().toString());

            HttpUtils.submitInviterCode(userId, new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                    if (baseResponse.code == 0) {
                        ToastUtils.showShortCenter(R.string.set_social_sucess);
                        if (null != iUpdateListener) {
                            iUpdateListener.update();
                        }
                        dismiss();
                    } else if (baseResponse.code == 1002) {
                        ToastUtils.showShortCenter(R.string.recycle_inviter_error);
                    } else {
                        ToastUtils.showShortCenter(R.string.sett_social_failed);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    ToastUtils.showLong(com.kitty.kitty_res.R.string.sett_social_failed);
                }
            });

        } catch (Exception e) {
            ToastUtils.showLong(com.kitty.kitty_res.R.string.sett_social_failed);
        }
    }

}

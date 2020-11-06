package com.kitty.kittyworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.base.WebViewActivity;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.fragmentdialog.StopServerDialog;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.StopServerModel;
import com.kitty.kitty_base.model.TipContentModel;
import com.kitty.kitty_base.model.WebViewModel;
import com.kitty.kitty_base.router.RouterActivityPath;
import com.kitty.kitty_base.ui.MainActivity;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R2;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = RouterActivityPath.ACTIVITY_LOGIN_PATH)
public class LoginActivity extends BaseActivity {
    public static LoginActivity loginActivity;
    @BindView(R2.id.cb_proto_check)
    CheckBox cbProtoCheck;
    private boolean isChecked;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        loginActivity = this;
        isChecked = cbProtoCheck.isChecked();
        initStopServer();
    }

    private void initStopServer() {
        HttpUtils.getStopServer(new IResponse<StopServerModel>() {
            @Override
            public void onSuccess(BaseResponse<StopServerModel> baseResponse) {
                try {
                    if (null != baseResponse.data) {
                        if (baseResponse.code == 0) {
                            StopServerModel stopServerModel = baseResponse.data;
                            int type = stopServerModel.getType();
                            if (type == 1) {
                                if ((System.currentTimeMillis() / 1000) < stopServerModel.getStop_time() && (System.currentTimeMillis() / 1000) >= stopServerModel.getBegin_time()) {
                                    type = 0;
                                } else if (System.currentTimeMillis() / 1000 >= stopServerModel.getStop_time() && System.currentTimeMillis() / 1000 <= stopServerModel.getStart_time()) {
                                    type = 1;
                                    SPUtils.remove(LoginActivity.this, SPConfig.UID);
                                    SPUtils.remove(LoginActivity.this, SPConfig.TOKEN);
                                }
                            }
                            TipContentModel tipContentModel = new TipContentModel();
                            tipContentModel.setTitle(stopServerModel.getTitle());
                            tipContentModel.setContent(stopServerModel.getContent());
                            tipContentModel.setType(type);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(IntentConfig.TIPCONENTMODEL, tipContentModel);
                            StopServerDialog stopServerDialog = new StopServerDialog();
                            stopServerDialog.setArguments(bundle);
                            stopServerDialog.show(getSupportFragmentManager(), "stopServerDialog");
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

    @OnClick(R2.id.ll_login)
    public void loginWx() {
        if (!isChecked) {
            ToastUtils.showLong("请先勾选协议！");
            return;
        }
        if (!KittyWorldApplication.api.isWXAppInstalled()) {
            ToastUtils.showShort("您还未安装微信客户端！");
            return;
        }
        showDialogEachTime();
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "snsapi_login_lnint";
        KittyWorldApplication.api.sendReq(req);
        dismissDialog();
    }

    //    http://kitty.dev.cocosbcx.net/privacyprotocol
    @OnClick(R2.id.tv_user_proto)
    public void userProto(View view) {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(com.kitty.kitty_home.R.string.login_user_proto_title));
            webViewModel.setUrl(Utils.getString(com.kitty.kitty_home.R.string.login_user_proto_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.tv_private_proto)
    public void privateProto(View view) {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(com.kitty.kitty_home.R.string.login_privcy_proto_title));
            webViewModel.setUrl(Utils.getString(com.kitty.kitty_home.R.string.login_privcy_proto_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.cb_proto_check)
    public void onViewClicked() {
        cbProtoCheck.setChecked(!isChecked);
        isChecked = !isChecked;
    }
}

package com.kitty.kitty_base.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.base.WebViewActivity;
import com.kitty.kitty_base.model.WebViewModel;
import com.kitty.kitty_base.utils.AppApplicationMgr;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.version_name)
    TextView versionName;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_aboutus;
    }

    @Override
    protected void initData() {
        tvTitle.setText(Utils.getString(R.string.about_game));
        versionName.setText(AppApplicationMgr.getVersionName(Utils.getContext()));
    }


    @OnClick(R2.id.iv_back)
    public void iv_back(View view) {
        finish();
    }

    @OnClick(R2.id.ll_server_proto)
    public void serverProto(View view) {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.login_user_proto));
            webViewModel.setUrl(Utils.getString(R.string.login_user_proto_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(AboutUsActivity.this, WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_privacy)
    public void llPrivacy(View view) {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.login_privcy_proto));
            webViewModel.setUrl(Utils.getString(R.string.login_privcy_proto_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(AboutUsActivity.this, WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }
}

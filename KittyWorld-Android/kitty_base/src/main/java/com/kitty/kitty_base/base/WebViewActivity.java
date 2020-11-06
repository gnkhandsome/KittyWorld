package com.kitty.kitty_base.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.model.ShareInfo;
import com.kitty.kitty_base.model.WebViewModel;
import com.kitty.kitty_base.ui.PostShareDialog;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ShareUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.compress.BitmapMergeUtil;
import com.kitty.kitty_base.utils.compress.BitmapUtil;
import com.kitty.kitty_base.views.HtmlWebView;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.util.LogUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WebViewActivity extends BaseActivity {
    @BindView(R2.id.tv_title)
    TextView tvWebTitle;
    @BindView(R2.id.html_web_view)
    HtmlWebView htmlWebView;
    private WebViewModel webViewModel;
    private Bitmap bitmap;
    private String url;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_webview;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {
        try {
            Bundle bundle = getIntent().getExtras();
            webViewModel = (WebViewModel) bundle.getSerializable(IntentConfig.WEB_MODEL);
            if (!TextUtils.isEmpty(webViewModel.getUrl())) {
                htmlWebView.loadUrl(webViewModel.getUrl());
            }
            if (!TextUtils.isEmpty(webViewModel.getTitle())) {
                tvWebTitle.setText(webViewModel.getTitle());
            }
            htmlWebView.setWebViewClient(new BridgeWebViewClient(htmlWebView) {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.contains("tel:")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    WebViewActivity.this.url = url;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    setTitle(view.getTitle());
                }
            });

            htmlWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    setTitle(title);
                }

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        htmlWebView.progressView.setVisibility(View.GONE);
                    } else {
                        htmlWebView.progressView.setProgress(newProgress);
                    }
                    super.onProgressChanged(view, newProgress);
                }
            });

        } catch (Exception e) {
        }
    }

    @Override
    protected void setListener() {
//        receiveDefault();
        sendToken();
        registerSendToken();
        registerShare();
        registerShareToWechatPic();
        registershowShareBoard();
    }


//    /**
//     */
//    private void receiveDefault() {
//        try {
//            htmlWebView.setDefaultHandler((data, function) -> Log.i("data", data));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 发送token给js
     */
    private void sendToken() {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            JSONObject msg = new JSONObject();
            msg.put("token", token);
            htmlWebView.callHandler("KTWeb_SendToken", msg.toJSONString(), data -> ToastUtils.showLong(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送token给js
     */
    private void registershowShareBoard() {
        htmlWebView.registerHandler("KTNative_ShareToBoard", (data, function) -> {
            try {
//                Intent intent = new Intent(WebViewActivity.this, PostShareActivity.class);
//                startActivity(intent);
                PostShareDialog postShareDialog = PostShareDialog.newInstance();
                postShareDialog.show(getSupportFragmentManager(),"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 分享到微信
     */
    private void registerShare() {
        htmlWebView.registerHandler("KTNative_ShareToWechat", (data, function) -> {
//            ToastUtils.showLong("data from js",data);
            ShareInfo shareInfo1 = JSONObject.parseObject(data, ShareInfo.class);
            ShareUtils shareUtils = new ShareUtils();
            if (TextUtils.isEmpty(shareInfo1.getShareMedia())) {
                shareUtils.shareTo("WEIXIN", shareInfo1, WebViewActivity.this, null);
                return;
            }
            if (TextUtils.equals(shareInfo1.getShareMedia(), "wechat")) {
                shareUtils.shareTo("WEIXIN", shareInfo1, WebViewActivity.this, null);
            } else if (TextUtils.equals(shareInfo1.getShareMedia(), "moments")) {
                shareUtils.shareTo("WEIXIN_CIRCLE", shareInfo1, WebViewActivity.this, null);
            } else {
                shareUtils.shareTo("WEIXIN", shareInfo1, WebViewActivity.this, null);
            }
        });
    }

    /**
     * 分享图片到微信
     */
    private void registerShareToWechatPic() {
        htmlWebView.registerHandler("KTNative_ShareToWechatPic", (data, function) -> {
            ShareInfo shareInfo1 = JSONObject.parseObject(data, ShareInfo.class);
            bitmap = BitmapUtil.stringToBitmap(shareInfo1.getPicBase());
            ShareUtils.shareImage(WebViewActivity.this, SHARE_MEDIA.WEIXIN, bitmap);
        });
    }


    void sendShareSuccessMsg() {
        JSONObject msg = new JSONObject();
        msg.put("isShareSuccess", 1);
        htmlWebView.callHandler("KTWeb_ShareSuccess", msg.toJSONString(), ToastUtils::showLong);
    }


    /**
     * js调用方法获取token
     */
    private void registerSendToken() {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            htmlWebView.registerHandler("KTNative_GetToken", (data, callBackFunction) -> {
                JSONObject callback = new JSONObject();
                callback.put("token", token);
                callBackFunction.onCallBack(callback.toString());
            });
        } catch (Exception e) {

        }
    }


    @OnClick(R2.id.iv_back)
    public void onBackClicked(View view) {
        if (null != htmlWebView && htmlWebView.canGoBack()) {
            htmlWebView.goBack();
            return;
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (null != htmlWebView && htmlWebView.canGoBack()) {
                htmlWebView.goBack();
                return true;
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        CookieSyncManager.createInstance(this);  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
        if (null != htmlWebView) {
            htmlWebView.setWebViewClient(null);
            htmlWebView.getSettings().setJavaScriptEnabled(false);
            htmlWebView.destroy();
        }
        if (null != bitmap) {
            BitmapMergeUtil.recycleBitmap(bitmap);
        }
        super.onDestroy();
    }

    @OnClick(R2.id.ll_right)
    public void onViewClicked() {
        if (null != htmlWebView&&url!=null) {
            htmlWebView.loadUrl(url);
        }
    }
}

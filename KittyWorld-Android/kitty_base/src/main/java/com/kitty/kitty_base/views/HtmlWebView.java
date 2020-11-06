package com.kitty.kitty_base.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseWebView;
import com.kitty.kitty_base.utils.Utils;

public class HtmlWebView extends BridgeWebView {

    public ProgressView progressView;
    private Context context;
    private BridgeWebViewClient webChromeClient;

    public HtmlWebView(Context context) {
        this(context, null);
    }

    public HtmlWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HtmlWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        progressView = new ProgressView(context);
        progressView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(2)));
        progressView.setColor(getResources().getColor(R.color.color_262A33));
        progressView.setProgress(10);
        addView(progressView);
        initWebSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        getSettings().setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

}

package com.kitty.kittyworld.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kitty.kittyworld.BuildConfig;
import com.kitty.kittyworld.KittyWorldApplication;
import com.kitty.kitty_base.http.HttpUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.umeng.socialize.UMShareAPI;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {


    private WXEntryActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //这句没有写,是不能执行回调的方法的
        KittyWorldApplication.api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.i("onReq", "baseReq");
    }

    @Override
    public void onResp(BaseResp resp) {
//        String result = "";
//        switch (resp.errCode) {
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                result = "操作取消";
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                result = "请求被拒绝";
//                break;
//            default:
//                result = "未知错误";
//                break;
//        }
        switch (resp.getType()) {
            case 1: {
                if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                    SendAuth.Resp resp2 = (SendAuth.Resp) resp;
                    String code = resp2.code;
                    Log.i("wxcode", code);
                    HttpUtils.postWxCode(code);
                }
                break;
            }
            case 2: {
                if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
//                    result = "分享成功";
                }
            }
        }
        finish();
    }

}

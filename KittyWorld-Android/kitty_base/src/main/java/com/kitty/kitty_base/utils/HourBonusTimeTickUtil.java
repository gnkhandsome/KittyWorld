package com.kitty.kitty_base.utils;

import android.os.CountDownTimer;
import android.text.Html;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.constant.SPConfig;

/**
 * 短信验证码发送计时器
 *
 * @author hoolai
 * @date 2017/6/16
 */

public class HourBonusTimeTickUtil extends CountDownTimer {


    private TextView mTextView;

    /**
     * millisInfutrue 总倒计时时间 单位毫秒
     * countDownInterval 每几毫秒调用一次onTick（）方法
     */
    public HourBonusTimeTickUtil(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        String time = getTimeFromInt(millisUntilFinished);
        if (null != mTextView) {
            mTextView.setText(time);
        }
    }

    @Override
    public void onFinish() {
        if (null != mTextView) {
            mTextView.setText(Utils.getString(R.string.receive));
        }
    }

    public static String getTimeFromInt(long time) {
        if (time <= 0) {
            return "0:00";
        }
        long secondnd = (time / 1000) / 60;
        long million = (time / 1000) % 60;
        String f = String.valueOf(secondnd);
        String m = million >= 10 ? String.valueOf(million) : "0" + million;
        return f + ":" + m;
    }
}


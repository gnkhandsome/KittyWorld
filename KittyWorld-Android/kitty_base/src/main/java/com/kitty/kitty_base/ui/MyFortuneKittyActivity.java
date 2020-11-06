package com.kitty.kitty_base.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.base.BaseTipDialog;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.constant.UnlockConfig;
import com.kitty.kitty_base.fragmentdialog.MyFortuneTipDialog;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.KittyMoneyModel;
import com.kitty.kitty_base.model.MapModel;
import com.kitty.kitty_base.model.TipContentModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.math.BigDecimal;
import java.math.RoundingMode;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFortuneKittyActivity extends BaseActivity {
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_progress)
    TextView tvProgress;
    @BindView(R2.id.pb_fortune_pb)
    ProgressBar pbFortunePb;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_my_fortune_kitty;
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.my_fortune_kitty);
        tvProgress.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
        initProgress();
    }


    private void initProgress() {
        HttpUtils.getMapInfo(new IResponse<MapModel>() {
            @Override
            public void onSuccess(BaseResponse<MapModel> baseResponse) {
                try {
                    if (baseResponse.code == 0 && null != baseResponse.data) {
                        MapModel mapModel = baseResponse.data;
                        float percent = mapModel.getPercent() * 100;
                        SPUtils.putString(MyFortuneKittyActivity.this, SPConfig.PERCENT_MAP, String.valueOf(mapModel.getPercent()));
                        pbFortunePb.setProgress(percent > 0 && percent < 1 ? 1 : (int) percent);
                        tvProgress.setText(BigDecimal.valueOf(percent).setScale(2, RoundingMode.HALF_EVEN) + "%");
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
    public void iv_back(View view) {
        finish();
    }

    @OnClick(R2.id.rl_invite_friends)
    public void rl_invite_friends(View view) {
        try {
//            Intent intent = new Intent(MyFortuneKittyActivity.this, PostShareActivity.class);
//            startActivity(intent);
            PostShareDialog postShareDialog = PostShareDialog.newInstance();
            postShareDialog.show(getSupportFragmentManager(),"");
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.fl_up_activity)
    public void fl_up_activity(View view) {
        try {
            Intent intent = new Intent(MyFortuneKittyActivity.this, MainActivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.fl_unlock_city)
    public void fl_unlock_city(View view) {
        try {
            boolean map = CatHelperUtil.isUnlockedNoToast(UnlockConfig.MAP);
            if (!map) {
                ToastUtils.showShortCenter(R.string.untouch_to_unlock);
                return;
            }
            Intent intent = new Intent(MyFortuneKittyActivity.this, MapActivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.rl_top_borad)
    public void rl_top_borad() {

    }


    @OnClick(R2.id.tv_my_kitty_tip)
    public void tv_my_kitty_tip() {
        try {
            MyFortuneTipDialog my_forever_fortune_content = new MyFortuneTipDialog();
            my_forever_fortune_content.show(getSupportFragmentManager(), "my_forever_fortune_content");
        } catch (Exception e) {
        }
    }
}

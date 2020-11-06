package com.kitty.kitty_base.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.base.WebViewActivity;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.KittyMoneyModel;
import com.kitty.kitty_base.model.MapModel;
import com.kitty.kitty_base.model.WebViewModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.TextViewBanner;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FortuneKittyProfitActivity extends BaseActivity {

    @BindView(R2.id.tv_each_money)
    TextView tvEachMoney;
    @BindView(R2.id.kitty_count)
    TextView kittyCount;
    @BindView(R2.id.today_bonus_amount)
    TextView todayBonusAmount;
    @BindView(R2.id.fl_today_profit)
    FrameLayout flTodayProfit;
    @BindView(R2.id.added_bonus_amount)
    TextView addedBonusAmount;
    @BindView(R2.id.tv_progress_finshed)
    TextView tvProgressFinshed;
    @BindView(R2.id.progress_to_get)
    ProgressBar progress_to_get;
//    @BindView(R2.id.tv_banner)
//    TextViewBanner tv_banner;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_fortune;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        try {
            tvEachMoney.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            todayBonusAmount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            addedBonusAmount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            kittyCount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            int todayDivide = SPUtils.getInt(Utils.getContext(), SPConfig.TODAY_DIVIDE, 0);
            tvEachMoney.setText(NumberUtil.getWanDividedNumber(BigDecimal.valueOf(todayDivide)).toString() + Utils.getString(R.string.cny_unit));
            initKittyMoney();
            initProgress();
//            initPublish();
        } catch (Exception e) {
        }
    }

    private void initPublish() {
//        List<String> noticeModels = SPUtils.getDataList(SPConfig.NOTICE_LIST);
//        tv_banner.setMTexts(noticeModels);
//        tv_banner.invalidate();
//        tv_banner.setFrontColor(Utils.getColor(R.color.color_FF5E6E));
//        tv_banner.setMDuration(1200);
//        tv_banner.setMInterval(200);
    }

    private void initProgress() {
        HttpUtils.getMapInfo(new IResponse<MapModel>() {
            @Override
            public void onSuccess(BaseResponse<MapModel> baseResponse) {
                try {
                    if (baseResponse.code == 0 && null != baseResponse.data) {
                        MapModel mapModel = baseResponse.data;
                        float percent = mapModel.getPercent() * 100;
                        SPUtils.putString(FortuneKittyProfitActivity.this, SPConfig.PERCENT_MAP, String.valueOf(mapModel.getPercent()));
                        progress_to_get.setProgress(percent > 0 && percent < 1 ? 1 : (int) percent);
                        tvProgressFinshed.setText(Html.fromHtml(Utils.getString(R.string.finished_progress_font) + "<font color=\"#FF4B5D\">" + BigDecimal.valueOf(percent).setScale(2, RoundingMode.HALF_EVEN) + "%</font>" + Utils.getString(R.string.finished_progress_last)));
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }

        });
    }

    private void initKittyMoney() {
        HttpUtils.getKittyMoney(new IResponse<KittyMoneyModel>() {
            @Override
            public void onSuccess(BaseResponse<KittyMoneyModel> baseResponse) {
                if (baseResponse.code == 0 && null != baseResponse.data) {
                    try {
                        KittyMoneyModel kittyMoneyModel = baseResponse.data;
                        todayBonusAmount.setText(NumberUtil.getWanDividedNumber(kittyMoneyModel.getToday_dividend(), 2).toString() + Utils.getString(R.string.cny_unit));
                        addedBonusAmount.setText(NumberUtil.getWanDividedNumber(kittyMoneyModel.getSum_income(), 2).toString() + Utils.getString(R.string.cny_unit));
                        kittyCount.setText(String.valueOf(kittyMoneyModel.getKitty_count()) + Utils.getString(R.string.unit_zhi));
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }


    @OnClick(R2.id.fl_get_more)
    public void learnMore() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.fortune_title));
            webViewModel.setUrl(Utils.getString(R.string.fortune_learn_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(FortuneKittyProfitActivity.this, WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.ll_fortune_profit)
    public void todayDivided() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.today_bonus_detail));
            webViewModel.setUrl(Utils.getString(R.string.today_bonus_detail_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(FortuneKittyProfitActivity.this, WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.iv_back)
    public void iv_back() {
        finish();
    }

    @OnClick(R2.id.fl_to_speed)
    public void fl_to_speed() {
        try {
            Intent intent = new Intent(FortuneKittyProfitActivity.this, MyFortuneKittyActivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }
}

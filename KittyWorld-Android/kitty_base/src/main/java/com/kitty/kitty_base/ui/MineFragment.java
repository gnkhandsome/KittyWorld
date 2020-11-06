package com.kitty.kitty_base.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kitty.kitty_base.BuildConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseFragment;
import com.kitty.kitty_base.base.WebViewActivity;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.MapModel;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.model.WebViewModel;
import com.kitty.kitty_base.ui.AssetCenterActivity;
import com.kitty.kitty_base.ui.MessageCenterActivity;
import com.kitty.kitty_base.ui.MyFortuneKittyActivity;
import com.kitty.kitty_base.utils.GlideUtils;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.compress.BitmapMergeUtil;
import com.kitty.kitty_base.zxing.utils.QRCodeHelper;
import com.kitty.kitty_res.R2;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.math.BigDecimal;
import java.math.RoundingMode;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {

    @BindView(R2.id.iv_icon)
    ImageView ivIcon;
    @BindView(R2.id.tv_name_wx)
    TextView tvNameWx;
    @BindView(R2.id.tv_number)
    TextView tvNumWx;
    @BindView(R2.id.tv_my_asset)
    TextView myAsset;
    @BindView(R2.id.tv_my_invite_code)
    TextView inviteCode;
    @BindView(R2.id.tv_complete_progress)
    TextView tv_complete_progress;
    @BindView(R2.id.pb_complete_progress)
    ProgressBar pb_complete_progress;
    @BindView(R2.id.refreshLayout)
    RefreshLayout refreshLayout;
    UserProfile userProfile;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onUserVisible() {
        loadMineData();
    }

    @Override
    protected void onUserVisibleResume() {
        loadMineData();
    }

    @Override
    protected void initData() {
        loadMineData();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadMineData();
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setEnableLoadMore(false);
    }


    private void loadMineData() {
        String token = SPUtils.getString(getContext(), SPConfig.TOKEN);
        HttpUtils.getAccountInfo(token, new IResponse<UserProfile>() {
            @Override
            public void onSuccess(BaseResponse<UserProfile> baseResponse) {
                try {
                    if (null != baseResponse && null != baseResponse.data) {
                        userProfile = baseResponse.data;
                        GlideUtils.loadCirclePic(getContext(), baseResponse.data.getAvatar(), ivIcon);
                        tvNameWx.setText(baseResponse.data.getNickname());
                        tvNumWx.setText(Utils.getString(R.string.kitty_mine_wx_num) + baseResponse.data.getId());
                        myAsset.setText("￥" + NumberUtil.getWanDividedNumber(baseResponse.data.getMoney(), 2).toString());
                        inviteCode.setText(String.valueOf(baseResponse.data.getId()));
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
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
                        SPUtils.putString(getContext(), SPConfig.PERCENT_MAP, String.valueOf(mapModel.getPercent()));
                        pb_complete_progress.setProgress(percent > 0 && percent < 1 ? 1 : (int) percent);
                        tv_complete_progress.setText(BigDecimal.valueOf(percent).setScale(2, RoundingMode.HALF_EVEN) + "%");
                        tv_complete_progress.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

//    private void refreshQrCodeImage() {
//        try {
//            long user_id = SPUtils.getLong(Utils.getContext(), SPConfig.USER_ID, 0L);
//            String message = BuildConfig.INVITE_URL + user_id;
//            qrBitmap = QRCodeHelper.generateBitmap(message, Utils.dip2px(199), Utils.dip2px(199));
//            qrCode.setImageBitmap(qrBitmap);
//        } catch (Exception e) {
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void iniListener() {

    }

    @OnClick(R2.id.iv_ar_code)
    public void myQrCode() {
        try {
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.USERPROFILE, userProfile);
            Intent intent = new Intent(getActivity(), MineQrCodeActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_message)
    public void toMessageCenter() {
        try {
            Intent intent = new Intent(getActivity(), MessageCenterActivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_my_asset)
    public void toAssetCenter() {
        try {
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.USERPROFILE, userProfile);
            Intent intent = new Intent(getActivity(), AssetCenterActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick({R2.id.ll_mine_setting, R2.id.ll_to_setting})
    public void setting() {
        try {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_partner)
    public void partner() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.web_partner_title));
            webViewModel.setUrl(Utils.getString(R.string.web_partner_title_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_helper_center)
    public void helperCenter() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.mine_helper_center));
            webViewModel.setUrl(Utils.getString(R.string.mine_helper_center_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.rl_drawback_author)
    public void drawbackAuthor() {
        try {
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.USERPROFILE, userProfile);
            Intent intent = new Intent(getActivity(), BindAlPayActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.rl_my_invite_code)
    public void toMyInvite() {
        try {
            Intent intent = new Intent(getActivity(), MyInviteCodeAcivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.rl_to_get_fortune_kitty)
    public void toMyFortuneKitty() {
        try {
            Intent intent = new Intent(getActivity(), MyFortuneKittyActivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

}

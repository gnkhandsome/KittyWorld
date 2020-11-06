package com.kitty.kitty_home.ui;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseDialogActivity;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.constant.AdsType;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.fragmentdialog.MoreCouponDialog;
import com.kitty.kitty_base.fragmentdialog.RewardDialogUtil;
import com.kitty.kitty_base.interfaces.IAdsLoaded;
import com.kitty.kitty_base.model.LuckWheelModel;
import com.kitty.kitty_base.model.RewardModel;
import com.kitty.kitty_base.router.RouterActivityPath;
import com.kitty.kitty_base.utils.AdviseUtil;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.ResourceUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.singleton.MainHandler;
import com.kitty.kitty_base.views.rotate.RotateLayoutView;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R;
import com.kitty.kitty_res.R2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kitty_protos.MessageOuterClass;
import kitty_protos.RewardObjectOuterClass;
import kitty_protos.S2CLuckyWheel;
import kitty_protos.S2CPangolinReward;


@Route(path = RouterActivityPath.ACTIVITY_TURNPLATE_PATH)
public class TurnPlateActivity extends BaseDialogActivity {
    @BindView(R2.id.tv_turnplate_coupon_count)
    TextView tvTurnplateCouponCount;
    @BindView(R2.id.tv_default_coupon)
    TextView defaultCoupon;
    @BindView(R2.id.rv_rotateview)
    RotateLayoutView tpvView;
    @BindView(R2.id.tv_get_coupon)
    TextView getCoupon;
    @BindView(R2.id.tv_start)
    TextView tv_start;
    private RewardObjectOuterClass.RewardObject rewardObject;
    private ArrayList<Integer> kittyLevelList;
    private AssetFileDescriptor turn_begin;
    private AssetFileDescriptor turn_end;
    private MediaPlayer mediaPlayer;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_turnplate;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        try {
            setFinishOnTouchOutside(false);
            Bundle bundle = getIntent().getExtras();
            if (null != bundle) {
                kittyLevelList = (ArrayList<Integer>) bundle.getSerializable(IntentConfig.KITTY_LEVEL_LIST);
            }
            int wheelCount = SPUtils.getInt(Utils.getContext(), SPConfig.WHEEL_COUNT, 0);
            tvTurnplateCouponCount.setText(Utils.getString(R.string.turnplate_coupon_count) + NumberUtil.getPositiveInteger(wheelCount));
            RewardModel<Double> rewardModel = FormDataUtil.getRewardModelMap().get("luckywheel_default");
            defaultCoupon.setText(Utils.getString(R.string.turnpalte_coupon_tip_front)
                    + (rewardModel.getContent().intValue() < 0 ? 5 : rewardModel.getContent().intValue())
                    + Utils.getString(R.string.turnpalte_coupon_tip_end));
            showGetCoupon(wheelCount);
            initRotate();
            initSound();
            turn_begin = Utils.getResources().getAssets().openFd("music/turn_begin.mp3");
            turn_end = Utils.getResources().getAssets().openFd("music/turn_end.mp3");
        } catch (Exception e) {
        }
    }


    private void initSound() {
        try {
            boolean isSoundOpen = SPUtils.getBoolean(TurnPlateActivity.this, SPConfig.SOUND_STATUS, true);
            if (null != mediaPlayer && !isSoundOpen) {
                mediaPlayer.release();
                mediaPlayer = null;
            } else if (null == mediaPlayer && isSoundOpen) {
                mediaPlayer = new MediaPlayer();
            }
        } catch (Exception e) {
        }
    }

    private void showGetCoupon(int wheelCount) {
        getCoupon.setVisibility(wheelCount > 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        try {
            if (data instanceof MessageOuterClass.Message) {
                MessageOuterClass.Message message = (MessageOuterClass.Message) data;
                if (message.getMessageId() == MessageOuterClass.Message.S2CLUCKYWHEEL_FIELD_NUMBER) {
                    S2CLuckyWheel.S2C_LuckyWheel s2C_luckyWheel = message.getS2CLuckyWheel();
                    int wheelCount = s2C_luckyWheel.getLuckyWheelCount();
                    tvTurnplateCouponCount.setText(Utils.getString(R.string.turnplate_coupon_count) + NumberUtil.getPositiveInteger(wheelCount));
                    SPUtils.putInt(Utils.getContext(), SPConfig.WHEEL_COUNT, wheelCount);
                    showGetCoupon(wheelCount);
                    List<RewardObjectOuterClass.RewardObject> rewardObjects = s2C_luckyWheel.getRewardsList();
                    if (rewardObjects.size() > 0) {
                        rewardObject = rewardObjects.get(0);
                        tpvView.startAnimation(Integer.valueOf(rewardObject.getExtra().split("\\|")[0]) - 1);
                        tpvView.setStart(true);
                    }
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CPANGOLINREWARD_FIELD_NUMBER) {
                    S2CPangolinReward.S2C_PangolinReward s2C_pangolinReward = message.getS2CPangolinReward();
                    if (TextUtils.equals(s2C_pangolinReward.getRewardName(), AdsType.WHEEL_TIMES)) {
                        int wheelTime = SPUtils.getInt(Utils.getContext(), SPConfig.WHEEL_COUNT, 0);
                        int remainWheelTime = (Integer.valueOf(s2C_pangolinReward.getRewardAmount()) + wheelTime);
                        tvTurnplateCouponCount.setText(Utils.getString(R.string.turnplate_coupon_count) + NumberUtil.getPositiveInteger(remainWheelTime));
                        showGetCoupon(remainWheelTime);
                        SPUtils.putInt(Utils.getContext(), SPConfig.WHEEL_COUNT, (Integer.valueOf(s2C_pangolinReward.getRewardAmount()) + wheelTime));
                        SPUtils.putInt(Utils.getContext(), SPConfig.AD_REMAIN_COUNT, s2C_pangolinReward.getRemainAdNum());
                        SPUtils.putLong(Utils.getContext(), SPConfig.LAST_VEDIO_TIME, System.currentTimeMillis());
                    }

                    if (TextUtils.equals(s2C_pangolinReward.getRewardName(), AdsType.WHEEL_BOX)) {
                        SPUtils.putInt(Utils.getContext(), SPConfig.AD_REMAIN_COUNT, s2C_pangolinReward.getRemainAdNum());
                        SPUtils.putLong(Utils.getContext(), SPConfig.LAST_VEDIO_TIME, System.currentTimeMillis());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRotate() {
        try {
            List<LuckWheelModel> luckWheelModels = CatHelperUtil.getLuckWheelList();
            List<Integer> imageIcons = new ArrayList<>();
            List<String> strName = new ArrayList<>();
            for (LuckWheelModel luckWheelModel : luckWheelModels) {
                imageIcons.add(ResourceUtil.getDrawableId(TurnPlateActivity.this, luckWheelModel.getItem_icon()));
                strName.add(luckWheelModel.getDescription());
            }
            tpvView.setImageIcon(imageIcons);
            tpvView.setStrName(strName);
        } catch (Exception e) {
        }
    }


    @Override
    protected void setListener() {
        //获取到位置
        tpvView.setOnCallBackPosition(new RotateLayoutView.onCallBackPosition() {
            @Override
            public void onStart() {
                try {
                    int wheelCount = SPUtils.getInt(Utils.getContext(), SPConfig.WHEEL_COUNT, 0);
                    if (wheelCount <= 0) {
                        MoreCouponDialog moreCouponDialog = new MoreCouponDialog();
                        moreCouponDialog.show(getSupportFragmentManager(), "moreCouponDialog");
                        return;
                    }
                    MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setMessageId(1009);
                    SocketManager.send(callObject);
                } catch (Exception e) {
                }
            }

            @Override
            public void onAnimationStart() {
                startRoate();
            }

            @Override
            public void onStopPosition(int pos) {
                try {
                    if (null != rewardObject) {
                        if (null != mediaPlayer) {
                            mediaPlayer.release();
                            mediaPlayer = null;
                            endRoate();
                        }
                        RewardObject rewardObj = new RewardObject();
                        rewardObj.amount = String.valueOf(rewardObject.getAmount());
                        rewardObj.rewardType = rewardObject.getType();
                        rewardObj.kittyLevelList = kittyLevelList;
                        rewardObj.extra = rewardObject.getExtra();
                        RewardDialogUtil.showRewardDialog(rewardObj, getSupportFragmentManager());
                    }
                    MainHandler.getInstance().postDelayed(() -> tpvView.setStart(false), 1000);
                    MainHandler.getInstance().postDelayed(() -> tv_start.setClickable(true), 1000);
                } catch (Exception e) {
                    if (null != tpvView) {
                        tpvView.setStart(false);
                    }
                    if (null != tv_start) {
                        tv_start.setClickable(true);
                    }
                }
            }
        });
    }

    private void startRoate() {
        try {
            initSound();
            if (null != mediaPlayer && !mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(turn_begin.getFileDescriptor(), turn_begin.getStartOffset(), turn_begin.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.setOnErrorListener((mp, what, extra) -> false);
            }

        } catch (Exception e) {
        }
    }


    private void endRoate() {
        try {
            initSound();
            if (null != mediaPlayer && !mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(turn_end.getFileDescriptor(), turn_end.getStartOffset(), turn_end.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.setOnErrorListener((mp, what, extra) -> false);
            }

        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mediaPlayer) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @OnClick(R2.id.tv_start)
    public void tv_start(View view) {
        try {
            int wheelCount = SPUtils.getInt(Utils.getContext(), SPConfig.WHEEL_COUNT, 0);
            if (wheelCount <= 0) {
                MoreCouponDialog moreCouponDialog = new MoreCouponDialog();
                moreCouponDialog.show(getSupportFragmentManager(), "moreCouponDialog");
                return;
            }
            tv_start.setClickable(false);
            MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setMessageId(1009);
            SocketManager.send(callObject);
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.ll_turn_plate_close)
    public void onCloseClicked(View view) {
        finish();
    }

    @OnClick(R2.id.tv_get_coupon)
    public void get_Coupon(View view) {
        try {
            int wheelCount = SPUtils.getInt(Utils.getContext(), SPConfig.WHEEL_COUNT, 0);
            if (wheelCount > 0) {
                ToastUtils.showLong(R.string.kitty_res_has_coupon);
                return;
            }
            MoreCouponDialog moreCouponDialog = new MoreCouponDialog();
            moreCouponDialog.show(getSupportFragmentManager(), "moreCouponDialog");
        } catch (Exception e) {
        }
    }

}

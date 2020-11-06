package com.kitty.kitty_base.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.event.HomeRefreshEvent;
import com.kitty.kitty_base.fragmentdialog.SpecialKittyDialog;
import com.kitty.kitty_base.model.SpecialLevelRewardModel;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.views.special_luck_panel.SpecialLuckyMonkeyPanelView;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R2;

import org.greenrobot.eventbus.EventBus;

import java.nio.ByteBuffer;

import butterknife.BindView;
import butterknife.OnClick;
import kitty_protos.C2SMergeKitty;
import kitty_protos.MessageOuterClass;
import kitty_protos.SyncKittyInfoOuterClass;

public class SpecialLevelBonusDialog extends BaseDialogFragment {
    @BindView(R2.id.lucky_panel)
    SpecialLuckyMonkeyPanelView luckyPanel;
    @BindView(R2.id.btn_action)
    TextView btn_action;
    private int merge_from;
    private int merge_to;

    public static SpecialLevelBonusDialog newInstance( Bundle bundle) {
        SpecialLevelBonusDialog fragment = new SpecialLevelBonusDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected boolean needForbidBackPress() {
        return false;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return false;
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.activity_special_level_bonus;
    }

    @Override
    protected void initData() {
        getDialog().setCanceledOnTouchOutside(false);
        Bundle bundle = getArguments();
        if (null != bundle) {
            merge_from = bundle.getInt(IntentConfig.MERGE_FROM);
            merge_to = bundle.getInt(IntentConfig.MERGE_TO);
        }
    }

    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        if (data instanceof MessageOuterClass.Message) {
            MessageOuterClass.Message message = (MessageOuterClass.Message) data;
            try {
                if (message.getMessageId() == MessageOuterClass.Message.S2CMERGEKITTY_FIELD_NUMBER) {
                    SyncKittyInfoOuterClass.kittyLevelList level = message.getSyncKittyInfo().getKittyLevelList(merge_to);
                    SpecialLevelRewardModel specialLevelRewardModel = null;
                    for (SpecialLevelRewardModel specialLevelReward : FormDataUtil.getSpecialLevelReward()) {
                        if (level.getLevel() == specialLevelReward.level) {
                            specialLevelRewardModel = specialLevelReward;
                        }
                    }
                    if (null == specialLevelRewardModel) {
                        return;
                    }
                    btn_action.setClickable(false);
                    if (!luckyPanel.isGameRunning()) {
                        luckyPanel.startGame();
                        SpecialLevelRewardModel finalSpecialLevelRewardModel = specialLevelRewardModel;
                        luckyPanel.addAnimationListener(new SpecialLuckyMonkeyPanelView.IAnimationListener() {
                            @Override
                            public void onAnimationStop() {
                                try {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(IntentConfig.SPECIALLEVELREWARDMODEL, finalSpecialLevelRewardModel);
                                    SpecialKittyDialog specialKittyDialog = new SpecialKittyDialog();
                                    specialKittyDialog.setArguments(bundle);
                                    specialKittyDialog.show(getFragmentManager(), "specialKittyDialog");
                                    specialKittyDialog.setOnDismissListener(new BaseDialogFragment.IDismissListener() {
                                        @Override
                                        public void dismissed() {
                                            dismiss();
                                            EventBus.getDefault().post(new HomeRefreshEvent());
//                                            SPUtils.putBoolean(Utils.getContext(), SPConfig.LEVEL_BONUS_KITTY, true);
//                                            HomeFragment.homeFragment.initAnimation(level.getLevel(), false, null);
                                        }
                                    });
                                } catch (Exception e) {
                                    if (null != btn_action) {
                                        btn_action.setClickable(true);
                                    }
                                }
                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    luckyPanel.tryToStop(finalSpecialLevelRewardModel.reward_id - 1);
                                } catch (Exception e) {
                                    if (null != luckyPanel) {
                                        luckyPanel.tryToStop(0);
                                    }
                                    if (null != btn_action) {
                                        btn_action.setClickable(true);
                                    }
                                    ToastUtils.showLong(R.string.unknow_fail);
                                }
                            }
                        }, 4000);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    @OnClick(R2.id.btn_action)
    public void startTurn(View view) {
        C2SMergeKitty.C2S_MergeKitty.Builder c2S_mergeKitty = C2SMergeKitty.C2S_MergeKitty.newBuilder().setMoveFrom(merge_from).setMoveTo(merge_to);
        MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SMergeKitty(c2S_mergeKitty).setMessageId(MessageOuterClass.Message.C2SMERGEKITTY_FIELD_NUMBER);
        SocketManager.send(builder);
    }

    @OnClick(R2.id.ll_turn_plate_close)
    public void close(View view) {
        dismiss();
    }
}

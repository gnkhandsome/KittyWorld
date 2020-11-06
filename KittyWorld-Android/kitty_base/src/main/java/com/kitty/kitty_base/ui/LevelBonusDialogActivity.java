package com.kitty.kitty_base.ui;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogActivity;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.fragmentdialog.RewardDialogUtil;
import com.kitty.kitty_base.model.TaskLevelModel;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.singleton.MainHandler;
import com.kitty.kitty_base.views.luck_panel.LuckyMonkeyPanelView;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R2;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kitty_protos.C2SMergeBonus;
import kitty_protos.MessageOuterClass;
import kitty_protos.RewardObjectOuterClass;
import kitty_protos.S2CMergeBonus;

public class LevelBonusDialogActivity extends BaseDialogActivity {
    @BindView(R2.id.lucky_panel)
    LuckyMonkeyPanelView luckyPanel;
    private RewardObjectOuterClass.RewardObject rewardObject;
    private ArrayList<Integer> kittyLevelList;
    private int mergeLevel;
    private AssetFileDescriptor fd;
    private MediaPlayer mediaPlayer;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_level_bonus;
    }

    @Override
    protected void initData() {
        try {
            setFinishOnTouchOutside(false);
            Bundle bundle = getIntent().getExtras();
            if (null != bundle) {
                kittyLevelList = (ArrayList<Integer>) bundle.getSerializable(IntentConfig.KITTY_LEVEL_LIST);
                mergeLevel = bundle.getInt(IntentConfig.MERGE_LEVEL);
            }
            fd = Utils.getResources().getAssets().openFd("music/luckly_draw.mp3");
            initSound();
        } catch (Exception e) {
        }
    }

    private void initSound() {
        try {
            boolean isSoundOpen = SPUtils.getBoolean(LevelBonusDialogActivity.this, SPConfig.SOUND_STATUS, true);
            if (null != mediaPlayer && !isSoundOpen) {
                mediaPlayer.release();
                mediaPlayer = null;
            } else if (null == mediaPlayer && isSoundOpen) {
                mediaPlayer = new MediaPlayer();
            }
        } catch (Exception e) {
        }
    }


    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        if (data instanceof MessageOuterClass.Message) {
            MessageOuterClass.Message message = (MessageOuterClass.Message) data;
            if (message.getMessageId() == MessageOuterClass.Message.S2CMERGEBONUS_FIELD_NUMBER) {
                try {
                    S2CMergeBonus.S2C_MergeBonus s2C_mergeBonus = message.getS2CMergeBonus();
                    List<RewardObjectOuterClass.RewardObject> rewardObjects = s2C_mergeBonus.getRewardsList();
                    if (rewardObjects.size() > 0) {
                        rewardObject = rewardObjects.get(0);
                        if (!luckyPanel.isGameRunning()) {
                            luckyPanel.startGame();
                            if (null != mediaPlayer && !mediaPlayer.isPlaying()) {
                                mediaPlayer.reset();
                                mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                                mediaPlayer.setOnErrorListener((mp, what, extra) -> false);
                            }
                            luckyPanel.addAnimationListener(new LuckyMonkeyPanelView.IAnimationListener() {
                                @Override
                                public void onAnimationStop() {
                                    // 显示奖励弹窗
                                    try {
                                        if (null != mediaPlayer) {
                                            mediaPlayer.release();
                                            mediaPlayer = null;
                                        }
                                        if (null != rewardObject) {
                                            MainHandler.getInstance().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    HashMap<String, TaskLevelModel> taskLevelModels = FormDataUtil.getTaskLevel();
                                                    RewardObject rewardObj = new RewardObject();
                                                    rewardObj.amount = String.valueOf(rewardObject.getAmount());
                                                    rewardObj.kittyLevelList = kittyLevelList;
                                                    rewardObj.rewardType = (!taskLevelModels.containsKey(String.valueOf(mergeLevel + 1)) ? rewardObject.getType() : 10);
                                                    rewardObj.extra = rewardObject.getExtra();
                                                    RewardDialogUtil.showRewardDialog(rewardObj, getSupportFragmentManager(), () -> {
                                                        finish();
                                                    });
                                                }
                                            }, 600);
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            });

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        luckyPanel.tryToStop(Integer.valueOf(rewardObject.getExtra().split("\\|")[0]) - 1);
                                    } catch (Exception e) {
                                    }
                                }
                            }, 4000);
                        }
                    }
                } catch (Exception e) {
                }
            }
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

    @OnClick(R2.id.btn_action)
    public void startTurn(View view) {
        if (mergeLevel > 0) {
            C2SMergeBonus.C2S_MergeBonus.Builder c2S_mergeBonus = C2SMergeBonus.C2S_MergeBonus.newBuilder().setLevel(mergeLevel);
            MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SMergeBonus(c2S_mergeBonus).setMessageId(MessageOuterClass.Message.C2SMERGEBONUS_FIELD_NUMBER);
            SocketManager.send(callObject);
        }
    }
}

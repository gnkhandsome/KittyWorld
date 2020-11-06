package com.kitty.kitty_base.fragmentdialog;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_res.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.model.CatNameModel;
import com.kitty.kitty_base.model.MessageModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R2;

import java.nio.ByteBuffer;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import kitty_protos.C2SReceiveMessage;
import kitty_protos.MessageOuterClass;
import kitty_protos.S2CReceiveMessage;

public class MessageReceiveDialog extends BaseDialogFragment {

    @BindView(R2.id.iv_type_icon)
    ImageView ivTypeIcon;
    @BindView(R2.id.tv_reward_desc)
    TextView tvRewardDesc;
    @BindView(R2.id.tv_to_receive)
    TextView tvReceive;
    @BindView(R2.id.tv_kitty_level)
    TextView tvKittyLevel;
    private MessageModel.MessageModelItem messageModelItem;


    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        try {
            if (null != bundle) {
                messageModelItem = (MessageModel.MessageModelItem) bundle.getSerializable(IntentConfig.MESSAGE_MODEL);
                if (null != messageModelItem) {
                    ivTypeIcon.setImageResource(getImageFromType(messageModelItem.getReward_info()));
                    tvRewardDesc.setText(Html.fromHtml(getDescByType(messageModelItem)));
                    tvReceive.setText(messageModelItem.getStatus() == 0 ? Utils.getString(R.string.click_to_receive) : Utils.getString(R.string.received));
                    tvReceive.setBackgroundResource(messageModelItem.getStatus() == 0 ? R.drawable.activity_repertory_draw : R.drawable.unreceived_back);
                }
            }
        } catch (Exception e) {
        }
    }

    private String getDescByType(MessageModel.MessageModelItem messageModelItem) {
        HashMap<String, CatNameModel> nameModelHashMap = CatHelperUtil.getKittyStringMap();
        MessageModel.MessageModelItem.RewardInfoBean rewardInfoBean = messageModelItem.getReward_info();
        String timeDesc = null;
        CatNameModel catNameModel = null;
        if (messageModelItem.getType() == 1) {
            catNameModel = nameModelHashMap.get("str_gm_content_type" + rewardInfoBean.getType());
        } else if (messageModelItem.getType() == 2) {
            catNameModel = nameModelHashMap.get("str_build_content_type" + rewardInfoBean.getType());
        } else if (messageModelItem.getType() == 3 || messageModelItem.getType() == 4) {
            catNameModel = nameModelHashMap.get("str_rebpacket_content_type" + rewardInfoBean.getType());
        } else if (messageModelItem.getType() == 5) {
            catNameModel = nameModelHashMap.get("str_task_content_type" + rewardInfoBean.getType());
        } else if (messageModelItem.getType() == 6) {
            catNameModel = nameModelHashMap.get("str_global_content_type" + rewardInfoBean.getType());
        } else {
            return catNameModel.getName_cn();
        }
        String desc = catNameModel.getName_cn();
        if (rewardInfoBean.getType() == 5) {
            String timeD;
            long time = Long.valueOf(rewardInfoBean.getAmount()) / 60;
            if (time > 0) {
                timeD = time + Utils.getString(R.string.minite);
            } else {
                timeD = Utils.getString(R.string.forever);
            }
            timeDesc = desc.replace("###", "<font color=\"#FF4B5D\"><b>" + timeD + "</b></font>");
        } else if (rewardInfoBean.getType() == 3) {
            long time = Long.valueOf(rewardInfoBean.getAmount()) / 60;
            String timeK = null;
            if (time > 0) {
                timeK = time + Utils.getString(R.string.minite);
            }
            timeDesc = desc.replace("###", "<font color=\"#FF4B5D\"><b>" + timeK + "</b></font>");
        } else if (rewardInfoBean.getType() == 2) {
            timeDesc = desc.replace("###", "<font color=\"#FF4B5D\"><b>" + rewardInfoBean.getExtra() + "</b></font>");
        } else if (rewardInfoBean.getType() == 7) {
            timeDesc = desc.replace("###", "<font color=\"#FF4B5D\"><b>" + rewardInfoBean.getAmount() + Utils.getString(R.string.unit_page) + "</b></font>");
            int wheelCount = SPUtils.getInt(Utils.getContext(), SPConfig.WHEEL_COUNT, 0);
            SPUtils.putInt(Utils.getContext(), SPConfig.WHEEL_COUNT, wheelCount + rewardInfoBean.getAmount());
        } else if (rewardInfoBean.getType() == 8) {
            timeDesc = desc.replace("###", "<font color=\"#FF4B5D\"><b>" + rewardInfoBean.getAmount() + Utils.getString(R.string.unit_page) + "</b></font>");
            int adTimes = SPUtils.getInt(Utils.getContext(), SPConfig.AD_REMAIN_COUNT, 0);
            SPUtils.putInt(Utils.getContext(), SPConfig.AD_REMAIN_COUNT, adTimes + rewardInfoBean.getAmount());
        } else if (rewardInfoBean.getType() == 1) {
            timeDesc = desc.replace("###", "<font color=\"#FF4B5D\"><b>" + nameModelHashMap.get("str_cat_lv" + rewardInfoBean.getId()).getName_cn() + "</b></font>");
        } else if (rewardInfoBean.getType() == 6) {
            timeDesc = desc.replace("###", "<font color=\"#FF4B5D\"><b>" + NumberUtil.formatInteger(rewardInfoBean.getAmount()) + "</b></font>");
        }
        return timeDesc;
    }


    private int getImageFromType(MessageModel.MessageModelItem.RewardInfoBean rewardInfoBean) {
        int type = rewardInfoBean.getType();
        if (type == 8) {
            return R.drawable.ads_coupon_icon;
        } else if (type == 5) {
            return R.drawable.ic_kitty_level45;
        } else if (type == 2 || type == 3) {
            return R.drawable.hour_bonus_icon;
        } else if (type == 1) {
            tvKittyLevel.setVisibility(View.VISIBLE);
            tvKittyLevel.setText(String.valueOf(rewardInfoBean.getId() > 38 ? 38 : rewardInfoBean.getId()));
            return ImageSourceUtil.getResorceByLevel(rewardInfoBean.getId());
        } else if (type == 7) {
            return R.drawable.rotate_coupon_icon;
        } else if (type == 6) {
            if (rewardInfoBean.getAmount() == 5) {
                return R.drawable.five_box_coin;
            } else if (rewardInfoBean.getAmount() == 10) {
                return R.drawable.ten_box_coin_icon;
            }
        }
        return 0;
    }

    @Override
    protected boolean needForbidBackPress() {
        return false;
    }

    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        try {
            if (data instanceof MessageOuterClass.Message) {
                MessageOuterClass.Message message = (MessageOuterClass.Message) data;
                if (message.getMessageId() == MessageOuterClass.Message.S2CRECEIVEMESSAGE_FIELD_NUMBER) {
                    S2CReceiveMessage.S2C_ReceiveMessage s2CReceiveMessage = message.getS2CReceiveMessage();
                    if (s2CReceiveMessage.getCode() == 0) {
                        ToastUtils.showLong(R.string.receive_success);
                        if (null != iOnClickListener) {
                            iOnClickListener.onConfirmClick();
                        }
                        dismiss();
                    } else if (s2CReceiveMessage.getCode() == 2 || s2CReceiveMessage.getCode() == 1) {
                        ToastUtils.showLong(getString(R.string.position_filled_please_try_litter));
                        dismiss();
                    } else {
                        ToastUtils.showLong(R.string.unknow_fail);
                    }
                    dismissDialog();
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return true;
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.dialog_message_receive;
    }

    @OnClick(R2.id.close_dialog)
    public void close(View view) {
        dismiss();
        if (null != iOnClickListener) {
            iOnClickListener.onDismissClick();
        }
    }

    @OnClick(R2.id.tv_to_receive)
    public void receive(View view) {
        if (null != messageModelItem) {
            showDialogEachTime();
            C2SReceiveMessage.C2S_ReceiveMessage.Builder builder = C2SReceiveMessage.C2S_ReceiveMessage.newBuilder().setMessageId(messageModelItem.getId());
            MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SReceiveMessage(builder).setMessageId(MessageOuterClass.Message.C2SRECEIVEMESSAGE_FIELD_NUMBER);
            SocketManager.send(callObject);
        }
    }
}

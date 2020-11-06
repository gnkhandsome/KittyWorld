package com.kitty.kitty_base.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.adapter.AdventureGainAdapter;
import com.kitty.kitty_base.adapter.AdventureMyPetAdapter;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.base.WebViewActivity;
import com.kitty.kitty_base.constant.AdsType;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.fragmentdialog.AdventureGainDialog;
import com.kitty.kitty_base.fragmentdialog.RewardDialogUtil;
import com.kitty.kitty_base.interfaces.IAdsLoaded;
import com.kitty.kitty_base.model.AdventureDynamicModel;
import com.kitty.kitty_base.model.AdventureMyPetModel;
import com.kitty.kitty_base.model.CatNameModel;
import com.kitty.kitty_base.model.WebViewModel;
import com.kitty.kitty_base.utils.AdviseUtil;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.CountDownTimerUtil;
import com.kitty.kitty_base.utils.DateUtil;
import com.kitty.kitty_base.utils.JsonFileUtil;
import com.kitty.kitty_base.utils.ResourceUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.TimeTickUtil;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.util.LogUtil;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import kitty_protos.Adventure;
import kitty_protos.C2SReceiveAdventure;
import kitty_protos.C2SStartAdventure;
import kitty_protos.C2SSyncAdventure;
import kitty_protos.MessageOuterClass;
import kitty_protos.S2CPangolinReward;
import kitty_protos.S2CReceiveAdventure;
import kitty_protos.S2CStartAdventure;
import kitty_protos.S2CSyncAdventure;

public class AdventureActivity extends BaseActivity {
    @BindView(R2.id.gv_adventure_gain)
    GridView gvAdventureGain;
    @BindView(R2.id.gv_my_pet)
    GridView gvMyPet;
    @BindView(R2.id.pet_stamina)
    TextView petStamina;
    @BindView(R2.id.tv_dynamic_title)
    TextView tvDynamicTitle;
    @BindView(R2.id.tv_dynamic_content)
    TextView tvDynamicContent;
    @BindView(R2.id.tv_dynamic_time)
    TextView tvDynamicTime;
    @BindView(R2.id.tv_dynamic_title_two)
    TextView tvDynamicTitleTwo;
    @BindView(R2.id.tv_dynamic_content_two)
    TextView tvDynamicContentTwo;
    @BindView(R2.id.tv_dynamic_time_two)
    TextView tvDynamicTimeTwo;
    @BindView(R2.id.rl_adventure_event)
    RelativeLayout adventure_event;
    @BindView(R2.id.rl_adventure_event_two)
    RelativeLayout adventure_event_two;
    @BindView(R2.id.tv_remain_time)
    TextView tv_remain_time;
    @BindView(R2.id.start_adventure)
    TextView start_adventure;
    @BindView(R2.id.ll_adventure_info)
    LinearLayout ll_adventure_info;
    @BindView(R2.id.tv_empty)
    TextView tv_empty;
    @BindView(R2.id.iv_dynamic_icon_one)
    ImageView iv_dynamic_icon_one;
    @BindView(R2.id.iv_dynamic_icon_two)
    ImageView iv_dynamic_icon_two;
    private CountDownTimerUtil timeTickUtil;
    private RewardObject adventureRewardModel;
    private ArrayList<Integer> integers;
    private S2CStartAdventure.S2C_StartAdventure s2C_startAdventure;
    @BindView(R2.id.animation_view_person)
    LottieAnimationView animation_view_person;
    @BindView(R2.id.animation_kitty_one)
    LottieAnimationView animation_kitty_one;
    @BindView(R2.id.animation_kitty_two)
    LottieAnimationView animation_kitty_two;
    @BindView(R2.id.animation_kitty_three)
    LottieAnimationView animation_kitty_three;
    @BindView(R2.id.animation_kitty_four)
    LottieAnimationView animation_kitty_four;
    @BindView(R2.id.animation_kitty_five)
    LottieAnimationView animation_kitty_five;
    @BindView(R2.id.sv_ground)
    HorizontalScrollView sv_ground;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_adventure;
    }


    @Override
    protected void initData() {
        try {
            Bundle bundle = getIntent().getExtras();
            integers = bundle.getIntegerArrayList(IntentConfig.KITTY_LEVEL_LIST);
            initGridView();
            initAdventure();
            timeTickUtil = new CountDownTimerUtil(0, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String time = getTimeFromInt(millisUntilFinished);
                    if (null != tv_remain_time) {
                        tv_remain_time.setText(Html.fromHtml(Utils.getString(R.string.remain_time) + "&nbsp <font color=\"#FF6C7A\">" + time + "</font>"));
                    }
                }

                @Override
                public void onFinish() {
                    if (null != tv_remain_time) {
                        tv_remain_time.setText(Html.fromHtml(Utils.getString(R.string.remain_time) + "&nbsp <font color=\"#FF6C7A\">0:00</font>"));
                    }
                }
            };
            tv_remain_time.setText(Html.fromHtml(Utils.getString(com.kitty.kitty_base.R.string.remain_time) + "&nbsp <font color=\"#FF6C7A\">0:00</font>"));
            initAnim(0);
            initPersonAnim(SPUtils.getString(AdventureActivity.this, SPConfig.MAN_ANIMATION_TYPE, "nan.json"));
        } catch (Exception e) {
        }

    }

    private void initPersonAnim(String fileName) {
        try {
            // 人物动画
            animation_view_person.setAnimation(fileName);
            animation_view_person.loop(true);
            animation_view_person.playAnimation();

            // 背景动画
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float width = displayMetrics.widthPixels;
            TranslateAnimation translateAnimation1 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE,
                    -width, Animation.ABSOLUTE, 0f,
                    Animation.ABSOLUTE, 0f);
            translateAnimation1.setDuration(90000);
            translateAnimation1.setInterpolator(new LinearInterpolator());
            translateAnimation1.setRepeatCount(Integer.MAX_VALUE);
            translateAnimation1.setRepeatMode(Animation.RESTART);
            translateAnimation1.setFillAfter(false);
            sv_ground.startAnimation(translateAnimation1);
        } catch (Exception e) {
        }
    }

    private void initAnim(int level) {
        Map<Integer, LottieAnimationView> animationViewHashMap = new HashMap<>();
        animationViewHashMap.put(41, animation_kitty_one);
        animationViewHashMap.put(44, animation_kitty_two);
        animationViewHashMap.put(42, animation_kitty_three);
        animationViewHashMap.put(43, animation_kitty_four);
        animationViewHashMap.put(40, animation_kitty_five);
        if (!animationViewHashMap.containsKey(level)) {
            return;
        }
        animationViewHashMap.get(level).setVisibility(View.VISIBLE);
        String fileName = "kittys/level" + level + "/level" + level + ".json";
        String json = JsonFileUtil.getJson(Utils.getContext(), fileName);
        if (TextUtils.isEmpty(json)) {
            return;
        }
        animationViewHashMap.get(level).setAnimation(fileName);
        animationViewHashMap.get(level).setImageAssetsFolder("kittys/level" + level);
        animationViewHashMap.get(level).loop(true);
        animationViewHashMap.get(level).playAnimation();


    }

    private void initAdventure() {
        C2SSyncAdventure.C2S_SyncAdventure.Builder c2S_SyncAdventure = C2SSyncAdventure.C2S_SyncAdventure.newBuilder().setCallTimestamp((int) (System.currentTimeMillis() / 1000));
        MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SSyncAdventure(c2S_SyncAdventure).setMessageId(MessageOuterClass.Message.C2SSYNCADVENTURE_FIELD_NUMBER);
        SocketManager.send(callObject);
    }

    private void initGridView() {
        List<AdventureMyPetModel> adventureMyPetModels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            adventureMyPetModels.add(null);
        }
        AdventureMyPetAdapter adventureMyPetAdapter = new AdventureMyPetAdapter(adventureMyPetModels, AdventureActivity.this);
        gvMyPet.setAdapter(adventureMyPetAdapter);

        List<RewardObject> adventureRewards = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            adventureRewards.add(null);
        }
        AdventureGainAdapter adventureGainAdapter = new AdventureGainAdapter(adventureRewards, AdventureActivity.this);
        gvAdventureGain.setAdapter(adventureGainAdapter);
    }


    @Override
    protected void setListener() {
        gvAdventureGain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdventureGainAdapter adventureGainAdapter = (AdventureGainAdapter) parent.getAdapter();
                adventureRewardModel = adventureGainAdapter.getItem(position);
                if (null != adventureRewardModel && Double.valueOf(adventureRewardModel.amount) > 0) {
                    adventureRewardModel.kittyLevelList = integers;
                    C2SReceiveAdventure.C2S_ReceiveAdventure.Builder c2S_SyncAdventure = C2SReceiveAdventure.C2S_ReceiveAdventure.newBuilder().setPosition((position));
                    MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SReceiveAdventure(c2S_SyncAdventure).setMessageId(MessageOuterClass.Message.C2SRECEIVEADVENTURE_FIELD_NUMBER);
                    SocketManager.send(callObject);
                    showDialogEachTime();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        try {
            if (data instanceof MessageOuterClass.Message) {
                MessageOuterClass.Message message = (MessageOuterClass.Message) data;
                if (message.getMessageId() == MessageOuterClass.Message.S2CSYNCADVENTURE_FIELD_NUMBER) {
                    S2CSyncAdventure.S2C_SyncAdventure s2C_syncAdventure = message.getS2CSyncAdventure();
                    Log.d("探险原始数据==",s2C_syncAdventure.toString());
                    SPUtils.putInt(AdventureActivity.this, SPConfig.ADVENTURE_REMAIN_COUNT, s2C_syncAdventure.getVisitAdTimes());
                    SPUtils.putLong(AdventureActivity.this, SPConfig.ADVENTURE_END_TIME, s2C_syncAdventure.getEndTime());
                    SPUtils.putInt(AdventureActivity.this, SPConfig.ADVENTURE_STATUS, s2C_syncAdventure.getStatus());
                    start_adventure.setText(s2C_syncAdventure.getStatus() == 0 ? Utils.getString(R.string.go_to_adventure) : (s2C_syncAdventure.getVisitAdTimes() > 0 ? Utils.getString(R.string.add_power) : Utils.getString(R.string.wait_minut)));
                    if (s2C_syncAdventure.getCode() == 0) {

                        long adventureTime = s2C_syncAdventure.getEndTime() - (System.currentTimeMillis() / 1000);
                        if (null != timeTickUtil && adventureTime > 0) {
                            timeTickUtil.setCountdownInterval(1000);
                            timeTickUtil.setMillisInFuture(adventureTime * 1000);
                            timeTickUtil.start();
                        }
                        petStamina.setText(Utils.getString(R.string.total_power) + s2C_syncAdventure.getPower());
                        List<Adventure.AdventureEvent> adventureEvents = s2C_syncAdventure.getEventsList();
                        if (adventureEvents.size() > 0) {
                            ll_adventure_info.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.INVISIBLE);
                            adventure_event_two.setVisibility(adventureEvents.size() > 1 ? View.VISIBLE : View.GONE);
                            showAdventureList(adventureEvents);
                        } else {
                            ll_adventure_info.setVisibility(View.INVISIBLE);
                            tv_empty.setVisibility(View.VISIBLE);
                        }
                        Map<Integer, Integer> integerIntegerMap = s2C_syncAdventure.getKittiesMap();
                        TreeMap<Integer, Integer> treeMap = new TreeMap();
                        if (integerIntegerMap.size() > 0) {
                            List<AdventureMyPetModel> adventureMyPetModels = new ArrayList<>();
                            for (Map.Entry<Integer, Integer> entry : integerIntegerMap.entrySet()) {
                                treeMap.put(entry.getKey(), entry.getValue());
                            }
                            for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
                                AdventureMyPetModel adventureMyPetModel = new AdventureMyPetModel();
                                adventureMyPetModel.level = entry.getKey();
                                adventureMyPetModel.count = entry.getValue();
                                adventureMyPetModels.add(adventureMyPetModel);
                                initAnim(adventureMyPetModel.count > 0 ? adventureMyPetModel.level : 0);
                            }
                            AdventureMyPetAdapter adventureMyPetAdapter = new AdventureMyPetAdapter(adventureMyPetModels, AdventureActivity.this);
                            gvMyPet.setAdapter(adventureMyPetAdapter);
                        }

                        List<Adventure.AdventureReward> adventureRewards = s2C_syncAdventure.getRewardList();
                        if (adventureRewards.size() > 0) {
                            List<RewardObject> adventureRewardModels = new ArrayList<>();
                            for (Adventure.AdventureReward adventureReward : adventureRewards) {
                                RewardObject adventureRewardModel = new RewardObject();
                                adventureRewardModel.rewardType = adventureReward.getRewardType();
                                adventureRewardModel.amount = adventureReward.getAmount();
                                adventureRewardModel.createTime = adventureReward.getCreateTime();
                                adventureRewardModel.rewardID = adventureReward.getRewardID();
                                adventureRewardModels.add(adventureRewardModel);
                            }
                            if (adventureRewards.size() < 6) {
                                for (int i = 0; i < 6 - adventureRewards.size(); i++) {
                                    adventureRewardModels.add(null);
                                }
                            }
                            AdventureGainAdapter adventureGainAdapter = new AdventureGainAdapter(adventureRewardModels, AdventureActivity.this);
                            gvAdventureGain.setAdapter(adventureGainAdapter);
                        }
                    }
                    // 开始探险
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CSTARTADVENTURE_FIELD_NUMBER) {
                    try {
                        s2C_startAdventure = message.getS2CStartAdventure();
                    } catch (Exception e) {
                    }
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CPANGOLINREWARD_FIELD_NUMBER) {
                    // todo 探险广告奖励
                    C2SStartAdventure.C2S_StartAdventure.Builder c2S_StartAdventure = C2SStartAdventure.C2S_StartAdventure.newBuilder().setCallTimestamp((int) (System.currentTimeMillis() / 1000));
                    MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SStartAdventure(c2S_StartAdventure).setMessageId(MessageOuterClass.Message.C2SSTARTADVENTURE_FIELD_NUMBER);
                    SocketManager.send(callObject);
                    S2CPangolinReward.S2C_PangolinReward s2C_pangolinReward = message.getS2CPangolinReward();
                    if (TextUtils.equals(s2C_pangolinReward.getRewardName(), AdsType.ADVENTURE)) {
                        initAdventure();
                        SPUtils.putInt(AdventureActivity.this, SPConfig.AD_REMAIN_COUNT, s2C_pangolinReward.getRemainAdNum());
                        SPUtils.putLong(Utils.getContext(), SPConfig.LAST_VEDIO_TIME, System.currentTimeMillis());
                    }
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CRECEIVEADVENTURE_FIELD_NUMBER) {
                    dismissDialog();
                    S2CReceiveAdventure.S2C_ReceiveAdventure s2C_receiveAdventure = message.getS2CReceiveAdventure();
                    if (s2C_receiveAdventure.getCode() == 0) {
                        RewardDialogUtil.showRewardDialog2(adventureRewardModel, getSupportFragmentManager(), new BaseDialogFragment.IDismissListener() {
                            @Override
                            public void dismissed() {
                                initAdventure();
                            }
                        });
                    } else if (s2C_receiveAdventure.getCode() == 2) {
                        ToastUtils.showLong(R.string.over_repertory_capacity);
                    } else if (s2C_receiveAdventure.getCode() == 1) {
                        ToastUtils.showLong(R.string.position_empty);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAdventureList(List<Adventure.AdventureEvent> adventureEvents) {
        try {
            for (int i = 0; i < adventureEvents.size(); i++) {
                Adventure.AdventureEvent adventureEvent = adventureEvents.get(i);
                for (int j = 0; j < CatHelperUtil.getAdventureModels().size(); j++) {
                    AdventureDynamicModel adventureDynamicModel = CatHelperUtil.getAdventureModels().get(j);
                    if (adventureEvent.getActionID() == adventureDynamicModel.getId()) {
                        String rewardId = adventureDynamicModel.getStory().split("\\|")[(int) adventureEvent.getEventID()];
                        CatNameModel catNameModel = CatHelperUtil.getKittyStringMap().get(rewardId);
                        String desc = null;
                        String content = null;
                        if (adventureEvent.getRewardType() == 1) {
                            content = CatHelperUtil.getKittyNameByLevel(adventureEvent.getRewardID());
                        } else if (adventureEvent.getRewardType() == 2) {
                            content = CatHelperUtil.getDispalyedNumber(adventureEvent.getAmount());
                        } else if (adventureEvent.getRewardType() == 3) {
                            content = CatHelperUtil.getDispalyedNumber(BigDecimal.valueOf(Long.valueOf(adventureEvent.getAmount())).multiply(CatHelperUtil.getTotalOutPutAmountInteger(integers)).toString());
                        } else if (adventureEvent.getRewardType() == 5) {
                            content = TimeDescUtil.getTimeDesc(Integer.valueOf(adventureEvent.getAmount()));
                        } else {
                            content = adventureEvent.getAmount();
                        }
                        if (catNameModel.getName_cn().contains("###")) {
                            desc = catNameModel.getName_cn().replace("###", content);
                        } else {
                            desc = catNameModel.getName_cn();
                        }
                        if (i == 0) {
                            tvDynamicContent.setText(desc);
                            String time = DateUtil.TimeStampDateSecond(adventureEvent.getCreateTime(), "HH:mm");
                            tvDynamicTime.setText(time);
                            iv_dynamic_icon_one.setImageResource(ImageSourceUtil.getResourceByAction(adventureEvent.getActionID()));
                        } else if (i == 1) {
                            tvDynamicContentTwo.setText(desc);
                            String time = DateUtil.TimeStampDateSecond(adventureEvent.getCreateTime(), "HH:mm");
                            tvDynamicTimeTwo.setText(time);
                            iv_dynamic_icon_two.setImageResource(ImageSourceUtil.getResourceByAction(adventureEvent.getActionID()));
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.check_more_dynamic)
    public void checkMoreDynamic() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.adventure_dynamic));
            webViewModel.setUrl(Utils.getString(R.string.adventure_dynamic_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(AdventureActivity.this, WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.tv_adventure_rule)
    public void onViewClicked() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.adventure_rule));
            webViewModel.setUrl(Utils.getString(R.string.adventure_rule_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(AdventureActivity.this, WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.adventure_may_gain)
    public void adventureGain() {
        try {
            AdventureGainDialog adventureGainDialog = new AdventureGainDialog();
            adventureGainDialog.show(getSupportFragmentManager(), "hourBonusDialog");
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.start_adventure)
    public void startAdventure() {
        try {
            int remain_count = SPUtils.getInt(AdventureActivity.this, SPConfig.ADVENTURE_REMAIN_COUNT, 0);
            long endTime = SPUtils.getLong(AdventureActivity.this, SPConfig.ADVENTURE_END_TIME, 0);
            long status = SPUtils.getInt(AdventureActivity.this, SPConfig.ADVENTURE_STATUS, 0);
            if (endTime > 0 && remain_count <= 0 && status != 0) {
                return;
            }
            AdviseUtil.playRewardVedio(AdventureActivity.this, AdsType.ADVENTURE, new IAdsLoaded() {
                @Override
                public void onLoaded() {
                }
            });
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.iv_back)
    public void back() {
        finish();
    }


}

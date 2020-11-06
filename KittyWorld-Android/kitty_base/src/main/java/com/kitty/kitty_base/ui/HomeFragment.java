package com.kitty.kitty_base.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.launcher.ARouter;
//import com.dalong.marqueeview.MarqueeView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.ByteString;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.adapter.LevelTaskAdapter;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.BaseFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.base.WebViewActivity;
import com.kitty.kitty_base.constant.AdsType;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.constant.UnlockConfig;
import com.kitty.kitty_base.event.HomeRefreshEvent;
import com.kitty.kitty_base.fragmentdialog.CashDialog;
import com.kitty.kitty_base.fragmentdialog.DividedKittyDialog;
import com.kitty.kitty_base.fragmentdialog.DividingKittyDialog;
import com.kitty.kitty_base.fragmentdialog.HourBonusDialog;
import com.kitty.kitty_base.fragmentdialog.KittyInfoDialog;
import com.kitty.kitty_base.fragmentdialog.LevelUpdateDialog;
import com.kitty.kitty_base.fragmentdialog.MoreCoinDialog;
import com.kitty.kitty_base.fragmentdialog.OutLineBonusDialog;
import com.kitty.kitty_base.fragmentdialog.RedPackDialog;
import com.kitty.kitty_base.fragmentdialog.RewardDialogUtil;
import com.kitty.kitty_base.fragmentdialog.SignLogDailog;
import com.kitty.kitty_base.fragmentdialog.VeriseDialog;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.DragResourceViewModel;
import com.kitty.kitty_base.model.DragViewLocationModel;
import com.kitty.kitty_base.model.MapModel;
import com.kitty.kitty_base.model.PointModel;
import com.kitty.kitty_base.model.RewardModel;
import com.kitty.kitty_base.model.RewardTimeBonuObject;
import com.kitty.kitty_base.model.ShareInfo;
import com.kitty.kitty_base.model.TaskModel;
import com.kitty.kitty_base.model.UnlockModel;
import com.kitty.kitty_base.model.UserLoginModel;
import com.kitty.kitty_base.model.WebViewModel;
import com.kitty.kitty_base.router.RouterActivityPath;
import com.kitty.kitty_base.system.navigation.NavigationBarObserver;
import com.kitty.kitty_base.system.navigation.OnNavigationBarListener;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.CountDownTimerUtil;
import com.kitty.kitty_base.utils.DateUtil;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.HourBonusTimeTickUtil;
import com.kitty.kitty_base.utils.JsonFileUtil;
import com.kitty.kitty_base.utils.MarqueeTextView;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.ResourceUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.StatusBarUtils;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.TimeTickUtil;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.singleton.GsonSingleInstance;
import com.kitty.kitty_base.utils.singleton.MainHandler;
import com.kitty.kitty_base.views.DragFrameLayout;
import com.kitty.kitty_base.views.dragview.DragView;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.util.LogUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import kitty_protos.C2SBuyKitty;
import kitty_protos.C2SOnekey;
import kitty_protos.C2SSyncKittyInfo;
import kitty_protos.MessageOuterClass;
import kitty_protos.RewardObjectOuterClass;
import kitty_protos.S2CBuyKitty;
import kitty_protos.S2CHourBonus;
import kitty_protos.S2CMarquee;
import kitty_protos.S2CMergeKitty;
import kitty_protos.S2COnUserLoginSync;
import kitty_protos.S2CPangolinReward;
import kitty_protos.SyncKittyInfoOuterClass;
import me.jessyan.autosize.utils.ScreenUtils;

import static com.kitty.kitty_base.utils.CatHelperUtil.getBuyLevelFromMaxLevel;
import static kitty_protos.MessageOuterClass.Message.S2CJOINBACKPACK_FIELD_NUMBER;
import static kitty_protos.MessageOuterClass.Message.S2CMERGEKITTY_FIELD_NUMBER;
import static kitty_protos.MessageOuterClass.Message.S2CSELLKITTY_FIELD_NUMBER;


public class HomeFragment extends BaseFragment implements DragView.IChangeAnimation, OnNavigationBarListener {

    @BindView(R2.id.tv_current_price)
    TextView tvCurrentPrice;
    @BindView(R2.id.tv_current_level)
    TextView tvCurrentLevel;
    @BindView(R2.id.animation_view_nan)
    LottieAnimationView animationView;
    @BindView(R2.id.animation_view_cat)
    LottieAnimationView animationViewCat;
    @BindView(R2.id.ll_top_back)
    LinearLayout llTopBack;
    @BindView(R2.id.ll_first_item)
    LinearLayout llFirstItem;
    @BindView(R2.id.gl_home)
    TableLayout glHome;
    @BindView(R2.id.ll_home_fragment)
    FrameLayout llHomeFragment;
    @BindView(R2.id.current_level_icon)
    ImageView currentLevelIcon;
    @BindView(R2.id.tv_total_coin)
    TextView tvTotalCoin;
    @BindView(R2.id.tv_total_output)
    TextView tv_total_output;
    @BindView(R2.id.tv_get_bonus)
    TextView tv_get_bonus;
    @BindView(R2.id.today_bonus_count)
    TextView todayBonusCount;
    @BindView(R2.id.fl_task_box)
    FrameLayout fl_task_box;
    @BindView(R2.id.fl_lucky_rotate)
    FrameLayout fl_lucky_rotate;

    @BindView(R2.id.fl_get_bonus)
    FrameLayout fl_get_bonus;
    @BindView(R2.id.ll_current_bonus_and_level)
    FrameLayout ll_current_bonus_and_level;
    @BindView(R2.id.tv_loaction)
    TextView tvLoaction;
    @BindView(R2.id.iv_loaction)
    ImageView ivLoaction;
    @BindView(R2.id.iv_bonus_red_point)
    ImageView iv_bonus_red_point;
    @BindView(R2.id.tv_banner)
    MarqueeTextView marqueeView;
    @BindView(R2.id.iv_luck_red_point)
    ImageView iv_luck_red_point;
    @BindView(R2.id.iv_task_red_point)
    ImageView iv_task_red_point;
    @BindView(R2.id.pb_location)
    ProgressBar pbLocation;
    @BindView(R2.id.fl_lottie_kitty)
    FrameLayout fl_lottie_kitty;
    @BindView(R2.id.tv_dividing_timer)
    TextView tv_dividing_timer;
    @BindView(R2.id.tv_dividing)
    TextView tv_dividing;
    @BindView(R2.id.ll_fortune_profit)
    LinearLayout ll_fortune_profit;
    @BindView(R2.id.ll_city_build)
    LinearLayout ll_city_build;
    @BindView(R2.id.ll_ground_one)
    LinearLayout ll_ground_one;
    @BindView(R2.id.ll_ground_two)
    LinearLayout ll_ground_two;
    @BindView(R2.id.ll_ground_three)
    LinearLayout ll_ground_three;
    @BindView(R2.id.sv_ground)
    HorizontalScrollView sv_ground;
    @BindView(R2.id.ll_city)
    LinearLayout ll_city;
    @BindView(R2.id.ll_marquee_view)
    LinearLayout ll_marquee_view;
    @BindView(R2.id.iv_map_red_point)
    ImageView iv_map_red_point;
    @BindView(R2.id.tv_build_unlock)
    TextView tv_build_unlock;

    @BindView(R2.id.tv_task_unlock)
    TextView tv_task_unlock;
    @BindView(R2.id.tv_adventure_unlock)
    TextView tv_adventure_unlock;
    @BindView(R2.id.iv_luck_wheel_text)
    ImageView iv_luck_wheel_text;
    @BindView(R2.id.tv_luck_wheel_unlock)
    TextView tv_luck_wheel_unlock;
    @BindView(R2.id.tv_bouns_unlock)
    TextView tv_bouns_unlock;
    @BindView(R2.id.fl_veris_icon)
    FrameLayout fl_veris_icon;
    @BindView(R2.id.fl_adventure_icon)
    FrameLayout fl_adventure_icon;
    @BindView(R2.id.iv_adventure_red_point)
    ImageView iv_adventure_red_point;
    @BindView(R2.id.iv_build_red_point)
    ImageView iv_build_red_point;
    @BindView(R2.id.iv_adventure)
    ImageView iv_adventure;
    @BindView(R2.id.iv_build)
    ImageView iv_build;
    @BindView(R2.id.fl_check_guid)
    FrameLayout fl_check_guid;


    private boolean isAnimationCatMeasure;
    private boolean isMeasured;
    private int statusHeight;
    private Map<String, DragView> dragViewMap = new TreeMap();
    List<DragView> dragViewList = new ArrayList<>();
    private SyncKittyInfoOuterClass.SyncKittyInfo syncKittyInfo;
    private ArrayList<Integer> integers;
    private boolean messageReceived;
    private MoreCoinDialog moreCoinDialog;
    public MediaPlayer mediaPlayer;
    private AssetFileDescriptor fd;
    private int[] screenSize;
    private HashMap<String, UnlockModel> unlockModelHashMap = CatHelperUtil.getUnlockMap();
    private boolean isAnimationManMeasure;
    private int lotieManViewWidth;
    private List<S2CPangolinReward.S2C_PangolinReward> s2C_pangolinRewards = new ArrayList<>();
    private CountDownTimerUtil timeTickUtil;
    private BigDecimal bigDecimal1 = BigDecimal.ZERO;
    public boolean isDividing;
    private DragResourceViewModel dividingDragResourceViewModel;// 计算值
    public static HomeFragment homeFragment;
    public AssetFileDescriptor fd_merge;
    private AssetFileDescriptor fd_buy;
    public int size;
    private boolean refreshed;
    private boolean isClicked;
    private String totalCoin;
    private BigDecimal totalOutPutAmountInteger;
    private BigDecimal totalOutPutAmount;
    private String currBuyPrice;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            totalOutPutAmountInteger = (BigDecimal) savedInstanceState.getSerializable("totalOutPutAmountInteger");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onUserVisible() {

        initSound();
        refresh();
        initPersonAnimation(SPUtils.getString(getContext(), SPConfig.MAN_ANIMATION_TYPE, "nan.json"));
    }

    //手动刷新数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HomeRefreshEvent event) {
        refresh();
    }

    ;

    private void refresh() {
        C2SSyncKittyInfo.C2S_SyncKittyInfo.Builder c2S_syncKittyInfo = C2SSyncKittyInfo.C2S_SyncKittyInfo.newBuilder().setCallTimestamp(System.currentTimeMillis() / 1000);
        MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SSyncKittyInfo(c2S_syncKittyInfo).setMessageId(MessageOuterClass.Message.C2SSYNCKITTYINFO_FIELD_NUMBER);
        SocketManager.send(callObject);
        initProgress();
    }

    @Override
    protected void onUserInVisible() {
        if (null != mediaPlayer) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;

    }

    private void initSound() {
        try {
            boolean isSoundOpen = SPUtils.getBoolean(getContext(), SPConfig.SOUND_STATUS, true);
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
    protected void initData() {
        try {
            homeFragment = this;
            init();
            todayBonusCount.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            tv_total_output.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            tvCurrentPrice.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            tvCurrentLevel.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            tvTotalCoin.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            int todayDivide = SPUtils.getInt(Utils.getContext(), SPConfig.TODAY_DIVIDE, 0);
            todayBonusCount.setText(NumberUtil.getWanDividedNumber(BigDecimal.valueOf(todayDivide)).toString() + Utils.getString(R.string.cny_unit));
            int maxLevel = SPUtils.getInt(getContext(), SPConfig.MAX_LEVEL, 1);
            int animationLevel = SPUtils.getInt(Utils.getContext(), SPConfig.ANIMATION_LEVEL, maxLevel);
            String animationType = SPUtils.getString(getContext(), SPConfig.MAN_ANIMATION_TYPE, "nan.json");
            initPersonAnimation(animationType);
            initAnimation(animationLevel == 45 ? maxLevel : animationLevel, true, null);
            initCountDown();
            initProgress();
            NavigationBarObserver.getInstance().addOnNavigationBarListener(this);
        } catch (Exception e) {
        }
    }

    private void initProgress() {
        HttpUtils.getMapInfo(new IResponse<MapModel>() {

            private PointModel beforePointModel;

            @Override
            public void onSuccess(BaseResponse<MapModel> baseResponse) {
                try {
                    if (baseResponse.code == 0 && null != baseResponse.data) {
                        MapModel mapModel = baseResponse.data;
                        List<PointModel> pointModels = CatHelperUtil.getLocations();
                        for (int i = 0; i < pointModels.size(); i++) {
                            PointModel pointModel = pointModels.get(i);
                            if (i != 0) {
                                beforePointModel = pointModels.get(i - 1);
                            }
                            if (null != pointModel) {
                                if (mapModel.getKilometers() < pointModel.total_kilometers) {
                                    ll_city.setBackgroundResource(ResourceUtil.getDrawableId(Utils.getContext(), "home_" + pointModel.address_icon + "_back"));
                                    ll_city_build.setBackgroundResource(ResourceUtil.getDrawableId(Utils.getContext(), "home_" + pointModel.address_icon + "_build"));
                                    ll_ground_one.setBackgroundResource(ResourceUtil.getDrawableId(Utils.getContext(), "home_" + pointModel.address_icon + "_g"));
                                    ll_ground_two.setBackgroundResource(ResourceUtil.getDrawableId(Utils.getContext(), "home_" + pointModel.address_icon + "_g"));
                                    ll_ground_three.setBackgroundResource(ResourceUtil.getDrawableId(Utils.getContext(), "home_" + pointModel.address_icon + "_g"));
                                    float ratio = (mapModel.getKilometers() - beforePointModel.getTotal_kilometers()) / pointModels.get(i).getTotal_kilometers();
                                    int ration = (int) ((ratio * 100) >= 100 ? 100 : ((ratio * 100) < 1 && (ratio * 100) > 0) ? 1 : ratio * 100);
                                    pbLocation.setProgress(ration);
                                    tvLoaction.setText(pointModel.getAddress_string());
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }


    private void initPersonAnimation(String fileName) {
        try {
            ViewTreeObserver observer2 = animationView.getViewTreeObserver();
            ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    if (!isAnimationManMeasure) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) animationView.getLayoutParams();
                        lotieManViewWidth = animationView.getMeasuredWidth();
                        layoutParams.leftMargin = (int) ((screenSize[0] * 0.3) - (animationView.getMeasuredWidth() / 5));
                        animationView.setLayoutParams(layoutParams);
                        TypedValue outValue = new TypedValue();
                        getResources().getValue(R.dimen.anim_man_ratio, outValue, true);
                        float value = outValue.getFloat();
//                        float lineSpacing = getResources().getDimension(R.dimen.anim_man_ratio);
                        animationView.setScale(value);
                        MainHandler.getInstance().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (null != animationView) {
                                    animationView.setVisibility(View.VISIBLE);
                                }
                                isAnimationManMeasure = true;
                            }
                        }, 800);
                    }
                    return true;
                }
            };
            observer2.addOnPreDrawListener(onPreDrawListener);
            MainHandler.getInstance().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isAnimationManMeasure && null != animationView) {
                        animationView.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
                    }
                }
            }, 1500);
            animationView.setAnimation(fileName);
            animationView.loop(true);
            animationView.playAnimation();
            SPUtils.putString(getContext(), SPConfig.MAN_ANIMATION_TYPE, fileName);
        } catch (Exception e) {
        }
    }

    private void initKittyAnimation(int level, boolean init, DragResourceViewModel dragResourceViewModel) {
        try {
            isAnimationCatMeasure = false;
            int leftMargin = 0;

            if (level < 11) {
                size = 70;
                leftMargin = 10;
            } else if (level < 26) {
                size = 90;
                leftMargin = 10;
            } else if (level < 36) {
                size = 110;
            } else {
                size = 130;
            }
            if (!init) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fl_lottie_kitty.getLayoutParams();
                FrameLayout.LayoutParams animationViewCatlayoutParams = (FrameLayout.LayoutParams) animationViewCat.getLayoutParams();
                layoutParams.leftMargin = (int) ((screenSize[0] * 0.3) + (lotieManViewWidth * 0.45)) + leftMargin;
                fl_lottie_kitty.setLayoutParams(layoutParams);
                animationViewCatlayoutParams.width = Utils.dip2px(size);
                animationViewCatlayoutParams.height = Utils.dip2px(size);
                animationViewCat.setLayoutParams(animationViewCatlayoutParams);
                tv_dividing_timer.setVisibility(View.INVISIBLE);
                tv_dividing.setVisibility(View.INVISIBLE);
                if (null != dragResourceViewModel && level == 45 && dragResourceViewModel.getExpireTime() != 0) {
                    if (dragResourceViewModel.getExpireTime() == -1) {
                        tv_dividing_timer.setVisibility(View.INVISIBLE);
                        tv_dividing.setVisibility(View.INVISIBLE);
                        return;
                    }
                    tv_dividing.setVisibility(View.VISIBLE);
                    tv_dividing_timer.setVisibility(View.VISIBLE);

                    int todayDivide = SPUtils.getInt(Utils.getContext(), SPConfig.TODAY_DIVIDE, 0);
                    float divideMoney = (dragResourceViewModel.getExpireIn() * 1000) * todayDivide / 86400000;
                    BigDecimal bigDecimal = NumberUtil.getWanDividedNumber(BigDecimal.valueOf(divideMoney), 5);

                    float start = bigDecimal1.floatValue();
                    ValueAnimator anim = ValueAnimator.ofFloat(start, bigDecimal.floatValue());
                    anim.setDuration((dragResourceViewModel.getExpireTime() * 1000) - System.currentTimeMillis());
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float divideMoney = (float) animation.getAnimatedValue();
                            bigDecimal1 = new BigDecimal(divideMoney).setScale(5, RoundingMode.HALF_EVEN);
                            if (null != tv_dividing) {
                                tv_dividing.setText("分红中" + bigDecimal1.toString() + "元");
                            }
                        }
                    });

                    anim.start();
                    timeTickUtil = new CountDownTimerUtil(0, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            String time = getTimeFromInt(millisUntilFinished);
                            if (null != tv_dividing_timer) {
                                tv_dividing_timer.setText(time);
                            }
                        }

                        @Override
                        public void onFinish() {
                            if (null != tv_dividing_timer) {
                                tv_dividing_timer.setVisibility(View.INVISIBLE);
                            }
                            if (null != tv_dividing) {
                                tv_dividing.setVisibility(View.INVISIBLE);
                            }
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(IntentConfig.DRAG_MODEL, dividingDragResourceViewModel);
                            DividedKittyDialog dividedKittyDialog = new DividedKittyDialog();
                            dividedKittyDialog.setArguments(bundle);
                            dividedKittyDialog.show(getFragmentManager(), "dividedKittyDialog");
                            isDividing = false;
                            int animation_level = SPUtils.getInt(getContext(), SPConfig.ANIMATION_LEVEL, 1);
                            initAnimation(animation_level, false, null);
                            dividingDragResourceViewModel = null;
                            bigDecimal1 = BigDecimal.ZERO;
                        }
                    };
                    timeTickUtil.setCountdownInterval(1000);
                    timeTickUtil.setMillisInFuture((dragResourceViewModel.getExpireTime() * 1000) - System.currentTimeMillis());
                    timeTickUtil.start();
                    isDividing = true;
                }
                return;
            }
            ViewTreeObserver observer2 = fl_lottie_kitty.getViewTreeObserver();
            int finalLeftMargin1 = leftMargin;
            ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (!isAnimationCatMeasure && init) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fl_lottie_kitty.getLayoutParams();
                        layoutParams.leftMargin = (int) ((screenSize[0] * 0.3) + (lotieManViewWidth * 0.45)) + finalLeftMargin1;
                        fl_lottie_kitty.setLayoutParams(layoutParams);
                        FrameLayout.LayoutParams animationViewCatlayoutParams = (FrameLayout.LayoutParams) animationViewCat.getLayoutParams();
                        animationViewCatlayoutParams.width = Utils.dip2px(size);
                        animationViewCatlayoutParams.height = Utils.dip2px(size);
                        animationViewCat.setLayoutParams(animationViewCatlayoutParams);
                        MainHandler.getInstance().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isAnimationCatMeasure = true;
                            }
                        }, 800);
                    }
                    return true;
                }
            };
            observer2.addOnPreDrawListener(onPreDrawListener);
            MainHandler.getInstance().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isAnimationCatMeasure && null != fl_lottie_kitty) {
                        fl_lottie_kitty.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
                    }
                }
            }, 1500);
        } catch (Exception e) {
        }
    }

    public void initAnimation(int level, boolean b, DragResourceViewModel dragResourceViewModel) {
        try {
            this.dividingDragResourceViewModel = dragResourceViewModel;
            if (isDividing) {
                return;
            }
            initKittyAnimation(level, b, dragResourceViewModel);
            String json = JsonFileUtil.getJson(Utils.getContext(), "kittys/level" + level + "/level" + level + ".json");
            if (TextUtils.isEmpty(json)) {
                return;
            }
            animationViewCat.setAnimation("kittys/level" + level + "/level" + level + ".json");
            animationViewCat.setImageAssetsFolder("kittys/level" + level);
            animationViewCat.loop(true);
            animationViewCat.playAnimation();
            if (level == 45 && dragResourceViewModel != null && dragResourceViewModel.getExpireTime() > 0) {
                return;
            }
            SPUtils.putInt(Utils.getContext(), SPConfig.ANIMATION_LEVEL, level);
        } catch (Exception e) {
        }
    }

    private void initCountDown() {
        try {
            UserLoginModel userLoginModel = (UserLoginModel) SPUtils.get(Utils.getContext(), SPConfig.USER_LOGIN_MODEL);
            if (null != userLoginModel) {
                initBonusTimeAndOffLine(userLoginModel);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void iniListener() {

    }


    /**
     * 导航栏的显示和隐藏监听
     *
     * @param show the show
     */
    @Override
    public void onNavigationBarChange(boolean show) {
        if (dragViewList == null) return;
        int navigationBarHeight = StatusBarUtils.getNavigationBarHeight(mActivity);

        for (DragView dragView : dragViewList) {
            View view = dragView.getDragView();
            FrameLayout.LayoutParams fl = (FrameLayout.LayoutParams) view.getLayoutParams();
            DragViewLocationModel positionModel = dragView.getDragViewLocationModel();
            int[][] location = positionModel.getLocation();
            int[] ints = location[0];
            int[] ints1 = location[1];

            if (show) {
                ints[1] = ints[1] - navigationBarHeight;
                fl.setMargins(ints[0], ints[1] + statusHeight, 0, 0);
            } else {
                ints[1] = ints[1] + navigationBarHeight;
                fl.setMargins(ints[0], ints[1] + statusHeight, 0, 0);
            }
            positionModel.setLocation(location);
            dragView.setDragViewLocationModel(positionModel);
            view.requestLayout();
        }
    }

    private void init() {
        // 初始化控件并加载数据
        try {
            statusHeight = StatusBarUtils.getStatusBarHeight(Utils.getContext());
            screenSize = ScreenUtils.getRawScreenSize(Utils.getContext());
            llTopBack.setPadding(0, statusHeight, 0, 0);
//            ToastUtils.showShortCenter("widthPixels" + screenSize[0] + "height" + screenSize[1]);
            ViewTreeObserver observer = llFirstItem.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                private int orginTop;
                private int orginLeft;

                public boolean onPreDraw() {
                    //避免重复监听
                    llFirstItem.getViewTreeObserver().removeOnPreDrawListener(this);
//                    if (!isMeasured) {
                    int[] location = new int[2];
                    llFirstItem.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    if (x == 0 || y == 0) {
                        Rect rect = new Rect();
                        llFirstItem.getGlobalVisibleRect(rect);
                        x = rect.left;
                        y = rect.top;
                    }
                    orginLeft = x;
                    orginTop = y - statusHeight;
                    for (int i = 0; i < 3; i++) {
                        int finalY = (y - statusHeight) + (i * Utils.dip2px(11) + i * Utils.dip2px(78));
                        for (int j = 0; j < 4; j++) {
                            int finalX = x + (j * Utils.dip2px(11) + (j * Utils.dip2px(78)));
                            // 初始化12个可拖动控件
                            DragFrameLayout dragFrameLayout = (DragFrameLayout) View.inflate(mActivity, R.layout.gridview_item_home, null);
                            DragView dragView = new DragView.Builder()
                                    .setActivity(mActivity)//当前Activity，不可为空
                                    .setFragmentManager(getFragmentManager())
                                    .setDefaultLeft(finalX)//初始位置左边距
                                    .setDefaultTop(finalY)//初始位置上边距
                                    .setSize(Utils.dip2px(78))//DragView大小
                                    .setView(dragFrameLayout)
                                    .build(llHomeFragment);
                            DragViewLocationModel dragViewLocationModel = new DragViewLocationModel();
                            int btnPosition[][] = new int[2][];
                            btnPosition[0] = new int[]{finalX, finalY, finalX + Utils.dip2px(78), finalY + Utils.dip2px(78)};
                            btnPosition[1] = new int[]{i, j};
                            dragViewLocationModel.setRaw(i);
                            dragViewLocationModel.setCul(j);
                            dragViewLocationModel.setOriginLeft(orginLeft);
                            dragViewLocationModel.setOriginTop(orginTop);
                            dragViewLocationModel.setOriginRight((3 * Utils.dip2px(11) + 4 * Utils.dip2px(78)));
                            dragViewLocationModel.setOriginBottom((orginTop + (2 * Utils.dip2px(11) + 3 * Utils.dip2px(78))));
                            dragViewLocationModel.setLocation(btnPosition);
                            dragView.setDragViewLocationModel(dragViewLocationModel);
                            dragView.setOnAnimationChange(HomeFragment.this);
                            dragViewMap.put(i + "," + j, dragView);
                            dragViewList.add(dragView);
                            dragView.setDragViewMap(dragViewMap);
                            dragView.setDragViewList(dragViewList);
                        }
                    }
                    isMeasured = true;
                    refresh();
                    initUnlock();
                    initAnimation(dragViewList);
//                    }
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }

    private void initUnlock() {
        try {
            int maxLevel = SPUtils.getInt(Utils.getContext(), SPConfig.MAX_LEVEL, 1);
            fl_check_guid.setVisibility(maxLevel >= 10 ? View.GONE : View.VISIBLE);
            boolean task = CatHelperUtil.isUnlockedNoToast(UnlockConfig.TASKS);
            String taskText = CatHelperUtil.getUnlockString(UnlockConfig.TASKS);

            boolean luck_wheel = CatHelperUtil.isUnlockedNoToast(UnlockConfig.LUCK_WHEEL);
            String luckWheelText = CatHelperUtil.getUnlockString(UnlockConfig.LUCK_WHEEL);

            boolean online_reward = CatHelperUtil.isUnlockedNoToast(UnlockConfig.ONLINE_REWARD);
            String onlineRewardText = CatHelperUtil.getUnlockString(UnlockConfig.ONLINE_REWARD);

            if (task) {
                tv_task_unlock.setVisibility(View.INVISIBLE);
                fl_task_box.setClickable(true);
                fl_task_box.setEnabled(true);
            } else {
                tv_task_unlock.setVisibility(View.VISIBLE);
                tv_task_unlock.setText(taskText);
                fl_task_box.setClickable(false);
                fl_task_box.setEnabled(false);
            }

            if (luck_wheel) {
                tv_luck_wheel_unlock.setVisibility(View.INVISIBLE);
                iv_luck_wheel_text.setVisibility(View.VISIBLE);
                fl_lucky_rotate.setClickable(true);
                fl_lucky_rotate.setEnabled(true);
            } else {
                iv_luck_wheel_text.setVisibility(View.INVISIBLE);
                tv_luck_wheel_unlock.setVisibility(View.VISIBLE);
                tv_luck_wheel_unlock.setText(luckWheelText);
                fl_lucky_rotate.setClickable(false);
                fl_lucky_rotate.setEnabled(false);
            }

            if (online_reward) {
                tv_get_bonus.setVisibility(View.VISIBLE);
                tv_bouns_unlock.setVisibility(View.INVISIBLE);
                fl_get_bonus.setClickable(true);
                fl_get_bonus.setEnabled(true);
            } else {
                tv_get_bonus.setVisibility(View.INVISIBLE);
                tv_bouns_unlock.setVisibility(View.VISIBLE);
                tv_bouns_unlock.setText(onlineRewardText);
                fl_get_bonus.setClickable(false);
                fl_get_bonus.setEnabled(false);
            }

//            boolean redpacket = CatHelperUtil.isUnlockedNoToast(UnlockConfig.REDPACKET);
            boolean adventure = CatHelperUtil.isUnlockedNoToast(UnlockConfig.ADVENTURE);
            String adventureText = CatHelperUtil.getUnlockString(UnlockConfig.ADVENTURE);

            boolean building = CatHelperUtil.isUnlockedNoToast(UnlockConfig.BUILDING);
            String buildingText = CatHelperUtil.getUnlockString(UnlockConfig.BUILDING);

            boolean map = CatHelperUtil.isUnlockedNoToast(UnlockConfig.MAP);

            UnlockModel veriseUnlockModel = unlockModelHashMap.get(UnlockConfig.BUILDING);
            UnlockModel adventureUnlockModel = unlockModelHashMap.get(UnlockConfig.ADVENTURE);

            boolean aBoolean = SPUtils.getBoolean(Utils.getContext(), SPConfig.BUILDING_UNLOCKED_CLICKED, false);
            boolean aBoolean1 = SPUtils.getBoolean(Utils.getContext(), SPConfig.ADVENTURE_UNLOCKED_CLICKED, false);
            iv_build_red_point.setVisibility(building && maxLevel == veriseUnlockModel.getUnlock_level() && !aBoolean ? View.VISIBLE : View.INVISIBLE);
            iv_adventure_red_point.setVisibility(adventure && maxLevel == adventureUnlockModel.getUnlock_level() && !aBoolean1 ? View.VISIBLE : View.INVISIBLE);

            if (adventure) {
                iv_adventure.setImageResource(R.drawable.home_adventure_icon);
                tv_adventure_unlock.setVisibility(View.INVISIBLE);
                fl_adventure_icon.setClickable(true);
                fl_adventure_icon.setEnabled(true);
            } else {
                iv_adventure.setImageResource(R.drawable.adventure_unlock_icon);
                tv_adventure_unlock.setVisibility(View.VISIBLE);
                tv_adventure_unlock.setText(adventureText);
                fl_adventure_icon.setClickable(false);
                fl_adventure_icon.setEnabled(false);
            }
            if (building) {
                iv_build.setImageResource(R.drawable.veris_icon);
                tv_build_unlock.setVisibility(View.INVISIBLE);
                fl_veris_icon.setClickable(true);
                fl_veris_icon.setEnabled(true);
            } else {
                iv_build.setImageResource(R.drawable.build_unlock_icon);
                tv_build_unlock.setVisibility(View.VISIBLE);
                tv_build_unlock.setText(buildingText);
                fl_veris_icon.setClickable(false);
                fl_veris_icon.setEnabled(false);
            }
            if (map) {
                tvLoaction.setVisibility(View.VISIBLE);
                ivLoaction.setVisibility(View.VISIBLE);
                pbLocation.setVisibility(View.VISIBLE);
                boolean mapUnlockClicked = SPUtils.getBoolean(Utils.getContext(), SPConfig.MAP_UNLOCKED, false);
                if (!mapUnlockClicked && null != iv_map_red_point) {
                    iv_map_red_point.setVisibility(View.VISIBLE);
                }
            }


        } catch (Exception e) {
        }
    }


    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        try {
            if (data instanceof MessageOuterClass.Message) {
                MessageOuterClass.Message message = (MessageOuterClass.Message) data;

                commenMessage(message);
                if (message.getMessageId() == MessageOuterClass.Message.S2CBUYKITTY_FIELD_NUMBER) {
                    S2CBuyKitty.S2C_BuyKitty c2SBuyKitty = message.getS2CBuyKitty();
                    int code = c2SBuyKitty.getCode();
                    if (code == 0) {//购买成功
                        if (currBuyPrice != null) {
//                            String bouns = currBuyPrice.split("\\.")[0];
                            BigDecimal bigCur = new BigDecimal(currBuyPrice);
                            if (totalOutPutAmountInteger != null && totalOutPutAmountInteger.compareTo(bigCur) > 0 && bigCur.compareTo(BigDecimal.ZERO) >= 0) {
                                totalOutPutAmountInteger = totalOutPutAmountInteger.subtract(bigCur);
                                String dispalyedNumber = CatHelperUtil.getDispalyedNumber(totalOutPutAmountInteger.toString());
                                if (tvTotalCoin != null && dispalyedNumber != null) {
                                    tvTotalCoin.setText(dispalyedNumber);
                                }
                            }
                        }
                    } else if (code == 1) {
                        ToastUtils.showShortCenter(R.string.over_max_level);
                    } else if (code == 2) {
                        ToastUtils.showShortCenter(R.string.no_more_position);
                    } else if (code == 3) {
                        try {
                            showDialogEachTime();
                            Bundle bundle = new Bundle();
                            bundle.putIntegerArrayList(IntentConfig.KITTY_LEVEL_LIST, integers);
                            if (moreCoinDialog == null) {
                                moreCoinDialog = new MoreCoinDialog();
                                moreCoinDialog.setOnDismissListener(new BaseDialogFragment.IDismissListener() {
                                    @Override
                                    public void dismissed() {
                                        moreCoinDialog = null;
                                    }
                                });
                                moreCoinDialog.setArguments(bundle);
                                moreCoinDialog.show(getFragmentManager(), "moreCoinDialog");
                            }
                            dismissDialog();
                        } catch (Exception e) {
                        }
                    }
                } else if (message.getMessageId() == S2CJOINBACKPACK_FIELD_NUMBER) {
                    if (message.getS2CJoinBackpack().getCode() == 2) {
                        ToastUtils.showShortCenter("仓库已满");
                    }
                } else if (message.getMessageId() == S2CSELLKITTY_FIELD_NUMBER) {
                    if (message.getS2CJoinBackpack().getCode() != 0) {
                        ToastUtils.showShortCenter("回收失败");
                    } else {
                    }
                } else if (message.getMessageId() == S2CMERGEKITTY_FIELD_NUMBER) {
                    try {
                        S2CMergeKitty.S2C_MergeKitty s2C_mergeKitty = message.getS2CMergeKitty();
                        if (s2C_mergeKitty.getCode() == 0) {
                            if (s2C_mergeKitty.getRedpacketAmount() > 0) {
                                showRedPackDialog(s2C_mergeKitty.getRedpacketAmount());
                            }
                            if (s2C_mergeKitty.getIsCanMergeBouns() && s2C_mergeKitty.getMergeLevel() <= 37) {
                                showLevelUpdateDialog(s2C_mergeKitty.getMergeLevel());
                                if (CatHelperUtil.isUnlockedNoToast(UnlockConfig.LEVEL_WHEEL)) {
                                    showLevelBonusDialog(s2C_mergeKitty.getMergeLevel());
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CMARQUEE_FIELD_NUMBER) {
                    try {
                        ll_marquee_view.setVisibility(View.VISIBLE);
                        S2CMarquee.S2C_Marquee s2C_marquee = message.getS2CMarquee();
                        marqueeView.setText(s2C_marquee.getMarquee().getContent());
                        MainHandler.getInstance().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ll_marquee_view.setVisibility(View.INVISIBLE);
                            }
                        }, 5000);
                    } catch (Exception e) {
                    }
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CHOURBONUS_FIELD_NUMBER) {
                    try {
                        S2CHourBonus.S2C_HourBonus s2C_hourBonus = message.getS2CHourBonus();
                        RewardModel<String> rewardModel = FormDataUtil.getRewardModelMap().get("countdown_time");
                        String time = rewardModel.getContent();
                        List<Long> times = GsonSingleInstance.buildGson().fromJson(time, new TypeToken<List<Long>>() {
                        }.getType());
                        int bonusCount = s2C_hourBonus.getBonusCount();
                        if (bonusCount > times.size()) {
                            ToastUtils.showShortCenter("领取次数已达上限");
                            return;
                        }
                        if (bonusCount <= 0) {
                            return;
                        }
                        long lastBonusTime = s2C_hourBonus.getLastBonusTime();
                        long bonusTime = (lastBonusTime + times.get(bonusCount - 1)) - System.currentTimeMillis() / 1000;
                        HourBonusTimeTickUtil timeTickUtil = new HourBonusTimeTickUtil(tv_get_bonus, bonusTime * 1000, 1000);
                        timeTickUtil.start();
                        if (s2C_hourBonus.getStatus() == 0) {
                            RewardTimeBonuObject rewardObject = new RewardTimeBonuObject();
                            RewardObjectOuterClass.RewardObject rewardObject1 = s2C_hourBonus.getRewardsList().get(0);
                            rewardObject.rewardType = rewardObject1.getType();
                            List<SyncKittyInfoOuterClass.kittyLevelList> kittyLevelLists = syncKittyInfo.getKittyLevelListList();
                            rewardObject.amount = CatHelperUtil.getDispalyedNumber(BigDecimal.valueOf(rewardObject1.getAmount()).multiply(CatHelperUtil.getTotalOutPutAmount(kittyLevelLists)).toString());
                            rewardObject.bonusTime = rewardObject1.getAmount();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(IntentConfig.REWARD_OBJECT_MODEL, rewardObject);
                            HourBonusDialog hourBonusDialog = new HourBonusDialog();
                            hourBonusDialog.setArguments(bundle);
                            hourBonusDialog.show(getFragmentManager(), "hourBonusDialog");
                        } else if (s2C_hourBonus.getStatus() == 1) {
                            ToastUtils.showShortCenter("领取次数已达上限");
                        } else if (s2C_hourBonus.getStatus() == 2) {
                        } else if (s2C_hourBonus.getStatus() == 3) {
                            ToastUtils.showShortCenter(R.string.receive_failed);
                        } else if (s2C_hourBonus.getStatus() == -1) {
                            ToastUtils.showShortCenter(R.string.receive_failed);
                        }
                        SPUtils.putBoolean(Utils.getContext(), SPConfig.ONLINE_REWARD_UNLOCKED, true);
                        if (null != iv_bonus_red_point) {
                            iv_bonus_red_point.setVisibility(View.INVISIBLE);
                        }
                    } catch (Exception e) {
                    }
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CONUSERLOGINSYNC_FIELD_NUMBER) {
                    initBonusTimeAndOffLine(message);
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CREDPACKETSHARE_FIELD_NUMBER) {
                    try {
                        if (message.getS2CRedPacketShare().getCode() == 0) {
                            String redpackUrl = Utils.getString(R.string.red_pack_url) + message.getS2CRedPacketShare().getRedPacketID();
                            ShareInfo shareInfo = new ShareInfo();
                            shareInfo.setDesc("限量大红包，手快有，手慢无~");
                            shareInfo.setTitle(Utils.getString(R.string.red_pack_title));
                            shareInfo.setImgUrl("");
                            shareInfo.setLink(redpackUrl);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(IntentConfig.SHARE_INFO, shareInfo);
                            RedPackDialog redPackDialog = new RedPackDialog();
                            redPackDialog.setArguments(bundle);
                            redPackDialog.show(getFragmentManager(), "redPackDialog");
                        } else {
                            ToastUtils.showLong(Utils.getString(R.string.failed_to_get_packetid));
                        }
                    } catch (Exception e) {
                    }
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CPANGOLINREWARD_FIELD_NUMBER) {
                    S2CPangolinReward.S2C_PangolinReward s2C_pangolinReward = message.getS2CPangolinReward();
                    if (TextUtils.equals(s2C_pangolinReward.getRewardName(), AdsType.ON_HOOK)) {
                        s2C_pangolinRewards.add(s2C_pangolinReward);
//                        ToastUtils.showShort(s2C_pangolinReward.getRewardAmount());
                        SPUtils.putInt(Utils.getContext(), SPConfig.AD_REMAIN_COUNT, s2C_pangolinReward.getRemainAdNum());
                        SPUtils.putLong(Utils.getContext(), SPConfig.LAST_VEDIO_TIME, System.currentTimeMillis());
                    }

                    if (TextUtils.equals(s2C_pangolinReward.getRewardName(), AdsType.MORE_COIN)) {
                        s2C_pangolinRewards.add(s2C_pangolinReward);
                        SPUtils.putInt(Utils.getContext(), SPConfig.AD_REMAIN_COUNT, s2C_pangolinReward.getRemainAdNum());
                        SPUtils.putLong(Utils.getContext(), SPConfig.LAST_VEDIO_TIME, System.currentTimeMillis());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showRedPackDialog(int redpacketAmount) {
        try {
            Bundle bundle = new Bundle();
            bundle.putInt(IntentConfig.REDPACK_AMOUNT, redpacketAmount);
            CashDialog cashDialog = new CashDialog();
            cashDialog.setArguments(bundle);
            cashDialog.show(getFragmentManager(), "cashDialog");
        } catch (Exception e) {
        }
    }

    private void showLevelUpdateDialog(int mergeLevel) {
        if (null != integers) {
            Bundle bundle = new Bundle();
            bundle.putInt(IntentConfig.MERGE_LEVEL, mergeLevel);
            LevelUpdateDialog levelUpdateDialog = new LevelUpdateDialog();
            levelUpdateDialog.setArguments(bundle);
            levelUpdateDialog.show(getFragmentManager(), "levelUpdateDialog");
        }
    }

    /**
     * 显示等级奖励
     *
     * @param mergeLevel
     */
    private void showLevelBonusDialog(int mergeLevel) {
        if (null != integers) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.KITTY_LEVEL_LIST, integers);
            bundle.putInt(IntentConfig.MERGE_LEVEL, mergeLevel);
            Intent intent = new Intent(getActivity(), LevelBonusDialogActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void initBonusTimeAndOffLine(UserLoginModel message) {
        try {
            if (messageReceived) {
                return;
            }
            messageReceived = true;
            int todayDivide = SPUtils.getInt(Utils.getContext(), SPConfig.TODAY_DIVIDE, 0);
            setTodayDivide(message.todayDividend > 0 ? message.todayDividend : todayDivide);
            SPUtils.putInt(Utils.getContext(), SPConfig.WHEEL_COUNT, message.luckyWheelCount);
            RewardModel<String> rewardModel = FormDataUtil.getRewardModelMap().get("countdown_time");
            String time = rewardModel.getContent();
            List<Long> times = GsonSingleInstance.buildGson().fromJson(time, new TypeToken<List<Long>>() {
            }.getType());
            if (message.hourBonusCount <= times.size() && message.hourBonusCount > 0) {
                long bonusTime = (message.lastHourBonusTime + times.get(message.hourBonusCount - 1)) - System.currentTimeMillis() / 1000;
                HourBonusTimeTickUtil timeTickUtil = new HourBonusTimeTickUtil(tv_get_bonus, bonusTime * 1000, 1000);
                timeTickUtil.start();
            }
            if (Long.valueOf(message.offlineTime) > 5 * 60) {
                BigDecimal totalBonus = CatHelperUtil.getTotalOutPutAmountInteger(message.kittyInfos);
                RewardTimeBonuObject rewardObject = new RewardTimeBonuObject();
                rewardObject.amount = BigDecimal.valueOf(Long.valueOf(message.offlineTime)).multiply(totalBonus).toString();
                rewardObject.bonusTime = Long.valueOf(message.offlineTime);
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentConfig.REWARD_OBJECT_MODEL, rewardObject);
                OutLineBonusDialog hourBonusDialog = new OutLineBonusDialog();
                hourBonusDialog.setArguments(bundle);
                hourBonusDialog.show(getFragmentManager(), "hourBonusDialog");
            }
        } catch (Exception e) {
        }
    }


    private void initBonusTimeAndOffLine(MessageOuterClass.Message message) {
        try {
            if (messageReceived) {
                return;
            }
            messageReceived = true;
            S2COnUserLoginSync.S2C_OnUserLoginSync s2C_onUserLoginSync = message.getS2COnUserLoginSync();
            int todayDivide = SPUtils.getInt(Utils.getContext(), SPConfig.TODAY_DIVIDE, 0);
            setTodayDivide(s2C_onUserLoginSync.getTodayDividend() > 0 ? s2C_onUserLoginSync.getTodayDividend() : todayDivide);
            if (s2C_onUserLoginSync.getTodayDividend() > 0) {
                SPUtils.putInt(Utils.getContext(), SPConfig.TODAY_DIVIDE, s2C_onUserLoginSync.getTodayDividend());
            }
            SPUtils.putInt(Utils.getContext(), SPConfig.WHEEL_COUNT, s2C_onUserLoginSync.getLuckyWheelCount());
            RewardModel<String> rewardModel = FormDataUtil.getRewardModelMap().get("countdown_time");
            String time = rewardModel.getContent();
            List<Long> times = GsonSingleInstance.buildGson().fromJson(time, new TypeToken<List<Long>>() {
            }.getType());
            if (s2C_onUserLoginSync.getHourBonusCount() <= times.size() && s2C_onUserLoginSync.getHourBonusCount() > 0) {
                long bonusTime = (s2C_onUserLoginSync.getLastHourBonusTime() + times.get(s2C_onUserLoginSync.getHourBonusCount() - 1)) - System.currentTimeMillis() / 1000;
                HourBonusTimeTickUtil timeTickUtil = new HourBonusTimeTickUtil(tv_get_bonus, bonusTime * 1000, 1000);
                timeTickUtil.start();
            }
            if (Long.valueOf(s2C_onUserLoginSync.getOfflineTime()) >= 5 * 60) {
                BigDecimal totalBonus = CatHelperUtil.getTotalOutPutAmountByKittyInfo(s2C_onUserLoginSync.getKittiesList());
                RewardTimeBonuObject rewardObject = new RewardTimeBonuObject();
                rewardObject.amount = BigDecimal.valueOf(Long.valueOf(s2C_onUserLoginSync.getOfflineTime())).multiply(totalBonus).toString();
                rewardObject.bonusTime = Long.valueOf(s2C_onUserLoginSync.getOfflineTime());
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentConfig.REWARD_OBJECT_MODEL, rewardObject);
                OutLineBonusDialog hourBonusDialog = new OutLineBonusDialog();
                hourBonusDialog.setArguments(bundle);
                hourBonusDialog.show(getFragmentManager(), "hourBonusDialog");
            }
        } catch (Exception e) {
        }
    }

    private void commenMessage(MessageOuterClass.Message message) {
        syncKittyInfo = message.getSyncKittyInfo();
        setBuyLevelResource(syncKittyInfo.getBuyLevel());
        if (syncKittyInfo.getMaxLevel() <= 0) {
            return;
        }

        int maxLevelBefore = SPUtils.getInt(getContext(), SPConfig.MAX_LEVEL, 1);
        if (syncKittyInfo.getMaxLevel() > 37) {
            List<Integer> integers = new ArrayList<>();
            for (SyncKittyInfoOuterClass.kittyLevelList kittyLevelList : syncKittyInfo.getKittyLevelListList()) {
                integers.add(kittyLevelList.getLevel());
            }
            int maxRealLevel = Collections.max(integers);
            SPUtils.putInt(getContext(), SPConfig.MAX_LEVEL, maxRealLevel);
        } else {
            SPUtils.putInt(getContext(), SPConfig.MAX_LEVEL, syncKittyInfo.getMaxLevel());
        }
        int maxLevelAfter = SPUtils.getInt(getContext(), SPConfig.MAX_LEVEL, 1);
        if (maxLevelAfter > maxLevelBefore) {
            initAnimation(maxLevelAfter, false, null);
        }

        if (message.getMessageId() == S2CMERGEKITTY_FIELD_NUMBER) {
            return;
        }
        initDragView(syncKittyInfo.getKittyLevelListList());
        totalCoin = syncKittyInfo.getTotalCoin();
        totalOutPutAmount = CatHelperUtil.getTotalOutPutAmount(syncKittyInfo.getKittyLevelListList());
        currBuyPrice = syncKittyInfo.getCurrBuyPrice();
        setTotalCoin(totalCoin);
        setBuyPrice(currBuyPrice);
        setTotalOutPut(CatHelperUtil.getTotalOutPutAmount(syncKittyInfo.getKittyLevelListList()).toString());
        initUnlock();
        showUnLockDialog();
    }

    private void showUnLockDialog() {
        try {
            int maxLevel = SPUtils.getInt(Utils.getContext(), SPConfig.MAX_LEVEL, 1);
            if (maxLevel <= 0) {
                return;
            }
//
//            UnlockModel redPackUnlockModel = unlockModelHashMap.get(UnlockConfig.REDPACKET);
//            boolean redPackUnlock = SPUtils.getBoolean(Utils.getContext(), SPConfig.REDPACK_UNLOCKED, false);
//            if ((maxLevel == redPackUnlockModel.getUnlock_level()) && !redPackUnlock) {
//                C2SRedPacketShare.C2S_RedPacketShare.Builder c2S_RedPacketShare = C2SRedPacketShare.C2S_RedPacketShare.newBuilder().setCallTimestamp((int) (System.currentTimeMillis() / 1000));
//                MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SRedPacketShare(c2S_RedPacketShare).setMessageId(MessageOuterClass.Message.C2SREDPACKETSHARE_FIELD_NUMBER);
//                SocketManager.send(callObject);
//                SPUtils.putBoolean(Utils.getContext(), SPConfig.REDPACK_UNLOCKED, true);
//            }

            UnlockModel veriseUnlockModel = unlockModelHashMap.get(UnlockConfig.BUILDING);
            boolean veriseUnlock = SPUtils.getBoolean(Utils.getContext(), SPConfig.BUILDING_UNLOCKED, false);
            if ((maxLevel == veriseUnlockModel.getUnlock_level()) && !veriseUnlock) {
                VeriseDialog veriseDialog = new VeriseDialog();
                veriseDialog.show(getFragmentManager(), "veriseDialog");
                SPUtils.putBoolean(Utils.getContext(), SPConfig.BUILDING_UNLOCKED, true);
            }

            UnlockModel onlineRewardUnlockModel = unlockModelHashMap.get(UnlockConfig.ONLINE_REWARD);
            boolean onlineRewardUnlock = SPUtils.getBoolean(Utils.getContext(), SPConfig.ONLINE_REWARD_UNLOCKED, false);
            iv_bonus_red_point.setVisibility((maxLevel == onlineRewardUnlockModel.getUnlock_level()) && !onlineRewardUnlock ? View.VISIBLE : View.INVISIBLE);

            UnlockModel taskUnlockModel = unlockModelHashMap.get(UnlockConfig.TASKS);
            boolean taskUnlockClick = SPUtils.getBoolean(Utils.getContext(), SPConfig.TASK_UNLOCKED, false);
            iv_task_red_point.setVisibility((maxLevel == taskUnlockModel.getUnlock_level()) && !taskUnlockClick ? View.VISIBLE : View.INVISIBLE);


            long before = SPUtils.getLong(Utils.getContext(), SPConfig.CURRENT_TIME, System.currentTimeMillis());
            boolean isSomeDay = DateUtil.isSomeDay(before, System.currentTimeMillis());
            // 如果不是同一天
            if (!isSomeDay) {
                SPUtils.remove(Utils.getContext(), SPConfig.LUCK_WHEEL_UNLOCKED);
            }
            UnlockModel luckWheelUnlockModel = unlockModelHashMap.get(UnlockConfig.LUCK_WHEEL);
            boolean luckWheelUnlock = SPUtils.getBoolean(Utils.getContext(), SPConfig.LUCK_WHEEL_UNLOCKED, false);
            int wheelCount = SPUtils.getInt(Utils.getContext(), SPConfig.WHEEL_COUNT, 0);
            iv_luck_red_point.setVisibility((maxLevel >= luckWheelUnlockModel.getUnlock_level()) && !luckWheelUnlock && wheelCount > 0 ? View.VISIBLE : View.INVISIBLE);
        } catch (Exception e) {
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTodayDivide(int todayDivide) {
        try {
            todayBonusCount.setText(NumberUtil.getWanDividedNumber(BigDecimal.valueOf(todayDivide)).toString() + Utils.getString(R.string.cny_unit));
        } catch (Exception e) {
            Log.i("setTodayDivide", e.getMessage());
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTotalOutPut(String totalOutPut) {
        Log.i("totalOutPut", totalOutPut + "/s");
        tv_total_output.setText(CatHelperUtil.getDispalyedNumber(totalOutPut) + "/s");
    }


    private void setBuyPrice(String currBuyPrice) {
        Log.i("currBuyPrice", String.valueOf(currBuyPrice));
        if (TextUtils.isEmpty(currBuyPrice)) {
            return;
        }
        tvCurrentPrice.setText(CatHelperUtil.getDispalyedNumber(currBuyPrice));
    }

    @SuppressLint("SetTextI18n")
    private void setBuyLevelResource(int buyLevel) {
        if (buyLevel <= 0) {
            return;
        }
        tvCurrentLevel.setText(String.valueOf(buyLevel));
        currentLevelIcon.setImageResource(ImageSourceUtil.getBuyResorceByLevel(buyLevel));
    }

    private void setTotalCoin(String totalCoin) {
        if (TextUtils.isEmpty(totalCoin)) {
            return;
        }
        tvTotalCoin.setText(CatHelperUtil.getDispalyedNumber(totalCoin));
        BigDecimal bigDecimal = new BigDecimal(totalCoin);
        if (bigDecimal.compareTo(BigDecimal.ZERO) >= 0) {
            totalOutPutAmountInteger = bigDecimal;
        }
    }


    @Override
    protected void onUserVisibleResume() {
        super.onUserVisibleResume();
        refresh();
        initSound();
        showAdsReward();
        initPersonAnimation(SPUtils.getString(getContext(), SPConfig.MAN_ANIMATION_TYPE, "nan.json"));
    }

    private void showAdsReward() {
        if (null != s2C_pangolinRewards && s2C_pangolinRewards.size() > 0) {
            S2CPangolinReward.S2C_PangolinReward s2C_pangolinReward = s2C_pangolinRewards.get(0);
            RewardObject rewardObj = new RewardObject();
            rewardObj.amount = s2C_pangolinReward.getRewardAmount();
            rewardObj.rewardType = 3;
            rewardObj.kittyLevelList = integers;
            rewardObj.noTimeView = TextUtils.equals(s2C_pangolinReward.getRewardName(), AdsType.ON_HOOK) ? true : false;
            RewardDialogUtil.showRewardDialog(rewardObj, getFragmentManager());
            s2C_pangolinRewards.clear();
        }
    }

    @Override
    public void onConnected() {
        if (!refreshed) {
            refresh();
        }
        MainHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshed = false;
            }
        }, 3000);
    }

    public void initDragView(List<SyncKittyInfoOuterClass.kittyLevelList> homeGridModels) {
        integers = new ArrayList<>();
        try {
            for (int i = 0; i < dragViewList.size(); i++) {
                SyncKittyInfoOuterClass.kittyLevelList kittyLevelList = homeGridModels.get(i);
                int level = kittyLevelList.getLevel();
                long expireTime = kittyLevelList.getExpireTime();
                long expireIn = kittyLevelList.getExpireIn();
//                kittyLevelList.
                DragView dragView = dragViewList.get(i);
                // level大于0时设置资源
                if (level > 0) {
                    DragResourceViewModel dragResourceViewModel = dragView.getDragViewDataModel();
                    if (null == dragResourceViewModel) {
                        dragResourceViewModel = new DragResourceViewModel();
                    }
                    dragResourceViewModel.setExpireTime(expireTime);
                    dragResourceViewModel.setExpireIn(expireIn);
                    dragResourceViewModel.setLevel(level);
                    dragView.setDragViewDataModel(dragResourceViewModel);
                    dragView.setResourceValue();
                    integers.add(level);
                    dragView.setKittyList(integers);
                } else {
                    dragView.setDragViewDataModel(null);
                    dragView.setResourceValue();
                }
                dragView.setPosition(i);
            }
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_current_bonus_and_level)
    public void BuyCat(View view) {
        buyDragView();
    }

    /**
     * 购买猫猫
     */
    private void buyDragView() {
        try {
            int maxLevel = SPUtils.getInt(getContext(), SPConfig.MAX_LEVEL, 0);
            int buyLevel = getBuyLevelFromMaxLevel(maxLevel);


            C2SBuyKitty.C2S_BuyKitty.Builder c2S_buyKitty = C2SBuyKitty.C2S_BuyKitty.newBuilder().setBuyLevel(buyLevel);
            MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SBuyKitty(c2S_buyKitty).setMessageId(MessageOuterClass.Message.C2SBUYKITTY_FIELD_NUMBER);
            SocketManager.send(builder);
            ll_current_bonus_and_level.setClickable(false);
            MainHandler.getInstance().postDelayed(() -> ll_current_bonus_and_level.setClickable(true), 300);
            if (null != mediaPlayer && !mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(fd_buy.getFileDescriptor(), fd_buy.getStartOffset(), fd_buy.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.setOnErrorListener((mp, what, extra) -> false);
            }
        } catch (Exception e) {
            ToastUtils.showLong("购买失败");
        }
    }

    @OnClick(R2.id.ll_reposity)
    public void onViewClicked() {
        try {
            DialogFragment fragment = (DialogFragment) ARouter.getInstance().build(RouterActivityPath.DIALOG_REPERTORY_PATH).navigation();
            fragment.show(getFragmentManager(), "");
//            ARouter.getInstance().build(RouterActivityPath.ACTIVITY_REPERTORY_PATH).navigation();
        } catch (Exception e) {
        }
    }

    //点击猫
    @OnClick(R2.id.animation_view_cat)
    public void animation_view_cat() {
        try {
            int kittyLevel = SPUtils.getInt(Utils.getContext(), SPConfig.ANIMATION_LEVEL, 1);
            if (kittyLevel == 45 || isDividing) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentConfig.DRAG_MODEL, dividingDragResourceViewModel);
                float start = null == bigDecimal1 ? 0 : bigDecimal1.floatValue();
                bundle.putFloat(IntentConfig.DIVIDING_MONEY, start);
                DividingKittyDialog dividingKittyDialog = new DividingKittyDialog();
                dividingKittyDialog.setArguments(bundle);
                dividingKittyDialog.show(getFragmentManager(), "dividingKittyDialog");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt(IntentConfig.ANIMATION_LEVEL, kittyLevel);
            KittyInfoDialog kittyInfoDialog = new KittyInfoDialog();
            kittyInfoDialog.setArguments(bundle);
            kittyInfoDialog.show(getFragmentManager(), "kittyInfoDialog");
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.fl_check_level)
    public void checkLevel() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.home_level_text));
            webViewModel.setUrl(Utils.getString(R.string.home_level_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.fl_check_guid)
    public void checkGuid() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.home_guid_text));
            webViewModel.setUrl(Utils.getString(R.string.home_guid_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.ll_image_share)
    public void imageShare() {
        try {
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.home_image_share));
            webViewModel.setUrl(Utils.getString(R.string.home_image_share_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.fl_lucky_rotate)
    public void luckyRotate() {
        try {
            if (!CatHelperUtil.isUnlocked(UnlockConfig.LUCK_WHEEL)) {
                return;
            }
            if (null != integers) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentConfig.KITTY_LEVEL_LIST, integers);
                ARouter.getInstance().build(RouterActivityPath.ACTIVITY_TURNPLATE_PATH).with(bundle).navigation();
                if (!SPUtils.getBoolean(Utils.getContext(), SPConfig.LUCK_WHEEL_UNLOCKED, false)) {
                    SPUtils.putLong(Utils.getContext(), SPConfig.CURRENT_TIME, System.currentTimeMillis());
                    SPUtils.putBoolean(Utils.getContext(), SPConfig.LUCK_WHEEL_UNLOCKED, true);
                }
                iv_luck_red_point.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @OnClick(R2.id.fl_get_bonus)
    public void getBonus() {
        try {
            if (!CatHelperUtil.isUnlocked(UnlockConfig.ONLINE_REWARD)) {
                return;
            }
            MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setMessageId(1008);
            SocketManager.send(callObject);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.fl_veris_icon)
    public void toBuild() {
        try {
            toVeris();
            SPUtils.putBoolean(Utils.getContext(), SPConfig.BUILDING_UNLOCKED_CLICKED, true);
            iv_build_red_point.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.fl_adventure_icon)
    public void toAdventures() {
        try {
            toAdventure();
            SPUtils.putBoolean(Utils.getContext(), SPConfig.ADVENTURE_UNLOCKED_CLICKED, true);
            iv_adventure_red_point.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
        }
    }


//    @OnClick(R2.id.iv_all_play)
//    public void showAllPlay() {
//        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.home_menu_popup, null);
//        PopupWindow popWnd = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
////        boolean redpacket = CatHelperUtil.isUnlockedNoToast(UnlockConfig.REDPACKET);
//        boolean adventure = CatHelperUtil.isUnlockedNoToast(UnlockConfig.ADVENTURE);
//        boolean building = CatHelperUtil.isUnlockedNoToast(UnlockConfig.BUILDING);
//        ImageView iv_veris = contentView.findViewById(R.id.iv_veris);
//        ImageView iv_home_adventure = contentView.findViewById(R.id.iv_home_adventure);
//        ImageView iv_red_pack = contentView.findViewById(R.id.iv_red_pack);
//
//        ImageView iv_veris_red_point = contentView.findViewById(R.id.iv_veris_red_point);
//        ImageView iv_red_pack_red_point = contentView.findViewById(R.id.iv_red_pack_red_point);
//        ImageView iv_home_adventure_red_point = contentView.findViewById(R.id.iv_home_adventure_red_point);
//
//        boolean aBoolean = SPUtils.getBoolean(Utils.getContext(), SPConfig.BUILDING_UNLOCKED_CLICKED, false);
//        boolean aBoolean1 = SPUtils.getBoolean(Utils.getContext(), SPConfig.ADVENTURE_UNLOCKED_CLICKED, false);
//        boolean aBoolean2 = SPUtils.getBoolean(Utils.getContext(), SPConfig.REDPACK_UNLOCKED_CLICKED, false);
//        UnlockModel veriseUnlockModel = unlockModelHashMap.get(UnlockConfig.BUILDING);
//        UnlockModel adventureUnlockModel = unlockModelHashMap.get(UnlockConfig.ADVENTURE);
////        UnlockModel redPackUnlockModel = unlockModelHashMap.get(UnlockConfig.REDPACKET);
//        int maxLevel = SPUtils.getInt(Utils.getContext(), SPConfig.MAX_LEVEL, 0);
//
//        iv_veris_red_point.setVisibility(building && maxLevel == veriseUnlockModel.getUnlock_level() && !aBoolean ? View.VISIBLE : View.INVISIBLE);
////        iv_red_pack_red_point.setVisibility(redpacket && maxLevel == redPackUnlockModel.getUnlock_level() && !aBoolean2 ? View.VISIBLE : View.INVISIBLE);
//        iv_home_adventure_red_point.setVisibility(adventure && maxLevel == adventureUnlockModel.getUnlock_level() && !aBoolean1 ? View.VISIBLE : View.INVISIBLE);
//
//
//        iv_veris.setVisibility(building ? View.VISIBLE : View.GONE);
//        iv_home_adventure.setVisibility(adventure ? View.VISIBLE : View.GONE);
////        iv_red_pack.setVisibility(redpacket ? View.VISIBLE : View.GONE);
//        iv_veris.setOnClickListener(v -> {
//            toVeris();
//            SPUtils.putBoolean(Utils.getContext(), SPConfig.BUILDING_UNLOCKED_CLICKED, true);
//            iv_veris_red_point.setVisibility(View.INVISIBLE);
//            popWnd.dismiss();
//        });
//        iv_home_adventure.setOnClickListener(v -> {
//            toAdventure();
//            SPUtils.putBoolean(Utils.getContext(), SPConfig.ADVENTURE_UNLOCKED_CLICKED, true);
//            iv_home_adventure_red_point.setVisibility(View.INVISIBLE);
//            popWnd.dismiss();
//        });
////        iv_red_pack.setOnClickListener(v -> {
////            toRedPack();
////            SPUtils.putBoolean(Utils.getContext(), SPConfig.REDPACK_UNLOCKED_CLICKED, true);
////            iv_red_pack_red_point.setVisibility(View.INVISIBLE);
////            popWnd.dismiss();
////        });
//        popWnd.setAnimationStyle(R.style.pop_animation);
//        popWnd.showAsDropDown(allPlay, 0, -Utils.dip2px(40), Gravity.CENTER);
//        setBackgroundAlpha(0.4f);
//        popWnd.setOnDismissListener(() -> {
//            setBackgroundAlpha(1f);
//            allPlay.setVisibility(View.VISIBLE);
//        });
//    }

    private void setBackgroundAlpha(float alpha) {
        try {
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = alpha;
            getActivity().getWindow().setAttributes(lp);
        } catch (Exception e) {
        }
    }

    public void toVeris() {
        try {
            if (!CatHelperUtil.isUnlocked(UnlockConfig.BUILDING)) {
                return;
            }
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setTitle(Utils.getString(R.string.home_veris));
            webViewModel.setUrl(Utils.getString(R.string.home_veris_url));
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConfig.WEB_MODEL, webViewModel);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }
//
//    public void toRedPack() {
//        if (!CatHelperUtil.isUnlocked(UnlockConfig.REDPACKET)) {
//            return;
//        }
//        C2SRedPacketShare.C2S_RedPacketShare.Builder c2S_RedPacketShare = C2SRedPacketShare.C2S_RedPacketShare.newBuilder().setCallTimestamp((int) (System.currentTimeMillis() / 1000));
//        MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SRedPacketShare(c2S_RedPacketShare).setMessageId(MessageOuterClass.Message.C2SREDPACKETSHARE_FIELD_NUMBER);
//        SocketManager.send(callObject);
//    }

    public void toAdventure() {
        try {
            if (!CatHelperUtil.isUnlocked(UnlockConfig.ADVENTURE)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList(IntentConfig.KITTY_LEVEL_LIST, integers);
            Intent intent = new Intent(getActivity(), AdventureActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_fortune_profit)
    public void toForTuneProfit() {
        try {
            Intent intent = new Intent(getActivity(), FortuneKittyProfitActivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.tv_loaction)
    public void tv_loaction() {
        try {
            Intent intent = new Intent(getActivity(), MapActivity.class);
            startActivity(intent);
            SPUtils.putBoolean(Utils.getContext(), SPConfig.MAP_UNLOCKED, true);
            if (null != iv_map_red_point) {
                iv_map_red_point.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.one_key_merge)
    public void one_key_merge() {
        try {
            C2SOnekey.C2S_Onekey.Builder builder = C2SOnekey.C2S_Onekey.newBuilder().setCallTimestamp((int) (System.currentTimeMillis() / 1000));
            MessageOuterClass.Message.Builder callObject = MessageOuterClass.Message.newBuilder().setC2SOnekey(builder).setMessageId(MessageOuterClass.Message.C2SONEKEY_FIELD_NUMBER);
            SocketManager.send(callObject);
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.animation_view_nan)
    public void animation_view_nan() {
        try {
            String animationType = SPUtils.getString(getContext(), SPConfig.MAN_ANIMATION_TYPE, "nan.json");
            initPersonAnimation(TextUtils.equals(animationType, "nan.json") ? "nv.json" : "nan.json");
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.one_key_vedio)
    public void one_key_vedio() {
        try {
            long userId = SPUtils.getLong(getContext(), SPConfig.USER_ID, -1L);
            HttpUtils.setVideoOneKey(new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                    ToastUtils.showLong(baseResponse.message);
                }

                @Override
                public void onError(Throwable throwable) {

                }
            }, userId, AdsType.MORE_COIN);
        } catch (Exception e) {
        }
    }

    public void toTask() {
        taskBox();
    }

    @OnClick(R2.id.fl_task_box)
    public void taskBox() {
        if (!CatHelperUtil.isUnlocked(UnlockConfig.TASKS)) {
            return;
        }
        if (isClicked) {
            return;
        }
        isClicked = true;
        MainHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                isClicked = false;
            }
        }, 1500);
        SPUtils.putBoolean(Utils.getContext(), SPConfig.TASK_UNLOCKED, true);
        iv_task_red_point.setVisibility(View.INVISIBLE);
        BottomSheetDialog dialog = new BottomSheetDialog(Objects.requireNonNull(getActivity()), R.style.BottomSheetDialog);
        View view = getLayoutInflater().inflate(R.layout.sheet_task_box, null);
        ImageView imageView = view.findViewById(R.id.iv_close_task_box);
        TextView sign = view.findViewById(R.id.tv_sign);
        TextView buyKitty = view.findViewById(R.id.tv_receive_buy_kitty);
        TextView buyKittyTitle = view.findViewById(R.id.tv_buy_kitty_title);
        TextView buyKittyDesc = view.findViewById(R.id.tv_buy_kitty_desc);
        TextView tv_set_social = view.findViewById(R.id.tv_set_social);
        TextView tv_set_social_desc = view.findViewById(R.id.tv_set_social_desc);
        TextView tv_withdraw_author = view.findViewById(R.id.tv_withdraw_author);
        TextView tv_withdraw_author_desc = view.findViewById(R.id.tv_withdraw_author_desc);
        ListView levelTask = view.findViewById(R.id.lv_level_task);
        ImageView dayOne = view.findViewById(R.id.iv_day_one);
        ImageView dayTwo = view.findViewById(R.id.iv_day_two);
        ImageView dayThree = view.findViewById(R.id.iv_day_three);
        ImageView dayFour = view.findViewById(R.id.iv_day_four);
        ImageView dayFive = view.findViewById(R.id.iv_day_five);
        ImageView daySix = view.findViewById(R.id.iv_day_six);
        ImageView daySeven = view.findViewById(R.id.iv_day_seven);
        LinearLayout lldayOne = view.findViewById(R.id.ll_day_one);
        LinearLayout lldayTwo = view.findViewById(R.id.ll_day_two);
        LinearLayout lldayThree = view.findViewById(R.id.ll_day_three);
        LinearLayout lldayFour = view.findViewById(R.id.ll_day_four);
        LinearLayout lldayFive = view.findViewById(R.id.ll_day_five);
        LinearLayout lldaySix = view.findViewById(R.id.ll_day_six);
        LinearLayout lldaySeven = view.findViewById(R.id.ll_day_seven);
        TextView tvDayOne = view.findViewById(R.id.tv_day_one);
        tvDayOne.setTag(1);
        TextView tvDayTwo = view.findViewById(R.id.tv_day_two);
        tvDayTwo.setTag(2);
        TextView tvDayThree = view.findViewById(R.id.tv_day_three);
        tvDayThree.setTag(3);
        TextView tvDayFour = view.findViewById(R.id.tv_day_four);
        tvDayFour.setTag(4);
        TextView tvDayFive = view.findViewById(R.id.tv_day_five);
        tvDayFive.setTag(5);
        TextView tvDaySix = view.findViewById(R.id.tv_day_six);
        tvDaySix.setTag(6);
        TextView tvDaySeven = view.findViewById(R.id.tv_day_seven);
        tvDaySeven.setTag(7);
        imageView.setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
        HttpUtils.getTaskModel(new IResponse<TaskModel>() {
            @Override
            public void onSuccess(BaseResponse<TaskModel> baseResponse) {
                try {
                    if (null != baseResponse && null != baseResponse.data) {
                        TaskModel taskModel = baseResponse.data;
                        TaskModel.UserSignTaskBean user_sign_task = taskModel.getUser_sign_task();
                        TaskModel.UserSignTaskBean.BuyKittyTaskBean buy_kitty_task = taskModel.getBuy_kitty_task();
                        HashMap<String, TaskModel.UserSignTaskBean.LevelKittyTasksBean> level_kitty_tasks = taskModel.getLevel_kitty_tasks();
                        TaskModel.UserSignTaskBean.SocialTaskBean socialTaskBean = taskModel.getSocial_task();
                        TaskModel.UserSignTaskBean.WithdrawVerificationBean withdrawVerificationBean = taskModel.getWithdraw_verification();
                        int curDay = user_sign_task.getCurr_day() + 1;
                        Map<String, TaskModel.UserSignTaskBean.RewardBean> stringRewardBeanMap = user_sign_task.getRewards();
                        TaskModel.UserSignTaskBean.RewardBean rewardBean = stringRewardBeanMap.get(String.valueOf(curDay));
                        setSignDay(rewardBean, lldayOne, tvDayOne, dayOne, curDay, user_sign_task.getStatus(), sign);
                        setSignDay(rewardBean, lldayTwo, tvDayTwo, dayTwo, curDay, user_sign_task.getStatus(), sign);
                        setSignDay(rewardBean, lldayThree, tvDayThree, dayThree, curDay, user_sign_task.getStatus(), sign);
                        setSignDay(rewardBean, lldayFour, tvDayFour, dayFour, curDay, user_sign_task.getStatus(), sign);
                        setSignDay(rewardBean, lldayFive, tvDayFive, dayFive, curDay, user_sign_task.getStatus(), sign);
                        setSignDay(rewardBean, lldaySix, tvDaySix, daySix, curDay, user_sign_task.getStatus(), sign);
                        setSignDay(rewardBean, lldaySeven, tvDaySeven, daySeven, curDay, user_sign_task.getStatus(), sign);
                        tv_set_social_desc.setText(Utils.getString(R.string.get) + socialTaskBean.getReward().getValue() + Utils.getString(R.string.ads_bouns));
                        tv_withdraw_author_desc.setText(Utils.getString(R.string.get) + withdrawVerificationBean.getReward().getValue() + Utils.getString(R.string.ads_bouns));
                        buyKittyTitle.setText(Html.fromHtml(Utils.getString(R.string.buy) + buy_kitty_task.getNeed_buy_num() + Utils.getString(R.string.kitty_count_unit) + "(<font color=\"#FF4B5D\">"
                                + (buy_kitty_task.getCurr_buy_num() > buy_kitty_task.getNeed_buy_num() ? buy_kitty_task.getNeed_buy_num() : buy_kitty_task.getCurr_buy_num()) + "</font>/" + buy_kitty_task.getNeed_buy_num() + ")"));
                        buyKittyDesc.setText(Utils.getString(R.string.get) + TimeDescUtil.getTimeDesc(buy_kitty_task.getReward().getAmount()) + Utils.getString(R.string.minite_reward));
                        setTaskStatus(sign, user_sign_task.getStatus(), user_sign_task.getCurr_day());
                        setRewardXStatus(buyKitty, buy_kitty_task.getStatus(), buy_kitty_task.getReward(), R.drawable.unreceived_back, R.drawable.buy_kitty_receive_back, "buy_kitty_task");
                        setSocailStatus(tv_set_social, socialTaskBean, R.drawable.set_social_receive_back, R.drawable.unreceived_back, "social_task");
                        setWithDrawAuthor(tv_withdraw_author, withdrawVerificationBean, R.drawable.author_receive_back, R.drawable.unreceived_back, "withdraw_verification");
                        List<TaskModel.UserSignTaskBean.LevelKittyTasksBean> levelKittyTasksBeans = new ArrayList<>();
                        TreeMap<Integer, TaskModel.UserSignTaskBean.LevelKittyTasksBean> integerLevelKittyTasksBeanTreeMap = new TreeMap<>();
                        for (Map.Entry<String, TaskModel.UserSignTaskBean.LevelKittyTasksBean> entry : level_kitty_tasks.entrySet()) {
                            TaskModel.UserSignTaskBean.LevelKittyTasksBean levelKittyTasksBean = entry.getValue();
                            levelKittyTasksBean.setId(entry.getKey());
                            integerLevelKittyTasksBeanTreeMap.put(levelKittyTasksBean.getStatus(), levelKittyTasksBean);
                        }
                        for (Map.Entry<Integer, TaskModel.UserSignTaskBean.LevelKittyTasksBean> kittyTasksBeanEntry : integerLevelKittyTasksBeanTreeMap.entrySet()) {
                            TaskModel.UserSignTaskBean.LevelKittyTasksBean levelKittyTasksBean = kittyTasksBeanEntry.getValue();
                            levelKittyTasksBeans.add(levelKittyTasksBean);
                        }
                        LevelTaskAdapter levelTaskAdapter = new LevelTaskAdapter(levelKittyTasksBeans, getContext());
                        levelTaskAdapter.setOnReceiveListener(rewardObject -> RewardDialogUtil.showRewardDialog(rewardObject, getFragmentManager()));
                        levelTask.setAdapter(levelTaskAdapter);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }

    private void setWithDrawAuthor(TextView tv_withdraw_author, TaskModel.UserSignTaskBean.WithdrawVerificationBean withdrawVerificationBean, int receiveableback1, int unreceived_back, String with_draw_) {
        int status = withdrawVerificationBean.getStatus();
        if (status == 1) {
            tv_withdraw_author.setBackgroundResource(receiveableback1);
            tv_withdraw_author.setText(Utils.getString(R.string.receive));
            tv_withdraw_author.setOnClickListener(v -> HttpUtils.receiveTask(new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                    try {
                        if (baseResponse.code == 0) {
                            RewardObject rewardObject = new RewardObject();
                            rewardObject.rewardType = 9;
                            rewardObject.kittyLevelList = integers;
                            rewardObject.value = withdrawVerificationBean.getReward().getValue();
                            RewardDialogUtil.showRewardDialog(rewardObject, getFragmentManager());
                            tv_withdraw_author.setBackgroundResource(unreceived_back);
                            tv_withdraw_author.setText(Utils.getString(R.string.received));
                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onError(Throwable throwable) {

                }
            }, with_draw_));
        } else if (status == 0) {
            tv_withdraw_author.setBackgroundResource(unreceived_back);
            tv_withdraw_author.setText(Utils.getString(R.string.task_unfinsih));
        } else if (status == 2) {
            tv_withdraw_author.setBackgroundResource(unreceived_back);
            tv_withdraw_author.setText(Utils.getString(R.string.received));
        }
    }

    private void setSocailStatus(TextView tv_set_social, TaskModel.UserSignTaskBean.SocialTaskBean socialTaskBean, int receiveableback1, int unreceived_back, String socialTask) {
        int status = socialTaskBean.getStatus();
        if (status == 1) {
            tv_set_social.setBackgroundResource(receiveableback1);
            tv_set_social.setText(Utils.getString(R.string.receive));
            tv_set_social.setOnClickListener(v -> HttpUtils.receiveTask(new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                    try {
                        if (baseResponse.code == 0) {
                            RewardObject rewardObject = new RewardObject();
                            rewardObject.rewardType = 9;
                            rewardObject.kittyLevelList = integers;
                            rewardObject.value = socialTaskBean.getReward().getValue();
                            RewardDialogUtil.showRewardDialog(rewardObject, getFragmentManager());
                            tv_set_social.setBackgroundResource(unreceived_back);
                            tv_set_social.setText(Utils.getString(R.string.received));
                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onError(Throwable throwable) {

                }
            }, socialTask));
        } else if (status == 0) {
            tv_set_social.setBackgroundResource(unreceived_back);
            tv_set_social.setText(Utils.getString(R.string.task_unfinsih));
        } else if (status == 2) {
            tv_set_social.setBackgroundResource(unreceived_back);
            tv_set_social.setText(Utils.getString(R.string.received));
        }
    }

    private void setRewardXStatus(TextView textView, int status, TaskModel.UserSignTaskBean.RewardBeanX reward, int unreceived_back, int receiveableback1, String buy_kitty_task) {
        if (status == 1) {
            textView.setBackgroundResource(receiveableback1);
            textView.setText(Utils.getString(R.string.receive));
            textView.setOnClickListener(v -> HttpUtils.receiveTask(new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                    try {
                        if (baseResponse.code == 0) {
                            RewardObject rewardObject = new RewardObject();
                            rewardObject.rewardType = 3;
                            rewardObject.amount = String.valueOf(reward.getAmount());
                            rewardObject.kittyLevelList = integers;
                            RewardDialogUtil.showRewardDialog(rewardObject, getFragmentManager());
                            textView.setBackgroundResource(unreceived_back);
                            textView.setText(Utils.getString(R.string.received));
                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onError(Throwable throwable) {

                }
            }, buy_kitty_task));
        } else if (status == 0) {
            textView.setBackgroundResource(unreceived_back);
            textView.setText(Utils.getString(R.string.task_unfinsih));
        } else if (status == 2) {
            textView.setBackgroundResource(unreceived_back);
            textView.setText(Utils.getString(R.string.received));
        }

    }


    private void setTaskStatus(TextView everLogin, int status, int current_day) {
        if (status == 0) {
            everLogin.setText(Utils.getString(R.string.sign_now));
        } else if (status == 2) {
            everLogin.setText(Utils.getString(R.string.signed) + ((current_day + 1) > 7 ? 7 : current_day + 1) + Utils.getString(R.string.day_text));
        }
    }


    @SuppressLint("SetTextI18n")
    private void setSignDay(TaskModel.UserSignTaskBean.RewardBean rewardBean, LinearLayout
            lldayOne, TextView textView, ImageView ivDay, int curr_day, int status, TextView everLogin) {
        int day = (int) textView.getTag();
        if (day > curr_day) {
            textView.setText(day + Utils.getString(R.string.day_text));
            textView.setTextColor(Utils.getColor(R.color.white));
            ivDay.setImageResource(R.drawable.coin_task_icon);
            lldayOne.setBackgroundResource(R.drawable.task_box_not_finish_back);
        } else if (day == curr_day) {
            if (status == 0) {
                textView.setText(day + Utils.getString(R.string.day_text));
                textView.setTextColor(Utils.getColor(R.color.white));
                ivDay.setImageResource(R.drawable.coin_task_icon);
                lldayOne.setBackgroundResource(R.drawable.task_box_not_finish_back);
                everLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpUtils.signTask(new IResponse() {
                            @Override
                            public void onSuccess(BaseResponse baseResponse) {
                                Log.i("signTask", baseResponse.message);
                                if (baseResponse.code == 0) {
                                    textView.setText(Utils.getString(R.string.already_task));
                                    textView.setTextColor(Utils.getColor(R.color.dark));
                                    lldayOne.setBackgroundResource(R.drawable.task_box_already_back);
                                    if (day < 7) {
                                        ivDay.setImageResource(R.drawable.task_back_already);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable(IntentConfig.SIGN_TASK_REWARD, rewardBean);
                                        bundle.putInt(IntentConfig.SIGN_TASK_DAY, day);
                                        bundle.putIntegerArrayList(IntentConfig.KITTY_LEVEL_LIST, integers);
                                        SignLogDailog signLogDialog = new SignLogDailog();
                                        signLogDialog.setArguments(bundle);
                                        signLogDialog.show(getFragmentManager(), "signLogDialog");
//                                        RewardObject rewardObject = new RewardObject();
//                                        rewardObject.rewardType = rewardBean.getType();
//                                        rewardObject.amount = String.valueOf(rewardBean.getAmount());
//                                        rewardObject.kittyLevelList = integers;
//                                        RewardDialogUtil.showRewardDialog(rewardObject, getFragmentManager());
                                    } else {
                                        ivDay.setImageResource(R.drawable.task_forturn_kitty_icon);
                                        RewardObject rewardObject = new RewardObject();
                                        rewardObject.rewardType = rewardBean.getType();
                                        rewardObject.amount = String.valueOf(rewardBean.getAmount());
                                        RewardDialogUtil.showRewardDialog(rewardObject, getFragmentManager());
                                    }
                                    everLogin.setText(Utils.getString(R.string.signed) + day + Utils.getString(R.string.day_text));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }
                        });
                    }
                });
            } else {
                textView.setText(Utils.getString(R.string.already_task));
                textView.setTextColor(Utils.getColor(R.color.dark));
                ivDay.setImageResource(R.drawable.task_back_already);
                lldayOne.setBackgroundResource(R.drawable.task_box_already_back);
            }
        } else {
            textView.setText(Utils.getString(R.string.already_task));
            textView.setTextColor(Utils.getColor(R.color.dark));
            ivDay.setImageResource(R.drawable.task_back_already);
            lldayOne.setBackgroundResource(R.drawable.task_box_already_back);
        }
        if (day == 7) {
            ivDay.setImageResource(R.drawable.task_forturn_kitty_icon);
        }
    }


    //每个item的加钱动画
    private void initAnimation(List<DragView> dragViews) {
        try {
            initSound();
            ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(7);
            Random rand = new Random();
            fd = Utils.getResources().getAssets().openFd("music/coin_add.mp3");
            fd_merge = Utils.getResources().getAssets().openFd("music/items_merge_general.mp3");
            fd_buy = Utils.getResources().getAssets().openFd("music/buy_items.wav");
            int[] period = {3000, 7000, 9000, 5000, 7000, 11000, 6000};
            for (DragView dragView : dragViews) {
                int myRand = rand.nextInt(period.length);
                if (null != dragView) {
                    int numTime = period[myRand];
                    FrameLayout dragFrameLayout = (FrameLayout) dragView.getDragView();
                    ImageView levelIcon = dragFrameLayout.findViewById(R.id.iv_cat_icon);
                    LinearLayout ll_drag_view_coin = dragFrameLayout.findViewById(R.id.ll_drag_view_coin);
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            MainHandler.getInstance().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DragResourceViewModel dragResourceViewModel = dragView.getDragViewDataModel();
                                    if (null != dragResourceViewModel && dragResourceViewModel.getLevel() > 0 && dragResourceViewModel.getLevel() != 45) {
                                        ll_drag_view_coin.setVisibility(View.VISIBLE);
                                        showAnimation(ll_drag_view_coin, levelIcon);
                                        //动态添加总额度
                                        BigDecimal ads_rewards = CatHelperUtil.getKittyOutput(dragResourceViewModel.getLevel());
                                        BigDecimal multiply = ads_rewards.multiply(new BigDecimal(numTime / 1000));
                                        if (totalOutPutAmountInteger != null) {
                                            totalOutPutAmountInteger = totalOutPutAmountInteger.add(multiply);
                                            String dispalyedNumber = CatHelperUtil.getDispalyedNumber(totalOutPutAmountInteger.toString());
                                            if (tvTotalCoin != null && dispalyedNumber != null) {
                                                tvTotalCoin.setText(dispalyedNumber);
                                            }
                                        }
                                    }
                                }
                            }, numTime);
                        }
                    };
                    newScheduledThreadPool.scheduleAtFixedRate(task, 0L, period[myRand], TimeUnit.MILLISECONDS);
                }
            }

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float width = displayMetrics.widthPixels;
            float x = ll_city_build.getX();
            float translateX = width - x;
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, -translateX, Animation.ABSOLUTE,
                    -width - ll_city_build.getMeasuredWidth(), Animation.ABSOLUTE, 0f,
                    Animation.ABSOLUTE, 0f);
            translateAnimation.setInterpolator(new LinearInterpolator());
            translateAnimation.setDuration(140000);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    try {
                        ll_city_build.clearAnimation();
                        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, -30, Animation.ABSOLUTE,
                                -width - ll_city_build.getMeasuredWidth(), Animation.ABSOLUTE, 0f,
                                Animation.ABSOLUTE, 0f);
                        translateAnimation.setInterpolator(new LinearInterpolator());
                        translateAnimation.setDuration(140000);
                        translateAnimation.setRepeatCount(Integer.MAX_VALUE);
                        translateAnimation.setRepeatMode(Animation.RESTART);
                        translateAnimation.setFillAfter(false);
                        ll_city_build.startAnimation(translateAnimation);
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            translateAnimation.setRepeatCount(0);
            translateAnimation.setFillAfter(true);
            ll_city_build.startAnimation(translateAnimation);

            TranslateAnimation translateAnimation1 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE,
                    -width, Animation.ABSOLUTE, 0f,
                    Animation.ABSOLUTE, 0f);
            translateAnimation1.setDuration(60000);
            translateAnimation1.setInterpolator(new LinearInterpolator());
            translateAnimation1.setRepeatCount(Integer.MAX_VALUE);
            translateAnimation1.setRepeatMode(Animation.RESTART);
            translateAnimation1.setFillAfter(false);
            sv_ground.startAnimation(translateAnimation1);

            Animation rotateAnimation = new RotateAnimation(0, 6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setInterpolator(new CycleInterpolator(3f));
            rotateAnimation.setDuration(700);

            TimerTask task = new TimerTask() {
                public void run() {
                    MainHandler.getInstance().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (null != ll_current_bonus_and_level) {
                                ll_current_bonus_and_level.startAnimation(rotateAnimation);
                            }
                        }
                    }, 3000);
                }
            };
            newScheduledThreadPool.scheduleAtFixedRate(task, 0L, 6000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
        }
    }

    private void showAnimation(View view, View view2) {
        try {
            AnimatorSet animSet = new AnimatorSet();
            AnimatorSet animSet2 = new AnimatorSet();
            ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationY", 0f, -Utils.dip2px(34));
            moveIn.setDuration(700);
            ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f);
            fadeInOut.setDuration(700);
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view2, "scaleX", 1f, 1.2f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view2, "scaleY", 1f, 1.2f, 1f);
            scaleX.setDuration(700);
            scaleY.setDuration(700);
            fadeOut.setDuration(30);
            animSet.play(moveIn).with(fadeInOut).with(scaleX).with(scaleY).before(fadeOut);
            animSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animSet.start();

            ObjectAnimator scale = ObjectAnimator.ofFloat(tvTotalCoin, "scaleX", 1f, 1.2f, 1f);
            ObjectAnimator scale1 = ObjectAnimator.ofFloat(tvTotalCoin, "scaleY", 1f, 1.2f, 1f);
            scale.setDuration(800);
            scale1.setDuration(800);
            animSet2.play(scale).with(scale1);
            animSet2.start();


            if (null != mediaPlayer && !mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.setOnErrorListener((mp, what, extra) -> false);
            }
        } catch (Exception e) {
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (null != mediaPlayer) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        if (null != mediaPlayer) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        NavigationBarObserver.getInstance().removeOnNavigationBarListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initSound();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (totalOutPutAmountInteger != null) {
            outState.putSerializable("totalOutPutAmountInteger", totalOutPutAmountInteger);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != tvTotalCoin) {
            tvTotalCoin.clearAnimation();
        }
        if (null != mediaPlayer) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (null != homeFragment) {
            homeFragment = null;
        }
    }

    @Override
    public void onChange(int level) {
        initAnimation(level, false, null);
    }

    @Override
    public void onDividing(DragResourceViewModel dragResourceViewModel) {
        initAnimation(dragResourceViewModel.getLevel(), false, dragResourceViewModel);
    }

    @Override
    public void onRefresh() {
        refresh();
    }


}

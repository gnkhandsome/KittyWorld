package com.kitty.kitty_base.views.dragview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.event.HomeRefreshEvent;
import com.kitty.kitty_base.fragmentdialog.AddRepertoryDialog;
import com.kitty.kitty_base.fragmentdialog.CuponKittyDialog;
import com.kitty.kitty_base.fragmentdialog.RecycleDialog;
import com.kitty.kitty_base.ui.HomeFragment;
import com.kitty.kitty_base.ui.MergeDialogActivity;
import com.kitty.kitty_base.ui.SpecialLevelBonusDialog;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.model.DragViewLocationModel;
import com.kitty.kitty_base.model.DragResourceViewModel;
import com.kitty.kitty_base.utils.CountDownTimerUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.ws.utils.SocketManager;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kitty_protos.C2SJoinBackpack;
import kitty_protos.C2SMergeKitty;
import kitty_protos.C2SSellKitty;
import kitty_protos.MessageOuterClass;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * 可拖动的悬浮按钮
 * Created by linqs on 2017/12/21.
 */

public class DragView implements View.OnTouchListener {

    @Setter
    @Getter
    private boolean isAdded;

    private Builder mBuilder;

    @Setter
    @Getter
    private int position;

    private int mStatusBarHeight, mScreenWidth, mScreenHeight;

    private int[][] btnPosition = {new int[4]};
    private boolean mTouchResult = false;
    @Setter
    @Getter
    private DragResourceViewModel dragViewDataModel;

    @Setter
    @Getter
    private DragResourceViewModel dragViewDataModelBackUp;

    @Setter
    @Getter
    private DragViewLocationModel dragViewLocationModel;

    @Setter
    @Getter
    private Map<String, DragView> dragViewMap;
    @Setter
    @Getter
    private List<DragView> dragViewList;

    //手指按下位置
    private int mStartX, mStartY, mLastX, mLastY;
    public static HomeFragment homeFragment;
    @Setter
    @Getter
    private ArrayList<Integer> kittyList;

    private DragView(Builder builder, FrameLayout rootView) {
        mBuilder = builder;
        initDragView(rootView);
    }

    public View getDragView() {
        return mBuilder.view;
    }

    public LottieAnimationView getLottioAnimationView() {
        return mBuilder.lottieAnimationView;
    }

    @SuppressLint({"WrongConstant", "NewApi"})
    public void setResourceValue() {
        try {
            DragResourceViewModel dragResourceViewModel = getDragViewDataModel();
            FrameLayout dragFrameLayout = (FrameLayout) mBuilder.view;
            ImageView levelIcon = dragFrameLayout.findViewById(R.id.iv_cat_icon);
            TextView level = dragFrameLayout.findViewById(R.id.tv_item_level);
            TextView outPut = dragFrameLayout.findViewById(R.id.tv_item_output);
            ImageView imageIcon = dragFrameLayout.findViewById(R.id.iv_item_coin);
            if (null != dragResourceViewModel && dragResourceViewModel.getLevel() == 45 && dragResourceViewModel.getExpireTime() != 0) {
                mBuilder.timer.setVisibility(View.VISIBLE);
                if (dragResourceViewModel.getExpireTime() == -1) {
                    mBuilder.timer.setText(Utils.getString(R.string.forever));
                    if (null != homeFragment) {
                        homeFragment.initAnimation(45, false, null);
                    }
                } else {
                    if (mBuilder.timeTickUtil != null) {
                        mBuilder.timeTickUtil.setCountdownInterval(1000);
                        mBuilder.timeTickUtil.setMillisInFuture(dragResourceViewModel.getExpireTime() * 1000 - System.currentTimeMillis());
                        mBuilder.timeTickUtil.start();
                        if (null != homeFragment && ((dragResourceViewModel.getExpireTime() * 1000) - System.currentTimeMillis()) > 0) {
                            homeFragment.initAnimation(45, false, dragResourceViewModel);
                        }
                    }
                }

            } else {
                mBuilder.timeTickUtil.cancel();
                mBuilder.timer.setVisibility(View.INVISIBLE);
            }

            levelIcon.setImageResource(null == dragResourceViewModel ? 0 : ImageSourceUtil.getResorceByLevel(dragResourceViewModel.getLevel()));
            level.setText(null == dragResourceViewModel ? "" : String.valueOf(dragResourceViewModel.getLevel() > 37 ? 38 : dragResourceViewModel.getLevel()));
            level.setBackgroundResource(null == dragResourceViewModel ? 0 : R.drawable.level_back);
            level.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            outPut.setText(null == dragResourceViewModel ? "" : "+" + CatHelperUtil.getDisplayKittyOutput(dragResourceViewModel.getLevel()));
            outPut.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            imageIcon.setImageResource(null == dragResourceViewModel ? 0 : R.drawable.drag_view_coin_icon);
        } catch (Exception e) {

        }
    }


    public Activity getActivity() {
        return mBuilder.activity;
    }

    public FragmentManager getFragmentManager() {
        return mBuilder.manager;
    }


    private void initDragView(FrameLayout rootView) {
        if (null == getActivity()) {
            throw new NullPointerException("the activity is null");
        }
        if (null == mBuilder.view) {
            throw new NullPointerException("the dragView is null");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mBuilder.activity.isDestroyed()) {
            return;
        }

        //屏幕宽高
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        if (null != windowManager) {
            DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
            mScreenWidth = displayMetrics.widthPixels;
            mScreenHeight = displayMetrics.heightPixels;
        }

        //状态栏高度
        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        mStatusBarHeight = frame.top;
        if (mStatusBarHeight <= 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                mStatusBarHeight = getActivity().getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        FrameLayout.LayoutParams layoutParams = createLayoutParams(mBuilder.defaultLeft, mStatusBarHeight + mBuilder.defaultTop, 0, 0);
        rootView.addView(getDragView(), layoutParams);
        getDragView().setOnTouchListener(this);
    }

    private static DragView createDragView(Builder builder, FrameLayout rootView) {
        if (null == builder) {
            throw new NullPointerException("the param builder is null when execute method createDragView");
        }
        if (null == builder.activity) {
            throw new NullPointerException("the activity is null");
        }
        if (null == builder.view) {
            throw new NullPointerException("the view is null");
        }
        DragView dragView = new DragView(builder, rootView);
        return dragView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchResult = false;
                mStartX = mLastX = (int) event.getRawX();
                mStartY = mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int left, top, right, bottom;
                int dx = (int) event.getRawX() - mLastX;
                int dy = (int) event.getRawY() - mLastY;
                left = (v.getLeft() + dx);
                if (left < 0) {
                    left = 0;
                }
                right = left + v.getWidth();
                if (right > mScreenWidth) {
                    right = mScreenWidth;
                    left = right - v.getWidth();
                }
                top = (v.getTop() + dy);
                if (top < mStatusBarHeight + 2) {
                    top = mStatusBarHeight + 2;
                }
                bottom = top + v.getHeight();
                if (bottom > mScreenHeight) {
                    bottom = mScreenHeight;
                    top = bottom - v.getHeight();
                }
                v.layout(left, top, right, bottom);
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                v.setLayoutParams(createLayoutParams(v.getLeft(), v.getTop(), 0, 0));
                break;
            case MotionEvent.ACTION_UP:
                //这里需设置LayoutParams，不然按home后回再到页面等view会回到原来的地方
                v.setLayoutParams(createLayoutParams(v.getLeft(), v.getTop(), 0, 0));
                float mLastX = event.getRawX();
                float mLastY = event.getRawY();
                float removedX = Math.abs((mLastX - mStartX) / Utils.dip2px(89));
                float removedNabsX = (mLastX - mStartX) / Utils.dip2px(89);
                float removedY = Math.abs((mLastY - mStartY) / Utils.dip2px(89));
                float removedNabsY = (mLastY - mStartY) / Utils.dip2px(89);
                int scaleX = Math.round(removedX);
                int scaleNabsX = Math.round(removedNabsX);
                int scaleY = Math.round(removedY);
                int scaleNabsY = Math.round(removedNabsY);
                // 删除
                int maxLevel = SPUtils.getInt(Utils.getContext(), SPConfig.MAX_LEVEL, 1);

                if (null != this.dragViewDataModel
                        && (mLastX > dragViewLocationModel.getOriginRight() - Utils.dip2px(20))
                        && (mLastY > dragViewLocationModel.getOriginTop() - Utils.dip2px(60)) && (mLastY < dragViewLocationModel.getOriginTop() + Utils.dip2px(50))) {
                    RecycleDialog recycleDialog = new RecycleDialog();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(IntentConfig.DRAGRESOURCEVIEWMODEL, dragViewDataModel);
                    recycleDialog.setArguments(bundle);
                    recycleDialog.show(getFragmentManager(), "recycleDialog");
                    recycleDialog.setIOnClickListener(new BaseDialogFragment.IOnClickListener() {
                        @Override
                        public void onDismissClick() {
                        }

                        @Override
                        public void onConfirmClick() {
                            try {
                                if (dragViewDataModel.getLevel() > 37) {
                                    recoverPlace();
                                    ToastUtils.showShortCenter(R.string.this_level_cannt_recycle);
                                    return;
                                }

                                if (dragViewDataModel.getLevel() == maxLevel) {
                                    recoverPlace();
                                    ToastUtils.showShortCenter(R.string.max_level_cannt_recycle);
                                    return;
                                }
                                C2SSellKitty.C2S_SellKitty.Builder c2S_mergeKitty = C2SSellKitty.C2S_SellKitty.newBuilder().setPosition(getPosition());
                                MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SSellKitty(c2S_mergeKitty).setMessageId(MessageOuterClass.Message.C2SSELLKITTY_FIELD_NUMBER);
                                SocketManager.send(builder);
                                DragView.this.setDragViewDataModel(null);
                                DragView.this.setResourceValue();
                            } catch (Exception e) {
                            }
                        }
                    });
                    recoverPlace();
                    return true;
                }

                // 加入仓库
                if (null != this.dragViewDataModel && (mLastX > dragViewLocationModel.getOriginRight() - Utils.dip2px(60)) && (mLastY > (dragViewLocationModel.getOriginBottom()) + Utils.dip2px(35))) {
                    AddRepertoryDialog addRepertoryDialog = new AddRepertoryDialog();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(IntentConfig.DRAGRESOURCEVIEWMODEL, dragViewDataModel);
                    addRepertoryDialog.setArguments(bundle);
                    addRepertoryDialog.show(getFragmentManager(), "addRepertoryDialog");
                    addRepertoryDialog.setIOnClickListener(new BaseDialogFragment.IOnClickListener() {
                        @Override
                        public void onDismissClick() {
                        }

                        @Override
                        public void onConfirmClick() {
                            try {
                                if (dragViewDataModel.getLevel() == 45) {
                                    recoverPlace();
                                    ToastUtils.showShortCenter(R.string.fortune_kitty_can_not_join_in_pack);
                                    return;
                                }
                                C2SJoinBackpack.C2S_JoinBackpack.Builder c2S_joinBackpack = C2SJoinBackpack.C2S_JoinBackpack.newBuilder().setPosition(getPosition());
                                MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SJoinBackpack(c2S_joinBackpack).setMessageId(MessageOuterClass.Message.C2SJOINBACKPACK_FIELD_NUMBER);
                                SocketManager.send(builder);
                                DragView.this.setDragViewDataModel(null);
                                DragView.this.setResourceValue();
                            } catch (Exception e) {
                            }
                        }
                    });
                    recoverPlace();
                    return true;
                }

                if ((mLastY < dragViewLocationModel.getOriginTop() + mStatusBarHeight && scaleY >= 1.7)) {
                    if (null != homeFragment) {
                        if (null != DragView.this.getDragViewDataModel()) {
                            int level = DragView.this.getDragViewDataModel().getLevel();
                            if (level != 45) {
                                homeFragment.onChange(level);
                            } else if (DragView.this.getDragViewDataModel().getExpireTime() == -1) {
                                homeFragment.onChange(level);
                            } else {
                                homeFragment.onDividing(DragView.this.getDragViewDataModel());
                            }
                        }
                    }
                    recoverPlace();
                    return true;
                }

                if ((scaleX == 0 && scaleY == 0)
                        || (mLastY < dragViewLocationModel.getOriginTop() + mStatusBarHeight && scaleX < 1)
                        || (mLastY < dragViewLocationModel.getOriginTop() + mStatusBarHeight && scaleY >= 1)
                        || ((dragViewLocationModel.getOriginTop()) + (2 * Utils.dip2px(11) + 3 * Utils.dip2px(Utils.getDimens(R.dimen.drag_size))) + mStatusBarHeight < mLastY && scaleX < 1)
                        || ((dragViewLocationModel.getOriginTop()) + (2 * Utils.dip2px(11) + 3 * Utils.dip2px(Utils.getDimens(R.dimen.drag_size))) + mStatusBarHeight < mLastY && scaleY >= 1)) {
                    recoverPlace();
                } else {
                    int beforeRemoveRaw = dragViewLocationModel.getRaw();
                    int beforeRemoveCul = dragViewLocationModel.getCul();
                    int raw = beforeRemoveRaw + scaleNabsY;
                    int cul = beforeRemoveCul + scaleNabsX;
                    // 横向移动超出范围后处理
                    if (cul < 0) {
                        cul = 0;
                    }
                    if (cul > 3) {
                        cul = 3;
                    }
                    // 空组件不移动；
                    if (null == this.getDragViewDataModel()) {
                        recoverPlace();
                        return true;
                    }

                    try {
                        for (Map.Entry<String, DragView> entry : dragViewMap.entrySet()) {
                            DragView dragView = entry.getValue();
                            // 遍历所有组件 获取位置 和组件数据对象
                            DragResourceViewModel dragViewDataModel = dragView.getDragViewDataModel();
                            DragViewLocationModel dragViewLocationModel = dragView.getDragViewLocationModel();
                            // 判断当前元素是要移动到的元素
                            if (dragViewLocationModel.getCul() == cul && dragViewLocationModel.getRaw() == raw) {
                                if (null != dragViewDataModel) {
                                    // 移动的是同一个元素
                                    if (dragViewLocationModel.getCul() == beforeRemoveCul && dragViewLocationModel.getRaw() == beforeRemoveRaw) {
                                        recoverPlace();
                                        return true;
                                    }

                                    if (dragViewDataModel.getLevel() == 45 && this.getDragViewDataModel().getLevel() == 45) {
                                        recoverPlace();
                                        return true;
                                    }

                                    if ((dragViewDataModel.getLevel() == 38 && this.getDragViewDataModel().getLevel() == 39)
                                            || (dragViewDataModel.getLevel() == 39 && this.getDragViewDataModel().getLevel() == 38)) {
                                        CuponKittyDialog cuponKittyDialog = new CuponKittyDialog();
                                        cuponKittyDialog.show(getFragmentManager(), "cuponKittyDialog");
                                        recoverPlace();
                                        return true;
                                    }

                                    if (dragViewDataModel.getLevel() > 37 && this.getDragViewDataModel().getLevel() > 37 && null != kittyList && kittyList.size() > 0 &&
                                            this.getDragViewDataModel().getLevel() != 45 && dragViewDataModel.getLevel() != 45) {
                                        Bundle bundle = new Bundle();
                                        bundle.putIntegerArrayList(IntentConfig.KITTY_LEVEL_LIST, kittyList);
                                        Intent intent = new Intent(getActivity(), MergeDialogActivity.class);
                                        intent.putExtras(bundle);
                                        getActivity().startActivity(intent);
                                        recoverPlace();
                                        return true;
                                    }

                                    if (dragViewDataModel.getLevel() == this.getDragViewDataModel().getLevel() && this.getDragViewDataModel().getLevel() != 45) {
                                        if (dragViewDataModel.getLevel() == 37) {
                                            Bundle bundle = new Bundle();
                                            bundle.putInt(IntentConfig.MERGE_FROM, this.position);
                                            bundle.putInt(IntentConfig.MERGE_TO, dragView.position);
                                            SpecialLevelBonusDialog specialLevelBonusDialog = SpecialLevelBonusDialog.newInstance(bundle);
                                            specialLevelBonusDialog.show(getFragmentManager(),"SpecialLevelBonusDialog");
                                            recoverPlace();

                                            return true;
                                        }
                                        C2SMergeKitty.C2S_MergeKitty.Builder c2S_mergeKitty = C2SMergeKitty.C2S_MergeKitty.newBuilder().setMoveFrom(this.position).setMoveTo(dragView.position);
                                        MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SMergeKitty(c2S_mergeKitty).setMessageId(MessageOuterClass.Message.C2SMERGEKITTY_FIELD_NUMBER);
                                        SocketManager.send(builder);
                                        // ToastUtils.showLong("合并");
                                        move(dragViewLocationModel.getLocation()[0][0], dragViewLocationModel.getLocation()[0][1]);
//                                            LottieAnimationView lottieAnimationView = dragView.getLottioAnimationView();
//                                            if (null != lottieAnimationView) {
//                                                lottieAnimationView.setImageAssetsFolder("merge");
//                                                lottieAnimationView.setAnimation("merge.json");
//                                                lottieAnimationView.playAnimation();
//                                            }
                                        try {
                                            if (null != homeFragment.mediaPlayer && !homeFragment.mediaPlayer.isPlaying()) {
                                                homeFragment.mediaPlayer.reset();
                                                homeFragment.mediaPlayer.setDataSource(homeFragment.fd_merge.getFileDescriptor(), homeFragment.fd_merge.getStartOffset(), homeFragment.fd_merge.getLength());
                                                homeFragment.mediaPlayer.prepare();
                                                homeFragment.mediaPlayer.start();
                                                homeFragment.mediaPlayer.setOnErrorListener((mp, what, extra) -> false);
                                            }
                                        } catch (Exception e) {
                                        }
                                        dragView.getDragViewDataModel().setLevel(dragViewDataModel.getLevel() + 1);
                                        dragView.setResourceValue();
                                        this.setDragViewDataModel(null);
                                        this.setResourceValue();
                                        move(this.getDragViewLocationModel().getLocation()[0][0], this.getDragViewLocationModel().getLocation()[0][1]);

                                    } else {
                                        C2SMergeKitty.C2S_MergeKitty.Builder c2S_mergeKitty = C2SMergeKitty.C2S_MergeKitty.newBuilder().setMoveFrom(this.position).setMoveTo(dragView.position);
                                        MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SMergeKitty(c2S_mergeKitty).setMessageId(MessageOuterClass.Message.C2SMERGEKITTY_FIELD_NUMBER);
                                        SocketManager.send(builder);
//                                      ToastUtils.showLong("交换");
                                        // 备份当前dragview的数据
                                        dragViewDataModelBackUp = this.dragViewDataModel;
                                        move(dragViewLocationModel.getLocation()[0][0], dragViewLocationModel.getLocation()[0][1]);
                                        DragView.this.setDragViewDataModel(dragView.getDragViewDataModel());
                                        DragView.this.setResourceValue();
                                        dragView.setDragViewDataModel(dragViewDataModelBackUp);
                                        dragView.setResourceValue();
                                        move(this.getDragViewLocationModel().getLocation()[0][0], this.getDragViewLocationModel().getLocation()[0][1]);
                                        // 更新当前备份数据
                                        dragViewDataModelBackUp = this.dragViewDataModel;
                                    }
                                } else {
//                                  ToastUtils.showLong("移动");
                                    C2SMergeKitty.C2S_MergeKitty.Builder c2S_mergeKitty = C2SMergeKitty.C2S_MergeKitty.newBuilder().setMoveFrom(this.position).setMoveTo(dragView.position);
                                    MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SMergeKitty(c2S_mergeKitty).setMessageId(MessageOuterClass.Message.C2SMERGEKITTY_FIELD_NUMBER);
                                    SocketManager.send(builder);
                                    move(dragViewLocationModel.getLocation()[0][0], dragViewLocationModel.getLocation()[0][1]);
                                    dragView.setDragViewDataModel(this.dragViewDataModel);
                                    dragView.setResourceValue();
                                    this.setDragViewDataModel(null);
                                    this.setResourceValue();
                                    move(this.getDragViewLocationModel().getLocation()[0][0], this.getDragViewLocationModel().getLocation()[0][1]);
                                }
                            } else {
                                recoverPlace();
                            }
                        }
                    } catch (Exception e) {
                        Log.i("DragException", e.getMessage());
                    }
                }
                break;
        }
        return mTouchResult;
    }


    /**
     *
     */
    @SneakyThrows
    private void move(int left, int top) {
        try {
            getDragView().setLayoutParams(createLayoutParams(left, mStatusBarHeight + top, 0, 0));
            mStartX = mBuilder.defaultLeft;
            mLastY = mStatusBarHeight + mBuilder.defaultTop;
        } catch (Exception e) {
            throw new Throwable("need restart app!");
        }
    }

    /**
     * 回到原位置
     */
    @SneakyThrows
    private void recoverPlace() {
        try {
            getDragView().setLayoutParams(createLayoutParams(mBuilder.defaultLeft, mStatusBarHeight + mBuilder.defaultTop, 0, 0));
            mStartX = mBuilder.defaultLeft;
            mLastY = mStatusBarHeight + mBuilder.defaultTop;
        } catch (Exception e) {
            throw new Throwable("need restart app!");
        }
    }

    private FrameLayout.LayoutParams createLayoutParams(int left, int top, int right, int bottom) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mBuilder.size, mBuilder.size);
        layoutParams.setMargins(left, top, right, bottom);
        return layoutParams;
    }

    public void setOnAnimationChange(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public static class Builder {
        private Activity activity;
        private int size = FrameLayout.LayoutParams.WRAP_CONTENT;
        private int defaultTop = 0;
        private int defaultLeft = 0;
        private View view;
        private LottieAnimationView lottieAnimationView;
        private CountDownTimerUtil timeTickUtil;
        private TextView timer;
        private FragmentManager manager;

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setFragmentManager(FragmentManager manager) {
            this.manager = manager;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setDefaultTop(int top) {
            this.defaultTop = top;
            return this;
        }

        public Builder setDefaultLeft(int left) {
            this.defaultLeft = left;
            return this;
        }

        public Builder setView(View view) {
            try {
                this.view = view;
                this.lottieAnimationView = view.findViewById(R.id.animation_view_merge);
                this.timer = view.findViewById(R.id.tv_timer);
                this.timeTickUtil = new CountDownTimerUtil(0, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        String time = getTimeFromInt(millisUntilFinished);
                        timer.setText(time);
                    }

                    @Override
                    public void onFinish() {
                        timer.setVisibility(View.INVISIBLE);
                        if (null != homeFragment) {
                            homeFragment.onRefresh();
                        }
                    }
                };
            } catch (Exception e) {
            }
            return this;
        }

        public DragView build(FrameLayout rootView) {
            return createDragView(this, rootView);
        }
    }

    /**
     * 小数位第一位逢三进一
     *
     * @param f1
     * @return
     */
    public int roundThree(float f1) {
        int num = (int) (f1 * 10);
        if (num % 10 > 2) {
            f1 = (num - num % 10 + 10 * 1.0f) / 10.0f;
        } else if (num % 10 < -2) {
            f1 = -(num % 10 - num + 10 * 1.0f) / 10.0f;
        } else {
            f1 = num * 1.0f / 10.0f;
        }
        return (int) f1;
    }

    public interface IChangeAnimation {
        void onChange(int level);

        void onDividing(DragResourceViewModel level);

        void onRefresh();
    }
}

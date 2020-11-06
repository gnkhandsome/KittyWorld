package com.kitty.kitty_base.views.luck_panel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.model.LevelRewardModel;
import com.kitty.kitty_base.model.PanelItemModel;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.ToastUtils;

import java.util.List;

/**
 * Created by jeanboy on 2017/4/20.
 */

public class LuckyMonkeyPanelView extends FrameLayout {


    private PanelItemView itemView1, itemView2, itemView3,
            itemView4, itemView5,
            itemView8, itemView9, itemView12, itemView13, itemView14, itemView15, itemView16;

    private ItemView[] itemViewArr = new ItemView[12];
    private int currentIndex = 0;
    private int currentTotal = 0;
    private int stayIndex = 0;

    private boolean isMarqueeRunning = false;
    private boolean isGameRunning = false;
    private boolean isTryToStop = false;

    private static final int DEFAULT_SPEED = 150;
    private static final int MIN_SPEED = 50;
    private int currentSpeed = DEFAULT_SPEED;
    private IAnimationListener iAnimationListener;


    public LuckyMonkeyPanelView(@NonNull Context context) {
        this(context, null);
    }

    public LuckyMonkeyPanelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyMonkeyPanelView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_lucky_mokey_panel, this);
        setupView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startMarquee();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopMarquee();
        super.onDetachedFromWindow();
    }

    private void setupView() {
        try {
            List<LevelRewardModel> levelRewardModels = FormDataUtil.getLevelReward();
            itemView1 = (PanelItemView) findViewById(R.id.item1);
            itemView2 = (PanelItemView) findViewById(R.id.item2);
            itemView3 = (PanelItemView) findViewById(R.id.item3);
            itemView4 = (PanelItemView) findViewById(R.id.item4);
            itemView5 = (PanelItemView) findViewById(R.id.item5);
            itemView8 = (PanelItemView) findViewById(R.id.item8);
            itemView9 = (PanelItemView) findViewById(R.id.item9);
            itemView12 = (PanelItemView) findViewById(R.id.item12);
            itemView13 = (PanelItemView) findViewById(R.id.item13);
            itemView14 = (PanelItemView) findViewById(R.id.item14);
            itemView15 = (PanelItemView) findViewById(R.id.item15);
            itemView16 = (PanelItemView) findViewById(R.id.item16);
            itemViewArr[0] = itemView5.setData(levelRewardModels.get(11));
            itemViewArr[1] = itemView1.setData(levelRewardModels.get(0));
            itemViewArr[2] = itemView2.setData(levelRewardModels.get(1));
            itemViewArr[3] = itemView3.setData(levelRewardModels.get(2));
            itemViewArr[4] = itemView4.setData(levelRewardModels.get(3));
            itemViewArr[5] = itemView8.setData(levelRewardModels.get(4));
            itemViewArr[6] = itemView12.setData(levelRewardModels.get(5));
            itemViewArr[7] = itemView16.setData(levelRewardModels.get(6));
            itemViewArr[8] = itemView15.setData(levelRewardModels.get(7));
            itemViewArr[9] = itemView14.setData(levelRewardModels.get(8));
            itemViewArr[10] = itemView13.setData(levelRewardModels.get(9));
            itemViewArr[11] = itemView9.setData(levelRewardModels.get(10));
        } catch (Exception e) {
        }
    }

    private void stopMarquee() {
        isMarqueeRunning = false;
        isGameRunning = false;
        isTryToStop = false;
    }

    private void startMarquee() {
        isMarqueeRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isMarqueeRunning) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private long getInterruptTime() {
        currentTotal++;
        if (isTryToStop) {
            currentSpeed += 10;
            if (currentSpeed > DEFAULT_SPEED) {
                currentSpeed = DEFAULT_SPEED;
            }
        } else {
            if (currentTotal / itemViewArr.length > 0) {
                currentSpeed -= 10;
            }
            if (currentSpeed < MIN_SPEED) {
                currentSpeed = MIN_SPEED;
            }
        }
        return currentSpeed;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void startGame() {
        isGameRunning = true;
        isTryToStop = false;
        currentSpeed = DEFAULT_SPEED;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isGameRunning) {
                    try {
                        Thread.sleep(getInterruptTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    post(new Runnable() {
                        @Override
                        public void run() {
                            int preIndex = currentIndex;
                            currentIndex++;
                            if (currentIndex >= itemViewArr.length) {
                                currentIndex = 0;
                            }

                            itemViewArr[preIndex].setFocus(false);
                            itemViewArr[currentIndex].setFocus(true);

                            if (isTryToStop && currentSpeed == DEFAULT_SPEED && stayIndex == currentIndex) {
                                isGameRunning = false;
                                if (null != iAnimationListener) {
                                    iAnimationListener.onAnimationStop();
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public void tryToStop(int position) {
        stayIndex = position;
        isTryToStop = true;
    }

    public interface IAnimationListener {
        void onAnimationStop();
    }

    public void addAnimationListener(IAnimationListener iAnimationListener) {
        this.iAnimationListener = iAnimationListener;
    }
}

package com.kitty.kitty_base.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseDialogActivity;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.utils.ResourceUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.DragView;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MergeDialogActivity extends BaseDialogActivity {
    @BindView(R2.id.iv_merge_back)
    ImageView ivMergeBack;
    @BindView(R2.id.iv_merge)
    Button iv_merge;
    @BindView(R2.id.ll_rootview)
    FrameLayout rootView;
    private boolean isMeasured;
    private int mStatusBarHeight;
    private Map<Integer, int[]> resourceMap;
    private ArrayList<Integer> integers;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_merge;
    }

    @Override
    protected void initData() {
        try {
            Intent intent = getIntent();
            if (null != intent) {
                Bundle bundle = intent.getExtras();
                if (null != bundle) {
                    integers = bundle.getIntegerArrayList(IntentConfig.KITTY_LEVEL_LIST);
                    if (null != integers && integers.size() > 0) {
                        initResourceMap();
                        ViewTreeObserver observer = ivMergeBack.getViewTreeObserver();
                        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                            public boolean onPreDraw() {
                                if (!isMeasured) {
                                    int[] location = new int[2];
                                    ivMergeBack.getLocationOnScreen(location);
                                    int x = location[0];
                                    int y = location[1];
                                    if (x == 0 || y == 0) {
                                        Rect rect = new Rect();
                                        ivMergeBack.getGlobalVisibleRect(rect);
                                        x = rect.left;
                                        y = rect.top;
                                    }
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inJustDecodeBounds = true;
                                    BitmapFactory.decodeResource(getResources(), R.drawable.ic_kitty_level40_black, options);
                                    int ivWidth = ivMergeBack.getWidth();

                                    int xA = ivWidth / 2 - options.outWidth / 2;
                                    int yA = 0;
                                    int rFive = (int) (((ivWidth - (2 * x) - Utils.dip2px(55)) * 1.176 / 2) * Math.sin(Math.toRadians(58))) * 2;
                                    int xB = 0;
                                    int xC = 0;
                                    int xD = 0;
                                    int xE = 0;
                                    int yB = 0;
                                    int yC = 0;
                                    int yD = 0;
                                    int yE = 0;
                                    xD = (int) (xA - rFive * Math.sin(Math.toRadians(18)));
                                    xC = (int) (xA + rFive * Math.sin(Math.toRadians(18)));
                                    yD = yC = (int) (yA + Math.cos(Math.toRadians(18)) * rFive);
                                    yB = yE = (int) (yA + Math.sqrt(Math.pow((xC - xD), 2) - Math.pow((rFive / 2), 2)));
                                    xB = xA + (rFive / 2);
                                    xE = xA - (rFive / 2);
                                    Map<String, Integer> integerMap = new HashMap<>();
                                    integerMap.put(xA + "|" + yA, integers.contains(40) ? resourceMap.get(40)[1] : resourceMap.get(40)[0]);
                                    integerMap.put(xB + "|" + yB, integers.contains(41) ? resourceMap.get(41)[1] : resourceMap.get(41)[0]);
                                    integerMap.put((xC) + Utils.dip2px(20) + "|" + (yC), integers.contains(42) ? resourceMap.get(42)[1] : resourceMap.get(42)[0]);
                                    integerMap.put(xD + "|" + (yD), integers.contains(43) ? resourceMap.get(43)[1] : resourceMap.get(43)[0]);
                                    integerMap.put((xE) + "|" + yE, integers.contains(44) ? resourceMap.get(44)[1] : resourceMap.get(44)[0]);
                                    iv_merge.setBackgroundResource(integers.contains(40) && integers.contains(41)
                                            && integers.contains(42)
                                            && integers.contains(43)
                                            && integers.contains(44) ? R.drawable.can_merge_icon : R.drawable.merge_icon);
                                    initKitty(integerMap);
                                    isMeasured = true;
                                }
                                return true;
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    private void initResourceMap() {
        try {
            int[] ic_default_resource = {R.drawable.ic_kitty_level40_black, R.drawable.ic_kitty_level41_black,
                    R.drawable.ic_kitty_level42_black,
                    R.drawable.ic_kitty_level43_black,
                    R.drawable.ic_kitty_level44_black};
            int[] ic_resource = {R.drawable.ic_kitty_level40, R.drawable.ic_kitty_level41,
                    R.drawable.ic_kitty_level42,
                    R.drawable.ic_kitty_level43,
                    R.drawable.ic_kitty_level44};
            resourceMap = new HashMap<>();

            for (int i = 0; i < ic_default_resource.length; i++) {
                int[] resource = new int[2];
                resource[0] = ic_default_resource[i];
                resource[1] = ic_resource[i];
                resourceMap.put(40 + i, resource);
            }
        } catch (Exception e) {

        }
    }

    private void initKitty(Map<String, Integer> floats) {
        //状态栏高度
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        mStatusBarHeight = frame.top;
        if (mStatusBarHeight <= 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                mStatusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        // 此时返回的bitmap为null
        for (Map.Entry<String, Integer> entry : floats.entrySet()) {
            String result = entry.getKey();
            String[] postion = result.split("\\|");
            int x = Integer.valueOf(postion[0]);
            int y = Integer.valueOf(postion[1]);
            ImageView imageView = new ImageView(MergeDialogActivity.this);
            imageView.setImageResource(entry.getValue());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(x, y, 0, 0);
            rootView.addView(imageView, layoutParams);
        }
    }


    @OnClick(R2.id.iv_close)
    public void close() {
        finish();
    }
}

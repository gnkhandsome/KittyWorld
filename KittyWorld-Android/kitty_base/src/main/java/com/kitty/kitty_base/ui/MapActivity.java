package com.kitty.kitty_base.ui;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.Guideline;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.adapter.MapGiftAdapter;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.MapGiftModel;
import com.kitty.kitty_base.model.MapModel;
import com.kitty.kitty_base.model.PointModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.ResourceUtil;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.compress.BitmapMergeUtil;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.util.LogUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.utils.ScreenUtils;

public class MapActivity extends BaseActivity {

    @BindView(R2.id.ll_map_back)
    FrameLayout rootView;
    @BindView(R2.id.iv_map)
    ImageView imageView;
    @BindView(R2.id.gv_map_gift)
    GridView gridView;
    @BindView(R2.id.tv_progress)
    TextView tvProgress;
    @BindView(R2.id.my_kitty_progress)
    ProgressBar myKittyProgress;
    @BindView(R2.id.kilometers_progress)
    ProgressBar kilometersProgress;
    @BindView(R2.id.tv_current_location_name)
    TextView tvCurrentLocationName;
    @BindView(R2.id.tv_dest_location_name)
    TextView tvDestLocationName;
    @BindView(R2.id.tv_current_kilometers)
    TextView tv_current_kilometers;
    @BindView(R2.id.tv_dest_kilometers)
    TextView tv_dest_kilometers;
    @BindView(R2.id.hs_scroll)
    HorizontalScrollView hs_scroll;
    @BindView(R2.id.guideline)
    Guideline guideline;
    @BindView(R2.id.tv_show_progress)
    TextView tvShowProgress;
    private int backWidth;
    private int backHeight;
    private Bitmap bitmap;
    private boolean hasFront = false;
    private List<ImageView> imageViews = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();
    private int[] ints;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_map;
    }


    @Override
    protected void initData() {
        try {
            ints = ScreenUtils.getRawScreenSize(getApplicationContext());
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
            backWidth = (ints[1] * 3358) / 1863;
            backHeight = ints[1];
            layoutParams.height = backHeight;
            layoutParams.width = backWidth;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map_back, options);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageBitmap(bitmap);
            List<PointModel> pointModels = CatHelperUtil.getLocations();

            for (int i = 0; i < pointModels.size(); i++) {
                PointModel pointModel = pointModels.get(i);
                int finalx = (pointModel.X * backWidth) / 3358;
                int finaly = (pointModel.Y * backHeight) / 1863;
                TextView textView = new TextView(MapActivity.this);
                textView.setTextColor(Utils.getColor(R.color.white));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView.setText(pointModel.getAddress_string());
                ImageView imageView = new ImageView(MapActivity.this);
                imageView.setImageResource(R.drawable.location_normal_point);
                FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams1.width = Utils.dip2px(16);
                layoutParams1.height = Utils.dip2px(16);
                layoutParams1.setMargins(finalx, finaly, 0, 0);
                FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams2.setMargins(finalx - getDpByTextLength(pointModel.getAddress_string()), finaly - Utils.dip2px(15), 0, 0);
                rootView.addView(imageView, layoutParams1);
                rootView.addView(textView, layoutParams2);
                imageViews.add(imageView);
                textViews.add(textView);
                if (TextUtils.equals("中国", pointModel.getAddress_string())) {
                    hs_scroll.scrollTo(finalx - (ints[0] / 2), finaly);
                }
            }
            refresh_map(backWidth, backHeight);
        } catch (Exception e) {
        }
    }

    private void refresh_map(int backWidth, int backHeight) {
        HttpUtils.getMapInfo(new IResponse<MapModel>() {

            private PointModel beforePointModel;

            @Override
            public void onSuccess(BaseResponse<MapModel> baseResponse) {
                try {
                    if (baseResponse.code == 0 && null != baseResponse.data) {
                        MapModel mapModel = baseResponse.data;
                        float percent = mapModel.getPercent() * 100;
                        myKittyProgress.setProgress(percent > 0 && percent < 1 ? 1 : (int) percent);

                        tvProgress.setText(BigDecimal.valueOf(percent).setScale(2, RoundingMode.HALF_EVEN) + "%");
                        List<PointModel> pointModels = CatHelperUtil.getLocations();
                        for (int i = 0; i < pointModels.size(); i++) {
                            PointModel pointModel = pointModels.get(i);
                            if (i != 0) {
                                beforePointModel = pointModels.get(i - 1);
                            }
                            if (mapModel.getKilometers() < pointModel.total_kilometers) {
                                tv_dest_kilometers.setText(pointModel.getKilometers() + "KM");
                                tvCurrentLocationName.setText(pointModel.getAddress_string());
                                tvDestLocationName.setText(i == pointModels.size() - 1 ? pointModels.get(0).getAddress_string() : pointModels.get(i + 1).getAddress_string());
                                float ratio = (mapModel.getKilometers() - beforePointModel.getTotal_kilometers()) / pointModels.get(i).getTotal_kilometers();
                                float ration =  ((ratio * 100) >= 100 ? 100 : ((ratio * 100) < 1 && (ratio * 100) > 0) ? 1 : ratio * 100);
                                BigDecimal progress = BigDecimal.valueOf(ration).setScale(2, RoundingMode.HALF_EVEN);
                                guideline.setGuidelinePercent(progress.floatValue()/100);
                                tvShowProgress.setText(progress.toString() + "%");
                                kilometersProgress.setProgress((int) ration);
                                List<Integer> integers = mapModel.getTakeInfo();
                                List<MapGiftModel> mapGiftModels = new ArrayList<>();
                                for (int j = 0; j < i; j++) {
                                    if (hasFront) {
                                        break;
                                    }
                                    PointModel pointModel1 = pointModels.get(j);
                                    Integer gift = integers.get(j);
                                    for (int j1 = 0; j1 < pointModel1.gift_amount; j1++) {
                                        int b = gift.byteValue() >> j1 & 1;
                                        if (b == 0) {
                                            MapGiftModel mapGiftModel = new MapGiftModel();
                                            mapGiftModel.received = b;
                                            mapGiftModel.giftId = pointModel1.id;
                                            mapGiftModel.percent = pointModel1.gift_percent;
                                            mapGiftModel.position = j1;
                                            mapGiftModel.isLocked = false;
                                            mapGiftModels.add(mapGiftModel);
                                            hasFront = true;
                                            break;
                                        }
                                    }
                                }
                                Integer gift = integers.get(i);
                                int custome;
                                if (hasFront) {
                                    custome = 1;
                                } else {
                                    custome = 0;
                                }
                                for (int j = custome; j < pointModel.gift_amount; j++) {
                                    int b = gift.byteValue() >> j & 1;
                                    MapGiftModel mapGiftModel = new MapGiftModel();
                                    mapGiftModel.received = b;
                                    mapGiftModel.giftId = pointModel.id;
                                    mapGiftModel.percent = pointModel.gift_percent;
                                    mapGiftModel.position = j;
                                    mapGiftModel.isLocked = j == pointModel.gift_amount - 1 ? ration != 100 : ((j + 1) * 15) > ration;
                                    mapGiftModels.add(mapGiftModel);
                                }
                                gridView.setAdapter(new MapGiftAdapter(mapGiftModels, getApplicationContext()));
                                hasFront = false;

                                for (int j = 0; j < i; j++) {
                                    PointModel pointModel1 = pointModels.get(j);
                                    int finalx = (pointModel1.X * backWidth) / 3358;
                                    int finaly = (pointModel1.Y * backHeight) / 1863;
                                    ImageView locationIcon = new ImageView(MapActivity.this);
                                    locationIcon.setImageResource(ResourceUtil.getDrawableId(Utils.getContext(), pointModel1.address_icon));
                                    FrameLayout.LayoutParams pekingParams1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                                    pekingParams1.setMargins(finalx - Utils.dip2px(18), finaly + Utils.dip2px(4), 0, 0);
                                    rootView.addView(locationIcon, pekingParams1);
                                    ImageView imageView = imageViews.get(j);
                                    imageView.setVisibility(View.GONE);
                                }

                                int finalx = (pointModel.X * backWidth) / 3358;
                                int finaly = (pointModel.Y * backHeight) / 1863;

                                ImageView locationIcon = new ImageView(MapActivity.this);
                                locationIcon.setImageResource(ResourceUtil.getDrawableId(Utils.getContext(), pointModel.address_icon));
                                FrameLayout.LayoutParams pekingParams1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                                pekingParams1.setMargins(finalx - Utils.dip2px(14), finaly + Utils.dip2px(26), 0, 0);
                                rootView.addView(locationIcon, pekingParams1);
                                TextView textView = textViews.get(i);
                                textView.setGravity(Gravity.CENTER);
                                textView.setPadding(0, 0, 0, Utils.dip2px(2));
                                textView.setBackgroundResource(R.drawable.map_text_back);
                                textView.setTextColor(Utils.getColor(R.color.color_5d75ff));
                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
                                FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) textView.getLayoutParams();
                                layoutParams6.setMargins(finalx - Utils.dip2px(11), finaly - Utils.dip2px(30), 0, 0);
                                ImageView imageView = imageViews.get(i);
                                FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                                layoutParams4.width = Utils.dip2px(24);
                                layoutParams4.height = Utils.dip2px(24);
                                imageView.setImageResource(R.drawable.map_start_icon);
                                PointModel destPointModel = (i == pointModels.size() - 1 ? pointModels.get(0) : pointModels.get(i + 1));
                                int destfinalx = (destPointModel.X * backWidth) / 3358;
                                int destfinaly = (destPointModel.Y * backHeight) / 1863;
                                hs_scroll.scrollTo(destfinalx - (ints[0] / 2), destfinaly);
                                ImageView destIcon = new ImageView(MapActivity.this);
                                destIcon.setImageResource(ResourceUtil.getDrawableId(Utils.getContext(), destPointModel.address_icon));
                                FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams3.setMargins(destfinalx - Utils.dip2px(10), destfinaly + Utils.dip2px(26), 0, 0);
                                rootView.addView(destIcon, layoutParams3);
                                ImageView imageView1 = imageViews.get(i == pointModels.size() - 1 ? 0 : i + 1);
                                FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) imageView1.getLayoutParams();
                                layoutParams5.width = Utils.dip2px(24);
                                layoutParams5.height = Utils.dip2px(24);
                                imageView1.setImageResource(R.drawable.map_dest_icon);
                                TextView textView1 = textViews.get(i == pointModels.size() - 1 ? 0 : i + 1);
                                textView1.setGravity(Gravity.CENTER);
                                textView1.setPadding(0, 0, 0, Utils.dip2px(2));
                                textView1.setBackgroundResource(R.drawable.map_text_back);
                                textView1.setTextColor(Utils.getColor(R.color.color_5d75ff));
                                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
                                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView1.getLayoutParams();
                                layoutParams.setMargins(destfinalx - Utils.dip2px(11), destfinaly - Utils.dip2px(30), 0, 0);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    LogUtil.i("refresh_map", e.getMessage());
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }


    private int getDpByTextLength(String region) {
        if (region.length() > 1) {
            return (region.length() - 1) * Utils.dip2px(5);
        } else {
            return 0;
        }
    }


    @Override
    protected void setListener() {
        if (null != gridView) {
            gridView.setOnItemClickListener((parent, view, position, id) -> {
                showDialogEachTime();
                MapGiftAdapter mapGiftAdapter = (MapGiftAdapter) parent.getAdapter();
                MapGiftModel mapGiftModel = mapGiftAdapter.getItem(position);
                HttpUtils.getMapGift(new IResponse() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        if (baseResponse.code == 0) {
                            refresh_map(backWidth, backHeight);
                            dismissDialog();
                            ToastUtils.showLong(R.string.receive_success);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.showLong(R.string.receive_failed);
                        dismissDialog();
                    }
                }, mapGiftModel.giftId, mapGiftModel.position);
            });
        }
    }

    @OnClick(R2.id.iv_back)
    public void iv_back() {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapMergeUtil.recycleBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

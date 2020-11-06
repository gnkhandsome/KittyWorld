package com.kitty.kitty_base.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.api.WxUrlApi;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.http.callback.BaseObserver;
import com.kitty.kitty_base.http.http.HttpMethods;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.UploadModel;
import com.kitty.kitty_base.model.UserProfile;
import com.kitty.kitty_base.router.RouterActivityPath;
import com.kitty.kitty_base.utils.ActivityContainer;
import com.kitty.kitty_base.utils.AppApplicationMgr;
import com.kitty.kitty_base.utils.GlideUtils;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.utils.SettingUtil;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.compress.CompressHelper;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.util.LogUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class SettingActivity extends BaseActivity {


    @BindView(R2.id.kitty_version)
    TextView kittyVersion;
    @BindView(R2.id.iv_sound_status)
    ImageView ivSoundStatus;
    private UserProfile userProfile;
        @BindView(R2.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R2.id.nick_name)
    TextView nickName;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.animation_sex)
    TextView animation_sex;

    /**
     * 相册页面返回码
     */
    private static final int RESULT = 1;
    private boolean isFrist = true;
    private boolean isSoundOpen;
    private String animationType;
    private PopupWindow popupWindow;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.setting_title);
        isSoundOpen = SPUtils.getBoolean(this, SPConfig.SOUND_STATUS, true);
        ivSoundStatus.setImageResource(isSoundOpen ? R.drawable.sound_switch_open_icon : R.drawable.sound_switch_close_icon);
        kittyVersion.setText(AppApplicationMgr.getVersionName(Utils.getContext()));

        animationType = SPUtils.getString(SettingActivity.this, SPConfig.MAN_ANIMATION_TYPE, "nan.json");
        animation_sex.setText(TextUtils.equals(animationType, "nan.json") ? Utils.getString(R.string.male) : Utils.getString(R.string.female));
        initPopupWindow();
        loadMineData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!isFrist) {
            loadMineData();
        }
        isFrist = false;
    }

    private void loadMineData() {
        String token = SPUtils.getString(SettingActivity.this, SPConfig.TOKEN);
        HttpUtils.getAccountInfo(token, new IResponse<UserProfile>() {
            @Override
            public void onSuccess(BaseResponse<UserProfile> baseResponse) {
                try {
                    if (null != baseResponse && null != baseResponse.data) {
                        userProfile = baseResponse.data;
                        GlideUtils.loadCirclePic(SettingActivity.this, baseResponse.data.getAvatar(), ivUserIcon);
                        nickName.setText(baseResponse.data.getNickname());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

    }

    @OnClick(R2.id.iv_back)
    public void back() {
        finish();
    }

    @OnClick(R2.id.ll_privcy)
    public void onViewClicked() {
        try {
            Intent intent = new Intent(SettingActivity.this, PrivcyActivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @OnClick(R2.id.ll_set_nick_name)
    public void setNickName() {
//        try {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(IntentConfig.USERPROFILE, userProfile);
//            Intent intent = new Intent(SettingActivity.this, SetNickNameActivity.class);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        } catch (Exception e) {
//        }
    }


    private void initPopupWindow() {
        try {
            //要在布局中显示的布局
            View contentView = LayoutInflater.from(this).inflate(R.layout.setting_sex_switch, null, false);
            popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            //点击外部消失，这里因为PopupWindow填充了整个窗口，所以这句代码就没用了
            popupWindow.setOutsideTouchable(true);
            //设置可以点击
            popupWindow.setTouchable(true);
            //实例化PopupWindow并设置宽高
            TextView male = contentView.findViewById(R.id.tv_sex_male);
            TextView female = contentView.findViewById(R.id.tv_sex_female);
            TextView tv_cancel = contentView.findViewById(R.id.tv_cancel);
            String name = SPUtils.getString(SettingActivity.this, SPConfig.MAN_ANIMATION_TYPE, "nan.json");
            if (TextUtils.equals("nan.json", name)) {
                male.setTextColor(Utils.getColor(R.color.dark));
                female.setTextColor(Utils.getColor(R.color.color_9B9B9B));
            } else {
                female.setTextColor(Utils.getColor(R.color.dark));
                male.setTextColor(Utils.getColor(R.color.color_9B9B9B));
            }
            male.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    male.setTextColor(Utils.getColor(R.color.dark));
                    female.setTextColor(Utils.getColor(R.color.color_9B9B9B));
                    SPUtils.putString(SettingActivity.this, SPConfig.MAN_ANIMATION_TYPE, "nan.json");
                    animation_sex.setText(Utils.getString(R.string.male));
                    popupWindow.dismiss();
                }
            });
            female.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    female.setTextColor(Utils.getColor(R.color.dark));
                    male.setTextColor(Utils.getColor(R.color.color_9B9B9B));
                    SPUtils.putString(SettingActivity.this, SPConfig.MAN_ANIMATION_TYPE, "nv.json");
                    animation_sex.setText(Utils.getString(R.string.female));
                    popupWindow.dismiss();
                }
            });
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            //进入退出的动画
//        popupWindow.setAnimationStyle(R.style.MyPopWindowAnim);
        } catch (Exception e) {

        }
    }

    private void showPopWindow() {
        fitPopupWindowOverStatusBar(true);
        View rootview = LayoutInflater.from(SettingActivity.this).inflate(R.layout.activity_setting, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }


    public void fitPopupWindowOverStatusBar(boolean needFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                //利用反射重新设置mLayoutInScreen的值，当mLayoutInScreen为true时则PopupWindow覆盖全屏。
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(popupWindow, needFullScreen);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    @OnClick(R2.id.ll_about_us)
    public void aboutUs(View view) {
        try {
            Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
            startActivity(intent);
        } catch (Exception e) {
        }
    }


    @SuppressLint("CheckResult")
    @OnClick(R2.id.ll_upload_icon)
    public void uploadIcon(View view) {
//        RxPermissions rxPermissions = new RxPermissions(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe((Consumer<? super Permission>) permission -> {
//                if (permission.granted) {
//                    SettingUtil.startAppSettings(SettingActivity.this, RESULT);
//                } else if (permission.shouldShowRequestPermissionRationale) {
//                    ToastUtils.showLong(R.string.write_permission_denied);
//                } else {
//                    ToastUtils.showLong(R.string.write_permission_denied);
//                }
//            });
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            uploadIconToServer(imagePath);
            c.close();
        }
    }

    private void uploadIconToServer(String imagePath) {
        try {
            String token = SPUtils.getString(Utils.getContext(), SPConfig.TOKEN);
            File file = new File(imagePath);
            File newFile = CompressHelper.getDefault(SettingActivity.this).compressToFile(file);
            ToastUtils.showLong(String.valueOf(newFile.length()));
            LogUtil.i("uploadIconToServer", String.valueOf(newFile.length()));
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"avatar\"; filename=\"" + newFile.getName() + "\""),
                            RequestBody.create(MediaType.parse("image/png"), newFile)).build();
            Observable<BaseResponse<UploadModel>> observable = WxUrlApi.getApiBaseService().uploadFile(token, requestBody);
            HttpMethods.toSubscribe(observable, new BaseObserver<BaseResponse<UploadModel>>() {
                @Override
                protected void onBaseNext(BaseResponse<UploadModel> data) {
                    if (data.code == 0) {
                        GlideUtils.loadCirclePic(SettingActivity.this, data.data.getAvatar(), ivUserIcon);
                    } else {
                        ToastUtils.showLong(data.message);
                    }
                }

                @Override
                protected void onBaseError(Throwable t) {
                    ToastUtils.showLong("上传失败");
                    super.onBaseError(t);
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong("上传失败");
        }
    }


    @OnClick(R2.id.tv_logout)
    public void logOut(View view) {
        try {
            HttpUtils.logOut(new IResponse() {
                @Override
                public void onSuccess(BaseResponse baseResponse) {
                }

                @Override
                public void onError(Throwable throwable) {
                }
            });
            SPUtils.clear();
            ActivityContainer.finishAllActivity(true);
            ARouter.getInstance().build(RouterActivityPath.ACTIVITY_LOGIN_PATH).navigation();
        } catch (Exception e) {
        }
    }


    @OnClick(R2.id.ll_sound_status)
    public void soundStatus(View view) {
        isSoundOpen = !isSoundOpen;
        ivSoundStatus.setImageResource(isSoundOpen ? R.drawable.sound_switch_open_icon : R.drawable.sound_switch_close_icon);
        SPUtils.putBoolean(this, SPConfig.SOUND_STATUS, isSoundOpen);
    }

    @OnClick(R2.id.ll_sex)
    public void switchSex(View view) {
        showPopWindow();
    }
}

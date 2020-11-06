package com.kitty.kitty_base.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.kitty.kitty_base.base.BaseCenterTipDialog;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.fragmentdialog.StopServerDialog;
import com.kitty.kitty_base.model.StopServerModel;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.TipContentModel;
import com.kitty.kitty_base.system.navigation.NavigationBarObserver;
import com.kitty.kitty_base.system.navigation.OSUtils;
import com.kitty.kitty_base.system.navigation.OnNavigationBarListener;
import com.kitty.kitty_base.utils.ExampleUtil;
import com.kitty.kitty_base.utils.SPUtils;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R;
import com.kitty.kitty_base.adapter.MainViewPagerAdapter;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.router.RouterActivityPath;
import com.kitty.kitty_base.utils.ActivityContainer;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.utils.VersionUtil;
import com.kitty.kitty_base.views.NoAnimationViewPager;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import me.jessyan.autosize.utils.ScreenUtils;


@Route(path = RouterActivityPath.ACTIVITY_MAIN_PATH)
public class MainActivity extends BaseActivity {

    @BindView(R2.id.view_pager)
    NoAnimationViewPager viewPager;
    @BindView(R2.id.tab_layout)
    TabLayout tabLayout;
    private List<String> tabIndicators;
    private ArrayList<Fragment> fragments;
    private int[] tabSelectors = {R.drawable.selector_main_tab_home, R.drawable.selector_main_tab_travel, R.drawable.selector_main_tab_mine};
    private long mExitTime;
    public static boolean isForeground = false;
    private int navigationBar;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        try {
            initIndicators();
            initTab();
            registerMessageReceiver();
            initStopServer();
            registerNavigationBarObserver();
            navigationBar = ScreenUtils.getHeightOfNavigationBar(MainActivity.this);
        } catch (Exception e) {
        }
    }

    private void initStopServer() {
        HttpUtils.getStopServer(new IResponse<StopServerModel>() {
            @Override
            public void onSuccess(BaseResponse<StopServerModel> baseResponse) {
                try {
                    if (null != baseResponse.data) {
                        if (baseResponse.code == 0) {
                            StopServerModel stopServerModel = baseResponse.data;
                            int type = stopServerModel.getType();
                            if (type == 1) {
                                if ((System.currentTimeMillis() / 1000) < stopServerModel.getStop_time() && (System.currentTimeMillis() / 1000) >= stopServerModel.getBegin_time()) {
                                    type = 0;
                                } else if (System.currentTimeMillis() / 1000 >= stopServerModel.getStop_time() && System.currentTimeMillis() / 1000 <= stopServerModel.getStart_time()) {
                                    type = 1;
                                    SPUtils.clear();
                                    ActivityContainer.finishAllActivity(true);
                                    ARouter.getInstance().build(RouterActivityPath.ACTIVITY_LOGIN_PATH).navigation();
                                }
                            }
                            TipContentModel tipContentModel = new TipContentModel();
                            tipContentModel.setTitle(stopServerModel.getTitle());
                            tipContentModel.setContent(stopServerModel.getContent());
                            tipContentModel.setType(type);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(IntentConfig.TIPCONENTMODEL, tipContentModel);
                            StopServerDialog stopServerDialog = new StopServerDialog();
                            stopServerDialog.setArguments(bundle);
                            stopServerDialog.show(getSupportFragmentManager(), "stopServerDialog");
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


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.kitty.kittyworld.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }



    private void initIndicators() {
        tabIndicators = new ArrayList<>();
        tabIndicators.add(Utils.getString(R.string.main_tab_home));
        tabIndicators.add(Utils.getString(R.string.main_tab_travel));
        tabIndicators.add(Utils.getString(R.string.main_tab_mine));
    }

    private void initTab() {
        fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(TravelFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        MainViewPagerAdapter tabPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.size());
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabIndicators.size(); i++) {
            TabLayout.Tab itemTab = tabLayout.getTabAt(i);
            if (itemTab != null) {
                itemTab.setCustomView(R.layout.main_tab_layout);
                TextView itemTv = Objects.requireNonNull(itemTab.getCustomView()).findViewById(R.id.tv_menu_item);
                ImageView imageView = itemTab.getCustomView().findViewById(R.id.image_menu_item);
                imageView.setImageResource(tabSelectors[i]);
                itemTv.setText(tabIndicators.get(i));
            }
        }
        Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(0)).getCustomView()).setSelected(true);
        VersionUtil.updateVersion(MainActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (hasInstallPermission && !TextUtils.isEmpty(VersionUtil.getDestFileDir(MainActivity.this))) {
                    VersionUtil.installApkO(MainActivity.this, VersionUtil.getDestFileDir(MainActivity.this));
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.system_exit), Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            ActivityContainer.finishAllActivity(true);
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }




    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }


    private void setCostomMsg(String msg) {
        ToastUtils.showLong(msg);
    }


    private void registerNavigationBarObserver() {
        NavigationBarObserver.getInstance().register(getApplication());

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            getContentResolver().registerContentObserver(Settings.System.getUriFor
//                    ("navigationbar_is_min"), true, mNavigationBarObserver);
//        } else {
//            getContentResolver().registerContentObserver(Settings.Global.getUriFor
//                    ("navigationbar_is_min"), true, mNavigationBarObserver);
//        }
    }



    private ContentObserver mNavigationBarObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            try {

                int navigationBarIsMin = 0;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                            "navigationbar_is_min", 0);
                } else {
                    navigationBarIsMin = Settings.Global.getInt(getContentResolver(),
                            "navigationbar_is_min", 0);
                }
                if (navigationBarIsMin == 1) {
                    //导航键隐藏了
                    if (navigationBar > 0) {
                        tabLayout.setPadding(0, 0, 0, navigationBar);
                    }
                } else {
                    if (navigationBar > 0) {
                        tabLayout.setPadding(0, 0, 0, 0);
                    }
                }

            } catch (Exception e) {

            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

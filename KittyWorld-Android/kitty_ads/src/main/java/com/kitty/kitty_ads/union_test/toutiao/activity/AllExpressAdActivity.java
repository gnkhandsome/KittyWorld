package com.kitty.kitty_ads.union_test.toutiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.kitty.kitty_ads.R;

/**
 * created by wuzejian on 2019-12-19
 */
public class AllExpressAdActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_express_ad);
        bindButton(R.id.express_native_ad, NativeExpressActivity.class);
        bindButton(R.id.express_native_ad_list, NativeExpressListActivity.class);
//        bindButton(R.id.express_banner_ad, BannerExpressActivity.class);
        bindButton(R.id.express_interstitial_ad, InteractionExpressActivity.class);
//        bindButton(R.id.express_splash_ad, SplashActivity.class);
//        bindButton(R.id.express_rewarded_video_ad, RewardVideoActivity.class);
    }

    private void bindButton(@IdRes int id, final Class clz) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllExpressAdActivity.this, clz);
                //全屏模板视频代码位id
                if (v.getId() == R.id.express_full_screen_video_ad) {
                    intent.putExtra("horizontal_rit", "901121516");
                    intent.putExtra("vertical_rit", "901121073");
                    intent.putExtra("is_express", true);
                }
                //激励模板视频代码位id
                if (v.getId() == R.id.express_rewarded_video_ad) {
                    intent.putExtra("horizontal_rit", "901121543");
                    intent.putExtra("vertical_rit", "901121593");
                    intent.putExtra("is_express", true);
                }
                //开屏模板代码位id
                if (v.getId() == R.id.express_splash_ad) {
                    intent.putExtra("splash_rit", "801121974");
                    intent.putExtra("is_express", true);
                }
                startActivity(intent);
            }
        });
    }
}

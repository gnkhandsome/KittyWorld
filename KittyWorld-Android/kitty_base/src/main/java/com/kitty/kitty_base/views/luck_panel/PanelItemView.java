package com.kitty.kitty_base.views.luck_panel;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.model.CatNameModel;
import com.kitty.kitty_base.model.LevelRewardModel;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;


public class PanelItemView extends FrameLayout implements ItemView {

    private TextView tv_time;
    private ImageView iv_icon;
    private RelativeLayout item_bg;
    private ImageView iv_fortune_kitty_icon;

    public PanelItemView(Context context) {
        this(context, null);
    }

    public PanelItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_panel_item, this);
        iv_fortune_kitty_icon = findViewById(R.id.iv_fortune_kitty_icon);
        item_bg = findViewById(R.id.item_bg);
        tv_time = findViewById(R.id.tv_time);
        iv_icon = findViewById(R.id.iv_icon);
    }

    public PanelItemView setData(LevelRewardModel data) {
        CatNameModel catNameModel = FormDataUtil.getKittyStringMap().get(data.getReward_name_key());
        if (TextUtils.equals("str_level_reward_5", data.getReward_name_key())) {
            tv_time.setVisibility(GONE);
            iv_fortune_kitty_icon.setImageResource(R.drawable.forever_hour_icon);
        } else if (TextUtils.equals("str_level_reward_6", data.getReward_name_key())) {
            tv_time.setVisibility(GONE);
            iv_fortune_kitty_icon.setImageResource(R.drawable.seven_day_icon);
        } else if (TextUtils.equals("str_level_reward_7", data.getReward_name_key())) {
            tv_time.setVisibility(GONE);
            iv_fortune_kitty_icon.setImageResource(R.drawable.three_hour_icon);
        } else if (TextUtils.equals("str_level_reward_8", data.getReward_name_key())) {
            tv_time.setVisibility(GONE);
            iv_fortune_kitty_icon.setImageResource(R.drawable.one_hour_icon);
        } else if (!TextUtils.isEmpty(catNameModel.getName_cn())) {
            tv_time.setVisibility(VISIBLE);
            tv_time.setText(catNameModel.getName_cn());
        } else {
            tv_time.setVisibility(GONE);
        }
        if (null != iv_icon) {
            iv_icon.setImageResource(ImageSourceUtil.getTurnImageByName(data.getReward_icon()));
        }
        return this;
    }

    @Override
    public void setFocus(boolean isFocused) {
        if (item_bg != null) {
            item_bg.setBackgroundResource(isFocused ? R.drawable.level_dialog_item_focuse_back : R.drawable.lucky_turn_item_back);
        }
    }

}

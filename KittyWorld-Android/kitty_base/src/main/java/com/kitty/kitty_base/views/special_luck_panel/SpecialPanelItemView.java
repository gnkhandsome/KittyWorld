package com.kitty.kitty_base.views.special_luck_panel;

import android.content.Context;
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
import com.kitty.kitty_base.model.SpecialLevelRewardModel;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.ResourceUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;


public class SpecialPanelItemView extends FrameLayout implements ItemView {

    private ImageView iv_icon;
    private RelativeLayout item_bg;

    public SpecialPanelItemView(Context context) {
        this(context, null);
    }

    public SpecialPanelItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpecialPanelItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.special_view_panel_item, this);
        item_bg = findViewById(R.id.item_bg);
        iv_icon = findViewById(R.id.iv_icon);
    }

    public SpecialPanelItemView setData(SpecialLevelRewardModel data) {
        if (null != iv_icon) {
            iv_icon.setImageResource(ImageSourceUtil.getTurnImageByName(data.special_kitty_icon));
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

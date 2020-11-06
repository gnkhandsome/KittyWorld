package com.kitty.kitty_base.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.base.lvadapter.BasicListDataAdapter;
import com.kitty.kitty_base.base.lvadapter.ViewHolder;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdventureGainAdapter extends BasicListDataAdapter<RewardObject> {

    public AdventureGainAdapter(List<RewardObject> datas, Context context) {
        super(datas, context);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.gv_adventure_gain_item;
    }

    @Override
    public ViewHolder createViewHolderAndFindViewById(int position, View convertView) {
        return new AdventureGainViewHolder(convertView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showData(int position, ViewHolder viewHolder, RewardObject data) {
        final AdventureGainViewHolder holder = (AdventureGainViewHolder) viewHolder;
        try {
            holder.lvItemIcon.setBackground(Utils.getDrawable(R.drawable.adventure_gain_item_back));
            if (data != null) {
                holder.tvBonusCount.setVisibility(View.VISIBLE);
                if (data.rewardType == 1 && Double.valueOf(data.amount) > 0) {
                    holder.lvItemIcon.setImageResource(ImageSourceUtil.getResorceByLevel(data.rewardID));
                    holder.tvItemCount.setBackground(Utils.getDrawable(R.drawable.adventure_gain_level_back));
                    holder.tvItemCount.setText("x" + data.amount);
                } else if (data.rewardType == 3 && Double.valueOf(data.amount) > 0) {
                    holder.lvItemIcon.setImageResource(R.drawable.adventure_reward_coin_icon);
                    holder.tvBonusCount.setText(TimeDescUtil.getTimeDesc(Integer.valueOf(data.amount)));
                } else if (data.rewardType == 2 && Double.valueOf(data.amount) > 0) {
                    holder.lvItemIcon.setImageResource(R.drawable.adventure_reward_coin_icon);
                    holder.tvBonusCount.setText(CatHelperUtil.getDispalyedNumber(data.amount));
                } else if (data.rewardType == 5 && Double.valueOf(data.amount) > 0) {
                    holder.lvItemIcon.setImageResource(R.drawable.ic_kitty_level45);
                    holder.tvBonusCount.setText(TimeDescUtil.getTimeDesc(Integer.valueOf(data.amount)));
                }
            }
        } catch (Exception e) {
        }
    }

    public static class AdventureGainViewHolder implements ViewHolder {

        @BindView(R2.id.iv_adventure_gain)
        ImageView lvItemIcon;
        @BindView(R2.id.tv_item_count)
        TextView tvItemCount;
        @BindView(R2.id.tv_bonus_count)
        TextView tvBonusCount;

        public AdventureGainViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

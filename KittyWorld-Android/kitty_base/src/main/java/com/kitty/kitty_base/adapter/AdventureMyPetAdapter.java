package com.kitty.kitty_base.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.lvadapter.BasicListDataAdapter;
import com.kitty.kitty_base.base.lvadapter.ViewHolder;
import com.kitty.kitty_base.model.AdventureGainModel;
import com.kitty.kitty_base.model.AdventureMyPetModel;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdventureMyPetAdapter extends BasicListDataAdapter<AdventureMyPetModel> {

    public AdventureMyPetAdapter(List<AdventureMyPetModel> datas, Context context) {
        super(datas, context);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.gv_adventure_mypet_item;
    }

    @Override
    public ViewHolder createViewHolderAndFindViewById(int position, View convertView) {
        return new AdventureGainViewHolder(convertView);
    }

    @Override
    public void showData(int position, ViewHolder viewHolder, AdventureMyPetModel data) {
        final AdventureGainViewHolder holder = (AdventureGainViewHolder) viewHolder;
        holder.ivCatIcon.setBackground(Utils.getDrawable(R.drawable.adventure_gain_item_back));
        holder.ivCatIcon.setImageResource(null == data ? 0 : ImageSourceUtil.getResorceByLevel(data.level));
        holder.tvItemCount.setBackground(null == data ? null : (data.count == 0 ? Utils.getDrawable(R.drawable.adventure_count_zero_back) : Utils.getDrawable(R.drawable.adventure_gain_level_back)));
        holder.tvItemCount.setText(null == data ? "" : "x" + data.count);
    }

    public static class AdventureGainViewHolder implements ViewHolder {

        @BindView(R2.id.iv_cat_icon)
        ImageView ivCatIcon;
        @BindView(R2.id.tv_item_count)
        TextView tvItemCount;

        public AdventureGainViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

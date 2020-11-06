package com.kitty.kitty_base.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.lvadapter.BasicListDataAdapter;
import com.kitty.kitty_base.base.lvadapter.ViewHolder;
import com.kitty.kitty_base.model.AdventureGainModel;
import com.kitty.kitty_base.utils.ResourceUtil;
import com.kitty.kitty_base.utils.UiUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdventureGainDialogAdapter extends BasicListDataAdapter<AdventureGainModel> {

    public AdventureGainDialogAdapter(List<AdventureGainModel> datas, Context context) {
        super(datas, context);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.gv_adventure_gain_dialog_item;
    }

    @Override
    public ViewHolder createViewHolderAndFindViewById(int position, View convertView) {
        return new AdventureGainViewHolder(convertView);
    }

    @Override
    public void showData(int position, ViewHolder viewHolder, AdventureGainModel data) {
        try {
            final AdventureGainViewHolder holder = (AdventureGainViewHolder) viewHolder;
            holder.ivItemIcon.setBackground(Utils.getDrawable(R.drawable.adventure_gain_item_back));
            holder.ivItemIcon.setImageResource(data.imageResource);
            holder.tvAdventureTime.setVisibility(TextUtils.isEmpty(data.desc) ? View.GONE : View.VISIBLE);
            holder.tvAdventureTime.setBackground(Utils.getDrawable(R.drawable.adventure_gain_level_back));
            holder.tvAdventureTime.setText(data.desc);
        } catch (Exception e) {
        }
    }

    public static class AdventureGainViewHolder implements ViewHolder {

        @BindView(R2.id.iv_adventure_gain)
        ImageView ivItemIcon;
        @BindView(R2.id.tv_adventure_time)
        TextView tvAdventureTime;

        public AdventureGainViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

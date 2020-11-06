package com.kitty.kitty_base.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.lvadapter.BasicListDataAdapter;
import com.kitty.kitty_base.base.lvadapter.ViewHolder;
import com.kitty.kitty_base.model.CatNameModel;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.ResourceUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R2;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import kitty_protos.S2CKittyBackpack;

public class RepertoryLvAdaper extends BasicListDataAdapter<S2CKittyBackpack.KittyBackpackItem> {


    /**
     * 选中的位置, -1表示未选中
     */
    private int selectedPosition = -1;

    public RepertoryLvAdaper(List datas, Context context) {
        super(datas, context);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }


    @Override
    public int getItemLayoutId(int position) {
        return R.layout.list_item_repertory;
    }

    @Override
    public ViewHolder createViewHolderAndFindViewById(int position, View convertView) {
        return new RepertoryViewHolder(convertView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showData(int position, ViewHolder viewHolder, S2CKittyBackpack.KittyBackpackItem data) {
        final RepertoryViewHolder holder = (RepertoryViewHolder) viewHolder;
        holder.lvItemIcon.setBackground(selectedPosition == position ? Utils.getDrawable(R.drawable.repertory_icon_border) : Utils.getDrawable(R.drawable.repertory_icon_border_normal));
        holder.llRepertoryItem.setBackgroundColor(selectedPosition == position ? Utils.getColor(R.color.color_ECF9F4) : Utils.getColor(R.color.white));
        holder.lvItemIcon.setImageResource(ImageSourceUtil.getResorceByLevel(data.getLevel()));
        holder.tvItemLevel.setText(String.valueOf(data.getLevel() > 38 ? 38 : data.getLevel()));
        holder.lvItemName.setText(CatHelperUtil.getKittyNameByLevel(data.getLevel()));
        holder.lvItemCount.setText(data.getCount() + Utils.getString(R.string.unit_zhi));
    }


    public static class RepertoryViewHolder implements ViewHolder {
        @BindView(R2.id.lv_item_icon)
        ImageView lvItemIcon;
        @BindView(R2.id.lv_item_name)
        TextView lvItemName;
        @BindView(R2.id.lv_item_count)
        TextView lvItemCount;
        @BindView(R2.id.tv_item_level)
        TextView tvItemLevel;
        @BindView(R2.id.ll_repertory_item)
        LinearLayout llRepertoryItem;

        public RepertoryViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

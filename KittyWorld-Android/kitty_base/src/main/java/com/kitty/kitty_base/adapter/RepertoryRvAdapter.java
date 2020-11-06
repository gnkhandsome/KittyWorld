package com.kitty.kitty_base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.adapter.BaseRvAdapter;
import com.kitty.kitty_base.model.KittyInfo;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.views.dragview.ImageSourceUtil;
import com.kitty.kitty_res.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基类adapter
 * created by Jiang on 2020/5/28 15:32
 */
public class RepertoryRvAdapter extends BaseRvAdapter<KittyInfo> {
    private int selectedPosition;
    public static final String TYPE_COUNT = "type_count";
    public RepertoryRvAdapter(Context context, List<KittyInfo> datas) {
        super(context, datas);
    }

    @Override
    protected int setView(int itemType) {
        return R.layout.list_item_repertory;
    }

    @Override
    protected RecyclerView.ViewHolder setCreateViewHolder(View view, int itemType) {
        return new KittyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.size()==0) {
            super.onBindViewHolder(holder, position, payloads);
        }else if (payloads.size()==1){
            String type = (String) payloads.get(0);
            if (type.equals(TYPE_COUNT)){
                KittyHolder viewHolder = (KittyHolder) holder;
                KittyInfo kittyInfo = getDatas().get(position);
                viewHolder.lvItemCount.setText(kittyInfo.getCount() + Utils.getString(R.string.unit_zhi));
            }
        }
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder viewHolder, int position, KittyInfo data) {
        KittyHolder holder = (KittyHolder) viewHolder;
        holder.lvItemIcon.setBackground(selectedPosition == position ? Utils.getDrawable(R.drawable.repertory_icon_border) : Utils.getDrawable(R.drawable.repertory_icon_border_normal));
        holder.llRepertoryItem.setBackgroundColor(selectedPosition == position ? Utils.getColor(R.color.color_ECF9F4) : Utils.getColor(R.color.white));
        holder.lvItemIcon.setImageResource(ImageSourceUtil.getResorceByLevel(data.getLevel()));
        holder.tvItemLevel.setText(String.valueOf(data.getLevel() > 38 ? 38 : data.getLevel()));
        holder.lvItemName.setText(CatHelperUtil.getKittyNameByLevel(data.getLevel()));
        holder.lvItemCount.setText(data.getCount() + Utils.getString(R.string.unit_zhi));
        viewHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener!=null){
                onItemClickListener.onItemClick(viewHolder.itemView,position);
            }
        });
    }

    class KittyHolder extends RecyclerView.ViewHolder{
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
        public KittyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}

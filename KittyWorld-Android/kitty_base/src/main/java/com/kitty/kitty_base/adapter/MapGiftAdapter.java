package com.kitty.kitty_base.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.lvadapter.BasicListDataAdapter;
import com.kitty.kitty_base.base.lvadapter.ViewHolder;
import com.kitty.kitty_base.model.MapGiftModel;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapGiftAdapter extends BasicListDataAdapter<MapGiftModel> {


    public MapGiftAdapter(List<MapGiftModel> datas, Context context) {
        super(datas, context);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.gv_map_gift_item;
    }

    @Override
    public ViewHolder createViewHolderAndFindViewById(int position, View convertView) {
        return new MapGiftViewHolder(convertView);
    }

    @Override
    public void showData(int position, ViewHolder viewHolder, MapGiftModel data) {
        MapGiftViewHolder mapGiftViewHolder = (MapGiftViewHolder) viewHolder;
        mapGiftViewHolder.llLocked.setVisibility(data.isLocked ? View.VISIBLE : View.INVISIBLE);
        mapGiftViewHolder.llReceived.setVisibility(data.received == 1 && !data.isLocked ? View.VISIBLE : View.INVISIBLE);
        mapGiftViewHolder.rlReceive.setVisibility(data.received == 0 && !data.isLocked ? View.VISIBLE : View.INVISIBLE);
        mapGiftViewHolder.tvGiftReceivedPercent.setText(data.received == 0 ? "" : "+" + new BigDecimal(data.percent * 100).setScale(2, RoundingMode.HALF_EVEN) + "%");
        mapGiftViewHolder.tvGiftReceiveStatus.setText(data.received == 0 ? Utils.getString(R.string.receive) : Utils.getString(R.string.received));
        mapGiftViewHolder.ivMapGiftIcon.setImageResource(R.drawable.map_gift_icon);
    }

    public static class MapGiftViewHolder implements ViewHolder {
        @BindView(R2.id.tv_gift_receive_status)
        TextView tvGiftReceiveStatus;
        @BindView(R2.id.iv_map_gift_icon)
        ImageView ivMapGiftIcon;
        @BindView(R2.id.rl_receive)
        RelativeLayout rlReceive;
        @BindView(R2.id.ll_locked)
        LinearLayout llLocked;
        @BindView(R2.id.tv_gift_received_percent)
        TextView tvGiftReceivedPercent;
        @BindView(R2.id.ll_received)
        LinearLayout llReceived;

        public MapGiftViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.kitty.kitty_base.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.lvadapter.BasicListDataAdapter;
import com.kitty.kitty_base.base.lvadapter.ViewHolder;
import com.kitty.kitty_base.model.WithDrawModel;
import com.kitty.kitty_base.utils.NumberUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WithDrawAdapter extends BasicListDataAdapter<WithDrawModel> {

    int selectedPosition = 0;

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public WithDrawAdapter(List<WithDrawModel> datas, Context context) {
        super(datas, context);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.withdraw_item;
    }

    @Override
    public ViewHolder createViewHolderAndFindViewById(int position, View convertView) {
        return new WithDrawViewHolder(convertView);
    }

    @Override
    public void showData(int position, ViewHolder viewHolder, WithDrawModel data) {
        try {
            WithDrawViewHolder withDrawViewHolder = (WithDrawViewHolder) viewHolder;
            withDrawViewHolder.tvWithdrawAmount.setText(String.valueOf(NumberUtil.getWithDrawWanDividedNumber(BigDecimal.valueOf(data.amount))));
            withDrawViewHolder.llWithdrawItem.setBackgroundResource(position == selectedPosition ? R.drawable.with_draw_amount_selected : R.drawable.with_draw_amount_normal);
            withDrawViewHolder.tvFirstWithdraw.setVisibility(datas.size() == 6 && position == 0 ? View.VISIBLE : View.INVISIBLE);
            withDrawViewHolder.tvWithdrawAmount.setTextColor(position == selectedPosition ? Utils.getColor(R.color.white) : Utils.getColor(R.color.gray));
            withDrawViewHolder.tv_cny_unit.setTextColor(position == selectedPosition ? Utils.getColor(R.color.white) : Utils.getColor(R.color.gray));
        } catch (Exception e) {
        }
    }

    public static class WithDrawViewHolder implements ViewHolder {
        @BindView(R2.id.tv_withdraw_amount)
        TextView tvWithdrawAmount;
        @BindView(R2.id.ll_withdraw_item)
        LinearLayout llWithdrawItem;
        @BindView(R2.id.tv_first_withdraw)
        TextView tvFirstWithdraw;
        @BindView(R2.id.tv_cny_unit)
        TextView tv_cny_unit;

        public WithDrawViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

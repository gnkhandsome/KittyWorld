package com.kitty.kitty_base.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.lvadapter.BasicListDataAdapter;
import com.kitty.kitty_base.base.lvadapter.ViewHolder;
import com.kitty.kitty_base.model.MessageModel;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageLvAdapter extends BasicListDataAdapter<MessageModel.MessageModelItem> {


    public MessageLvAdapter(List<MessageModel.MessageModelItem> datas, Context context) {
        super(datas, context);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.message_center_list_item;
    }

    @Override
    public ViewHolder createViewHolderAndFindViewById(int position, View convertView) {
        return new MessageViewHolder(convertView);
    }

    @Override
    public void showData(int position, ViewHolder viewHolder, MessageModel.MessageModelItem data) {
        MessageViewHolder messageViewHolder = (MessageViewHolder) viewHolder;
        try {
            messageViewHolder.messageOpTitle.setText(data.getTitle());
            messageViewHolder.messageOpTitle.setTextColor(data.getStatus() == 0 ? Utils.getColor(R.color.dark) : Utils.getColor(R.color.color_9B9B9B));
            String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(data.getCreate_time() * 1000));
            messageViewHolder.messageTime.setText(result1);
            messageViewHolder.messageTime.setTextColor(data.getStatus() == 0 ? Utils.getColor(R.color.color_9B9B9B) : Utils.getColor(R.color.color_DCDCDC));
            messageViewHolder.readStatus.setVisibility(data.getStatus() == 0 ? View.VISIBLE : View.INVISIBLE);
            messageViewHolder.messageTypeIcon.setImageResource(data.getType() == 1 || data.getType() == 6 ? (data.getStatus() == 0 ? R.drawable.message_reward_icon : R.drawable.reward_gray_icon) : (data.getStatus() == 0 ? R.drawable.message_mail_icon : R.drawable.message_mail_gray_icon));
        } catch (Exception e) {
        }
    }

    public static class MessageViewHolder implements ViewHolder {

        @BindView(R2.id.message_op_title)
        TextView messageOpTitle;
        @BindView(R2.id.tv_message_time)
        TextView messageTime;
        @BindView(R2.id.iv_read_status)
        ImageView readStatus;
        @BindView(R2.id.iv_message_type_icon)
        ImageView messageTypeIcon;

        public MessageViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

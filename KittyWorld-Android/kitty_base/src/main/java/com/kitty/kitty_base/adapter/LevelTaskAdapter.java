package com.kitty.kitty_base.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.base.lvadapter.BasicListDataAdapter;
import com.kitty.kitty_base.base.lvadapter.ViewHolder;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.TaskModel;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_res.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LevelTaskAdapter extends BasicListDataAdapter<TaskModel.UserSignTaskBean.LevelKittyTasksBean> {


    private IReceiveListener iReceiveListener;

    public LevelTaskAdapter(List<TaskModel.UserSignTaskBean.LevelKittyTasksBean> datas, Context context) {
        super(datas, context);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.lv_level_task_item;
    }

    @Override
    public ViewHolder createViewHolderAndFindViewById(int position, View convertView) {
        return new LevelTaskViewHolder(convertView);
    }

    @Override
    public void showData(int position, ViewHolder viewHolder, TaskModel.UserSignTaskBean.LevelKittyTasksBean data) {
        LevelTaskViewHolder levelTaskViewHolder = (LevelTaskViewHolder) viewHolder;
        try {
            levelTaskViewHolder.ivLevelTaskIcon.setImageResource(R.drawable.task_invite_icon);
            levelTaskViewHolder.tvInviteTaskTitle.setText(Html.fromHtml(Utils.getString(R.string.invite) + data.getNeed_invite_num()
                    + Utils.getString(R.string.invite_friend_count) + "(<font color=\"#FF4B5D\">"
                    + (data.getCurr_invite_num() > data.getNeed_invite_num() ? data.getNeed_invite_num() : data.getCurr_invite_num()) + "</font>/" + data.getNeed_invite_num() + ")"));
            levelTaskViewHolder.tvInviteTaskDesc.setText(Utils.getString(R.string.get) + TimeDescUtil.getTimeDesc(data.getReward().getAmount()) + Utils.getString(R.string.kiity_minute));
            int status = data.getStatus();
            if (status == 1) {
                levelTaskViewHolder.tvReceiveInvite.setBackgroundResource(R.drawable.invite_friend_receive_back);
                levelTaskViewHolder.tvReceiveInvite.setText(Utils.getString(R.string.receive));
                levelTaskViewHolder.tvReceiveInvite.setOnClickListener(v -> HttpUtils.receiveLevelTask(new IResponse() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        try {
                            if (baseResponse.code == 0) {
                                levelTaskViewHolder.tvReceiveInvite.setBackgroundResource(R.drawable.unreceived_back);
                                levelTaskViewHolder.tvReceiveInvite.setText(Utils.getString(R.string.received));
                                RewardObject rewardObject = new RewardObject();
                                rewardObject.rewardType = data.getReward().getType();
                                rewardObject.amount = String.valueOf(data.getReward().getAmount());
                                if (null != iReceiveListener) {
                                    iReceiveListener.onReceive(rewardObject);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                    }
                }, Integer.valueOf(data.getId())));
            } else if (status == 0) {
                levelTaskViewHolder.tvReceiveInvite.setBackgroundResource(R.drawable.unreceived_back);
                levelTaskViewHolder.tvReceiveInvite.setText(Utils.getString(R.string.task_unfinsih));
            } else if (status == 2) {
                levelTaskViewHolder.tvReceiveInvite.setBackgroundResource(R.drawable.unreceived_back);
                levelTaskViewHolder.tvReceiveInvite.setText(Utils.getString(R.string.received));
            }
        } catch (Exception e) {
        }
    }

    public void setOnReceiveListener(IReceiveListener onReceiveListener) {
        iReceiveListener = onReceiveListener;
    }

    public static class LevelTaskViewHolder implements ViewHolder {
        @BindView(R2.id.iv_level_task_icon)
        ImageView ivLevelTaskIcon;
        @BindView(R2.id.tv_invite_task_title)
        TextView tvInviteTaskTitle;
        @BindView(R2.id.tv_invite_task_desc)
        TextView tvInviteTaskDesc;
        @BindView(R2.id.tv_receive_invite)
        TextView tvReceiveInvite;

        public LevelTaskViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface IReceiveListener {
        void onReceive(RewardObject rewardObject);
    }
}

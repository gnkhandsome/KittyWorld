package com.kitty.kitty_base.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.adapter.MessageLvAdapter;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.base.BaseCenterTipDialog;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.BaseTipDialog;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.fragmentdialog.MessageReceiveDialog;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.MessageModel;
import com.kitty.kitty_base.model.TipContentModel;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_res.R2;
import com.kitty.websocket.util.LogUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageCenterActivity extends BaseActivity {
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.lv_message)
    ListView lvMessage;
    @BindView(R2.id.ll_empty_view)
    LinearLayout ll_empty_view;
    @BindView(R2.id.refreshLayout)
    RefreshLayout refreshLayout;

    int page = 1;
    int limit = 8;
    private MessageLvAdapter messageLvAdapter;
    private List<MessageModel.MessageModelItem> messageModelItems = new ArrayList<>();

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_message;
    }

    @Override
    protected void initData() {
        try {
            tvTitle.setText(R.string.message_center_title);
            refresh(false);
            messageLvAdapter = new MessageLvAdapter(messageModelItems, MessageCenterActivity.this);
            lvMessage.setAdapter(messageLvAdapter);
            refreshLayout.setRefreshHeader(new ClassicsHeader(this));
            refreshLayout.setRefreshFooter(new ClassicsFooter(this));
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    try {
                        page = 1;
                        messageModelItems.clear();
                        refresh(false);
                        refreshLayout.finishRefresh(1000);
                    } catch (Exception e) {
                    }
                }
            });
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshlayout) {
                    ++page;
                    refresh(false);
                }
            });
        } catch (Exception e) {
        }
    }

    private void refresh(boolean isRead) {
        HttpUtils.getMessages(new IResponse<MessageModel>() {
            @Override
            public void onSuccess(BaseResponse<MessageModel> baseResponse) {
                try {
                    if (null != baseResponse && null != baseResponse.data) {
                        if (null != baseResponse.data.getItems() && baseResponse.data.getItems().size() > 0) {
                            refreshLayout.finishRefresh();
                            lvMessage.setVisibility(View.VISIBLE);
                            ll_empty_view.setVisibility(View.INVISIBLE);
                            if (isRead) {
                                messageModelItems.clear();
                                messageModelItems.addAll(baseResponse.data.getItems());
                            } else {
                                messageModelItems.addAll(baseResponse.data.getItems());
                            }
                            messageLvAdapter.notifyDataSetChanged();
                            if (page == baseResponse.data.getTotal()) {
                                refreshLayout.setNoMoreData(true);
                            }
                        } else if (page == 1) {
                            lvMessage.setVisibility(View.INVISIBLE);
                            ll_empty_view.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    if (null != refreshLayout) {
                        refreshLayout.finishLoadMore(false);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (null != refreshLayout) {
                    refreshLayout.finishLoadMore(false);
                }
            }
        }, page, limit);
        refreshLayout.finishLoadMore(1000);
    }

    @Override
    protected void setListener() {
        if (null != lvMessage) {
            lvMessage.setOnItemClickListener((parent, view, position, id) -> {
                MessageLvAdapter messageLvAdapter = (MessageLvAdapter) parent.getAdapter();
                MessageModel.MessageModelItem messageModelItem = messageLvAdapter.getItem(position);
                if (null != messageModelItem && !TextUtils.isEmpty(messageModelItem.getReward())) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(IntentConfig.MESSAGE_MODEL, messageModelItem);
                    MessageReceiveDialog receiveDialog = new MessageReceiveDialog();
                    receiveDialog.setArguments(bundle);
                    receiveDialog.setIOnClickListener(new BaseDialogFragment.IOnClickListener() {
                        @Override
                        public void onDismissClick() {
                        }

                        @Override
                        public void onConfirmClick() {
                            page = 1;
                            refresh(true);
                        }
                    });
                    receiveDialog.show(getSupportFragmentManager(), "receiveDialog");
                }
                if (null != messageModelItem && TextUtils.isEmpty(messageModelItem.getReward())) {
                    try {
                        TipContentModel tipContentModel = new TipContentModel();
                        tipContentModel.setTitle(messageModelItem.getTitle());
                        tipContentModel.setContent(messageModelItem.getContent());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(IntentConfig.TIPCONENTMODEL, tipContentModel);
                        BaseCenterTipDialog baseTipDialog = new BaseCenterTipDialog();
                        baseTipDialog.setArguments(bundle);
                        baseTipDialog.show(getSupportFragmentManager(), "baseTipDialog");

                        HttpUtils.readMessage(new IResponse<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse<BaseResponse> baseResponse) {
                                if (baseResponse.code == 0) {
                                    page = 1;
                                    refresh(true);
                                }
                            }

                            @Override
                            public void onError(Throwable throwable) {
                            }
                        }, messageModelItem.getId());
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    @OnClick(R2.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}

package com.kitty.kitty_home.ui;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.protobuf.InvalidProtocolBufferException;
import com.kitty.kitty_base.adapter.RepertoryLvAdaper;
import com.kitty.kitty_base.base.BaseDialogActivity;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.fragmentdialog.BuyRepertoryCapacityDialog;
import com.kitty.kitty_base.model.AdventureMyPetModel;
import com.kitty.kitty_base.router.RouterActivityPath;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.ToastUtils;
import com.kitty.kitty_base.utils.Utils;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_home.R;
import com.kitty.kitty_res.R2;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import kitty_protos.C2SCapacityBackpack;
import kitty_protos.C2SKittyBackpack;
import kitty_protos.C2SLeaveBackpack;
import kitty_protos.MessageOuterClass;
import kitty_protos.S2CCapacityBackpack;
import kitty_protos.S2CKittyBackpack;
import kitty_protos.S2CLeaveBackpack;

@Route(path = RouterActivityPath.ACTIVITY_REPERTORY_PATH)
public class RepertoryActivity extends BaseDialogActivity {

    @BindView(R2.id.ll_dismiss)
    LinearLayout llDismiss;
    @BindView(R2.id.lv_stored)
    ListView lvStored;
    @BindView(R2.id.ll_empty_view)
    LinearLayout llEmptyView;
    @BindView(R2.id.ll_list_view)
    LinearLayout llListView;
    @BindView(R2.id.tv_expand)
    TextView tvExpand;
    int position;
    private RepertoryLvAdaper repertoryLvAdaper;
    private S2CKittyBackpack.KittyBackpackItem kittyBackpackItem;
    private List<S2CKittyBackpack.KittyBackpackItem> kittyBackpackItems;
    private S2CCapacityBackpack.S2C_CapacityBackpack s2C_capacityBackpack;
    private S2CKittyBackpack.S2C_KittyBackpack s2C_kittyBackpack;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_repertory;
    }

    @Override
    protected void setListener() {
        llDismiss.setOnClickListener(v -> finish());
        lvStored.setOnItemClickListener((parent, view, position, id) -> {
            if (null != repertoryLvAdaper) {
                RepertoryActivity.this.kittyBackpackItem = repertoryLvAdaper.getItem(position);
                this.position = position;
                repertoryLvAdaper.setSelectedPosition(position);
                repertoryLvAdaper.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        showDialogEachTime();
        C2SKittyBackpack.C2S_KittyBackpack.Builder c2S_buyKitty = C2SKittyBackpack.C2S_KittyBackpack.newBuilder().setCallTimestamp(System.currentTimeMillis() / 1000);
        MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SKittyBackpack(c2S_buyKitty).setMessageId(MessageOuterClass.Message.C2SKITTYBACKPACK_FIELD_NUMBER);
        SocketManager.send(builder);
        tvExpand.setText(Utils.getString(R.string.kitty_home_capacity_text) + "0/" + FormDataUtil.getDefaultCapacity());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        try {
            MessageOuterClass.Message message = MessageOuterClass.Message.parseFrom(bytes);
            if (null != message) {
                if (MessageOuterClass.Message.S2CKITTYBACKPACK_FIELD_NUMBER == message.getMessageId()) {
                    try {
                        s2C_kittyBackpack = message.getS2CKittyBackpack();
                        kittyBackpackItems = s2C_kittyBackpack.getKittyBackpackItemsList();
                        if (kittyBackpackItems.size() <= 0) {
                            llEmptyView.setVisibility(View.VISIBLE);
                            llListView.setVisibility(View.GONE);
                        } else {
                            llEmptyView.setVisibility(View.GONE);
                            llListView.setVisibility(View.VISIBLE);
                        }
                        TreeMap<Integer, S2CKittyBackpack.KittyBackpackItem> treeMap = new TreeMap(new Comparator<Integer>(){
                            public int compare(Integer a,Integer b){
                                return b-a;
                            }
                        });
                        for (S2CKittyBackpack.KittyBackpackItem kittyBackpackItem : kittyBackpackItems) {
                            treeMap.put(kittyBackpackItem.getLevel(), kittyBackpackItem);
                        }
                        List<S2CKittyBackpack.KittyBackpackItem> orderKittyBackpackItems = new ArrayList<>();
                        for (Map.Entry<Integer, S2CKittyBackpack.KittyBackpackItem> entry : treeMap.entrySet()) {
                            orderKittyBackpackItems.add(entry.getValue());
                        }
                        repertoryLvAdaper = new RepertoryLvAdaper(orderKittyBackpackItems, RepertoryActivity.this);
                        repertoryLvAdaper.setSelectedPosition(0);
                        lvStored.setAdapter(repertoryLvAdaper);
                        RepertoryActivity.this.kittyBackpackItem = repertoryLvAdaper.getItem(0);

                        if (null != kittyBackpackItems) {
                            tvExpand.setText(Utils.getString(R.string.kitty_home_capacity_text) + (s2C_kittyBackpack.getCount() < 0 ? 0 : s2C_kittyBackpack.getCount()) + "/" + s2C_kittyBackpack.getCapacity());
                        } else {
                            tvExpand.setText(Utils.getString(R.string.kitty_home_capacity_text) + "0/" + s2C_kittyBackpack.getCapacity());
                        }
                    } catch (Exception e) {
                    }
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CLEAVEBACKPACK_FIELD_NUMBER) {
                    S2CLeaveBackpack.S2C_LeaveBackpack s2CLeaveBackpack = message.getS2CLeaveBackpack();
                    if (s2CLeaveBackpack.getCode() == 0) {
                        C2SKittyBackpack.C2S_KittyBackpack.Builder c2S_buyKitty = C2SKittyBackpack.C2S_KittyBackpack.newBuilder().setCallTimestamp(System.currentTimeMillis() / 1000);
                        MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SKittyBackpack(c2S_buyKitty).setMessageId(MessageOuterClass.Message.C2SKITTYBACKPACK_FIELD_NUMBER);
                        SocketManager.send(builder);
                        ToastUtils.showShortCenter(R.string.leve_pack_success);
                    } else {
                        ToastUtils.showShortCenter(R.string.position_filled);
                    }
                    showDialogEachTime();
                } else if (message.getMessageId() == MessageOuterClass.Message.S2CCAPACITYBACKPACK_FIELD_NUMBER) {
                    s2C_capacityBackpack = message.getS2CCapacityBackpack();
                    if (s2C_capacityBackpack.getCode() == 0) {
                        if (null != kittyBackpackItems) {
                            tvExpand.setText(Utils.getString(R.string.kitty_home_capacity_text) + kittyBackpackItems.size() + "/" + s2C_capacityBackpack.getCapacity());
                        } else {
                            tvExpand.setText(Utils.getString(R.string.kitty_home_capacity_text) + "0/" + s2C_capacityBackpack.getCapacity());
                        }
                        ToastUtils.showShortCenter(R.string.expand_success);
                    } else if (s2C_capacityBackpack.getCode() == 1) {
                        ToastUtils.showShortCenter(R.string.max_capacity);
                    } else if (s2C_capacityBackpack.getCode() == 2) {
                        ToastUtils.showShortCenter(R.string.lack_coin);
                    } else {
                        ToastUtils.showShortCenter(R.string.unknow_fail);
                    }
                }
            } else {
                llEmptyView.setVisibility(View.VISIBLE);
                llListView.setVisibility(View.GONE);
            }
            dismissDialog();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnect() {
        if (null != llEmptyView && null != llListView) {
            llEmptyView.setVisibility(View.VISIBLE);
            llListView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnectFailed(Throwable e) {
        if (null != llEmptyView && null != llListView) {
            llEmptyView.setVisibility(View.VISIBLE);
            llListView.setVisibility(View.GONE);
        }
    }

    @OnClick(R2.id.tv_repertory_draw)
    public void drawOut() {
        if (null != kittyBackpackItem) {
            showDialogEachTime();
            C2SLeaveBackpack.C2S_LeaveBackpack.Builder c2S_LeaveBackpack = C2SLeaveBackpack.C2S_LeaveBackpack.newBuilder().setLevel(kittyBackpackItem.getLevel());
            MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SLeaveBackpack(c2S_LeaveBackpack).setMessageId(MessageOuterClass.Message.C2SLEAVEBACKPACK_FIELD_NUMBER);
            SocketManager.send(builder);
        } else {
            ToastUtils.showShortCenter(R.string.draw_tip);
        }
    }


    @OnClick(R2.id.tv_repertory_expansion)
    public void expansion() {
        BuyRepertoryCapacityDialog buyRepertoryCapacityDialog = new BuyRepertoryCapacityDialog();
        buyRepertoryCapacityDialog.show(getSupportFragmentManager(), "buyRepertoryCapacityDialog");
        buyRepertoryCapacityDialog.setIOnClickListener(new BaseDialogFragment.IOnClickListener() {
            @Override
            public void onDismissClick() {
            }

            @Override
            public void onConfirmClick() {
                C2SCapacityBackpack.C2S_CapacityBackpack.Builder c2S_LeaveBackpack = C2SCapacityBackpack.C2S_CapacityBackpack.newBuilder().setCallTimestamp(System.currentTimeMillis() / 1000);
                MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SCapacityBackpack(c2S_LeaveBackpack).setMessageId(MessageOuterClass.Message.C2SCAPACITYBACKPACK_FIELD_NUMBER);
                SocketManager.send(builder);
            }
        });
    }

}

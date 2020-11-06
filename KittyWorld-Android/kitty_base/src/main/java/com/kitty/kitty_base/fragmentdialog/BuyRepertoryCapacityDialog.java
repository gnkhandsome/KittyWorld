package com.kitty.kitty_base.fragmentdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.utils.CatHelperUtil;
import com.kitty.kitty_base.utils.FormDataUtil;
import com.kitty.kitty_base.utils.TypeFaceUtils;
import com.kitty.kitty_base.ws.utils.SocketManager;
import com.kitty.kitty_res.R;
import com.kitty.kitty_res.R2;

import java.nio.ByteBuffer;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import kitty_protos.C2SKittyBackpack;
import kitty_protos.MessageOuterClass;
import kitty_protos.S2CKittyBackpack;

/**
 * @author ningkang
 */

public class BuyRepertoryCapacityDialog extends BaseDialogFragment {


    @BindView(R2.id.capacity_price)
    TextView capacityPrice;

    @Override
    protected boolean needForbidBackPress() {
        return false;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return false;
    }

    @Override
    protected void initData() {
        try {
            C2SKittyBackpack.C2S_KittyBackpack.Builder c2S_buyKitty = C2SKittyBackpack.C2S_KittyBackpack.newBuilder().setCallTimestamp(System.currentTimeMillis() / 1000);
            MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder().setC2SKittyBackpack(c2S_buyKitty).setMessageId(MessageOuterClass.Message.C2SKITTYBACKPACK_FIELD_NUMBER);
            SocketManager.send(builder);
            capacityPrice.setTypeface(TypeFaceUtils.getNumberTypeFace(TypeFaceUtils.NUM_TYPE));
            showDialogEachTime();
        } catch (Exception e) {
        }
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.fragment_dialog_buy_capacity;
    }


    @OnClick(R2.id.tv_add_to_repertory_confirm)
    public void onRepertoryConfirmClicked(View view) {
        iOnClickListener.onConfirmClick();
        dismiss();
    }

    @OnClick(R2.id.ll_buy_capacity_dialog_close_icon)
    public void onDialogCloseClicked(View view) {
        iOnClickListener.onDismissClick();
        dismiss();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        super.onMessage(bytes, data);
        MessageOuterClass.Message message = null;
        try {
            message = MessageOuterClass.Message.parseFrom(bytes);
            if (null != message) {
                if (MessageOuterClass.Message.S2CKITTYBACKPACK_FIELD_NUMBER == message.getMessageId()) {
                    S2CKittyBackpack.S2C_KittyBackpack s2C_kittyBackpack = message.getS2CKittyBackpack();
                    capacityPrice.setText(CatHelperUtil.getDispalyedNumber(FormDataUtil.getBackpackPriceByCapital(s2C_kittyBackpack.getCapacity() + 1).toString()));
                    dismissDialog();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);
            Objects.requireNonNull(dialog.getWindow()).setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}

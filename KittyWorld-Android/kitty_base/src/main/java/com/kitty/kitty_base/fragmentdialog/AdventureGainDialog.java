package com.kitty.kitty_base.fragmentdialog;

import android.widget.GridView;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.adapter.AdventureGainAdapter;
import com.kitty.kitty_base.adapter.AdventureGainDialogAdapter;
import com.kitty.kitty_base.adapter.AdventureMyPetAdapter;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.model.AdventureGainModel;
import com.kitty.kitty_base.model.AdventureMyPetModel;
import com.kitty.kitty_base.utils.TimeDescUtil;
import com.kitty.kitty_res.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AdventureGainDialog extends BaseDialogFragment {
    @BindView(R2.id.gv_adventure_gain)
    GridView gvAdventureGain;


    @Override
    protected void initData() {
        int[] images = {R.drawable.ic_kitty_level45,
                R.drawable.ic_kitty_level45,
                R.drawable.ic_kitty_level45,
                R.drawable.ic_kitty_level45,
                R.drawable.ic_kitty_level45,
                R.drawable.adventure_gain_coin,
                R.drawable.adventure_redpack_icon,
                R.drawable.ic_kitty_level39,
                R.drawable.ic_kitty_level38,
                R.drawable.ic_kitty_level40,
                R.drawable.ic_kitty_level44,
                R.drawable.ic_kitty_level41,
                R.drawable.ic_kitty_level42,
                R.drawable.ic_kitty_level43,
                R.drawable.ic_kitty_level36,
                R.drawable.ic_kitty_level37,
                R.drawable.ic_kitty_level35,
                R.drawable.ic_kitty_level34,
                R.drawable.ic_kitty_level33,
                R.drawable.unknow_gift,
        };
        String[] kittyTimes = {"永久", "7天", "1天", "小时", "分钟"};
        List<AdventureGainModel> adventureGainModels = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            AdventureGainModel adventureGainModel = new AdventureGainModel();
            adventureGainModel.imageResource = images[i];
            if (i < 5) {
                adventureGainModel.desc = kittyTimes[i];
            }
            adventureGainModels.add(adventureGainModel);
        }
        AdventureGainDialogAdapter adventureGainAdapter = new AdventureGainDialogAdapter(adventureGainModels, getContext());
        gvAdventureGain.setAdapter(adventureGainAdapter);
    }

    @Override
    protected boolean needForbidBackPress() {
        return false;
    }

    @Override
    protected boolean isCancelableTouchOutSide() {
        return true;
    }

    @Override
    protected int getPopUpLayoutId() {
        return R.layout.fragment_dialog_adventure_gain;
    }

    @OnClick(R2.id.iv_dismiss)
    public void dismissDialog() {
        dismiss();
    }
}

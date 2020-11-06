package com.kitty.kitty_base.views.dragview;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.utils.ResourceUtil;
import com.kitty.kitty_base.utils.Utils;

import java.util.concurrent.ExecutionException;

public class ImageSourceUtil {

    public static int getResorceByLevel(int level) {
        try {
            return ResourceUtil.getDrawableId(Utils.getContext(), "ic_kitty_level" + level);
        } catch (Exception e) {
            return R.drawable.ic_kitty_level37;
        }

    }

    public static int getTurnImageByName(String name) {
        try {
            return ResourceUtil.getDrawableId(Utils.getContext(), name);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getBuyResorceByLevel(int buyLevel) {
        try {
            return ResourceUtil.getDrawableId(Utils.getContext(), "ic_kitty_level" + buyLevel + "_buy");
        } catch (Exception e) {
            return R.drawable.ic_kitty_level37_buy;
        }
    }

    public static int getResourceByAction(int actionID) {
        int[] resourceId = {R.drawable.adventure_bag_icon, R.drawable.adventure_coin_icon, R.drawable.adventure_kitty_icon, R.drawable.adventure_kitty_icon, R.drawable.adventure_house};
        try {
            return resourceId[actionID];
        } catch (Exception e) {
            return resourceId[1];
        }
    }
}

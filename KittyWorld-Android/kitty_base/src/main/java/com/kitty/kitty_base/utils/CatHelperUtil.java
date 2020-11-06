package com.kitty.kitty_base.utils;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kitty.kitty_base.R;
import com.kitty.kitty_base.constant.SPConfig;
import com.kitty.kitty_base.constant.UnlockConfig;
import com.kitty.kitty_base.model.AdventureDynamicModel;
import com.kitty.kitty_base.model.CatNameModel;
import com.kitty.kitty_base.model.FriendLevelModel;
import com.kitty.kitty_base.model.FriendLevelProfitModel;
import com.kitty.kitty_base.model.KittyBase;
import com.kitty.kitty_base.model.LuckWheelModel;
import com.kitty.kitty_base.model.PointModel;
import com.kitty.kitty_base.model.RewardType;
import com.kitty.kitty_base.model.UnlockModel;
import com.kitty.websocket.util.LogUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kitty_protos.UserKittyInfoOuterClass;

public class CatHelperUtil {

    public static final String KITTY_NAME = "str_cat_lv";

    public static int getBuyLevelFromMaxLevel(int level) {
        try {
            List<KittyBase> kittyBases;
            if (null != FormDataUtil.kittyBases && FormDataUtil.kittyBases.size() > 0) {
                kittyBases = FormDataUtil.kittyBases;
            } else {
                kittyBases = FormDataUtil.getKittyBase();
            }
            for (KittyBase kittyBase1 : kittyBases) {
                if (kittyBase1.getLevel() == level) {
                    return (int) kittyBase1.getBuy_level();
                }
            }
        } catch (Exception e) {
            return 1;
        }
        return 1;
    }

    public static String getRecyclePriceByLevel(int level) {
        try {
            List<KittyBase> kittyBases;
            if (null != FormDataUtil.kittyBases && FormDataUtil.kittyBases.size() > 0) {
                kittyBases = FormDataUtil.kittyBases;
            } else {
                kittyBases = FormDataUtil.getKittyBase();
            }
            for (KittyBase kittyBase1 : kittyBases) {
                if (kittyBase1.getLevel() == level) {
                    return getDispalyedNumber(kittyBase1.getRecycle_price().toString());
                }
            }
        } catch (Exception e) {
            return "0";
        }
        return "0";
    }

    public static KittyBase getKittyBaseByLevel(int level) {
        try {
            List<KittyBase> kittyBases;
            if (null != FormDataUtil.kittyBases && FormDataUtil.kittyBases.size() > 0) {
                kittyBases = FormDataUtil.kittyBases;
            } else {
                kittyBases = FormDataUtil.getKittyBase();
            }
            for (KittyBase kittyBase1 : kittyBases) {
                if (kittyBase1.getLevel() == level) {
                    return kittyBase1;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

//    public static FriendLevelProfitModel getProfitIntCurrentState(BigDecimal money) {
//        if (null == money) {
//            return new FriendLevelProfitModel();
//        }
//        List<FriendLevelModel> friendLevelModels;
//        try {
//            if (null != FormDataUtil.friendLevelModels && FormDataUtil.friendLevelModels.size() > 0) {
//                friendLevelModels = FormDataUtil.friendLevelModels;
//            } else {
//                friendLevelModels = FormDataUtil.getFriendLevel();
//            }
//            FriendLevelProfitModel friendLevelProfitModel = new FriendLevelProfitModel();
//            for (int i = 0; i < friendLevelModels.size(); i++) {
//                FriendLevelModel friendLevelModel = friendLevelModels.get(i);
//                if (money.compareTo(friendLevelModel.getTotal_amount()) < 0) {
//                    friendLevelProfitModel.setState(NumberUtil.formatInteger(friendLevelModel.getStage()));
//                    friendLevelProfitModel.setMultiplier(friendLevelModel.getMultiplier());
//                    friendLevelProfitModel.setStateTotal(NumberUtil.getWanDividedNumber(friendLevelModel.getAmount()));
//                    BigDecimal tatolAmount = i == 0 ? BigDecimal.ZERO : friendLevelModels.get(i - 1).getTotal_amount();
//                    friendLevelProfitModel.setStateTotalProfit(NumberUtil.getWanDividedNumber(money.subtract(tatolAmount)));
//                    break;
//                }
//            }
//            return friendLevelProfitModel;
//        } catch (Exception e) {
//            return new FriendLevelProfitModel();
//        }
//    }


    public static List<LuckWheelModel> getLuckWheelList() {
        try {
            List<LuckWheelModel> luckWheelModels;
            if (null != FormDataUtil.luckWheelModels && FormDataUtil.luckWheelModels.size() > 0) {
                luckWheelModels = FormDataUtil.luckWheelModels;
            } else {
                luckWheelModels = FormDataUtil.getLuckWheelList();
            }
            return luckWheelModels;
        } catch (Exception e) {
            return new ArrayList();
        }
    }


    public static HashMap<String, FriendLevelModel> getFriendLevel() {
        try {
            HashMap<String, FriendLevelModel> luckWheelModels;
            if (null != FormDataUtil.friendLevelModels && FormDataUtil.friendLevelModels.size() > 0) {
                luckWheelModels = FormDataUtil.friendLevelModels;
            } else {
                luckWheelModels = FormDataUtil.getFriendLevel();
            }
            return luckWheelModels;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }


    public static List<PointModel> getLocations() {
        try {
            List<PointModel> locations;
            if (null != FormDataUtil.locations && FormDataUtil.locations.size() > 0) {
                locations = FormDataUtil.locations;
            } else {
                locations = FormDataUtil.getMapLocation();
            }
            return locations;
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static String getDisplayKittyOutput(int level) {
        try {
            List<KittyBase> kittyBases;
            if (null != FormDataUtil.kittyBases && FormDataUtil.kittyBases.size() > 0) {
                kittyBases = FormDataUtil.kittyBases;
            } else {
                kittyBases = FormDataUtil.getKittyBase();
            }
            List<JsonObject> kittyOutputModels;
            if (null != FormDataUtil.kittyOutputModels && FormDataUtil.kittyOutputModels.size() > 0) {
                kittyOutputModels = FormDataUtil.kittyOutputModels;
            } else {
                kittyOutputModels = FormDataUtil.getKittyOutPut();
            }
            BigDecimal outPut = BigDecimal.ZERO;
            for (KittyBase kittyBase1 : kittyBases) {
                if (kittyBase1.getLevel() == level) {
                    int outIndex = kittyBase1.getOutput_index();
                    for (JsonObject kittyOutputModel : kittyOutputModels) {
                        JsonElement jsonElement = kittyOutputModel.get("level");
                        int out_level = jsonElement.getAsInt();
                        if (out_level == level) {
                            JsonElement jsonElement1 = kittyOutputModel.get("output" + outIndex);
                            outPut = jsonElement1.getAsBigDecimal();
                        }
                    }
                }
            }
            return getDispalyedNumber(outPut.toString());
        } catch (Exception e) {
            return "0";
        }
    }

    public static BigDecimal getKittyOutput(int level) {
        try {
            List<KittyBase> kittyBases;
            if (null != FormDataUtil.kittyBases && FormDataUtil.kittyBases.size() > 0) {
                kittyBases = FormDataUtil.kittyBases;
            } else {
                kittyBases = FormDataUtil.getKittyBase();
            }
            List<JsonObject> kittyOutputModels;
            if (null != FormDataUtil.kittyOutputModels && FormDataUtil.kittyOutputModels.size() > 0) {
                kittyOutputModels = FormDataUtil.kittyOutputModels;
            } else {
                kittyOutputModels = FormDataUtil.getKittyOutPut();
            }
            BigDecimal outPut = BigDecimal.ZERO;
            for (KittyBase kittyBase1 : kittyBases) {
                if (kittyBase1.getLevel() == level) {
                    int outIndex = kittyBase1.getOutput_index();
                    for (JsonObject kittyOutputModel : kittyOutputModels) {
                        JsonElement jsonElement = kittyOutputModel.get("level");
                        int out_level = jsonElement.getAsInt();
                        if (out_level == level) {
                            JsonElement jsonElement1 = kittyOutputModel.get("output" + outIndex);
                            outPut = jsonElement1.getAsBigDecimal();
                        }
                    }
                }
            }
            return outPut;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static String getDispalyedNumber(String numStr) {
        try {
            if (TextUtils.isEmpty(numStr)) {
                return numStr;
            }
            String bouns = numStr.split("\\.")[0];
            if (new BigDecimal(bouns).compareTo(BigDecimal.ZERO) <= 0) {
                return numStr;
            }
            if (bouns.length() > 5) {
                String formatNum = null;
                String[] unit = {"k", "m", "b", "t", "aa", "ab", "ac", "ad", "ae", "af", "ag", "ah", "ai", "aj", "ak", "al", "am", "an", "ao", "ap", "aq"};
                BigDecimal num = new BigDecimal(bouns);
                DecimalFormat format = new DecimalFormat(",##0.00");
                String[] numArr = format.format(num).split(",");
                int remainder = String.valueOf(num).length() % 3;
                if (remainder == 1) {
                    formatNum = numArr[0] + numArr[1] + '.' + numArr[2].substring(0, 1) + unit[numArr.length - 3];
                } else if (remainder == 2) {
                    formatNum = numArr[0] + numArr[1] + unit[numArr.length - 3];
                } else {
                    formatNum = numArr[0] + "." + numArr[1].substring(0, 2) + unit[numArr.length - 2];
                }
                return formatNum;
            } else {
                return bouns;
            }
        } catch (Exception e) {
            return "0";
        }
    }

    private static String getFinalBonus(String strings, String endStr, int index) {
        String front = strings.substring(0, strings.length() - (index - 4));
        String end = strings.substring(strings.length() - (index - 4), strings.length());
        if (!TextUtils.isEmpty(endStr) && !TextUtils.isEmpty(end)) {
            endStr = endStr + end;
        } else if (TextUtils.isEmpty(endStr)) {
            endStr = end;
        }
        String finalStr = null;
        if (TextUtils.isEmpty(endStr)) {
            finalStr = front;
        } else if (endStr.length() > 2) {
            finalStr = front + "." + endStr.substring(0, 2);
        } else {
            finalStr = front + "." + endStr;
        }
        return finalStr;
    }


    public static List<AdventureDynamicModel> getAdventureModels() {
        try {
            List<AdventureDynamicModel> adventureDynamicModels;
            if (null != FormDataUtil.adventureDynamicModels && FormDataUtil.adventureDynamicModels.size() > 0) {
                adventureDynamicModels = FormDataUtil.adventureDynamicModels;
            } else {
                adventureDynamicModels = FormDataUtil.getAdventureEventModel();
            }
            return adventureDynamicModels;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static HashMap<String, CatNameModel> getKittyStringMap() {
        try {
            HashMap<String, CatNameModel> kittyStringModels;
            if (null != FormDataUtil.catNameModelHashMap && FormDataUtil.catNameModelHashMap.size() > 0) {
                kittyStringModels = FormDataUtil.catNameModelHashMap;
            } else {
                kittyStringModels = FormDataUtil.getKittyStringMap();
            }
            return kittyStringModels;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }


    public static String getKittyNameByLevel(int level) {
        HashMap<String, CatNameModel> nameModelHashMap = getKittyStringMap();
        return nameModelHashMap.get(KITTY_NAME + level).getName_cn();
    }

    public static BigDecimal getTotalOutPutAmountInteger(List<Integer> levelList) {
        try {
            BigDecimal total = new BigDecimal("0.00");
            for (int i = 0; i < levelList.size(); i++) {
                int level = levelList.get(i);
                BigDecimal output = CatHelperUtil.getKittyOutput(level);
                total = total.add(output);
            }
            Log.i("totalOutput", total.toString());
            return total;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal getTotalOutPutAmount(List<kitty_protos.SyncKittyInfoOuterClass.kittyLevelList> levelList) {
        try {
            BigDecimal total = BigDecimal.ZERO;
            for (int i = 0; i < levelList.size(); i++) {
                int level = levelList.get(i).getLevel();
                BigDecimal output = CatHelperUtil.getKittyOutput(level);
                total = total.add(output);
            }
            Log.i("totalOutput", total.toString());
            return total;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static String getTotalOutPut(List<Integer> levelList) {
        try {
            BigDecimal total = BigDecimal.ZERO;
            for (int i = 0; i < levelList.size(); i++) {
                int level = levelList.get(i);
                BigDecimal output = CatHelperUtil.getKittyOutput(level);
                total = total.add(output);
            }
            Log.i("totalOutput", total.toString());
            return getDispalyedNumber(total.toString());
        } catch (Exception e) {
            return "0";
        }
    }

    public static BigDecimal getTotalOutPutAmountByKittyInfo(List<UserKittyInfoOuterClass.KittyInfo> kittiesList) {
        try {
            BigDecimal total = BigDecimal.ZERO;
            for (int i = 0; i < kittiesList.size(); i++) {
                UserKittyInfoOuterClass.KittyInfo kittyInfo = kittiesList.get(i);
                BigDecimal output = CatHelperUtil.getKittyOutput(kittyInfo.getLevel());
                total = total.add(output);
            }
            Log.i("totalOutput", total.toString());
            return total;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static HashMap<String, UnlockModel> getUnlockMap() {
        try {
            HashMap<String, UnlockModel> unlockModelHashMap;
            if (null != FormDataUtil.unLockMap && FormDataUtil.unLockMap.size() > 0) {
                unlockModelHashMap = FormDataUtil.unLockMap;
            } else {
                unlockModelHashMap = FormDataUtil.getUnlockMap();
            }
            return unlockModelHashMap;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static void isUnlocked(String functionLocked, View view, boolean isGone) {
        int maxLevel = SPUtils.getInt(Utils.getContext(), SPConfig.MAX_LEVEL, 0);
        if (maxLevel <= 0) {
            return;
        }
        HashMap<String, UnlockModel> unlockModelHashMap = getUnlockMap();
        UnlockModel unlockModel = unlockModelHashMap.get(functionLocked);
        if (null == unlockModel) {
            return;
        }
        if (maxLevel < unlockModel.getUnlock_level()) {
            view.setVisibility(isGone ? View.GONE : View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static boolean isUnlocked(String functionLocked) {
        int maxLevel = SPUtils.getInt(Utils.getContext(), SPConfig.MAX_LEVEL, 0);
        if (maxLevel <= 0) {
            return false;
        }
        HashMap<String, UnlockModel> unlockModelHashMap = getUnlockMap();
        UnlockModel unlockModel = unlockModelHashMap.get(functionLocked);
        if (null == unlockModel) {
            return false;
        }
        if (maxLevel < unlockModel.getUnlock_level()) {
            ToastUtils.showLong(unlockModel.getUnlock_level() + Utils.getString(R.string.kitty_res_unlock_yet));
            return false;
        }
        return true;
    }


    public static boolean isUnlockedNoToast(String functionLocked) {
        int maxLevel = SPUtils.getInt(Utils.getContext(), SPConfig.MAX_LEVEL, 0);
        if (maxLevel <= 0) {
            return false;
        }
        HashMap<String, UnlockModel> unlockModelHashMap = getUnlockMap();
        UnlockModel unlockModel = unlockModelHashMap.get(functionLocked);
        if (null == unlockModel) {
            return false;
        }
        if (maxLevel < unlockModel.getUnlock_level()) {
            return false;
        }
        return true;
    }

    public static String getUnlockString(String functionLocked) {
        HashMap<String, UnlockModel> unlockModelHashMap = getUnlockMap();
        UnlockModel unlockModel = unlockModelHashMap.get(functionLocked);
        return unlockModel.getUnlock_level() + "级解锁";
    }


}

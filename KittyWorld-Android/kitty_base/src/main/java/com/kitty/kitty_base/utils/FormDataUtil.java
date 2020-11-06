package com.kitty.kitty_base.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kitty.kitty_base.model.AdsSettingModel;
import com.kitty.kitty_base.model.AdventureDynamicModel;
import com.kitty.kitty_base.model.BackPackPriceModel;
import com.kitty.kitty_base.model.CapitalPriceModel;
import com.kitty.kitty_base.model.CatNameModel;
import com.kitty.kitty_base.model.FriendLevelModel;
import com.kitty.kitty_base.model.KittyBase;
import com.kitty.kitty_base.model.LevelRewardModel;
import com.kitty.kitty_base.model.LuckWheelModel;
import com.kitty.kitty_base.model.PointModel;
import com.kitty.kitty_base.model.RewardModel;
import com.kitty.kitty_base.model.SpecialLevelRewardModel;
import com.kitty.kitty_base.model.TaskLevelModel;
import com.kitty.kitty_base.model.UnlockModel;
import com.kitty.kitty_base.utils.singleton.GsonSingleInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormDataUtil {

    public static List<LuckWheelModel> luckWheelModels;
    public static List<KittyBase> kittyBases;
    public static List<JsonObject> kittyOutputModels;
    //    public static List<BackPackPriceModel> backPackPriceModels;
    public static HashMap<String, FriendLevelModel> friendLevelModels;
    public static List<AdventureDynamicModel> adventureDynamicModels;
    public static HashMap<String, CatNameModel> catNameModelHashMap;
    public static List<PointModel> locations;
    public static HashMap<String, UnlockModel> unLockMap;

    public static void initFormData() {
        new Thread(() -> {
            luckWheelModels = getLuckWheelList();
            kittyBases = getKittyBase();
            kittyOutputModels = getKittyOutPut();
//            backPackPriceModels = getBackPackPrice();
            friendLevelModels = getFriendLevel();
            adventureDynamicModels = getAdventureEventModel();
            catNameModelHashMap = getKittyStringMap();
            locations = getMapLocation();
            unLockMap = getUnlockMap();
        }).start();
    }

    public static List<LuckWheelModel> getLuckWheelList() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "lucky_wheel_main.json");
            List<LuckWheelModel> luckWheelModel = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<List<LuckWheelModel>>() {
            }.getType());
            return luckWheelModel;
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<KittyBase> getKittyBase() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "kitty_basic.json");
            List<KittyBase> kittyBase = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<List<KittyBase>>() {
            }.getType());
            return kittyBase;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static List<JsonObject> getKittyOutPut() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "kitty_output.json");
            List<JsonObject> kittyBase = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<List<JsonObject>>() {
            }.getType());
            return kittyBase;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static List<BackPackPriceModel> getBackPackPrice() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "backpack_price.json");
            List<BackPackPriceModel> backPackPriceModels = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<List<BackPackPriceModel>>() {
            }.getType());
            return backPackPriceModels;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static HashMap<String, FriendLevelModel> getFriendLevel() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "friend_level.json");
            HashMap<String, FriendLevelModel> friendLevelModels = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<HashMap<String, FriendLevelModel>>() {
            }.getType());
            return friendLevelModels;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }


    public static int getDefaultCapacity() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "backpack_setting.json");
            List<JsonObject> defaultCapacity = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<List<JsonObject>>() {
            }.getType());
            JsonElement jsonElement = defaultCapacity.get(0).get("default_capacity");
            return jsonElement.getAsInt();
        } catch (Exception e) {
            return 0;
        }
    }


    public static BigDecimal getBackpackPriceByCapital(int capital) {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "backpack_price.json");
            List<CapitalPriceModel> capitalPriceModels = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<List<CapitalPriceModel>>() {
            }.getType());
            for (CapitalPriceModel capitalPriceModel : capitalPriceModels) {
                if (capitalPriceModel.getCapacity_num() == capital) {
                    return capitalPriceModel.getPrice();
                }
            }
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

    public static List<AdventureDynamicModel> getAdventureEventModel() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "adventure_strings.json");
            List<AdventureDynamicModel> adventureDynamicModels = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<List<AdventureDynamicModel>>() {
            }.getType());
            return adventureDynamicModels;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static HashMap<String, CatNameModel> getKittyStringMap() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "strings_map.json");
            HashMap<String, CatNameModel> catNameModelMap = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<HashMap<String, CatNameModel>>() {
            }.getType());
            return catNameModelMap;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static HashMap<String, RewardModel> getRewardModelMap() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "settings_main_map.json");
            HashMap<String, RewardModel> rewardModelHashMap = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<HashMap<String, RewardModel>>() {
            }.getType());
            return rewardModelHashMap;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }


    /**
     * 获取等级奖励
     *
     * @return
     */
    public static List<LevelRewardModel> getLevelReward() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "level_reward_main.json");
            List<LevelRewardModel> rewardModelHashMap = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<List<LevelRewardModel>>() {
            }.getType());
            return rewardModelHashMap;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 获取等级奖励
     *
     * @return
     */
    public static List<SpecialLevelRewardModel> getSpecialLevelReward() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "special_main.json");
            List<SpecialLevelRewardModel> rewardModelHashMap = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<List<SpecialLevelRewardModel>>() {
            }.getType());
            return rewardModelHashMap;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 获取地图坐标
     *
     * @return
     */
    public static List<PointModel> getMapLocation() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "location.json");
            List<PointModel> pointModelList = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<List<PointModel>>() {
            }.getType());
            return pointModelList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 任务等级奖励
     *
     * @return
     */
    public static HashMap<String, TaskLevelModel> getTaskLevel() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "task_level.json");
            HashMap<String, TaskLevelModel> taskLevelModels = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<HashMap<String, TaskLevelModel>>() {
            }.getType());
            return taskLevelModels;
        } catch (Exception e) {
            return new HashMap();
        }
    }


    /**
     * @return
     */
    public static HashMap<String, UnlockModel> getUnlockMap() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "unlock_unlock_map.json");
            HashMap<String, UnlockModel> unlockModelHashMap = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<HashMap<String, UnlockModel>>() {
            }.getType());
            return unlockModelHashMap;
        } catch (Exception e) {
            return new HashMap();
        }
    }


    /**
     * 获取广告设置
     *
     * @return
     */
    public static HashMap<String, AdsSettingModel> getAdsTypeSetting() {
        try {
            String json = JsonFileUtil.getJson(Utils.getContext(), "ads_type_setting_map.json");
            HashMap<String, AdsSettingModel> adsSettingModels = GsonSingleInstance.buildGson().fromJson(json, new TypeToken<HashMap<String, AdsSettingModel>>() {
            }.getType());
            return adsSettingModels;
        } catch (Exception e) {
            return new HashMap();
        }
    }

}

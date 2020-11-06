package com.kitty.kitty_base.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskModel implements Serializable {

    private int user_id;
    private UserSignTaskBean user_sign_task;
    private UserSignTaskBean.SocialTaskBean social_task;
    private UserSignTaskBean.WithdrawVerificationBean withdraw_verification;
    private UserSignTaskBean.BuyKittyTaskBean buy_kitty_task;
    private HashMap<String, UserSignTaskBean.LevelKittyTasksBean> level_kitty_tasks;

    @Getter
    @Setter
    public static class UserSignTaskBean implements Serializable {
        private String last_sign_day;
        private int curr_day;
        private int status;
        private Map<String, RewardBean> rewards;

        @Getter
        @Setter
        public static class RewardBean implements Serializable {
            private int type;
            private String id;
            private int amount;
            private int flag;
            private String extra;
        }

        @Getter
        @Setter
        public static class RewardBeanX implements Serializable {
            private String key;
            private int value;
            private int amount;
        }

        @Getter
        @Setter
        public static class SocialTaskBean implements Serializable {
            private int is_social;
            private int status;
            private RewardBeanX reward;
        }

        @Getter
        @Setter
        public static class WithdrawVerificationBean implements Serializable {
            private int status;
            private RewardBeanX reward;
        }

        @Getter
        @Setter
        public static class BuyKittyTaskBean implements Serializable {
            private int curr_buy_num;
            private int need_buy_num;
            private int status;
            private RewardBeanX reward;
        }

        @Getter
        @Setter
        public static class LevelKittyTasksBean implements Serializable {
            private String id;
            private int level;
            private int curr_invite_num;
            private int need_invite_num;
            private int status;
            private RewardBean reward;
        }
    }
}

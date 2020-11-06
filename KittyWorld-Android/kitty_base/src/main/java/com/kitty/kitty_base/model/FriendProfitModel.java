package com.kitty.kitty_base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendProfitModel implements Serializable {

    private int friend_count;
    private BigDecimal friend_income;
    private int friend_stage;
    private int unnamed_count;
    private BigDecimal today_friend_income;
    private BigDecimal today_direct_friend_income;
    private BigDecimal today_second_friend_income;
    private BigDecimal income;
    private BigDecimal will_income;
    private ParentInfoBean parent_info;
    private List<LatestFriendsBean> latest_friends;

    @Getter
    @Setter
    public static class ParentInfoBean implements Serializable {
        private int id;
        private String nickname;
        private String avatar;
        private String qq;
        private String wechat;
        private int invite_count;
        private int unnamed;
        private int is_social;
        private BigDecimal income;
        private int max_level;
    }

    @Getter
    @Setter
    public static class LatestFriendsBean implements Serializable {
        private int id;
        private String nickname;
        private String avatar;
    }
}

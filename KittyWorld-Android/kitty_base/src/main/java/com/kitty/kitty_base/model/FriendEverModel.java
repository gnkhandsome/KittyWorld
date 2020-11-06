package com.kitty.kitty_base.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FriendEverModel {

    private int ads_level;
    private int ads_amount;
    private int ad_value;
    private double invitepeople_percent;
    private double direct_friend_percent;
    private double indirect_friend_percent;
}

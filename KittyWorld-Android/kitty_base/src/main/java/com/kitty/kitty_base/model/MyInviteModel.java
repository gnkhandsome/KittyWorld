package com.kitty.kitty_base.model;


import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyInviteModel {

    private int invite_code;
    private int direct_friend;
    private int second_friend;
    private FriendProfitModel.ParentInfoBean parent_social;

}

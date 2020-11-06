package com.kitty.kitty_base.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RewardObject implements Serializable {
    public long rewardType;
    public String amount;
    public long createTime;
    public int rewardID;
    public ArrayList<Integer> kittyLevelList;
    public String extra;
    // 广告次数
    public int value;
    public boolean noTimeView = false;
}

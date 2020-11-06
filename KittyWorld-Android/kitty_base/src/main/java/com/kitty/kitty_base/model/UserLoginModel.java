package com.kitty.kitty_base.model;

import java.io.Serializable;
import java.util.List;

public class UserLoginModel implements Serializable {
    public int hourBonusCount;
    public int luckyWheelCount;
    public int adRemainNum;
    public String offlineTime;
    public long lastHourBonusRefreshTime;
    public long lastHourBonusTime;
    public boolean redPacket;
    public List<Integer> kittyInfos;
    public int todayDividend;
    public List<Boolean> unlockList;
}

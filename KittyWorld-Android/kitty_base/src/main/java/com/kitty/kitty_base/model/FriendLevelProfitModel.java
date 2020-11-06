package com.kitty.kitty_base.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendLevelProfitModel {

    private BigDecimal stateTotalProfit;
    private BigDecimal stateTotal;
    private String state;
    private double multiplier;
}

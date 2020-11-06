package com.kitty.kitty_base.model;


import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendLevelModel {

    private int stage;
    private BigDecimal amount;
    private BigDecimal total_amount;
    private double multiplier;

}

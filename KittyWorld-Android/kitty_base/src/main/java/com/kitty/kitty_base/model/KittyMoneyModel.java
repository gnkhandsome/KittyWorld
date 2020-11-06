package com.kitty.kitty_base.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KittyMoneyModel {
    private int kitty_count;
    private BigDecimal today_dividend;
    private BigDecimal sum_income;
}

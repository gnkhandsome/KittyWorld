package com.kitty.kitty_base.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KittyBase {
    private int level;
    private int buy_level;
    private BigDecimal recycle_price;
    private int output_index;
    private String animation;
    private String avatar;
    private BigDecimal ads_rewards;
    private String description;
    private String name_key;

}

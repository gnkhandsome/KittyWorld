package com.kitty.kitty_base.model;


import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LuckWheelModel implements Serializable {
    private int item_id;
    private String description;
    private String item_name_key;
    private String item_icon;
}

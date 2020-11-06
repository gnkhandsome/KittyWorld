package com.kitty.kitty_base.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdsSettingModel implements Serializable {

    private String ads_type;
    private String description;
    private int expend_times;
    private String rewards_description;
}

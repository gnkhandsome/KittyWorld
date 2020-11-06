package com.kitty.kitty_base.model;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.utils.CountDownTimerUtil;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DragResourceViewModel implements Serializable {
    private int level;
    private long expireTime;
    private long expireIn;
}

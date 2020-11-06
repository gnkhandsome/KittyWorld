package com.kitty.kitty_base.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithDrawModel implements Serializable {
    public int amount;
    public String way;
    public boolean isFirst;
    public String type;
}

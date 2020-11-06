package com.kitty.kitty_base.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DragViewLocationModel {
    private int[][] location;
    private int originLeft;
    private int originRight;
    private int originTop;
    private int originBottom;
    private int raw;
    private int cul;
    private int position;
}

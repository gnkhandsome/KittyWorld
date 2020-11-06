package com.kitty.kitty_base.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TipContentModel implements Serializable {
    private String title;
    private String content;
    private boolean isHtml;
    private int type;
}

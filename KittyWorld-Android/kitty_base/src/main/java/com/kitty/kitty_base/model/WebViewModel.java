package com.kitty.kitty_base.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WebViewModel implements Serializable {
    public String title;
    public String url;
    public String iconUrl;
    public String desc;
}
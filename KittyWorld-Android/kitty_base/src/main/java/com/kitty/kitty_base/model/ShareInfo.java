package com.kitty.kitty_base.model;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareInfo implements Serializable {

    private String title;

    private String desc;

    private String link;

    private String imgUrl;

    private String shareMedia;

    private String picBase;
}

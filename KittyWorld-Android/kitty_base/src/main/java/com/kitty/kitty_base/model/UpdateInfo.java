package com.kitty.kitty_base.model;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInfo implements Serializable {

    private int id;
    private String channel;
    private String platform;
    private String version;
    private int is_force;
    private String download_url;
    private String info;
    private String info_en;
    private int create_time;
}

package com.kitty.kitty_base.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopServerModel implements Serializable {

    private long begin_time;
    private long end_time;
    private long start_time;
    private long stop_time;
    private String title;
    private String content;
    private int type;

}




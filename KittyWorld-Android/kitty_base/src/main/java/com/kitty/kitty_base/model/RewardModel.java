package com.kitty.kitty_base.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardModel<T> {

    private String id;
    private String data;
    private T content;
    private String desc;

}

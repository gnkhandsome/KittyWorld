package com.kitty.kitty_base.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class WithDrawDataModel implements Serializable {

    private boolean is_new_player;
    private ConditionBean condition;
    private List<Integer> amount;

    @Setter
    @Getter
    public static class ConditionBean {
        private String type;
        private int amount;
        private int number;
        private int curr_number;
        private boolean finished;
    }
}

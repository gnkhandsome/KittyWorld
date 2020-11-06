package com.kitty.kitty_base.model;


import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MessageModel implements Serializable {

    private List<MessageModelItem> Items;
    private int curr_page;
    private int limit;
    private int total;

    @Getter
    @Setter
    public static class MessageModelItem  implements Serializable {

        private int id;
        private int user_id;
        private String title;
        private String content;
        private String reward;
        private RewardInfoBean reward_info;
        private int type;
        private int status;
        private long create_time;
        private String created_at;
        private String updated_at;


        @Getter
        @Setter
        public static class RewardInfoBean {
            private int id;
            private int amount;
            private int type;
            private String extra;
        }
    }
}

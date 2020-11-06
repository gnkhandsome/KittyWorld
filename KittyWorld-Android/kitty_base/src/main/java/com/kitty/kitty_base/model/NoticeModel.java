package com.kitty.kitty_base.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息通知
 */

@Getter
@Setter
public class NoticeModel implements Serializable {

    /**
     * content
     */
    private String content;

}

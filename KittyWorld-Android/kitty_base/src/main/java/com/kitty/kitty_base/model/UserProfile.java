package com.kitty.kitty_base.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile implements Serializable {
    private long id;
    private String uuid;
    private String nickname;
    private String avatar;
    private long status;
    private String oauth_type;
    private String oauth_id;
    private String oauth_access_token;
    private long oauth_expires;
    private String oauth_extra;
    private long is_first;
    private long is_login;
    private String created_at;
    private String qq;
    private String wechat;
    private BigDecimal money;
    private String updated_at;
    private long parent_visit;
    private long child_visit;
    private int unnamed;
}

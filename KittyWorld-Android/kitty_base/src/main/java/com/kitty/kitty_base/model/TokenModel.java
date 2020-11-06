package com.kitty.kitty_base.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenModel {
    private String access_token;
    private String token_type;
    private long expires_in;
}

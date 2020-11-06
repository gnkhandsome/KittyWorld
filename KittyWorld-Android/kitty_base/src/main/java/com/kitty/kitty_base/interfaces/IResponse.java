package com.kitty.kitty_base.interfaces;

import com.kitty.kitty_base.http.base.BaseResponse;

public interface IResponse<T> {
    void onSuccess(BaseResponse<T> baseResponse);

    void onError(Throwable throwable);
}

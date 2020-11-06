package com.kitty.kitty_base.http.callback;


import android.app.Activity;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.http.Platform;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 数据返回结果回调
 *
 * @author ningkang
 */

public abstract class BaseObserver<T> implements Observer<T> {

    private Platform mPlatform;


    public BaseObserver() {
        mPlatform = Platform.get();
    }

    public BaseObserver(Activity mActivity) {
        mPlatform = Platform.get();
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (null != d) {
            onBaseDisposable(d);
        }
    }

    @Override
    public void onNext(final T value) {
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                onBaseNext(value);
                if (value instanceof BaseResponse) {
                    showError(((BaseResponse) value).code);
                }
            }
        });
    }

    private void showError(int code) {
        if (code == 1002) {
            ToastUtils.showShortCenter(R.string.recycle_inviter_error);
        } else if (code == 1004) {
            ToastUtils.showShortCenter(R.string.not_allow_size);
        } else if (code == 1005) {
            ToastUtils.showShortCenter(R.string.not_allow_nick_name);
        } else if (code == 1010) {
            ToastUtils.showShortCenter(R.string.experied);
        } else if (code == 1018) {
            ToastUtils.showShortCenter(R.string.money_not_enough);
        } else if (code == 1019) {
            ToastUtils.showShortCenter(R.string.level_not_enough);
        } else if (code == 1021) {
            ToastUtils.showShortCenter(R.string.position_empty);
        }
    }

    @Override
    public void onError(final Throwable e) {
        mPlatform.execute(() -> onBaseError(e));
    }

    @Override
    public void onComplete() {
        onBaseComplete();
    }

    protected void onBaseError(Throwable t) {
    }


    protected abstract void onBaseNext(T data);

    protected void onBaseComplete() {
    }

    private void onBaseDisposable(Disposable d) {
    }

}
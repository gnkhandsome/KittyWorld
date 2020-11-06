package com.kitty.kitty_base.receiver;

import com.kitty.kitty_base.utils.NetUtils;

public interface NetChangeObserver {
    void onNetConnected(NetUtils.NetType netType);

    void onNetDisConnect();
}

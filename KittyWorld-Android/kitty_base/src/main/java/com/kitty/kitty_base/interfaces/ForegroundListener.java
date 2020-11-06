
package com.kitty.kitty_base.interfaces;

/**
 * 前后台切换监听
 * @author ningkang
 */
public interface ForegroundListener {
    /**
     * 进入前台
     */
    void onBecameForeground();

    /**
     * 进入后台
     */
    void onBecameBackground();
}
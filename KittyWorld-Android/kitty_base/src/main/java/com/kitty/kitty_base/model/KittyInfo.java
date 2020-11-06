package com.kitty.kitty_base.model;

/**
 * 动物信息基类
 * created by Jiang on 2020/5/28 15:18
 */
public class KittyInfo {
    private int level;//等级
    private int count;//数量

    public KittyInfo(int level, int count) {
        this.level = level;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

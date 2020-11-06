package com.kitty.kitty_base.utils;

import com.kitty.kitty_base.model.FriendLevelModel;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {

    public static BigDecimal getWanDividedNumber(BigDecimal origin, int scale) {
        if (null != origin) {
            try {
                BigDecimal result = origin.divide(new BigDecimal(100000)).setScale(scale, RoundingMode.HALF_UP);
                if (result.compareTo(BigDecimal.ZERO) == 0) {
                    return BigDecimal.ZERO.setScale(2);
                }
                return result;
            } catch (Exception e) {
                return BigDecimal.ZERO.setScale(2);
            }
        }
        return BigDecimal.ZERO.setScale(2);
    }

    public static BigDecimal getWanDividedNumber(BigDecimal origin) {
        if (null != origin) {
            try {
                return origin.divide(new BigDecimal(100000)).setScale(2, RoundingMode.HALF_UP);
            } catch (Exception e) {
                return BigDecimal.ZERO.setScale(2);
            }
        }
        return BigDecimal.ZERO.setScale(2);
    }

    public static BigDecimal getWithDrawWanDividedNumber(BigDecimal origin) {
        if (null != origin) {
            try {
                return origin.divide(new BigDecimal(100000));
            } catch (Exception e) {
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    public static String formatInteger(int num) {
        if (num == 5) {
            return "五";
        } else if (num == 10) {
            return "十";
        } else if (num == 0) {
            return "零";
        } else if (num == 1) {
            return "一";
        } else if (num == 2) {
            return "二";
        } else if (num == 3) {
            return "三";
        } else if (num == 4) {
            return "四";
        } else if (num == 6) {
            return "六";
        } else if (num == 7) {
            return "七";
        } else if (num == 8) {
            return "八";
        } else if (num == 9) {
            return "九";
        }
        return String.valueOf(num);
    }

    public static int getPositiveInteger(int num) {
        return num < 0 ? 0 : num;
    }
}

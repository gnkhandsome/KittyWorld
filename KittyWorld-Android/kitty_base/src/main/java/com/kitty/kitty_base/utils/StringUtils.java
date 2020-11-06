package com.kitty.kitty_base.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author zhangfeng
 * @date 2017/7/6
 */

public class StringUtils {


    /**
     * 字符串是否为空
     *
     * @param str 字符串
     * @return 为空返回true，否则false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }

    /**
     * 字符串是否为身份证号码，基本校验
     *
     * @param str
     * @return 是返回true，否则false
     */
    public static boolean isIdNo(String str) {
        Pattern compile = Pattern.compile("^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|X)$");

        return compile.matcher(str).matches();
    }

    /**
     * utf-8编码
     *
     * @param string
     * @return
     */
    public static String encodeUTF8(String string) {
        if (StringUtils.isEmpty(string)) {
            return string;
        }

        try {
            return new String(string.getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * url编码
     *
     * @param param
     * @return
     */
    public static String urlEncode(String param) {
        try {
            return URLEncoder.encode(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 隐藏手机号中间部分
     *
     * @param mobile
     * @return
     */
    public static String hideMobileMiddle(String mobile) {
        return isEmpty(mobile) ? "****" : mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length());
    }

    /**
     * 移除一个小数后面无用的0
     *
     * @param decimal
     * @return
     */
    public static String removeDecimalZeros(String decimal) {
        return decimal = decimal.indexOf(".") < 0 ? decimal : decimal.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    /**
     * 获取文件链接的后缀名
     *
     * @param downLoadUrl 下载链接
     * @return
     */
    public static String getDownLoadFileType(String downLoadUrl) {
        return downLoadUrl.substring(downLoadUrl.lastIndexOf("."));
    }

    /**
     * Url截取键值对工具
     */
    public static Map<String, List<String>> getQueryMap(String url) {
        try {
            Map<String, List<String>> params = new HashMap<String, List<String>>();
            String[] urlParts = url.split("\\?");
            if (urlParts.length <= 1) {
                return new HashMap<>();
            }

            String query = urlParts[1];
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                String key = URLDecoder.decode(pair[0], "UTF-8");
                String value = "";
                if (pair.length > 1) {
                    value = URLDecoder.decode(pair[1], "UTF-8");
                }

                List<String> values = params.get(key);
                if (values == null) {
                    values = new ArrayList<String>();
                    params.put(key, values);
                }
                values.add(value);
            }

            return params;
        } catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
        }
    }

    /**
     * 通过图片路径截取图片名称
     */
    public static String getPicNameFromPath(String picturePath) {
        if (TextUtils.isEmpty(picturePath)) {
            return System.currentTimeMillis() + ".png";
        }
        String[] temp = picturePath.replaceAll("\\\\", "/").split("/");
        String fileName = "";
        if (temp.length > 1) {
            fileName = temp[temp.length - 1];
        }
        return fileName;
    }

    /**
     * 输入的金额格式是否合法
     */
    public static boolean isLegalAmountInput(String input) {
        return !TextUtils.isEmpty(input) && !input.startsWith(".");
    }

}

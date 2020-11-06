package com.kitty.kitty_base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import androidx.core.content.SharedPreferencesCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kitty.kitty_base.constant.SPConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kitty_protos.MessageOuterClass;

/**
 * 简化SharedPreferences工具类
 *
 * @author zhangfeng
 * @date 2017/7/4
 */

public class SPUtils {

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    private static SharedPreferences getSp(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("SpUtils", Context.MODE_PRIVATE);
            editor = sp.edit();
        }
        return sp;
    }


    /**
     * 存入字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的值
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences preferences = getSp(context);
        //存入数据
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(Context context, String key) {
        SharedPreferences preferences = getSp(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @return 得到的字符串
     */
    public static String getString(Context context, String key) {
        SharedPreferences preferences = getSp(context);
        return preferences.getString(key, "");
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的默认值
     * @return 得到的字符串
     */
    public static String getString(Context context, String key, String value) {
        SharedPreferences preferences = getSp(context);
        return preferences.getString(key, value);
    }

    /**
     * 保存布尔值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取布尔值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 返回保存的值
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存long值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putLong(Context context, String key, long value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获取long值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static long getLong(Context context, String key, long defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getLong(key, defValue);
    }

    /**
     * 保存int值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 获取long值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getInt(key, defValue);
    }


    /**
     * 保存List
     *
     * @param tag
     * @param dataList
     */
    public static <T> void setDataList(String tag, List<T> dataList) {
        try {
            Gson gson = new Gson();
            //转换成json数据，再保存
            String strJson = gson.toJson(dataList);
            editor.putString(tag, strJson);
            editor.commit();
        } catch (Exception e) {

        }
    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public static <T> List<T> getDataList(String tag) {
        List<T> datalist = new ArrayList<>();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }


    public static ArrayList<String> getUrlList(String tag) {
        try {
            ArrayList<String> datalist = new ArrayList<>();
            String strJson = sp.getString(tag, null);
            if (null == strJson) {
                return datalist;
            }
            Gson gson = new Gson();
            datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
            }.getType());
            return datalist;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    /**
     * 存储Map集合
     *
     * @param key 键
     * @param map 存储的集合
     * @param <K> 指定Map的键
     * @param <T> 指定Map的值
     */
    public <K, T> void setMap(String key, Map<K, T> map) {
        if (map == null || map.isEmpty() || map.size() < 1) {
            return;
        }

        Gson gson = new Gson();
        String strJson = gson.toJson(map);
        editor.putString(key, strJson);
        editor.commit();
    }

    /**
     * 获取Map集合
     */
    public <K, T> Map<K, T> getMap(String key) {
        Map<K, T> map = new HashMap<>();
        String strJson = sp.getString(key, null);
        if (strJson == null) {
            return map;
        }
        Gson gson = new Gson();
        map = gson.fromJson(strJson, new TypeToken<Map<K, T>>() {
        }.getType());
        return map;
    }

    /**
     * 存储对象
     */
    public static void put(Context context, String key, Object obj) throws IOException {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();

        putString(context, key, objectStr);
    }

    /**
     * 获取对象
     */
    public static Object get(Context context, String key)
            throws IOException, ClassNotFoundException {
        String wordBase64 = getString(context, key);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        // 将byte数组转换成product对象
        Object obj = ois.readObject();
        bais.close();
        ois.close();
        return obj;
    }


    /**
     * 清除所有数据
     */
    public static void clear() {
        // 先获取当前配置
        String type = SPUtils.getString(Utils.getContext(), SPConfig.URL_TYPE);
        SharedPreferences sp = getSp(Utils.getContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        //配置url测试正式
        SPUtils.putString(Utils.getContext(), SPConfig.URL_TYPE, type);
    }
}


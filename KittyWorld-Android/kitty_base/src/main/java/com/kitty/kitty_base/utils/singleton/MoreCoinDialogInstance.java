package com.kitty.kitty_base.utils.singleton;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Gson单列对象类
 *
 * @author ningkang
 */

public class MoreCoinDialogInstance {

    private static class GsonInstanceHolder {
        static final Gson INSTANCE = new Gson();
    }

    public static Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    public static Gson getGsonInstance() {
        return GsonInstanceHolder.INSTANCE;
    }

}

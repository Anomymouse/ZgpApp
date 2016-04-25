package com.zgp.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/3/3.
 */
public class MyLogger {
    public static void log(String msg) {
        Log.e("MyLogger", msg);
    }

    public static void log(String tag, String msg) {
        Log.e(tag, msg);
    }
}

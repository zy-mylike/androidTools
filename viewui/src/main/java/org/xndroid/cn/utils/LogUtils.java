package org.xndroid.cn.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class LogUtils {

    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;
    public static String customTagPrefix = "";

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(Locale.getDefault(), tag, new Object[]{callerClazzName, caller.getMethodName(), Integer.valueOf(caller.getLineNumber())});
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void e(String error, Throwable e) {
        if (allowE) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.e(tag, error, e);
        }
    }

    public static void e(String error) {
        if (allowE) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.e(tag, error);
        }
    }

    public static void d(String error, Throwable e) {
        if (allowD) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.d(tag, error);
        }
    }

    public static void d(String error) {
        if (allowD) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.d(tag, error);
        }
    }

    public static void w(String error, Throwable e) {
        if (allowW) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.w(tag, error);
        }
    }

    public static void w(String error) {
        if (allowW) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.w(tag, error);
        }
    }

    public static void i(String error, Throwable e) {
        if (allowI) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.i(tag, error);
        }
    }

    public static void i(String error) {
        if (allowI) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.i(tag, error);
        }
    }

    public static void v(String error, Throwable e) {
        if (allowV) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.v(tag, error);
        }
    }

    public static void v(String error) {
        if (allowV) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.v(tag, error);
        }
    }


}

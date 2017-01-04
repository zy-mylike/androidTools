package org.xndroid.cn.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class CommonUtils {
    /**
     * 隐藏输入键盘
     *
     * @param context
     * @param edit
     */
    public static void hideKeyboard(Context context, EditText edit) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService("input_method");
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    /**
     * 获取app 版本Code
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();

        try {
            PackageInfo e = pm.getPackageInfo(context.getPackageName(), 0);
            return e.versionCode;
        } catch (PackageManager.NameNotFoundException var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取app 版本
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();

        try {
            PackageInfo e = pm.getPackageInfo(context.getPackageName(), 0);
            return e.versionName;
        } catch (PackageManager.NameNotFoundException var3) {
            var3.printStackTrace();
            return "未知";
        }
    }

    /**
     * 安装apk
     *
     * @param temp
     * @param context
     */
    public static void installApk(File temp, Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(268435456);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(temp), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

}

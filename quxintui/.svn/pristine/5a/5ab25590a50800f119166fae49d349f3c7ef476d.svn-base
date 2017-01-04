package com.enetic.push.utils;

import android.text.TextUtils;

import org.eteclab.base.utils.Bytes;
import org.eteclab.base.utils.Digests;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by json on 2016/3/31.
 */
public class AppTools {

    public static String MD5(String s) {
        try {
            return Bytes.toHex(Digests.md5((s).getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @param phone
     * @return
     */
    public static boolean isMobileValid(String phone) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[6-8])|(18[1-4,5-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }


    /**
     * @param
     * @return
     */
    public static boolean isMoneyValid(String phone) {
        Pattern p = Pattern.compile("##.00");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * @param
     * @return
     */
    public static String moneyFormat(double phone) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        ;
        return df.format(phone);
    }

    public static String numFormat(String value) {

        if (TextUtils.isEmpty(value)) {
            return "0";
        }

        Integer in = Integer.valueOf(value);
        if (in >= 0 && in <= 10000) {
            return in + "";
        } else if (in > 10000 && in < 10000 * 10000) {
            return in / 10000 + "万+";
        } else/*
        if (in < 100000000) */ {
            return in / 100000000 + "亿+";
        }
    }

    public static String getNowDate(long times) {
        Date date = new Date(times);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public static String getNowTime(long times) {
        Date date = new Date(times);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}

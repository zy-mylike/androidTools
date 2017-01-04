package com.enetic.push.view;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by David on 16/8/5.
 * 整数输入范围 eg:0~100
 */
public class IntegerInputFilter implements InputFilter {

    Pattern mPattern;

    //最大数
    private int MAX_VALUE;

    private static final String ZERO = "0";

    public IntegerInputFilter() {
        mPattern = Pattern.compile("([0-9])*");
    }


    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return 输入内容
     */

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();

        //验证删除等按键
        if (TextUtils.isEmpty(sourceText) || (destText.startsWith("0"))) {
            return "";
        }


        Matcher matcher = mPattern.matcher(source);

            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
            if (!matcher.matches()) {
                return "";
            } else {
                //验证输入数字的大小
                //double sumText = Double.parseDouble(destText + sourceText);
                int sumText2=Integer.parseInt(destText + sourceText);
                if (sumText2 > 100) {
                    return dest.subSequence(dstart, dend);
                }

            }



        return dest.subSequence(dstart, dend) + sourceText;
    }


    /**
     * 设置最大数。
     * @param MAX_VALUE
     */
    public void setMAX_VALUE(int MAX_VALUE) {
        this.MAX_VALUE = MAX_VALUE;
    }
}

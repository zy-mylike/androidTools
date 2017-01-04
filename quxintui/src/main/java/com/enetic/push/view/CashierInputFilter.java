package com.enetic.push.view;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CashierInputFilter implements InputFilter {
    Pattern mPattern;

    //最大数
    private Double MAX_VALUE = Double.MAX_VALUE;
    //小数点前位数
    private int mDigit_nums = 100;
    //小数点后的位数  
    private static final int POINTER_LENGTH = 2;

    private static final String POINTER = ".";

    private static final String ZERO = "0";

    public CashierInputFilter() {
        mPattern = Pattern.compile("([0-9]|\\.)*");
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
        if (TextUtils.isEmpty(sourceText) || (destText.startsWith("0") && !destText.contains(POINTER))) {
            return "";
        }

        Matcher matcher = mPattern.matcher(source);
        //已经输入小数点的情况下，只能输入数字  
        if (destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return "";
            } else {
                if (POINTER.equals(source)) {  //只能输入一个小数点  
                    return "";
                }
            }

            //验证小数点精度，保证小数点后只能输入两位  
            int index = destText.indexOf(POINTER);
            int length = dend - index;


            if (length > POINTER_LENGTH) {
                return dest.subSequence(dstart, dend);
            }
        } else {
            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0  
            if (!matcher.matches()) {
                return "";
            } else {
                if ((POINTER.equals(source)) && TextUtils.isEmpty(destText)) {
                    return "";
                }
            }
        }

        //验证输入金额的大小  
        double sumText = Double.parseDouble(destText + sourceText);
        if (sumText > MAX_VALUE) {
            return dest.subSequence(dstart, dend);
        }

        return dest.subSequence(dstart, dend) + sourceText;
    }

    public CashierInputFilter setmDigit(int digit) {
        if(digit <=0 ){
            throw new RuntimeException("digit < 0 not legitimate");
        }
        this.mDigit_nums = digit;
        String d = "9";
        for(int index = 0 ; index < digit ;index++){
            d +="9";
        }
        MAX_VALUE = Double.parseDouble(d+".99");
        return this;
    }
}  
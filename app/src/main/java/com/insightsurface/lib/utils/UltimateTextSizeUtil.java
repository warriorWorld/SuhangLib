package com.insightsurface.lib.utils;/**
 * Created by Administrator on 2016/10/27.
 */

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

/**
 * 作者：苏航 on 2016/10/27 10:56
 * 邮箱：772192594@qq.com
 */
public class UltimateTextSizeUtil {

    public static SpannableString getColoredText(String text, int start, int end, int color) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static String getFormatRate(double rate) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        return df.format(rate);
    }

    //获取仅数字被染色且被逗号的text
    public static SpannableString getColoredNumText(String frontText, String num, String behindText, int color, boolean needZero) {
        String dotNum;
        //需要.00的形式就用这个
        if (needZero) {
            dotNum = NumberUtil.toCommaNum(num);
        } else {
            dotNum = NumberUtil.toCommaNumButNoDot(num);
        }
        String res = frontText + dotNum + behindText;
        return getColoredText(res, frontText.length(), frontText.length() + dotNum.length(), color);
    }

    public static SpannableString getEmphasizedSpannableString(String text, String emphasizeText,
                                                               int textSize, int color, int textStyle) {
        String[] empasize = new String[1];
        empasize[0] = emphasizeText;
        return getEmphasizedSpannableString(text, empasize, textSize, color, textStyle);
    }

    public static SpannableString getEmphasizedSpannableString(String text, String[] emphasizeTexts,
                                                               int textSize, int color, int textStyle) {
        SpannableString spannableString = new SpannableString(text);
        String emphasizeText;
        int start = -1, end = 0;

        for (int i = 0; i < emphasizeTexts.length; i++) {
            emphasizeText = emphasizeTexts[i];
            if (!text.contains(emphasizeText)) {
                break;
            }
            //从上一个的结束开始搜 否则如果有重复的就会有bug
            start = text.indexOf(emphasizeText, end);

            end = start + emphasizeText.length();

            if (start == -1) {
                break;
            }
            //必须得每次都new一遍 否则只有一个起效
            if (color != 0) {
                spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (textSize != 0) {
                spannableString.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (textStyle != 0) {
                spannableString.setSpan(new StyleSpan(textStyle), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }
}

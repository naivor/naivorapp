package com.naivor.app.extras.utils;

import android.content.Context;


/**
 * DpUtil 是一跟屏幕适配有关的工具类，主要功能为dp，sp，px的相互转换
 */
public class DpUtil {

    /**
     * 获取屏幕像素密度相对于标准屏幕(160dpi)倍数
     *
     * @param context 当前上下文对象
     * @return float 屏幕像素密度
     */
    @SuppressWarnings("deprecation")
    public static float getScreenDensity(Context context) {

        return context.getResources().getDisplayMetrics().density;
    }


    /**
     * 将dp转换成px
     *
     * @param context 当前上下文对象
     * @param dp dp值
     * @return px的值
     */
    public static int dp2px(Context context, float dp) {
        return (int) (getScreenDensity(context) * dp+0.5f);
    }

    /**
     * 将px转换成dp
     *
     * @param context 当前上下文对象
     * @param px px的值
     * @return dp值
     */
    public static int px2dp(Context context, float px) {
        if (px==0){
            return 0;
        }else {
            return (int) (px/getScreenDensity(context)+0.5f);
        }
    }
}

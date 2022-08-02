/*
 * Copyright (c) 2016. Naivor.All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.naivor.android.app.common.utils

import android.content.Context

/**
 * DpUtil 是一跟屏幕适配有关的工具类，主要功能为dp，sp，px的相互转换
 */
object DpUtil {
    /**
     * 获取屏幕像素密度相对于标准屏幕(160dpi)倍数
     *
     * @param context 当前上下文对象
     * @return float 屏幕像素密度
     */
    fun getScreenDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    /**
     * 将dp转换成px
     *
     * @param context 当前上下文对象
     * @param dp dp值
     * @return px的值
     */
    fun dp2px(context: Context, dp: Float): Int {
        return (getScreenDensity(context) * dp + 0.5f).toInt()
    }

    /**
     * 将px转换成dp
     *
     * @param context 当前上下文对象
     * @param px px的值
     * @return dp值
     */
    fun px2dp(context: Context, px: Float): Int {
        return if (px == 0f) {
            0
        } else {
            (px / getScreenDensity(context) + 0.5f).toInt()
        }
    }
}
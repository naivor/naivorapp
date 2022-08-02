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

import android.text.format.Time
import java.text.SimpleDateFormat
import java.util.*

/**
 * DateUtil 是一个关于时间，日期转换和判断的类
 */
object DateUtil {
    /**
     * 获取当前时间
     *
     * @return 毫秒
     */
    fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

    /**
     * 获取当前时间
     *
     * @return 标准时间字符串
     */
    @JvmStatic
    fun currentTime(): String {
        return getDate("yyyy-MM-dd HH:mm:ss", currentTimeMillis())
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param time1  日期一，毫秒表示
     * @param time2  日期二，毫秒表示
     * @return boolean
     */
    fun isSameDay(time1: Long, time2: Long): Boolean {
        val time = Time()
        time.set(time1)
        val thenYear = time.year
        val thenMonth = time.month
        val thenMonthDay = time.monthDay
        time.set(time2)
        return thenYear == time.year && thenMonth == time.month && thenMonthDay == time.monthDay
    }

    /**
     * 判断两个日期恰好相隔n天（n 为正整数）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return boolean
     */
    fun isAfterDays(startTime: Long, endTime: Long, days: Int): Boolean {
        val time = Time()
        val deadTime = getNextDay(startTime, days)
        time.set(deadTime)
        val thenYear = time.year
        val thenMonth = time.month
        val thenMonthDay = time.monthDay
        time.set(endTime)
        return thenYear == time.year && thenMonth == time.month && thenMonthDay == time.monthDay
    }

    /**
     * 获取从某个日期开始计算未来日期的时间戳
     *
     * @param startTime 开始日期
     * @param index index表示今天后的第几天，0表示今天
     * @return boolean
     */
    fun getNextDay(startTime: Long, index: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = startTime
        calendar.add(Calendar.DAY_OF_MONTH, index)
        return calendar.time.time
    }

    /**
     * 获取今天开始计算未来日期的时间戳
     *
     * @param index index表示今天后的第几天，0表示今天
     * @return boolean
     */
    fun getNextDay(index: Int): Long { // 获取日期，index表示今天后的第几天
        return getNextDay(currentTimeMillis(), index)
    }

    /**
     * 将时间date转化成String类型
     *
     * @param template 时间的格式，为正则表达式，如 “YYYY-MM-dd HH:mm:ss”
     * @param date 要转换日期，单位为毫秒
     * @return String类型日期，如 “2015年08月08日 12:00:00”
     */
    fun getDate(template: String?, date: Long): String {
        if (date > 0) {
            val format = SimpleDateFormat(template, Locale.CHINA)
            return format.format(Date(date))
        }
        return ""
    }

    /**
     * 通过判断将时间转换为更容易理解的具体词语
     *
     * @param time 要转换的时间
     * @return String（刚刚 几分钟前 几小时前 几天前 具体时间）
     */
    fun transTime(time: Long): String {
        if (time > 0) {
            var btm = (System.currentTimeMillis() - time) / 1000
            return if (btm <= 60) {
                "刚刚"
            } else {
                btm = btm / 60
                if (btm <= 60) {
                    btm.toString() + "分钟前"
                } else {
                    btm = btm / 60
                    if (btm <= 24) {
                        btm.toString() + "小时前"
                    } else {
                        btm = btm / 24
                        if (btm <= 3) {
                            btm.toString() + "天前"
                        } else {
                            val format =
                                SimpleDateFormat("yyyy.MM.dd  HH:mm:ss", Locale.CHINA)
                            format.format(Date(time))
                        }
                    }
                }
            }
        }
        return ""
    }
}
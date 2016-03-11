package com.naivor.app.extras.utils;

import android.util.Log;

/**
 * LogUtil 是一个日志打印类，用来控制日志的打印，比如正式版本发布的时候取消打印所有日志
 *
 * Created by tianlai on 16-3-7.
 */
public final class LogUtil {

    private static boolean logable;
    private static int logLevel;


    public static enum LogPriority {
        VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT;
    }

    /**
     * 同Log.v
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int v(String tag, String msg) {
        return println(LogPriority.VERBOSE, tag, msg);
    }

    /**
     * 同Log.d
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int d(String tag, String msg) {
        return println(LogPriority.DEBUG, tag, msg);
    }

    /**
     * 同Log.i
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int i(String tag, String msg) {
        return println(LogPriority.INFO, tag, msg);
    }

    /**
     * 同Log.w
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int w(String tag, String msg) {
        return println(LogPriority.WARN, tag, msg);
    }

    /**
     * 同Log.e
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int e(String tag, String msg) {
        return println(LogPriority.ERROR, tag, msg);
    }

    /**
     * 获取是否可打印日志
     *
     * @return
     */
    public static boolean isLogable() {
        return logable;
    }

    /**
     * 设置是否打印日志
     *
     * @param logable
     */
    public static void setDebugMode(boolean logable) {
        LogUtil.logable = logable;
    }

    /**
     * 设置日志等级
     *
     * @param level
     */
    public static void setLogLevel(LogPriority level) {
        LogUtil.logLevel = level.ordinal();
    }

    /**
     *  日志输出
     *
     * @param priority
     * @param tag
     * @param msg
     * @return
     */
    private static int println(LogPriority priority, String tag, String msg) {
        int level = priority.ordinal();

        if (logable && logLevel <= level) {
            return Log.println(level + 2, tag, msg);
        } else {
            return -1;
        }
    }

}
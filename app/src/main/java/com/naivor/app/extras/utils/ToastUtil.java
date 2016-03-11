package com.naivor.app.extras.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * ToastUtil  提示工具类,处理整个应用的toast提示，使用单例实现，避免在大量Toast消息弹出的时候某些消息无法显示
 *
 * @author tianlai
 */
public class ToastUtil {
    private static Toast toast;
    private static long time;
    private static String mMessage;

    public static void initialize(Context context) {
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    /**
     * 显示消息
     *
     * @param message 消息
     */
    public static void showToast(String message) {
        long now = System.currentTimeMillis();

        if (isNeedShow(now) || !message.equals(mMessage)) {
            cancleToast();

            toast.setText(message);

        } else {

            return;
        }

        time = now;
        mMessage = message;

        toast.show();

    }

    /**
     * 判断是否需要显示消息
     *
     * @param now 现在时间
     */
    private static boolean isNeedShow(long now) {
        return time == 0 || (now - time) > 2000;
    }

    /**
     * 取消显示的toast
     */
    public static void cancleToast() {
        toast.cancel();
    }

}

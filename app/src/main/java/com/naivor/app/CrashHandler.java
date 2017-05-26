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

package com.naivor.app;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.naivor.app.common.utils.AppUtil;
import com.naivor.app.common.utils.DateUtil;
import com.naivor.app.common.utils.LogUtil;
import com.naivor.app.common.utils.SDCardUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 */

@Singleton
public class CrashHandler implements UncaughtExceptionHandler {
    private String TAG = "CrashHandler";

    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;

    // 用来存储设备信息和异常信息
    private Map<String, String> infos;

    //保存崩溃信息的目录
    private String path;

    // 程序的Context对象
    private Context context;

    //App的Activity管理类
    private UIController activityManager;

    @Inject
    public CrashHandler(Context app, UIController activityManager) {

        this.context = app;

        this.activityManager = activityManager;
    }

    /**
     * 初始化CrashHandler
     */
    public void init() {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);

        infos = new HashMap<String, String>();

        path = context.getString(R.string.crash_path);
    }


    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);

        }

        Toast.makeText(context, "很抱歉,我要崩溃了", Toast.LENGTH_LONG).show();

        //两秒后退出
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //关闭所有activity
                activityManager.ExitApplication();
                // 异常退出
                System.exit(1);
            }
        }, 2000);

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {

                Looper.prepare();

                // 收集设备参数信息
                collectDeviceInfo();
                // 保存日志文件
                saveCrashInfo2File(ex);

                Looper.loop();
            }
        }.start();

        return true;
    }

    /**
     * 收集设备参数信息
     */
    public void collectDeviceInfo() {

        infos.put("versionName", AppUtil.getAppVersionName(context));
        infos.put("versionCode", AppUtil.getAppVersionCode(context) + "");

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                LogUtil.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                LogUtil.e(TAG, "an error occured when collect crash info");
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        //打印日志，以便调试
        LogUtil.e(TAG, sb.toString());

        //保存错误信息到文件
        try {
            long timestamp = System.currentTimeMillis();
            String time = DateUtil.currentTime();
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (SDCardUtil.checkSDCardAvailable()) {

                SDCardUtil.saveFileToSDCard(path, fileName, sb.toString());

            }
            return fileName;
        } catch (Exception e) {
            LogUtil.e(TAG, "an error occured while writing file...\n" + "path" + path);
            e.printStackTrace();
        }
        return null;
    }
}
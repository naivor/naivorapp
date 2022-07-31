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
package com.naivor.app.common.utils

import android.Manifest
import android.content.pm.PackageManager
import android.content.Intent
import android.app.KeyguardManager
import android.app.KeyguardManager.KeyguardLock
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.app.ActivityManager
import android.app.ActivityManager.RunningTaskInfo
import android.content.ComponentName
import android.net.ConnectivityManager
import android.app.Activity
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import android.view.WindowManager
import android.annotation.TargetApi
import android.content.Context
import android.net.Uri
import com.naivor.app.common.utils.AppUtil
import android.net.wifi.WifiManager
import android.net.wifi.WifiInfo
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import java.io.File

/**
 * AppUtils是一个android工具类，主要包含一些常用的有关android调用的功能，比如拨打电话，判断网络，获取屏幕宽高等等
 */
object AppUtil {
    /**
     * 拨打电话
     *
     * @param context     当前上下文对象
     * @param phoneNumber 电话号码
     */
    fun call(context: Context, phoneNumber: String) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        context.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber")))
    }

    /**
     * 跳转至拨号界面
     *
     * @param context     当前上下文对象
     * @param phoneNumber 电话号码
     */
    fun callDial(context: Context, phoneNumber: String) {
        context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber")))
    }

    /**
     * 发送短信
     *
     * @param context     当前上下文对象
     * @param phoneNumber 电话号码
     * @param content     短信内容
     */
    fun sendSms(
        context: Context, phoneNumber: String?,
        content: String?
    ) {
        val uri = Uri.parse(
            "smsto:"
                    + if (TextUtils.isEmpty(phoneNumber)) "" else phoneNumber
        )
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", if (TextUtils.isEmpty(content)) "" else content)
        context.startActivity(intent)
    }

    /**
     * 唤醒屏幕并解锁
     *
     * @param context 当前上下文对象
     */
    fun wakeUpAndUnlock(context: Context) {
        val km = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val kl = km.newKeyguardLock("unLock")
        //解锁  
        kl.disableKeyguard()
        //获取电源管理器对象  
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag  
        val wl = pm.newWakeLock(
            PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_DIM_WAKE_LOCK,
            "bright"
        )
        //点亮屏幕  
        wl.acquire()
        //释放  
        wl.release()
    }

    /**
     * 判断当前App处于前台还是后台状态
     *
     * @param context 当前上下文对象
     * @return boolean
     */
    fun isApplicationBackground(context: Context): Boolean {
        val am = context
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = am.getRunningTasks(1)
        if (!tasks.isEmpty()) {
            val topActivity = tasks[0].topActivity
            if (topActivity!!.packageName != context.packageName) {
                return true
            }
        }
        return false
    }

    /**
     * 判断当前手机是否处于锁屏(睡眠)状态
     *
     * @param context 当前上下文对象
     * @return boolean
     */
    fun isSleeping(context: Context): Boolean {
        val kgMgr = context
            .getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        return kgMgr.inKeyguardRestrictedInputMode()
    }

    /**
     * 判断当前是否有网络连接
     *
     * @param context 当前上下文对象
     * @return boolean
     */
    fun isOnline(context: Context): Boolean {
        val manager = context
            .getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return if (info != null && info.isConnected) {
            true
        } else false
    }

    /**
     * 判断当前是否是WIFI连接状态
     *
     * @param context 当前上下文对象
     * @return boolean
     */
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiNetworkInfo = connectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return if (wifiNetworkInfo!!.isConnected) {
            true
        } else false
    }

    /**
     * 安装APK
     *
     * @param context 当前上下文对象
     * @param file apk文件
     */
    fun installApk(context: Context, file: File?) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        intent.addCategory("android.intent.category.DEFAULT")
        intent.type = "application/vnd.android.package-archive"
        intent.setDataAndType(
            Uri.fromFile(file),
            "application/vnd.android.package-archive"
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * 判断当前设备是否为手机
     *
     * @param context 当前上下文对象
     * @return boolean
     */
    fun isPhone(context: Context): Boolean {
        val telephony = context
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (telephony.phoneType == TelephonyManager.PHONE_TYPE_NONE) {
            false
        } else {
            true
        }
    }

    /**
     * 获取当前设备宽，单位px
     *
     * @param context 当前上下文对象
     * @return 屏幕宽度
     */
    fun getDeviceWidth(context: Context): Int {
        val manager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return manager.defaultDisplay.width
    }

    /**
     * 获取当前设备高，单位px
     *
     * @param context 当前上下文对象
     * @return 屏幕高度
     */
    fun getDeviceHeight(context: Context): Int {
        val manager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return manager.defaultDisplay.height
    }

    /**
     * 获取当前设备的IMEI，需要与上面的isPhone()一起使用
     *
     * @param context 当前上下文对象
     * @return String IMEi信息
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    fun getDeviceIMEI(context: Context): String {
        val deviceId: String
        deviceId = if (isPhone(context)) {
            val telephony = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephony.deviceId
        } else {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }
        return deviceId
    }

    /**
     * 获取当前设备的MAC地址
     *
     * @param context 当前上下文对象
     * @return MAC的地址
     */
    fun getMacAddress(context: Context): String {
        var macAddress: String
        val wifi = context
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        macAddress = info.macAddress
        if (null == macAddress) {
            return ""
        }
        macAddress = macAddress.replace(":", "")
        return macAddress
    }

    /**
     * 获取当前程序的版本
     *
     * @param context 当前上下文对象
     * @return String 当前版本号
     */
    @JvmStatic
    fun getAppVersionName(context: Context): String {
        var version = "0"
        try {
            version = context.packageManager.getPackageInfo(
                context.packageName, 0
            ).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return version
    }

    /**
     * 获取当前程序的版本号
     *
     * @param context 当前上下文对象
     * @return int 版本code
     */
    @JvmStatic
    fun getAppVersionCode(context: Context): Int {
        var version = 0
        try {
            version = context.packageManager.getPackageInfo(
                context.packageName, 0
            ).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return version
    }
}
package com.naivor.app.common.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import kotlin.jvm.JvmOverloads
import com.naivor.app.common.utils.SPUtils
import java.lang.NullPointerException

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : SP相关工具类
</pre> *
 */
object SPUtils {
    private const val DEFAULT = "DEFAULT_SP"
    private var sp: SharedPreferences? = null
    @JvmOverloads
    fun init( context: Context?, preferenceName: String? = context!!.packageName) {
        if (context == null) {
            throw NullPointerException("context can't be null")
        }
        if (null == sp) {
            sp = context.getSharedPreferences(
                if (TextUtils.isEmpty(preferenceName)) DEFAULT else preferenceName,
                Context.MODE_PRIVATE
            )
        }
    }

    /**
     * SP中写入String类型value
     *
     * @param key   键
     * @param value 值
     */
    fun save(key: String?,  value: String?) {
        checkNotNull(sp) { "SPUtils not initialized" }
        sp!!.edit().putString(key, value).apply()
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值`null`
     */
    fun getString(key: String?): String? {
        checkNotNull(sp) { "SPUtils not initialized" }
        return getString(key, null)
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getString(key: String?, defaultValue: String?): String? {
        checkNotNull(sp) { "SPUtils not initialized" }
        return sp!!.getString(key, defaultValue)
    }

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     */
    fun save(key: String?, value: Int) {
        checkNotNull(sp) { "SPUtils not initialized" }
        sp!!.edit().putInt(key, value).apply()
    }

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    fun getInt(key: String?): Int {
        checkNotNull(sp) { "SPUtils not initialized" }
        return getInt(key, -1)
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getInt(key: String?, defaultValue: Int): Int {
        checkNotNull(sp) { "SPUtils not initialized" }
        return sp!!.getInt(key, defaultValue)
    }

    /**
     * SP中写入long类型value
     *
     * @param key   键
     * @param value 值
     */
    fun save(key: String?, value: Long) {
        checkNotNull(sp) { "SPUtils not initialized" }
        sp!!.edit().putLong(key, value).apply()
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    fun getLong(key: String?): Long {
        checkNotNull(sp) { "SPUtils not initialized" }
        return getLong(key, -1L)
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getLong(key: String?, defaultValue: Long): Long {
        checkNotNull(sp) { "SPUtils not initialized" }
        return sp!!.getLong(key, defaultValue)
    }

    /**
     * SP中写入float类型value
     *
     * @param key   键
     * @param value 值
     */
    fun save(key: String?, value: Float) {
        checkNotNull(sp) { "SPUtils not initialized" }
        sp!!.edit().putFloat(key, value).apply()
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    fun getFloat(key: String?): Float {
        checkNotNull(sp) { "SPUtils not initialized" }
        return getFloat(key, -1f)
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getFloat(key: String?, defaultValue: Float): Float {
        checkNotNull(sp) { "SPUtils not initialized" }
        return sp!!.getFloat(key, defaultValue)
    }

    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     */
    fun save(key: String?, value: Boolean) {
        checkNotNull(sp) { "SPUtils not initialized" }
        sp!!.edit().putBoolean(key, value).apply()
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值`false`
     */
    fun getBoolean(key: String?): Boolean {
        checkNotNull(sp) { "SPUtils not initialized" }
        return getBoolean(key, false)
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        checkNotNull(sp) { "SPUtils not initialized" }
        return sp!!.getBoolean(key, defaultValue)
    }

    /**
     * SP中写入String集合类型value
     *
     * @param key    键
     * @param values 值
     */
    fun save(key: String?,  values: Set<String?>?) {
        checkNotNull(sp) { "SPUtils not initialized" }
        sp!!.edit().putStringSet(key, values).apply()
    }

    /**
     * SP中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值`null`
     */
    fun getStringSet(key: String?): Set<String>? {
        checkNotNull(sp) { "SPUtils not initialized" }
        return getStringSet(key, null)
    }

    /**
     * SP中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getStringSet(key: String?,  defaultValue: Set<String?>?): Set<String>? {
        checkNotNull(sp) { "SPUtils not initialized" }
        return sp!!.getStringSet(key, defaultValue)
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    val all: Map<String, *>
        get() {
            checkNotNull(sp) { "SPUtils not initialized" }
            return sp!!.all
        }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    fun remove(key: String?) {
        checkNotNull(sp) { "SPUtils not initialized" }
        sp!!.edit().remove(key).apply()
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return `true`: 存在<br></br>`false`: 不存在
     */
    operator fun contains(key: String?): Boolean {
        checkNotNull(sp) { "SPUtils not initialized" }
        return sp!!.contains(key)
    }

    /**
     * SP中清除所有数据
     */
    fun clear() {
        checkNotNull(sp) { "SPUtils not initialized" }
        sp!!.edit().clear().apply()
    }
}
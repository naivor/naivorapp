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

import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import kotlin.Throws

/**
 * SDCardUtil 是一个有关文件在SD卡上面存取的工具类
 */
object SDCardUtil {
    /**
     * Check the SD card state whether it is available
     *
     * @return
     */
    @JvmStatic
    fun checkSDCardAvailable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * Check if the file is exists
     *
     * @param filePath
     * @param fileName
     * @return
     */
    fun isFileExistsInSDCard(filePath: String?, fileName: String?): Boolean {
        var flag = false
        if (checkSDCardAvailable()) {
            val file = File(filePath, fileName)
            if (file.exists()) {
                flag = true
            }
        }
        return flag
    }

    /**
     * Write file to SD card
     *
     * @param filePath
     * @param filename
     * @param content
     * @return
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun saveFileToSDCard(filePath: String?, filename: String?, content: String): Boolean {
        var flag = false
        if (checkSDCardAvailable()) {
            val dir = File(filePath)
            if (!dir.exists()) {
                dir.mkdir()
            }
            val file = File(filePath, filename)
            val outStream = FileOutputStream(file)
            outStream.write(content.toByteArray())
            outStream.close()
            flag = true
        }
        return flag
    }

    /**
     * Read file as stream from SD card
     *
     * @param fileName String PATH =
     * Environment.getExternalStorageDirectory().getAbsolutePath() +
     * "/dirName";
     * @return
     */
    fun readFileFromSDCard(filePath: String, fileName: String): ByteArray? {
        var buffer: ByteArray? = null
        try {
            if (checkSDCardAvailable()) {
                val filePaht = "$filePath/$fileName"
                val fin = FileInputStream(filePaht)
                val length = fin.available()
                buffer = ByteArray(length)
                fin.read(buffer)
                fin.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return buffer
    }

    /**
     * Delete file
     *
     * @param filePath
     * @param fileName filePath =
     * android.os.Environment.getExternalStorageDirectory().getPath()
     * @return
     */
    fun deleteSDFile(filePath: String, fileName: String): Boolean {
        val file = File("$filePath/$fileName")
        return if (file == null || !file.exists() || file.isDirectory) false else file.delete()
    }

    /**
     * Temp file
     *
     * @param filePath
     * @param fileName filePath =
     * android.os.Environment.getExternalStorageDirectory().getPath()
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getTemFile(filePath: String?, fileName: String?): File {
        return if (checkSDCardAvailable()) {
            val file = File(filePath, fileName)
            if (!file.exists()) {
                file.parentFile.mkdirs() //创建缺少 的父路径
            }
            file.createNewFile() //创建文件
            file
        } else {
            File.createTempFile(null, ".jpg")
        }
    }

    /**
     * root  path
     *
     * @return
     */
    val rootPath: String
        get() = Environment.getExternalStorageDirectory().absolutePath
}
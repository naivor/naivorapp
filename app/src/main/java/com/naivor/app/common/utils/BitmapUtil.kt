package com.naivor.app.common.utils

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.text.TextUtils
import com.naivor.app.common.utils.BitmapUtil
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.Exception

object BitmapUtil {
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize > reqHeight
                && halfWidth / inSampleSize > reqWidth
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
    private fun createScaleBitmap(
        src: Bitmap, dstWidth: Int,
        dstHeight: Int
    ): Bitmap {
        val dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false)
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle() // 释放Bitmap的native像素数组
        }
        return dst
    }

    // 从Resources中加载图片
    fun decodeSampledBitmapFromResource(
        res: Resources?,
        resId: Int, reqWidth: Int, reqHeight: Int
    ): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options) // 读取图片长款
        options.inSampleSize = calculateInSampleSize(
            options, reqWidth,
            reqHeight
        ) // 计算inSampleSize
        options.inJustDecodeBounds = false
        val src = BitmapFactory.decodeResource(res, resId, options) // 载入一个稍大的缩略图
        return createScaleBitmap(src, reqWidth, reqHeight) // 进一步得到目标大小的缩略图
    }

    // 从sd卡上加载图片
    fun decodeSampledBitmapFromFd(
        pathName: String?,
        reqWidth: Int, reqHeight: Int
    ): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(pathName, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        val src = BitmapFactory.decodeFile(pathName, options)
        return createScaleBitmap(src, reqWidth, reqHeight)
    }

    /**
     * 压缩图片
     *
     * @param image
     * @param degree
     * @return
     */
    fun compressImage(image: Bitmap, degree: Int): Bitmap? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos) // 质量压缩方法，这�?100表示不压缩，把压缩后的数据存放到baos�?
        var options = 100
        while (baos.toByteArray().size / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset() // 重置baos即清空baos
            image.compress(
                Bitmap.CompressFormat.JPEG,
                options,
                baos
            ) // 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10 // 每次都减少10
        }
        val isBm = ByteArrayInputStream(baos.toByteArray()) // 把压缩后的数据baos存放到ByteArrayInputStream�?
        var bitmap = BitmapFactory.decodeStream(isBm, null, null) // 把ByteArrayInputStream数据生成图片
        val matrix = Matrix()
        matrix.preRotate(degree.toFloat())
        bitmap = Bitmap.createBitmap(bitmap!!, 0, 0, bitmap.width, bitmap.height, matrix, true)
        return bitmap
    }

    /**
     * @param path
     * @param bitmapMaxWidth
     * @return
     */
    fun initImage(path: String?, bitmapMaxWidth: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        val height = options.outHeight
        val width = options.outWidth
        var reqHeight = 0
        reqHeight = bitmapMaxWidth * height / width
        options.inSampleSize = calculateInSampleSize(options, bitmapMaxWidth, reqHeight)
        options.inJustDecodeBounds = false
        var bitmap = BitmapFactory.decodeFile(path, options)
        val bbb = compressImage(
            Bitmap.createScaledBitmap(bitmap!!, bitmapMaxWidth, reqHeight, false),
            readPictureDegree(path)
        )
        bitmap.recycle()
        bitmap = null
        return bbb
    }

    fun readPictureDegree(path: String?): Int {
        if (TextUtils.isEmpty(path)) {
            return 0
        }
        var degree = 0
        try {
            val exifInterface = ExifInterface(path!!)
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: Exception) {
        }
        return degree
    }
}
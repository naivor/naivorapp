package com.naivor.app.common.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class BitmapUtil {

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;

        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;

            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight

                    && (halfWidth / inSampleSize) > reqWidth) {

                inSampleSize *= 2;

            }

        }

        return inSampleSize;

    }

    // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响

    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,

                                            int dstHeight) {

        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);

        if (src != dst) { // 如果没有缩放，那么不回收

            src.recycle(); // 释放Bitmap的native像素数组

        }

        return dst;

    }

    // 从Resources中加载图片

    public static Bitmap decodeSampledBitmapFromResource(Resources res,

                                                         int resId, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(res, resId, options); // 读取图片长款

        options.inSampleSize = calculateInSampleSize(options, reqWidth,

                reqHeight); // 计算inSampleSize

        options.inJustDecodeBounds = false;

        Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图

        return createScaleBitmap(src, reqWidth, reqHeight); // 进一步得到目标大小的缩略图

    }

    // 从sd卡上加载图片

    public static Bitmap decodeSampledBitmapFromFd(String pathName,

                                                   int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(pathName, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        Bitmap src = BitmapFactory.decodeFile(pathName, options);

        return createScaleBitmap(src, reqWidth, reqHeight);

    }

    /**
     * 压缩图片
     *
     * @param image
     * @param degree
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int degree) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这�?100表示不压缩，把压缩后的数据存放到baos�?
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream�?
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        Matrix matrix = new Matrix();
        matrix.preRotate(degree);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    /**
     * @param path
     * @param bitmapMaxWidth
     * @return
     */
    public static Bitmap initImage(String path, int bitmapMaxWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int reqHeight = 0;
        int reqWidth = bitmapMaxWidth;
        reqHeight = (reqWidth * height) / width;
        options.inSampleSize = calculateInSampleSize(options, bitmapMaxWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        Bitmap bbb = compressImage(Bitmap.createScaledBitmap(bitmap, bitmapMaxWidth, reqHeight, false),
                readPictureDegree(path));
        bitmap.recycle();
        bitmap = null;
        return bbb;
    }

    public static int readPictureDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
        }
        return degree;
    }

}

package com.naivor.app.extras.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * SDCardUtil 是一个有关文件在SD卡上面存取的工具类
 */
public class SDCardUtil {

    /**
     * Check the SD card state whether it is available
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * Check if the file is exists
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static boolean isFileExistsInSDCard(String filePath, String fileName) {
        boolean flag = false;
        if (checkSDCardAvailable()) {
            File file = new File(filePath, fileName);
            if (file.exists()) {
                flag = true;
            }
        }
        return flag;
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
    public static boolean saveFileToSDCard(String filePath, String filename, String content) throws Exception {
        boolean flag = false;
        if (checkSDCardAvailable()) {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(filePath, filename);
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(content.getBytes());
            outStream.close();
            flag = true;
        }
        return flag;
    }

    /**
     * Read file as stream from SD card
     *
     * @param fileName String PATH =
     *                 Environment.getExternalStorageDirectory().getAbsolutePath() +
     *                 "/dirName";
     * @return
     */
    public static byte[] readFileFromSDCard(String filePath, String fileName) {
        byte[] buffer = null;
        try {
            if (checkSDCardAvailable()) {
                String filePaht = filePath + "/" + fileName;
                FileInputStream fin = new FileInputStream(filePaht);
                int length = fin.available();
                buffer = new byte[length];
                fin.read(buffer);
                fin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * Delete file
     *
     * @param filePath
     * @param fileName filePath =
     *                 android.os.Environment.getExternalStorageDirectory().getPath()
     * @return
     */
    public static boolean deleteSDFile(String filePath, String fileName) {
        File file = new File(filePath + "/" + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        return file.delete();
    }

    /**
     * Temp file
     *
     * @param filePath
     * @param fileName filePath =
     *                 android.os.Environment.getExternalStorageDirectory().getPath()
     * @return
     * @throws IOException
     */
    public static File getTemFile(String filePath, String fileName) throws IOException {
        if (checkSDCardAvailable()) {
            File file = new File(filePath, fileName);

            if (!file.exists()) {
                file.getParentFile().mkdirs();    //创建缺少 的父路径
            }

            file.createNewFile();   //创建文件

            return file;
        } else {
            File file = File.createTempFile(null, ".jpg");
            return file;
        }
    }

    /**
     * root  path
     *
     * @return
     */
    public static String getRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

}

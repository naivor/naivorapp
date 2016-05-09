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

package com.naivor.app.presentation.ui.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;

import com.naivor.app.R;
import com.naivor.app.extras.utils.LogUtil;
import com.naivor.app.extras.utils.SDCardUtil;
import com.naivor.app.presentation.widget.imagepicker.CropImageDialog;
import com.naivor.app.presentation.widget.imagepicker.PicKImageDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by tianlai on 16-4-12.
 */
public class PickImageHelper {
    private static final String TAG = "PickImageHelper";

    private PickType pickType;
    private Type type;

    private Activity activtiy;

    private PicKImageDialog picKImageDialog;
    private CropImageDialog cropImageDialog;

    private String cachePath;

    private OnImageOutputListener onImageOutputListener;

    @Inject
    public PickImageHelper(PicKImageDialog picKImageDialog) {
        this.picKImageDialog = picKImageDialog;
    }


    /**
     * 初始化
     *
     * @param activtiy
     */
    public void initPickImageHelper(final Activity activtiy) {
        this.activtiy = activtiy;

        picKImageDialog.setCancelable(false);

        cachePath = SDCardUtil.getRootPath() + activtiy.getString(R.string.cache_path);


        picKImageDialog.setPickImageListener(new PicKImageDialog.PickImageListener() {
            @Override
            public void onPickFinish(List<String> paths) {

                LogUtil.i(TAG, "已选的图片---" + paths.toString());

                picKImageDialog.dismiss();

                if (onImageOutputListener != null) {
                    if (pickType == PickType.HEADICON) {
                        onImageOutputListener.outputHeadImage(paths.get(0));
                    } else if (pickType == PickType.MULTIIMAGE) {
                        onImageOutputListener.outputPickedImages(paths);
                    }

                }
            }

            @Override
            public void toCropImage(String path) {
                LogUtil.i(TAG, "要剪裁的图片---" + path.toString());

                picKImageDialog.dismiss();
                initCropImageDialog(Uri.parse("file://" + path));

            }
        });


    }

    /**
     * 初始化剪裁的对话框
     *
     * @param path
     * @param activtiy
     */
    public void onCaptureResult(final String path, Activity activtiy) {
        this.activtiy = activtiy;

        new AlertDialog.Builder(activtiy)
                .setTitle("提示")
                .setMessage("是否剪裁图片？")
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dealCropResult(path);
                    }
                }).setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initCropImageDialog(Uri.parse("file://" + path));
            }
        }).create().show();

    }

    /**
     * 初始化剪裁的对话框
     *
     * @param uri
     */
    private void initCropImageDialog(Uri uri) {
        cropImageDialog = new CropImageDialog(activtiy);
        cropImageDialog.setCancelable(false);
        cropImageDialog.setCachePath(cachePath);

        cropImageDialog.setCropImageListener(new CropImageDialog.CropImageListener() {
            @Override
            public void onPickImage() {
                LogUtil.i(TAG, "---重新选择图片---");

                cropImageDialog.dismiss();
                picKImageDialog.setMaxNum(1);
                picKImageDialog.show();
            }

            @Override
            public void onCropSucceed(String path) {
                LogUtil.i(TAG, "剪裁后的图片路径---" + path);

                cropImageDialog.dismiss();
                dealCropResult(path);
            }
        });

        cropImageDialog.show(uri, true);
    }

    /**
     * 剪裁结果的处理
     *
     * @param path
     */
    private void dealCropResult(String path) {
        if (onImageOutputListener != null) {
            if (pickType == PickType.HEADICON) {
                onImageOutputListener.outputHeadImage(path);
            } else if (pickType == PickType.MULTIIMAGE) {
                List<String> paths = new ArrayList<String>();
                paths.add(path);
                onImageOutputListener.outputPickedImages(paths);
            }
        }
    }


    /**
     * 选择头像
     */
    public void pickHeadImage() {
        this.pickType = PickType.HEADICON;

        picKImageDialog.setMaxNum(1);

        showPickOrCapture();
    }

    /**
     * 选择图片
     */
    public void pickImages(int maxNum) {
        this.pickType = PickType.MULTIIMAGE;

        picKImageDialog.setMaxNum(maxNum);

        showPickOrCapture();

    }


    /**
     * 从相册选择或者拍照
     */
    private void showPickOrCapture() {
        new AlertDialog.Builder(activtiy)
                .setItems(R.array.pick_image, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:      //拍照
                                type = Type.CAPTURE;
                                if (onImageOutputListener != null) {
                                    onImageOutputListener.takePhoto();
                                }

                                break;
                            case 1:      //图库
                                type = Type.PICKIMAGE;

                                picKImageDialog.show();

                                break;
                            case 2:      //取消
                                break;
                        }
                    }
                }).create().show();
    }


    public OnImageOutputListener getOnImageOutputListener() {
        return onImageOutputListener;
    }

    public void setOnImageOutputListener(OnImageOutputListener onImageOutputListener) {
        this.onImageOutputListener = onImageOutputListener;
    }

    public PickType getPickType() {
        return pickType;
    }

    public void setPickType(PickType pickType) {
        this.pickType = pickType;
    }

    //选择类型，单张头像还是多张
    public enum PickType {
        HEADICON, MULTIIMAGE
    }

    //选择类型，拍照还是图库
    public enum Type {
        CAPTURE, PICKIMAGE
    }

    public static interface OnImageOutputListener {

        public void takePhoto();

        /**
         * 输出头像
         *
         * @param path
         */
        public void outputHeadImage(String path);

        /**
         * 输出选择的图片
         *
         * @param paths
         */
        public void outputPickedImages(List<String> paths);
    }

}

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

package com.naivor.app.features.repo.interceptor;

import android.graphics.Bitmap;

import com.naivor.app.common.utils.BitmapUtil;
import com.naivor.app.common.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by tianlai on 16-4-15.
 */
public class RequestBodyHelper {
    private static final String TAG = "request params";

    /**
     * 封装请求体（文件  参数）
     *
     * @param filePaths
     * @param params
     * @return
     */
    public static MultipartBody getRequestBody(List<String> filePaths, Map<String, String> params) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        //用来打印请求参数
        StringBuilder mBuilder = new StringBuilder();

        //添加上传参数
        if (params != null) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (value != null) {
                    builder.addFormDataPart(key, value);

                    mBuilder.append(key);
                    mBuilder.append("=");
                    mBuilder.append(value);
                    mBuilder.append("&");
                }
            }
        }


        //添加上传文件
        if (filePaths != null) {
            int size = filePaths.size();
            for (int i = 0; i < size; i++) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                Bitmap initImage = BitmapUtil.initImage(filePaths.get(i), 1080);
                initImage.compress(Bitmap.CompressFormat.JPEG, 70, bos);// 压缩
                builder.addFormDataPart("up_img_com_" + (i + 1), "up_img_com_" + (i + 1), RequestBody.create(MediaType.parse
                        ("image/png"), bos.toByteArray()));

                mBuilder.append("up_img_com_" + (i + 1));
                mBuilder.append("=");
                mBuilder.append(bos.size());
                mBuilder.append("&");
            }
        }

        LogUtil.i(TAG, mBuilder.toString());

        return builder.build();
    }


    /**
     * 封装请求体（只上传文件）
     *
     * @param filePaths
     * @return
     */
    public static MultipartBody getRequestBody(List<String> filePaths) {
        return getRequestBody(filePaths, null);
    }


    /**
     * 封装请求体（只上传参数）
     *
     * @param params
     * @return
     */
    public static MultipartBody getRequestBody(Map<String, String> params) {
        return getRequestBody(null, params);
    }

    /**
     * 封装单文件上传的请求体
     *
     * @param filePath
     * @return
     */
    public static MultipartBody getRequestBody(String filePath) {
        List<String> filePaths = new ArrayList<String>();
        filePaths.add(filePath);

        return getRequestBody(filePaths, null);
    }
}
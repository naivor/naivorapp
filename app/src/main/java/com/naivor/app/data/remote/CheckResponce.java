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

package com.naivor.app.data.remote;

import android.content.Context;

import com.naivor.app.data.remote.ApiResponce.BaseResponce;
import com.naivor.app.extras.utils.LogUtil;
import com.naivor.app.extras.utils.ToastUtil;

/**
 * CheckResponce 网络请求检查类
 * <p/>
 * Created by tianlai on 16-3-15.
 */
public class CheckResponce {
    public static final  String TAG="responce";

    public static boolean check(Context context,BaseResponce responce) {

        LogUtil.i(TAG, responce.toString());

        boolean isSucceed = false;
        
        switch (responce.getRespCode()) {
            case 1003: //返回为空
                isSucceed=true;
                LogUtil.w(TAG,"返回数据为空");
                break;
            case 8000:  //有新版本
                isSucceed=true;
                LogUtil.w(TAG,"应用有新版本，请更新");
                break;
            case 8001: //没有新版本
                isSucceed=false;
                ToastUtil.showToast(context, "当前已是最新版本");
                LogUtil.w(TAG, "应用已是最新版本");
                break;
            case 1000:
                isSucceed=true;
                LogUtil.w(TAG, "数据正常返回");
                break;
            case 1001:
                isSucceed=false;
                ToastUtil.showToast(context,"手机号码未注册");
                LogUtil.w(TAG, "手机号码未注册");
                break;
            case 1002:
                isSucceed=false;
                ToastUtil.showToast(context,"密码错误");
                LogUtil.w(TAG, "密码错误");
                break;
            case 2001:
                isSucceed=false;
                ToastUtil.showToast(context,"此手机号已注册");
                LogUtil.w(TAG, "此手机号已注册");
                break;
            case 2002:
                isSucceed=false;
                ToastUtil.showToast(context,"短信验证码错误");
                LogUtil.w(TAG, "短信验证码错误");
                break;
            case 2003:
                isSucceed=false;
                ToastUtil.showToast(context,"网络连接超时");
                LogUtil.w(TAG, "网络连接超时");
                break;
            case 2004:
                isSucceed=false;
                ToastUtil.showToast(context,"验证码已过期");
                LogUtil.w(TAG, "验证码已过期");
                break;
            case 3003:
                isSucceed=false;
                ToastUtil.showToast(context,"无此项目");
                LogUtil.w(TAG, "无此项目");
                break;
            case 9007:
                isSucceed=false;
                ToastUtil.showToast(context,"删除类容失败");
                LogUtil.w(TAG, "删除类容失败");
                break;
            case 9008:
                isSucceed=false;
                ToastUtil.showToast(context,"更新类容失败");
                LogUtil.w(TAG, "更新类容失败");
                break;
            case 9009:
                isSucceed=false;
                ToastUtil.showToast(context,"新增内容失败");
                LogUtil.w(TAG, "新增内容失败");
                break;
            case 9999:
                isSucceed=false;
                ToastUtil.showToast(context,"获取内容失败");
                LogUtil.w(TAG, "获取内容失败");
                break;
            case 2013:
                isSucceed=false;
                ToastUtil.showToast(context,"超过改签时间");
                LogUtil.w(TAG, "超过改签时间");
                break;
            case 0001:
                isSucceed=false;
                ToastUtil.showToast(context,"服务器错误");
                LogUtil.w(TAG, "服务器错误");
                break;
            default:
                isSucceed=false;
                ToastUtil.showToast(context,"网络请求出错");
                LogUtil.w(TAG, "网络请求出错");
                break;
        }

        return isSucceed;
    }

}

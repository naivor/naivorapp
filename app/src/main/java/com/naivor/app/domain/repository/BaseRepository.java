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

package com.naivor.app.domain.repository;


import android.content.Context;

import com.naivor.app.data.model.User;
import com.naivor.app.extras.utils.AppUtil;
import com.naivor.app.extras.utils.LogUtil;
import com.naivor.app.extras.utils.ToastUtil;

import retrofit2.Retrofit;

/**
 * BaseRepository app的数据仓库类，所有需要的数据都通过它获得
 * <p/>
 * Created by tianlai on 16-3-3.
 */

public abstract class BaseRepository<S> {
    private static final String TAG="repository";

    protected Retrofit retrofit;

    protected static User user;  //用户信息

    protected Context mContext;//Context对象，用来判断网络连接

    private boolean isCancle=false;

    protected S service;

    public BaseRepository(Context context, Retrofit retrofit) {
        this.mContext = context;
        this.retrofit = retrofit;

        service=retrofit.create(getServiceClass());
    }

    /**
     * 获取网络接口类型
     *
     * @return
     */
    protected abstract Class<S> getServiceClass();

    /**
     * 获取用户的信息
     *
     * @return
     */
    public static User getUser() {
        return user;
    }

    /**
     * 设置用户的信息
     *
     * @param user
     */
    public static void setUser(User user) {
        BaseRepository.user = user;
    }

    /**
     * 检查是否可以进行网络请求
     *
     * @return
     */
    public boolean isCanRequest() {
        //验证用户
        if (user == null || user.id() == 0) {
            ToastUtil.showToast(mContext, "用户为空,请重新登录");

            return false;
        }

        //验证网络连接
        if (!AppUtil.isOnline(mContext)) {
            ToastUtil.showToast(mContext, "请检查你的网络连接");

            return false;
        }

        return true;
    }

    /**
     * 判断请求是否取消
     *
     * @return
     */
    public boolean isCancled() {

        LogUtil.d(TAG, "请求是否取消：" + isCancle);

        return isCancle;
    }

    /**
     * 取消请求
     */
    public void cancleRequest() {
        this.isCancle = true;
    }

    /**
     * 开始请求
     */
    public void startRequest() {
        this.isCancle = false;
    }

    /**
     * 将置位转换成code
     *
     * @return
     */
    public int getPositionCode() {
        String position = user.position();

        if (position.equals("初级美容师")) {
            return 1;
        } else if (position.equals("高级美容师")) {
            return 2;
        } else if (position.equals("美容师导师")) {
            return 3;
        }

        return 0;
    }

}

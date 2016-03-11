package com.naivor.app.domain.repository;


import android.content.Context;
import android.database.Observable;

import com.naivor.app.data.model.User;
import com.naivor.app.data.remote.ApiResponce.LoginResponce;
import com.naivor.app.data.remote.ApiService.LoginApiService;
import com.naivor.app.extras.utils.AppUtil;
import com.naivor.app.extras.utils.ToastUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository app的数据仓库类，所有需要的数据都通过它获得
 * <p>
 * Created by tianlai on 16-3-3.
 */

@Singleton
public class Repository {

    private User user;  //用户信息

    private Context mContext;//Context对象，用来判断网络连接

    @Inject
    public Repository(Context context) {
        this.mContext=context;
    }

    /**
     * 设置用户的信息
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 检查是否可以进行网络请求
     *
     * @return
     */
    private boolean isCanRequest() {
        //验证用户
        if (user == null || user.getId() == 0) {
            ToastUtil.showToast("用户为空,请重新登录");

            return false;
        }

        //验证网络连接
        if (!AppUtil.isOnline(mContext)) {
            ToastUtil.showToast("请检查你的网络连接");

            return false;
        }

        return true;
    }


    @Inject
    LoginApiService loginApiService;

    /**
     * 登录请求
     *
     * @param mobile
     * @param psw
     * @return
     */
    public Observable<LoginResponce> login(String mobile, String psw) {
        return loginApiService.login(mobile, psw);
    }


}

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

package com.naivor.app.domain.presenter;

import android.text.TextUtils;

import com.naivor.app.data.cache.spf.SpfManager;
import com.naivor.app.data.model.User;
import com.naivor.app.data.remote.ApiResponce.LoginData;
import com.naivor.app.domain.repository.LoginRepository;
import com.naivor.app.domain.rxjava.MineSubscriber;
import com.naivor.app.extras.utils.EncryptUtil;
import com.naivor.app.extras.utils.ToastUtil;
import com.naivor.app.presentation.view.LoginView;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by tianlai on 16-3-3.
 */
public class LoginPresenter extends BasePresenter<LoginView, LoginRepository> {

    private SpfManager spfManager;

    private String phone;
    private String securityPsw;

    @Inject
    public LoginPresenter(SpfManager spfManager, LoginRepository repository) {
        super(repository);

        this.spfManager = spfManager;
    }


    /**
     * 登录应用
     */
    public void login() {

        phone = mUiView.getPhoneNum();

        //检查账号是否合法
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToast(context, "请输入用户名或邮箱");
            return;
        }

        final String passwd = mUiView.getPasswd();

        //检查密码是否合法
        if (TextUtils.isEmpty(passwd)) {
            ToastUtil.showToast(context, "请输入密码");
            return;
        }

        //密码MD5加密
        securityPsw = EncryptUtil.md5Encode(passwd);

        //进行登录
        mRepository.login(phone, securityPsw)
                .subscribe(new MineSubscriber<LoginData>(this) {
                    @Override
                    public void onNext(LoginData loginData) {
                        if (loginData != null) {

                            mRepository.setUser(loginData.userMapper());

                            mUiView.toMainPage();

                            saveLoginInfo(phone, passwd);
                        }
                    }
                });

    }

    /**
     * 保存账号密码，以便下次自动登录
     *
     * @param phone
     * @param securityPsw
     */
    private void saveLoginInfo(String phone, String securityPsw) {
        if (spfManager != null) {
            spfManager.saveString("phone", phone);
            spfManager.saveString("passwd", securityPsw);
        }
    }

    @Override
    public void loadErrorOccured(Throwable e) {
        super.loadErrorOccured(e);

        mUiView.toMainPage();
    }
}

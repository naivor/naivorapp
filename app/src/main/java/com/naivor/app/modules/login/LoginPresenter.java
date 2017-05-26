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

package com.naivor.app.modules.login;

import android.content.Context;

import com.naivor.app.common.base.BasePresenter;
import com.naivor.app.common.utils.EncryptUtil;
import com.naivor.app.common.utils.SPUtils;
import com.naivor.app.features.repo.LoginRepo;
import com.naivor.app.features.repo.deal.MineSubscriber;
import com.naivor.app.features.repo.responce.LoginData;
import com.naivor.app.others.UserManager;

import javax.inject.Inject;

/**
 * Created by tianlai on 16-3-3.
 */
public class LoginPresenter extends BasePresenter implements LoginVPContact.LoginPresenter{

    @Inject
    LoginRepo mRepository;

    @Inject
    public LoginPresenter(Context context) {
        super(context);
    }

    @Override
    public LoginVPContact.LoginView getView() {
        return (LoginVPContact.LoginView) view;
    }


    /**
     * 保存账号密码，以便下次自动登录
     *
     * @param phone
     * @param securityPsw
     */
    private void saveLoginInfo(String phone, String securityPsw) {
            SPUtils.save("phone", phone);
            SPUtils.save("passwd", securityPsw);
    }

    @Override
    public void login(String account, String password) {

        String psw = EncryptUtil.md5Encode(password);

        //进行登录
        mRepository.login(account, psw)
                .subscribe(new MineSubscriber<LoginData>(this) {
                    @Override
                    public void onNext(LoginData loginData) {
                        if (loginData != null) {

                            UserManager.get().update(loginData.userMapper());

                            getView().toMainPage();

                            saveLoginInfo(account, psw);
                        }
                    }
                });
    }
}

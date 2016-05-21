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
import com.naivor.app.data.remote.ApiResponce.LoginResponce;
import com.naivor.app.data.remote.CheckResponce;
import com.naivor.app.domain.repository.LoginRepository;
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
public class LoginPresenter extends BasePresenter<LoginView,LoginRepository> {

    private SpfManager spfManager;

    public LoginPresenter(LoginRepository mRepository) {
        super(mRepository);
    }

    private String phone;
    private String securityPsw;

    @Inject
    public LoginPresenter(SpfManager spfManager,LoginRepository repository) {
        super(repository);

        subscriber=new Subscriber() {
            @Override
            public void onCompleted() {
                if (mUiView!=null) {
                    mUiView.loadingComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (mUiView!=null) {
                    mUiView.loadingComplete();
                    mUiView.showError();

                    mUiView.toMainPage();
                }
            }

            @Override
            public void onNext(Object o) {
                if (!mRepository.isCancled()){
                    onResponce(o);
                }
            }
        };

        this.spfManager=spfManager;
    }

    @Override
    protected void onResponce(Object o) {
        if (o instanceof LoginResponce){
            LoginResponce loginResponce= (LoginResponce) o;

            if (CheckResponce.check(context, loginResponce)) {
                //保存登录信息，下次自动登录
                saveLoginInfo(phone, securityPsw);

                User user = loginResponce.userMapper();

                if (user.userType() != null) {

                    //保存用户信息到Repository，全局可使用
                    mRepository.setUser(user);

                    //登录结果处理
                    dealResponce(loginResponce);
                }
            }
        }

    }

    /**
     * 登录应用
     */
    public void login() {

        phone=mUiView.getPhoneNum();

        //检查账号是否合法
        if (TextUtils.isEmpty(phone)){
            ToastUtil.showToast(context,"请输入用户名或邮箱");
            return;
        }

        String passwd=mUiView.getPasswd();

        //检查密码是否合法
        if (TextUtils.isEmpty(passwd)){
            ToastUtil.showToast(context,"请输入密码");
            return;
        }

        //密码MD5加密
         securityPsw = EncryptUtil.md5Encode(passwd);

        //进行登录
        mRepository.login(phone, securityPsw)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //显示登录的进度条对话框
                        mUiView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

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


    /**
     * 登录结果的处理（给view加数据，控制跳转等等）
     *
     * @param loginResponce
     */
    private void dealResponce(LoginResponce loginResponce) {
        mUiView.toMainPage();
    }
}

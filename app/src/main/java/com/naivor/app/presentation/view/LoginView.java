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

package com.naivor.app.presentation.view;

/**
 *
 * Created by tianlai on 16-3-3.
 */
public interface LoginView extends BaseUiView {
    /**
     * 去到注册页面
     */
    public void toRegisterPage();

    /**
     * 获取登录手机号
     *
     * @return
     */
    public String getPhoneNum();

    /**
     * 获取登录密码
     *
     * @return
     */
    public String getPasswd();

    /**
     * 去主页面
     */
    public void toMainPage();

    /**
     * 去重置密码页面
     */
    public void toResetPasswdPage();
}

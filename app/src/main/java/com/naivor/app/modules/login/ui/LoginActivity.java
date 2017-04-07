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

package com.naivor.app.modules.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.naivor.app.R;
import com.naivor.app.common.base.BaseActivity;
import com.naivor.app.common.base.BasePresenter;
import com.naivor.app.features.di.component.ActivityComponent;
import com.naivor.app.modules.login.ui.register.RegisterActivity;
import com.naivor.app.modules.login.ui.resetPsw.ResetPswActivity;
import com.naivor.app.modules.main.ui.MainActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * LoginActivity app的登录页面
 * <p>
 * Created by tianlai on 16-3-3.
 */
public class LoginActivity extends BaseActivity implements LoginView {

    @Inject
    LoginPresenter loginPresenter;

    @Bind(R.id.edt_account)
    EditText edtAccount;

    @Bind(R.id.edt_psw)
    EditText edtPsw;

    @Bind(R.id.tv_login)
    TextView tvLogin;

    @Bind(R.id.tv_register)
    TextView tvRegister;

    @Bind(R.id.tv_forgetpsw)
    TextView tvForgetpsw;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_forgetpsw})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                //进行登录请求
                loginPresenter.login();
                break;
            case R.id.tv_register:
                toRegisterPage();
                break;

            case R.id.tv_forgetpsw:
                toResetPasswdPage();
                break;
        }
    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return loginPresenter;
    }


    @Override
    public void showEmpty() {

    }

    @Override
    public void toRegisterPage() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public String getPhoneNum() {
        return edtAccount.getText().toString().trim();
    }

    @Override
    public String getPasswd() {
        return edtPsw.getText().toString().trim();
    }

    @Override
    public void toMainPage() {
        startActivity(new Intent(this, MainActivity.class));

        finish();
    }

    @Override
    public void toResetPasswdPage() {
        startActivity(new Intent(this, ResetPswActivity.class));
    }
}

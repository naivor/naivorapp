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

package com.naivor.app.modules.login.ui.register;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.naivor.app.R;
import com.naivor.app.common.base.BaseActivity;
import com.naivor.app.common.base.BasePresenter;
import com.naivor.app.common.utils.ToastUtil;
import com.naivor.app.features.di.component.ActivityComponent;
import com.naivor.app.modules.others.EmptyPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by naivor on 16-4-2.
 */
public class RegisterActivity extends BaseActivity implements RegisterView {

    @Inject
    EmptyPresenter registerPresenter;

    @Bind(R.id.edt_name)
    EditText edtName;

    @Bind(R.id.edt_email)
    EditText edtEmail;

    @Bind(R.id.edt_psw)
    EditText edtPsw;

    @Bind(R.id.edt_pswagain)
    EditText edtPswagain;

    @Bind(R.id.tv_register)
    TextView tvRegister;

    @Bind(R.id.tv_center)
    TextView tvCenter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        tvCenter.setText("新用户注册");

        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_register)
    public void onClick(View v) {
        if (TextUtils.isEmpty(edtName.getText().toString())) {
            ToastUtil.showToast(context, "请填写用户名");
            return;
        }

        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            ToastUtil.showToast(context, "请填写您的邮箱");
            return;
        }

        String psw = edtPsw.getText().toString();
        if (TextUtils.isEmpty(psw)) {
            ToastUtil.showToast(context, "请填写密码");
            return;
        }

        String pswagain = edtPswagain.getText().toString();
        if (TextUtils.isEmpty(pswagain)) {
            ToastUtil.showToast(context, "请再次填写密码");
            return;
        }

        if (psw.equals(pswagain)) {
            ToastUtil.showToast(context, "注册成功");
        } else {
            ToastUtil.showToast(context, "两次输入的密码不一致");
        }


    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return registerPresenter;
    }

    @Override
    public void showEmpty() {

    }


}

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

package com.naivor.app.modules.login.register;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by naivor on 16-4-2.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.edt_name)
    EditText edtName;

    @BindView(R.id.edt_email)
    EditText edtEmail;

    @BindView(R.id.edt_psw)
    EditText edtPsw;

    @BindView(R.id.edt_pswagain)
    EditText edtPswagain;

    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindView(R.id.tv_center)
    TextView tvCenter;

    @BindView(R.id.toolbar)
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
            ToastUtil.show( "请填写用户名");
            return;
        }

        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            ToastUtil.show( "请填写您的邮箱");
            return;
        }

        String psw = edtPsw.getText().toString();
        if (TextUtils.isEmpty(psw)) {
            ToastUtil.show( "请填写密码");
            return;
        }

        String pswagain = edtPswagain.getText().toString();
        if (TextUtils.isEmpty(pswagain)) {
            ToastUtil.show( "请再次填写密码");
            return;
        }

        if (psw.equals(pswagain)) {
            ToastUtil.show( "注册成功");
        } else {
            ToastUtil.show( "两次输入的密码不一致");
        }


    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }



}

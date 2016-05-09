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

package com.naivor.app.presentation.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.naivor.app.R;
import com.naivor.app.domain.presenter.BasePresenter;
import com.naivor.app.domain.presenter.ResetPswPresenter;
import com.naivor.app.extras.utils.ToastUtil;
import com.naivor.app.presentation.di.component.ActivityComponent;
import com.naivor.app.presentation.view.ResetPswView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by naivor on 16-4-2.
 */
public class ResetPswActivity extends BaseActivity implements ResetPswView {

    @Inject
    ResetPswPresenter resetPswPresenter;

    @Bind(R.id.edt_email)
    EditText edtEmail;

    @Bind(R.id.tv_sendemail)
    TextView tvSendemail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = inflateView(R.layout.activity_resetpsw);

        setContentViewToRoot(contentView);

        setPageTitle("找回密码");

        ButterKnife.bind(this, contentView);
    }

    @OnClick(R.id.tv_sendemail)
    public void onClick(View v){
        if (TextUtils.isEmpty(edtEmail.getText().toString())){

            ToastUtil.showToast(context,"请填写您的注册邮箱");
        }else {
            ToastUtil.showToast(context,"发送邮件成功");
        }
    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return resetPswPresenter;
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

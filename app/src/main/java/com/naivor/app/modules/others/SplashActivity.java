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

package com.naivor.app.modules.others;


import android.content.Intent;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.naivor.app.R;
import com.naivor.app.common.base.BaseActivity;
import com.naivor.app.common.base.BasePresenter;
import com.naivor.app.features.di.component.ActivityComponent;
import com.naivor.app.modules.login.ui.LoginActivity;
import com.naivor.app.modules.main.ui.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * SplashActivity app的欢迎页面
 * <p>
 * Created by tianlai on 16-3-3.
 */
public class SplashActivity extends BaseActivity implements SplashView {

    @Inject
    EmptyPresenter splashPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        StatusBarUtil.setTransparent(this);

        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        toMainPage();
                    }
                }, 2000);

    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return splashPresenter;
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void toMainPage() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void toLoginPage() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}

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

import android.content.Context;
import android.os.Bundle;

import com.naivor.app.data.cache.spf.SpfManager;
import com.naivor.app.data.model.User;
import com.naivor.app.domain.repository.SplashRepository;
import com.naivor.app.presentation.view.SplashView;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * Created by tianlai on 16-3-9.
 */
public class SplashPresenter extends BasePresenter<SplashView,SplashRepository> {


    private SpfManager mSpfManager;

    private User mUser;

    @Inject
    public SplashPresenter(SplashRepository mRepository) {
        super(mRepository);
    }

    @Override
    protected void onResponce(Object o) {

    }


    @Override
    public void oncreate(Bundle savedInstanceState,Context context) {
        super.oncreate(savedInstanceState,context);


        mRepository.setUser(mUser);
    }

    @Override
    public void onResume(SplashView uiView) {
        super.onResume(uiView);

        //开启定时任务
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                if (mUser == null || mUser.id() == 0) {
                    mUiView.toLoginPage();
                } else {
                    mUiView.toMainPage();
                }
            }

        }, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

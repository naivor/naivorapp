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

package com.naivor.app.presentation.presenter;

import android.content.Context;
import android.os.Bundle;

import com.naivor.app.domain.repository.HomeRepository;
import com.naivor.app.presentation.di.PerFragment;
import com.naivor.app.presentation.view.HomeFragmentView;

import javax.inject.Inject;

/**
 * Created by tianlai on 16-3-18.
 */
@PerFragment
public class HomeFragmentPresenter extends BasePresenter<HomeFragmentView,HomeRepository> {

    @Inject
    public HomeFragmentPresenter(HomeRepository mRepository) {
        super(mRepository);
    }

    @Override
    public void oncreate(Bundle savedInstanceState, Context context) {
        super.oncreate(savedInstanceState, context);

    }

    @Override
    public void cancleLoading() {

    }

    @Override
    public void retryLoading() {

    }
}

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

package com.naivor.app.modules.main;


import com.naivor.app.common.base.Presenter;
import com.naivor.app.common.base.UiView;

/**
 * Created by tianlai on 16-3-9.
 */
public interface MainVPContact {
   interface MainView extends UiView{

           /**
            * 双击退出应用
            */
            void exitOnClickTwice(int keyCode);

           /**
            * 切换到HomeFragment
            */
            void toHomePage();

           /**
            * 切换到OrderFragment
            */
            void toOrderPage();

           /**
            *切换到DateFragment
            */
            void toDatePage();

           /**
            *切换到MineFragment
            */
            void toMinePage();

   }

   interface MainPresenter extends Presenter{

   }
}

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

package com.naivor.app;


import com.naivor.app.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * PageManager app的activity的管理类
 * <p/>
 * Created by tianlai on 16-3-7.
 */
@Singleton
public class PageManager {

    //Activity 容器
    private List<BaseActivity> activities;

    @Inject
    public PageManager() {
        this.activities = new ArrayList<>();
    }

    /**
     * 将activity加入列表
     *
     * @param activity
     */
    public void addActivity(BaseActivity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    /**
     * 将activity移除列表
     *
     * @param activity
     */
    public void removeActivity(BaseActivity activity) {
        if (activity != null) {
            activities.remove(activity);
        }
    }

    /**
     * 退出程序
     */
    public void ExitApplication() {
        for (BaseActivity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }

        // 杀死进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}

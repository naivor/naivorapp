package com.naivor.app.presentation.di.component;

import com.naivor.app.presentation.di.PerActivity;
import com.naivor.app.presentation.di.module.ActivityModule;
import com.naivor.app.presentation.ui.activity.LoginActivity;
import com.naivor.app.presentation.ui.activity.SplashActivity;

import dagger.Component;

/**
 * Created by tianlai on 16-3-9.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

    ////要注入的类型
//    void inject(BaseFragment baseFragment);

    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    //要暴露给其他依赖本组件的组件的依赖方法

}

package com.naivor.app.presentation.di.component;


import android.content.Context;
import android.view.LayoutInflater;

import com.naivor.app.ActivityManager;
import com.naivor.app.AppApplication;
import com.naivor.app.data.model.User;
import com.naivor.app.domain.repository.Repository;
import com.naivor.app.presentation.di.module.ApplicationModule;
import com.naivor.app.presentation.ui.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ApplicationComponent 生命周期跟Application一样的组件。可注入到自定义的Application类中，@Singletion代表各个注入对象为单例。
 * <p>
 * Created by tianlai on 16-3-3.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    //要注入的类型
    void inject(BaseActivity baseActivity);

    void inject(AppApplication appApplication);

    //要暴露给其他依赖本组件的组件的依赖方法
    Context context();

    User user();

    LayoutInflater layoutInflater();

    ActivityManager activityManager();

    Repository repository();


}

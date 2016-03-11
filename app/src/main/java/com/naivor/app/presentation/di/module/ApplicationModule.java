package com.naivor.app.presentation.di.module;

import android.content.Context;
import android.view.LayoutInflater;

import com.naivor.app.ActivityManager;
import com.naivor.app.AppApplication;
import com.naivor.app.data.model.User;
import com.naivor.app.domain.repository.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ApplicationModule  Activity的模块，为Activity里面需要自动实例化的类提供依赖
 * <p>
 * Created by tianlai on 16-3-3.
 */

@Module
public class ApplicationModule {
    private  AppApplication mApplication;

    public ApplicationModule(AppApplication application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ActivityManager provideActivityManager() {
        return new ActivityManager();
    }

    @Singleton
    @Provides
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(mApplication);
    }


    @Singleton
    @Provides
    Repository privateRepository(Context context){
        return new Repository(context);
    }

    @Provides
    User provideUser(){
        return new User();
    }

}

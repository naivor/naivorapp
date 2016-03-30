package com.naivor.app.presentation.di.module;

import com.naivor.app.data.cache.spf.SpfManager;
import com.naivor.app.data.model.User;
import com.naivor.app.domain.repository.Repository;
import com.naivor.app.presentation.di.PerActivity;
import com.naivor.app.presentation.presenter.LoginPresenter;
import com.naivor.app.presentation.presenter.SplashPresenter;
import com.naivor.app.presentation.ui.activity.BaseActivity;
import com.naivor.widget.requestdialog.LoadingDialog;

import dagger.Module;
import dagger.Provides;

/**
 * ActivityModule  Activity的模块，为Activity里面需要自动实例化的类提供依赖
 * <p>
 * Created by tianlai on 16-3-9.
 */
@Module
public class ActivityModule {
    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }


    @PerActivity
    @Provides
    LoadingDialog provideRequestDialog(){
        return new LoadingDialog(baseActivity);
    }

    @Provides
    SpfManager provideSpfManager(){
        return new SpfManager(baseActivity);
    }

    @PerActivity
    @Provides
    SplashPresenter provideSplashPresenter(SpfManager spfManager,User user,Repository repository) {
        return new SplashPresenter(spfManager,user,repository);
    }



}

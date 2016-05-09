package com.naivor.app;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.naivor.app.extras.utils.AppUtil;
import com.naivor.app.extras.utils.LogUtil;
import com.naivor.app.presentation.di.component.ApplicationComponent;
import com.naivor.app.presentation.di.component.DaggerApplicationComponent;
import com.naivor.app.presentation.di.module.ApplicationModule;
import com.naivor.app.presentation.di.module.NetworkModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;


/**
 * AppApplication 程序的Application类，统领全局
 * <p>
 * Created by tianlai on 16-3-3.
 */
public class AppApplication extends Application {
    private static final String BUGTAGS_KEY = "50da38c2a9e9a6771461a37b465566e7";

    private ApplicationComponent mAppComponent;

    @Inject
    CrashHandler crashHandler;

    @Inject
    OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();

        initInjector();

        injectApplication();

        ButterKnife.setDebug(BuildConfig.DEBUG);

        LogUtil.setDebugMode(BuildConfig.DEBUG);

        Fresco.initialize(this);

        crashHandler.init();

        initBugtags();
    }

    /**
     * 初始化bug管理工具
     */
    private void initBugtags() {
        BugtagsOptions options = new BugtagsOptions.Builder()
                .trackingLocation(true)       //是否获取位置
                .trackingCrashLog(true)       //是否收集闪退
                .trackingConsoleLog(true)     //是否收集控制台日志
                .trackingUserSteps(true)      //是否跟踪用户操作步骤
                .crashWithScreenshot(true)    //收集闪退是否附带截图
                .versionName(AppUtil.getAppVersionName(this))            //自定义版本名称
                .versionCode(AppUtil.getAppVersionCode(this))             //自定义版本号
//                .trackingNetworkURLFilter("(.*)")                      //自定义网络请求跟踪的 url 规则(收费版方可使用)
                .build();

        //debug包显示悬浮小球，release包不显示
        int level;
        if (BuildConfig.DEBUG) {
            level = Bugtags.BTGInvocationEventBubble;
        } else {
            level = Bugtags.BTGInvocationEventNone;
        }

        Bugtags.start(BUGTAGS_KEY, this, level, options);
    }

    /**
     * 进行注入
     */
    private void injectApplication() {
        mAppComponent.inject(this);
    }

    /**
     * 初始化注入器
     */
    private void initInjector() {
        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    /**
     * mAppComponent 的getter方法
     *
     * @return
     */
    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }


}

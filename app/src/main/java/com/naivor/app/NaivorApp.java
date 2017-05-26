package com.naivor.app;

import android.app.Application;
import android.util.Log;

import com.naivor.app.common.utils.LogUtil;
import com.naivor.app.common.utils.ToastUtil;
import com.naivor.app.features.di.InjectionManager;
import com.naivor.app.features.di.component.ApplicationComponent;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;


/**
 * NaivorApp 程序的Application类，统领全局
 * <p>
 * Created by tianlai on 16-3-3.
 */
public class NaivorApp extends Application {
    private static final String BUGTAGS_KEY = "50da38c2a9e9a6771461a37b465566e7";

    private ApplicationComponent mAppComponent;

    @Inject
    CrashHandler crashHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);

        InjectionManager.init(this);
        InjectionManager.get().getAppComponent().inject(this);

        ButterKnife.setDebug(BuildConfig.DEBUG);

        LogUtil.setDebugMode(BuildConfig.DEBUG);

        crashHandler.init();

        ToastUtil.init(this);

        initLog();
    }


    private void initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

        }
    }


}

package com.naivor.app.common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bugtags.library.Bugtags;
import com.naivor.app.NaivorApp;
import com.naivor.app.PageManager;
import com.naivor.app.features.model.User;
import com.naivor.app.common.rxJava.RxBus;
import com.naivor.app.common.utils.AppUtil;
import com.naivor.app.common.utils.LogUtil;
import com.naivor.app.common.utils.ToastUtil;
import com.naivor.app.features.di.component.ActivityComponent;
import com.naivor.app.features.di.component.ApplicationComponent;
import com.naivor.app.features.di.component.DaggerActivityComponent;
import com.naivor.app.features.di.module.ActivityModule;
import com.naivor.app.others.UserManager;
import com.naivor.app.others.widget.LoadingDialog;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * BaseActivity 是所有activity的基类，把一些公共的方法放到里面
 * <p/>
 * Created by tianlai on 16-3-3.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseUiView, DialogInterface.OnCancelListener {
    protected final String TAG = this.getClass().getSimpleName();

    private ActivityComponent activityComponent;

    private NaivorApp application;

    protected Context context;

    protected LayoutInflater inflater;

    protected BasePresenter presenter;

    protected LoadingDialog dialog;

    private boolean isComplete = true;

    protected Handler handler;

    private Runnable cancleRunnable;

    private BroadcastReceiver receiver;


    @Inject
    protected PageManager pageManager;

    private Subscription sub;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        cancleRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isComplete && dialog != null && dialog.isShowing()) {
                    dialog.cancel();
                    ToastUtil.showToast(context, "超时，请检查网络后重试");
                }
            }
        };

        //初始化
        application = (NaivorApp) getApplication();
        context = getApplicationContext();
        inflater = LayoutInflater.from(context);

        // 使用依赖注入
        initInjector();
        injectActivity(activityComponent);

        // 将当前activity加入ActivityManager中
        pageManager.addActivity(this);
        presenter = getPresenter();
        if (presenter != null) {
            presenter.oncreate(savedInstanceState, this);
        } else {
            throw new NullPointerException("presenter can't be null");
        }

        //初始化加载数据对话框
        dialog = new LoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(this);

    }

    /**
     * 进行注入
     *
     * @param activityComponent
     */
    protected abstract void injectActivity(ActivityComponent activityComponent);

    /**
     * 获取 ActivityModule
     *
     * @return
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * 获取注入Activity的Presenter对象
     *
     * @return
     */
    protected abstract BasePresenter getPresenter();

    /**
     * 把布局变成View
     *
     * @param layoutId
     * @return
     */
    protected View inflateView(int layoutId) {
        return inflater.inflate(layoutId, null);
    }

    /**
     * 设置注入器
     */
    private void initInjector() {
        ApplicationComponent appComponent = getAppComponent();

        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build();
    }


    /**
     * 获取 ApplicationComponent
     *
     * @return
     */
    public ApplicationComponent getAppComponent() {
        if (application != null) {
            return application.getAppComponent();
        } else {
            return ((NaivorApp) getApplication()).getAppComponent();
        }
    }

    /**
     * activityComponent的getter方法
     *
     * @return
     */
    public ActivityComponent getActivityComponent() {

        return activityComponent;
    }

    /**
     * 显示加载对话框
     */
    @Override
    public void showLoading() {
        isComplete = false;
        //延迟0.5秒显示加载对话框,如果网速较快,在0.5秒内返回了数据,则不显示加载对话框

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isComplete) {
                    showLoadingDialogNow(true);
                }
            }
        }, 160);

    }

    /**
     * 立即显示加载对话框
     */
    public void showLoadingDialogNow(boolean timeout) {
        if (AppUtil.isOnline(context)) {
            if (dialog != null && !dialog.isShowing()) {
                LogUtil.i("showLoadingDialog", "显示加载对话框");
                dialog.show();

                if (timeout) {
                    handler.postDelayed(cancleRunnable, 15000);
                }
            }
        }
    }

    /**
     * 取消加载对话框
     */
    @Override
    public void dismissLoading() {

        isComplete = true;

        handler.removeCallbacks(cancleRunnable);

        if (dialog != null && dialog.isShowing()) {
            LogUtil.i("showLoadingDialog", "取消加载对话框");
            dialog.dismiss();
        }
    }

    /**
     * 对于列表页面,数据条数为0
     */
    @Override
    public void showEmpty() {

    }


    /**
     * 数据加载出错
     */
    @Override
    public void showError(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showToast(context, msg);
            dismissLoading();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Bugtags.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        Bugtags.onPause(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        presenter.onSave(outState);
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        presenter.cancle();
    }

    @Override
    protected void onStop() {

        //取消正在进行的请求
        presenter.onStop();

        if (sub!=null&&sub.isUnsubscribed()) {
            sub.unsubscribe();
        }

        sub = null;

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        pageManager.removeActivity(this);

        presenter.onDestroy();

        presenter = null;

        dialog = null;

        ToastUtil.cancleToast();
    }


    /**
     * 设置应用的字体不随系统设置的更改而更改
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return resources;
    }


    /**
     * 隐藏软键盘
     */
    protected void hideIME() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 获取当前用户
     *
     * @return
     */
    public User getUser() {
        return UserManager.get().getUser();
    }

    /**
     * 更新当前用户
     *
     * @return
     */
    public void updateUser(User newUser) {
        UserManager.get().update(newUser);
    }


    /**
     * 将数据添加到总线中
     *
     * @param object
     */
    public void postBus(Object object) {
        RxBus.getDefault().post(object);
    }

    /**
     * 订阅事件
     *
     * @param c
     */
    public <T> void busEvent(Class<T> c, Action1<T> a) {
        sub = RxBus.getDefault().toObservable(c)
                .subscribe(a, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

    }
}
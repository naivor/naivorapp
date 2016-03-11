package com.naivor.app.presentation.ui.activity;

import android.os.Bundle;

import com.naivor.app.R;
import com.naivor.app.presentation.di.component.ActivityComponent;
import com.naivor.app.presentation.presenter.BasePresenter;
import com.naivor.app.presentation.presenter.LoginPresenter;
import com.naivor.app.presentation.view.LoginView;
import com.naivor.requestdialog.LoadingDialog;

import javax.inject.Inject;

/**
 * LoginActivity app的登录页面
 *
 * Created by tianlai on 16-3-3.
 */
public class LoginActivity extends BaseActivity implements LoginView{

    @Inject
    LoadingDialog dialog;

    @Inject
    LoginPresenter  loginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected LoadingDialog initLoadingDialog() {
        return dialog;
    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return loginPresenter;
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void LoadingComplete() {

    }

}

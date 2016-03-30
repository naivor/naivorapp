package com.naivor.app.presentation.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.naivor.app.R;
import com.naivor.app.presentation.di.component.ActivityComponent;
import com.naivor.app.presentation.presenter.BasePresenter;
import com.naivor.app.presentation.presenter.MainPresenter;
import com.naivor.app.presentation.view.MainView;
import com.naivor.widget.requestdialog.LoadingDialog;

import javax.inject.Inject;

/**
 * Created by tianlai on 16-3-11.
 */
public class MainActivity extends BaseActivity implements MainView{
    @Inject
    LoadingDialog dialog;

    @Inject
    MainPresenter  mainPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentToRoot(R.layout.activity_main);

        toolbar.setTitle("app首页");
    }

    @Override
    protected LoadingDialog initLoadingDialog() {
        return dialog;
    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {

    }

    @Override
    protected BasePresenter getPresenter() {
        return mainPresenter;
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

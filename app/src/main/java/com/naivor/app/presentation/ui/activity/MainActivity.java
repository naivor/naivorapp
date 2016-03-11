package com.naivor.app.presentation.ui.activity;

import com.naivor.app.R;
import com.naivor.app.presentation.di.component.ActivityComponent;
import com.naivor.app.presentation.presenter.BasePresenter;
import com.naivor.app.presentation.presenter.MainPresenter;
import com.naivor.app.presentation.view.MainView;
import com.naivor.requestdialog.LoadingDialog;

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
    protected int getContentViewId() {
        return R.layout.activity_main;
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

    @Override
    protected void onResume() {
        super.onResume();

        presenter.onResume(this);
    }
}

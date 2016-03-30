package com.naivor.app.presentation.presenter;

import com.naivor.app.presentation.usecase.LoginUseCase;
import com.naivor.app.presentation.view.LoginView;

import javax.inject.Inject;

/**
 * Created by tianlai on 16-3-3.
 */
public class LoginPresenter extends BasePresenter<LoginUseCase,LoginView>{

    @Inject
    public LoginPresenter() {

    }

    @Override
    public void cancleLoading() {

    }

    @Override
    public void retryLoading() {

    }

}

package com.naivor.app.presentation.presenter;

import android.os.Bundle;

import com.naivor.app.data.cache.spf.SpfManager;
import com.naivor.app.data.model.User;
import com.naivor.app.data.model.enums.UserType;
import com.naivor.app.domain.repository.Repository;
import com.naivor.app.presentation.usecase.SplashUseCase;
import com.naivor.app.presentation.view.SplashView;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * Created by tianlai on 16-3-9.
 */
public class SplashPresenter extends BasePresenter<SplashUseCase, SplashView> {


    private SpfManager mSpfManager;

    private User mUser;

    private Repository mRepository;


    @Inject
    public SplashPresenter(SpfManager spfManager,User user,Repository repository) {
        this.mSpfManager = spfManager;
        this.mUser=user;
        this.mRepository=repository;
    }

    @Override
    public void oncreate(Bundle savedInstanceState) {
        super.oncreate(savedInstanceState);


        if (mSpfManager == null) throw new NullPointerException("SharedPrefrences 不能为空");

        if (mUser == null) throw new NullPointerException("User 不能为空");

        mUser.setId(mSpfManager.getInt("uid"));
        mUser.setName(mSpfManager.getString("name"));
        mUser.setPhone(mSpfManager.getLong("phone"));
        mUser.setUserType(UserType.getType(mSpfManager.getString("userType")));

        mRepository.setUser(mUser);
    }

    @Override
    public void onResume(SplashView uiView) {
        super.onResume(uiView);

        //开启定时任务
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                if (mUser == null || mUser.getId() == 0) {
                    mUiView.toLoginPage();
                } else {
                    mUiView.toMainPage();
                }
            }

        }, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void cancleLoading() {

    }

    @Override
    public void retryLoading() {

    }
}

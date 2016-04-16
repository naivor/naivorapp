/*
 * Copyright (c) 2016. Naivor.All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naivor.app.presentation.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.naivor.app.R;
import com.naivor.app.domain.presenter.BasePresenter;
import com.naivor.app.domain.presenter.MainPresenter;
import com.naivor.app.extras.utils.ToastUtil;
import com.naivor.app.presentation.adapter.ViewPagerAdapter;
import com.naivor.app.presentation.di.component.ActivityComponent;
import com.naivor.app.presentation.ui.fragment.BaseFragment;
import com.naivor.app.presentation.ui.fragment.DateFragment;
import com.naivor.app.presentation.ui.fragment.HomeFragment;
import com.naivor.app.presentation.ui.fragment.MineFragment;
import com.naivor.app.presentation.ui.fragment.OrderFragment;
import com.naivor.app.presentation.ui.helper.ToolbarHelper;
import com.naivor.app.presentation.view.MainView;
import com.naivor.widget.requestdialog.LoadingDialog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnPageChange;

/**
 * MainActivity App的柱框架Activity，包含首页，订单，日程，我的四个Fragment
 *
 * Created by tianlai on 16-3-11.
 */
public class MainActivity extends BaseActivity implements MainView {

    @Inject
    LoadingDialog dialog;

    @Inject
    MainPresenter mainPresenter;

    @Bind(R.id.main_vp_container)
    ViewPager vp_container;

    @Bind(R.id.main_rg_container)
    RadioGroup rg_container;

    @Bind({R.id.main_rb_home, R.id.main_rb_orderform, R.id.main_rb_date, R.id.main_rb_mine})
    List<RadioButton> radioButtons;

    @Inject
    FragmentManager fragmentManager;

    @Inject
    ViewPagerAdapter pagerAdapter;

    @Inject
    HomeFragment homeFragment;

    @Inject
    OrderFragment orderFragment;

    @Inject
    DateFragment dateFragment;

    @Inject
    MineFragment mineFragment;

    @Inject
    List<Fragment> fragments;

    private TextView tv_toolbar;

    private boolean isCenterTitleStyle = false;

    private boolean isExitApp;

    private boolean isPagerChangePage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = inflateView(R.layout.activity_main);

        setContentViewToRoot(contentView);

        ButterKnife.bind(this, contentView);

        initViewPager();

    }


    /**
     * 初始化viewpager（初始化数据，绑定适配器）
     */
    private void initViewPager() {
        fragments.add(homeFragment);
        fragments.add(orderFragment);
        fragments.add(dateFragment);
        fragments.add(mineFragment);

        pagerAdapter.setDate(fragments);

        vp_container.setAdapter(pagerAdapter);
    }

    /**
     * radiobutton的点击事件
     *
     * @param buttonView
     * @param isChecked
     */
    @OnCheckedChanged({R.id.main_rb_home, R.id.main_rb_orderform, R.id.main_rb_date, R.id.main_rb_mine})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isPagerChangePage) {
            isPagerChangePage = false;
        } else {
            if (isChecked)
                switch (buttonView.getId()) {
                    case R.id.main_rb_home: // 首页
                        toHomePage();
                        break;
                    case R.id.main_rb_orderform: // 订单
                        toOrderPage();
                        break;
                    case R.id.main_rb_date: // 预约
                        toDatePage();
                        break;
                    case R.id.main_rb_mine: // 我的
                        toMinePage();
                        break;

                    default:
                        break;
                }

        }
    }

    /**
     * viewpager的切换事件
     *
     * @param paramInt
     */
    @OnPageChange(R.id.main_vp_container)
    public void onPageSelected(int paramInt) {
        isPagerChangePage = true;

        switch (paramInt) {
            case 0:
                toHomePage();
                break;
            case 1:
                toOrderPage();
                break;
            case 2:
                toDatePage();
                break;
            case 3:
                toMinePage();
                break;
            default:
                break;
        }

        radioButtons.get(paramInt).setChecked(true);

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
        return mainPresenter;
    }


    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);

        fragments = null;

    }

    @Override
    protected void initToolbar(Toolbar toolbar) {

    }

    /**
     * 设置MainActivity中rightAction的name
     *
     * @param rightActionName
     */
    public void setRightActionName(String rightActionName) {
        if (!isCenterTitleStyle) {
            if (tv_toolbar != null) {
                tv_toolbar.setText(rightActionName);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);  需要注释掉，不然在Fragment中getActivity()可能为null
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        exitOnClickTwice(keyCode);

        return false;
    }

    @Override
    public void exitOnClickTwice(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Timer timer = new Timer();
            if (isExitApp == false) {
                isExitApp = true;
                ToastUtil.showToast(context, "再按一次退出");
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        isExitApp = false;
                    }
                }, 2000);
            } else {
                pageManager.ExitApplication();
                System.exit(0);
            }
        }

    }

    @Override
    public void toHomePage() {
        changePage(0);
    }

    @Override
    public void toOrderPage() {
        changePage(1);
    }

    @Override
    public void toDatePage() {
        changePage(2);
    }

    @Override
    public void toMinePage() {
        changePage(3);
    }


    /**
     * 切换页面，改变页面标题
     *
     * @param page
     */
    private void changePage(int page) {
        vp_container.setCurrentItem(page, true);

        BaseFragment fragment = (BaseFragment) pagerAdapter.getItem(page);

        initPageTitle(fragment);

    }

    /**
     * 初始化页面标题
     *
     * @param fragment
     */
    public void initPageTitle(BaseFragment fragment) {
        toolbar.removeAllViews();

        ToolbarHelper helper = fragment.getToolbarHelper();
        tv_toolbar = helper.getTopView();
        isCenterTitleStyle = helper.isCenterTitleStyle();

        toolbar.addView(tv_toolbar);

        if (isCenterTitleStyle) {
            tv_toolbar.setText(helper.getTitle());
        }else {
            setPageTitle(helper.getTitle());
        }
    }

}

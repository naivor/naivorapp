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

package com.naivor.app.modules.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.naivor.app.R;
import com.naivor.app.common.base.BaseActivity;
import com.naivor.app.common.base.BaseFragment;
import com.naivor.app.common.base.BasePresenter;
import com.naivor.app.common.utils.ToastUtil;
import com.naivor.app.features.adapter.ViewPagerAdapter;
import com.naivor.app.features.di.component.ActivityComponent;
import com.naivor.app.modules.partfour.FourFragment;
import com.naivor.app.modules.partone.OneFragment;
import com.naivor.app.modules.partthree.ThreeFragment;
import com.naivor.app.modules.parttwo.TwoFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnPageChange;

/**
 * MainActivity App的柱框架Activity，包含首页，订单，日程，我的四个Fragment
 * <p>
 * Created by tianlai on 16-3-11.
 */
public class MainActivity extends BaseActivity implements MainVPContact.MainView {

    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.main_vp_container)
    ViewPager vp_container;

    @BindView(R.id.main_rb_home)
    RadioButton mainRbHome;
    @BindView(R.id.main_rb_orderform)
    RadioButton mainRbOrderform;
    @BindView(R.id.main_rb_date)
    RadioButton mainRbDate;
    @BindView(R.id.main_rb_mine)
    RadioButton mainRbMine;

    @Inject
    OneFragment partOneFragment;

    @Inject
    TwoFragment partTwoFragment;

    @Inject
    ThreeFragment partThreeFragment;

    @Inject
    FourFragment partFourFragment;

    @Inject
    List<Fragment> fragments;

    private List<RadioButton> radioButtons;

    private ViewPagerAdapter pagerAdapter;

    private boolean isExitApp;

    private boolean isPagerChangePage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initViewPager();

    }


    /**
     * 初始化viewpager（初始化数据，绑定适配器）
     */
    private void initViewPager() {
        radioButtons = new ArrayList<>();
        radioButtons.add(mainRbHome);
        radioButtons.add(mainRbDate);
        radioButtons.add(mainRbOrderform);
        radioButtons.add(mainRbMine);

        fragments.add(partOneFragment);
        fragments.add(partTwoFragment);
        fragments.add(partThreeFragment);
        fragments.add(partFourFragment);

        pagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

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
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mainPresenter;
    }


    @Override
    public void showEmpty() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        fragments = null;

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
                ToastUtil.show("再按一次退出");
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        isExitApp = false;
                    }
                }, 2000);
            } else {
                controller.ExitApplication();
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
        Toolbar toolbar = fragment.getToolbar();

        if (toolbar != null) {

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

}

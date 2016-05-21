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

package com.naivor.app.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.naivor.app.R;
import com.naivor.app.domain.presenter.BasePresenter;
import com.naivor.app.domain.presenter.HomeFragmentPresenter;
import com.naivor.app.extras.utils.FontUtil;
import com.naivor.app.extras.utils.ToastUtil;
import com.naivor.app.presentation.adapter.BaseAbsListAdapter;
import com.naivor.app.presentation.adapter.HomeListAdapter;
import com.naivor.app.presentation.di.component.FragmentComponent;
import com.naivor.app.presentation.ui.activity.MainActivity;
import com.naivor.app.presentation.ui.helper.LoadMoreHelper;
import com.naivor.app.presentation.view.HomeFragmentView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by tianlai on 16-3-18.
 */
public class HomeFragment extends BaseFragment implements HomeFragmentView {

    @Inject
    HomeFragmentPresenter homeFragmentPresenter;

    @Bind(R.id.tv_empty)
    TextView tvEmpty;

    @Bind(R.id.lv_list)
    ListView lvList;

    @Bind(R.id.ptr_refresh)
    PtrClassicFrameLayout ptrRefresh;

    @Inject
    HomeListAdapter adapter;
    
    @Inject
    LoadMoreHelper loadMoreHelper;
    
    private boolean isRefreshing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_home, container, false);

        setPageTitle();

        ButterKnife.bind(this, contentView);

        initListView();

        return contentView;
    }

    /**
     * 初始化下拉刷新部分
     */
    private void initListView() {

        //底部加载更多
        loadMoreHelper.setListView(lvList, inflater);
        loadMoreHelper.setOnLoadMoreListener(new LoadMoreHelper.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                homeFragmentPresenter.loadNextPageDate();
            }
        });


        //下拉刷新
        ptrRefresh.setLastUpdateTimeRelateObject(this);
        ptrRefresh.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lvList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (!isRefreshing) {
                    isRefreshing = true;
                    homeFragmentPresenter.refreshPageDate();
                }
            }
        });
        

        lvList.setAdapter(adapter);

    }

    @OnItemClick(R.id.lv_list)
    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
        ToastUtil.showToast(context,adapter.getItem(position));
    }


    @Override
    protected void initToolbarHelper() {

        //初始化Toolbar显示
        toolbarHelper.setTitle(FontUtil.addColor(baseActivity.getResources().getColor(R.color.green), "绿色页"));
        toolbarHelper.setIsCenterTitleStyle(true);
        toolbarHelper.setTopView(baseActivity.addCenterTitleView("", 0));
    }

    @Override
    protected void injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return homeFragmentPresenter;
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        tvEmpty.setText(context.getResources().getString(R.string.empty_message));

        showDataOrEmpty(true);
    }

    @Override
    public void showError() {
        super.showError();
        tvEmpty.setText(context.getResources().getString(R.string.error_message));

        showDataOrEmpty(true);
    }

    @Override
    public void loadingComplete() {
        super.loadingComplete();

        if (homeFragmentPresenter.isLoadMore()) {
            loadMoreHelper.loadMoreComplete();
        }

        if (isRefreshing){
            ptrRefresh.refreshComplete();
            isRefreshing = false;
        }

        ToastUtil.showToast(context,"加载完成");

    }


    @Override
    public void setPageTitle() {
        ((MainActivity) baseActivity).initPageTitle(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public BaseAbsListAdapter getListAdapter() {
        return adapter;
    }

    @Override
    public void showDataOrEmpty(boolean showEmpty) {
        if (showEmpty) {
            tvEmpty.setVisibility(View.VISIBLE);
            ptrRefresh.setVisibility(View.INVISIBLE);
        } else {
            tvEmpty.setVisibility(View.INVISIBLE);
            ptrRefresh.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void setHasMoreData(boolean hasMoreData) {
        loadMoreHelper.setHasMoreDate(hasMoreData);
    }

    @Override
    public void hideEmptyView() {
        showDataOrEmpty(false);
    }

    @Override
    public void resetBottomToOrigin() {
        loadMoreHelper.resetToOriginState();
    }
}

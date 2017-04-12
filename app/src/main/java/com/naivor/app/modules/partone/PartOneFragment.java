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

package com.naivor.app.modules.partone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.naivor.adapter.AdapterOperator;
import com.naivor.app.R;
import com.naivor.app.common.base.BaseFragment;
import com.naivor.app.common.base.BasePresenter;
import com.naivor.app.common.utils.ToastUtil;
import com.naivor.app.features.adapter.HomeListAdapter;
import com.naivor.app.features.di.component.FragmentComponent;
import com.naivor.app.modules.main.ui.MainActivity;
import com.naivor.loadmore.LoadMoreHelper;
import com.naivor.loadmore.OnLoadMoreListener;

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
public class PartOneFragment extends BaseFragment implements PartOneView {

    @Inject
    PartOnePresenter partOnePresenter;

    @Bind(R.id.tv_center)
    TextView tvCenter;

    @Bind(R.id.tv_right)
    TextView tvRight;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState == null) {

            return inflater.inflate(R.layout.fragment_home, container, false);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        tvCenter.setText("绿色");

        setPageTitle();

        initListView();

        partOnePresenter.requestData(loadMoreHelper.getIndex());
    }

    /**
     * 初始化下拉刷新部分
     */
    private void initListView() {

        //下拉刷新
        ptrRefresh.setLastUpdateTimeRelateObject(this);
        ptrRefresh.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lvList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadMoreHelper.reset();
                partOnePresenter.requestData(loadMoreHelper.getIndex());
            }
        });


        lvList.setAdapter(adapter);

        //底部加载更多
        loadMoreHelper.target(lvList);
        loadMoreHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(int i) {
                partOnePresenter.requestData(i);
            }

        });

    }

    @OnItemClick(R.id.lv_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.showToast(context, adapter.getItem(position));
    }


    @Override
    protected void injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return partOnePresenter;
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        tvEmpty.setText(context.getResources().getString(R.string.empty_message));

    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
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
    public void dismissLoading() {
        super.dismissLoading();

        if (isLoadMore()) {
            loadMoreHelper.loadComplete();
        } else {
            ptrRefresh.refreshComplete();
        }
    }

    /**
     * 获取客户列表的适配器
     *
     * @return
     */
    @Override
    public AdapterOperator getAdapter() {
        return adapter;
    }

    /**
     * 是否加载更多
     */
    @Override
    public boolean isLoadMore() {
        return loadMoreHelper.isLoadMore();
    }

    /**
     * 设置是否有更多数据
     *
     * @param hasMore
     */
    @Override
    public void setHasMore(boolean hasMore) {
        loadMoreHelper.setHasMore(hasMore);
    }

    @Override
    public void hideEmpty() {

    }

}

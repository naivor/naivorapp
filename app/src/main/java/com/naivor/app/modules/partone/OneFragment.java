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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naivor.adapter.AdapterOperator;
import com.naivor.app.R;
import com.naivor.app.common.base.BaseFragment;
import com.naivor.app.common.base.BasePresenter;
import com.naivor.app.common.utils.ToastUtil;
import com.naivor.app.features.adapter.HomeListAdapter;
import com.naivor.app.features.di.component.FragmentComponent;
import com.naivor.app.modules.main.MainActivity;
import com.naivor.loadmore.LoadMoreHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import timber.log.Timber;

/**
 * Created by tianlai on 16-3-18.
 */
public class OneFragment extends BaseFragment implements OneVPContact.OneView {

    @Inject
    OnePresenter onePresenter;

    @BindView(R.id.tv_center)
    TextView tvCenter;

    @BindView(R.id.tv_right)
    TextView tvRight;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    @BindView(R.id.ptr_refresh)
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

        onePresenter.requestData(loadMoreHelper.getIndex());
    }

    /**
     * 初始化下拉刷新部分
     */
    private void initListView() {

        rvContent.setLayoutManager(new LinearLayoutManager(context));

        //下拉刷新
        ptrRefresh.setLastUpdateTimeRelateObject(this);
        ptrRefresh.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rvContent, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadMoreHelper.reset();
                onePresenter.requestData(loadMoreHelper.getIndex());
            }
        });

        adapter.setInnerListener(new AdapterOperator.InnerListener<String>() {
            @Override
            public void onClick(View view, String itemData, int postition) {
                ToastUtil.show(adapter.getItem(postition));
            }
        });


        rvContent.setAdapter(adapter);

        //底部加载更多
        loadMoreHelper.target(rvContent);
        loadMoreHelper.setOnLoadMoreListener(i -> onePresenter.requestData(i));

    }

    @Override
    protected void injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return onePresenter;
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
        Timber.d("是否有下一页：%s",hasMore);
        loadMoreHelper.setHasMore(hasMore);
    }


    @Override
    public void hideEmpty() {

    }

}

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

package com.naivor.app.others.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.naivor.adapter.AdapterOperator;
import com.naivor.app.R;
import com.naivor.app.common.utils.LogUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * listview 的帮助类
 * <p/>
 * Created by tianlai on 16-4-1.
 */
public class LoadMoreHelper implements AbsListView.OnScrollListener {
    public final String TAG = this.getClass().getSimpleName();

    private ListView listView;
    private Context context;
    private LayoutInflater inflater;

    private State state;

    private boolean hasMoreDate;

    private OnLoadMoreListener onLoadMoreListener;

    private String loading;
    private String noMoreData;

    @Bind(R.id.v_loadmore)
    View loadMoreView;

    @Bind(R.id.more_pb_anim)
    ProgressBar pb_anim;

    @Bind(R.id.more_tv_content)
    TextView tv_content;

    @Inject
    public LoadMoreHelper() {

    }

    public ListView getListView() {
        return listView;
    }


    public void setListView(ListView listView, LayoutInflater inflater) {
        this.listView = listView;
        this.context = listView.getContext();
        this.inflater = inflater;

        listView.addFooterView(inflater.inflate(R.layout.footer_list_loadmore, null));

        ButterKnife.bind(this, listView);

        listView.setOnScrollListener(this);

        loading = context.getResources().getString(R.string.loading_more);
        noMoreData = context.getResources().getString(R.string.no_moredate);

        resetToOriginState();
    }

    /**
     * 底部置为初始状态
     */
    public void resetToOriginState() {
        changeState(State.ORIGIN);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (hasMoreDate) {
            if (totalItemCount > 1 && firstVisibleItem != 0) {
                if (!canChildScrollDown(view)) {

                    if (onLoadMoreListener != null && state != State.LOADING) {

                        changeState(State.LOADING);

                        LogUtil.i(TAG, "--加载更多--");

                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        } else {
            if (state != State.NOMOREDATA) {
                changeState(State.NOMOREDATA);
            }
        }

    }

    /**
     * 改变加载状态
     */
    private void changeState(State state) {
        this.state = state;

        switch (state) {
            case ORIGIN:
                loadMoreView.setVisibility(View.GONE);
                hasMoreDate = true;
                break;
            case LOADING:
                loadMoreView.setVisibility(View.VISIBLE);
                pb_anim.setVisibility(View.VISIBLE);
                tv_content.setText(loading);
                break;
            case COMPLETE:
                loadMoreView.setVisibility(View.GONE);
                break;
            case NOMOREDATA:
                loadMoreView.setVisibility(View.VISIBLE);
                pb_anim.setVisibility(View.GONE);
                tv_content.setText(noMoreData);
                break;
        }

    }


    /**
     * 加载更多数据完成
     */
    public void loadMoreComplete() {
        LogUtil.i(TAG, " --加载完成--");

        changeState(State.COMPLETE);
    }

    public boolean isHasMoreDate() {
        return hasMoreDate;
    }

    public void setHasMoreDate(boolean hasMoreDate) {
        this.hasMoreDate = hasMoreDate;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * 判断是否已到底部
     *
     * @param view
     * @return
     */
    public static boolean canChildScrollDown(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                int childCount = absListView.getChildCount();

                return childCount > 1
                        && (absListView.getLastVisiblePosition() < childCount || absListView.getChildAt(childCount)
                        .getBottom() > absListView.getPaddingBottom());

            } else {
                return view.getScrollY() < view.getMeasuredHeight();
            }
        } else {
            return view.canScrollVertically(1);
        }
    }



    /**
     * 加载更多的监听器
     */
    public static interface OnLoadMoreListener {
        public void onLoadMore();
    }

    /**
     * 底部的状态
     */
    enum State {
        ORIGIN, LOADING, COMPLETE, NOMOREDATA
    }

    public interface LoadMoreView {

        /**
         * 获取客户列表的适配器
         *
         * @return
         */
         AdapterOperator getListAdapter();


        /**
         * 隐藏emptyview
         */
         abstract void hideEmpty();

        /**
         * listview底部置为初始状态
         */
         abstract void resetBottom();
    }

    public interface LoadMorePresenter {

        /**
         * 下拉刷新页面数据
         */
         void refreshPage();

        /**
         * 置为初始状态并请求加载数据
         */
         void resetAndLoad();

        /**
         * 加载更多
         */
         void loadNextPage();


    }
}

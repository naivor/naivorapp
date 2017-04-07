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

package com.naivor.app.modules.parttwo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naivor.app.R;
import com.naivor.app.common.base.BaseFragment;
import com.naivor.app.common.base.BasePresenter;
import com.naivor.app.features.model.SimpleItem;
import com.naivor.app.features.adapter.TestRecyAdapter;
import com.naivor.app.features.di.component.FragmentComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tianlai on 16-3-18.
 */
public class PartTwoFragment extends BaseFragment implements PartTwoView {

    @Inject
    PartTwoPresenter partTwoPresenter;

    @Bind(R.id.tv_center)
    TextView tvCenter;

    @Bind(R.id.tv_right)
    TextView tvRight;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.rv_content)
    RecyclerView rvContent;

    @Inject
    TestRecyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            return inflater.inflate(R.layout.fragment_order, container, false);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        tvCenter.setText("橙色");

        rvContent.setLayoutManager(new LinearLayoutManager(context));
        rvContent.setAdapter(adapter);

        Observable.create(new Observable.OnSubscribe<SimpleItem>() {
            @Override
            public void call(Subscriber<? super SimpleItem> subscriber) {
                subscriber.onNext(SimpleItem.Builder().resId(0).content("我是 itemview ").build());
                subscriber.onCompleted();
            }
        })
                .repeat(10)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SimpleItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<SimpleItem> simpleItems) {
                        adapter.setItems(simpleItems);
                    }
                });

    }

    @OnClick({R.id.btn_add_header, R.id.btn_add_footer, R.id.btn_rm_header,
            R.id.btn_rm_footer, R.id.btn_clear_header, R.id.btn_clear_footer})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_header:
                adapter.addHeaderView(createHeader());
                break;
            case R.id.btn_add_footer:
                adapter.addFooterView(createFooter());
                break;
            case R.id.btn_rm_header:
                adapter.removeHeaderView(0);
                break;
            case R.id.btn_rm_footer:
                adapter.removeFooterView(0);
                break;
            case R.id.btn_clear_header:
                adapter.clearHeader();
                break;
            case R.id.btn_clear_footer:
                adapter.clearFooter();
                break;
            default:
                break;

        }
    }

    @Override
    protected void injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return partTwoPresenter;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public View createHeader() {
        View view = inflater.inflate(R.layout.list_item_home, null);
        ((TextView) find(view, R.id.tv_text)).setText("我是头部");
        return view;
    }

    public View createFooter() {
        View view = inflater.inflate(R.layout.list_item_home, null);
        ((TextView) find(view, R.id.tv_text)).setText("我是尾部");
        return view;
    }
}

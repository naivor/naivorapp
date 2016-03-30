package com.naivor.app.presentation.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.naivor.app.presentation.di.component.FragmentComponent;
import com.naivor.app.presentation.di.module.FragmentModule;
import com.naivor.app.presentation.presenter.BasePresenter;
import com.naivor.app.presentation.ui.activity.BaseActivity;
import com.naivor.app.presentation.view.BaseUiView;

public abstract class BaseFragment extends Fragment implements BaseUiView{

    private BasePresenter  presenter;

    protected BaseActivity  baseActivity;

    private FragmentComponent  fragmentComponent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        baseActivity= (BaseActivity) getActivity();

       fragmentComponent= baseActivity.getActivityComponent().plus(getFragmentModule());

        injectFragment(fragmentComponent);

        presenter=getPresenter();
    }

    protected abstract BasePresenter getPresenter();

    private FragmentModule  getFragmentModule(){
        return new FragmentModule(this);
    }

    protected abstract void injectFragment(FragmentComponent fragmentComponent);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.oncreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        presenter.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        presenter.onSave(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }
}

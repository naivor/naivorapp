package com.naivor.app.presentation.di.module;

import android.support.v4.app.Fragment;

import dagger.Module;

/**
 * FragmentModule  Fragment的模块，为Fragment里面需要自动实例化的类提供依赖
 *
 * Created by tianlai on 16-3-10.
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }


}

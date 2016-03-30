package com.naivor.app.presentation.di.component;

import com.naivor.app.presentation.di.PerFragment;
import com.naivor.app.presentation.di.module.FragmentModule;
import com.naivor.app.presentation.ui.fragment.BaseFragment;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by tianlai on 16-3-10.
 */
@PerFragment
@Subcomponent(modules =FragmentModule.class)
public interface FragmentComponent {


}

package com.naivor.app.presentation.di.component;

import com.naivor.app.presentation.di.PerFragment;
import com.naivor.app.presentation.di.module.FragmentModule;

import dagger.Component;

/**
 * Created by tianlai on 16-3-10.
 */
@PerFragment
@Component(dependencies = ActivityComponent.class,modules =FragmentModule.class)
public interface FragmentComponent {


}

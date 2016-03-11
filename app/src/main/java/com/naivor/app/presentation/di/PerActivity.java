package com.naivor.app.presentation.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * PerActivity 自定义注解，限定作用域为Activity
 *
 * Created by tianlai on 16-3-3.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {

}

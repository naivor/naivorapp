package com.naivor.app.presentation.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 *PerFragment 自定义注解，限定作用域为Fragment
 *
 * Created by tianlai on 16-3-10.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}

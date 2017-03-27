package com.naivor.app.modules.others;

import android.content.Context;

import com.naivor.app.common.base.BasePresenter;
import com.naivor.app.common.base.BaseUiView;

import javax.inject.Inject;

/**
 * 空的Presenter，用于不需要Presenter的页面占位
 * <p/>
 * Created by tianlai on 16-8-3.
 */
public class EmptyPresenter extends BasePresenter<BaseUiView> {

    @Inject
    public EmptyPresenter(Context context) {
        super(context);
    }
}

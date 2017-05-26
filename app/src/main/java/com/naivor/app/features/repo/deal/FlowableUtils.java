package com.naivor.app.features.repo.deal;


import android.annotation.SuppressLint;

import com.naivor.app.features.exception.ApiException;
import com.naivor.app.features.repo.responce.DataResult;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * rxJava的工具类
 * <p>
 * Created by tianlai on 17-3-16.
 */

public class FlowableUtils {


    /**
     * 线程切换处理，io线程发布，main线程订阅
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> transSchedule() {

        return flowable -> flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 线程切换处理，io线程发布，main线程订阅
     *
     * @return
     */
    @SuppressLint("NewApi")
    public static <T> FlowableTransformer<DataResult<T>, T> transData() {

      return flowable -> flowable.map(new Function<DataResult<T>, T>() {
          @Override
          public T apply(@NonNull DataResult<T> result) throws Exception {
              if (result.getCode() == 0) {
                  return result.getData();
              } else {
                  Flowable.error(new ApiException(result.getMessage(),result.getCode()));
              }

              return null;
          }
      });

    }


    /**
     * 线程切换处理，io线程发布，main线程订阅
     *
     * @return
     */
    @SuppressLint("NewApi")
    public static <T> FlowableTransformer<DataResult<T>, T> transDataAndSchedule() {
        return flowable -> flowable.compose(transData())
                .compose(transSchedule());
    }
}

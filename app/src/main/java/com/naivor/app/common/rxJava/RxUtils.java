package com.naivor.app.common.rxJava;

import com.naivor.app.features.repo.responce.DataResult;
import com.naivor.app.features.exception.ApiException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * rxJava的工具类
 *
 * Created by tianlai on 17-3-16.
 */

public class RxUtils {

    /**
     * 线程切换处理，io线程发布，main线程订阅
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> transSchedule() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> obs) {
                return obs.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 请求结果预处理，根据DataResult的情况做具体处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<DataResult<T>, T> transData() {
        return new Observable.Transformer<DataResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<DataResult<T>> resultObs) {
                return resultObs.flatMap(new Func1<DataResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(DataResult<T> result) {
                        if (result.getCode() == 200) {
                            return Observable.just(result.getData());
                        }

                        return Observable.error(new ApiException(result.getMessage(), result.getCode()));
                    }
                });
            }
        };
    }
}

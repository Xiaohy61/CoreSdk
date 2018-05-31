package com.skyward.android.sdk.base;

import android.accounts.NetworkErrorException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: skyward
 * date: 2018/1/2 0002
 * desc:    数据结果回调处理
 */
public abstract class BaseObserver<T> extends BasePresenterImpl implements Observer<T> {




    @Override
    public void onSubscribe(Disposable d) {
        addDisposable(d);
    }


    @Override
    public void onNext(T t) {
        try {

            onSuccess(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                netWorkError(e);
            } else {
                dataError(e);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    /**
     * 数据请求成功
     *
     * @param t 返回的数据
     * @throws Exception 数据异常
     */
    public abstract void onSuccess(T t) throws Exception;

    /**
     * /**
     * 网络请求错误
     *@param e 异常
     * @throws NetworkErrorException 网络异常
     */
    public abstract void netWorkError(Throwable e) throws NetworkErrorException;

    /**
     * 数据请求失败
     *
     * @param e 异常
     * @throws Exception 数据异常
     */
    public abstract void dataError(Throwable e) throws Exception;
}

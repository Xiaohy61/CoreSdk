package com.skyward.android.myapp.mvp;

/**
 * @author: skyward
 * date: 2018/1/4 0004
 * desc:
 */
public interface ModelDataCallBack<T> {
    void success(T t);
    void netWorkError(Throwable e);
    void dataError(Throwable e);
}

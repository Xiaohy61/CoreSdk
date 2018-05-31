package com.skyward.android.sdk.base;

import io.reactivex.disposables.Disposable;

/**
 * @author: skyward
 * date: 2018/4/20
 * desc:
 */
public interface BasePresenter {

    /**
     * 添加进 addDisposable
     *
     * @param d Disposable
     */
    void addDisposable(Disposable d);

    /**
     * 解绑所有绑定
     */
    void unbindComponent();
}

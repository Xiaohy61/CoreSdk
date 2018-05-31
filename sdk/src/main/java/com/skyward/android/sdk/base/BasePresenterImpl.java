package com.skyward.android.sdk.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author: skyward
 * date: 2018/4/20
 * desc:将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
 */
public class BasePresenterImpl<V extends BaseView> implements BasePresenter {


    protected V mView;

    private CompositeDisposable mCompositeDisposable;

    public BasePresenterImpl() {

    }


    public BasePresenterImpl(V view) {
        this.mView = view;
        mCompositeDisposable = new CompositeDisposable();

    }

    @Override
    public void addDisposable(Disposable d) {
        // 如果解绑了的话添加 mCompositeDisposable 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
    }

    @Override
    public void unbindComponent() {
        //所有网络请求解绑
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        //所有继承BaseView 的view解除引用
        if (mView != null) {
            mView = null;
        }
    }
}

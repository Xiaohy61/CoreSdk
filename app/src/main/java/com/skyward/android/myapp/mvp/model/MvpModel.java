package com.skyward.android.myapp.mvp.model;

import android.accounts.NetworkErrorException;

import com.skyward.android.myapp.api.ApiConfig;
import com.skyward.android.myapp.bean.LoginBean;
import com.skyward.android.myapp.mvp.ModelDataCallBack;
import com.skyward.android.sdk.base.BaseObserver;
import com.skyward.android.sdk.network.request.RxJavaCustomTransform;
import com.skyward.android.sdk.network.request.SubNetworkRequest;

/**
 * @author: skyward
 * date: 2018/4/21
 * desc:
 */
public class MvpModel implements IMvpModel{

    private  volatile static MvpModel instance;

    public static MvpModel getInstance(){
        if(instance == null){
            synchronized (MvpModel.class){
                if(instance == null){
                    instance = new MvpModel();
                }
            }
        }
        return instance;
    }


    @Override
    public void login(String account, String pwd, final ModelDataCallBack<LoginBean> dataCallBack) {
        SubNetworkRequest.getInstance().request().create(ApiConfig.class).getLogin(account,pwd,1,1)
                .compose(RxJavaCustomTransform.<LoginBean>defaultSchedulers())
                .subscribe(new BaseObserver<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean loginBean) throws Exception {
                        dataCallBack.success(loginBean);
                    }

                    @Override
                    public void netWorkError(Throwable e) throws NetworkErrorException {
                        dataCallBack.netWorkError(e);
                    }

                    @Override
                    public void dataError(Throwable e) throws Exception {
                        dataCallBack.dataError(e);
                    }
                });
    }


    @Override
    public void register(String account, String pwd, String phone, String sex) {

    }
}

package com.skyward.android.myapp.mvp.presenter;

import android.util.Log;

import com.skyward.android.myapp.bean.LoginBean;
import com.skyward.android.myapp.mvp.ModelDataCallBack;
import com.skyward.android.myapp.mvp.model.MvpModel;
import com.skyward.android.myapp.mvp.view.LoginView;
import com.skyward.android.sdk.base.BasePresenterImpl;

/**
 * @author: skyward
 * date: 2018/4/21
 * desc:
 */
public class LoginPresenter extends BasePresenterImpl<LoginView> {

    public LoginPresenter(LoginView view) {
        super(view);

    }

    public void login(String account, String pwd){
        if(mView != null){
            MvpModel.getInstance().login(account, pwd, new ModelDataCallBack<LoginBean>() {
                @Override
                public void success(LoginBean loginBean) {
                    Log.i("myLog"," onSuccess: "+loginBean.toString());
                    mView.getSuccessLoginData(loginBean);

                }

                @Override
                public void netWorkError(Throwable e) {
                    Log.i("myLog"," netWorkError: ");

                }

                @Override
                public void dataError(Throwable e) {
                    Log.i("myLog"," dataError: "+e.getMessage());
                }
            });
        }

    }
}
